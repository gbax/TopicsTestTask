class window.App.Collections.Topics extends Backbone.Collection
  model: window.App.Models.Topic
  url: '/topic/all'

class window.App.Collections.Messages extends Backbone.Collection
  model: window.App.Models.Message
  @getCurrentTopicId: ->
    [open, content..., close] = document.URL.split("/")
    close
  url: '/topic/messages/' + @getCurrentTopicId()
