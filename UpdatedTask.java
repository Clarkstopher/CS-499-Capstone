/*
 * Christopher Clark
 * CS-320 6-1 project (Enhanced for CS-499 Milestone Two)
 * Enhanced: Software Design & Engineering
 */

package TaskService;

/**
 * Represents a task with an ID, name, and description.
 * Includes validation, sanitization, and defensive programming.
 */
public class Task {

    private final String taskId;
    private String name;
    private String description;

    /**
     * Creates a new Task object.
     */
    public Task(String taskId, String name, String description) {
        this.taskId = validateId(taskId);
        this.name = validateName(name);
        this.description = validateDescription(description);
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

    /**
     * Updates the task name after validation.
     */
    public void setName(String name) {
        this.name = validateName(name);
    }

    /**
     * Updates the task description after validation.
     */
    public void setDescription(String description) {
        this.description = validateDescription(description);
    }

    // -----------------------------
    // Validation & Sanitization
    // -----------------------------

    private String sanitize(String input) {
        return input.trim();
    }

    private boolean containsIllegalCharacters(String input) {
        return input.contains("<") || input.contains(">") ||
               input.contains("{") || input.contains("}") ||
               input.contains(";") || input.contains("\"");
    }

    private String validateId(String id) {
        if (id == null)
            throw new IllegalArgumentException("Task ID cannot be null.");
        id = sanitize(id);
        if (id.isEmpty() || id.length() > 10)
            throw new IllegalArgumentException("Task ID must be 1–10 characters.");
        if (containsIllegalCharacters(id))
            throw new IllegalArgumentException("Task ID contains illegal characters.");
        return id;
    }

    private String validateName(String name) {
        if (name == null)
            throw new IllegalArgumentException("Task name cannot be null.");
        name = sanitize(name);
        if (name.isEmpty() || name.length() > 20)
            throw new IllegalArgumentException(
                "Task name must be 1–20 characters. Received length: " + name.length()
            );
        if (containsIllegalCharacters(name))
            throw new IllegalArgumentException("Task name contains illegal characters.");
        return name;
    }

    private String validateDescription(String description) {
        if (description == null)
            throw new IllegalArgumentException("Task description cannot be null.");
        description = sanitize(description);
        if (description.isEmpty() || description.length() > 50)
            throw new IllegalArgumentException(
                "Task description must be 1–50 characters. Received length: " + description.length()
            );
        if (containsIllegalCharacters(description))
            throw new IllegalArgumentException("Task description contains illegal characters.");
        return description;
    }
}