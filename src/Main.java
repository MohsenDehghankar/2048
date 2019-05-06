import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int squareSize = scanner.nextInt();
        PlayGround playGround = new PlayGround(squareSize);
        while (true) {
            playGround.show();
            String commmand = scanner.next();
            if(commmand.equals("right")){
                playGround.move(Direction.RIGHT);
            }else if(commmand.equals("left")){
                playGround.move(Direction.LEFT);
            }else if (commmand.equals("up")){
                playGround.move(Direction.UP);
            }else if (commmand.equals("down")){
                playGround.move(Direction.DOWN);
            } else if (commmand.equals("w"))
                break;
        }
    }
}
