package TodoList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoList {
	protected List<Task> list;

	public TodoList() {
		list = new ArrayList<Task>();
	}
	
	public boolean addTask(Task t) {
		for (Task task : list) {
			if (task.getId() == t.getId())
				return false;
		}
		return list.add(t);
	}
	
	public boolean removeTask(int id) {
		return list.removeIf(task -> task.getId()== id);
	}
	
	public boolean updateTask(int ma, String tieuDe, String moTa, LocalDate ngayHoanThanh, boolean isComplete) throws Exception{
		for (Task task : list) {
			if (task.getId() == ma) {
				task.setTitle(tieuDe);
				task.setDescription(moTa);
				task.setDueDate(ngayHoanThanh);
				task.setCompleted(isComplete);
				return true;
			}
		}
		return false;
	}
	
	public boolean markTaskAsCompleted(int id) {
	    for (Task task : list) {
	        if (task.getId() == id) {
	            task.setCompleted(true); 
	            return true;
	        }
	    }
	    return false; 
	}
	
	public Task findTaskById(int id) {
        for (Task task : list) {
            if (task.getId() == id) {
                return task; 
            }
        }
        return null;
    }
}
