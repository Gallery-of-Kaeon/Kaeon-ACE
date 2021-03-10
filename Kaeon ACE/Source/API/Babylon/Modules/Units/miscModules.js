var moduleDependencies = {
	one: "",
	onePlus: "",
	philosophersStone: ""
};

var one = require(moduleDependencies.one);
var onePlus = require(moduleDependencies.onePlus);
var philosophersStone = require(moduleDependencies.philosophersStone);

function getItem(element, item, defaultOption) {

	if(one.getChild(element, item) != null)
		return one.getChild(element, item).children[0].content;

	return defaultOption;
}

var ground = {

	onDeserialize: function(core, ace, entity) {

		if(one.getChild(ace, "ground") != null) {
			
			let item = one.getChild(ace, "ground");

			let texture = getItem(item, "texture");
			let map = getItem(item, "map");
			let subdivisions = Number(getItem(item, "subdivisions", "1000"));
			let width = Number(getItem(item, "width", "1"));
			let length = Number(getItem(item, "length", "1"));
			let height = Number(getItem(item, "height", "0"));

			var groundMaterial = new BABYLON.StandardMaterial("ground", core.scene);

			if(texture != null)
				groundMaterial.diffuseTexture = new BABYLON.Texture(texture, core.scene);

			let ground = null;

			if(map != null) {
				
				ground = BABYLON.Mesh.CreateGroundFromHeightMap(
					"ground",
					map,
					width,
					length,
					subdivisions,
					0,
					height,
					core.scene,
					false,
					function() {
						
					}
				);

				ground.material = groundMaterial;
			}

			else {

				ground = BABYLON.MeshBuilder.CreateGround(
					"ground",
					{
						width: width,
						height: length,
						subdivisions: 4
					},
					core.scene
				);

				ground.material = groundMaterial;
			}

			entity.components.push(ground);
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

module.exports = [
	ground,
	move
];