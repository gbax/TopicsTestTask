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
    console.log resp.length
    return {totalRecords: resp.length}

  parseRecords: (resp, options)->
    return resp
