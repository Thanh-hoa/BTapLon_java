package DTO;

public class ProductDTO {
	private int id;
	private String name;
	private String thumbnail;
	private String description;
	private float price;
	private String unit;
	private CategoryDTO idCategory;
	
	public ProductDTO(int id, String name, String thumbnail, String description, float price, String unit,
			CategoryDTO idCategory) {
		super();
		this.id = id;
		this.name = name;
		this.thumbnail = thumbnail;
		this.description = description;
		this.price = price;
		this.unit = unit;
		this.idCategory = idCategory;
	}

	public ProductDTO(int id, String name, float price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", name=" + name + ", thumbnail=" + thumbnail + ", description=" + description
				+ ", price=" + price + ", unit=" + unit + ", idCategory=" + idCategory.getId() + "]";
	}
	
	public ProductDTO(int id, String name, float price, String unit, CategoryDTO idCategory) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.idCategory = idCategory;
	}

	public ProductDTO(String name, float price, String unit, CategoryDTO idCategory) {
		super();
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.idCategory = idCategory;
	}

	public ProductDTO(String name, String thumbnail, String description, float price, String unit,
			CategoryDTO idCategory) {
		super();
		this.name = name;
		this.thumbnail = thumbnail;
		this.description = description;
		this.price = price;
		this.unit = unit;
		this.idCategory = idCategory;
	}

	public ProductDTO(int id) {
		super();
		this.id = id;
	}
	public ProductDTO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public CategoryDTO getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(CategoryDTO idCategory) {
		this.idCategory = idCategory;
	}
	
	
}