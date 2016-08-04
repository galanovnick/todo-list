if (typeof define !== 'function' && typeof require !== 'undefined') {
    events = require('events');
}

var UserService = function(_userEventBus) {

    var _create = function(user) {
        $.ajax({
            type: "post",
            data: {
                email: user.email,
                password: user.password,
                passwordConfirm: user.passwordConfirm
            },
            url: "/api/register",
            dataType: "json",
            jsonp: false,
            success: function() {
                _userEventBus.post(user.email, events.successfulRegistrationEvent);
            },
            error: function(response) {
                response = JSON.parse(response.responseText);
                _userEventBus.post(response.message, events.registrationFailedEvent);
            }
        });
    };

    var _authenticate = function(user) {
        $.ajax({
            type: "post",
            data: {
                email: user.email,
                password: user.password
            },
            url: "/api/login",
            dataType: "json",
            jsonp: false,
            success: function(response) {
                _userEventBus.post({
                    token: response.token,
                    email: user.email
                }, events.successfulAuthenticationEvent);
            },
            error: function(response) {
                response = JSON.parse(response.responseText);
                _userEventBus.post(response.message, events.authenticationFailedEvent);
            }
        });
    };

    var _checkToken = function(token) {
        $.ajax({
            type: "get",
            data: {
                token: token
            },
            url: "/api/login",
            dataType: "json",
            jsonp: false,
            success: function(response) {
                _userEventBus.post({
                    email: response.email,
                    token: token
                }, events.successfulAuthenticationEvent);
            }
        });
    }

    var _onUserAdded = function(user) {
        _create(user);
    };

    var _onUserAuthenticated = function(user) {
        return _authenticate(user);
    };

    return {
        "onUserAdded": _onUserAdded,
        "onUserAuthenticated": _onUserAuthenticated,
        "checkToken": _checkToken
    }
};

if (typeof define !== 'function' && typeof require != 'undefined') {
    var define = require('amdefine')(module);
}
if (typeof define !== 'undefined') {
    define(function () {
        return UserService;
    });
}