$(document).ready ->
  new App.Router()
  Backbone.history.start()

  new window.App.Views.MainForm()
