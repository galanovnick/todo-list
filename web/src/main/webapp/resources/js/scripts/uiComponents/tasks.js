var TaskListComponent = function(_componentRootId, _rootId, _eventBus) {
    var _init = function() {

        _eventBus.subscribe(events.taskListProvidedEvent, _render);
        _eventBus.subscribe(events.taskCreatedEvent, _updateTaskList);
        _eventBus.subscribe(events.taskRemovedEvent, _updateTaskList);
        _eventBus.subscribe(events.taskEditedEvent, _updateTaskList);

        _eventBus.post($("#u-token").val(), events.taskListRequiredEvent);
    };

    var _render = function(taskList) {
        taskList.forEach(function(item) {
           if (item.isDone === 'false') {
               item.isDone = false;
           }else {
               item.isDone = true;
           }
        });
        $('#' + _rootId).children(".task-list-container").mustache('task-list-template',
            {
                id: _componentRootId,
                tasks: taskList
            }, {"method": "html"});

        $(".delete-task-btn").click(function() {
           _eventBus.post({
                token: $("#u-token").val(),
                taskId: $(this).attr("id")
           }, events.removeTaskButtonClickedEvent);
        });

        $(".is-done-checkbox").click(function(event) {
            _eventBus.post({
                token: $("#u-token").val(),
                description: $(this).parent().parent().children(".description").html(),
                isDone: (this.checked) ? 'true' : 'false',
                taskId: $(this).attr("id")
            }, events.isDoneCheckboxChanged);
        });
    };

    var _updateTaskList = function() {
        _eventBus.post($("#u-token").val(), events.taskListRequiredEvent);
    };

    return {
        "init": _init
    }
};

if (typeof define !== 'function') {
    var define = require('amdefine')(module);
}

define(function() {
    return TaskListComponent;
});