/**
 * Main module
 *
 * "define" is for require.js
 * - 1st arg is the name of this module
 * - 2nd arg is the list of dependencies
 * - 3rd arg is a function executed when everything is loaded, its arguments are dependencies
 *
 * All paths are relatives to the directory of this main (always ../)
 */
define("main",

    // inspinia_loader loads all dependencies for inspinia template in 1 step, from require.config below
    // controllers do not need dependencies - they don't execute until document is ready
    // chart_controller is a singleton and it is not in the factory
	["inspinia_loader_level1", "controllers/chart_controller", "controllers/factory", "ajax/login", "ajax/submit_data"],

	function (inspinia_loader, chart_controller, factory, ajax_login, ajax_submit_data) {
    // chosen init
    $(document).ready(function () {
      var config = {
              '.chosen-select'           : {},
              '.chosen-select-deselect'  : {allow_single_deselect:true},
              '.chosen-select-no-single' : {disable_search_threshold:10},
              '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
              '.chosen-select-width'     : {width:"95%"}
      }
      for (var selector in config) {
          $(selector).chosen(config[selector]);
      }
    });

    // icheck init
    $(document).ready(function () {
      $('.i-checks').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
      });
    });

    // almadraba button controllers
    $(document).ready(function(){

      document.getElementById("submit-form-collapse").click();

      var chart_collapse = document.getElementById("chart-collapse");
      chart_collapse.click();

      // function to detect is element is collapsed
      var is_chart_collapsed = function() {
        var button_chart_collapse = $(chart_collapse).find("i");
        var classList = button_chart_collapse.attr("class").split(/\s+/);
        var arrayLength = classList.length;
        for (var i=0; i < arrayLength; i++) {
          if (classList[i] == "fa-chevron-up") {
            return false;
          }
          if (classList[i] == "fa-chevron-down") {
            return true;
          }
        }
      };

      // function that manages the view of the chart
      var chart_view_update = (function () {

          var chart = chart_controller.getChartInstance();
          var series = chart.series,
              xaxis = chart.xaxis;

          $('#high-charts-container').highcharts({

              title: {
                  text: 'Users notoriety versus time'
              },

              xAxis: {
                  categories: xaxis
              },

              yAxis: {
                  title: {
                      text: 'Page Rank'
                  },
                  plotLines: [{
                      value: 0,
                      width: 1,
                      color: '#808080'
                  }]
              },

              plotOptions: {
                  series: {
                      point: {
                          events: {
                              mouseOver: function () {
                                  var chart = this.series.chart;
                                  if (!chart.lbl) {
                                      chart.lbl = chart.renderer.label('')
                                          .attr({
                                              padding: 10,
                                              r: 10,
                                              fill: Highcharts.getOptions().colors[1]
                                          })
                                          .css({
                                              color: '#FFFFFF'
                                          })
                                          .add();
                                  }
                                  chart.lbl
                                      .show()
                                      .attr({
                                          text: 'x: ' + this.x + ', y: ' + this.y
                                      });
                              }
                          }
                      },
                      events: {
                          mouseOut: function () {
                              if (this.chart.lbl) {
                                  this.chart.lbl.hide();
                              }
                          }
                      }
                  }
              },

              tooltip: {
                  enabled: false
              },

              legend: {
              },

              series: series
          });
      });

      // load empty chart so that it gets size
      chart_view_update();

      // controller factory
      var selected_execution_label_controller = factory.createController("combo_controller");
      selected_execution_label_controller.init("select-execution");
      var selected_type_of_query_controller = factory.createController("combo_controller");
      selected_type_of_query_controller.init("select-type-of-query");

      // Login button controller
      ajax_login.setActionFunction(function(data) {
        selected_type_of_query_controller.initData(data.queryTypes);
        selected_execution_label_controller.initData(data.executionLabels);

        document.getElementById("log-in-collapse").click();
        document.getElementById("submit-form-collapse").click();
      });
      $('#log-in').click(ajax_login.onClick);

      // Submit data controller
      ajax_submit_data.setActionFunction(function(data) {
        var chart = chart_controller.getChartInstance();
        if (data.series) {
          if (is_chart_collapsed()) {
            chart_collapse.click();
          }
          chart.set_series(data.series);
          chart.set_xaxis(data.stepIds);
          chart_view_update();
          document.getElementById("chart").focus()
        } else {
          console.log("Error in response from server. No data.series")
        }
      });
      ajax_submit_data.setGetInputDataFunction(function() {
        // utility for getting input from form
        number_of_users = document.getElementById('number-of-users').value
        input_data = {
          "number" : number_of_users,
          "queryType":  selected_type_of_query_controller.getValue(),
          "executionLabel": selected_execution_label_controller.getValue()
        }
        return input_data;
      });
      $('#submit-data').click(ajax_submit_data.onClick);

    });

  }
);

require.config({
    paths: {
   		// all these depend on the template
    	jquery : '../jquery-2.1.1',
   		bootstrap : "../bootstrap",
   		jquerymetismenu : "../plugins/metisMenu/jquery.metisMenu",
   		jqueryslimscroll : "../plugins/slimscroll/jquery.slimscroll.min",
      inspinia: "../inspinia",
      pace: "../plugins/pace/pace.min",
      chosen: "../plugins/chosen/chosen.jquery",
      icheck: "../plugins/iCheck/icheck.min",
      highcharts: "../highcharts/highcharts",
      highchartsmore: "../highcharts/highcharts-more",
      highchartsexporting: "../highcharts/exporting",
      // here the custom controllers for this application
      custompageloader: "almadraba_loader"
    }
});
