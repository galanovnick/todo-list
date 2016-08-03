var events = {
	userAddedEvent : "USER_ADDED_EVENT",
	registrationFailedEvent : "REGISTRATION_FAILED_EVENT",
	successfulRegistrationEvent: "SUCCESSFUL_REGISTRATION_EVENT",
	userAuthenticatedEvent: "USER_AUTHENTICATED_EVENT",
	authenticationFailedEvent: "AUTHENTICATION_FAILED_EVENT",
	successfulAuthenticationEvent: "SUCCESSFUL_AUTHENTICATION_EVENT"
};

if (typeof define !== 'function' && typeof require != 'undefined') {
    var define = require('amdefine')(module);
}
if (typeof define !== 'undefined') {
	define(function () {
		return events;
	});
}