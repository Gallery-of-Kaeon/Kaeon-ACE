var moduleDependencies = {
	moduleUtilities: "",
	one: "https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-FUSION/master/Kaeon%20FUSION/Source/Engine/ONE.js",
};

var moduleUtilities = require(moduleDependencies.moduleUtilities);
var one = require(moduleDependencies.one);

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

var ground = {

	onDeserialize: function(core, ace, entity) {

		if(one.getChild(ace, "ground") != null) {
			
			let item = one.getChild(ace, "ground");

			let texture = moduleUtilities.getItem(item, "texture");
			let map = moduleUtilities.getItem(item, "map");
			let subdivisions = Number(moduleUtilities.getItem(item, "subdivisions", "1000"));
			let width = Number(moduleUtilities.getItem(item, "width", "1"));
			let length = Number(moduleUtilities.getItem(item, "length", "1"));
			let height = Number(moduleUtilities.getItem(item, "height", "0"));

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
	ball,
	ground,
	skybox
];