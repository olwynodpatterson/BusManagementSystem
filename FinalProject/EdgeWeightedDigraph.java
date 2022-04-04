//Class to handle all graph searching elements of the project
//Adapted from EdgeWeightedDigraph from textbook
public class EdgeWeightedDigraph  {

	//Find shortest path between 2 bus stops 
	public void buildGraph() {

	}

	private final int V; // number of vertices
	private int E; // number of edges
	private Bag<DirectedEdge>[] adj; // adjacency lists
	public EdgeWeightedDigraph(int V)
	{
		this.V = V;
		this.E = 0;
		adj = (Bag<DirectedEdge>[]) new Bag[V];
		for (int v = 0; v < V; v++) 
			adj[v] = new Bag<DirectedEdge>();
	}
	public EdgeWeightedDigraph(In in)
	// See Exercise 4.4.2.
	public int V() { return V; }
	public int E() { return E; }
	public void addEdge(DirectedEdge e)
	{
		adj[e.from()].add(e);
		E++;
	}
	public Iterable<Edge> adj(int v)
	{ return adj[v]; }
	public Iterable<DirectedEdge> edges()
	{
		Bag<DirectedEdge> bag = new Bag<DirectedEdge>();
		for (int v = 0; v < V; v++)
			for (DirectedEdge e : adj[v])
				bag.add(e);
		return bag;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
