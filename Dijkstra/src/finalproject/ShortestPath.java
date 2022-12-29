package finalproject;


import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

public class ShortestPath extends PathFindingService {

    //TODO level 4: find distance prioritized path
    public ShortestPath(Tile start) {
        super(start);
        this.generateGraph();
    }

	@Override
       public void generateGraph() {
        super.g = new Graph(GraphTraversal.BFS(super.source));
        for (Tile t : super.g.getGraph_node()) {
            for (Tile t2 : t.neighbors) {
                if(t instanceof MetroTile && t2 instanceof MetroTile){
                    super.g.addEdge(t, t2, ((MetroTile) t2).metroDistanceCost);
                }
                else if (t2.isWalkable()) {
                    super.g.addEdge(t, t2, t2.distanceCost);
                }
            }
        }
    }


}