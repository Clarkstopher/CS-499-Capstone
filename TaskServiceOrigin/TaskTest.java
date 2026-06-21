/*
 * Christopher Clark
 * CS-320 6-1 Project
 * 08/07/2025
 */

package TaskService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TaskTest {

	// Validates that a valid task is created w/ correct values
    @Test
    public void testValidTask() {
        Task task = new Task("1234567890", "My Task", "This is a test task.");
        assertEquals("1234567890", task.getTaskId());
        assertEquals("My Task", task.getName());
        assertEquals("This is a test task.", task.getDescription());
    }

    //Validates null task ID throws exception
    @Test
    public void testInvalidTaskId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(null, "Name", "Desc");
        });
    }

    //Validates null name throws exception.
    @Test
    public void testInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Task("1", null, "Desc");
        });
    }

    //Validates setting invalid name and description throws exception.
    @Test
    public void testSettersValidation() {
        Task task = new Task("1", "Name", "Desc");
        //Null name should fail
        assertThrows(IllegalArgumentException.class, () -> {
            task.setName(null);
        });
        //Null description should fail
        assertThrows(IllegalArgumentException.class, () -> {
            task.setDescription(null);
        });
    }
    // Test that creating a task with a taskId longer than 10 characters throws an exception
    @Test
    public void testTaskIdTooLong() {
        String longId = "12345678901"; // 11 characters
        assertThrows(IllegalArgumentException.class, () -> {
            new Task(longId, "Name", "Desc");
        });
    }
    // Test that creating a task with a name longer than 20 characters throws an exception
    @Test
    public void testNameTooLong() {
        String longName = "ThisNameIsWayTooLongToBeValid";
        assertThrows(IllegalArgumentException.class, () -> {
            new Task("1", longName, "Desc");
        });
    }
    // Test that creating a task with a description longer than 50 characters throws an exception
    @Test
    public void testDescriptionTooLong() {
        String longDesc = "This description is definitely longer than fifty characters and should fail.";
        assertThrows(IllegalArgumentException.class, () -> {
            new Task("1", "Name", longDesc);
        });
    }
    // Test that valid setter methods correctly update the name and description
    @Test
    public void testValidSetters() {
        Task task = new Task("1", "Initial", "Initial description");
        
        // Update name and description with valid values
        task.setName("Updated Name");
        task.setDescription("Updated description");
        // Verify that the updates were applied
        assertEquals("Updated Name", task.getName());
        assertEquals("Updated description", task.getDescription());
    }
    
    // Test that boundary values (max allowed lengths) are accepted without error
    @Test
    public void testBoundaryValues() {
        String validId = "1234567890"; // 10 chars
        String validName = "ABCDEFGHIJKLMNOPQRST"; // 20 chars
        String validDesc = "12345678901234567890123456789012345678901234567890"; // 50 chars
        // Create a task using boundary values
        Task task = new Task(validId, validName, validDesc);
        // Create a task using boundary values
        assertEquals(validId, task.getTaskId());
        assertEquals(validName, task.getName());
        assertEquals(validDesc, task.getDescription());
    }

    
}