package firstProblem;

import java.util.ArrayList;
import java.util.Collections;
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
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                numbers[i][j] = new Number(0, i, j);
            }
        }
        int randomRowOne = new Random().nextInt(squareSize);
        int randomColOne = new Random().nextInt(squareSize);
        int randomRowTwo = new Random().nextInt(squareSize);
        int randomColTwo = new Random().nextInt(squareSize);
        numbers[randomRowOne][randomColOne].setNumber(2);
        numbers[randomRowTwo][randomColTwo].setNumber(2);
    }

    public void move(Direction direction) {
        Integer[][] previouseNumbers = getCopyOfNumbers();
        switch (direction) {
            case LEFT:
                for (int i = 0; i < squareSize; i++) {
                    ArrayList<Number> numbers = getArrayListOfNumbers(i, true);
                    Collections.reverse(numbers);
                    moveInRowORColumn(numbers);
                }
                break;
            case RIGHT:
                for (int i = 0; i < squareSize; i++) {
                    moveInRowORColumn(getArrayListOfNumbers(i, true));
                }
                break;
            case DOWN:
                for (int i = 0; i < squareSize; i++) {
                    moveInRowORColumn(getArrayListOfNumbers(i, false));
                }
                break;
            case UP:
                for (int i = 0; i < squareSize; i++) {
                    ArrayList<Number> numbers = getArrayListOfNumbers(i, false);
                    Collections.reverse(numbers);
                    moveInRowORColumn(numbers);
                }
                break;
        }
        Integer[][] newNumbers = getCopyOfNumbers();
        if (!isEqual(previouseNumbers, newNumbers))
            generateRandomAfterMove();
        //generateRandomAfterMove();
        trueHasJoineds();
    }

    private void generateRandomAfterMove() {
        ArrayList<Integer> rows = new ArrayList<>();
        ArrayList<Integer> cols = new ArrayList<>();
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                if (numbers[i][j].getNumber() == 0) {
                    rows.add(i);
                    cols.add(j);
                }
            }
        }
        int random = (new Random().nextInt(rows.size()));
        int randomNumber = (new Random().nextInt(2));
        Number newNumber = new Number((randomNumber + 1) * 2, rows.get(random), cols.get(random));
        numbers[rows.get(random)][cols.get(random)] = newNumber;
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

    private Integer[][] getCopyOfNumbers() {
        Integer[][] copy = new Integer[squareSize][squareSize];
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                copy[i][j] = numbers[i][j].getNumber();
            }
        }
        return copy;
    }

    private boolean isEqual(Integer[][] first, Integer[][] second) {
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                if (first[i][j] != second[i][j])
                    return false;
            }
        }
        return true;
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

    private void trueHasJoineds() {
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                numbers[i][j].setHasJoined(false);
            }
        }
    }

    private void moveInRowORColumn(ArrayList<Number> numbers) {
        // from first to last -->
        for (int i = squareSize - 2; i >= 0; i--) {
            // i --> i + 1
            for (int j = squareSize - 2; j >= 0; j--) {
                movrOneNumber(numbers, i);
            }
        }
    }

    private void movrOneNumber(ArrayList<Number> numbers, int index) {
        for (int i = index; i < squareSize - 1; i++) {
            if (numbers.get(i + 1).getNumber() == 0) {
                numbers.get(i + 1).setNumber(numbers.get(i).getNumber());
                numbers.get(i).setNumber(0);
            } else if (numbers.get(i + 1).getNumber() == numbers.get(i).getNumber()
                    && !numbers.get(i).getHasJoined() && !numbers.get(i + 1).getHasJoined()) {
                numbers.get(i).setNumber(0);
                numbers.get(i + 1).setNumber(2 * numbers.get(i + 1).getNumber());
                numbers.get(i).setHasJoined(true);
                numbers.get(i + 1).setHasJoined(true);
            }
        }
    }
}

