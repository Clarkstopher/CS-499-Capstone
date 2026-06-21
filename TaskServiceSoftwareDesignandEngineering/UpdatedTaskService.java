/*
 * Christopher Clark
 * CS-320 6-1 project (Enhanced for CS-499 Milestone Two)
 * Enhanced: Software Design & Engineering
 */

package TaskService;

import java.util.HashMap;

/**
 * Service class for managing Task objects.
 * Includes defensive programming, null checks, and improved maintainability.
 */
public class TaskService {

    private final HashMap<String, Task> tasks = new HashMap<>();

    /**
     * Adds a new task if the ID is unique.
     */
    public boolean addTask(Task task) {
        if (task == null)
            throw new IllegalArgumentException("Task cannot be null.");

        String id = task.getTaskId();
        if (tasks.containsKey(id))
            return false;

        tasks.put(id, task);
        return true;
    }

    /**
     * Deletes a task by ID.
    */
    public boolean deleteTask(String taskId) {
        validateTaskId(taskId);
        return tasks.remove(taskId) != null;
    }

    /**
     * Updates the name of a task.
    */
    public boolean updateName(String taskId, String name) {
        validateTaskId(taskId);
        Task task = tasks.get(taskId);
        if (task == null)
            return false;

        task.setName(name);
        return true;
    }

    /**
     * Updates the description of a task.
    */
    public boolean updateDescription(String taskId, String description) {
        validateTaskId(taskId);
        Task task = tasks.get(taskId);
        if (task == null)
            return false;

        task.setDescription(description);
        return true;
    }

    /**
     * Retrieves a task by ID.
    */
    public Task getTask(String taskId) {
        validateTaskId(taskId);
        return tasks.get(taskId);
    }

    // -----------------------------
    // Helper Validation
    // -----------------------------

    private void validateTaskId(String taskId) {
        if (taskId == null || taskId.isBlank())
            throw new IllegalArgumentException("Task ID cannot be null or blank.");
    }
}