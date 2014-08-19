new App.Router()
Backbone.history.start({ pushState: true, root: '/'})

App.messages = new App.Collections.Messages()

App.messages.fetch().then ->
  new App.Views.TopicForm collection: App.messages

