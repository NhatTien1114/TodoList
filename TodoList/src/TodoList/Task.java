package TodoList;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
	private int id;
	private String title;
	private String description;
	private LocalDate dueDate;
	private boolean isCompleted;

	public Task() {
		id = 0;
		title = "xxx";
		description = "xxx";
		dueDate = LocalDate.now();
		isCompleted = false;
	}

	public Task(int id, String title, String description, LocalDate dueDate, boolean isCompleted) throws Exception{
		setId(id);
		setTitle(title);
		setDescription(description);
		setDueDate(dueDate);
		setCompleted(isCompleted);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) throws Exception {
		if (id < 0)
			throw new Exception("Lỗi! Id phải > 0");
		else
			this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws Exception {
		if (title.trim().equals(""))
			throw new Exception("Lỗi! Không được bỏ trống");
		else
			this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws Exception {
		if (description.trim().equals(""))
			throw new Exception("Lỗi! Không được bỏ trống");
		this.description = description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) throws Exception {
		if (dueDate.isBefore(LocalDate.now()))
			throw new Exception("Lỗi! Ngày dự kiến phải sau ngày hiện tại");
		else
			this.dueDate = dueDate;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	@Override
	public String toString() {
		DateTimeFormatter dfm = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return String.format("%5d %30s %30s %15s %10s", id, title, description, dfm.format(dueDate), (isCompleted) ? "đã hoàn thành" : "chưa hoàn thành");
	}
	
}
