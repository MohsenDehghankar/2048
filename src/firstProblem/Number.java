package firstProblem;

import javafx.scene.paint.Color;

public class Number {
    private int number;
    private int x;
    private int y;
    private boolean hasJoined = false;
    private Color color;

    public Number(int number, int x, int y) {
        this.number = number;
        this.x = x;
        this.y = y;
        setColor();
    }

    private void setColor() {
        int sample = number;
        int counter = 0;
        while (sample > 1) {
            counter++;
            sample /= 2;
        }
        color = Color.rgb(255, 255 - 20 * counter, 255 - 20 * counter);
    }

    public Color getColor() {
        return color;
    }

    public void setNumber(int number) {
        this.number = number;
        setColor();
    }

    public int getNumber() {
        return number;
    }

    public void setHasJoined(boolean hasJoined) {
        this.hasJoined = hasJoined;
    }

    public boolean getHasJoined() {
        return hasJoined;
    }
}
