new App.Router()
Backbone.history.start()

App.topics = new App.Collections.Topics()

App.topics.fetch().then ->
  new App.Views.MainForm collection: App.topics

