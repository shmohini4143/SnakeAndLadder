import java.util.*;

public class SnakeAndLadder {
    static final int WINNING_POSITION = 100;
    static final int BOARD_SIZE = 10;

    static Map<Integer, Integer> snakes = new HashMap<>();
    static Map<Integer, Integer> ladders = new HashMap<>();

    static {
        // Define Snakes (start → end)
        snakes.put(99, 7);   // S1
        snakes.put(90, 48);  // S3
        snakes.put(76, 36);  // S4
        snakes.put(54, 16);  // S2
        snakes.put(30, 5);   // S5

        // Define Ladders (start → end)
        ladders.put(3, 22);  //L2
        ladders.put(8, 26);  //L4
        ladders.put(20, 41);  //L3
        ladders.put(28, 77);  //L5
        ladders.put(50, 91);  //L1
    }

    public static int rollDice() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    public static int movePlayer(int position, int diceValue, List<Integer> journey) {
        position += diceValue;
        if (position > WINNING_POSITION) {
            return position - diceValue; // Stay in the same position if exceeding 100
        }
        if (snakes.containsKey(position)) {
            System.out.println("Oops! Bitten by a snake at " + position);
            position = snakes.get(position);
        } else if (ladders.containsKey(position)) {
            System.out.println("Yay! Climbed a ladder at " + position);
            position = ladders.get(position);
        }
        journey.add(position);
        return position;
    }

    public static void printDice(int number) {
        String[] diceFaces = {
            "+-------+\n|       |\n|   ●   |\n|       |\n+-------+",
            "+-------+\n| ●     |\n|       |\n|     ● |\n+-------+",
            "+-------+\n| ●     |\n|   ●   |\n|     ● |\n+-------+",
            "+-------+\n| ●   ● |\n|       |\n| ●   ● |\n+-------+",
            "+-------+\n| ●   ● |\n|   ●   |\n| ●   ● |\n+-------+",
            "+-------+\n| ●   ● |\n| ●   ● |\n| ●   ● |\n+-------+"
        };
        System.out.println(diceFaces[number - 1]);
    }

    public static void printBoard(int player1Pos, int player2Pos) {
        System.out.println("\nSNAKE AND LADDER BOARD:");
        int snakeIndex = 1, ladderIndex = 1;
        Map<Integer, String> markers = new HashMap<>();

        // Mark ladders (start = bottom, end = top)
        for (Map.Entry<Integer, Integer> entry : ladders.entrySet()) {
            int start = entry.getKey();
            int end = entry.getValue();
            markers.put(start, "L" + ladderIndex);
            markers.put(end, "L" + ladderIndex);
            ladderIndex++;
        }

        // Mark snakes (start = head, end = tail)
        for (Map.Entry<Integer, Integer> entry : snakes.entrySet()) {
            int head = entry.getKey();
            int tail = entry.getValue();
            markers.put(head, "S" + snakeIndex);
            markers.put(tail, "S" + snakeIndex);
            snakeIndex++;
        }

        for (int i = BOARD_SIZE; i > 0; i--) {
            for (int j = 1; j <= BOARD_SIZE; j++) {
                int num = (i % 2 == 0) ? (i - 1) * BOARD_SIZE + (BOARD_SIZE - j + 1) : (i - 1) * BOARD_SIZE + j;
                String display = markers.getOrDefault(num, String.valueOf(num));

                if (num == player1Pos) display = "P1";
                if (num == player2Pos) display = "P2";

                System.out.printf("%-4s", display);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int player1Pos = 1, player2Pos = 1;
        List<Integer> player1Journey = new ArrayList<>();
        List<Integer> player2Journey = new ArrayList<>();

        System.out.println("🎲 Welcome to Snake and Ladder Game! 🎲");
        printBoard(player1Pos, player2Pos);

        while (player1Pos < WINNING_POSITION && player2Pos < WINNING_POSITION) {
            // Player 1's Turn
            System.out.println("\nPlayer 1, press Enter to roll the dice...");
            scanner.nextLine();
            int player1Dice = rollDice();
            printDice(player1Dice);
            System.out.println("🎲 Player 1 rolled: " + player1Dice);
            player1Pos = movePlayer(player1Pos, player1Dice, player1Journey);
            printBoard(player1Pos, player2Pos);
            System.out.println("📍 Player 1's new position: " + player1Pos);

            if (player1Pos == WINNING_POSITION) {
                System.out.println("🎉 Congratulations! Player 1 wins! 🎉");
                break;
            }

            // Player 2's Turn
            System.out.println("\nPlayer 2, press Enter to roll the dice...");
            scanner.nextLine();
            int player2Dice = rollDice();
            printDice(player2Dice);
            System.out.println("🎲 Player 2 rolled: " + player2Dice);
            player2Pos = movePlayer(player2Pos, player2Dice, player2Journey);
            printBoard(player1Pos, player2Pos);
            System.out.println("📍 Player 2's new position: " + player2Pos);

            if (player2Pos == WINNING_POSITION) {
                System.out.println("🎉 Congratulations! Player 2 wins! 🎉");
                break;
            }
        }

        // Display journey history
        System.out.println("\nDo you want to see the journey of a player? (Enter: p1/p2/none)");
        String choice = scanner.nextLine().toLowerCase();

        if (choice.equals("p1")) {
            System.out.println("📜 Player 1's journey: " + player1Journey);
        } else if (choice.equals("p2")) {
            System.out.println("📜 Player 2's journey: " + player2Journey);
        } else {
            System.out.println("Thank you for playing! 🎲");
        }

        scanner.close();
    }
}
