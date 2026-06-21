/*
 * Christopher Clark
 * CS-320 6-1 project
 * 08/07/2025
 */

package TaskService;

import java.util.HashMap;

public class TaskService {
    // HashMap to store tasks with taskId as key for quick access
    private final HashMap<String, Task> tasks = new HashMap<>();

    // Adds a new task if the taskId is unique, returns false if duplicate
    public boolean addTask(Task task) {
        if (tasks.containsKey(task.getTaskId()))
            return false;
        tasks.put(task.getTaskId(), task);
        return true;
    }

    // Deletes a task by taskId, returns false if taskId not found
    public boolean deleteTask(String taskId) {
        return tasks.remove(taskId) != null;
    }

    // Updates the name of a task, returns false if taskId not found
    public boolean updateName(String taskId, String name) {
        Task task = tasks.get(taskId);
        if (task != null) {
            task.setName(name);
            return true;
        }
        return false;
    }

    public boolean updateDescription(String taskId, String description) {
        Task task = tasks.get(taskId);
        if (task != null) {
            task.setDescription(description);
            return true;
        }
        return false;
    }

    public Task getTask(String taskId) {
        return tasks.get(taskId);
    }
}