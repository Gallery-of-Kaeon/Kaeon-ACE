var fusion = require("https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/United%20Bootstrap/FUSION.js");
var philosophersStone = require("https://raw.githubusercontent.com/Gallery-of-Kaeon/Philosophers-Stone/master/Philosopher's%20Stone/API/JavaScript/PhilosophersStone.js");

let ACEModule = function() {

	philosophersStone.abide(this, new fusion.FUSIONUnit());

	this.verify = function(element) {
		return element.parent == null;
	}

	this.process = function(element, processed) {

		require("https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-ACE/master/Kaeon%20ACE/API/Kaeon%20ACE/Babylon/KaeonACE.js").run(
			this.fusion,
			element,
			document.documentElement
		);
	}
}

module.exports = function(fusion) {

	let aceModule = new ACEModule();
	aceModule.fusion = fusion;

	philosophersStone.connect(fusion, aceModule, [], true);
};