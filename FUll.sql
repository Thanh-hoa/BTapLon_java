-- Tạo bảng Category
CREATE TABLE Category (
    id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(50) NOT NULL
);

-- Tạo bảng Product
CREATE TABLE Product (
    id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(50) NOT NULL UNIQUE,
    thumbnail NVARCHAR(50),
    description TEXT,
    price FLOAT NOT NULL,
    unit NVARCHAR(20),
    idCategory INT NOT NULL,
    FOREIGN KEY (idCategory) REFERENCES Category(id),
    CHECK (price > 0)
);

-- Tạo bảng Employee
CREATE TABLE Employee (
    id INT NOT NULL IDENTITY(1,1),
    idCard VARCHAR(12) NOT NULL UNIQUE,
    fullname NVARCHAR(50) NOT NULL,
    birthday DATE,
    address NVARCHAR(50),
    email NVARCHAR(30),
    gender BIT,
    phoneNumber VARCHAR(10) ,
    username NVARCHAR(40) NOT NULL UNIQUE,
    password NVARCHAR(20) NOT NULL,
    access NVARCHAR(30) NOT NULL,
    PRIMARY KEY(id),
    CHECK (LEN(password) >= 6),
    CHECK (LEN(idCard) = 12),
    CHECK (DATEDIFF(YEAR, birthday, GETDATE()) >= 18)
);

-- Tạo bảng Customer
CREATE TABLE Customer (
    id INT NOT NULL IDENTITY(1,1),
    fullname NVARCHAR(50),
    phoneNumber NVARCHAR(10) UNIQUE,
    PRIMARY KEY(id)
);

-- Tạo bảng Tables
CREATE TABLE Tables (
    id INT NOT NULL IDENTITY(1,1),
    status BIT NOT NULL,
    seats INT,
    PRIMARY KEY(id)
);

-- Tạo bảng Orders
CREATE TABLE Orders (
    id INT NOT NULL IDENTITY(1,1),
    idEmployee INT,
    idCustomer INT,
    idTable INT,
    orderTime DATETIME,
    PRIMARY KEY(id),
    FOREIGN KEY (idEmployee) REFERENCES Employee(id),
    FOREIGN KEY (idCustomer) REFERENCES Customer(id),
    FOREIGN KEY (idTable) REFERENCES Tables(id)
);

-- Tạo bảng OrderDetail
CREATE TABLE OrderDetail (
    id INT NOT NULL IDENTITY(1,1),
    idOrder INT NOT NULL,
    idProduct INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY(id, idOrder),
    FOREIGN KEY (idProduct) REFERENCES Product(id),
    FOREIGN KEY (idOrder) REFERENCES Orders(id),
    CHECK (quantity > 0)
);

-- Tạo bảng Invoices
CREATE TABLE Invoices (
    id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    idOrder INT ,
    idCustomer INT,
    idEmployee INT,
    invoiceTime DATETIME,
    calculateMoney FLOAT,
    quantity INT,
    paymentStatus BIT DEFAULT 0,
    paymentMethod BIT DEFAULT 0,
    FOREIGN KEY (idOrder) REFERENCES Orders(id),
    FOREIGN KEY (idCustomer) REFERENCES Customer(id),
    FOREIGN KEY (idEmployee) REFERENCES Employee(id)
);

-- Tạo bảng InvoiceDetail
CREATE TABLE InvoiceDetail (
    id INT NOT NULL IDENTITY(1,1),
    idInvoice INT,
    name NVARCHAR(50),
    quantity INT,
    unit NVARCHAR(20),
    calculateMoney FLOAT,
    PRIMARY KEY (id),
    FOREIGN KEY (idInvoice) REFERENCES Invoices(id)
);
-- Tạo bảng Shifts
CREATE TABLE Shifts (
    id INT NOT NULL IDENTITY(1,1),
    idEmployee INT NOT NULL,
    job NVARCHAR(50),
    workDate DATE,
    startTime TIME,
    endTime TIME,
    PRIMARY KEY (id),
    FOREIGN KEY (idEmployee) REFERENCES Employee(id),
	CHECK (
        (startTime = '07:00:00' AND endTime = '12:00:00') OR
        (startTime = '12:00:00' AND endTime = '18:00:00') OR
        (startTime = '18:00:00' AND endTime = '23:00:00')
    )
);
-- Tạo bảng VipCustomer
CREATE TABLE VipCustomer (
    id INT NOT NULL IDENTITY(1,1),
    idCustomer INT NOT NULL UNIQUE,
    PRIMARY KEY (id),
    FOREIGN KEY (idCustomer) REFERENCES Customer(id)
);

