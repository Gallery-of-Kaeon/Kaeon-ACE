package kaeon_ace_modules.ascent.utilities.extension.game.sprite;

import java.awt.image.BufferedImage;

import kaeon_ace_modules.ascent.processes.frame.Frame;
import kaeon_ace_modules.ascent.utilities.geometry.mesh.Mesh;
import kaeon_ace_modules.ascent.utilities.geometry.mesh.Polygon;
import kaeon_ace_modules.ascent.utilities.geometry.mesh.Vertex;
import kaeon_ace_modules.ascent.utilities.math.Vector3;
import kaeon_ace_modules.ascent.utilities.resource.visual.ImageLoader;
import kaeon_ace_modules.ascent.utilities.resource.visual.ImageProcessor;
import kaeon_ace_modules.ascent.utilities.user.GeneralEntity;
import philosophers_stone.PhilosophersStoneUtilities;

public class Sprite extends GeneralEntity {
	
	public static String COLLISION_UP = "COLLISION_UP";
	public static String COLLISION_DOWN = "COLLISION_DOWN";
	public static String COLLISION_LEFT = "COLLISION_LEFT";
	public static String COLLISION_RIGHT = "COLLISION_RIGHT";
	
	private BufferedImage collisionImage;

	private Polygon polygon;
	
	private Vertex upperLeft;
	private Vertex upperRight;
	private Vertex lowerLeft;
	private Vertex lowerRight;
	
	public Frame frame;
	
	public Sprite() {
		polygon = new Polygon();
	}

	public Sprite(String texture) {
		
		polygon = new Polygon();
		
		setTexture(texture);
	}

	public Sprite(String texture, String renderMode) {
		
		polygon = new Polygon();
		
		setTexture(texture);
		setRenderMode(renderMode);
	}
	
	public void onInitialize() {
		
		frame = (Frame) PhilosophersStoneUtilities.get(getEngine(), "Frame").get(0);
		
		generateVertices();
		
		getMesh().addVertex(upperLeft);
		getMesh().addVertex(upperRight);
		getMesh().addVertex(lowerRight);
		getMesh().addVertex(lowerLeft);
		
		polygon.addVertex(0);
		polygon.setTexCoord(0, new Vector3(0, 0, 0));
		
		polygon.addVertex(1);
		polygon.setTexCoord(1, new Vector3(1, 0, 0));
		
		polygon.addVertex(2);
		polygon.setTexCoord(2, new Vector3(1, 1, 0));
		
		polygon.addVertex(3);
		polygon.setTexCoord(3, new Vector3(0, 1, 0));
		
		getMesh().addPolygon(polygon);
	}
	
	private void generateVertices() {
		
		if(upperLeft == null) {
			
			upperLeft = new Vertex();
			
			upperLeft.setPosition(-.5, .5, 0);
		}
		
		if(upperRight == null) {
			
			upperRight = new Vertex();
			
			upperRight.setPosition(.5, .5, 0);
		}
		
		if(lowerLeft == null) {
	
			lowerLeft = new Vertex();
			
			lowerLeft.setPosition(-.5, -.5, 0);
		}
		
		if(lowerRight == null) {
	
			lowerRight = new Vertex();
			
			lowerRight.setPosition(.5, -.5, 0);
		}
	}
	
	public void setTexture(String texture) {
		polygon.getMaterial().setTexture(texture);
	}
	
	public void setRenderMode(String renderMode) {
		getMesh().setRenderMode(renderMode);
	}
	
	public void setTexCoords(double x, double y, double width, double height) {
		
		generateVertices();

		polygon.setTexCoord(0, new Vector3(x, y, 0));
		polygon.setTexCoord(1, new Vector3(x + width, y, 0));
		polygon.setTexCoord(2, new Vector3(x + width, y + height, 0));
		polygon.setTexCoord(3, new Vector3(x, y + height, 0));
	}
	
	public void setCollisionImage() {
		collisionImage = ImageLoader.getBufferedImage(polygon.getMaterial().getTexture());
	}
	
