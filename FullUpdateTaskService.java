/*
 * Christopher Clark
 * CS-320 6-1 project (Enhanced for CS-499 Milestone Two & Three)
 * Enhanced: Software Design & Engineering + Algorithms & Data Structures
 */

package TaskService;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

/**
 * Service class for managing Task objects.
 * Includes defensive programming, null checks, and improved maintainability.
 */
public class TaskService {

    private final HashMap<String, Task> tasks = new HashMap<>();

    public boolean addTask(Task task) {
        if (task == null)
            throw new IllegalArgumentException("Task cannot be null.");

        String id = task.getTaskId();
        if (tasks.containsKey(id))
            return false;

        tasks.put(id, task);
        return true;
    }

    public boolean deleteTask(String taskId) {
        validateTaskId(taskId);
        return tasks.remove(taskId) != null;
    }

    public boolean updateName(String taskId, String name) {
        validateTaskId(taskId);
        Task task = tasks.get(taskId);
        if (task == null)
            return false;

        task.setName(name);
        return true;
    }

    public boolean updateDescription(String taskId, String description) {
        validateTaskId(taskId);
        Task task = tasks.get(taskId);
        if (task == null)
            return false;

        task.setDescription(description);
        return true;
    }

    public Task getTask(String taskId) {
        validateTaskId(taskId);
        return tasks.get(taskId);
    }

    /**
     * Linear search algorithm.
     * Acceptable for small datasets; for large-scale systems, indexing or database-backed search would be preferred.
     */
    public Task searchTaskByName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or blank.");

        for (Task task : tasks.values()) {
            if (task.getName().equalsIgnoreCase(name)) {
                return task;
            }
        }
        return null;
    }

    /**
     * Sorting algorithm using Comparator.
     * Efficient for small collections; larger systems may require indexed structures.
     */
    public List<Task> getTasksSortedByName() {
        List<Task> sorted = new ArrayList<>(tasks.values());
        sorted.sort(Comparator.comparing(Task::getName));
        return sorted;
    }

    private void validateTaskId(String taskId) {
        if (taskId == null || taskId.isBlank())
            throw new IllegalArgumentException("Task ID cannot be null or blank.");
    }
}