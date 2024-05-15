import java.util.Random;
import java.util.Scanner;

public class TextAdventureGame {
    private static final char TREE = 'T';
    private static final char OPEN_SPACE = '.';
    private static final char PLAYER = 'P';

    public static void main(String[] args) {
        int rows = 10;
        int cols = 20;
        char[][] forest = generateForest(rows, cols);

        Scanner scanner = new Scanner(System.in);
        boolean gameRunning = true;

        while (gameRunning) {
            displayForest(forest);
            System.out.print("Enter a direction (W/A/S/D) or 'Q' to quit: ");
            String input = scanner.nextLine().toUpperCase();

            if (input.equals("Q")) {
                gameRunning = false;
            } else if (input.length() == 1 && "WASD".contains(input)) {
                movePlayer(forest, input.charAt(0));
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }

        scanner.close();
    }

    public static char[][] generateForest(int rows, int cols) {
        char[][] forest = new char[rows][cols];
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double probability = random.nextDouble();
                if (probability < 0.3) {
                    forest[i][j] = TREE;
                } else {
                    forest[i][j] = OPEN_SPACE;
                }
            }
        }

        // Place the player at a random empty location
        int playerRow, playerCol;
        do {
            playerRow = random.nextInt(rows);
            playerCol = random.nextInt(cols);
        } while (forest[playerRow][playerCol] == TREE);
        forest[playerRow][playerCol] = PLAYER;

        return forest;
    }

    public static void displayForest(char[][] forest) {
        for (char[] row : forest) {
            for (char cell : row) {
                switch (cell) {
                    case TREE:
                        System.out.print(" T ");
                        break;
                    case OPEN_SPACE:
                        System.out.print(" . ");
                        break;
                    case PLAYER:
                        System.out.print(" P ");
                        break;
                }
            }
            System.out.println();
        }
    }

    public static void movePlayer(char[][] forest, char direction) {
        int playerRow = -1, playerCol = -1;

        // Find the player's current location
        for (int i = 0; i < forest.length; i++) {
            for (int j = 0; j < forest[i].length; j++) {
                if (forest[i][j] == PLAYER) {
                    playerRow = i;
                    playerCol = j;
                    break;
                }
            }
        }

        // Update the player's location based on the direction
        switch (direction) {
            case 'W':
                if (playerRow > 0 && forest[playerRow - 1][playerCol] != TREE) {
                    forest[playerRow][playerCol] = OPEN_SPACE;
                    forest[--playerRow][playerCol] = PLAYER;
                } else {
                    System.out.println("Cannot move up. Obstacle encountered.");
                }
                break;
            case 'S':
                if (playerRow < forest.length - 1 && forest[playerRow + 1][playerCol] != TREE) {
                    forest[playerRow][playerCol] = OPEN_SPACE;
                    forest[++playerRow][playerCol] = PLAYER;
                } else {
                    System.out.println("Cannot move down. Obstacle encountered.");
                }
                break;
            case 'A':
                if (playerCol > 0 && forest[playerRow][playerCol - 1] != TREE) {
                    forest[playerRow][playerCol] = OPEN_SPACE;
                    forest[playerRow][--playerCol] = PLAYER;
                } else {
                    System.out.println("Cannot move left. Obstacle encountered.");
                }
                break;
            case 'D':
                if (playerCol < forest[playerRow].length - 1 && forest[playerRow][playerCol + 1] != TREE) {
                    forest[playerRow][playerCol] = OPEN_SPACE;
                    forest[playerRow][++playerCol] = PLAYER;
                } else {
                    System.out.println("Cannot move right. Obstacle encountered.");
                }
                break;
            default:
                System.out.println("Invalid direction.");
                break;
        }
    }
}