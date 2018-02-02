package kaeon_ace.engine.utilities.math;

import java.util.ArrayList;

public class Matrix<Type> {
	
	private ArrayList<Object> elements;
	
	private int dimension;
	
	public Matrix() {
		this(0);
	}
	
	private Matrix(int dimension) {
		
		this.dimension = dimension;
		
		elements = new ArrayList<Object>();
		
		if(this.dimension == 0)
			elements.add(null);
	}
	
	@SuppressWarnings("unchecked")
	public void setValue(ArrayList<Integer> indices, Type value) {
		
		while(dimension < indices.size()) {
			
			Matrix<Type> matrix = new Matrix<Type>(dimension);
			matrix.elements.addAll(elements);
			
			elements = new ArrayList<Object>();
			
			for(int i = 0; i < indices.get((indices.size() - 1) - dimension); i++)
				elements.add(null);
			
			elements.set(indices.get((indices.size() - 1) - dimension) - 1, matrix);
			
			dimension++;
		}
		
		Matrix<Type> matrix = this;
		
		for(int i = 0; i < indices.size(); i++) {
			
			for(int j = matrix.elements.size() - 1; j < indices.get(i); j++)
				matrix.elements.add(null);
			
			if(matrix.elements.get(indices.get(i) - 1) == null)
				matrix.elements.set(indices.get(i) - 1, new Matrix<Type>(matrix.dimension - 1));
			
			matrix = (Matrix<Type>) matrix.elements.get(indices.get(i) - 1);
		}
		
		matrix.elements.set(0, value);
	}
	
	@SuppressWarnings("unchecked")
	public Type getValue(ArrayList<Integer> indices) {
		
		if(indices.size() != dimension)
			return null;
		
		Matrix<Type> matrix = this;
		
		for(int i = 0; i < indices.size(); i++) {
			
			for(int j = matrix.elements.size(); j < indices.get(i); j++)
				matrix.elements.add(new Matrix<Type>(matrix.dimension - 1));
			
			matrix = (Matrix<Type>) matrix.elements.get(indices.get(i) - 1);
			
			if(matrix == null)
				return null;
		}
		
		return (Type) matrix.elements.get(0);
	}
}