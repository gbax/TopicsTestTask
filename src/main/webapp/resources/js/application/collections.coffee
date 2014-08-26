###
Topics
###
class window.App.Collections.Topics extends Backbone.PageableCollection
  model: window.App.Models.Topic
  initialize: ->
    @on 'add', @fetchTopics, @

  url: '/topics'

  state: {
    pageSize: 5
  }

  queryParams: {
    totalPages: null
    totalRecords: null
    sortKey: "sort"
  }

  parseState: (resp, queryParams, state, options)->
    respJSON = JSON.parse(resp)
    return {totalRecords: respJSON.total_page}

  parseRecords: (resp, options)->
    respJSON = JSON.parse(resp)
    return respJSON.items

  fetchTopics: (e) ->
    @fetch {reset: true}

  renderOnDestroy: () ->
    if @length == 0
      if @hasPreviousPage()
        @getPreviousPage({fetch: true})
    else
      @fetch {reset: true}

###
Messages
###
class window.App.Collections.Messages extends Backbone.PageableCollection
  model: window.App.Models.Message
  initialize: ->
    @on 'destroy', @renderMessagesOnDestroy, @
    @on 'remove', @fetchMessages, @
    @on 'error', @errorHandle, @
    @on 'add', @fetchMessages, @

  @getCurrentTopicId: ->
    $('#topicId').val()

  url: '/topic/messages/' + @getCurrentTopicId()

  state: {
    pageSize: 5
  }

  queryParams: {
    totalPages: null
    totalRecords: null
    sortKey: "sort"
  }

  parseState: (resp, queryParams, state, options)->
    respJSON = JSON.parse(resp)
    return {totalRecords: respJSON.total_page}

  parseRecords: (resp, options)->
    respJSON = JSON.parse(resp)
    return respJSON.items

  fetchMessages:  () ->
    @fetch {reset: true}

  errorHandle: (e, m)->
    window.location=window.App.getContextPath()+"/index?error="+ m.responseJSON.error.error

  renderMessagesOnDestroy: () ->
    if @length == 0
      if @hasPreviousPage()
        @getPreviousPage({fetch: true})
