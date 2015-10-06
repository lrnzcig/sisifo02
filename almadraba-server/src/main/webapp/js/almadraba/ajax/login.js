define("ajax/login",

        ["jquery", "ajax/utils"],

        function ($, ajax_utils) {

          var Singleton = (function () {
            var instance;

            function init() {
              return {
                actionFunction: null
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
            onClick : function() {
              $.ajax({
                url: 'http://localhost:8080/almadraba/webapi/login',
                type: 'GET',
                headers: ajax_utils.getHeaders(),
                complete: ajax_utils.onComplete,
                success: function(data, textStatus, jqXHR) {
                  s = Singleton.getInstance();
                  s.actionFunction(data);
                }
              });
            }
          }

        }
)
