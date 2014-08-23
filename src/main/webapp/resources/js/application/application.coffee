window.App =
  Models: {}, Views: {}, Collections: {}, Controllers: {}, router: {}

window.vent = _.extend {}, Backbone.Events

window.App.template = (id) ->
  if $('#' + id).length > 0
    Handlebars.compile($('#' + id).html())
  else
    null

window.App.getContextPath = () ->
  if $("#contextPath").value then $("#contextPath").value else ""

