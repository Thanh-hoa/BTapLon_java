package DTO;

public class TablesDTO {
	private int id;
	private boolean status;
	private int seats;
	
	public TablesDTO(int id, boolean status, int seats) {
		super();
		this.id = id;
		this.status = status;
		this.seats = seats;
	}
	
	@Override
	public String toString() {
		return "TablesDTO [id=" + id + ", status=" + status + ", seats=" + seats + "]";
	}

	public TablesDTO(boolean status, int seats) {
		super();
		this.status = status;
		this.seats = seats;
	}
	
	public TablesDTO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}
	
	
}
