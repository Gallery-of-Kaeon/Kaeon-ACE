var moduleDependencies = {
	one: "https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-FUSION/master/Kaeon%20FUSION/Source/Engine/ONE.js",
};

var one = require(moduleDependencies.one);

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

		/*

		if(core.input.pc.keyboard.includes(16)) // SHIFT
			core.camera.rotation.z += .015;

		if(core.input.pc.keyboard.includes(32)) // SPACE
			core.camera.rotation.z -= .015;

		*/

		if(core.camera.rotation.x > Math.PI / 2 - .001)
			core.camera.rotation.x = Math.PI / 2 - .001;

		else if(core.camera.rotation.x < -Math.PI / 2 + .001)
			core.camera.rotation.x = -Math.PI / 2 + .001;
	}
};

module.exports = [
	camera
];