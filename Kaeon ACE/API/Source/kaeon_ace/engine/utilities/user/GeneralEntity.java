package kaeon_ace.engine.utilities.user;

import kaeon_ace.engine.processes.loader.Loader;
import kaeon_ace.engine.processes.scene.utilities.Entity;
import kaeon_ace.engine.processes.scene.utilities.script.Script;
import kaeon_ace.engine.utilities.geometry.mesh.Mesh;
import kaeon_ace.engine.utilities.geometry.utilities.physics.collider.Collider;
import kaeon_ace.engine.utilities.geometry.utilities.physics.forces.Force;
import kaeon_ace.engine.utilities.geometry.utilities.visual.Color;
import kaeon_ace.engine.utilities.geometry.utilities.visual.effects.Effect;
import kaeon_ace.engine.utilities.math.Vector3;
import kaeon_ace.engine.utilities.math.transform.Rotation;
import kaeon_ace.engine.utilities.math.transform.ScaleTo;
import kaeon_ace.engine.utilities.math.transform.Transform;
import kaeon_ace.engine.utilities.math.transform.Translation;
import kaeon_ace.engine.utilities.user.super_user.CustomRender;
import philosophers_stone.PhilosophersStoneUtilities;

public class GeneralEntity extends Entity {
	
	private Mesh mesh;
	private CustomRender customRender;
	
	private Translation position;
	private Rotation rotation;
	private ScaleTo size;
	
	private int ticks;

	public GeneralEntity() {
		
		super();
		
		getRunTime().addScript(
				
			new Script() {
				
				public void onStart() {
					onStartBootstrap();
				}
				
				public void onUpdate() {
					onUpdateBootstrap();
				}
				
				public void onActivate() {
					onActivateBootstrap();
				}
				
				public void onDeactivate() {
					onDeactivateBootstrap();
				}
				
				public void onParentActivate() {
					onParentActivateBootstrap();
				}
				
				public void onParentDeactivate() {
					onParentDeactivateBootstrap();
				}
				
				public void onDestroy() {
					onDestroyBootstrap();
				}
			}
		);
		
		mesh = new Mesh();
		
		position = new Translation();
		rotation = new Rotation();
		size = new ScaleTo(1, 1, 1);
		
		position.setPrioirty(Integer.MIN_VALUE);
		rotation.setPrioirty(Integer.MIN_VALUE + 1);
		size.setPrioirty(Integer.MIN_VALUE + 2);
		
		transform(position);
		transform(rotation);
		transform(size);
		
		getBody().addMesh(mesh);
	}

	public GeneralEntity(Mesh mesh) {
		
		this();
		
		this.mesh = mesh;
		addMesh(this.mesh);
	}

	public GeneralEntity(CustomRender customRender) {
		
		this();
		
		customRender.setEntity(this);
		this.customRender = customRender;
	}

	public GeneralEntity(Effect effect) {
		addEffect(effect);
	}

	public GeneralEntity(Script script) {
		
		this();
		
		this.addScript(script);
	}
	
	public void onStartBootstrap() {
		
		if(customRender != null)
			((Loader) PhilosophersStoneUtilities.get(getEngine(), "Loader").get(0)).addNewCustomRender(customRender);
		
		onStart();
	}
	
	public void onStart() {
		
	}
	
	public void onUpdateBootstrap() {
		
		ticks++;
		
		onUpdate();
	}
	
	public void onUpdate() {
		
	}
	
	public void onActivateBootstrap() {
		onActivate();
	}
	
	public void onActivate() {
		
	}
	
	public void onDeactivateBootstrap() {
		onDeactivate();
	}
	
	public void onDeactivate() {
		
	}
	
	public void onParentActivateBootstrap() {
		onActivate();
	}
	
	public void onParentActivate() {
		
	}
	
	public void onParentDeactivateBootstrap() {
		onDeactivate();
	}
	
	public void onParentDeactivate() {
		
	}
	
	public void onDestroyBootstrap() {
		onDestroy();
	}
	
	public void onDestroy() {
		
	}
	
	public void activate() {
		getRunTime().activate();
	}
	
	public void deactivate() {
		getRunTime().deactivate();
	}
	
	public void destroy() {
		getRunTime().destroy();
	}
	
	public void setRenderMode(String renderMode) {
		mesh.setRenderMode(renderMode);
	}

