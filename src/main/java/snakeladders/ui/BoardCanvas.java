package snakeladders.ui;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import snakeladders.Player;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Pure rendering layer over an existing Player set. Never mutates game state — only reads
 * Player.getPosition(), the snake/ladder maps Main already built (not asked back from Board),
 * plus the display positions and current-turn player the caller feeds in for animation/highlight.
 */
public class BoardCanvas extends Canvas {

    private static final int CELL = 62;
    private static final int START_ZONE = 112;
    private static final int GRID = 10;
    private static final int FRAME = 8;

    private final Map<Integer, Integer> snakes;
    private final Map<Integer, Integer> ladders;
    private final List<Player> players;
    private final Map<Player, Color> colors;
    private final Map<Player, Integer> displayPositions = new LinkedHashMap<>();
    private Player currentPlayer;

    public BoardCanvas(Map<Integer, Integer> snakes, Map<Integer, Integer> ladders, List<Player> players, Map<Player, Color> colors) {
        super(START_ZONE + CELL * GRID + FRAME * 2, CELL * GRID + FRAME * 2);
        this.snakes = snakes;
        this.ladders = ladders;
        this.players = players;
        this.colors = colors;
        for (Player p : players) {
            displayPositions.put(p, p.getPosition());
        }
        draw();
    }

    public void setDisplayPosition(Player player, int position) {
        displayPositions.put(player, position);
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        drawFrame(gc);
        drawStartZone(gc);
        drawGrid(gc);
        drawLadders(gc);
        drawSnakes(gc);
        drawTokens(gc);
    }

    private void drawFrame(GraphicsContext gc) {
        gc.setFill(Color.web("#3c2a21"));
        gc.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
    }

    private void drawStartZone(GraphicsContext gc) {
        double x = FRAME;
        double y = FRAME;
        double h = CELL * GRID;
        gc.setFill(Color.web("#2e2a24"));
        gc.fillRect(x, y, START_ZONE, h);
        gc.setFill(Color.web("#d9a441"));
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gc.fillText("🏁 START", x + 12, y + h - 16);
    }

    private void drawGrid(GraphicsContext gc) {
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        for (int row = 0; row < GRID; row++) {
            for (int col = 0; col < GRID; col++) {
                int rowFromBottom = GRID - 1 - row;
                int posInRow = (rowFromBottom % 2 == 0) ? col : (GRID - 1 - col);
                int cellNumber = rowFromBottom * GRID + posInRow + 1;

                double x = FRAME + START_ZONE + col * CELL;
                double y = FRAME + row * CELL;

                boolean special = cellNumber == 1 || cellNumber == GRID * GRID;
                boolean light = (row + col) % 2 == 0;
                gc.setFill(special ? Color.web("#ffd166") : (light ? Color.web("#fff3da") : Color.web("#ffb84d")));
                gc.fillRect(x, y, CELL, CELL);

                gc.setStroke(Color.web("#3c2a21"));
                gc.setLineWidth(1.2);
                gc.strokeRect(x + 0.5, y + 0.5, CELL - 1, CELL - 1);

                double badgeR = 11;
                gc.setFill(Color.rgb(255, 255, 255, 0.75));
                gc.fillOval(x + 4, y + 4, badgeR * 2, badgeR * 2);
                gc.setFill(Color.web("#3c2a21"));
                gc.fillText(String.valueOf(cellNumber), x + (cellNumber < 10 ? 10 : 6), y + 18);

                if (cellNumber == GRID * GRID) {
                    gc.setFill(Color.web("#bc4749"));
                    gc.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                    gc.fillText("🏆", x + CELL - 26, y + CELL - 10);
                    gc.setFont(Font.font("Arial", FontWeight.BOLD, 11));
                }
            }
        }
    }

    private void drawLadders(GraphicsContext gc) {
        gc.setLineCap(StrokeLineCap.ROUND);
        for (Map.Entry<Integer, Integer> entry : ladders.entrySet()) {
            Point2D bottom = cellCenter(entry.getKey());
            Point2D top = cellCenter(entry.getValue());
            Point2D dir = top.subtract(bottom).normalize();
            Point2D normal = new Point2D(-dir.getY(), dir.getX()).multiply(8);

            Point2D rail1Start = bottom.add(normal);
            Point2D rail1End = top.add(normal);
            Point2D rail2Start = bottom.subtract(normal);
            Point2D rail2End = top.subtract(normal);

            gc.setLineWidth(5);
            gc.setStroke(Color.web("#7f4f24"));
            gc.strokeLine(rail1Start.getX(), rail1Start.getY(), rail1End.getX(), rail1End.getY());
            gc.strokeLine(rail2Start.getX(), rail2Start.getY(), rail2End.getX(), rail2End.getY());
            gc.setLineWidth(3);
            gc.setStroke(Color.web("#f2a65a"));
            gc.strokeLine(rail1Start.getX(), rail1Start.getY(), rail1End.getX(), rail1End.getY());
            gc.strokeLine(rail2Start.getX(), rail2Start.getY(), rail2End.getX(), rail2End.getY());

            int rungs = 6;
            gc.setLineWidth(4);
            gc.setStroke(Color.web("#f2a65a"));
            for (int i = 0; i <= rungs; i++) {
                double t = i / (double) rungs;
                Point2D p1 = lerp(rail1Start, rail1End, t);
                Point2D p2 = lerp(rail2Start, rail2End, t);
                gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            }
        }
    }

    private static final Color[] SNAKE_COLORS = {
            Color.web("#2a9d34"), Color.web("#d62839"), Color.web("#7b2cbf"), Color.web("#e85d04")
    };

