package model;

public class Bomb{
    private final int line;
    private final int col;

    public Bomb(int line, int col){
        this.line = line;
        this.col = col;
    }
    public final int get_line(){
        return line;
    }
    public final int get_column(){
        return col;
    }

    public boolean equals(Object ot){
        Bomb cv = (Bomb) ot;
        return line == cv.line && col == cv.col;
    }
    public int hashCode(){
        return (line + col) % 10;
    }

}
