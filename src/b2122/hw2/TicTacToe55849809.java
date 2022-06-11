/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package b2122.hw2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author shivitiwari
 * NAME: Shivi TIWARI
 * SID: 55849809
 */
public class TicTacToe55849809 implements TicTacToe {

    private Player currentPlayer; //to keep track of who is playing currently
    private String[][] board;      //initializing a board for playing the game
    private int gridSize;         //to help keep track of the size of the grid across all fucntions

    public TicTacToe55849809() {
        board = new String[4][4];
        currentPlayer = Player.X;     //initializing the first player to be X
    }

    @Override
    public void init(int size) throws IllegalArgumentException {
        int k = 1;
        if (size < 3) {
            throw new IllegalArgumentException();    //throw exception if grid size < 3
        } else {
            gridSize = size;
            board = new String[size][size];          //creating a new board for desired size
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    board[i][j] = Integer.toString(k);    //initializing the values on the baord
                    k++;
                }
            }
        }
    }

    @Override
    public boolean hasNext() {
        //if a winner has been determined then the function returns false
        if (hasWinner()) {
            return false;
        }
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                //if the string isnt equal to X or O, the game continues
                if (!"X".equals(board[i][j]) && !"O".equals(board[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Player getTurn() {
        return currentPlayer;
    }

    @Override
    public void mark(int pos) throws IllegalArgumentException {
        //throw exception in case the position entered doesnt fall in this range
        if (pos <= 0 || pos > (gridSize * gridSize)) {
            throw new IllegalArgumentException();
        }
        boolean check = false;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (board[i][j].equals(Integer.toString(pos))) {
                    if (currentPlayer == Player.X) {         //making the actual marking on the grid
                        board[i][j] = "X";
                    } else if (currentPlayer == Player.O) {
                        board[i][j] = "O";
                    }
                    check = true;
                }
            }
        }
        //if the input number has already been occupied
        if (check == false) {
            throw new IllegalArgumentException();
        }
        //changing the currentplayer's chance
        if (getTurn() == Player.X) {
            currentPlayer = Player.O;
        } else {
            currentPlayer = Player.X;
        }
    }

    @Override
    public void print() {
        int number = 0;
        int squareOfNum = gridSize * gridSize;
        while (squareOfNum != 0) {
            squareOfNum /= 10;
            number += 1;
        }
        String format = "%" + number + "s";
        //Printing the actual board
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.printf(format, board[i][j]);
                if (j != gridSize - 1) {    //this condition to make sure we dont have extra "|"
                    System.out.print("|");
                }
            }
            System.out.println();
        }
    }

    @Override
    public boolean hasWinner() {
        //the three fucntions here have been defined below
        if (checkRows() || checkColumns() || checkDiagonal()) {
            return true;
        }
        return false;
    }

    @Override
    public Player getWinner() throws IllegalStateException {
        if (hasWinner()) {
            if (null == getTurn()) {
                throw new IllegalStateException();
            } else {
                switch (getTurn()) {
                    case X:
                        return Player.O;
                    case O:
                        return Player.X;
                    default:
                        throw new IllegalStateException();
                }
            }
        } else {
            throw new IllegalStateException();
        }
    }

    private boolean checkRows() {
        //array list stores the characters from each row 
        List<String> store = new ArrayList<>();
        for (int i = 0; i < gridSize; i++) {
            store.clear();
            for (int j = 0; j < gridSize; j++) {
                store.add(board[i][j]);
            }
            //this function determines if the stored chars of a particular row are equal
            //the function checkChar has been defined below
            if (checkChar(store)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns() {
        //store stroes the different elements of a particular columns
        List<String> store = new ArrayList<>();
        for (int i = 0; i < gridSize; i++) {
            store.clear();
            for (int j = 0; j < gridSize; j++) {
                store.add(board[j][i]);
            }
            //here the checking happens if whether or not all elements of a particular 
            //column are equal
            if (checkChar(store)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonal() {
        //here we store and check different elements from the main and
        //secondary diagonal
        List<String> storeMainDiagonal = new ArrayList<>();
        List<String> storeSecondDiagonal = new ArrayList<>();

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (i == j) {
                    storeMainDiagonal.add(board[i][j]);
                }
                if (i + j == (gridSize - 1)) {
                    storeSecondDiagonal.add(board[i][j]);
                }
            }
        }
        if (checkChar(storeMainDiagonal)) {
            return true;
        }
        if (checkChar(storeSecondDiagonal)) {
            return true;
        }
        return false;
    }

    private boolean checkChar(List<String> arr) {
        //this function is responsible for storing different elements and returning true if all of 
        //them are same since set does not store duplicate elements
        Set<String> set = new HashSet<>();
        for (String s : arr) {
            set.add(s);
        }
        return (set.size() == 1);
    }

}
