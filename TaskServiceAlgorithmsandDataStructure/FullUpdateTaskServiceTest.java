/*
 * Christopher Clark
 * CS-320 6-1 Project (Enhanced for CS-499)
 */

package TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class TaskServiceTest {

    private TaskService service;

    @BeforeEach
    public void setUp() {
        service = new TaskService();
        service.addTask(new Task("1", "Task One", "First task description"));
    }

    @Test
    public void testAddDuplicateTask() {
        assertFalse(service.addTask(new Task("1", "Task One", "Duplicate")));
    }

    @Test
    public void testDeleteTask() {
        assertTrue(service.deleteTask("1"));
        assertNull(service.getTask("1"));
    }

    @Test
    public void testDeleteNonexistentTask() {
        assertFalse(service.deleteTask("999"));
    }

    @Test
    public void testUpdateName() {
        assertTrue(service.updateName("1", "Updated Name"));
        assertEquals("Updated Name", service.getTask("1").getName());
    }

    @Test
    public void testUpdateDescription() {
        assertTrue(service.updateDescription("1", "Updated Description"));
        assertEquals("Updated Description", service.getTask("1").getDescription());
    }

    @Test
    public void testUpdateNameInvalid() {
        assertThrows(IllegalArgumentException.class, () -> service.updateName("1", null));
    }

    @Test
    public void testUpdateDescriptionInvalid() {
        assertThrows(IllegalArgumentException.class, () -> service.updateDescription("1", ""));
    }

    @Test
    public void testGetNonexistentTask() {
        assertNull(service.getTask("999"));
    }

    @Test
    public void testNullTaskIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.getTask(null));
        assertThrows(IllegalArgumentException.class, () -> service.deleteTask(null));
    }

    @Test
    public void testSearchTaskByNameFound() {
        assertNotNull(service.searchTaskByName("Task One"));
    }

    @Test
    public void testSearchTaskByNameNotFound() {
        assertNull(service.searchTaskByName("Ghost"));
    }

    @Test
    public void testSearchTaskByNameInvalid() {
        assertThrows(IllegalArgumentException.class, () -> service.searchTaskByName("   "));
    }

    @Test
    public void testSorting() {
        service.addTask(new Task("2", "Apple", "desc"));
        List<Task> sorted = service.getTasksSortedByName();
        assertEquals("Apple", sorted.get(0).getName());
    }

    @Test
    public void testSortingEmptyService() {
        TaskService empty = new TaskService();
        assertTrue(empty.getTasksSortedByName().isEmpty());
    }
}