var AddTaskComponent = function(_componentRootId, _rootId, _eventBus) {
    var _init = function() {
        _eventBus.subscribe(events.taskCreatedEvent, _onTaskCreated);
        _eventBus.subscribe(events.taskCreationFailedEvent, _onTaskCreationFailed);

        _render();
    };

    var _render = function() {
        $('#' + _rootId).children(".add-task-container")
            .mustache('add-task-template', {id: _componentRootId});
        $("#" + _componentRootId).children(".add-task-btn").click(function() {
            _eventBus.post({
                token: $("#u-token").val(),
                description: $("#" + _componentRootId).children(".task-description").val()
            }, events.taskAddedEvent);
        });
    }

    var _onTaskCreated = function() {
        $("#" + _componentRootId).children(".error").hide();
        $("#" + _componentRootId).children(".error").html("");
        $("#" + _componentRootId).children(".task-description").val("");
    }

    var _onTaskCreationFailed = function(message) {
        $("#" + _componentRootId).children(".error").show();
        $("#" + _componentRootId).children(".error").html(message);
    }

    return {
        "init": _init
    }
};

if (typeof define !== 'function') {
    var define = require('amdefine')(module);
}

define(function() {
    return AddTaskComponent;
});
