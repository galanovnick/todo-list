package com.teamdev.javaclasses.service.impl;

import com.teamdev.javaclasses.service.InvalidDescriptionException;
import com.teamdev.javaclasses.service.TaskService;
import com.teamdev.javaclasses.service.dto.TaskDto;
import com.teamedv.javaclasses.todolist.entity.Task;
import com.teamedv.javaclasses.todolist.entity.UserTask;
import com.teamedv.javaclasses.todolist.entity.tiny.TaskId;
import com.teamedv.javaclasses.todolist.entity.tiny.UserId;
import com.teamedv.javaclasses.todolist.repository.impl.TaskRepository;
import com.teamedv.javaclasses.todolist.repository.impl.UserTasksRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

public class TaskServiceImpl implements TaskService {

    private static TaskService instance;

    private final TaskRepository taskRepository = TaskRepository.getInstance();
    private final UserTasksRepository userTasksRepository = UserTasksRepository.getInstance();

    @Override
    public TaskId add(Task task) throws InvalidDescriptionException {
        checkNotNull(task, "Task cannot be null");

        if (task.getDescription().equals("")) {
            throw new InvalidDescriptionException("Description cannot be empty.");
        }

        Pattern patter = Pattern.compile("^( +)");
        Matcher matcher = patter.matcher(task.getDescription());

        if (matcher.find()) {
            throw new InvalidDescriptionException("Description cannot start with whitespace.");
        }

        TaskId taskId = taskRepository.add(task);
        userTasksRepository.add(new UserTask(task.getCreatorId(), task.getId()));
        return taskId;
    }

    @Override
    public TaskDto getById(TaskId taskId) {
        Optional<Task> one = taskRepository.findOne(taskId);
        if (one.isPresent()) {
            return new TaskDto(one.get().getDescription(), one.get().getCreatorId(), one.get().isDone());
        }
        throw new IllegalStateException("Attempt to get task by invalid id.");
    }

    @Override
    public Collection<TaskDto> getByUser(UserId userId) {
        return userTasksRepository.getUserTasksByUser(userId).stream()
                .map(userTask -> {
                    Optional<Task> one = taskRepository.findOne(userTask.getTaskId());
                    if (one.isPresent()) {
                        return new TaskDto(
                                one.get().getId(),
                                one.get().getDescription(),
                                one.get().getCreatorId(),
                                one.get().isDone());
                    } else {
                        throw new IllegalStateException("Attempt to get task by invalid id.");
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public void editTask(TaskId taskId, Task task) throws InvalidDescriptionException {
        if (task.getDescription().equals("")) {
            throw new InvalidDescriptionException("Description cannot be empty.");
        }

        Pattern patter = Pattern.compile("^( +)");
        Matcher matcher = patter.matcher(task.getDescription());

        if (matcher.find()) {
            throw new InvalidDescriptionException("Description cannot start with whitespace.");
        }
        Optional<Task> one = taskRepository.findOne(taskId);
        if (one.isPresent()) {
            one.get().setDescription(task.getDescription());
            one.get().setDone(task.isDone());
        } else {
            throw new IllegalStateException("Attempt to get task by invalid id.");
        }
    }

    @Override
    public void delete(TaskId taskId) {
        taskRepository.delete(taskId);
        userTasksRepository.deleteByTaskId(taskId);
    }

    private TaskServiceImpl(){}

    public static TaskService getInstance() {
        if (instance == null) {
            return instance = new TaskServiceImpl();
        }
        return instance;
    }
}
