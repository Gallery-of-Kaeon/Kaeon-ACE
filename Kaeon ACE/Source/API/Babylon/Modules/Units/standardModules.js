var moduleDependencies = {
	inputUtils: "https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/Utilities/UI/Visual/General/input.js",
	one: "https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-FUSION/master/Kaeon%20FUSION/Source/Engine/ONE.js",
	youtube: "https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/Utilities/UI/Audio/playYoutubeAudio.js"
};

var inputUtils = require(moduleDependencies.inputUtils);
var one = require(moduleDependencies.one);
var youtube = require(moduleDependencies.youtube);

var audio = {

	tracks: [],

	onDeserialize: function(core, ace, entity) {

		if(one.getChild(ace, "audio") != null) {

			let medium = "file";

			if(one.getChild(one.getChild(ace, "audio"), "medium") != null)
				medium = one.getChild(one.getChild(ace, "audio"), "medium").children[0].content.trim().toLowerCase();
	
			let source = one.getChild(one.getChild(ace, "audio"), "source").children[0].content;

			if(medium == "file") {

				let track = new Audio(source);
				track.loop = true;
	
				this.tracks.push(track);
	
				track.play();
			}

			if(medium == "youtube")
				youtube.playYoutubeAudio(source, null, true);
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
	tags: ["ID"],

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
			
			let language = one.getChild(this.scripts[i], "language").children[0].content.toLowerCase().trim();
			let code = one.getChild(this.scripts[i], "code").children[0].content

			try {

				if(language == "js" || language == "javascript")
					eval(code);
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

module.exports = [
	audio,
	ball,
	cursor,
	id,
	input,
	light,
	model,
	script,
	skybox
];