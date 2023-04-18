/*
 * Heuristics object  to calculate estimated cost
 */
public class Heuriatics {

    public enum method { h2, h1}
    private Node node;
    private Node goalNode;
    private int estimated_goalNode_cost;

    /*
     * Constructor to pass node parameters
     */
    Heuriatics(Node node, Node goalNode , method type){

        this.node = node;
        this.goalNode = goalNode;

        switch (type){

            case h2:
            	estimated_goalNode_cost =    h2();
                break;
            case h1:
            	estimated_goalNode_cost =    h1();
                break;
            default: estimated_goalNode_cost = 0;
        }
    }

    public int getDistance() {
        return estimated_goalNode_cost;
    }

    /*
     * Hamming Distance
     * Count number of misplaced titles between goalNode and current Node for Heuristics
     * h1 : A* search using the heuristic function h*(n), where h*(n) is thenumber of
     *        tiles out of place (not counting the blank).
     */
    public int h1(){
       
        int distance = 0;
        for (int i = 0; i < this.node.tileList.length; i += 1)
            if (this.node.tileList[i] != goalNode.tileList[i])
                distance ++;
        return distance;
    }


    /*
     * Calculate AstarManhattan distance for tiles
     * Check for the Manhattan distance between current state abd the goalNode state
     *   h2 : A* search using the Manhattan heuristic function.
     */
    public int h2(){
      
        int distance = 0;
        int row_index = 0;
        int col_index  = 0;
        int row_index_goalNode = 0;
        int col_index_goalNode = 0;
        
        for (int i = 0; i < this.node.tileList.length; i += 1) {
        	row_index = i / Puzzle.tilesNumber;
        	col_index = i % Puzzle.tilesNumber;
            for (int j = 0; j < goalNode.tileList.length; j += 1) {
            	row_index_goalNode = j / Puzzle.tilesNumber;
            	col_index_goalNode = j % Puzzle.tilesNumber;
                if (this.node.tileList[i] == goalNode.tileList[j])
                    distance = distance + ((Math.abs(col_index - col_index_goalNode)) + Math.abs(row_index + row_index_goalNode));
            }
        }
        return  distance ;
    }


}
