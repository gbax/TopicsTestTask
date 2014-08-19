class window.App.Collections.Topics extends Backbone.Collection
  model: window.App.Models.Topic
  url: '/topics'

class window.App.Collections.Messages extends Backbone.PageableCollection
  model: window.App.Models.Message
  @getCurrentTopicId: ->
    $('#topicId').val()
  url: '/topic/messages/' + @getCurrentTopicId()
