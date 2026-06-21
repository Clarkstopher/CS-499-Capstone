/*
 * Christopher Clark
 * CS-320 6-1 project
 * 08/07/2025
 */

package TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    private TaskService service;
    private Task task;

    //sets new service and task before each test
    @BeforeEach
    public void setUp() {
        service = new TaskService();
        task = new Task("1", "Task One", "First task description");
        service.addTask(task);
    }

    //Test for duplicate task
    @Test
    public void testAddDuplicateTask() {
        Task duplicate = new Task("1", "Task One", "First task description");
        assertFalse(service.addTask(duplicate));
    }

    //Tests deletion of task
    @Test
    public void testDeleteTask() {
        assertTrue(service.deleteTask("1"));
        assertNull(service.getTask("1"));
    }

    //Tests name update
    @Test
    public void testUpdateName() {
        assertTrue(service.updateName("1", "Updated Name"));
        assertEquals("Updated Name", service.getTask("1").getName());
    }

    //Tests description update
    @Test
    public void testUpdateDescription() {
        assertTrue(service.updateDescription("1", "Updated Description"));
        assertEquals("Updated Description", service.getTask("1").getDescription());
    }

    //Test for nonexistent task deletion returns fail.
    @Test
    public void testDeleteNonexistentTask() {
        assertFalse(service.deleteTask("nonexistent"));
    }
    
    // Test adding a new task with a unique ID
    @Test
    public void testAddNewTask() {
        // Create a new task with ID "2"
        Task newTask = new Task("2", "Task Two", "Second task description");
        // Verify that the task is successfully added
        assertTrue(service.addTask(newTask));
        // Confirm that the task can be retrieved and has the correct name
        assertEquals("Task Two", service.getTask("2").getName());
    }
    // Test updating the name of a task that doesn't exist
    @Test
    public void testUpdateNameNonexistentTask() {
        assertFalse(service.updateName("999", "Ghost Task"));
    }
    
    // Test updating the description of a task that doesn't exist
    @Test
    public void testUpdateDescriptionNonexistentTask() {
        assertFalse(service.updateDescription("999", "Ghost Description"));
    }
    
    // Test retrieving a task that doesn't exist
    @Test
    public void testGetNonexistentTask() {
        assertNull(service.getTask("999"));
    }
    
    // Test updating the name of a task using the maximum allowed length
    @Test
    public void testUpdateNameBoundary() {
        // Create a name with exactly 20 characters
        String maxName = "ABCDEFGHIJKLMNOPQRST";
        // Update the name of task with ID "1"
        assertTrue(service.updateName("1", maxName));
        // Verify that the name was updated correctly
        assertEquals(maxName, service.getTask("1").getName());
    }
    
    // Test updating the description of a task using the maximum allowed length
    @Test
    public void testUpdateDescriptionBoundary() {
        // Create a description with exactly 50 characters
        String maxDesc = "12345678901234567890123456789012345678901234567890";
        // Update the description of task with ID "1"
        assertTrue(service.updateDescription("1", maxDesc));
        // Verify that the description was updated correctly
        assertEquals(maxDesc, service.getTask("1").getDescription());
    }
}