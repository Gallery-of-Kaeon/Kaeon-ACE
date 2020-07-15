var one = require("https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/United%20Bootstrap/ONESuite.js");
var philosophersStone = require("https://raw.githubusercontent.com/Gallery-of-Kaeon/Philosophers-Stone/master/Philosopher's%20Stone/API/JavaScript/PhilosophersStone.js");
var ui = require("https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/UI/UI.js");

function loadComponent(core, ace, entity) {
	
	core.modules.forEach(
		function(item) {

			if(item.onDeserialize != null)
				item.onDeserialize(core, ace, entity);
		}
	);
}

function loadEntity(core, ace) {
	
	var entity = { parent: null, children: [], components: [] };

	ace.children.forEach(
		function(item) {
			
			if(item.content.toLowerCase() == "entity") {

				let child = loadEntity(core, item);
				child.parent = entity;

				entity.children.push(child);
			}

			else if(item.content.toLowerCase() == "component")
				loadComponent(core, item, entity);
		}
	);

	return entity;
}

function run(ace, modules, element) {
	
	var core = {};

	core.ace = ace != null ? ace : new one.Element();
	core.modules = modules != null ? modules : [];
	core.element = element != null ? element : ui.root;

	core.element.addEventListener(
		"contextmenu",
		function(event) {
			event.preventDefault();
		}
	);

	core.element.addEventListener(
		"mousedown",
		function(event) {
			
			if(event.button == 1)
				event.preventDefault();
		}
	);

	ui.load("https://preview.babylonjs.com/babylon.js");
	ui.load("https://preview.babylonjs.com/loaders/babylonjs.loaders.min.js");
	ui.load("https://code.jquery.com/pep/0.4.3/pep.js");
	
	ui.extend(
		ui.setStyle(
			core.element,
			[
				["overflow", "hidden"],
				["margin", "0"],
				["padding", "0"]
			]
		),
		ui.setStyle(
			ui.specify(
				ui.create("canvas"),
				[
					["id", "renderCanvas"],
					["touch-action", "none"]
				]
			),
			[
				["width", "100%"],
				["height", "100%"],
				["touch-action", "none"]
			]
		)
	);
	
	core.canvas = document.getElementById("renderCanvas");
	core.engine = new BABYLON.Engine(core.canvas, true);
	
	core.entity = { parent: null, children: [], components: {} };
	
	core.scene = new BABYLON.Scene(core.engine);

	core.scene.preventDefaultOnPointerDown = false;
    	core.scene.clearColor = new BABYLON.Color3.Black;
	
	core.camera =
		new BABYLON.UniversalCamera(
			"UniversalCamera",
			new BABYLON.Vector3(0, 0, -10),
			core.scene
		);

	core.time = (new Date()).getTime();
	
	core.modules.forEach(
		function(item) {

			philosophersStone.connect(core, item, null, true);
	
			if(item.onDefault != null)
				item.onDefault(core);
		}
	);

	core.call = function(data) {
		
		let result = [];

		for(let i = 0; i < core.modules.length; i++) {

			if(core.modules[i].onCall == null)
				continue;
			
			let response = core.modules[i].onCall(core, data);

			if(response != null)
				result.push("" + response);
		}
		
		return result;
	}

	core.entity.children.push(loadEntity(core, core.ace));
		
	core.engine.runRenderLoop(
		function() {

			if(core.canvas.parentNode != core.element)
				core.engine.stopRenderLoop();

			else {

				let time = (new Date()).getTime();
				let delta = (time - core.time) / 1000;
		
				core.modules.forEach(
					function(item) {

						try {
				
							if(item.onUpdate != null)
								item.onUpdate(core, delta);

						}

						catch(error) {

						}
					}
				);
	
				core.time = time;
		
				core.scene.render();
			}
		}
	);
	
	window.addEventListener(
		"resize",
		function() {
			core.engine.resize();
		}
	);
}

module.exports = {
	run
};