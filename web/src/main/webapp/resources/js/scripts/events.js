var events = {
	userAddedEvent : "USER_ADDED_EVENT",
	registrationFailedEvent : "REGISTRATION_FAILED_EVENT",
	successfulRegistrationEvent: "SUCCESSFUL_REGISTRATION_EVENT",
	userAuthenticatedEvent: "USER_AUTHENTICATED_EVENT",
	authenticationFailedEvent: "AUTHENTICATION_FAILED_EVENT",
	successfulAuthenticationEvent: "SUCCESSFUL_AUTHENTICATION_EVENT",

	taskCreatedEvent: "TASK_CREATED_EVENT",
	taskCreationFailedEvent: "TASK_CREATION_FAILED_EVENT",
	taskEditedEvent: "TASK_EDITED_EVENT",
	tasEditionFailedEvent: "TASK_EDITION_FAILED_EVENT",
	taskRemovedEvent: "TASK_REMOVED_EVENT",
	taskRemovalFailedEvent: "TASK_REMOVAL_FAILED_EVENT",
	taskListProvidedEvent: "TASK_LIST_PROVIDED_EVENT"
};

if (typeof define !== 'function' && typeof require != 'undefined') {
    var define = require('amdefine')(module);
}
if (typeof define !== 'undefined') {
	define(function () {
		return events;
	});
}