var moduleDependencies = {
	inputUtils: "https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/Utilities/UI/Visual/General/input.js",
	one: "https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-FUSION/master/Kaeon%20FUSION/Source/Engine/ONE.js",
	onePlus: "https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-FUSION/master/Kaeon%20FUSION/Source/Engine/ONEPlus.js",
	oneSuite: "https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-FUSION/master/Kaeon%20FUSION/Source/Engine/ONESuite.js",
	philosophersStone: "https://raw.githubusercontent.com/Gallery-of-Kaeon/Philosophers-Stone/master/Philosopher's%20Stone/API/PhilosophersStone.js",
	youtube: "https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/Utilities/UI/Audio/playYoutubeAudio.js"
};

var inputUtils = require(moduleDependencies.inputUtils);
var one = require(moduleDependencies.one);
var onePlus = require(moduleDependencies.onePlus);
var oneSuite = require(moduleDependencies.oneSuite);
var philosophersStone = require(moduleDependencies.philosophersStone);
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

var move = {

	id: null,

	onDefault: function(core) {
		
		move.id = philosophersStone.retrieve(
			philosophersStone.traverse(core),
			function(item) {
				return philosophersStone.isTagged(item, "ID");
			}
		)[0];


	},

	onUpdate: function(core, delta) {
		// console.log(delta);
	},

	onCall: function(core, data) {
		
		let command = onePlus.readONEPlus("" + data);

		if(one.getChild(command, "move") != null) {
			
			let item = one.getChild(command, "move").children[0];

			let entity = move.id.reference[item.content];

			for(let i = 0; i < entity.components.length; i++) {
				
				if(entity.components[i].position != null) {
					entity.components[i].position.x += Number(item.children[0].content);
					entity.components[i].position.y += Number(item.children[1].content);
					entity.components[i].position.z += Number(item.children[2].content);
				}
			}
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
			
			let start = one.getChild(this.scripts[i], "start");
			let update = one.getChild(this.scripts[i], "update");

			if(!this.scripts[i].started)
				this.executeSubscript(start, core, delta);

			this.executeSubscript(update, core, delta);

			this.scripts[i].started = true;
		}
	},

	executeSubscript: function(subscript, core, delta) {

		if(subscript == null)
			return;

		let language = one.getChild(subscript, "language").children[0].content.toLowerCase().trim().split(" ").join("");
		let source = one.getChild(subscript, "source").children[0].content;

		try {

			if(language == "kf" || language == "kaeonfusion")
				oneSuite.process(source);

			if(language == "js" || language == "javascript")
				((core, delta) => { eval(source); })(core, delta);
		}

		catch(error) {

		}
	}
};

module.exports = [
	audio,
	cursor,
	id,
	input,
	light,
	model,
	move,
	script
];