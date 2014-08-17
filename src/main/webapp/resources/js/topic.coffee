new App.Router()
Backbone.history.start()

App.messages = new App.Collections.Messages()

App.messages.fetch().then ->
  new App.Views.TopicForm collection: App.messages

