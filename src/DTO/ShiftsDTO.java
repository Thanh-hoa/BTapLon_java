package DTO;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

public class ShiftsDTO {
	private int id;
	private EmployeeDTO idEmployee;
	private String job;
	private Date workDate;
	private Time startTime;
	private Time endTime;
	
	@Override
	public String toString() {
		return "ShiftsDTO [id=" + id + ", idEmployee=" + idEmployee.getId() + ", job=" + job + ", workDate=" + workDate
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
	
	public ShiftsDTO(int id, EmployeeDTO idEmployee, String job, Date workDate, Time startTime, Time endTime) {
		super();
		this.id = id;
		this.idEmployee = idEmployee;
		this.job = job;
		this.workDate = workDate;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public ShiftsDTO(EmployeeDTO idEmployee, String job, Date workDate, Time startTime, Time endTime) {
		super();
		this.idEmployee = idEmployee;
		this.job = job;
		this.workDate = workDate;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public ShiftsDTO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EmployeeDTO getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(EmployeeDTO idEmployee) {
		this.idEmployee = idEmployee;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	
	
}
