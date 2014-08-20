class window.App.Collections.Topics extends Backbone.Collection
  model: window.App.Models.Topic
  url: '/topics'

class window.App.Collections.Messages extends Backbone.PageableCollection
#  model: window.App.Models.Message
#  mode: "client"
  @getCurrentTopicId: ->
    $('#topicId').val()
  url: '/topic/messages/' + @getCurrentTopicId()


  state: {
    pageSize: 5,
    sortKey: "updated",
    order: 1
  }


  queryParams: {
    totalPages: null,
    totalRecords: null,
    sortKey: "sort",
  }

  parseState: (resp, queryParams, state, options)->
    respJSON = JSON.parse(resp)
    return {totalRecords: respJSON.total_page}

  parseRecords: (resp, options)->
    respJSON = JSON.parse(resp)
    return respJSON.items
