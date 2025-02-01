package com.jornrigter;

import com.jornrigter.moves.InvalidMoveException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Mancala {

    private static final String LINE_SEPARATOR = "\n====================================================================\n";
    private static final String MOVE_SEPARATOR = "\n--------------------------------------------------------------------\n";

    private static final int INVALID_CHOICE = -1;
    private static final int END_GAME = 0;
    private static final int START_NEW_GAME = 1;
    private static final int EXIT_PROGRAM = 2;

    private static final List<Integer> setupOptions = Arrays.asList(START_NEW_GAME, EXIT_PROGRAM);
    private static final List<Integer> holeOptions = Arrays.asList(END_GAME, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);

    private static MancalaBoard mancalaBoard;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        // inGame is an array so that we can set it to false from gameEnded callback below
        final boolean[] inGame = {true};
        while (running) {
            showIntro();
            int choice = getChoice(scanner, setupOptions);
            if (choice == START_NEW_GAME) {
                inGame[0] = true;
                mancalaBoard = new MancalaBoard(new MancalaBoard.OnGameEndedListener() {
                    @Override
                    public void gameEnded() {
                        mancalaBoard.showEndScore();
                        inGame[0] = false;
                    }
                });
                mancalaBoard.printPretty();
                while (inGame[0]) {
                    System.out.println(MOVE_SEPARATOR);
                    choice = getChoice(scanner, holeOptions);
                    if (choice == END_GAME) {
                        inGame[0] = false;
                    } else if (choice != INVALID_CHOICE) {
                        try {
                            mancalaBoard.makeMove(choice);
                            if (mancalaBoard.canPlayerMakeMove()) {
                                mancalaBoard.printPretty();
                            }
                        } catch (InvalidMoveException e) {
                            System.out.println("INVALID MOVE: " + e.getType().getErrorMessage());
                        }
                    }
                }
            } else if (choice == END_GAME) {
                running = false;
            }
        }

        scanner.close();
    }

    private static void showIntro() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Welcome to Mancala!");
        System.out.println("1. Start Game");
        System.out.println("2. Exit Program");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice(Scanner scanner, List<Integer> options) {
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (options.contains(choice)) {
                return choice;
            } else {
                System.out.println("\nPlease choose a number that's in the range " + Arrays.toString(options.toArray()));
                return INVALID_CHOICE;
            }
        } catch (NumberFormatException e) {
            System.out.println("\nInvalid input! Please enter a number.");
            return INVALID_CHOICE;
        }
    }

}