    private void drawSnakes(GraphicsContext gc) {
        gc.setLineCap(StrokeLineCap.ROUND);
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : snakes.entrySet()) {
            Point2D head = cellCenter(entry.getKey());
            Point2D tail = cellCenter(entry.getValue());
            Point2D unit = tail.subtract(head).normalize();
            Point2D normal = new Point2D(-unit.getY(), unit.getX());
            Color bodyColor = SNAKE_COLORS[i % SNAKE_COLORS.length];

            double length = tail.distance(head);
            double amplitude = Math.min(28, length / 5.0);
            double waveCount = 2.5 + (i % 2) * 0.5;
            double phase = i * 1.7;

            int segments = 40;
            Point2D[] points = new Point2D[segments + 1];
            for (int s = 0; s <= segments; s++) {
                double t = s / (double) segments;
                double taperedWave = Math.sin(t * Math.PI * waveCount + phase) * amplitude * Math.sin(t * Math.PI);
                points[s] = lerp(head, tail, t).add(normal.multiply(taperedWave));
            }

            gc.setStroke(bodyColor);
            for (int s = 0; s < segments; s++) {
                double t = s / (double) segments;
                gc.setLineWidth(13 * (1 - t) + 3 * t);
                gc.strokeLine(points[s].getX(), points[s].getY(), points[s + 1].getX(), points[s + 1].getY());
            }

            gc.setFill(bodyColor.brighter());
            for (int s = 3; s < segments; s += 5) {
                Point2D p = points[s];
                gc.fillOval(p.getX() - 2.5, p.getY() - 2.5, 5, 5);
            }

            Point2D headPoint = points[0];
            Point2D headDir = points[1].subtract(points[0]).normalize();
            double angle = Math.toDegrees(Math.atan2(headDir.getY(), headDir.getX()));

            gc.save();
            gc.translate(headPoint.getX(), headPoint.getY());
            gc.rotate(angle);
            gc.setFill(bodyColor);
            gc.fillOval(-6, -10, 24, 20);
            gc.restore();

            Point2D eyeBase = headPoint.add(headDir.multiply(9));
            Point2D eyeOffset = normal.multiply(5);
            gc.setFill(Color.WHITE);
            gc.fillOval(eyeBase.getX() + eyeOffset.getX() - 3, eyeBase.getY() + eyeOffset.getY() - 3, 6, 6);
            gc.fillOval(eyeBase.getX() - eyeOffset.getX() - 3, eyeBase.getY() - eyeOffset.getY() - 3, 6, 6);
            gc.setFill(Color.BLACK);
            gc.fillOval(eyeBase.getX() + eyeOffset.getX() - 1.5, eyeBase.getY() + eyeOffset.getY() - 1.5, 3, 3);
            gc.fillOval(eyeBase.getX() - eyeOffset.getX() - 1.5, eyeBase.getY() - eyeOffset.getY() - 1.5, 3, 3);

            gc.setStroke(Color.web("#ff0000"));
            gc.setLineWidth(1.8);
            Point2D tongueTip = headPoint.add(headDir.multiply(17));
            gc.strokeLine(headPoint.getX(), headPoint.getY(), tongueTip.getX(), tongueTip.getY());

            i++;
        }
    }

    private void drawTokens(GraphicsContext gc) {
        Map<Integer, Integer> seenAtCell = new LinkedHashMap<>();
        int startIndex = 0;
        for (Player player : players) {
            int position = displayPositions.getOrDefault(player, player.getPosition());
            Color color = colors.get(player);

            double x;
            double y;
            if (position <= 0) {
                x = FRAME + START_ZONE / 2.0;
                y = FRAME + CELL * GRID - 50 - startIndex * 42;
                startIndex++;
            } else {
                Point2D center = cellCenter(position);
                int slot = seenAtCell.getOrDefault(position, 0);
                seenAtCell.put(position, slot + 1);
                double offsetX = (slot % 2 == 0 ? -1 : 1) * 11;
                double offsetY = (slot < 2) ? -9 : 9;
                x = center.getX() + offsetX;
                y = center.getY() + offsetY;
            }

            if (player == currentPlayer) {
                gc.setFill(color.deriveColor(0, 1, 1, 0.35));
                gc.fillOval(x - 17, y - 17, 34, 34);
            }

            gc.setFill(Color.rgb(0, 0, 0, 0.25));
            gc.fillOval(x - 9, y - 7, 20, 20);

            RadialGradient gradient = new RadialGradient(
                    0, 0, x - 4, y - 4, 16, false, CycleMethod.NO_CYCLE,
                    new Stop(0, color.brighter().brighter()),
                    new Stop(1, color.darker())
            );
            gc.setFill(gradient);
            gc.fillOval(x - 10, y - 10, 20, 20);
            gc.setStroke(Color.web("#3c2a21"));
            gc.setLineWidth(1.5);
            gc.strokeOval(x - 10, y - 10, 20, 20);

            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 11));
            gc.fillText(player.getName().substring(0, 1).toUpperCase(), x - 4, y + 4);
        }
    }

    private Point2D lerp(Point2D a, Point2D b, double t) {
        return a.add(b.subtract(a).multiply(t));
    }

    private Point2D cellCenter(int cell) {
        int index = Math.max(1, Math.min(cell, GRID * GRID)) - 1;
        int rowFromBottom = index / GRID;
        int posInRow = index % GRID;
        int col = (rowFromBottom % 2 == 0) ? posInRow : (GRID - 1 - posInRow);
        int rowFromTop = GRID - 1 - rowFromBottom;

        double x = FRAME + START_ZONE + col * CELL + CELL / 2.0;
        double y = FRAME + rowFromTop * CELL + CELL / 2.0;
        return new Point2D(x, y);
    }
}
