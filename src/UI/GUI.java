package UI;

import controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class Cell extends Rectangle{
        private final int row;
        private final int col;
        private final String text;

        public Cell(int v, int v1, int i, int j, String text){
            super(v, v1);
            row = i;
            col = j;
            this.text = text;

        }
        public String getText(){
            return text;
        }

}



public class GUI{
    private final Stage window;
    private final Controller service;
    private final Button new_game;
    private final Button settings;

    private class BombSetter{
        private final Stage stage;

        public BombSetter(){
            stage = new Stage();
            init();
        }

        public void show(){
            stage.show();
        }

        private void init(){
            var mainly = new VBox();
            var apply = new Button("Apply");
            var bombs = new Slider(10,60,service.getDefault_nummber_of_bombs());
            var bomb_value = new Label();
            bomb_value.setText("Numar de bombe: " + (int) bombs.getValue());

            bombs.valueProperty().addListener(e -> bomb_value.textProperty().setValue("Numar de bombe: " + ((int) bombs.getValue())));
            apply.setOnMouseClicked(e -> {
                service.set_default((int) bombs.getValue());
                service.new_game();
                initGUI();
                stage.close();
            });

            mainly.getChildren().addAll(bomb_value, bombs, apply);
            var scene = new Scene(mainly, 201, 101);
            stage.setScene(scene);
        }
    }

    private void uncover(BorderPane mainly, GridPane grid){
        if(service.isGameWon()){
            Alert congrats = new Alert(Alert.AlertType.CONFIRMATION);
            congrats.setHeaderText("Congratulations");
            congrats.show();
            try {
                service.new_game();
                uncover(mainly, grid);
            }
            catch(Exception ex){
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setHeaderText(ex.toString());
                al.show();
            }
            return;
        }
        var buttons = new HBox(new_game, settings);
        buttons.setAlignment(Pos.CENTER);
        var lay = new VBox(buttons, new Text("Number of undiscovered bombs: " + service.number_of_bombs()));
        lay.setAlignment(Pos.CENTER);
        lay.setStyle("-fx-padding: 10 0 0 0");
        lay.setSpacing(5);
        mainly.setTop(lay);
        grid.getChildren().clear();
        for(int i = 0; i < service.get_board().get_number_of_rows(); i ++){
            for(int j = 0; j < service.get_board().get_number_of_cols(); j ++){
                Cell rect = new Cell(30,30, i, j, String.valueOf(Math.abs(service.get_board().get_cell(i,j))));
                if(service.isFieldMarked(i, j))
                    rect.setFill(Color.GREEN);
                else
                    rect.setFill(Color.WHITE);
                rect.setStroke(Color.GRAY);
                if(service.isShowable(i, j)) {
                    if (service.isCellBomb(i, j)) {
                        gameOver(mainly, grid);
                        /*
                        if(service.isGame_over()) {
                            grid.add(new StackPane(rect, new Circle(13)), j, i);
                        }
                        else {
                            service.uncover(i, j);
                            uncover(mainly, grid);
                        }
                        
                         */
                        return;
                    }
                    else if (service.isCellNearABomb(i, j)) {
                        var text = new Text(rect.getText());
                        text.setFont(new Font("verdana", 25));
                        grid.add(new StackPane(rect, text), j, i);
                    }
                    else{
                        grid.add(rect, j, i);
                    }
                }
                else{
                    int line = i, col = j;
                    rect.setEffect(new Lighting());
                    grid.add(rect, j, i);
                    rect.setOnMouseClicked(e -> {
                        if(!service.isFieldMarked(line, col)) {
                            if (e.getButton() == MouseButton.PRIMARY) {
                                service.uncover(line, col);
                                uncover(mainly, grid);
                            }
                        }
                        if(e.getButton() == MouseButton.SECONDARY){
                            service.bombs(line, col);
                            uncover(mainly, grid);
                        }
                    });
                }
            }
        }
    }
    private void gameOver(BorderPane mainly, GridPane grid){
        grid.getChildren().clear();
        var buttons = new HBox(new_game, settings);
        buttons.setAlignment(Pos.CENTER);
        var lay = new VBox(buttons, new Text("Game over"));
        lay.setAlignment(Pos.CENTER);
        lay.setStyle("-fx-padding: 10 0 0 0");
        lay.setSpacing(5);
        mainly.setTop(lay);
        for(int i = 0; i < service.get_board().get_number_of_rows(); i ++) {
            for (int j = 0; j < service.get_board().get_number_of_cols(); j++) {
                Cell rect = new Cell(30, 30, i, j, String.valueOf(Math.abs(service.get_board().get_cell(i, j))));
                rect.setFill(Color.WHITE);
                rect.setStroke(Color.GRAY);
                if(service.isCellBomb(i, j))
                    grid.add(new StackPane(rect, new Circle(13)), j, i);
                else if(service.isCellNearABomb(i, j)) {
                    var text = new Text(rect.getText());
                    text.setFont(new Font("verdana", 25));
                    grid.add(new StackPane(rect, text), j, i);
                }
                else
                    grid.add(rect, j, i);
            }
        }
    }

    private void initGUI(){
        var grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        var mainly = new BorderPane();
        new_game.setAlignment(Pos.CENTER);
        new_game.setOnMouseClicked(e -> {
            try {
                service.new_game();
                initGUI();
            }
            catch(Exception ex){
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setHeaderText(ex.toString());
                al.show();
            }
        });
        settings.setOnMouseClicked(e -> {
            var fer = new BombSetter();
            fer.show();
        });
        for(int i = 0; i < service.get_board().get_number_of_rows(); i ++){
            for(int j = 0 ; j < service.get_board().get_number_of_cols(); j ++){
                Cell rect = new Cell(30,30, i, j, String.valueOf(service.get_board().get_cell(i,j)));
                rect.setFill(Color.WHITE);
                rect.setEffect(new Lighting());
                rect.setStroke(Color.GRAY);
                int line = i, col = j;
                rect.setOnMouseClicked(e -> {
                    if(e.getButton() == MouseButton.PRIMARY) {
                        service.uncover(line, col);
                        uncover(mainly, grid);
                    }
                    else if(e.getButton() == MouseButton.SECONDARY){
                        service.bombs(line, col);
                    }
                });
                grid.add(rect, j, i);
            }
        }
        var buttons = new HBox(new_game, settings);
        buttons.setAlignment(Pos.CENTER);
        var lay = new VBox(buttons, new Text("Number of undiscovered bombs: " + service.number_of_bombs()));
        lay.setAlignment(Pos.CENTER);
        lay.setStyle("-fx-padding: 10 0 0 0");
        lay.setSpacing(5);
        mainly.setTop(lay);
        mainly.setCenter(grid);
        mainly.setBottom(new Text("© Radu Baston"));
        window.setScene(new Scene(mainly, 230 + service.get_board().get_number_of_rows() * 30, 200 + service.get_board().get_number_of_cols() * 20));
        window.setTitle("MineSweeper");
        window.show();
    }

    public GUI(Controller s, Stage window){
        this.window = window;
        service = s;
        new_game = new Button("New game");
        settings = new Button("Settings");
        initGUI();
    }
}
