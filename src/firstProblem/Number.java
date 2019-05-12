package firstProblem;

public class Number {
    private int number;
    private int x;
    private int y;
    private boolean hasJoined = false;

    public Number(int number, int x, int y) {
        this.number = number;
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setHasJoined(boolean hasJoined) {
        this.hasJoined = hasJoined;
    }

    public boolean getHasJoined(){
        return hasJoined;
    }
}
