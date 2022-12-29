package finalproject;

import java.util.ArrayList;
import java.util.Arrays;


import finalproject.system.Tile;

public class TilePriorityQ {
	// TODO level 3: Add fields that can help you implement this data type
	public ArrayList<Tile> queue;


	// TODO level 3: implement the constructor for the priority queue
	public TilePriorityQ(ArrayList<Tile> vertices) {
		queue = new ArrayList<>();
		for (int k = 0; k < vertices.size(); k++) {
			buildHeap(vertices.get(k));
		}
	}

	public TilePriorityQ() {
		queue = new ArrayList<>();
	}


	// private method that will add the tile to the queue while preserving the min heap property
	private void buildHeap(Tile t) {

		//EDGE CASE: size is 0
		if (queue.size() == 0) {
			queue.add(t);
		} else {
			queue.add(t);
			int i = queue.size() - 1;
			int parent = (i - 1) / 2;
			while (i > 0 && queue.get(i).costEstimate < queue.get(parent).costEstimate) {
				swap(i, parent);
				i = parent;
				parent = (i - 1) / 2;
			}
		}
	}

	public void downHeap(int index) {
		int left = 2 * index + 1;
		int right = 2 * index + 2;
		int smallest = index;

		// If the min is only in the left
		if (left < queue.size() && right < queue.size()) {

			if (queue.get(left).costEstimate < queue.get(smallest).costEstimate && queue.get(right).costEstimate > queue.get(smallest).costEstimate) {
				smallest = left;
			} else if (queue.get(left).costEstimate > queue.get(smallest).costEstimate && queue.get(right).costEstimate < queue.get(smallest).costEstimate) {
				smallest = left;
			} else if (queue.get(left).costEstimate < queue.get(smallest).costEstimate && queue.get(right).costEstimate < queue.get(smallest).costEstimate) {
				if (queue.get(left).costEstimate <= queue.get(right).costEstimate) {
					smallest = left;
				} else
					smallest = right;
			}
		}

		// If the smallest element is not the current element, swap the elements and continue downheaping
		if (smallest != index) {
			swap(index, smallest);
			downHeap(smallest);
		}
	}

	private void swap(int i, int j) {
		Tile temp = queue.get(i);
		queue.set(i, queue.get(j));
		queue.set(j, temp);
	}

	// TODO level 3: implement remove min as seen in class
	public Tile removeMin() {
		Tile temp = queue.get(0);
		queue.set(0, queue.get(queue.size() - 1));
		queue.remove(queue.size() - 1);
		downHeap(0);
		return temp;
	}

	// TODO level 3: implement updateKeys as described in the pdf
	public void updateKeys(Tile t, Tile newPred, double newEstimate) {
		if (queue.contains(t)) {
			t.predecessor = newPred;
			t.costEstimate = newEstimate;
			queue.remove(t);
			buildHeap(t);
			downHeap(queue.indexOf(t));
		}
	}

}

//	public static void main(String[] args) {
//		Tile vertex1 = new DesertTile();
//		Tile vertex2 = new DesertTile();
//		Tile vertex3 = new PlainTile();
//		Tile vertex4 = new PlainTile();
//		vertex1.costEstimate = 40;
//		vertex2.costEstimate = 30;
//		vertex3.costEstimate = 20;
//		vertex4.costEstimate = 10;
//
//		ArrayList<Tile> vertices = new ArrayList<>();
//		vertices.add(vertex1);
//		vertices.add(vertex2);
//		vertices.add(vertex3);
//		vertices.add(vertex4);
//		TilePriorityQ queue_array = new TilePriorityQ(vertices);
//
//
//		for (Tile tile : queue_array.queue) {
//			if (tile == null) continue;
//			System.out.println(tile.costEstimate);
//		}
//
//		System.out.println("\n\n\n\n\n");
//
//		queue_array.updateKeys(vertex1, vertex2, 25);
//
//		for (Tile tile : queue_array.queue) {
//			if (tile == null) {
//				continue;
//			}
//			System.out.println(tile.costEstimate);
//		}
//
//
//		System.out.println("\n\n\n\n\n");
//		queue_array.removeMin();
//
//		for (Tile tile : queue_array.queue) {
//			if (tile == null) continue;
//			System.out.println(tile.costEstimate);
//		}
//
//		System.out.println("\n\n\n\n\n");
//
//		queue_array.updateKeys(vertex1, vertex2, 10);
//
//		for (Tile tile : queue_array.queue) {
//			if (tile == null) continue;
//			System.out.println(tile.costEstimate);
//		}
//	}