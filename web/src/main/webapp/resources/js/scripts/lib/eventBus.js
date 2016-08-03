var EventBus = function(webWorkersKey) {

	var createSubscriber;

	if (typeof webWorkersKey === 'boolean' && webWorkersKey) {
		createSubscriber = function(callback) {
			return function(eventData) {
				new Worker(
					window.URL.createObjectURL(
						new Blob(["onmessage = function(ev) {" + "(" + callback.toString() + ")(ev.data)};"]))
				).postMessage(eventData);
			}
		}
	} else {
		createSubscriber = function(callback) {
			return function(eventData) {
				callback(eventData);
			}
		}
	}

	var _subscribers = new Array();

	var _post = function(eventData, eventType) {

		if (eventType != undefined && !(eventType in _subscribers)) {
			return;
		}

		if (eventType != undefined) {
			for (var i = 0; i < _subscribers[eventType].length; i++) {

				_subscribers[eventType][i](eventData);

			}
		} else {
			Object.keys(_subscribers).forEach(function(eventTypeKey) {

				for (var i = 0; i < _subscribers[eventTypeKey].length; i++) {
					_subscribers[eventTypeKey][i](eventData);
				}

			});
		}
	};

	var _subscribe = function(eventType, callback) {
		if (typeof _subscribers[eventType] === 'undefined') {
			_subscribers[eventType] = new Array();
		}
		_subscribers[eventType].push(createSubscriber(callback));
	};

	return {
		"post": _post,
		"subscribe": _subscribe
	}
};

if (typeof define !== 'function' && typeof require != 'undefined') {
	var define = require('amdefine')(module);
}
if (typeof define !== 'undefined') {
	define(function () {
		return EventBus;
	});
}