	public String collide(Sprite sprite) {
		
		String collision = null;
		
		if(collides(sprite)) {
			
			Vector3 localSize = null;
			Vector3 foreignSize = null;
		
			Vector3 offset = null;
			
			if(getMesh().getRenderMode().equals(Mesh.RENDER_MODE_3D)) {
				
				localSize = getSize();
				foreignSize = sprite.getSize();
				
				offset = sprite.getPosition();
				offset.decrement(getPosition());
			}
			
			else {
				
				localSize = get2DSize();
				foreignSize = sprite.get2DSize();
				
				offset = sprite.get2DPosition();
				offset.decrement(get2DPosition());
			}
			
			localSize.setX(Math.abs(localSize.getX()));
			localSize.setY(Math.abs(localSize.getY()));
			localSize.setZ(Math.abs(localSize.getZ()));
			
			foreignSize.setX(Math.abs(foreignSize.getX()));
			foreignSize.setY(Math.abs(foreignSize.getY()));
			foreignSize.setZ(Math.abs(foreignSize.getZ()));
			
			if(Math.abs(offset.getX() / ((localSize.getX() / 2) + (foreignSize.getX() / 2))) >
					Math.abs(offset.getY() / ((localSize.getY() / 2) + (foreignSize.getY() / 2)))) {
				
				if(offset.getX() < 0) {
					offset.setX(-(localSize.getX() / 2) - (foreignSize.getX() / 2));
					collision = COLLISION_LEFT;
				}
				
				else {
					offset.setX((localSize.getX() / 2) + (foreignSize.getX() / 2));
					collision = COLLISION_RIGHT;
				}
			}
			
			else if(Math.abs(offset.getX() / ((localSize.getX() / 2) + (foreignSize.getX() / 2))) <
					Math.abs(offset.getY() / ((localSize.getY() / 2) + (foreignSize.getY() / 2)))) {
				
				if(offset.getY() < 0) {
					offset.setY(-(localSize.getY() / 2) - (foreignSize.getY() / 2));
					collision = COLLISION_UP;
				}
				
				else {
					offset.setY((localSize.getY() / 2) + (foreignSize.getY() / 2));
					collision = COLLISION_DOWN;
				}
			}
			
			else {
				
				if(offset.getX() < 0 && offset.getY() < 0) {
					offset.setX(-(localSize.getX() / 2) - (foreignSize.getX() / 2));
					offset.setY(-(localSize.getY() / 2) - (foreignSize.getY() / 2));
				}
			
				else if(offset.getX() > 0 && offset.getY() < 0) {
					offset.setX((localSize.getX() / 2) + (foreignSize.getX() / 2));
					offset.setY(-(localSize.getY() / 2) - (foreignSize.getY() / 2));
				}
			
				else if(offset.getX() < 0 && offset.getY() > 0) {
					offset.setX(-(localSize.getX() / 2) - (foreignSize.getX() / 2));
					offset.setY((localSize.getY() / 2) + (foreignSize.getY() / 2));
				}
			
				else if(offset.getX() > 0 && offset.getY() > 0) {
					offset.setX((localSize.getX() / 2) + (foreignSize.getX() / 2));
					offset.setY((localSize.getY() / 2) + (foreignSize.getY() / 2));
				}
			}
			
			if(getMesh().getRenderMode().equals(Mesh.RENDER_MODE_3D)) {
				offset.increment(getPosition());
				sprite.setPosition(offset);
			}
			
			else {
				
				offset.increment(get2DPosition());
				
				double xPos = (offset.getX() - (frame.getWidth() / 2)) / (frame.getWidth() / 2);
				double yPos = (offset.getY() - (frame.getHeight() / 2)) / (frame.getHeight() / 2);
				
				sprite.setPosition(xPos, -yPos, 0);
			}
		}
		
		return collision;
	}
	
	public boolean collides(Sprite sprite) {
		
		Vector3 offset = new Vector3();
		
		Vector3 localCollisionSize = getSize();
		Vector3 foreignCollisionSize = sprite.getSize();
		
		if(getMesh().getRenderMode().equals(Mesh.RENDER_MODE_3D)) {
			
			offset = new Vector3(getPosition());
			offset.decrement(sprite.getPosition());
			
			localCollisionSize = getSize();
			foreignCollisionSize = sprite.getSize();
		}
		
		else {
			
			offset = get2DPosition();
			offset.decrement(sprite.get2DPosition());
			
			localCollisionSize = get2DSize();
			foreignCollisionSize = sprite.get2DSize();
		}
		
		localCollisionSize.setX(Math.abs(localCollisionSize.getX()));
		localCollisionSize.setY(Math.abs(localCollisionSize.getY()));
		localCollisionSize.setZ(Math.abs(localCollisionSize.getZ()));
		
		foreignCollisionSize.setX(Math.abs(foreignCollisionSize.getX()));
		foreignCollisionSize.setY(Math.abs(foreignCollisionSize.getY()));
		foreignCollisionSize.setZ(Math.abs(foreignCollisionSize.getZ()));
	
		if(Math.abs(offset.getX()) < (localCollisionSize.getX() / 2) + (foreignCollisionSize.getX() / 2) &&
				Math.abs(offset.getY()) < (localCollisionSize.getY() / 2) + (foreignCollisionSize.getY() / 2)) {
			
			return true;
		}
		
		return false;
	}
	
