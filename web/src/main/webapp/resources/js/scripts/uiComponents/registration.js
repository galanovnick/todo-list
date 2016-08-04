var RegistrationComponent = function(_componentRootId, _rootId, _eventBus) {

	var _init = function() {
		$('#' + _rootId).mustache('registration-template', {id: _componentRootId});
		$("#" + _componentRootId + " .register").click(function() {
			var email = $("#" + _componentRootId + " .email").val();
			var password = $("#" + _componentRootId + " .password").val();
			var passwordConfirm = $("#" + _componentRootId + " .password_r").val();

			_register(new UserDto(email, password, passwordConfirm));
		});
	};

	var _register = function(user) {
		console.log("Trying to post 'userAddedEvent' (user = " + user.email + ")...");
		_eventBus.post(user, events.userAddedEvent);

	};

	var _registrationFailed = function(message) {
		$("#" + _componentRootId + " .error").html(message);
	};

	var _resetFields = function() {
		$("#" + _componentRootId + " .error").html("");

		$("#" + _componentRootId + " .email").val("");
		$("#" + _componentRootId + " .password").val("");
		$("#" + _componentRootId + " .password_r").val("");
	};

	var _onUserSuccessfullyAuthenticated = function() {
		$("#" + _componentRootId).remove();
	};

	var _onRegistrationFailed = function(message) {
		_registrationFailed(message);
		$("#" + _componentRootId + " .success").html("");
	};

	var _onRegistrationSuccess = function() {
		_resetFields();
		$("#" + _componentRootId + " .success").html("User has been registered");
	};

	return {
		"init": _init,
		"register": _register,
		"onRegistrationSuccess": _onRegistrationSuccess,
		"onRegistrationFailed": _onRegistrationFailed,
		"onUserSuccessfullyAuthenticated": _onUserSuccessfullyAuthenticated
	}
};

if (typeof define !== 'function') {
    var define = require('amdefine')(module);
}

define(function() {
	return RegistrationComponent;
});