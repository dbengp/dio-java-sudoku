package org.example;

import java.util.Random;
import java.util.Scanner;

public class SudokuGame {

    private static final int SIZE = 9;
    private static final int EMPTY = 0;
    private static final Random random = new Random();

    public static void main(String[] args) {
        int[][] board = generateSudokuBoard();
        playSudoku(board);
    }

    private static int[][] generateSudokuBoard() {
        int[][] board = new int[SIZE][SIZE];
        fillDiagonalBlocks(board);
        solveSudoku(board);
        removeNumbers(board);
        return board;
    }

    private static void fillDiagonalBlocks(int[][] board) {
        for (int i = 0; i < SIZE; i += 3) {
            fillBlock(board, i, i);
        }
    }

    private static void fillBlock(int[][] board, int row, int col) {
        int num;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                do {
                    num = random.nextInt(SIZE) + 1;
                } while (!isValidInBlock(board, row, col, num));
                board[row + i][col + j] = num;
            }
        }
    }

    private static boolean isValidInBlock(int[][] board, int row, int col, int num) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[row + i][col + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean solveSudoku(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValidMove(board, row, col, num)) {
                            board[row][col] = num;

                            if (solveSudoku(board)) {
                                return true;
                            }

                            board[row][col] = EMPTY;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static void removeNumbers(int[][] board) {
        int numbersToRemove = 40;
        while (numbersToRemove > 0) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);

            if (board[row][col] != EMPTY) {
                board[row][col] = EMPTY;
                numbersToRemove--;
            }
        }
    }

    private static void playSudoku(int[][] board) {
        Scanner scanner = new Scanner(System.in);

        while (!isBoardComplete(board)) {
            printBoard(board);

            System.out.print("Digite a linha (1-9): ");
            int row = scanner.nextInt() - 1;

            System.out.print("Digite a coluna (1-9): ");
            int col = scanner.nextInt() - 1;

            System.out.print("Digite o número (1-9): ");
            int num = scanner.nextInt();

            if (isValidMove(board, row, col, num)) {
                board[row][col] = num;
                System.out.println("Movimento válido!");
            } else {
                System.out.println("Movimento inválido. Tente novamente.");
            }
        }

        System.out.println("Parabéns! Você completou o Sudoku!");
        printBoard(board);
    }

    private static void printBoard(int[][] board) {
        System.out.println("-----------------------------");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] == EMPTY ? " . " : " " + board[i][j] + " ");
                if ((j + 1) % 3 == 0 && j < SIZE - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if ((i + 1) % 3 == 0 && i < SIZE - 1) {
                System.out.println("-----------------------------");
            }
        }
        System.out.println("-----------------------------");
    }

    private static boolean isValidMove(int[][] board, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean isBoardComplete(int[][] board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}