package b2122.hw3;

import b2122.hw2.TicTacToe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.border.TitledBorder;

public class TicTacToe55849809 extends JFrame implements TicTacToe {

    private static JPanel contentPane;
    private static Player currentPlayer; //to keep track of who is playing currently
    private static int gridSize;         //to help keep track of the size of the grid across all fucntions
    private static JPanel titlePanel = new JPanel();
    private static JPanel buttonPanel = new JPanel();
    private static JLabel entry;
    private static JTextArea output;
    private static JTextField textField = new JTextField(10);
    private static String[][] board;
    private static JButton enterButton;
    private static JButton[][] buttons;
    private static TicTacToe55849809 frame;
    private static JLabel winLabel;
    private static JPanel score;
    int x, y = 0;

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    frame = new TicTacToe55849809();       //initialising the frame object
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public TicTacToe55849809() {
        setTitle("Shivi's TicTacToe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBounds(100, 100, 500, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);               //setting up content pane as container
        contentPane.setLayout(new BorderLayout(0, 0));
        entry = new JLabel("Enter size to start:");
        entry.setFont(new Font("Montserrat Light", Font.PLAIN, 15));
        entry.setHorizontalTextPosition(SwingConstants.LEFT);
        titlePanel.add(entry);
        titlePanel.add(textField);                              //titlepanel as container for entry and text input
        contentPane.add(titlePanel, BorderLayout.NORTH);
        enterButton = new JButton("Enter");
        enterButton.setFont(new Font("Montserrat Light", Font.PLAIN, 15));
        enterButton.addActionListener(new ActionListener() {               //after enter clicked, action to be performed
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    gridSize = Integer.parseInt(textField.getText());    //getting input from user
                    if (buttonPanel != null) {
                        contentPane.remove(buttonPanel);                 //buttonpanel not visible until initialised
                        output.selectAll();
                        output.replaceSelection("");
                    }
                    init(gridSize);                  //go to init function to initialize buttonpanel
                    output.setText("Output:" + " \nA " + Integer.toString(gridSize) + "x"
                            + Integer.toString(gridSize) + " game is started. Player X first.");
                    contentPane.add(buttonPanel, BorderLayout.CENTER);       //adding components to panel
                    contentPane.add(score, BorderLayout.EAST);
                    contentPane.repaint();
                } catch (IllegalArgumentException ie) {
                    music("wrong.wav");
                    output.setText("Output:"
                            + "\nEnter a valid grid size please!");
                    contentPane.remove(buttonPanel);           //in case inavlid size entered, empty contents
                    contentPane.remove(score);
                    contentPane.revalidate();
                    contentPane.repaint();
                }
            }
        });
        titlePanel.add(enterButton);
        output = new JTextArea("Output:");
        output.setFont(new Font("Montserrat Light", Font.PLAIN, 15));
        output.setEditable(false);                   //not allowing user to edit textarea
        contentPane.add(output, BorderLayout.SOUTH);
        winLabel = new JLabel("<html>X Wins: " + String.valueOf(x)
                + "<br/><br/><br/>O Wins: <html>" + String.valueOf(y), SwingConstants.CENTER);
        winLabel.setBorder(new TitledBorder("Scores"));
        score = new JPanel();
        score.setLayout(new GridLayout(2, 1));
        score.add(winLabel);
        JButton resetScore = new JButton("<html>Click here to<br/>reset scores!!<html>");
        resetScore.setFocusable(false);
        score.add(resetScore);
        resetScore.addActionListener(new ActionListener() {  //if this is clicked scores become 0,0 again for X,O

            @Override
            public void actionPerformed(ActionEvent e) {
                x = 0;
                y = 0;
                winLabel.setText("<html>X Wins: " + String.valueOf(x)
                        + "<br/><br/><br/>O Wins: <html>" + String.valueOf(y));
                winLabel.setVerticalAlignment(SwingConstants.CENTER);
                winLabel.setHorizontalAlignment(SwingConstants.CENTER);
            }

        });
        currentPlayer = Player.X;     //initializing the first player to be X
    }

    private void resetTheButtons() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    private void focus() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                buttons[i][j].setFocusable(false);
            }
        }
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    
    //to play music 
    
    private void music(String musicFile) {
        try {
            File music = new File(musicFile);
            if (music.exists()) {
                AudioInputStream musFile = AudioSystem.getAudioInputStream(music);
                Clip play = AudioSystem.getClip();
                play.open(musFile);
                play.start();
            } else {
                System.out.println("File unavailable");
            }
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    @Override
    public void init(int size) throws IllegalArgumentException {
        int k = 1;
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(size, size));
        if (size < 3) {
            throw new IllegalArgumentException();    //throw exception if grid size < 3
        } else {
            buttons = new JButton[size][size];
            board = new String[size][size];          //creating a new board for desired size
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    JButton temp = new JButton();
                    temp.setText(Integer.toString(k));
                    buttons[i][j] = temp;             //adding numbers to the buttons
                    buttons[i][j].addActionListener(new ActionListener() {  //performing action when button clicked
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JButton buttonClicked = (JButton) e.getSource();
                            mark(Integer.parseInt(buttonClicked.getText()));
                            if (getTurn() == Player.X) {
                                buttonClicked.setOpaque(true);
                                buttonClicked.setBackground(Color.pink);
                                buttonClicked.setText("O");
                                focus();
                                output.setText("Output:"
                                        + " \nPlayer X's turn.");
                                buttonClicked.setEnabled(false);
                            } else {
                                buttonClicked.setOpaque(true);
                                buttonClicked.setBackground(new Color(176, 216, 230));
                                buttonClicked.setText("X");
                                focus();
                                output.setText("Output:"
                                        + " \nPlayer O's turn.");
                                buttonClicked.setEnabled(false);
                            }
                            if (hasWinner()) {
                                music("win.wav");
                                output.setText("Output:"
                                        + " \nThe winner is Player " + getWinner() + " !!!");
                                resetTheButtons();
                                if (getWinner() == Player.X) {
                                    winLabel.setText("<html>X Wins: " + String.valueOf(++x)
                                            + "<br/><br/><br/>O Wins: <html>" + String.valueOf(y));
                                } else if (getWinner() == Player.O) {
                                    winLabel.setText("<html>X Wins: " + String.valueOf(x)
                                            + "<br/><br/><br/>O Wins: <html>" + String.valueOf(++y));
                                }
                            } else if (hasNext()) {
                                currentPlayer = getTurn();
                            } else {
                                music("whenDraw.wav");
                                output.setText("Output:"
                                        + " \nDraw game!");
                            }

                        }
                    });
                    buttonPanel.add(temp);
                    board[i][j] = Integer.toString(k);    //initializing the values on the board
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
                if (j != gridSize - 1) {    //this condition to make sure we don't have extra "|"
                    System.out.print("|");
                }
            }
            System.out.println();
        }
    }

    @Override
    public boolean hasWinner() {
        //the three functions here have been defined below
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
