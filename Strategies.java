import java.util.*;

/*
 * Class for possible search Strategies
 */
public class Strategies {

	/*
	 * Search function strategy
	 * This will be used for A* solutions
	 */
	public static void Search(Node node,Heuriatics.method heurType) {
		
		int time = 0;
		Set<String> closedList = new HashSet<String>();
		PriorityQueue openList =new PriorityQueue(10, new compareTheNodes());
		Node currentNode = node;
		long startTime = System.currentTimeMillis();
		int maxOpenSize = 0;
		int dequeued = 0;

		while ( !currentNode.isGoalNode(Puzzle.goalNode))  {

			closedList.add(currentNode.toString());
			currentNode.expand();
			for (Node successor : currentNode.successors) {
				if (closedList.contains(successor.toString())) {continue;}
				closedList.add(successor.toString());

				//Append heuristics cost to node
				successor.heuristicCost = new Heuriatics(successor, Puzzle.goalNode, heurType).getDistance();
				successor.totalCost = successor.cost + currentNode.totalCost + successor.heuristicCost;

				openList.add(successor);

				//Get maximum PQ size
				if(openList.size() > maxOpenSize){
					maxOpenSize = openList.size();
				}
			}

			currentNode = (Node) openList.poll();
			dequeued +=1;
			time += 1;
		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		PrintResults(currentNode,time,"Search",elapsedTime,maxOpenSize,dequeued);

	}
	

	/*
	 *	comparator for openList to compare nodes
	 */
	public static class compareTheNodes implements Comparator<Node>
	{
		public int compare( Node x, Node y )
		{
			return  x.totalCost - y.totalCost;
		}
	}


	/*
	 * Print stats for selected statistics
	 */
	public static void PrintResults(Node node, int iterations, String stats, long timeInMiliseconds, int maxQueueSize, int dequeued) {

		System.out.println("Solution found:");
		System.out.println("Length: " + node.depth);
		System.out.println("Cost: " + node.totalCost);
		System.out.println("Time: " + dequeued);
		System.out.println("Space: " + maxQueueSize);
		System.out.println("Overall statistics for " + stats);
		System.out.println("Iterations: " + iterations);
		System.out.println("Time taken in ms: " + timeInMiliseconds);
	
		List<Node> path = new ArrayList<Node>();
		Node current = node;
		while (current != null) {
			path.add(current);
			current = current.parentState;
		}
	
		System.out.println("Steps:");
		for (int i = path.size() - 1; i >= 0; i--) {
			Node n = path.get(i);
			System.out.println("Direction: " + n.direction + " move, node depth: " + n.depth +
				", node cost: " + n.cost + ", total cost: " + n.totalCost +
				", heuristic estimation: " + n.heuristicCost);
			n.print();
		}
	}
	

	
	
}
