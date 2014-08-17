###
Main view
###
class window.App.Views.MainForm extends Backbone.View
  initialize: ->
    allTopics = new window.App.Views.TopicsView(collection: window.App.topics).render()
    $("#topicsTable").append allTopics.el

###
Topics table
###
class window.App.Views.TopicsView extends Backbone.View
  initialize: ->
    console.log @collection.toJSON()

  tagName: 'tbody'

  render: ->
    @collection.each @addOne, this
    this

  addOne: (topic) ->
    viewTopic = new window.App.Views.Topic model: topic

    @$el.append viewTopic.render().el


###
Topic row
###
class window.App.Views.Topic extends Backbone.View
  tagName: 'tr'

  template: window.App.template 'topicTemplate'

  render: ->
    @$el.html @template @model.toJSON()
    this

####################################

###
Topic view
###
class window.App.Views.TopicForm extends Backbone.Model
  initialize: ->
    allMessages = new window.App.Views.MessagesView(collection: window.App.messages).render()
    $("#messagesTable").append allMessages.el

###
Message row
###
class window.App.Views.Message extends Backbone.View
  tagName: 'tr'

  template: window.App.template 'messageTemplate'

  render: ->
    @$el.html @template @model.toJSON()
    this

###
Message table
###
class window.App.Views.MessagesView extends Backbone.View
  initialize: ->
    console.log @collection.toJSON()

  tagName: 'tbody'

  render: ->
    @collection.each @addOne, this
    this

  addOne: (message) ->
    viewMessage = new window.App.Views.Message model: message

    @$el.append viewMessage.render().el