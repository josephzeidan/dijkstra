package finalproject;


import java.util.ArrayList;
import java.util.LinkedList;

import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

public class SafestShortestPath extends ShortestPath {
	public int health;
	public Graph costGraph;
	public Graph damageGraph;
	public Graph aggregatedGraph;

	//TODO level 8: finish class for finding the safest shortest path with given health constraint
	public SafestShortestPath(Tile start, int health) {
		super(start);
		this.health = health;
		super.generateGraph();
	}


	public void generateGraph() {

		ArrayList<Tile> traversal = GraphTraversal.BFS(this.source);

		this.costGraph = new Graph(traversal);
		this.damageGraph = new Graph(traversal);
		this.aggregatedGraph = new Graph(traversal);


		for (Tile t: traversal) {
			for (Tile t2 : t.neighbors) {
				if(t instanceof MetroTile && t2 instanceof MetroTile){
					this.costGraph.addEdge(t, t2, ((MetroTile) t2).metroDistanceCost);
					this.damageGraph.addEdge(t, t2, t2.damageCost);
					this.aggregatedGraph.addEdge(t, t2, t2.damageCost);
				}
				else if (t2.isWalkable()) {
					this.costGraph.addEdge(t, t2, t2.distanceCost);
					this.damageGraph.addEdge(t, t2, t2.damageCost);
					this.aggregatedGraph.addEdge(t, t2, t2.damageCost);
				}
			}
		}

	}

	@Override
	public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {

			double damageCost_pc = 0;
			double damageCost_pd = 0;
			double distanceCost_pc = 0;
			double distanceCost_pd = 0;
			super.g = costGraph;
			ArrayList<Tile> pc = new ArrayList<>();
			pc = super.findPath(start, waypoints);
			damageCost_pc = damageGraph.computePathCost(pc);
			distanceCost_pc = costGraph.computePathCost(pc);
			if (damageCost_pc < health) {
				return pc;
			}


			super.g = damageGraph;
			ArrayList<Tile> pd = new ArrayList<>();
			pd = super.findPath(start, waypoints);
			damageCost_pd = damageGraph.computePathCost(pd);
			distanceCost_pd = costGraph.computePathCost(pd);
			if (damageCost_pd > health) {
				return null;
			}


			double multiplier = 0;
			multiplier = (distanceCost_pc - distanceCost_pd) / (damageCost_pd - damageCost_pc);

			for (Graph.Edge e : aggregatedGraph.edges) {
				double d = e.destination.distanceCost;
				double c = e.destination.damageCost;
				double aggregated_cost = c + multiplier * d;
				e.weight = aggregated_cost;
			}

			super.g = aggregatedGraph;
			ArrayList<Tile> pr = new ArrayList<>();
			pr = super.findPath(start, waypoints);
			double aggregatCost_pr = 0;
			aggregatCost_pr = aggregatedGraph.computePathCost(pr);
			double damageCost_pr = 0;
			damageCost_pr = damageGraph.computePathCost(pr);

			if (aggregatCost_pr == distanceCost_pc) {
				return pd;
			} else if (damageCost_pr <= health) {
				pd = pr;
			} else {
				pc = pr;
			}

			return null;
	}

}



//	public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {
//		super.g= costGraph;
//		ShortestPath dist= new ShortestPath(start);
//		ArrayList<Tile> pc= new ArrayList<>();
//		pc= super.findPath(start, waypoints);
//		double damageCost= 0;
//		damageCost=damageGraph.computePathCost(pc);
//		if(damageCost< health){
//			return pc;
//		}
//
//		super.g= damageGraph;
//		PathFindingService dam= new ShortestPath(start);
//		ArrayList<Tile> pd= new ArrayList<>();
//		pd= super.findPath(start, waypoints);
//		damageCost=0;
//		damageCost=damageGraph.computePathCost(pd);
//		System.out.println(damageCost);
//		if(damageCost> health){
//			return null;
//		}
//		return pc;
//	}
