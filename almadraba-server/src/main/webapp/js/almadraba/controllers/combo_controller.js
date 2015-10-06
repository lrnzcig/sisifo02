define("controllers/combo_controller",

        ["jquery"],

        function ($) {

          var ComboController = function() {};
          ComboController.prototype.init = function(pid) {
            this.combo_id = pid;
            this.el = document.getElementById(this.combo_id);
            // add controller for the combo
            $(this.el).chosen().change(this.triggerAction);
          }
          ComboController.prototype.initData = function(data) {
            var arrayLength = data.length;
            var lastValue;
            for (var i=0; i < arrayLength; i++) {
              opt = document.createElement("option");
              opt.value = data[i];
              opt.text = data[i];
              this.el.appendChild(opt);
              lastValue = data[i];
            }
            $(this.el).val(lastValue);
            $(this.el).trigger("chosen:updated");
          }
          ComboController.prototype.getValue = function() {
            return this.el.options[this.el.selectedIndex].text;
          }
          ComboController.prototype.triggerAction = function() {
            return;
          }

          // constructor for factory
          var ComboControllerBuilder = function() {
          };
          ComboControllerBuilder.prototype.build = function() {
            var controller = new ComboController();
            return controller;
          };

          return {
            getBuilder : function() {
              return new ComboControllerBuilder();
            }
          }

        }
)
