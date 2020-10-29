package tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Random;

public class TicTacToe extends Application  {

    private Stage window;
    private Random random = new Random();
    private char lastWinner = 'X';
    private int playerPoints = 0;
    private int computerPoints = 0;
    private int xCounter = 0;
    private int oCounter = 0;
    private String win = "";
    private Image imageBack = new Image("file:resources/greyBackground.png");
    private Button[][] gameButtons;

    public static void main (String[]args) {
        launch (args);
    }

    @Override
    public void start (Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("TicTacToe JavaFX");
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        HBox buttons = new HBox();
        Button newGameButton = new Button("New Game");

        Button quitGameButton = new Button("Quit");
        quitGameButton.setOnAction(e -> {
            e.consume();
            closeProgram();
        });
        HBox resultsButtons = new HBox();
        Button playerPointsButton = new Button("Player points: " + playerPoints);
        Button computerPointsButton = new Button("Computer points: " + computerPoints);

        BackgroundSize backgroundSize = new BackgroundSize(340, 500, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(imageBack, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        VBox board = new VBox();
        GridPane grid = new GridPane();
        board.setBackground(background);


        newGameButton.setPrefWidth(180);
        quitGameButton.setPrefWidth(180);
        HBox.setMargin(buttons, new Insets(10, 0, 10, 0));
        VBox.setMargin(grid, new Insets(25, 25, 25, 25));

        playerPointsButton.setPrefWidth(180);
        computerPointsButton.setPrefWidth(180);
        HBox.setMargin(resultsButtons, new Insets(25,25,25,25));

        Label winner = new Label();
        Label results = new Label();
        Label computer = new Label();
        Label player = new Label();

        Button[][] gameButtons = new Button[3][3];

        int i, j;
        for(i=0; i<3; i++)
            for(j=0; j<3; j++) {
                int x = i, y = j;
                gameButtons[i][j] = new Button("");
                grid.add(gameButtons[i][j],i,j);
                gameButtons[i][j].setPrefWidth(100);
                gameButtons[i][j].setPrefHeight(100);
                gameButtons[i][j].setOnAction(e ->
                {

                    if (results.getText().equals("")) {
                        if (lastWinner == 'X') {
                            xCounter = 0;
                            for (int a = 0; a < 3; a++)
                                for (int b = 0; b < 3; b++)
                                    if (gameButtons[a][b].getText().equals("X"))
                                        xCounter++;
                            if (gameButtons[x][y].getText().equals("")) {
                                gameButtons[x][y].setText("X");
                                xCounter++;
                                win = check(gameButtons);
                                if (!win.isEmpty()) {
                                    results.setText("The Winner is \n  " + win +" player!");
                                    switch (win) {
                                        case "X":
                                            lastWinner = 'X';
                                            playerPoints++;
                                            break;
                                        case "O":
                                            lastWinner = 'O';
                                            computerPoints++;
                                    }
                                } else if (xCounter == 5) {
                                    results.setText("Game Tie");
                                }
                                if (winner.getText().equals(""))
                                    newBoard(gameButtons);
                                win = check(gameButtons);
                                if (!win.isEmpty()) {
                                    results.setText("The Winner is \n  " + win +" player!");
                                    switch (win) {
                                        case "X":
                                            lastWinner = 'X';
                                            playerPoints++;
                                            break;
                                        case "O":
                                            lastWinner  = 'O';
                                            computerPoints++;
                                    }
                                }
                            }
                        } else {
                            oCounter = 0;
                            for (int a = 0; a < 3; a++)
                                for (int b = 0; b < 3; b++)
                                    if (gameButtons[a][b].getText().equals("O"))
                                        oCounter++;
                            if (gameButtons[x][y].getText().equals("")) {
                                gameButtons[x][y].setText("X");
                            }
                            win = check(gameButtons);
                            if (!win.isEmpty()) {
                                results.setText("The Winner is \n   " + win +" player!");

                                switch (win) {
                                    case "X":
                                        lastWinner = 'X';
                                        break;
                                    case "O":
                                        lastWinner = 'O';
                                        computerPoints++;
                                }
                            }
                            if (winner.getText().equals("")) {
                                newBoard(gameButtons);
                                oCounter++;
                            }
                            win = check(gameButtons);
                            if (!win.isEmpty()) {
                                results.setText("The Winner is \n   " + win +" player!");
                                switch (win) {
                                    case "X":
                                        lastWinner = 'X';
                                        playerPoints++;
                                        break;
                                    case "O":
                                        lastWinner = 'O';
                                        computerPoints++;
                                }
                            } else if (oCounter == 5) {
                                results.setText("Game Tie");
                            }
                        }
                    }
                });
            }


        newGameButton.setOnAction(e ->
        {
            for(int a = 0; a < 3; a++)
                for(int b = 0; b < 3; b++) {
                    gameButtons[a][b].setText("");
                }
            if(lastWinner == 'O')
                newBoard(gameButtons);
            results.setText("");
            playerPointsButton.setText("Player points: " + playerPoints);
            computerPointsButton.setText("Computer points: " + computerPoints);

        });

        results.setAlignment(Pos.CENTER);
        results.setPrefSize(100,100);
        HBox.setMargin(results, new Insets(0, 0, 40, 0));

        buttons.getChildren().addAll(newGameButton, quitGameButton);
        board.getChildren().addAll(buttons,grid,winner,results, computer, player, resultsButtons);
        resultsButtons.getChildren().addAll(playerPointsButton, computerPointsButton);

        Scene scene = new Scene(board, 340, 500);

        window.setScene(scene);
        window.show();

    }

    public String check(Button[][] a) {
        int i;
        for(i = 0; i < 3; i++)
        {
            if(a[i][0].getText().equals(a[i][1].getText()) && a[i][1].getText().equals(a[i][2].getText()) && !a[i][2].getText().equals(""))
                return(a[i][0].getText());
            else if(a[0][i].getText().equals(a[1][i].getText()) && a[1][i].getText().equals(a[2][i].getText()) && !a[2][i].getText().equals(""))
                return(a[0][i].getText());
        }
        if((a[0][0].getText().equals(a[1][1].getText()) && a[1][1].getText().equals(a[2][2].getText())) || (a[0][2].getText().equals(a[1][1].getText()) && a[1][1].getText().equals(a[2][0].getText())) && !a[1][1].getText().equals(""))
            return(a[1][1].getText());
        return("");
    }


    private void newBoard(Button[][] gameButtons) {
        this.gameButtons = gameButtons;
        int a;
        int b;
        int xc = 0;
        int oc = 0;
        int oCount = 0;

        for(a = 0; a < 3; a++)
            for(b = 0; b < 3; b++)
                if(gameButtons[a][b].getText().equals("O"))
                    oCount++;
        if((oCount < 4 && lastWinner == 'X') || (oCount < 5 && lastWinner == 'O')) {
            for(a = 0; a < 3; a++) {
                xc = 0; oc = 0;
                for(b = 0; b < 3; b++)
                    if(gameButtons[a][b].getText().equals("O"))
                        oc++;
                    else if(gameButtons[a][b].getText().equals("X"))
                        xc++;
                if(oc==2)
                    break;
            }
            if(oc==2 && xc==0) {
                for(b = 0; b <3; b++)
                    if(gameButtons[a][b].getText().equals("")) {
                        gameButtons[a][b].setText("O");
                    }
            } else {
                for(a = 0; a < 3; a++) {
                    xc =0; oc = 0;
                    for(b = 0; b < 3; b++)
                        if(gameButtons[b][a].getText().equals("O"))
                            oc++;
                        else if(gameButtons[b][a].getText().equals("X"))
                            xc++;
                    if(oc==2)
                        break;
                }
                if(oc==2 && xc==0) {
                    for(b = 0; b <3; b++)
                        if(gameButtons[b][a].getText().equals("")) {
                            gameButtons[b][a].setText("O");
                        }
                } else {
                    xc = oc = 0;
                    for(a = 0; a < 3; a++)
                        if(gameButtons[a][a].getText().equals("O"))
                            oc++;
                        else if(gameButtons[a][a].getText().equals("X"))
                            xc++;
                    if(oc==2 && xc==0) {
                        for(a = 0; a < 3; a++)
                            if(gameButtons[a][a].getText().equals("")) {
                                gameButtons[a][a].setText("O");
                            }
                    } else {
                        xc = oc = 0;
                        for(a = 0; a < 3; a++)
                            if(gameButtons[a][2-a].getText().equals("O"))
                                oc++;
                            else if(gameButtons[a][2-a].getText().equals("X"))
                                xc++;
                        if(oc==2 && xc==0) {
                            for(a = 0; a < 3; a++)
                                if(gameButtons[a][2-a].getText().equals("")) {
                                    gameButtons[a][2-a].setText("O");
                                }
                        } else {
                            for(a = 0; a < 3; a++) {
                                xc = oc = 0;
                                for(b = 0; b < 3; b++)
                                    if(gameButtons[a][b].getText().equals("O"))
                                        oc++;
                                    else if(gameButtons[a][b].getText().equals("X"))
                                        xc++;
                                if(xc==2)
                                    break;
                            }
                            if(xc==2 && oc==0) {
                                for(b = 0; b <3; b++)
                                    if(gameButtons[a][b].getText().equals("")) {
                                        gameButtons[a][b].setText("O");
                                    }
                            } else {
                                for(a = 0; a < 3; a++) {
                                    xc = oc = 0;
                                    for(b = 0; b < 3; b++)
                                        if(gameButtons[b][a].getText().equals("O"))
                                            oc++;
                                        else if(gameButtons[b][a].getText().equals("X"))
                                            xc++;
                                    if(xc==2)
                                        break;
                                }
                                if(xc==2 && oc==0) {
                                    for(b = 0; b <3; b++)
                                        if(gameButtons[b][a].getText().equals("")) {
                                            gameButtons[b][a].setText("O");
                                        }
                                } else {
                                    xc = oc = 0;
                                    for(a = 0; a < 3; a++)
                                        if(gameButtons[a][a].getText().equals("O"))
                                            oc++;
                                        else if(gameButtons[a][a].getText().equals("X"))
                                            xc++;
                                    if(xc==2 && oc==0) {
                                        for(a = 0; a < 3; a++)
                                            if(gameButtons[a][a].getText().equals("")) {
                                                gameButtons[a][a].setText("O");
                                            }
                                    } else {
                                        xc = oc = 0;
                                        for(a = 0; a < 3; a++)
                                            if(gameButtons[a][2-a].getText().equals("O"))
                                                oc++;
                                            else if(gameButtons[a][2-a].getText().equals("X"))
                                                xc++;
                                        if(xc==2 && oc==0) {
                                            for(a = 0; a < 3; a++)
                                                if(gameButtons[a][2-a].getText().equals("")) {
                                                    gameButtons[a][2-a].setText("O");
                                                }
                                        } else {
                                            do {
                                                a = random.nextInt(3);
                                                b = random.nextInt(3);
                                            } while (gameButtons[a][b].getText().equals("X") || gameButtons[a][b].getText().equals("O"));
                                            gameButtons[a][b].setText("O");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void closeProgram() {
        boolean answer = ConfirmBox.display("Quit","Do you want close game");
        if (answer)
            window.close();
    }
}