-- Trigger tự động thêm vào VipCustomer sau khi mua 20 hóa đơn
CREATE OR ALTER TRIGGER trg_InsertVipCustomer_AfterInvoice
ON Invoices
AFTER INSERT, UPDATE
AS
BEGIN
    -- Insert new VIP customers
    INSERT INTO VipCustomer (idCustomer)
    SELECT 
        i.idCustomer
    FROM
        (SELECT idCustomer
         FROM Invoices
         GROUP BY idCustomer
         HAVING COUNT(*) >= 20) AS i
    WHERE NOT EXISTS (
        SELECT 1
        FROM VipCustomer vc
        WHERE vc.idCustomer = i.idCustomer
    );
END;

CREATE OR ALTER TRIGGER Update_Invoices_After_Orders
ON Orders
AFTER INSERT, UPDATE
AS
BEGIN
    MERGE INTO Invoices i
	USING (
		SELECT
			o.id AS idInvoice,
			o.idEmployee AS idEmployee,
			o.orderTime AS invoiceTime
		FROM Orders o
		GROUP BY o.id, o.orderTime, o.idEmployee
	) AS source
	ON i.idOrder = source.idInvoice
	WHEN NOT MATCHED BY TARGET THEN
		INSERT (idOrder, idEmployee, invoiceTime)
    VALUES (source.idInvoice, source.idEmployee, source.invoiceTime);
END;

-- Tạo trigger Update_InvoiceDetail_After_OrderDetail
CREATE OR ALTER TRIGGER Update_InvoiceDetail_After_OrderDetail
ON OrderDetail
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    -- Xóa các bản ghi tương ứng từ InvoiceDetail cho OrderDetail bị xóa
    IF EXISTS (
        SELECT 1
        FROM deleted od
        INNER JOIN InvoiceDetail id ON id.idInvoice = od.idOrder
    )
    BEGIN
        DELETE id
        FROM InvoiceDetail id
        INNER JOIN deleted od ON id.idInvoice = od.idOrder;

		DELETE i
		FROM Invoices i
		WHERE NOT EXISTS (
			SELECT 1
			FROM InvoiceDetail id
			WHERE id.idInvoice = i.idOrder
		);

		DELETE o
		FROM Orders o
		WHERE NOT EXISTS (
			SELECT 1
			FROM InvoiceDetail id
			WHERE id.idInvoice = o.id
		);
    END;

    -- Cập nhật hoặc chèn các bản ghi mới vào InvoiceDetail cho OrderDetail đã được cập nhật hoặc thêm mới
    MERGE INTO InvoiceDetail AS id
	USING (
		SELECT 
			o.id AS idInvoice,
			p.name,
			p.unit,
			SUM(od.quantity) AS quantity,
			SUM(od.quantity * p.price) AS totalMoney
		FROM inserted od
		INNER JOIN Product p ON od.idProduct = p.id
		INNER JOIN Orders o ON od.idOrder = o.id
		INNER JOIN Invoices i ON o.id = i.idOrder
		GROUP BY o.id, p.name, p.unit
	) AS source
	ON id.idInvoice = source.idInvoice
	AND id.name = source.name
	WHEN MATCHED THEN
		UPDATE SET 
			id.unit = source.unit,
			id.quantity = source.quantity + id.quantity,
			id.calculateMoney = source.totalMoney + id.calculateMoney
	WHEN NOT MATCHED BY TARGET THEN
		INSERT (idInvoice, name, unit, quantity, calculateMoney)
		VALUES (source.idInvoice, source.name, source.unit, source.quantity, source.totalMoney);
END;

-- Trigger để cập nhật IdCustomer trong bảng Invoices sau mỗi lần thêm, cập nhật hoặc xóa dữ liệu
CREATE OR ALTER TRIGGER Update_Quantity_CalculateMoney_Invoices
ON InvoiceDetail
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    MERGE INTO Invoices AS i
    USING (
        SELECT 
		id.idInvoice AS idd,
		o.idCustomer AS idCustomerr,
		SUM(id.quantity) AS totalQuantity, 
		CASE
			WHEN v.idCustomer IS NOT NULL THEN 
				ROUND(SUM(id.calculateMoney) * 0.9, 2) 
			ELSE 
				ROUND(SUM(id.calculateMoney), 2)
			END AS totalCalculateMoney
		FROM InvoiceDetail id
		INNER JOIN Invoices i ON i.id = id.idInvoice
		INNER JOIN Orders o ON o.id = i.idOrder
		LEFT JOIN VipCustomer v ON i.idCustomer = v.idCustomer
		GROUP BY id.idInvoice, v.idCustomer, o.idCustomer
    ) AS source
    ON i.idOrder = source.idd
    WHEN MATCHED THEN
        UPDATE SET 
            i.quantity = source.totalQuantity,
            i.calculateMoney = source.totalCalculateMoney,
			i.idCustomer = source.idCustomerr;
