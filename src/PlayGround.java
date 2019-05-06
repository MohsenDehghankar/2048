import java.util.ArrayList;
import java.util.Random;

public class PlayGround {
    private Number[][] numbers;
    private int squareSize;

    public PlayGround(int squareSize) {
        this.squareSize = squareSize;
        numbers = new Number[squareSize][squareSize];
        // TODO initializing numbers
        initializePlayGround();
    }

    private void initializePlayGround() {
        int randomRowOne = new Random().nextInt(squareSize);
        int randomColOne = new Random().nextInt(squareSize);
        int randomRowTwo = new Random().nextInt(squareSize);
        int randomColTwo = new Random().nextInt(squareSize);
        numbers[randomRowOne][randomColOne] = new Number(2, randomRowOne, randomColOne);
        numbers[randomRowTwo][randomColTwo] = new Number(2, randomRowTwo, randomColTwo);
    }

    public void move(Direction direction) {
        switch (direction) {
            case LEFT:
                for (int i = squareSize-2; i >= 0 ; i--) {
                    joinTwoLists(i, i + 1, false);
                }
                break;
            case RIGHT:
                for (int i = 1; i < squareSize; i++) {
                    joinTwoLists( i, i - 1, false);
                }
                break;
            case DOWN:
                for (int i = 1; i <squareSize; i++) {
                    joinTwoLists(i,i-1,true);
                }
                break;
            case UP:
                for (int i = squareSize-2; i >= 0; i--) {
                    joinTwoLists(i,i+1,true);
                }
                break;
        }
    }

    private void moveListTo(ArrayList<Number> list, Direction direction, int target) {
        switch (direction) {
            case UP:
                for (int i = 0; i < squareSize; i++) {
                    numbers[target][i] = list.get(i);
                }
                break;
            case RIGHT:
                for (int i = 0; i < squareSize; i++) {
                    numbers[i][target] = list.get(i);
                }
                break;
        }
    }

    private void joinTwoNumbers(int targetX, int targetY, int secondNumberX, int secondNumberY) {
        // target stays in its position and secondNumber disappears
        if (numbers[targetX][targetY] == null && numbers[secondNumberX][secondNumberY] == null)
            return;
        else if (numbers[targetX][targetY] == null) {
            numbers[targetX][targetY] = numbers[secondNumberX][secondNumberY];
            numbers[secondNumberX][secondNumberY] = null;

        } else if (numbers[secondNumberX][secondNumberY] == null){
            return;
        } else if (numbers[secondNumberX][secondNumberY].getNumber()
                == numbers[targetX][targetY].getNumber()) {
            numbers[secondNumberX][secondNumberY] = null;
            numbers[targetX][targetY].setNumber(numbers[targetX][targetY].getNumber() * 2);
        }
    }

    private void joinTwoLists(int targetPosition, int secondPosition, boolean isRow) {
        if (isRow) {
            for (int i = 0; i < squareSize; i++) {
                joinTwoNumbers(targetPosition, i
                        , secondPosition, i);
            }
        } else {
            for (int i = 0; i < squareSize; i++) {
                joinTwoNumbers(i, targetPosition
                        , i, secondPosition);
            }
        }
    }

    private ArrayList<Number> getArrayListOfNumbers(int number, boolean isRow) {
        ArrayList<Number> result = new ArrayList<>();
        for (int i = 0; i < squareSize; i++) {
            if (isRow)
                result.add(numbers[number][i]);
            else
                result.add(numbers[i][number]);
        }
        return result;
    }

    public void show() {
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                if (numbers[i][j] == null) {
                    System.out.print("|_|");
                } else
                    System.out.printf("|%d|", numbers[i][j].getNumber());
            }
            System.out.println();
        }
    }
}

