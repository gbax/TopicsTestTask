class window.App.Models.Topic extends Backbone.Model

class window.App.Models.Message extends Backbone.Model
  validate: (attrs) ->
    if not attrs.message
      'Сообщение обязательно для заполнения'
