require.config({
	baseUrl: "resources/js/scripts",
	paths: {
		jquery: "lib/jquery.min"
	},
	urlArgs: "bust=" + (new Date()).getTime()
});

define(function(require) {

	require("lib/eventBus");
	require("todolist");

	var app = new TodoListApp("main-container").init();
});