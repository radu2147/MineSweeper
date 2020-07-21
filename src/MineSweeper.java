import UI.GUI;
import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import repo.RepoRandom;

public class MineSweeper extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            var r = new RepoRandom();
            var s = new Controller(r);
            var ui = new GUI(s, primaryStage);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
