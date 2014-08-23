$(document).ready ->
  new App.Router()
  Backbone.history.start({ pushState: true, root: '/'})

  new window.App.Views.TopicForm()

