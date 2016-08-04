package com.teamdev.javaclasses.service;

import com.teamdev.javaclasses.service.dto.TaskDto;
import com.teamdev.javaclasses.service.impl.TaskServiceImpl;
import com.teamedv.javaclasses.todolist.entity.Task;
import com.teamedv.javaclasses.todolist.entity.tiny.TaskId;
import com.teamedv.javaclasses.todolist.entity.tiny.UserId;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TaskServiceShould {

    private final TaskService taskService = TaskServiceImpl.getInstance();

    private final UserId userId = new UserId(1L);

    @Before
    public void before() {
        if (taskService.getByUser(userId).size() > 0) {
            fail("Repository is not empty.");
        }
    }

    @Test
    public void addTask() {
        Task task = new Task("Some task to be done.", userId);

        TaskId addedTaskId = taskService.add(task);

        taskService.delete(addedTaskId);

        assertEquals("Failed on task addition.", task.getId(), addedTaskId);
    }

    @Test
    public void returnTaskById() {
        Task task = new Task("Some task to be done.", userId);

        TaskId addedTaskId = taskService.add(task);

        TaskDto actual = taskService.getById(addedTaskId);

        taskService.delete(addedTaskId);

        assertEquals("Failed due incorrect description.", task.getDescription(), actual.getDescription());
        assertEquals("Failed due incorrect creator id.", task.getCreatorId(), actual.getCreatorId());
        assertEquals("Failed due incorrect done flag.", task.isDone(), actual.isDone());
    }

    @Test
    public void returnTaskByUser() {
        Task task = new Task("Some task to be done.", userId);

        TaskId addedTaskId = taskService.add(task);

        Collection<TaskDto> actual = taskService.getByUser(userId);

        taskService.delete(addedTaskId);

        assertTrue("Failed due incorrect user tasks.", actual.contains(
                new TaskDto(task.getDescription(), task.getCreatorId(), task.isDone())));
    }

    @Test
    public void editTask() {
        Task task = new Task("Some task to be done.", userId);

        TaskId addedTaskId = taskService.add(task);

        Task newTask = new Task("New description.", userId);

        taskService.editTask(addedTaskId, newTask);

        TaskDto actual = taskService.getById(addedTaskId);

        taskService.delete(addedTaskId);

        assertEquals("Failed due incorrect description.", newTask.getDescription(), actual.getDescription());
        assertEquals("Failed due incorrect creator id.", newTask.getCreatorId(), actual.getCreatorId());
        assertEquals("Failed due incorrect done flag.", newTask.isDone(), actual.isDone());
    }

    @Test
    public void deleteTask() {
        Task task = new Task("Some task to be done.", userId);

        TaskId addedTaskId = taskService.add(task);

        taskService.delete(addedTaskId);

        assertEquals("Failed on task removal.", 0, taskService.getByUser(userId).size());
    }
}
