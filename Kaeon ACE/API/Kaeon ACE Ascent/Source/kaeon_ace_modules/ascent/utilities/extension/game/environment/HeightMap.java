package kaeon_ace_modules.ascent.utilities.extension.game.environment;

import java.awt.image.BufferedImage;

import kaeon_ace_modules.ascent.utilities.geometry.mesh.Polygon;
import kaeon_ace_modules.ascent.utilities.geometry.mesh.Vertex;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.material.Material;
import kaeon_ace_modules.ascent.utilities.math.Vector3;
import kaeon_ace_modules.ascent.utilities.resource.visual.ImageLoader;
import kaeon_ace_modules.ascent.utilities.resource.visual.ImageProcessor;
import kaeon_ace_modules.ascent.utilities.user.GeneralEntity;

public class HeightMap extends GeneralEntity {
	
	private Double[][] heightValues;
	
	private Vector3[][] vertices;
	private Vector3[][][] texCoords;
	
	private int width;
	private int length;
	
	private double highestPoint;
	private double lowestPoint;
	
	private Material material;
	
	public HeightMap(
			int width,
			int length) {
		
		this.width = width;
		this.length = length;
		
		material = new Material();
		
		heightValues = new Double[width][length];
		
		for(int x = 0; x < width; x++) {
			
			for(int z = 0; z < length; z++)
				heightValues[x][z] = 0.0;
		}

		texCoords = new Vector3[width][length][4];
	}
	
	public HeightMap(
			int width,
			int length,
			int smoothness) {
		
		this(width, length);
		
		generateMapRandomly(smoothness);

		texCoords = new Vector3[width][length][4];
	}
	
	public HeightMap(
			int width,
			int length,
			String imagePath,
			int smoothness) {
		
		this(width, length);
		
		generateMapFromImage(imagePath, smoothness);
	}
	
	public void onInitialize() {
		generateMesh();
	}
	
	public void generateMapRandomly(int smoothingIterations) {
		
		heightValues = new Double[width][length];
		
		for(int x = 0; x < width; x++) {
			
			for(int z = 0; z < length; z++)
				heightValues[x][z] = null;
		}
		
		diamondSquare(width / 2, length / 2, 0);

		for(int x = 0; x < width; x++) {
			
			for(int z = 0; z < length; z++) {
				
				if(heightValues[x][z] == null)
					diamondSquare(x, z, 0);
			}
		}
		
		smoothMap(smoothingIterations);
		generateVertices();
	}
	
	private void diamondSquare(int x, int z, int iteration) {
		
		if(heightValues[x][z] != null)
			return;
		
		int factor = (int) Math.pow(2, iteration);
		
		int left = boundValue(x - width / factor, width);
		int right = boundValue(x + width / factor, width);
		int up = boundValue(z - length / factor, length);
		int down = boundValue(z + length / factor, length);
		
		boolean upLeftDefined = heightValues[left][up] != null;
		boolean upRightDefined = heightValues[right][up] != null;
		boolean downLeftDefined = heightValues[left][down] != null;
		boolean downRightDefined = heightValues[right][down] != null;
		
		double average =
				(upLeftDefined ? heightValues[left][up] : 0) +
				(upRightDefined ? heightValues[right][up] : 0) +
				(downLeftDefined ? heightValues[left][down] : 0) +
				(downRightDefined ? heightValues[right][down] : 0);
		
		if(average > 0) {
			
			average /=
					(upLeftDefined ? 1 : 0) +
					(upRightDefined ? 1 : 0) +
					(downLeftDefined ? 1 : 0) +
					(downRightDefined ? 1 : 0);
		}
		
		heightValues[x][z] = average + Math.random() * (1.0 / factor);
		
		if(!upLeftDefined)
			diamondSquare(left, up, iteration + 1);
		
		if(!upRightDefined)
			diamondSquare(right, up, iteration + 1);
		
		if(!downLeftDefined)
			diamondSquare(left, down, iteration + 1);
		
		if(!downRightDefined)
			diamondSquare(right, down, iteration + 1);
	}
	
	private int boundValue(int value, int upperBound) {
		
		if(value < 0)
			return 0;
		
		if(value > upperBound - 1)
			return upperBound - 1;
		
		return value;
	}
	
	public void generateMapFromImage(String imagePath, int smoothingIterations) {
		
		BufferedImage image = ImageLoader.getBufferedImage(imagePath);

		for(int x = 0; x < width; x++) {

			for(int z = 0; z < length; z++) {
				
				int xPos = x * (image.getWidth() / width);
				int zPos = z * (image.getHeight() / length);
				
				heightValues[x][z] = (double) ImageProcessor.getRed(image, xPos, zPos);
			}
		}
		
		smoothMap(smoothingIterations);
		generateVertices();
	}
	
	public void smoothMap(int iterations) {
		
		for(int i = 0; i < iterations; i++)
			smoothMap();
	}
	
	public void smoothMap() {
		
		highestPoint = Double.MIN_VALUE;
		lowestPoint = Double.MAX_VALUE;
		
		for(int x = 0; x < heightValues.length; x++) {
			
			for(int z = 0; z < heightValues.length; z++)
				smoothTile(x, z);
		}
	}
	
