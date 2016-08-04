if (typeof define !== 'function' && typeof require !== 'undefined') {
    events = require('events');
}

var TaskService = function(_userEventBus) {

    var _add = function(task) {
        $.ajax({
            type: "post",
            data: {
                token: task.token,
                description: task.description
            },
            url: "/api/task",
            dataType: "json",
            jsonp: false,
            success: function(response) {
                _userEventBus.post(response.taskId, events.taskCreatedEvent);
            },
            error: function(response) {
                response = JSON.parse(response.responseText);
                _userEventBus.post(response.message, events.taskCreationFailedEvent);
            }
        });
    };

    var _edit = function(task) {
        $.ajax({
            type: "put",
            data: {
                token: task.token,
                description: task.description,
                isDone: task.isDone
            },
            url: "/api/task",
            dataType: "json",
            jsonp: false,
            success: function(response) {
                _userEventBus.post(response.taskId, events.taskEditedEvent);
            },
            error: function(response) {
                response = JSON.parse(response.responseText);
                _userEventBus.post(response.message, events.tasEditionFailedEvent);
            }
        });
    };

    var _delete = function(task) {
        $.ajax({
            type: "delete",
            url: "/api/task" +
                "?token=" + task.token +
                "&taskId=" + task.taskId,
            dataType: "json",
            jsonp: false,
            success: function(response) {
                _userEventBus.post(response.taskId, events.taskRemovedEvent);
            },
            error: function(response) {
                response = JSON.parse(response.responseText);
                _userEventBus.post(response.message, events.taskRemovalFailedEvent);
            }
        });
    };

    var _getAll = function(token) {
        $.ajax({
            type: "get",
            url: "/api/task" +
            "?token=" + token,
            dataType: "json",
            jsonp: false,
            success: function(response) {
                _userEventBus.post(response.tasks, events.taskListProvidedEvent);
            }
        });
    };

    return {
        "onTaskAdded": _add,
        "onTaskEdited": _edit,
        "onTaskDeleted": _delete,
        "onTaskListRequested": _getAll
    }
};

if (typeof define !== 'function' && typeof require != 'undefined') {
    var define = require('amdefine')(module);
}
if (typeof define !== 'undefined') {
    define(function () {
        return TaskService;
    });
}