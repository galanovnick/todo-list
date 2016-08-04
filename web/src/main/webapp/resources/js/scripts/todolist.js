var TodoListApp = function(_rootId) {

    $.Mustache.load('resources/htmlTemplates/registration.html');
    $.Mustache.load('resources/htmlTemplates/login.html');

    var _eventBus = new EventBus();
    var _userService = new UserService(_eventBus);

    var _templatesLoadTimeout = 100;

    function _init() {
        var registrationComponent = new RegistrationComponent("reg-" + _rootId, _rootId, _eventBus);
        var loginComponent = new UserLoginComponent("login-" + _rootId, _rootId, _eventBus);

        _eventBus.subscribe(events.userAddedEvent, _userService.onUserAdded);
        _eventBus.subscribe(events.userAuthenticatedEvent, _userService.onUserAuthenticated);
        _eventBus.subscribe(events.successfulRegistrationEvent, registrationComponent.onRegistrationSuccess);
        _eventBus.subscribe(events.registrationFailedEvent, registrationComponent.onRegistrationFailed);
        _eventBus.subscribe(events.authenticationFailedEvent, loginComponent.onUserAuthenticationFailed);
        _eventBus.subscribe(events.successfulAuthenticationEvent, loginComponent.onUserSuccessfullyAuthenticated);
        _eventBus.subscribe(events.successfulAuthenticationEvent, registrationComponent.onUserSuccessfullyAuthenticated);
        _eventBus.subscribe(events.successfulAuthenticationEvent, _userAuthenticated);

        if (!$.Mustache.has("login-template") || !$.Mustache.has("registration-template")) {
            setTimeout(function() {
                registrationComponent.init();
                loginComponent.init();
            }, _templatesLoadTimeout);
        } else {
            registrationComponent.init();
            loginComponent.init();
        }
    }

    function _userAuthenticated() {

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