	public boolean onPoint(double x, double y) {
		
		Vector3 position2D = get2DPosition();
		Vector3 size2D = get2DSize();
		
		size2D.setX(Math.abs(size2D.getX()));
		size2D.setY(Math.abs(size2D.getY()));
		size2D.setZ(Math.abs(size2D.getZ()));
		
		Vector3 point = new Vector3();
		
		point.setX(
				(x * (frame.getWidth() / 2)) +
				(frame.getWidth() / 2));
		
		point.setY(
				(-y * (frame.getHeight() / 2)) +
				(frame.getHeight() / 2));
		
		if(!getMesh().getRenderMode().equals(Mesh.RENDER_MODE_3D) &&
				point.getX() >= position2D.getX() - size2D.getX() / 2 &&
				point.getX() <= position2D.getX() + size2D.getX() / 2 &&
				point.getY() >= position2D.getY() - size2D.getY() / 2 &&
				point.getY() <= position2D.getY() + size2D.getY() / 2)
			
			return true;
		
		return false;
	}
	
	public boolean imageOnPoint(double x, double y) {
		
		Vector3 position2D = get2DPosition();
		Vector3 size2D = get2DSize();
		
		Vector3 point = new Vector3();
		
		point.setX(
				(x * (frame.getWidth() / 2)) +
				(frame.getWidth() / 2));
		
		point.setY(
				(-y * (frame.getHeight() / 2)) +
				(frame.getHeight() / 2));
		
		if(onPoint(x, y)) {
			
			if(collisionImage == null)
				setCollisionImage();
			
			point.decrement(position2D);
			
			point.setX((point.getX() / size2D.getX()) + (1.0 / 2));
			point.setY((point.getY() / size2D.getY()) + (1.0 / 2));
			
			double texCoordLeft = polygon.getTexCoords().get(0).getX();
			double texCoordRight = polygon.getTexCoords().get(1).getX();
			double texCoordUp = polygon.getTexCoords().get(0).getY();
			double texCoordDown = polygon.getTexCoords().get(2).getY();
			
			int xPos =
					(int)
					(point.getX() *
					((texCoordRight - texCoordLeft) * collisionImage.getWidth()) +
					(texCoordLeft) * collisionImage.getWidth());
			
			int yPos =
					(int)
					(point.getY() *
					((texCoordDown - texCoordUp) * collisionImage.getHeight()) +
					(texCoordUp) * collisionImage.getHeight());
			
			if(xPos < 0)
				xPos = 0;
			
			if(yPos < 0)
				yPos = 0;
			
			if(xPos >= collisionImage.getWidth())
				xPos = collisionImage.getWidth() - 1;
			
			if(yPos >= collisionImage.getHeight())
				yPos = collisionImage.getHeight() - 1;
			
			if(ImageProcessor.getAlpha(collisionImage, xPos, yPos) > 0)
				return true;
		}
		
		return false;
	}
	
	public Vector3 get2DPosition() {
		
		Vector3 position2D = new Vector3();
		
		if(getMesh().getRenderMode().equals(Mesh.RENDER_MODE_2D)) {
			
			double aspect = (frame.getWidth() + frame.getHeight()) / 4;
			
			position2D.setX(
					(getPosition().getX() * aspect) +
					(frame.getWidth() / 2));
			
			position2D.setY(
					(-getPosition().getY() * aspect) +
					(frame.getHeight() / 2));
		}
		
		if(getMesh().getRenderMode().equals(Mesh.RENDER_MODE_2D_ASPECT_OFF)) {
			
			position2D.setX(
					(getPosition().getX() * (frame.getWidth() / 2)) +
					(frame.getWidth() / 2));
			
			position2D.setY(
					(-getPosition().getY() * (frame.getHeight() / 2)) +
					(frame.getHeight() / 2));
		}
		
		if(getMesh().getRenderMode().equals(Mesh.RENDER_MODE_2D_ABSOLUTE)) {
			position2D.setX((frame.getWidth() / 2) + getPosition().getX());
			position2D.setY((frame.getHeight() / 2) - getPosition().getY());
		}
		
		return position2D;
	}
	
	public Vector3 get2DSize() {
		
		Vector3 size2D = new Vector3();
		
		if(getMesh().getRenderMode().equals(Mesh.RENDER_MODE_2D)) {
			
			double aspect = (frame.getWidth() + frame.getHeight()) / 4;
			
			size2D.setX(getSize().getX() * aspect);
			size2D.setY(getSize().getY() * aspect);
		}
		
		if(getMesh().getRenderMode().equals(Mesh.RENDER_MODE_2D_ASPECT_OFF)) {
			size2D.setX(getSize().getX() * frame.getWidth() / 2);
			size2D.setY(getSize().getY() * frame.getHeight() / 2);
		}
		
		if(getMesh().getRenderMode().equals(Mesh.RENDER_MODE_2D_ABSOLUTE))
			size2D.copy(getSize());
		
		return size2D;
	}
}
