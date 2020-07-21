package model;
import model.Bomb;

public class Board {
    private final int[][] board;
    private int row;
    private int col;

    public Board(Bomb[] wid, int row, int col){
        this.row = row;
        this.col = col;
        board = new int[row][col];
        complete_matrix(wid);
    }

    public final int[][] get_board(){
        return board;
    }

    public final int get_number_of_rows(){
        return row;
    }

    public final int get_number_of_cols(){
        return col;
    }

    public final int get_cell(int line, int col){
        return this.board[line][col];
    }

    public void set_cell(int line, int col, int target){
        board[line][col] = target;
    }

    private void complete_matrix(Bomb[] bd){
        for(var el: bd){
            board[el.get_line()][el.get_column()] = 9;
            if(el.get_line() == 0){
                if(el.get_column() == 0){
                    board[el.get_line()][el.get_column() + 1] ++;
                    board[el.get_line() + 1][el.get_column() + 1] ++;
                    board[el.get_line() + 1][el.get_column()] ++;
                }
                else if(el.get_column() == col - 1){
                    board[el.get_line()][el.get_column() - 1] ++;
                    board[el.get_line() + 1][el.get_column() - 1] ++;
                    board[el.get_line() + 1][el.get_column()] ++;
                }
                else{
                    board[el.get_line()][el.get_column() - 1] ++;
                    board[el.get_line() + 1][el.get_column() - 1] ++;
                    board[el.get_line() + 1][el.get_column()] ++;
                    board[el.get_line()][el.get_column() + 1] ++;
                    board[el.get_line() + 1][el.get_column() + 1] ++;
                }
            }
            else if(el.get_line() == row - 1){
                if(el.get_column() == 0){
                    board[el.get_line()][el.get_column() + 1] ++;
                    board[el.get_line() - 1][el.get_column() + 1] ++;
                    board[el.get_line() - 1][el.get_column()] ++;
                }
                else if(el.get_column() == col - 1){
                    board[el.get_line()][el.get_column() - 1] ++;
                    board[el.get_line() - 1][el.get_column() - 1] ++;
                    board[el.get_line() - 1][el.get_column()] ++;
                }
                else{
                    board[el.get_line()][el.get_column() - 1] ++;
                    board[el.get_line() - 1][el.get_column() - 1] ++;
                    board[el.get_line() - 1][el.get_column()] ++;
                    board[el.get_line()][el.get_column() + 1] ++;
                    board[el.get_line() - 1][el.get_column() + 1] ++;
                }
            }
            else if(el.get_column() == 0){
                board[el.get_line()][el.get_column() + 1]++;
                board[el.get_line() + 1][el.get_column() + 1]++;
                board[el.get_line() - 1][el.get_column() + 1]++;
                board[el.get_line() - 1][el.get_column()]++;
                board[el.get_line() + 1][el.get_column()]++;
            }
            else if(el.get_column() == col - 1){
                board[el.get_line()][el.get_column() - 1]++;
                board[el.get_line() + 1][el.get_column() - 1]++;
                board[el.get_line() - 1][el.get_column() - 1]++;
                board[el.get_line() - 1][el.get_column()]++;
                board[el.get_line() + 1][el.get_column()]++;
            }
            else{
                board[el.get_line()][el.get_column() - 1]++;
                board[el.get_line() + 1][el.get_column() - 1]++;
                board[el.get_line() - 1][el.get_column() - 1]++;
                board[el.get_line() - 1][el.get_column()]++;
                board[el.get_line() + 1][el.get_column()]++;
                board[el.get_line()][el.get_column() + 1]++;
                board[el.get_line() - 1][el.get_column() + 1]++;
                board[el.get_line() + 1][el.get_column() + 1]++;
            }
        }
    }
}
