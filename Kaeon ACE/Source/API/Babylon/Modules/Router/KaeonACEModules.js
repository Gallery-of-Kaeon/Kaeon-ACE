/*

	on call: take core and data string, return string
	on default: take core
	on deserialize: take core, ace, and entity, modify entity
	on entity: take core, entity, and delta
	on serialize: take core and entity, return a ONE list form element or null
	on update: take core and delta

 */

var moduleDependencies = {
	modules: {
		cameraModules: "https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-ACE/master/Kaeon%20ACE/Source/API/Babylon/Modules/Units/cameraModules.js",
		miscModules: "https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-ACE/master/Kaeon%20ACE/Source/API/Babylon/Modules/Units/miscModules.js",
		standardModules: "https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-ACE/master/Kaeon%20ACE/Source/API/Babylon/Modules/Units/standardModules.js"
	},
	philosophersStone: "https://raw.githubusercontent.com/Gallery-of-Kaeon/Philosophers-Stone/master/Philosopher's%20Stone/API/PhilosophersStone.js",
};

var philosophersStone = require(moduleDependencies.philosophersStone);

module.exports = function(core) {

	let modules = [];

	modules = modules.concat(require(moduleDependencies.modules.cameraModules));
	modules = modules.concat(require(moduleDependencies.modules.miscModules));
	modules = modules.concat(require(moduleDependencies.modules.standardModules));

	for(let i = 0; i < modules.length; i++) {

		modules[i].tags = modules[i].tags ?
			modules[i].tags.concat(["Kaeon ACE"]) :
			["Kaeon ACE"];

		philosophersStone.connect(core, modules[i], [], true);
	}
};