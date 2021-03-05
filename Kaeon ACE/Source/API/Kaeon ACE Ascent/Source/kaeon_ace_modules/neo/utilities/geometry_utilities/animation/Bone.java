package kaeon_ace_modules.neo.utilities.geometry_utilities.animation;

import java.util.ArrayList;

import kaeon_ace_modules.neo.utilities.geometry.Transform;

public class Bone {
	
	public ArrayList<VertexWeight> weights;
	public ArrayList<Transform> transforms;
	public ArrayList<Bone> children;
}