	private void smoothTile(int x, int z) {
		
		double average =
				heightValues[x][z] +
				(x > 0 ? heightValues[x - 1][z] : 0) +
				(z > 0 ? heightValues[x][z - 1] : 0) +
				(x > 0 && z > 0 ? heightValues[x - 1][z - 1] : 0) +
				(x < length - 1 ? heightValues[x + 1][z] : 0) +
				(z < length - 1 ? heightValues[x][z + 1] : 0) +
				(x < length - 1 && z < length - 1 ? heightValues[x + 1][z + 1] : 0) +
				(x > 0 && z < length - 1 ? heightValues[x - 1][z + 1] : 0) +
				(x < length - 1 && z > 0 ? heightValues[x + 1][z - 1] : 0);
		
		heightValues[x][z] = 
				
				average / 
				
				(
					1 +
					(x > 0 ? 1 : 0) +
					(z > 0 ? 1 : 0) +
					(x > 0 && z > 0 ? 1 : 0) +
					(x < length - 1 ? 1 : 0) +
					(z < length - 1 ? 1 : 0) +
					(x < length - 1 && z < length - 1 ? 1 : 0) +
					(x > 0 && z < length - 1 ? 1 : 0) +
					(x < length - 1 && z > 0 ? 1 : 0)
				);
	}
	
	private void generateVertices() {
		
		vertices = new Vector3[width][length];
		
		for(int x = 0; x < heightValues.length; x++) {
			
			for(int z = 0; z < heightValues[x].length; z++)
				checkHeightRange(x, z);
		}
		
		double xScale = 1.0 / width;
		
		double yScale = 0;
		
		if(highestPoint - lowestPoint > 0)
			yScale = 1.0 / (highestPoint - lowestPoint);
		
		double zScale = 1.0 / length;
		
		for(int x = 0; x < heightValues.length; x++) {
			
			for(int z = 0; z < heightValues[x].length; z++) {
				
				vertices[x][z] =
						new Vector3(
								x * xScale - .5,
								heightValues[x][z] * yScale,
								z * zScale - .5);
			}
		}
	}
	
	private void checkHeightRange(int x, int z) {
		
		double value = heightValues[x][z];
		
		if(value < lowestPoint)
			lowestPoint = value;
		
		if(value > highestPoint)
			highestPoint = value;
	}
	
	private void generateMesh() {
		
		for(int x = 0; x < heightValues.length; x++) {
			
			for(int z = 0; z < heightValues[x].length; z++) {
				
				getMesh().addVertex(new Vertex(vertices[x][z]));
				
				if(x >= 1 && z >= 1) {
					
					Polygon polygon = new Polygon();
					
					polygon.setMaterial(material);
					
					polygon.addVertex(((x - 1) * heightValues[x].length) + (z - 1));
					polygon.addVertex(((x - 1) * heightValues[x].length) + z);
					polygon.addVertex((x * heightValues[x].length) + z);
					polygon.addVertex((x * heightValues[x].length) + (z - 1));
					
					polygon.setTexCoord(((x - 1) * heightValues[x].length) + (z - 1), texCoords[x][z][0]);
					polygon.setTexCoord(((x - 1) * heightValues[x].length) + z, texCoords[x][z][1]);
					polygon.setTexCoord((x * heightValues[x].length) + z, texCoords[x][z][2]);
					polygon.setTexCoord((x * heightValues[x].length) + (z - 1), texCoords[x][z][3]);
					
					getMesh().addPolygon(polygon);
				}
			}
		}
	}
	
	public void applyTexture(String texture, int xStretch, int zStretch) {
		
		material.setTexture(texture);
		
		for(int x = 0; x < heightValues.length; x++) {
			
			for(int z = 0; z < heightValues[x].length; z++) {
				
				texCoords[x][z][0] =
					new Vector3(
						(double) ((x - 1) % xStretch) / xStretch,
						(double) ((z - 1) % zStretch) / zStretch,
						0
					);
				
				texCoords[x][z][1] =
					new Vector3(
						(double) (x % xStretch) / xStretch,
						(double) ((z - 1) % zStretch) / zStretch,
						0
					);
				
				texCoords[x][z][2] =
					new Vector3(
						(double) ((x - 1) % xStretch) / xStretch,
						(double) (z % zStretch) / zStretch,
						0
					);
				
				texCoords[x][z][3] =
					new Vector3(
						(double) (x % xStretch) / xStretch,
						(double) (z % zStretch) / zStretch,
						0
					);
			}
		}
	}
	
	public double getValueAt(int x, int z) {
		return heightValues[x][z];
	}
	
	public double getHeightAt(double xPos, double zPos) {
		
		try {
			
			xPos += getXSize() / 2 - getXPosition();
			zPos += getZSize() / 2 - getZPosition();
			
			double squareSizeX = getXSize() / width;
			double squareSizeZ = getZSize() / length;
			
			int x = (int) ((double) (xPos) / squareSizeX);
			int z = (int) ((double) (zPos) / squareSizeZ);
			
			double height = getYSize() / (highestPoint - lowestPoint);
			
			double upperLeft = getValueAt(x, z) * height;
			double upperRight = getValueAt(x + 1, z) * height;
			double lowerLeft = getValueAt(x, z + 1) * height;
			double lowerRight = getValueAt(x + 1, z + 1) * height;
			
			double xScale = (1 / squareSizeX) * (xPos % squareSizeX);
			double zScale = (1 / squareSizeZ) * (zPos % squareSizeZ);
			
			upperLeft *= (1 - xScale) * (1 - zScale);
			upperRight *= xScale * (1 - zScale);
			lowerLeft *= (1 - xScale) * zScale;
			lowerRight *= xScale * zScale;
	
			return 
					((upperLeft + upperRight + lowerLeft + lowerRight)) +
					getYPosition();
		}
		
		catch(Exception e) {
			return Double.MIN_VALUE;
		}
	}
}
