package repo;
import model.*;

import java.util.HashSet;
import java.util.Random;

public class RepoRandom extends Repo {
    private Board board;


    public void generate_board(int nr){
        Random obj = new Random();
        HashSet<Bomb> aux = new HashSet<>();
        while(aux.size() < nr){
            int i = obj.nextInt(10 + (nr - 1) / 10);
            int j = obj.nextInt(10 + 2 * (nr - 1) / 10);
            aux.add(new Bomb(i, j));
        }
        Bomb[] bombs = new Bomb[nr];
        int z = 0;
        for(var el: aux) {
            bombs[z++] = el;
        }
        board = new Board(bombs, 10 + (nr - 1) / 10, 10 + 2 * (nr - 1) / 10);
    }

    public final Board get_board(){
        return board;
    }
    public RepoRandom(){
        generate_board(10);
    }
}
