var UserDto = function(email, password, passwordConfirm) {
	return {
		"email": email,
		"password": password,
		"passwordConfirm": passwordConfirm
	}
};

if (typeof define !== 'function') {
    var define = require('amdefine')(module);
}

define(function() {
	return UserDto;
});