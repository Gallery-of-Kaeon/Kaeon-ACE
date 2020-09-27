/*

	on call: take core and data string, return string
	on default: take core
	on deserialize: take core, ace, and entity, modify entity
	on entity: take core, ace, and delta
	on serialize: take core and entity, return a ONE list form element or null
	on update: take core and delta

 */

var inputUtils = require("https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/UI/input.js");
var one = require("https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-FUSION/master/Kaeon%20FUSION/APIs/ONE/JavaScript/ONE.js");
var onePlus = require("https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/United%20Bootstrap/ONEPlus.js");
var philosophersStone = require("https://raw.githubusercontent.com/Gallery-of-Kaeon/Philosophers-Stone/master/Philosopher's%20Stone/API/JavaScript/PhilosophersStone.js");

var audio = {

	tracks: [],

	onDeserialize: function(core, ace, entity) {

		if(one.getChild(ace, "audio") != null) {
	
			let source = one.getChild(one.getChild(ace, "audio"), "source").children[0].content;

			let track = new Audio(source);
			track.loop = true;

			this.tracks.push(track);

			track.play();
		}
	}
};

var ball = {

	onDeserialize: function(core, ace, entity) {

		if(one.getChild(ace, "ball") != null) {
	
			let ball = BABYLON.MeshBuilder.CreateSphere(
				"sphere",
				{ diameter: 2 },
				core.scene
			);

			entity.components.push(ball);
		}
	}
};

var camera = {

	controlOn: false,

	onDeserialize: function(core, ace, entity) {

		if(one.getChild(ace, "camera control") != null)
			this.controlOn = true;
	},

	onUpdate: function(core, delta) {

		if(!this.controlOn)
			return;

		if(core.input.pc.keyboard.includes(37)) { // LEFT
			core.camera.rotation.x -= .015 * Math.sin(-core.camera.rotation.z);
			core.camera.rotation.y -= .015 * Math.cos(-core.camera.rotation.z);
		}

		if(core.input.pc.keyboard.includes(39)) { // RIGHT
			core.camera.rotation.x += .015 * Math.sin(-core.camera.rotation.z);
			core.camera.rotation.y += .015 * Math.cos(-core.camera.rotation.z);
		}

		if(core.input.pc.keyboard.includes(38)) { // UP
			core.camera.rotation.x -= .015 * Math.cos(-core.camera.rotation.z);
			core.camera.rotation.y += .015 * Math.sin(-core.camera.rotation.z);
		}

		if(core.input.pc.keyboard.includes(40)) { // DOWN
			core.camera.rotation.x += .015 * Math.cos(-core.camera.rotation.z);
			core.camera.rotation.y -= .015 * Math.sin(-core.camera.rotation.z);
		}

		if(core.input.pc.keyboard.includes(83)) { // S
			core.camera.position.x += .25 * Math.sin(-core.camera.rotation.y) * Math.cos(-core.camera.rotation.x);
			core.camera.position.y -= .25 * Math.sin(-core.camera.rotation.x);
			core.camera.position.z -= .25 * Math.cos(-core.camera.rotation.y) * Math.cos(-core.camera.rotation.x);
		}

		if(core.input.pc.keyboard.includes(87)) { // W
			core.camera.position.x -= .25 * Math.sin(-core.camera.rotation.y) * Math.cos(-core.camera.rotation.x);
			core.camera.position.y += .25 * Math.sin(-core.camera.rotation.x);
			core.camera.position.z += .25 * Math.cos(-core.camera.rotation.y) * Math.cos(-core.camera.rotation.x);
		}

		if(core.input.pc.keyboard.includes(68)) { // D
			core.camera.position.x += .25 * Math.cos(-core.camera.rotation.y) * Math.cos(-core.camera.rotation.z);
			core.camera.position.y -= .25 * Math.sin(-core.camera.rotation.z);
			core.camera.position.z += .25 * Math.sin(-core.camera.rotation.y) * Math.cos(-core.camera.rotation.z);
		}

		if(core.input.pc.keyboard.includes(65)) { // A
			core.camera.position.x -= .25 * Math.cos(-core.camera.rotation.y) * Math.cos(-core.camera.rotation.z);
			core.camera.position.y += .25 * Math.sin(-core.camera.rotation.z);
			core.camera.position.z -= .25 * Math.sin(-core.camera.rotation.y) * Math.cos(-core.camera.rotation.z);
		}

		if(core.input.pc.keyboard.includes(69)) { // E
			core.camera.position.x += .25 * Math.sin(-core.camera.rotation.y) * Math.sin(-core.camera.rotation.x);
			core.camera.position.y += .25 * Math.cos(-core.camera.rotation.x + core.camera.rotation.z);
			core.camera.position.z -= .25 * Math.cos(-core.camera.rotation.y) * Math.sin(-core.camera.rotation.x);
		}

		if(core.input.pc.keyboard.includes(81)) { // Q
			core.camera.position.x -= .25 * Math.sin(-core.camera.rotation.y) * Math.sin(-core.camera.rotation.x)// * Math.sin(-core.camera.rotation.z);
			core.camera.position.y -= .25 * Math.cos(-core.camera.rotation.x);
			core.camera.position.z += .25 * Math.cos(-core.camera.rotation.y) * Math.sin(-core.camera.rotation.x)// * Math.sin(-core.camera.rotation.z);
		}

		if(core.input.pc.keyboard.includes(16)) // SHIFT
			core.camera.rotation.z += .015;

		if(core.input.pc.keyboard.includes(32)) // SPACE
			core.camera.rotation.z -= .015;
	}
};