END;



/* xong */
-- Insert Data into Category Table
INSERT INTO Category (name) VALUES 
	    (N'Coffee'),
		(N'Sinh tố và trà'),
		(N'Kem'),
		(N'Tráng miệng');
/* xong */
-- Insert Data into Product Table
INSERT INTO Product (name, thumbnail, description, price, unit, idCategory) VALUES 
            (N'Cà phê đen', 'coffee_black.jpg', N'Cà phê đen sấy khô', 25000, 'Ly', 1),
			(N'Cà phê sữa', 'coffee_milk.jpg', N'Cà phê sữa ngọt', 30000, 'Ly', 1),
			(N'Cà phê phin', 'vietnamese_coffee_dripper.jpg', N'Cà phê phin Việt Nam', 25000, 'Ly', 1),
			(N'Cà phê đá', 'iced_coffee.jpg', N'Cà phê đá mát lạnh', 25000, 'Ly', 1),
			(N'Espresso', 'espresso.jpg', N'Espresso đậm đà', 30000, 'Ly', 2),
			(N'Trà đào', 'peach_tea.jpg', N'Trà đào thơm ngon', 35000, 'Ly', 2),
			(N'Trà xanh', 'green_tea.jpg', N'Trà xanh không độ', 20000, 'Ly', 2),
			(N'Sinh tố bơ', 'smoothie_avocado.jpg', N'Sinh tố bơ tươi', 35000, 'Ly', 2),
			(N'Sinh tố xoài', 'mango_smoothie.jpg', N'Sinh tố xoài tươi mát', 35000, 'Ly', 2),
			(N'Kem vani', 'vanilla_ice_cream.jpg', N'Kem vani ngon mát', 30000, 'Ly', 3),
			(N'Kem chocolate', 'chocolate_ice_cream.jpg', N'Kem chocolate đắng ngọt', 30000, 'Ly', 3),
			(N'Kem dâu', 'strawberry_ice_cream.jpg', N'Kem dâu mát lạnh', 25000, 'Ly', 3),
			(N'Chè thái', 'thai_tea.jpg', N'Chè thái béo ngậy', 20000, 'Ly', 2),
			(N'Bánh bông lan', 'sponge_cake.jpg', N'Bánh bông lan mềm mịn', 30000, N'Cái', 4),
			(N'Bánh mì thịt gà', 'chicken_sandwich.jpg', N'Bánh mì thịt gà tươi ngon', 30000, N'Ổ', 4),
			(N'Bánh mì bơ tỏi', 'garlic_bread.jpg', N'Bánh mì bơ tỏi thơm ngon', 20000, N'Ổ', 4),
			(N'Bánh gato cà phê', 'coffee_cake.jpg', N'Bánh gato cà phê thơm ngon', 30000, N'Ổ', 4),
            (N'Bánh mì thịt heo', 'pork_sandwich.jpg', N'Bánh mì thịt heo sốt cà chua', 35000, N'Ổ', 4);
