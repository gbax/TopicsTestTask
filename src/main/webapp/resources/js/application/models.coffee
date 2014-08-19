class window.App.Models.Topic extends Backbone.Model
  validate: (attrs) ->
    if not attrs.description
      'Описание обязательно для заполнения'

  initialize: ->
    @.bind 'invalid', (model, msg) ->
      alert msg


class window.App.Models.Message extends Backbone.Model
  validate: (attrs) ->
    if not attrs.message
      'Сообщение обязательно для заполнения'

  initialize: ->
    @.bind 'invalid', (model, msg) ->
      alert msg
