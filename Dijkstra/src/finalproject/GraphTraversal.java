package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphTraversal {

	//TODO level 1: implement BFS traversal starting from s
	public static ArrayList<Tile> BFS(Tile s) {

		LinkedList<Tile> queue = new LinkedList<>();
		ArrayList<Tile> path = new ArrayList<>();

		s.setVisited(true);
		queue.add(s);

		while (!queue.isEmpty()) {
			Tile current = queue.remove();

			ArrayList<Tile> neighbors = current.neighbors;
			for (Tile neighbor : neighbors) {
				if (!neighbor.isVisited() && neighbor.isWalkable()) {
					neighbor.setVisited(true);
					queue.add(neighbor);
				}
			}
			path.add(current);
		}

		return path;
	}


	//TODO level 1: implement DFS traversal starting from s
	public static ArrayList<Tile> DFS(Tile s) {

		ArrayList<Tile> stack = new ArrayList<>();
		ArrayList<Tile> path = new ArrayList<>();

		s.setVisited(true);
		stack.add(s);

		while (!stack.isEmpty()) {
			Tile current = stack.remove(stack.size()-1);
			ArrayList<Tile> neighbors = current.neighbors;
			for (Tile neighbor: neighbors) {
				if (!neighbor.isVisited() && neighbor.isWalkable()) {
					neighbor.setVisited(true);
					stack.add(neighbor);
				}
			}
			path.add(current);
		}
		return path;
	}

}



