import java.util.ArrayList;

/**
 * Class implementing Dijkstra's shortest path algorithm
 * for directed simple (i.e. without loops) graphs
 * with non-negative weighted edges.
 * 
 * For more info: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm 
 */
public class Dijkstra {
	private Double[][] adjMatrix;			//Adjacency matrix
	
	private Double[] distances;				//Vector of estimated distances
	private boolean[] visited;				//Vector of visited nodes
	private Integer[] prev;					//Previous node in the shortest path
	private Integer nVertices;				//Number of vertices of the graph
	
	private boolean error = false;			//Flag: if an error happened
	private boolean dijkstraExec;			//Flag: if the algorithm was executed
	
	/**
	 * Constructor of the Dijkstra class.
	 * This function saves the adjacency matrix of a 
	 * directed simple graph with weighted edges.
	 * 
	 * The nodes are labeled by their position in the
	 * adjacency matrix. That is, the vertex whose
	 * outgoing edges are saved in the ith row of adjMatrix
	 * is the vertex i.
	 * 
	 * @param adjMatrix Square matrix that represents
	 * the adjacency matrix of the graph. The entry (i,j)
	 * of adjMatrix represents the weight of the edge that
	 * goes from vertex i to vertex j. The weights must be
	 * positive. A negative entry in the position (i,j)
	 * means that there is no edge between the vertices i and j.
	 *  
	 * @param nVertices Non-negative number of vertices of the graph,
	 * that is, the order of the adjacency matrix. 
	 */
	public Dijkstra(Double[][] adjMatrix, Integer nVertices){
		this.adjMatrix = adjMatrix;
		this.nVertices = nVertices;
	}
	
	/**
	 * Private function that initializes all the data
	 * structures as required by the Dijkstra's algorithm.
	 */
	private void initializeDataStructures() {
		// Creates the data structures
		distances = new Double[nVertices];
		visited = new boolean[nVertices];
		prev = new Integer[nVertices];
		
		// Initialization of data structures
		for(int i=0; i<nVertices; i++) {
			distances[i] = Double.POSITIVE_INFINITY;	//The estimated distance is infinity
			visited[i] = false;							//No node has been visited
			prev[i] = -1;								//No shortest path
		}
		
		return;
	}
	
	/**
	 * Returns the next vertex to be analyzed by the
	 * Dijkstra algorithm. As required, that should
	 * be the unvisited vertex with smallest expected cost.
	 * 
	 * @return Next vertex to be explored.
	 */
	private Integer nextCur() {
		Integer next = -1;
		Double cost = Double.POSITIVE_INFINITY;
		
		for(int i=0; i<nVertices; i++) {
			if(!visited[i] && distances[i]<cost) {
				next = i;
				cost = distances[i];
			}
		}
		
		return next;
	}
	
	/**
	 * Main call of Dijkstra's shortest path algorithm.
	 * Given a directed simple graph with non-negative
	 * weighted edges, an initial vertex ini and a
	 * final vertex end, this function computes the
	 * shortest path between ini and end. 
	 * 
	 * The minimum distance is returned by the function.
	 * The list of nodes that form the shortest path
	 * can be recovered by calling the function getPath
	 * after the call to this function.
	 * 
	 * If there is no path between ini and end, the function
	 * returns Double.POSITIVE_INFINITY and no shortest path
	 * is computed.
	 * 
	 * @param ini Initial vertex
	 * @param end Final vertex
	 * @return Cost of the shortest path between ini and end.
	 */
	public Double computeShortestPath(Integer ini, Integer end) {
		Double newDistance;
		initializeDataStructures();
		
		Integer cur = ini;	//Starts in the initial vertex
		distances[ini]=0.0;	//The cost of the path from ini to itself is 0
		while(!visited[end]) {
			//For every vertex in the node
			for(int neigh=0; neigh<nVertices; neigh++) {
				//If the vertex is connected with the current node and
				//has not been visited
				if(adjMatrix[cur][neigh]>0 && !visited[neigh]) {
					//Computes the distance of going to neigh via cur.
					newDistance = distances[cur] + adjMatrix[cur][neigh];
					
					//If the new distance is better, update it.
					if(distances[neigh] > newDistance) {
						distances[neigh] = newDistance;
						prev[neigh] = cur;
					}
				}
			}
			
			visited[cur] = true; 	//Mark the current node as visited
			cur = nextCur(); 		//Choose a new current node
		}
		
		dijkstraExec = true;	//Dijkstra has been executed
		return distances[end];
	}
	
	/**
	 * Get the shortest path computed by Dijkstra's algorithm.
	 * Returns an ArrayList with the ordered list of nodes that
	 * form the path, from the initial vertex to the final vertex.
	 * 
	 * If there are two possible paths with minimum cost, this
	 * function returns the one with less number of edges.
	 * 
	 * If Dijkstra's algorithm has not been previously executed,
	 * the function returns null and sets that an error occurred.
	 * 
	 * @param ini Initial vertex of Dijkstra's algorithm
	 * @param end Final vertex of Dijkstra's algorithm
	 * @return ArrayList with the shortest path.
	 */
	public ArrayList<Integer> getPath(Integer ini, Integer end) {
		if(!dijkstraExec) {
			error = true;
			return null;
		}
		
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		//Runs over the path from end to ini by
		//jumping through the previous nodes.
		Integer cur = end;
		path.add(cur);
		while(cur != ini) {
			path.add(prev[cur]);
			cur = prev[cur];
		}
		
		return path;
	}
	
	/**
	 * Checks if an error has occurred in the
	 * execution of the class.
	 * 
	 * @return True if an error happened, false if not.
	 */
	public boolean hasErrorHappened() {
		return error;
	}
}
