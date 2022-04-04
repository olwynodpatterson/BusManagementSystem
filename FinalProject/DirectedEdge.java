//adapted from DirectedEdge class 

public class DirectedEdge {
	private final int v;			//edge source
	private final int w;			//edge target
	private final double weight;	//edge weight	

	public DirectedEdge(int v, int w, double weight) {
		if (v < 0) throw new IllegalArgumentException("Vertex names must be non-negative integers");
		if (w < 0) throw new IllegalArgumentException("Vertex names must be non-negative integers");
		if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
		this.v = v;
		this.w = w;
		this.weight = weight;
	}
}