var cursor = {

	onDeserialize: function(core, ace, entity) {

		if(one.getChild(ace, "cursor") != null) {
	
			if(one.getChild(one.getChild(ace, "cursor"), "none") != null)
				core.element.style.cursor = "none";
		}
	}
};

var input = {

	onDefault: function(core) {
		
		core.input = { };

		inputUtils.addInput(core.element, core.input);
	}
};

var id = {

	reference: { },

	onDeserialize: function(core, ace, entity) {

		if(one.getChild(ace, "id") != null)
			this.reference[one.getChild(ace, "id").children[0].content] = entity;

	}
}

var light = {

	onDefault: function(core) {
		new BABYLON.HemisphericLight("light1", new BABYLON.Vector3(1, 1, 0), core.scene);
		new BABYLON.PointLight("light2", new BABYLON.Vector3(0, 1, -1), core.scene);
	}
};

var model = {

	onDeserialize: function(core, ace, entity) {

		if(one.getChild(ace, "model") != null) {
			
			let source = one.getChild(one.getChild(ace, "model"), "source").children[0].content;
	
			let model = BABYLON.SceneLoader.ImportMesh("", source.substring(0, source.lastIndexOf("/") + 1), source.substring(source.lastIndexOf("/") + 1), core.scene, function (meshes) {          
				entity.components = entity.components.concat(meshes);
			});
		}
	}
};

var script = {

	scripts: [], // { language (optional, same as universal preprocessor), code }

	onDeserialize: function(core, ace, entity) {

		if(one.getChild(ace, "script") != null)
			this.scripts = this.scripts.concat(one.getChild(ace, "script", null, true));
	},

	onUpdate: function(core, delta) {
		
		for(let i = 0; i < this.scripts.length; i++) {

			try {
				eval(one.getChild(this.scripts[i], "code").children[0].content);
			}

			catch(error) {

			}
		}
	}
};

var skybox = {

	skyboxes: [],

	onDeserialize: function(core, ace, entity) {

		if(one.getChild(ace, "skybox") != null) {
		
			var skybox = BABYLON.MeshBuilder.CreateBox("skyBox", { size: 10000.0 }, core.scene);
	
			var skyboxMaterial = new BABYLON.StandardMaterial("skyBox", core.scene);
			
			skyboxMaterial.backFaceCulling = false;

			skyboxMaterial.reflectionTexture = new BABYLON.CubeTexture(
				one.getChild(
					one.getChild(ace, "skybox"),
					"source"
				).children[0].content, core.scene);

			skyboxMaterial.reflectionTexture.coordinatesMode = BABYLON.Texture.SKYBOX_MODE;
			skyboxMaterial.disableLighting = true;
	
			skybox.material = skyboxMaterial;

			this.skyboxes.push(skybox);

			entity.components.push(skybox);
		}	
	},

	onUpdate: function(core, delta) {
		
		for(let i = 0; i < this.skyboxes.length; i++)
			this.skyboxes[i].position = core.camera.position;
	}
};

var test = {

	onDefault: function(core) {
		// STUB
	},

	onUpdate: function(core, delta) {
		// console.log(delta);
	},

	onCall: function(core, data) {
		
		let command = onePlus.readONEPlus("" + data);

		if(one.getChild(command, "move") != null) {
			
			let move = one.getChild(command, "move").children[0];

			let entity = id.reference[move.content];

			for(let i = 0; i < entity.components.length; i++) {
				
				if(entity.components[i].position != null) {
					entity.components[i].position.x += Number(move.children[0].content);
					entity.components[i].position.y += Number(move.children[1].content);
					entity.components[i].position.z += Number(move.children[2].content);
				}
			}
		}
	}
};

module.exports = function(core) {

	let modules = [
		audio,
		ball,
		camera,
		cursor,
		id,
		input,
		light,
		model,
		script,
		skybox,
		test
	];

	for(let i = 0; i < modules.length; i++) {

		modules[i].tags = ["Kaeon ACE"];

		philosophersStone.connect(core, modules[i], [], true);
	}
};