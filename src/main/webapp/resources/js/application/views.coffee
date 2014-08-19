###
Main view
###
class window.App.Views.MainForm extends Backbone.View
  initialize: ->
    topicForm = new window.App.Views.AddTopic(collection: window.App.topics).render()
    allTopics = new window.App.Views.TopicsView(collection: window.App.topics).render()
    $("#topicsTable").append allTopics.el

###
Topics table
###
class window.App.Views.TopicsView extends Backbone.View
  initialize: ->
    initialize: ->
    @collection.on 'add', @addOne, @

  tagName: 'tbody'

  render: ->
    @collection.each @addOne, this
    this

  addOne: (topic) ->
    viewTopic = new window.App.Views.Topic model: topic

    @$el.append viewTopic.render().el


###
Add topic form
###
class window.App.Views.AddTopic extends Backbone.View
  initialize: ->
    @descriptionEl = @$('#description')

  el: '#topicForm'

  events:
  {
    submit: 'addTopic'
  }

  addTopic: (e) ->
    e.preventDefault()
    @collection.create({description: @descriptionEl.val()}, {wait: true})
    @clearForm()

  clearForm: ->
    @descriptionEl.val('')

###
Topic row
###
class window.App.Views.Topic extends Backbone.View
  initialize: ->
    @model.on 'destroy', @unrender, @

  tagName: 'tr'

  events:
    'click a.delete': 'removeModel'

  removeModel: ->
    @model.destroy()

  unrender: ->
    @remove()

  template: window.App.template 'topicTemplate'

  render: ->
    @$el.html @template @model.toJSON()
    this

###-----------------------------------------------------------------------------------------------------------------###
###-----------------------------------------------------------------------------------------------------------------###

###
Topic view
###
class window.App.Views.TopicForm extends Backbone.Model
  initialize: ->
    addMessage = new window.App.Views.AddMessage(collection: window.App.messages)
    allMessages = new window.App.Views.MessagesView(collection: window.App.messages).render()
    $("#messagesTable").append allMessages.el

###
Message table
###
class window.App.Views.MessagesView extends Backbone.View
  initialize: ->
    @collection.on 'add', @addOne, @

  tagName: 'tbody'

  render: ->
    @collection.each @addOne, this
    this

  addOne: (message) ->
    viewMessage = new window.App.Views.Message model: message

    @$el.append viewMessage.render().el

###
Add message form
###
class window.App.Views.AddMessage extends Backbone.View
  initialize: ->
    @messageEl =  @$('#message')

  el: '#messageForm'

  events:
    submit: 'addMessage'

  addMessage: (e) ->
    e.preventDefault()
    @collection.create {message: @messageEl.val()},{ wait: true }
    @clearForm()

  clearForm: ->
    @messageEl.val('')

  errSh:  (view, attr, error, selector) =>
    alert("fook")


###
Message row
###
class window.App.Views.Message extends Backbone.View
  initialize: ->
    @model.on 'destroy', @unrender, @

  tagName: 'tr'

  events:
    'click a.delete': 'removeModel'

  removeModel: ->
    @model.destroy()

  unrender: ->
    @remove()

  template: window.App.template 'messageTemplate'

  render: ->
    @$el.html @template @model.toJSON()
    this


###
Paginator
###
class window.App.Views.PaginationView extends Backbone.View.extend
  template: _.template($("#pagination-view").html())

  link: ""

  page_count: null

  page_active: null

  page_show: 5

  attributes:
    'class': 'pagination'


  initialize: (params) ->
    this.link = params.link;
    this.page_count = params.page_count
    if (this.page_count <= this.page_show)
      @.page_show = this.page_count
    this.page_active = params.page_active


  render: (eventName) ->
    console.log "rere"
