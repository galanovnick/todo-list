require.config({
	baseUrl: "resources/js/scripts",
	paths: {
		jquery: "lib/jquery.min"
	},
	urlArgs: "bust=" + (new Date()).getTime()
});

define(function(require) {

	require("events");
	require("dto/userDto");
	require("lib/eventBus");
	require("todolist");
	require("service/remoteUserService");
	require("service/remoteTaskService");
	require("uiComponents/registration");
	require("uiComponents/login");

	var app = new TodoListApp("main-container").init();
});