	public void setRenderPriority(int renderPriority) {
		mesh.setRenderPriority(renderPriority);
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public void addScript(Script script) {
		getRunTime().addScript(script);
	}
	
	public void transform(Transform transform) {
		getBody().transform(transform);
	}
	
	public void addMesh(Mesh mesh) {
		getBody().addMesh(mesh);
	}
	
	public void addEffect(Effect effect) {
		getBody().addEffect(effect);
	}
	
	public void addForce(Force force) {
		getBody().getPhysics().addForce(force);
	}
	
	public void addCollider(Collider collider) {
		getBody().getPhysics().addCollider(collider);
	}
	
	public void move(Vector3 move) {
		position.increment(move);
	}
	
	public void move(double x, double y, double z) {
		position.increment(x, y, z);
	}
	
	public void moveX(double move) {
		position.incrementX(move);
	}
	
	public void moveY(double move) {
		position.incrementY(move);
	}
	
	public void moveZ(double move) {
		position.incrementZ(move);
	}
	
	public void setPositionAs(Vector3 position) {
		this.position.setTransformAs(position);
	}
	
	public void setPosition(Vector3 position) {
		this.position.setTransform(position);
	}
	
	public void setPosition(double x, double y, double z) {
		this.position.setTransform(x, y, z);
	}
	
	public void setXPosition(double xPosition) {
		position.setX(xPosition);
	}
	
	public void setYPosition(double yPosition) {
		position.setY(yPosition);
	}
	
	public void setZPosition(double zPosition) {
		position.setZ(zPosition);
	}
	
	public void rotate(Vector3 rotate) {
		rotation.increment(rotate);
	}
	
	public void rotate(double x, double y, double z) {
		rotation.increment(x, y, z);
	}
	
	public void rotateX(double rotate) {
		rotation.incrementX(rotate);
	}
	
	public void rotateY(double rotate) {
		rotation.incrementY(rotate);
	}
	
	public void rotateZ(double rotate) {
		rotation.incrementZ(rotate);
	}
	
	public void setRotationAs(Vector3 rotation) {
		this.rotation.setTransformAs(rotation);
	}
	
	public void setRotation(Vector3 rotation) {
		this.rotation.setTransform(rotation);
	}
	
	public void setRotation(double x, double y, double z) {
		this.rotation.setTransform(x, y, z);
	}
	
	public void setXRotation(double xRotation) {
		rotation.setX(xRotation);
	}
	
	public void setYRotation(double yRotation) {
		rotation.setY(yRotation);
	}
	
	public void setZRotation(double zRotation) {
		rotation.setZ(zRotation);
	}
	
	public void scale(Vector3 scale) {
		size.increment(scale);
	}
	
	public void scale(double x, double y, double z) {
		size.increment(x, y, z);
	}
	
	public void scaleX(double scale) {
		size.incrementX(scale);
	}
	
	public void scaleY(double scale) {
		size.incrementY(scale);
	}
	
	public void scaleZ(double scale) {
		size.incrementZ(scale);
	}
	
	public void setSizeAs(Vector3 size) {
		this.size.setTransformAs(size);
	}
	
	public void setSize(Vector3 size) {
		this.size.setTransform(size);
	}
	
	public void setSize(double x, double y, double z) {
		this.size.setTransform(x, y, z);
	}
	
	public void scaleTo(double size) {
		this.size.getTransform().setMagnitude(size);
	}
	
	public void setXSize(double xSize) {
		size.setX(xSize);
	}
	
	public void setYSize(double ySize) {
		size.setY(ySize);
	}
	
	public void setZSize(double zSize) {
		size.setZ(zSize);
	}
	
	public Vector3 getPosition() {
		return position.getTransform();
	}
	
	public double getXPosition() {
		return position.getX();
	}
	
	public double getYPosition() {
		return position.getY();
	}
	
	public double getZPosition() {
		return position.getZ();
	}
	
	public Vector3 getRotation() {
		return rotation.getTransform();
	}
	
	public double getXRotation() {
		return rotation.getX();
	}
	
	public double getYRotation() {
		return rotation.getY();
	}
	
	public double getZRotation() {
		return rotation.getZ();
	}
	
	public Vector3 getSize() {
		return size.getTransform();
	}
	
	public double getXSize() {
		return size.getX();
	}
	
	public double getYSize() {
		return size.getY();
	}
	
	public double getZSize() {
		return size.getZ();
	}
	
	public void setColor(Color color) {
		mesh.getMaterial().setColorAs(color);
	}
	
	public Color getColor() {
		return mesh.getMaterial().getColor();
	}
	
	public void setColor(double red, double green, double blue) {
		getColor().setColor(red, green, blue);
	}
	
	public void setColor(double red, double green, double blue, double alpha) {
		getColor().setColor(red, green, blue, alpha);
	}
	
	public void setRed(double red) {
		getColor().setRed(red);
	}
	
	public void setGreen(double green) {
		getColor().setGreen(green);
	}
	
	public void setBlue(double blue) {
		getColor().setBlue(blue);
	}
	
	public void setAlpha(double alpha) {
		getColor().setAlpha(alpha);
	}
	
	public void color(double red, double green, double blue) {
		getColor().color(red, green, blue);
	}
	
	public void color(double red, double green, double blue, double alpha) {
		getColor().color(red, green, blue, alpha);
	}
	
	public void colorRed(double red) {
		getColor().colorRed(red);
	}
	
	public void colorGreen(double green) {
		getColor().colorGreen(green);
	}
	
	public void colorBlue(double blue) {
		getColor().colorBlue(blue);
	}
	
	public void colorAlpha(double alpha) {
		getColor().colorAlpha(alpha);
	}
	
	public double getRed() {
		return getColor().getRed();
	}
	
	public double getGreen() {
		return getColor().getBlue();
	}
	
	public double getBlue() {
		return getColor().getGreen();
	}
	
	public double getAlpha() {
		return getColor().getAlpha();
	}
	
	public int getTicks() {
		return ticks;
	}
}