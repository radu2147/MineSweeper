package repo;

import model.Board;

public abstract class Repo {
    abstract public Board get_board();
    abstract public void generate_board(int nr);
}
