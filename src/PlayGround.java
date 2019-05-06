import java.util.ArrayList;

public class PlayGround {
    private Number[][] numbers;
    private int squareSize;

    public PlayGround(int squareSize) {
        this.squareSize = squareSize;
        numbers = new Number[squareSize][squareSize];
        // TODO initializing numbers
    }

    public void move(Direction direction) {
        switch (direction) {
            case LEFT:
                for (int i = 0; i < squareSize - 1; i++) {
                    joinTwoLists(getArrayListOfNumbers(i, false),
                            getArrayListOfNumbers(i + 1, false));
                }
                break;
            case RIGHT:
                for (int i = squareSize - 1; i >= 0; i--) {
                    joinTwoLists(getArrayListOfNumbers(i, false),
                            getArrayListOfNumbers(i - 1, false));
                }
                break;
            case DOWN:
                for (int i = squareSize - 1; i >= 0; i--) {
                    joinTwoLists(getArrayListOfNumbers(i, true),
                            getArrayListOfNumbers(i - 1, true));
                }
                break;
            case UP:
                for (int i = squareSize - 1; i >= 0; i--) {
                    joinTwoLists(getArrayListOfNumbers(i, true),
                            getArrayListOfNumbers(i + 1, true));
                }
                break;
        }
    }

    private void joinTwoNumbers(Number target, Number secondNumber) {
        // target stays in its position and secondNumber disappears
        if (secondNumber.getNumber() == target.getNumber()) {
            int x = secondNumber.getX();
            int y = secondNumber.getY();
            numbers[x][y] = null;
            target.setNumber(target.getNumber() * 2);
        }
    }

    private void joinTwoLists(ArrayList<Number> target, ArrayList<Number> secondList) {
        // either column or row
        for (int i = 0; i < squareSize; i++) {
            joinTwoNumbers(target.get(i), secondList.get(i));
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

