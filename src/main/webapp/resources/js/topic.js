// Generated by CoffeeScript 2.0.0-beta8
new App.Router;
Backbone.history.start({
  pushState: true,
  root: '/'
});

App.messages = new App.Collections.Messages;
App.messages.fetch().then(function () {
  return new App.Views.TopicForm({ collection: App.messages });
});