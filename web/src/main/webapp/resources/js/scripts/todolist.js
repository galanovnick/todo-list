var TodoListApp = function(_rootId) {
    $.Mustache.load('resources/htmlTemplates/registration.html');
    $.Mustache.load('resources/htmlTemplates/login.html');
    $.Mustache.load('resources/htmlTemplates/main.html');
    $.Mustache.load('resources/htmlTemplates/task-list.html');
    $.Mustache.load('resources/htmlTemplates/add-task.html');

    var _eventBus = new EventBus();
    var _userService = new UserService(_eventBus);
    var _taskService = new TaskService(_eventBus);

    var _templatesLoadTimeout = 100;

    function _init() {
        var registrationComponent = new RegistrationComponent("reg", _rootId, _eventBus);
        var loginComponent = new UserLoginComponent("login", _rootId, _eventBus);

        _eventBus.subscribe(events.userAddedEvent, _userService.onUserAdded);
        _eventBus.subscribe(events.pageRefreshedEvent, _userService.checkToken);
        _eventBus.subscribe(events.userAuthenticatedEvent, _userService.onUserAuthenticated);
        _eventBus.subscribe(events.successfulRegistrationEvent, registrationComponent.onRegistrationSuccess);
        _eventBus.subscribe(events.registrationFailedEvent, registrationComponent.onRegistrationFailed);
        _eventBus.subscribe(events.authenticationFailedEvent, loginComponent.onUserAuthenticationFailed);
        _eventBus.subscribe(events.successfulAuthenticationEvent, loginComponent.onUserSuccessfullyAuthenticated);
        _eventBus.subscribe(events.successfulAuthenticationEvent, registrationComponent.onUserSuccessfullyAuthenticated);
        _eventBus.subscribe(events.successfulAuthenticationEvent, _userAuthenticated);
        _eventBus.subscribe(events.taskListRequiredEvent, _taskService.onTaskListRequested);
        _eventBus.subscribe(events.taskAddedEvent, _taskService.onTaskAdded);
        _eventBus.subscribe(events.removeTaskButtonClickedEvent, _taskService.onTaskDeleted);
        _eventBus.subscribe(events.isDoneCheckboxChanged, _taskService.onTaskEdited);

        if (!$.Mustache.has("login-template") || !$.Mustache.has("registration-template")) {
            setTimeout(function() {
                registrationComponent.init();
                loginComponent.init();
                if (typeof localStorage.getItem("token") !== 'undefined') {
                    _eventBus.post(localStorage.getItem("token"), events.pageRefreshedEvent);
                }
            }, _templatesLoadTimeout);
        } else {
            registrationComponent.init();
            loginComponent.init();
            if (typeof localStorage.getItem("token") !== 'undefined') {
                _eventBus.post(localStorage.getItem("token"), events.pageRefreshedEvent);
            }
        }
    }

    function _userAuthenticated() {
        $('#' + _rootId).mustache('main-page-template', {email: $("#u-email").val()});

        $("#logout").click(function(event) {
            event.preventDefault();
            $('#' + _rootId).html("");
            localStorage.setItem("token", undefined);
            var registrationComponent = new RegistrationComponent("reg", _rootId, _eventBus);
            var loginComponent = new UserLoginComponent("login", _rootId, _eventBus);
            registrationComponent.init();
            loginComponent.init();
        });

        var addTaskComponent = new AddTaskComponent("add-task", _rootId, _eventBus);
        addTaskComponent.init();

        var taskListComponent = new TaskListComponent("task-list", _rootId, _eventBus);
        taskListComponent.init();
    }

    return {
        "init": _init
    }
};

if (typeof define !== 'function') {
    var define = require('amdefine')(module);
}

define(function() {
    return TodoListApp;
});
