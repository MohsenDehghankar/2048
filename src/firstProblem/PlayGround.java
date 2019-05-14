package firstProblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PlayGround {
    private Number[][] numbers;
    private int squareSize;
    private Player player;

    public PlayGround(int squareSize, Player player) {
        this.squareSize = squareSize;
        this.player = player;
        this.player.setPlayGround(this);
        numbers = new Number[squareSize][squareSize];
        initializePlayGround();
    }

    public Player getPlayer() {
        return player;
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

    public boolean move(Direction direction) {
        Integer[][] previousNumbers = getCopyOfNumbers();
        if(!canMove())
            return false;
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
        if (!isEqual(previousNumbers, newNumbers))
            generateRandomAfterMove();
        trueHasJoineds();
        return true;
    }

    private int calculateSumOfArray(Integer[][] numbers) {
        int result = 0;
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                result += numbers[i][j];
            }
        }
        return result;
    }

    /*private void assignPoint(Integer[][] previous, Integer[][] last) {
        int preSum = calculateSumOfArray(previous);
        int lastSum = calculateSumOfArray(last);
        //TODO
        System.out.println(preSum);
        System.out.println(lastSum);
        player.addPoint(lastSum - preSum);
    }
*/
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
        if(random >= 0) {
            Number newNumber = new Number((randomNumber + 1) * 2, rows.get(random), cols.get(random));
            numbers[rows.get(random)][cols.get(random)] = newNumber;
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

    /*public void show() {
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                if (numbers[i][j] == null) {
                    System.out.print("|_|");
                } else
                    System.out.printf("|%d|", numbers[i][j].getNumber());
            }
            System.out.println();
        }
    }*/

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
                moveOneNumber(numbers, i);
            }
        }
    }

    private void moveOneNumber(ArrayList<Number> numbers, int index) {
        for (int i = index; i < squareSize - 1; i++) {
            if (numbers.get(i + 1).getNumber() == 0) {
                numbers.get(i + 1).setNumber(numbers.get(i).getNumber());
                numbers.get(i).setNumber(0);
            } else if (numbers.get(i + 1).getNumber() == numbers.get(i).getNumber()
                    && !numbers.get(i).getHasJoined() && !numbers.get(i + 1).getHasJoined()) {
                player.addPoint(numbers.get(i).getNumber() * 2);
                numbers.get(i).setNumber(0);
                numbers.get(i + 1).setNumber(2 * numbers.get(i + 1).getNumber());
                numbers.get(i).setHasJoined(true);
                numbers.get(i + 1).setHasJoined(true);
            }
        }
    }

    private boolean canMove() { // if false , means game is finished
        for (int i = 0; i < squareSize; i++) {
            if (canMove(numbers[i]))
                return true;
        }
        for (int i = 0; i < squareSize; i++) {
            Number[] sample = new Number[squareSize];
            for (int j = 0; j < squareSize; j++) {
                sample[j] = numbers[j][i];
            }
            if (canMove(sample))
                return true;
        }
        return false;
    }

    private boolean canMove(Number[] row) {
        for (int i = 0; i < squareSize; i++) {
            if (row[i].getNumber() == 0)
                return true;
            if (i != (squareSize - 1) && (row[i].getNumber() == row[i + 1].getNumber()))
                return true;
        }
        return false;
    }

    public Number[][] getNumbers() {
        return numbers;
    }

    public int getSquareSize() {
        return squareSize;
    }
}

