package controller;
import model.Board;
import repo.Repo;

public class Controller {
    private final Repo r;
    private boolean game_over;
    private int number_of_bombs;
    private int default_nummber_of_bombs;

    public Controller(Repo r){
        this.r = r;
        game_over = false;
        number_of_bombs = 10;
        default_nummber_of_bombs = 10;
    }
    public void new_game(){
        game_over = false;
        number_of_bombs = default_nummber_of_bombs;
        r.generate_board(default_nummber_of_bombs);
    }

    public boolean isGame_over() {
        return game_over;
    }

    public void set_default(int nr){
        default_nummber_of_bombs = nr;
    }

    public final int getDefault_nummber_of_bombs(){
        return default_nummber_of_bombs;
    }


    public boolean isCellBomb(int line, int col){
        return Math.abs(r.get_board().get_cell(line, col)) >= 9 && Math.abs(r.get_board().get_cell(line, col)) != 1000;
    }

    public Board get_board(){
        return r.get_board();
    }

    public boolean isShowable(int line, int col){
        return get_board().get_cell(line, col) == 1000 || get_board().get_cell(line, col) < 0;
    }
    public boolean isCellNearABomb(int line, int col){
        return Math.abs(get_board().get_cell(line, col)) > 0 && Math.abs(get_board().get_cell(line, col)) < 9;
    }

    public final int number_of_bombs(){
        return number_of_bombs;
    }

    public void uncover(int line, int col){
        if(line < 0 || line > get_board().get_number_of_rows() - 1 || col < 0 || col > get_board().get_number_of_cols() - 1) return;
        if(isShowable(line, col) && isCellBomb(line, col)){
            for(int i = 0; i < get_board().get_number_of_rows(); i ++){
                for(int j = 0; j < get_board().get_number_of_cols(); j ++){
                    if(get_board().get_cell(i, j) > 0 && get_board().get_cell(i, j) < 1000)
                        get_board().set_cell(i, j, -get_board().get_cell(i, j));
                    else if(get_board().get_cell(i, j) == 0)
                        get_board().set_cell(i, j, 1000);
                }
            }
            game_over = true;
            return;
        }
        if(get_board().get_cell(line, col) > 0 && get_board().get_cell(line, col) != 1000) {
            get_board().set_cell(line, col, -(get_board().get_cell(line, col)));
        }
        else if(get_board().get_cell(line, col) == 0){
            get_board().set_cell(line, col, 1000);
            uncover(line - 1, col);
            uncover(line - 1, col - 1);
            uncover(line - 1, col + 1);
            uncover(line + 1, col);
            uncover(line + 1, col - 1);
            uncover(line + 1, col + 1);
            uncover(line, col + 1);
            uncover(line, col - 1);
        }
    }
    public boolean isGameWon(){
        return number_of_bombs == 0;
    }

    public void bombs(int line, int col){
        if(get_board().get_cell(line, col) >= 9 && get_board().get_cell(line, col) != 1001){
            get_board().set_cell(line, col, 1001);
            number_of_bombs --;
            return;
        }
        if(get_board().get_cell(line, col) == 1001){
            number_of_bombs++;
            get_board().set_cell(line, col, 9);
        }
    }
    public boolean isFieldMarked(int line, int col){
        return get_board().get_cell(line, col) == 1001;
    }
}
