/*
 * Christopher Clark
 * CS-320 6-1 project
 * 08/07/2025
 */

package TaskService;

public class Task {
    private final String taskId;
    private String name;
    private String description;

    public Task(String taskId, String name, String description) {
        if (taskId == null || taskId.length() > 10)
            throw new IllegalArgumentException("Invalid task ID");
        if (name == null || name.length() > 20)
            throw new IllegalArgumentException("Invalid name");
        if (description == null || description.length() > 50)
            throw new IllegalArgumentException("Invalid description");

        this.taskId = taskId;
        this.name = name;
        this.description = description;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        if (name == null || name.length() > 20)
            throw new IllegalArgumentException("Invalid name");
        this.name = name;
    }

    public void setDescription(String description) {
        if (description == null || description.length() > 50)
            throw new IllegalArgumentException("Invalid description");
        this.description = description;
    }
}