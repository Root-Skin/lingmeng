package codeWar02.codeWar0219;

/**
 * @author skin
 * @createTime 2021年02月19日
 * @Description
 * Write a function validSolution/ValidateSolution/valid_solution() that accepts a 2D array representing a
 * Sudoku board, and returns true if it is a valid solution, or false otherwise.
 * The cells of the sudoku board may also contain 0's, which will represent empty cells.
 * Boards containing one or more zeroes are considered to be invalid solutions.
 *
 * The board is always 9 cells by 9 cells, and every cell only contains integers from 0 to 9.
 */
public class SudokuSolutionValidator {
    public static void main(String[] args) {
        int[][] sudoku
                = {
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {2, 3, 1, 5, 6, 4, 8, 9, 7},
                {3, 1, 2, 6, 4, 5, 9, 7, 8},
                {4, 5, 6, 7, 8, 9, 1, 2, 3},
                {5, 6, 4, 8, 9, 7, 2, 3, 1},
                {6, 4, 5, 9, 7, 8, 3, 1, 2},
                {7, 8, 9, 1, 2, 3, 4, 5, 6},
                {8, 9, 7, 2, 3, 1, 5, 6, 4},
                {9, 7, 8, 3, 1, 2, 6, 4, 5}
        };
        System.out.println(check(sudoku));
    }
    public static boolean check(int[][] sudoku) {
        //do your magic
//        for(int i =0;i<9;i++){
//            TreeSet result = new TreeSet();
//            for(int j =0;j<9;j++){
//                if(sudoku[i][j] ==0){
//                    return false;
//                }else{
//                    result.add(sudoku[i][j]);
//                }
//            }
//            if(result.size() !=9){
//                return false;
//            }
//        }
        return true;

    }
}
