package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class PathFindingService {
	Tile source;
	Graph g;
    TilePriorityQ pq;


    public PathFindingService(Tile start) {
        this.source = start;
    }

	public abstract void generateGraph();

    //TODO level 4: Implement basic dijkstra's algorithm to find a path to the final unknown destination
    public ArrayList<Tile> findPath(Tile startNode) {
        initialization(g.graph_node, startNode);
        pq= new TilePriorityQ(g.graph_node);
        ArrayList<Tile> path = new ArrayList<>();
        Tile cur= startNode;

        while (!pq.queue.isEmpty()) {

            Tile u = pq.removeMin();
            for (Tile v: g.getNeighbors(u)) {
                relax(pq,u, v);
                bubbleSort(pq);
                if (v.isDestination) {
                    cur=v;
                    break;
                }
                if(cur != startNode){
                    break;
                }
            }
        }

        while(cur != null){
            path.add(0, cur);
            cur = cur.predecessor;
        }

        return path;
    }



    //TODO level 5: Implement basic dijkstra's algorithm to path find to a known destination
    public ArrayList<Tile> findPath(Tile start, Tile end) {
        initialization(g.graph_node, start);
        pq= new TilePriorityQ(g.graph_node);
        ArrayList<Tile> path = new ArrayList<>();

        while (!pq.queue.isEmpty()) {
            Tile u = pq.removeMin();
            for (Tile v: g.getNeighbors(u)) {
                relax(pq, u, v);
                bubbleSort(pq);
            }
            if(u == end){
                break;
            }
        }
        while(end != null ) {
            path.add(0, end);
            end = end.predecessor;
        }

        return path;
    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to the final destination passing through given waypoints
    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {
        ArrayList<Tile> path = new ArrayList<>();
        Tile destination = null;
        Tile current = start;

        if(waypoints.isEmpty()){
            for(Tile t: g.graph_node){
                if(t.isDestination){
                    destination= t;
                    break;
                }
            }
            path= findPath(start,destination);
            return path;
        }

        for (Tile waypoint : waypoints) {
            ArrayList<Tile> shortestPath = findPath(current, waypoint);
            path.addAll(shortestPath);
            current = waypoint;
        }


        for(Tile t: g.graph_node){
            if(t.isDestination){
                destination= t;
                break;
            }
        }

        if (destination != null) {
            ArrayList<Tile> shortestPath = findPath(current, destination);
            path.addAll(shortestPath);
        }

        for(Tile t: waypoints){
            path.remove(t);
        }

        return path;
    }

    //DIJKSTRA ALGORITHM:

    // STEP 1: INITIALIZATION
    private void initialization(ArrayList<Tile> V, Tile s){
        for(Tile v: V){
            v.costEstimate= Double.POSITIVE_INFINITY;
            v.predecessor= null;
        }
        s.costEstimate= 0;
    }


    // STEP 2: RELAXING
    private void relax(TilePriorityQ q, Tile start , Tile end) {
        Graph.Edge edge= null;
        for(Graph.Edge curEdge: g.edges){
            if(curEdge.origin == start && curEdge.destination == end){
                edge=curEdge;
                break;
            }
        }

        if (edge.destination.costEstimate > edge.origin.costEstimate + edge.weight) {
            q.updateKeys(edge.destination, edge.origin, edge.origin.costEstimate+edge.weight);
        }
    }

    private void bubbleSort(TilePriorityQ pq){
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < pq.queue.size() - 1; i++) {
                if (pq.queue.get(i).costEstimate > pq.queue.get(i + 1).costEstimate) {
                    Tile temp = pq.queue.get(i);
                    pq.queue.set(i, pq.queue.get(i + 1));
                    pq.queue.set(i + 1, temp);
                    sorted = false;
                }
            }
        }
    }


}











// STEP 3: DIJKSTRA
//    private void dijkstra(ArrayList<Tile> V, ArrayList<Graph.Edge> E, Tile source ) {
//
//        initialization(V, source);
//        LinkedList<Tile> S = new LinkedList<>();
//
//        while (!pq.queue.isEmpty()) {
//            Tile u = pq.removeMin();
//            S.add(u);
//
//            for (Tile v : u.neighbors) {
//                relax(u, v);
//                if(v.isDestination){
//                    break;
//                }
//            }
//        }
//    }


//    boolean y = false;
//            for (Tile x : pq.queue) {
//                if (x.isDestination == true) {
//                    y = true;
//                    break;
//                }
//            }
//            System.out.println(y);



//    public ArrayList<Tile> findPath(Tile startNode) {
//
//        dijkstra(g.getGraph_node(), g.getAllEdges(), startNode);
//        ArrayList<Tile> path= new ArrayList<>();
//        Tile cur = startNode;
//        path.add(cur);
//        for(Tile t: g.graph_node){
//            if(t.isDestination == true){
//                cur=t;
//                break;
//            }
//        }
//        while(!cur.isStart && cur != null) {
//            cur = cur.predecessor;
//            path.add(cur);
//        }
//
//        return path;
//    }



// BILLY
//    public ArrayList<Tile> findPath(Tile startNode) {
//        initialization(g.graph_node, startNode);
//        TilePriorityQ pq= new TilePriorityQ(this.g.graph_node);
//        Tile temp= null;
//        Tile current= null;
//
//        while(pq.queue.size() != 0){
//            current= pq.removeMin();
//            if(current.isDestination){
//                temp = current;
//            }
//            for(Tile neighbor: g.getNeighbors(current)){
//                relax(pq, current, neighbor);
//                bubbleSort(pq);
//                if(neighbor.isDestination){
//                    temp= neighbor;
//                    break;
//                }
//            }
//            if(temp!= null){
//                break;
//            }
//        }
//        ArrayList<Tile> path = new ArrayList<>();
//        while(temp != null){
//            path.add(0, temp);
//            temp= temp.predecessor;
//        }
//        return path;
//
//    }





//    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {
//       ArrayList<Tile> path= new ArrayList<>();
//       Tile dest= null;
//
//        for(Tile t: g.graph_node){
//            if(t.isDestination){
//                dest= t;
//                break;
//            }
//        }
//        path.addAll(findPath(start, waypoints.get(0)));
//        Tile cur= waypoints.get(0);
//        if(waypoints.size()==1){
//            path.addAll(findPath(cur, dest));
//        }
//
//        else {
//            for (int i = 1; i <waypoints.size(); ++i) {
//                path.addAll(findPath(cur, waypoints.get(i-1)));
//                cur = waypoints.get(i-1);
//            }
//        }
//
//        //Remove duplicates items:
//        Set<Tile> set = new HashSet<>(path);
//        path = new ArrayList<>(set);
//        System.out.println(path.toString());
//        return path;
//    }

