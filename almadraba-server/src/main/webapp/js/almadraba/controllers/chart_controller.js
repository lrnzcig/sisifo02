define("controllers/chart_controller",

        ["jquery"],

        function ($) {

          // chart manager as a singleton
          // NOTE: it manages only the data of the graph
          // updating of graph must be done by main class
          var ChartSingleton = (function () {

            var instance;

            function init() {

              return {
                // public methods and variables
                series: null,
                xaxis: null,
                set_series: function(series) {
                  this.series = [];
                  series.sort(function (a,b) {
                    return a.rowNumber - b.rowNumber;
                  });
                  var arrayLength = series.length;
                  for (var i=0; i < arrayLength; i++) {
                    obj = {
                      "name" : series[i].userId,
                      "data":  series[i].series
                    }
                    this.series.push(obj);
                  }
                },
                set_xaxis: function(xaxis) {
                  this.xaxis = [];
                  var arrayLength = xaxis.length;
                  for (var i=0; i < arrayLength; i++) {
                    this.xaxis.push(xaxis[i]);
                  }
                }
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
            getChartInstance : function() {
              return ChartSingleton.getInstance();
            }
          }

        }
)
