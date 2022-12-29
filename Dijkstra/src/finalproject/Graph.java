package finalproject;

import java.util.ArrayList;
import java.util.HashSet;

import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.*;

public class Graph {

	// TODO level 2: Add fields that can help you implement this data type
    public ArrayList<Tile> graph_node;
    public ArrayList<Edge> edges;


    // TODO level 2: initialize and assign all variables inside the constructor
	public Graph(ArrayList<Tile> vertices) {
		this.graph_node= vertices;
        this.edges= new ArrayList<>();
	}


    // TODO level 2: add an edge to the graph
    public void addEdge(Tile origin, Tile destination, double weight) {
        Edge e= new Edge(origin, destination, weight);
        this.edges.add(e);
        origin.addNeighbor(destination);
    }


    // TODO level 2: return a list of all edges in the graph
    public ArrayList<Edge> getAllEdges() {
        return this.edges;
    }


    // TODO level 2: return list of tiles adjacent to t
    public ArrayList<Tile> getNeighbors(Tile t) {
        ArrayList<Tile> list = new ArrayList<>();
        for(Edge e : edges){
            if(e.origin==t){
                if(e.destination.isWalkable())
                    list.add(e.destination);
            }
        }
        return list;
    }


    public ArrayList<Tile> getGraph_node(){
        return this.graph_node;
    }


    // TODO level 2: return total cost for the input path
    public double computePathCost(ArrayList<Tile> path) {
        double path_cost = 0.0;

        for(int i = 0; i < path.size() - 1; i++) {
            Tile cur = path.get(i);
            Tile next = path.get(i + 1);
            for(Edge e: edges){
                if(e.getStart() == cur && e.getEnd() == next){
                    path_cost=path_cost+e.weight;
                }
            }
        }
        return path_cost;
    }


    public static class Edge{
    	Tile origin;
    	Tile destination;
    	double weight;

        // TODO level 2: initialize appropriate fields
        public Edge(Tile s, Tile d, double cost) {
            this.origin = s;
            this.destination = d;
            this.weight = cost;
        }


        // TODO level 2: getter function 1
        public Tile getStart() {
            return this.origin;
        }


        // TODO level 2: getter function 2
        public Tile getEnd() {
            return this.destination;
        }

    }

}


//    public ArrayList<Tile> getNeighbors(Tile t) {
//        ArrayList<Tile> list = new ArrayList<>();
//        for(Tile tile : t.neighbors){
//            if(tile.isWalkable(){
//                list.add(tile);
//            }
//        }
//        return list;
//    }