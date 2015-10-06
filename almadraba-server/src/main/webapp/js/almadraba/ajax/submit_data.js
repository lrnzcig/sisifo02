define("ajax/submit_data",

        ["jquery", "ajax/utils"],

        function ($, ajax_utils) {

          var Singleton = (function () {
            var instance;

            function init() {
              return {
                actionFunction: null,
                getInputDataFunction: null
              };
            };

            return {
              getInstance: function () {
                if ( !instance ) {
                  instance = init();
                }
                return instance;
              }
            };
          })();

          return {
            setActionFunction : function(f) {
              s = Singleton.getInstance();
              s.actionFunction = f;
            },
            setGetInputDataFunction : function(f) {
              s = Singleton.getInstance();
              s.getInputDataFunction = f;
            },
            onClick : function() {
              s = Singleton.getInstance();
              $.ajax({
                url: 'http://localhost:8080/almadraba/webapi/chart',
                type: 'PUT',
                data: JSON.stringify(s.getInputDataFunction()),
                contentType: 'application/json',
                headers: ajax_utils.getHeaders(),
                complete: ajax_utils.onComplete,
                success: function(data, textStatus, jqXHR) {
                  s.actionFunction(data);
                }
              });
            }
          }

        }
)
