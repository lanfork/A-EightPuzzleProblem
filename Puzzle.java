import java.util.ArrayList;
import java.util.Scanner;

/*
 * THe main class for program
 * Prints menu to user. Reads and sets the starting state and method of solving
 * Contains Node class
 */

public class Puzzle {

	private static  Scanner scanner = new Scanner(System.in);

	public static Node goalNode;
	public static Node nodeNode;
	public static int tilesNumber = 9;

	public static Heuriatics.method the_method_selected;

	private static int[] goal_State = {1,2,3,8,0,4,7,6,5};
	private static int[] easy_State = {1,3,4,8,6,2,7,0,5};
	private static int[] medium_State = {2,8,1,0,4,3,7,6,5};
	private static int[] hard_State = {5,6,7,4,0,8,3,2,1};
	private static int[] worst_State = {5,6,7,4,0,8,3,2,1};
	

	// calls readMethod and readMode
	public static void main(String[] args) {
	
	while(true) {
		readMode();
		readMethod();
		System.out.println("\n\n0: select another method\n1: stop.");
		if(scanner.nextLine().equals("1")){
			break;
			}
		}
	}

	
	/*
	 * readMethod allows the user to choose which strategy they would like to solve the puzzle
	 * calls Straegie's Search()
	 */
	private static void readMethod() {
		
		System.out.println("Please select a strategy :\n1:h2\n2:h1");
		String scan = scanner.nextLine();

		switch (Integer.parseInt(scan)){
		
			case 1:
				the_method_selected =  Heuriatics.method.h2;
				Strategies.Search(nodeNode,the_method_selected);
				break;
			case 2:
				the_method_selected =  Heuriatics.method.h1;
				Strategies.Search(nodeNode,the_method_selected);
				break;
            default:
				System.out.println("Can not recognize your input ");
		}
	}

	
	/*
	 * readMode allows the user to choose the starting state of solving the puzzle
	 * sets the chosen state to nodeNode
	 */
	private static void readMode() {
		
		System.out.println("8 Puzzle Problem Solver with A* "
				+ "\n The goal state is 1,2,3,8,0,4,7,6,5");
		System.out.println("Select a starting state:  "
				+ "\n 1: Easy: 1,3,4,8,6,3,7,0,5"
				+ " \n 2: Medium: 2,8,1,0,4,37,6,5 "
				+ "\n 3: Hard: 2,8,1,4,6,3,0,7,5"
				+ " \n 4: Worst: 5,6,7,4,0,8,3,2,1 ");
		int mode = Integer.parseInt(scanner.nextLine());
		System.out.println("Selected mode : " + mode );

		goalNode = new Node(goal_State);
		
		switch (mode){

			case 1 :
				nodeNode = new Node(easy_State);
				break;
			case 2 :
				nodeNode = new Node(medium_State);
				break;
			case 3 :
				nodeNode = new Node(hard_State);
				break;
			case 4 : 
				nodeNode = new Node(worst_State);
				break;
			default:
				System.out.println("The starting state was not recognised");
		}
		System.out.println();
	}
}


class Node {
	

	public ArrayList<Node> successors = new ArrayList<Node>();
	public Node parentState;
	public int[] tileList = new int[Puzzle.tilesNumber];
	public int emptyTile = 0;
	public enum Direction { Left , Right, Up, Down}
	public int cost;
	public int heuristicCost;
	public int totalCost;
	public int depth = 0;
	public  String direction ;


	/*
	 * Feed the node with tiles here
	 */
	public Node(int[] list) {
		
		for (int i =0 ; i< list.length ; i++ ){
			this.tileList[i] = list[i];
		}
	}
	
	/*
	 * Make possible move to specified direction
	 * The method will check for the Puzzle boundaries within array
	 * For now assume the Puzzle is quadratic
     * The cost of the move is the value of the tile moved
	 */
	public void MakeMove(Direction direction, int [] state, int i){
		
		String nodeDirection = null;
		int newIndex = 0;
		int columns = (int) Math.sqrt(Puzzle.tilesNumber);
		boolean checkingConstraints  = false;
		int[] currentState = state.clone();
		
		switch (direction) {
		
			case Left :
			checkingConstraints = i%columns > 0 ;
				newIndex  = i-1;
				nodeDirection = "Left";
				break;
			case Right :
				checkingConstraints = i%columns < columns - 1 ;
				newIndex  = i+1;
				nodeDirection = "Right";
				break;
			case Up :
				checkingConstraints = i - columns >= 0 ;
				newIndex  = i-3;
				nodeDirection = "Up";
				break;
			case Down :
				checkingConstraints = i+columns < tileList.length ;
				newIndex  = i+3;
				nodeDirection = "Down";
				break;	
		}
		
		if(checkingConstraints) {
			int temp = currentState[newIndex];
			currentState[newIndex] = currentState[i];
			currentState[i] = temp;
			
			Node successor = new Node(currentState);
			successor.depth = depth +1;
			successor.direction = nodeDirection ;

			int cost  =  tileList[newIndex]; // the cost is the tile value
			successor.cost = cost;

			successors.add(successor);
			successor.parentState = this;
		}
	}
	
	
	/*
	 * Find empty tile and expand (step forward)
	 */
	public void expand(){
		
		for(int i = 0; i< tileList.length; i++){
			if(tileList[i] == 0){
				emptyTile = i;
			}		
		}
		MakeMove(Direction.Left, tileList,emptyTile);
		MakeMove(Direction.Right, tileList,emptyTile);
		MakeMove(Direction.Up, tileList,emptyTile);
		MakeMove(Direction.Down, tileList,emptyTile);
	}


	/*
	 * Check if the current node is the goal node
	 */
	public boolean isGoalNode(Node goalNode){

		for(int i =0; i< tileList.length;i++ ){
			if(tileList[i]  != goalNode.tileList[i]){
				return false;
			}
		}
		return true ;
	}


	/*
	 * Convert Node to a readable format
	 */
	public String toString(){

		String tiles = "";
		if(tileList.length > 0){
			for(int item:tileList){
				tiles += item + "";
			}
			return tiles;
		}
		return null;
	}

	
	public void print(){

		int num = (int) Math.sqrt(Puzzle.tilesNumber);
		int line = 0;
		if(tileList.length > 0){

			for(int i=0; i< num;i++){
				for(int j=0; j<num; j++){
					System.out.print(tileList[line] + " ");
					line++;
				}
				System.out.println();
			}
		}
		System.out.println();
		System.out.println();
	}

	
}