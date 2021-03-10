var moduleDependencies = {
	fusion: "https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-FUSION/master/Kaeon%20FUSION/Source/Engine/FUSION.js",
	kaeonACE: "https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-ACE/master/Kaeon%20ACE/Source/API/Babylon/Core/KaeonACE.js",
	philosophersStone: "https://raw.githubusercontent.com/Gallery-of-Kaeon/Philosophers-Stone/master/Philosopher's%20Stone/API/PhilosophersStone.js",
	widgets: "https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/Utilities/UI/Visual/Widgets/widgets.js"
};

var fusion = require(moduleDependencies.fusion);
var kaeonACE = require(moduleDependencies.kaeonACE);
var philosophersStone = require(moduleDependencies.philosophersStone);
var widgets = require(moduleDependencies.widgets);

function getACECallback(fusion, ace) {

	return function(element) {
		kaeonACE.run(fusion, ace, element);
	};
}

let ACEModule = function() {

	philosophersStone.abide(this, new fusion.FUSIONUnit());

	this.verify = function(element) {
		return element.parent == null;
	}

	this.process = function(element, processed) {

		widgets.createStartScreen(
			document.documentElement,
			"Start",
			getACECallback(this.fusion, element)
		);
	}
}

module.exports = function(fusion) {

	let aceModule = new ACEModule();
	aceModule.fusion = fusion;

	philosophersStone.connect(fusion, aceModule, [], true);
};