/* xong */
-- Insert Data into Employee Table
INSERT INTO Employee (idCard, fullname, birthday, address, email, gender, phoneNumber, username, password, access) VALUES 
        ('123456779012', N'Nguyễn Thanh Hòa', '2004-01-13', N'123 Đường ABC, Quận 1, TP.HCM', 'nguyenvana@example.com', 1, '0123456789', 'ThanhHoa', 'password123', 'Admin'),
		('224567890123', N'Phạm Văn Thuận', '2004-08-12', N'456 Đường XYZ, Quận 2, TP.HCM', 'tranthib@example.com', 0, '0234567890', 'VanThuan', 'pass456', 'Admin'),
        ('345678901234', N'Đặng Quang Vinh', '2004-03-26', N'789 Đường DEF, Quận 3, TP.HCM', 'levanc@example.com', 1, '0345678901', 'QuangVinh', 'abc@123', 'Admin'),
		('456789012345', N'Phạm Thị Duyên', '2000-11-30', N'1011 Đường GHI, Quận 4, TP.HCM', 'phamthid@example.com', 0, '0456789012', 'phamthid', 'qwe@123', 'employee'),
		('567890123456', N'Hoàng Văn Em', '2001-09-08', N'1213 Đường KLM, Quận 5, TP.HCM', 'hoangvane@example.com', 1, '0567890123', 'hoangvane', 'pass123', 'employee'),
		('678901234567', N'Nguyễn Thị Hồng', '2002-06-15', N'1415 Đường NOP, Quận 6, TP.HCM', 'nguyenthif@example.com', 0, '0678901234', 'nguyenthif', 'abc123', 'employee'),
		('789012345678', N'Trần Văn Giàu', '2003-04-22', N'1617 Đường QRS, Quận 7, TP.HCM', 'tranvang@example.com', 1, '0789012345', 'tranvang', 'pass@word', 'employee'),
		('890123456789', N'Lê Thị Hoa', '2001-01-05', N'1819 Đường TUV, Quận 8, TP.HCM', 'lethih@example.com', 0, '0890123456', 'lethih', 'qwe123', 'employee'),
		('901234567890', N'Phạm Văn Bá', '2003-07-20', N'2021 Đường WXY, Quận 9, TP.HCM', 'phamvani@example.com', 1, '0901234567', 'phamvani', '123456', 'employee'),
		('023345678901', N'Nguyễn Thị Minh Tú', '2004-02-14', N'2223 Đường ZAB, Quận 10, TP.HCM', 'hoangthik@example.com', 0, '0123456780', 'hoangthik', 'abc@456', 'employee');
/* xong */
-- Insert Data into Customer Table
INSERT INTO Customer (fullname, phoneNumber) VALUES 
        (N'Nguyễn Văn A', '0901234567'),
		(N'Trần Thị Bình', '0912345678'),
		(N'Lê Thành Công', '0923456789'),
		(N'Phạm Thị Dung', '0934567890'),
		(N'Hoàng Văn Đức', '0945678901'),
		(N'Vũ Thị Hương', '0956789012'),
		(N'Trần Đức Khánh', '0967890123'),
		(N'Lê Thị Lan', '0978901234'),
		(N'Nguyễn Thanh Mai', '0989012345'),
		(N'Nguyễn Văn Nam', '0990123456'),
		(N'Trần Thị Ngọc', '0123456789'),
		(N'Lê Thị Ôn', '0112345678'),
		(N'Nguyễn Văn Phú', '0101234567'),
		(N'Lê Thị Quỳnh', '0190123456'),
		(N'Phạm Văn Rồng', '0189012345'),
		(N'Nguyễn Thị Sen', '0178901234'),
		(N'Trần Văn Tài', '0167890123'),
		(N'Nguyễn Văn AHaha', '0903434567'),
		(N'Trần Công Bình', '0912345278'),
		(N'Lê Công', '0933457689');

-- Insert Data into VipCustomer Table
INSERT INTO VipCustomer (idCustomer) VALUES
(1),(2),(5);
/* xong */
-- Insert Data into Tables Table
INSERT INTO Tables (status, seats ) VALUES 
    (0, 4),
	(0, 4),
	(1, 4),
	(0, 4),
	(1, 4),
	(0, 6),
	(1, 6),
	(0, 6),
	(1, 6),
	(0, 6),
	(0,8),
	(0,8),
	(0,8),
	(0,8),
	(0,8),
	(0,8);
	/* xong */


	/* ngay 2/6 */
INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 1, 1, '2024-06-02 13:00:00');	 
INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (1, 2, 2),
    (1, 5, 3);
INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 2, 2, '2024-06-02 14:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (2, 9, 1),
    (2, 5, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 3, 3, '2024-06-02 15:00:00');	
	 
INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (3, 5, 2),
    (3, 7, 4);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 4, 1, '2024-06-02 16:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (4, 3, 2),
    (4, 8, 1);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 5, 2, '2024-06-02 17:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (5, 2, 3),
    (5, 4, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 6, 3, '2024-06-02 18:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (6, 6, 2),
    (6, 8, 1);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 7, 1, '2024-06-02 19:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (7, 3, 3),
    (7, 5, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 8, 2, '2024-06-02 20:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (8, 7, 1),
    (8, 2, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 9, 3, '2024-06-02 21:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (9, 5, 2),
    (9, 3, 3);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 10, 1, '2024-06-02 22:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (10, 2, 1),
    (10, 4, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 11, 2, '2024-06-02 23:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (11, 3, 2),
    (11, 7, 1);
/* ngay 3/6 */

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (2, 12, 1, '2024-06-03 8:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (12, 2, 2),
    (12, 3, 3);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (2, 13, 2, '2024-06-03 9:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (13, 1, 1),
    (13, 4, 3);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (2, 14, 3, '2024-06-03 10:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (14, 5, 1),
    (14, 7, 1);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 15, 1, '2024-06-03 13:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (15, 2, 2),
    (15, 5, 3);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 16, 2, '2024-06-03 14:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (16, 9, 1),
    (16, 5, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 17, 3, '2024-06-03 15:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (17, 5, 2),
    (17, 7, 4);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 18, 1, '2024-06-03 16:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (18, 3, 2),
    (18, 8, 1);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 19, 2, '2024-06-03 17:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (19, 2, 3),
    (19, 4, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 1, 3, '2024-06-03 18:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (20, 6, 2),
    (20, 8, 1);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 2, 1, '2024-06-03 19:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (21, 3, 3),
    (21, 5, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 3, 2, '2024-06-03 20:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (22, 7, 1),
    (22, 2, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 4, 3, '2024-06-03 21:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (23, 5, 2),
    (23, 3, 3);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 5, 1, '2024-06-03 22:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (24, 2, 1),
    (24, 4, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 6, 2, '2024-06-03 23:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (25, 3, 2),
    (25, 7, 1);

/* ngay 4/6 */
INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (2, 7, 1, '2024-06-04 8:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (26, 2, 2),
    (26, 3, 3);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (2, 8, 2, '2024-06-04 9:00:00');
	 
INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (27, 1, 1),
    (27, 4, 3);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (2, 9, 3, '2024-06-04 10:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (28, 5, 1),
    (28, 7, 1);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 10, 1, '2024-06-04 13:00:00');	
	 
INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (29, 2, 2),
    (29, 5, 3);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 11, 2, '2024-06-04 14:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (30, 9, 1),
    (30, 5, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 12, 3, '2024-06-04 15:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (31, 5, 2),
    (31, 7, 4);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 13, 1, '2024-06-04 16:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (32, 3, 2),
    (32, 8, 1);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime)
VALUES 
     (5, 14, 2, '2024-06-04 17:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (33, 2, 3),
    (33, 4, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 15, 3, '2024-06-04 18:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (34, 6, 2),
    (34, 8, 1);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 16, 1, '2024-06-04 19:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (35, 3, 3),
    (35, 5, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 17, 2, '2024-06-04 20:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (36, 7, 1),
    (36, 2, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 18, 3, '2024-06-04 21:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (37, 5, 2),
    (37, 3, 3);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 19, 1, '2024-06-04 22:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (38, 2, 1),
    (38, 4, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 20, 2, '2024-06-04 23:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (39, 3, 2),
    (39, 7, 1);

/* ngày 5/6 */
INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (2, 1, 1, '2024-06-05 8:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (40, 2, 2),
    (40, 3, 3);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (2, 2, 2, '2024-06-05 9:00:00');
	 
INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (41, 1, 1),
    (41, 4, 3);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (2, 3, 3, '2024-06-05 10:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (42, 5, 1),
    (42, 7, 1);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 4, 1, '2024-06-05 13:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (43, 2, 2),
    (43, 5, 3);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 5, 2, '2024-06-05 14:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (44, 9, 1),
    (44, 5, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 6, 3, '2024-06-05 15:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (45, 5, 2),
    (45, 7, 4);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 7, 1, '2024-06-05 16:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (46, 3, 2),
    (46, 8, 1);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 8, 2, '2024-06-05 17:00:00');
	 
INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (47, 2, 3),
    (47, 4, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 9, 3, '2024-06-05 18:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (48, 6, 2),
    (48, 8, 1);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 10, 1, '2024-06-05 19:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (49, 3, 3),
    (49, 5, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 11, 2, '2024-06-05 20:00:00');
	 
INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (50, 7, 1),
    (50, 2, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 12, 3, '2024-06-05 21:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (51, 5, 2),
    (51, 3, 3);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 13, 1, '2024-06-05 22:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (52, 2, 1),
    (52, 4, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 14, 2, '2024-06-05 23:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (53, 3, 2),
    (53, 7, 1);
/* Ngày 6/6 */
INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (2, 15, 1, '2024-06-06 8:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (54, 2, 2),
    (54, 3, 3);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (2, 16, 2, '2024-06-06 9:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (55, 1, 1),
    (55, 4, 3);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (2, 17, 3, '2024-06-06 10:00:00');


INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (56, 5, 1),
    (56, 7, 1);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 18, 1, '2024-06-06 13:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (57, 2, 2),
    (57, 5, 3);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 19, 2, '2024-06-06 14:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (58, 9, 1),
    (58, 5, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 20, 3, '2024-06-06 15:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (59, 5, 2),
    (59, 7, 4);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 1, 1, '2024-06-06 16:00:00');	 

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (60, 3, 2),
    (60, 8, 1);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (5, 2, 2, '2024-06-06 17:00:00');
	 
INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (61, 2, 3),
    (61, 4, 2);

INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES 
     (8, 3, 3, '2024-06-06 18:00:00');

INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES 
    (62, 6, 2),
    (62, 8, 1);

/* xong */
INSERT INTO Shifts (idEmployee, workDate, startTime, endTime, job) 
VALUES
	(1, '2024-06-01', '07:00:00', '12:00:00', N'Pha chế'),
	(2, '2024-06-01', '07:00:00', '12:00:00', N'Thu ngân'),
	(3, '2024-06-01', '07:00:00', '12:00:00', N'Phục vụ'),
	(4, '2024-06-01', '12:00:00', '18:00:00', N'Pha chế'),
	(5, '2024-06-01', '12:00:00', '18:00:00', N'Thu ngân'),
	(6, '2024-06-01', '12:00:00', '18:00:00', N'Phục vụ'),
	(7, '2024-06-01', '18:00:00', '23:00:00', N'Pha chế'),
	(8, '2024-06-01', '18:00:00', '23:00:00', N'Thu ngân'),
	(9, '2024-06-01', '18:00:00', '23:00:00', N'Phục vụ'),
	(1, '2024-06-02', '07:00:00', '12:00:00', N'Pha chế'),
	(2, '2024-06-02', '07:00:00', '12:00:00', N'Thu ngân'),
	(3, '2024-06-02', '07:00:00', '12:00:00', N'Phục vụ'),
	(4, '2024-06-02', '12:00:00', '18:00:00', N'Pha chế'),
	(5, '2024-06-02', '12:00:00', '18:00:00', N'Thu ngân'),
	(6, '2024-06-02', '12:00:00', '18:00:00', N'Phục vụ'),
	(7, '2024-06-02', '18:00:00', '23:00:00', N'Pha chế'),
	(8, '2024-06-02', '18:00:00', '23:00:00', N'Thu ngân'),
	(9, '2024-06-02', '18:00:00', '23:00:00', N'Phục vụ'),
	(1, '2024-06-03', '07:00:00', '12:00:00', N'Pha chế'),
	(2, '2024-06-03', '07:00:00', '12:00:00', N'Thu ngân'),
	(3, '2024-06-03', '07:00:00', '12:00:00', N'Phục vụ'),
	(4, '2024-06-03', '12:00:00', '18:00:00', N'Pha chế'),
	(5, '2024-06-03', '12:00:00', '18:00:00', N'Thu ngân'),
	(6, '2024-06-03', '12:00:00', '18:00:00', N'Phục vụ'),
	(7, '2024-06-03', '18:00:00', '23:00:00', N'Pha chế'),
	(8, '2024-06-03', '18:00:00', '23:00:00', N'Thu ngân'),
	(9, '2024-06-03', '18:00:00', '23:00:00', N'Phục vụ'),
	(1, '2024-06-04', '07:00:00', '12:00:00', N'Pha chế'),
	(2, '2024-06-04', '07:00:00', '12:00:00', N'Thu ngân'),
	(3, '2024-06-04', '07:00:00', '12:00:00', N'Phục vụ'),
	(4, '2024-06-04', '12:00:00', '18:00:00', N'Pha chế'),
	(5, '2024-06-04', '12:00:00', '18:00:00', N'Thu ngân'),
	(6, '2024-06-04', '12:00:00', '18:00:00', N'Phục vụ'),
	(7, '2024-06-04', '18:00:00', '23:00:00', N'Pha chế'),
	(8, '2024-06-04', '18:00:00', '23:00:00', N'Thu ngân'),
	(9, '2024-06-04', '18:00:00', '23:00:00', N'Phục vụ'),
	(1, '2024-06-05', '07:00:00', '12:00:00', N'Pha chế'),
	(2, '2024-06-05', '07:00:00', '12:00:00', N'Thu ngân'),
	(3, '2024-06-05', '07:00:00', '12:00:00', N'Phục vụ'),
	(4, '2024-06-05', '12:00:00', '18:00:00', N'Pha chế'),
	(5, '2024-06-05', '12:00:00', '18:00:00', N'Thu ngân'),
	(6, '2024-06-05', '12:00:00', '18:00:00', N'Phục vụ'),
	(7, '2024-06-05', '18:00:00', '23:00:00', N'Pha chế'),
	(8, '2024-06-05', '18:00:00', '23:00:00', N'Thu ngân'),
	(9, '2024-06-05', '18:00:00', '23:00:00', N'Phục vụ'),
	(1, '2024-06-06', '07:00:00', '12:00:00', N'Pha chế'),
	(2, '2024-06-06', '07:00:00', '12:00:00', N'Thu ngân'),
	(3, '2024-06-06', '07:00:00', '12:00:00', N'Phục vụ'),
	(4, '2024-06-06', '12:00:00', '18:00:00', N'Pha chế'),
	(5, '2024-06-06', '12:00:00', '18:00:00', N'Thu ngân'),
	(6, '2024-06-06', '12:00:00', '18:00:00', N'Phục vụ'),
	(7, '2024-06-06', '18:00:00', '23:00:00', N'Pha chế'),
	(8, '2024-06-06', '18:00:00', '23:00:00', N'Thu ngân'),
	(9, '2024-06-06', '18:00:00', '23:00:00', N'Phục vụ'),
	(1, '2024-06-07', '07:00:00', '12:00:00', N'Pha chế'),
	(2, '2024-06-07', '07:00:00', '12:00:00', N'Thu ngân'),
	(3, '2024-06-07', '07:00:00', '12:00:00', N'Phục vụ'),
	(4, '2024-06-07', '12:00:00', '18:00:00', N'Pha chế'),
	(5, '2024-06-07', '12:00:00', '18:00:00', N'Thu ngân'),
	(6, '2024-06-07', '12:00:00', '18:00:00', N'Phục vụ'),
	(7, '2024-06-07', '18:00:00', '23:00:00', N'Pha chế'),
	(8, '2024-06-07', '18:00:00', '23:00:00', N'Thu ngân'),
	(9, '2024-06-07', '18:00:00', '23:00:00', N'Phục vụ'),
	(1, '2024-06-08', '07:00:00', '12:00:00', N'Pha chế'),
	(2, '2024-06-08', '07:00:00', '12:00:00', N'Thu ngân'),
	(3, '2024-06-08', '07:00:00', '12:00:00', N'Phục vụ'),
	(4, '2024-06-08', '12:00:00', '18:00:00', N'Pha chế'),
	(5, '2024-06-08', '12:00:00', '18:00:00', N'Thu ngân'),
	(6, '2024-06-08', '12:00:00', '18:00:00', N'Phục vụ'),
	(7, '2024-06-08', '18:00:00', '23:00:00', N'Pha chế'),
	(8, '2024-06-08', '18:00:00', '23:00:00', N'Thu ngân'),
	(9, '2024-06-08', '18:00:00', '23:00:00', N'Phục vụ'),
	(1, '2024-06-09', '07:00:00', '12:00:00', N'Pha chế'),
	(2, '2024-06-09', '07:00:00', '12:00:00', N'Thu ngân'),
	(3, '2024-06-09', '07:00:00', '12:00:00', N'Phục vụ'),
	(4, '2024-06-09', '12:00:00', '18:00:00', N'Pha chế'),
	(5, '2024-06-09', '12:00:00', '18:00:00', N'Thu ngân'),
	(6, '2024-06-09', '12:00:00', '18:00:00', N'Phục vụ'),
	(7, '2024-06-09', '18:00:00', '23:00:00', N'Pha chế'),
	(8, '2024-06-09', '18:00:00', '23:00:00', N'Thu ngân'),
	(9, '2024-06-09', '18:00:00', '23:00:00', N'Phục vụ');


SELECT * FROM Category
SELECT * FROM Product
SELECT * FROM Employee
SELECT * FROM Customer
SELECT * FROM VipCustomer
SELECT * FROM Shifts
SELECT * FROM Tables
SELECT * FROM InvoiceDetail
SELECT * FROM Invoices
SELECT * FROM Orders
SELECT * FROM OrderDetail


SELECT
    c.id AS customerId,
    c.phoneNumber,
    CASE 
        WHEN vc.idCustomer IS NOT NULL THEN '10%'
        ELSE N'Không có' 
    END AS Voucher, 
    i.id AS invoiceId,
    i.invoiceTime,
    i.quantity AS invoiceQuantity,
    CASE 
        WHEN i.paymentStatus = 0 THEN N'Chưa thanh toán'
        WHEN i.paymentStatus = 1 THEN N'Đã thanh toán'
    END AS paymentStatus,
    i.calculateMoney AS invoiceCalculateMoney,
    e.fullname AS employeeName,
    o.idTable,
    od.quantity AS orderQuantity,
    p.price AS productPrice,
    p.name AS productName,
    p.unit AS productUnit,
    p.price * od.quantity AS totalOrder,
    SUM(p.price * od.quantity) OVER () AS totalInvoice
FROM 
    Invoices i
JOIN 
    Employee e ON i.idEmployee = e.id
JOIN 
    Orders o ON i.idOrder = o.id
JOIN 
    OrderDetail od ON o.id = od.idOrder
JOIN 
    Product p ON od.idProduct = p.id
JOIN 
    Customer c ON i.idCustomer = c.id
LEFT JOIN 
    VipCustomer vc ON c.id = vc.idCustomer
WHERE 
    i.InvoiceTime = (SELECT MAX(invoiceTime) FROM Invoices)
GROUP BY
    c.id,
    c.phoneNumber,
    vc.idCustomer,
    i.id,
    i.invoiceTime,
    i.quantity,
    i.paymentStatus,
    i.paymentMethod,
    i.calculateMoney,
    e.fullname,
    o.idTable,
    p.price,
    p.name,
    p.unit,
    od.quantity
/* doanh thu các ngày trong tháng */

WITH DatesInJune AS (
    SELECT DATEFROMPARTS(YEAR(GETDATE()), 6, 1) AS Date
    UNION ALL
    SELECT DATEADD(DAY, 1, Date)
    FROM DatesInJune
    WHERE MONTH(DATEADD(DAY, 1, Date)) = 6
)
TotalRevenuePreviousMonth AS (
    SELECT 
        COALESCE(SUM(calculateMoney), 0) AS TotalRevenuePrevious
    FROM Invoices
    WHERE MONTH(invoiceTime) = MONTH(DATEADD(MONTH, -1, GETDATE()))
)
SELECT 
    DatesInJune.Date AS Date,
    ISNULL(SUM(calculateMoney), 0) AS DailyRevenue,
    SUM(SUM(calculateMoney)) OVER () AS TotalRevenueJune,
    TRP.TotalRevenuePrevious AS TotalRevenuePreviousMonth
FROM DatesInJune
LEFT JOIN Invoices ON CONVERT(date, invoiceTime) = DatesInJune.Date
CROSS JOIN TotalRevenuePreviousMonth TRP
GROUP BY DatesInJune.Date, TRP.TotalRevenuePrevious;

SELECT
    YEAR(invoiceTime) AS Year,
    SUM(CASE WHEN MONTH(invoiceTime) = 1 THEN calculateMoney ELSE 0 END) AS Jan_Income,
    SUM(CASE WHEN MONTH(invoiceTime) = 2 THEN calculateMoney ELSE 0 END) AS Feb_Income,
    SUM(CASE WHEN MONTH(invoiceTime) = 3 THEN calculateMoney ELSE 0 END) AS Mar_Income,
    SUM(CASE WHEN MONTH(invoiceTime) = 4 THEN calculateMoney ELSE 0 END) AS Apr_Income,
    SUM(CASE WHEN MONTH(invoiceTime) = 5 THEN calculateMoney ELSE 0 END) AS May_Income,
    SUM(CASE WHEN MONTH(invoiceTime) = 6 THEN calculateMoney ELSE 0 END) AS Jun_Income,
    SUM(CASE WHEN MONTH(invoiceTime) = 7 THEN calculateMoney ELSE 0 END) AS Jul_Income,
    SUM(CASE WHEN MONTH(invoiceTime) = 8 THEN calculateMoney ELSE 0 END) AS Aug_Income,
    SUM(CASE WHEN MONTH(invoiceTime) = 9 THEN calculateMoney ELSE 0 END) AS Sep_Income,
    SUM(CASE WHEN MONTH(invoiceTime) = 10 THEN calculateMoney ELSE 0 END) AS Oct_Income,
    SUM(CASE WHEN MONTH(invoiceTime) = 11 THEN calculateMoney ELSE 0 END) AS Nov_Income,
    SUM(CASE WHEN MONTH(invoiceTime) = 12 THEN calculateMoney ELSE 0 END) AS Dec_Income
FROM
    Invoices
GROUP BY
    YEAR(invoiceTime)
ORDER BY
    YEAR(invoiceTime);
