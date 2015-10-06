/**
 * Controller factory
 *
 */
define("controllers/factory",

	// dependencies are the objects in the factory
	["controllers/combo_controller"],

	function(combo_controller) {

		// Factoria de controladores
		var ControllerFactory = function(){};
		ControllerFactory.prototype.registerType = function(controllerType, builder){
			this[controllerType] = builder;
		};
		ControllerFactory.prototype.createController = function(controllerType){
			if(this[controllerType] === undefined){
				return null;
			}
			var builder = this[controllerType];
			return builder.build();
		};

		var factory = new ControllerFactory();

		// register
		factory.registerType("combo_controller", combo_controller.getBuilder());
		return {
			createController : function(controllerType) {
				return factory.createController(controllerType);
			}
		}
	}
);
