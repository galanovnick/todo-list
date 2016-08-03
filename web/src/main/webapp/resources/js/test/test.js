var maxResponseTimeMillis = 200;

QUnit.test('User service should register user', function(assert){
    var eventBus = new EventBus();
    var userService = new UserService(eventBus);

    var done = assert.async();
    var registeredUsers = {};
    var username = randomString();
    eventBus.subscribe(events.successfulRegistrationEvent, function(name) {
        registeredUsers[name] = true;
    });
    userService.onUserAdded({username: username, password: "123", passwordConfirm: "123"});

    setTimeout(function() {
        assert.expect(1);
        assert.equal(registeredUsers[username], true, "done");
        done();
    }, maxResponseTimeMillis);
});

QUnit.test('User service should not register duplicated user', function(assert){
    var eventBus = new EventBus();
    var userService = new UserService(eventBus);

    var done = assert.async();
    var delivered;
    var username = randomString();
    eventBus.subscribe(events.registrationFailedEvent, function(message) {
        delivered = message;
    });
    userService.onUserAdded({username: username, password: "123", passwordConfirm: "123"});
    userService.onUserAdded({username: username, password: "123", passwordConfirm: "123"});

    setTimeout(function() {
        assert.expect(1);
        assert.equal(delivered, "User with such name already exists.", "done");
        done();
    }, maxResponseTimeMillis);
});

QUnit.test('User service should not register duplicated user', function(assert){
    var eventBus = new EventBus();
    var userService = new UserService(eventBus);

    var done = assert.async();
    var delivered;
    eventBus.subscribe(events.registrationFailedEvent, function(message) {
        delivered = message;
    });
    userService.onUserAdded({username: "", password: "", passwordConfirm: ""});

    setTimeout(function() {
        assert.expect(1);
        assert.equal(delivered, "Fields cannot be empty.", "done");
        done();
    }, maxResponseTimeMillis);
});

QUnit.test('User service should authenticate user', function(assert){
    var eventBus = new EventBus();
    var userService = new UserService(eventBus);

    var done = assert.async();
    var username = randomString();

    var registrationSucceed = false;
    var authenticationSucceed = false;

    eventBus.subscribe(events.successfulRegistrationEvent, function() {
        registrationSucceed = true;
    });
    eventBus.subscribe(events.successfulAuthenticationEvent, function() {
        authenticationSucceed = true;
    });


    userService.onUserAdded({username: username, password: "123", passwordConfirm: "123"});
    userService.onUserAuthenticated({username: username, password: "123"});

    setTimeout(function() {
        assert.expect(1);
        assert.equal(registrationSucceed && authenticationSucceed, true, "done");
        done();
    }, maxResponseTimeMillis);
});

QUnit.test('User service should not authenticate unregistered user', function(assert){
    var eventBus = new EventBus();
    var userService = new UserService(eventBus);

    var done = assert.async();
    var username = randomString();

    var delivered;

    eventBus.subscribe(events.authenticationFailedEvent, function(message) {
        delivered = message;
    });

    userService.onUserAuthenticated({username: username, password: "123"});

    setTimeout(function() {
        assert.expect(1);
        assert.equal(delivered, "Invalid username or password.", "done");
        done();
    }, maxResponseTimeMillis)
});

QUnit.test('User service should not authenticate user with empty fields', function(assert){
    var eventBus = new EventBus();
    var userService = new UserService(eventBus);

    var done = assert.async();

    var delivered;

    eventBus.subscribe(events.authenticationFailedEvent, function(message) {
        delivered = message;
    });

    userService.onUserAuthenticated({username: "", password: ""});

    setTimeout(function() {
        assert.expect(1);
        assert.equal(delivered, "Fields cannot be empty.", "done");
        done();
    }, maxResponseTimeMillis)
});

function randomString()
{
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for( var i=0; i < 20; i++ )
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}