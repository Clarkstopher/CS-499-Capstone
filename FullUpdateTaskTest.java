/*
 * Christopher Clark
 * CS-320 6-1 Project (Enhanced for CS-499)
 */

package TaskService;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void testValidTask() {
        Task task = new Task("1234567890", "My Task", "This is a test task.");
        assertEquals("1234567890", task.getTaskId());
        assertEquals("My Task", task.getName());
        assertEquals("This is a test task.", task.getDescription());
    }

    @Test
    public void testInvalidTaskId() {
        assertThrows(IllegalArgumentException.class, () -> new Task(null, "Name", "Desc"));
    }

    @Test
    public void testInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> new Task("1", null, "Desc"));
    }

    @Test
    public void testInvalidDescription() {
        assertThrows(IllegalArgumentException.class, () -> new Task("1", "Name", null));
    }

    @Test
    public void testTaskIdTooLong() {
        assertThrows(IllegalArgumentException.class, () -> new Task("12345678901", "Name", "Desc"));
    }

    @Test
    public void testNameTooLong() {
        assertThrows(IllegalArgumentException.class, () -> new Task("1", "X".repeat(25), "Desc"));
    }

    @Test
    public void testDescriptionTooLong() {
        assertThrows(IllegalArgumentException.class, () -> new Task("1", "Name", "Y".repeat(60)));
    }

    @Test
    public void testWhitespaceTrimmed() {
        Task task = new Task("1", "   Name   ", "   Description   ");
        assertEquals("Name", task.getName());
        assertEquals("Description", task.getDescription());
    }

    @Test
    public void testIllegalCharacters() {
        assertThrows(IllegalArgumentException.class, () -> new Task("1", "Bad<Name", "Desc"));
    }

    @Test
    public void testSettersValidation() {
        Task task = new Task("1", "Name", "Desc");
        assertThrows(IllegalArgumentException.class, () -> task.setName(null));
        assertThrows(IllegalArgumentException.class, () -> task.setDescription(null));
    }

    @Test
    public void testBoundaryValues() {
        Task task = new Task("1234567890", "ABCDEFGHIJKLMNOPQRST",
                "12345678901234567890123456789012345678901234567890");
        assertEquals("1234567890", task.getTaskId());
    }

    @Test
    public void testExtremelyLongValues() {
        assertThrows(IllegalArgumentException.class, () -> new Task("1", "N".repeat(200), "Desc"));
        assertThrows(IllegalArgumentException.class, () -> new Task("1", "Name", "D".repeat(500)));
    }

    @Test
    public void testTaskIdImmutability() {
        Task task = new Task("ID123", "Name", "Desc");
        assertEquals("ID123", task.getTaskId());
        task.setName("New");
        assertEquals("ID123", task.getTaskId());
    }
}