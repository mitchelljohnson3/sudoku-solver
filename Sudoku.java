import java.util.*;

class Sudoku {
    
    //static puzzle as a master puzzle that we change once the board is finally complete
    static char[][] pPuzz = SudokuP.puzzle();
    public static void main(String[] args) {
        char[][] puzzle = pPuzz;
        SudokuP.print();
        System.out.println();
        solve(puzzle);
    }
    
    //if the puzzle is solvable, then print it in the correct format
    public static void solve(char[][] puzzle) {
        if(puzzle == null || puzzle.length == 0){
            return;
        }
        //printing the board in the correct format
        if (solveSudoku(puzzle)) {
            boolean doubleCheck = true;
            System.out.print("[");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (j == 0 && doubleCheck == true) {System.out.print("[");
                    doubleCheck = false;}
                    else if (j == 0) {System.out.print(" [");}
                    System.out.print("'" + pPuzz[i][j] + "', ");
                    if (j == 8) {System.out.print("]");}
                    if (i != 8 && j == 8) {System.out.print(",");}
            }
                if (i != 8) {System.out.println();}
            }
            System.out.print("]");
        } else {
            System.out.print("This puzzle is not solvable.");
        }
    }
    
    public static boolean solveSudoku(char[][] puzzle){
        //for loop to get location of next slot to check
        //scan rows, columns, and 3x3 box and generate array of possible chars 
        //for loop running for all possible chars available to be placed in slot
        //  fill in that slot with the fist available char in the array
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (puzzle[i][j] == '.') {
                    char[] list = possibleChars(puzzle, i, j);
                    for (int n = 0; n < list.length; n++) {
                        if (isSpotValid(puzzle, i, j, list[n]) == true)
                        {
                            puzzle = duplicatePuzzle(puzzle, i, j, list[n]);
                            if(solveSudoku(puzzle))
                            {
                                return true;
                            }
                            else
                            {
                                //puzzle = duplicatePuzzle(puzzle, i, j, '.');
                            }
                        }
                    }
                    return false;
                }
            }
        }
        pPuzz = puzzle;
        return true;
    }   
    
    //checks to see if the given board is valid or not
    public static boolean check(char[][] puzzle) {
        int count = 0;
        int extraCount = 0;
        //checking columns
        for (int j = 0; j < 9; j ++) {
            if (isParticallyValid(puzzle, 0, j, j, 8) == true) {count++;}
        }
        if (count == 8) {extraCount++;
                         count = 0;}
                         else {return false;}
        //checking rows
        for (int f = 0; f < 9; f ++) {
            if (isParticallyValid(puzzle, f, 0, 8, f) == true) {count++;}
        }
        if (count == 8) {extraCount++;
                         count = 0;}
                         else {return false;}
        //checking boxes
        for (int q = 0; q < 9; q += 3) {
            for (int d = 2; d < 9; d += 3) {
                if (isParticallyValid(puzzle, d - 2, q, d, q + 2) == true) {count++;}
            }
        }
        if (count == 8) {extraCount++;}
        else {return false;}
        if (extraCount == 3) {return true;}
        else {return false;}
    }
    
    //checks to see if the are enclosed by the given coordinates is correct
    public static boolean isParticallyValid(char[][] puzzle, int x1, int y1,int x2,int y2){
        char[] list = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        String chars = "";
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                if (puzzle[i][j] != '.') {chars += puzzle[i][j];}
            }
        }
        //create a count of duplicates, if there are more than 0 duplicates, return false
        char[] usedChars = chars.toCharArray();
        int duplicates = 0;
        for (int g = 0; g < list.length; g++) {
            duplicates = 0;
            for (int h = 0; h < usedChars.length; h++) {
                if (usedChars[h] == list[g]) {duplicates++;}
            }
            if (duplicates > 1) {return false;}
        }
        return true;
    }
    
    //returns true if placing c at the given spot will make the board valid, false if not
    public static boolean isSpotValid(char[][] puzzle, int row, int col, char c){
        puzzle[row][col] = c;
        boolean check = false;
        //getting coordinates for boxes to check
        int xCoord = 0;
        if (row < 3) {}
        if (row < 6 && row > 2) {xCoord = 3;}
        if (row > 5) {xCoord = 6;}
        int yCoord = 0;
        if (col < 3) {}
        if (col < 6 && col > 2) {yCoord = 3;}
        if (col > 5) {yCoord = 6;}
        if ((isParticallyValid(puzzle, row, 0, row, 8) == true) && (isParticallyValid(puzzle, 0 ,col, 8, col) == true) && (isParticallyValid(puzzle,xCoord, yCoord, xCoord + 2, yCoord + 2) == true))
            {check = true;}
        return check;
    }
    
    //this method will take the puzzle and a specific slot in it and return an array of chars that could go there and still be valid
    public static char[] possibleChars(char[][] puzzle, int row, int col) {
        //possible chars array
        char[] list = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        //get row and column coordinates
        String chars = "";
        for (int i = 0; i < 9; i++) {
            if (puzzle[row][i] != '.') {chars += puzzle[row][i];}
        }
        char[] chars1 = chars.toCharArray();
        chars = "";
        for (int j = 0; j < 9; j++) {
            if (puzzle[j][col] != '.') {chars += puzzle[j][col];}
        }
        char[] chars2 = chars.toCharArray();
        chars = "";
        //get square coordinates
        int xCoord = 0;
        if (row < 3) {}
        if (row < 6 && row > 2) {xCoord = 3;}
        if (row > 5) {xCoord = 6;}
        int yCoord = 0;
        if (col < 3) {}
        if (col < 6 && col > 2) {yCoord = 3;}
        if (col > 5) {yCoord = 6;}
        //get array in square
        for (int i = xCoord; i <= xCoord + 2; i++) {
            for (int j = yCoord; j <= yCoord + 2; j++) {
                if (puzzle[i][j] != '.') {chars += puzzle[i][j];}
            }
        }
        char[] chars3 = chars.toCharArray();
        int length = chars1.length + chars2.length + chars3.length;
        char[] usedChars = new char[length];
        int b = 0;
        for (int y = 0; y < chars1.length; y++){
            usedChars[b] = chars1[y];
            b++;
        }
        for (int k = 0; k < chars2.length; k++){
            usedChars[b] = chars2[k];
            b++;
        }
        for (int n = 0; n < chars3.length; n++){
             usedChars[b] = chars3[n];
            b++;
        }
        //make array of possible chars that are valid in that spot
        String newChars = "";
        boolean alreadyThere = false;
        for (int z = 0; z < list.length; z++) {
            for (int m = 0; m < usedChars.length; m++) {
                if (usedChars[m] == list[z]) {alreadyThere = true;}
            }
            if (alreadyThere == false) {newChars += list[z];}
            alreadyThere = false;
        }
        char[] possChars = newChars.toCharArray();
        return possChars;
    }
    
    //this method returns the puzzle with the given char c placed at the given coordinate
    public static char[][] duplicatePuzzle(char[][] oPuzzle, int row, int col, char c)
    {
        char[][] nPuzzle = oPuzzle;
        nPuzzle[row][col] = c;
        return nPuzzle;
    }
}
