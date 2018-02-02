package kaeon_ace.engine.utilities.math;

import java.util.ArrayList;

public class Graph<Type> {

	private ArrayList<Type> elements;
	private ArrayList<ArrayList<Double>> adjacencyMatrix;
	
	public Graph() {
		elements = new ArrayList<Type>();
		adjacencyMatrix = new ArrayList<ArrayList<Double>>();
	}
	
	public void add(Type element) {
		
		elements.add(element);
		
		for(int i = 0; i < adjacencyMatrix.size(); i++)
			adjacencyMatrix.get(i).add(null);
		
		ArrayList<Double> row = new ArrayList<Double>();
		
		for(int i = 0; i < adjacencyMatrix.size() + 1; i++)
			row.add(null);
		
		adjacencyMatrix.add(row);
	}
	
	public void add(Type element, int index) {
		
		elements.add(index, element);
		
		for(int i = 0; i < adjacencyMatrix.size(); i++)
			adjacencyMatrix.get(i).add(index, null);
		
		ArrayList<Double> row = new ArrayList<Double>();
		
		for(int i = 0; i < adjacencyMatrix.size() + 1; i++)
			row.add(null);
		
		adjacencyMatrix.add(index, row);
	}
	
	public Type remove(int index) {
		
		Type element = elements.remove(index);
		
		adjacencyMatrix.remove(index);
		
		for(int i = 0; i < adjacencyMatrix.size(); i++)
			adjacencyMatrix.get(i).remove(index);
		
		return element;
	}
	
	public Type remove(Type element) {
		
		int index = getIndexOf(element);
		
		if(index == -1)
			return null;
		
		return remove(index);
	}
	
	public ArrayList<Type> getElements() {
		return elements;
	}
	
	public Type get(int index) {
		return elements.get(index);
	}
	
	public ArrayList<ArrayList<Double>> getAdjacencyMatrix() {
		return adjacencyMatrix;
	}
	
	public int size() {
		return elements.size();
	}
	
	public void connect(int indexA, int indexB, double weight) {
		adjacencyMatrix.get(indexA).set(indexB, weight);
	}
	
	public void connect(int indexA, int indexB) {
		connect(indexA, indexB, 0);
	}
	
	public void connect(Type elementA, Type elementB, double weight) {
		
		if(getIndexOf(elementA) == -1)
			add(elementA);
		
		if(getIndexOf(elementB) == -1)
			add(elementB);
		
		connect(getIndexOf(elementA), getIndexOf(elementB), weight);
	}
	
	public void connect(Type elementA, Type elementB) {
		
		if(getIndexOf(elementA) == -1)
			add(elementA);
		
		if(getIndexOf(elementB) == -1)
			add(elementB);
		
		connect(getIndexOf(elementA), getIndexOf(elementB), 0);
	}
	
	public void connectMutually(int indexA, int indexB, double weight) {
		connect(indexA, indexB, weight);
		connect(indexB, indexA, weight);
	}
	
	public void connectMutually(int indexA, int indexB) {
		connect(indexA, indexB);
		connect(indexB, indexA);
	}
	
	public void connectMutually(Type elementA, Type elementB, double weight) {
		
		if(getIndexOf(elementA) == -1)
			add(elementA);
		
		if(getIndexOf(elementB) == -1)
			add(elementB);
		
		connect(elementA, elementB, weight);
		connect(elementB, elementA, weight);
	}
	
	public void connectMutually(Type elementA, Type elementB) {
		
		if(getIndexOf(elementA) == -1)
			add(elementA);
		
		if(getIndexOf(elementB) == -1)
			add(elementB);
		
		connect(elementA, elementB);
		connect(elementB, elementA);
	}
	
	public void disconnect(int indexA, int indexB) {
		adjacencyMatrix.get(indexA).set(indexB, null);
	}
	
	public void disconnect(Type elementA, Type elementB) {
		
		if(getIndexOf(elementA) == -1)
			return;
		
		if(getIndexOf(elementB) == -1)
			return;
		
		disconnect(getIndexOf(elementA), getIndexOf(elementB));
	}
	
	public void disconnectMutually(int indexA, int indexB) {
		disconnect(indexA, indexB);
		disconnect(indexB, indexA);
	}
	
	public void disconnectMutually(Type elementA, Type elementB) {
		
		if(getIndexOf(elementA) == -1)
			return;
		
		if(getIndexOf(elementB) == -1)
			return;
		
		disconnect(getIndexOf(elementA), getIndexOf(elementB));
		disconnect(getIndexOf(elementB), getIndexOf(elementA));
	}
	
	public Double getConnection(int indexA, int indexB) {
		return adjacencyMatrix.get(indexA).get(indexB);
	}
	
	public Double getConnection(Type elementA, Type elementB) {
		
		if(getIndexOf(elementA) == -1)
			return null;
		
		if(getIndexOf(elementB) == -1)
			return null;
		
		return getConnection(getIndexOf(elementA), getIndexOf(elementB));
	}
	
	public boolean areConnected(int indexA, int indexB) {
		return adjacencyMatrix.get(indexA).get(indexB) != null;
	}
	
	public boolean areConnected(Type elementA, Type elementB) {
		
		if(getIndexOf(elementA) == -1)
			return false;
		
		if(getIndexOf(elementB) == -1)
			return false;
		
		return areConnected(getIndexOf(elementA), getIndexOf(elementB));
	}
	
	public int getIndexOf(Type element) {
		
		for(int i = 0; i < elements.size(); i++) {
			
			if(elements.get(i).equals(element))
				return i;
		}
		
		return -1;
	}
	
	public String toString() {
		
		String graph = "";
		
		for(int i = 0; i < elements.size(); i++)
			graph += "\t" + elements.get(i);
		
		for(int i = 0; i < elements.size(); i++) {
			
			graph += "\n" + elements.get(i);
			
			for(int j = 0; j < elements.size(); j++) {
				
				graph += "\t";
				
				if(adjacencyMatrix.get(i).get(j) == null)
					graph += "N/A";
				
				else if(adjacencyMatrix.get(i).get(j) % 1 > 0)
					graph += adjacencyMatrix.get(i).get(j);
				
				else
					graph += (int) (double) adjacencyMatrix.get(i).get(j);
			}
		}
		
		return graph;
	}
}