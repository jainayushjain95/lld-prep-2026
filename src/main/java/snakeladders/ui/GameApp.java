package snakeladders.ui;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import snakeladders.Board;
import snakeladders.Game;
import snakeladders.Player;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Renders and drives an already-constructed Board/Game/Player set. This never touches
 * Game's internals — it calls the same public takeTurn()/isGameOver() any console caller
 * would, and reconstructs the roll/snake/ladder outcome purely by diffing Player positions
 * before and after the call and cross-checking Board.getDestination() (all pre-existing
 * public API), so it can animate a play-by-play without duplicating any rule.
 */
public class GameApp extends Application {

    private static final Color[] PALETTE = {
            Color.web("#e63946"), Color.web("#2a9d8f"), Color.web("#f4a261"), Color.web("#457b9d")
    };
    private static final String[] DICE_FACES = {"", "⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};

    private static Board initialBoard;
    private static Map<Integer, Integer> initialSnakes;
    private static Map<Integer, Integer> initialLadders;
    private static List<Player> initialPlayers;
    private static Game initialGame;

    public static void launch(Board board, Map<Integer, Integer> snakes, Map<Integer, Integer> ladders,
                               List<Player> players, Game game) {
        GameApp.initialBoard = board;
        GameApp.initialSnakes = snakes;
        GameApp.initialLadders = ladders;
        GameApp.initialPlayers = players;
        GameApp.initialGame = game;
        Application.launch(GameApp.class);
    }

    private Board board;
    private List<Player> players;
    private Game game;
    private Map<Player, Color> colors;
    private int currentPlayerIndex = 0;

    private BoardCanvas boardCanvas;
    private Circle turnChip;
    private Label turnLabel;
    private Label diceLabel;
    private TextArea log;
    private Button rollButton;

    @Override
    public void start(Stage stage) {
        this.board = initialBoard;
        this.players = initialPlayers;
        this.game = initialGame;

        colors = new LinkedHashMap<>();
        for (int i = 0; i < players.size(); i++) {
            colors.put(players.get(i), PALETTE[i % PALETTE.length]);
        }

        boardCanvas = new BoardCanvas(initialSnakes, initialLadders, players, colors);
        boardCanvas.setCurrentPlayer(players.get(0));
        boardCanvas.setEffect(new DropShadow(25, Color.rgb(0, 0, 0, 0.6)));

        Label title = new Label("🐍  SNAKES & LADDERS  🪜");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #ffd166;");
        title.setEffect(new DropShadow(8, Color.rgb(0, 0, 0, 0.8)));

        turnChip = new Circle(8, colors.get(players.get(0)));
        turnChip.setStroke(Color.WHITE);
        turnChip.setStrokeWidth(1);
        turnLabel = new Label(players.get(0).getName() + "'s turn");
        turnLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #f4f1ea;");
        HBox turnRow = new HBox(8, turnChip, turnLabel);
        turnRow.setAlignment(Pos.CENTER_LEFT);

        diceLabel = new Label("🎲");
        diceLabel.setStyle("-fx-font-size: 44px;");
        StackPane diceBox = new StackPane(diceLabel);
        diceBox.setPrefSize(90, 90);
        diceBox.setStyle("-fx-background-color: #f4f1ea; -fx-background-radius: 14; "
                + "-fx-border-color: #3c2a21; -fx-border-width: 2; -fx-border-radius: 14;");
        diceBox.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.5)));

        rollButton = new Button("Roll Dice");
        rollButton.setPrefWidth(180);
        rollButton.setPrefHeight(40);
        rollButton.setStyle("-fx-background-color: linear-gradient(to bottom, #52b788, #2d6a4f); "
                + "-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; "
                + "-fx-background-radius: 20; -fx-cursor: hand;");
        rollButton.setOnAction(e -> onRoll());

        Label logTitle = new Label("MOVE LOG");
        logTitle.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #b8ab95;");

        log = new TextArea();
        log.setEditable(false);
        log.setWrapText(true);
        log.setPrefRowCount(18);
        log.setStyle("-fx-control-inner-background: #2e2a24; -fx-text-fill: #f4f1ea; "
                + "-fx-font-family: 'Menlo', monospace; -fx-font-size: 12px;");
        VBox.setVgrow(log, Priority.ALWAYS);

        VBox sidebar = new VBox(14, turnRow, diceBox, rollButton, logTitle, log);
        sidebar.setPadding(new Insets(18));
        sidebar.setAlignment(Pos.TOP_CENTER);
        sidebar.setPrefWidth(250);
        sidebar.setStyle("-fx-background-color: #241f1a; -fx-background-radius: 16;");
        sidebar.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.5)));

        VBox centerBox = new VBox(14, title, boardCanvas);
        centerBox.setAlignment(Pos.TOP_CENTER);

        BorderPane root = new BorderPane();
        root.setCenter(centerBox);
        root.setRight(sidebar);
        root.setPadding(new Insets(18));
        root.setStyle("-fx-background-color: radial-gradient(center 50% 35%, radius 100%, #1b3a2f, #0d1f19);");
        BorderPane.setAlignment(centerBox, Pos.CENTER);
        BorderPane.setMargin(sidebar, new Insets(0, 0, 0, 18));

        stage.setTitle("Snakes & Ladders");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    private void onRoll() {
        if (game.isGameOver()) {
            return;
        }
        rollButton.setDisable(true);

        Player current = players.get(currentPlayerIndex);
        int fromPosition = current.getPosition();

        game.takeTurn();

        int finalPosition = current.getPosition();
        boolean gameOver = game.isGameOver();

        int roll = 0;
        int rolledPosition = fromPosition;
        String event = "NONE";

        if (finalPosition != fromPosition) {
            for (int r = 1; r <= 6; r++) {
                int candidate = fromPosition + r;
                if (candidate > board.getSize()) {
                    break;
                }
                int destination = board.getDestination(candidate);
                if (destination == finalPosition) {
                    roll = r;
                    rolledPosition = candidate;
                    event = destination < candidate ? "SNAKE" : destination > candidate ? "LADDER" : "NONE";
                    break;
                }
            }
        }

        diceLabel.setText(roll > 0 ? DICE_FACES[roll] : "🎲");
        log.appendText(current.getName() + " rolled " + (roll > 0 ? roll : "?") + "\n");

        int stepCount = Math.max(0, rolledPosition - fromPosition);
        int finalRolledPosition = rolledPosition;
        String finalEvent = event;

        if (stepCount == 0) {
            afterMove(current, fromPosition, finalRolledPosition, finalPosition, finalEvent, gameOver);
            return;
        }

        Timeline timeline = new Timeline();
        for (int step = 1; step <= stepCount; step++) {
            int cell = fromPosition + step;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(step * 180), ev -> {
                boardCanvas.setDisplayPosition(current, cell);
                boardCanvas.draw();
            }));
        }
        timeline.setOnFinished(ev -> afterMove(current, fromPosition, finalRolledPosition, finalPosition, finalEvent, gameOver));
        timeline.play();
    }

    private void afterMove(Player current, int fromPosition, int rolledPosition, int finalPosition, String event, boolean gameOver) {
        if (rolledPosition == fromPosition && finalPosition == fromPosition) {
            log.appendText(current.getName() + " overshoots - stays at " + fromPosition + "\n");
            boardCanvas.setDisplayPosition(current, fromPosition);
            boardCanvas.draw();
            advanceTurn(gameOver, current);
            return;
        }

        if ("SNAKE".equals(event)) {
            log.appendText("Snake! " + current.getName() + " slides from " + rolledPosition + " to " + finalPosition + "\n");
        } else if ("LADDER".equals(event)) {
            log.appendText("Ladder! " + current.getName() + " climbs from " + rolledPosition + " to " + finalPosition + "\n");
        }

        PauseTransition pause = new PauseTransition(Duration.millis("NONE".equals(event) ? 0 : 450));
        pause.setOnFinished(ev -> {
            boardCanvas.setDisplayPosition(current, finalPosition);
            boardCanvas.draw();
            log.appendText(current.getName() + " is now at position " + finalPosition + "\n");
            advanceTurn(gameOver, current);
        });
        pause.play();
    }

    private void advanceTurn(boolean gameOver, Player current) {
        if (gameOver) {
            log.appendText(current.getName() + " wins!\n");
            turnLabel.setText("🏆 " + current.getName() + " wins!");
            turnLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffd166;");
            rollButton.setDisable(true);
            return;
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        Player next = players.get(currentPlayerIndex);
        turnLabel.setText(next.getName() + "'s turn");
        turnChip.setFill(colors.get(next));
        boardCanvas.setCurrentPlayer(next);
        boardCanvas.draw();
        rollButton.setDisable(false);
    }
}
