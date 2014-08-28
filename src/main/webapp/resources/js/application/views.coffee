###
Main view
###
class window.App.Views.MainForm extends Backbone.View
  initialize: ->
    window.App.topics = new window.App.Collections.Topics()

    grid = new Backgrid.Grid({
      events:
        'click th a': (e) ->
          $('th', $(@.el))
          .not($(e.target).parent())
          .removeClass('descending')
          .removeClass('ascending')

      columns: columns,
      collection: window.App.topics
    })

    paginator = new Backgrid.Extension.Paginator({
      collection: window.App.topics
    });

    $("#grid").append(grid.render().$el);
    $("#paginator").append(paginator.render().$el);

    window.App.topics.fetch(reset: true)

    new window.App.Views.AddTopic(collection: window.App.topics)


  columns = [
    {
      name: "id",
      label: "Номер"
      editable: false,
      cell: Backgrid.IntegerCell.extend({
        orderSeparator: ''
      })
    },
    {
      name: "description",
      label: "Форум"
      editable: false
      cell: Backgrid.UriCell.extend({
        render: () ->
          this.$el.empty();
          rawValue = "/topic/" + @model.get('id')
          formattedValue = @model.get('description')
          this.$el.append($("<a>", {
            tabIndex: -1,
            href: rawValue,
            title: formattedValue,
            target: "_self"
          }).text(formattedValue))
          @delegateEvents()
          @
      })
    },
    {
      name: "updateDate",
      cell: "string",
      label: "Дата создания",
      editable: false
      sortable: true
    },
    {
      cell: "id"
      label: "Действие"
      editable: false
      sortable: false
      cell: Backgrid.Cell.extend({

        events:
          'click': 'removeModel'

        removeModel: (e) ->
          e.preventDefault();
          collection = @model.collection
          @model.destroy
            dataType: "text",
            success: (model, response) ->
              collection.renderOnDestroy()
            error: (model, response) ->
              resp = JSON.parse(response.responseText)
              window.location=window.App.getContextPath()+"/index?error="+ resp.error.error

        render: () ->
          this.$el.empty();
          if @model.get('canDelete')
            formattedValue = "Удалить"
            this.$el.append($("<button>", {
              tabIndex: -1,
              type: "button"
              class: "delete"
              title: formattedValue,
              target: @target
            }).text(formattedValue))
            @delegateEvents()
          @
      })
    }
  ]

###
Add topic form
###
class window.App.Views.AddTopic extends Backbone.View
  initialize: ->
    @descriptionEl = @$('#description')

  el: '#topicForm'

  events:
    submit: 'addTopic'

  addTopic: (e) ->
    e.preventDefault()
    @collection.create({description: @descriptionEl.val()}, {wait: true})
    @clearForm()

  clearForm: ->
    @descriptionEl.val('')

###-----------------------------------------------------------------------------------------------------------------###

###
Topic view
###
class window.App.Views.TopicForm extends Backbone.Model
  initialize: ->
    window.App.messages = new window.App.Collections.Messages()

    grid = new Backgrid.Grid({
      events:
        'click th a': (e) ->
          $('th', $(@.el))
          .not($(e.target).parent())
          .removeClass('descending')
          .removeClass('ascending')

      columns: @columns
      collection: window.App.messages
    })

    paginator = new Backgrid.Extension.Paginator({
      collection: window.App.messages
    });

    $("#grid").append(grid.render().$el);
    $("#paginator").append(paginator.render().$el);

    window.App.messages.fetch(reset: true)

    new window.App.Views.AddMessage(collection: window.App.messages)


  columns: [
    {
      name: "id",
      label: "Номер"
      editable: false
      cell: Backgrid.IntegerCell.extend({
        orderSeparator: ''
      })
    },
    {
      name: "message",
      cell: "string",
      label: "Сообщение",
      editable: false
      sortable: true
    },
    {
      name: "date",
      cell: "string",
      label: "Дата создания",
      editable: false
      sortable: true
    },
    {
      cell: "id"
      label: "Действие"
      editable: false
      sortable: false
      cell: Backgrid.Cell.extend({

        events:
          'click': 'removeMessage'

        removeMessage: (e) ->
          e.preventDefault();
          @model.destroy
            dataType: "text",
            error: (model, response) ->
              resp = JSON.parse(response.responseText)
              window.location=window.App.getContextPath()+"/index?error="+ resp.error.error

        render: () ->
          this.$el.empty();
          if @model.get('canDelete')
            formattedValue = "Удалить"
            this.$el.append($("<button>", {
              tabIndex: -1,
              type: "button"
              class: "delete"
              title: formattedValue,
              target: @target
            }).text(formattedValue))
            @delegateEvents()
          @
      })
    }
  ]

###
Add message form
###
class window.App.Views.AddMessage extends Backbone.View
  initialize: ->
    @messageEl = @$('#message')

  el: '#messageForm'

  events:
    submit: 'addMessage'

  addMessage: (e) ->
    e.preventDefault()
    @collection.create {message: @messageEl.val()}, { wait: true }
    @clearForm()

  clearForm: ->
    @messageEl.val('')
