// Generated by CoffeeScript 1.7.1
(function() {
  $(document).ready(function() {
    var columns, grid, paginator;
    new App.Router();
    Backbone.history.start({
      pushState: true,
      root: '/'
    });
    columns = [
      {
        name: "id",
        editable: false,
        cell: Backgrid.IntegerCell.extend({
          orderSeparator: ''
        })
      }, {
        name: "message",
        cell: "string"
      }
    ];
    App.messages = new App.Collections.Messages();
    grid = new Backgrid.Grid({
      columns: columns,
      collection: App.messages
    });
    paginator = new Backgrid.Extension.Paginator({
      collection: App.messages
    });
    $("#grid").append(grid.render().$el);
    $("#paginator").append(paginator.render().$el);
    return App.messages.fetch({
      reset: true
    });

    /*.then ->
      new App.Views.TopicForm collection: App.messages
     */
  });

}).call(this);
