import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class Game {
    private JFrame frame;
    private JButton[][] buttons;
    private boolean isSinglePlayer;
    private char currentPlayer;

    public Game() {
        String[] options = {"Single Player (vs AI)", "Multiplayer (2 players)"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose game mode:",
                "Game Mode",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        isSinglePlayer = (choice == 0);
        currentPlayer = 'X';

        frame = new JFrame("Tic Tac Toe");
        frame.setLayout(new GridLayout(3, 3));
        buttons = new JButton[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].addActionListener(this::handleButtonClick);
                frame.add(buttons[i][j]);
            }
        }

        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void handleButtonClick(ActionEvent e) {
        JButton button = (JButton) e.getSource();


        if (!button.getText().isEmpty()) return;

        button.setText(String.valueOf(currentPlayer));

        if (checkWin(currentPlayer)) {
            JOptionPane.showMessageDialog(frame, "Player " + currentPlayer + " wins!");
            resetBoard();
            return;
        }

        if (isBoardFull()) {
            JOptionPane.showMessageDialog(frame, "It's a draw!");
            resetBoard();
            return;
        }

        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

        if (isSinglePlayer && currentPlayer == 'O') {
            aiMove();
        }
    }

    private void aiMove() {
        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!buttons[row][col].getText().isEmpty());

        buttons[row][col].setText("O");

        if (checkWin('O')) {
            JOptionPane.showMessageDialog(frame, "Computer wins!");
            resetBoard();
            return;
        }

        if (isBoardFull()) {
            JOptionPane.showMessageDialog(frame, "It's a draw!");
            resetBoard();
            return;
        }

        currentPlayer = 'X';
    }

    private boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if ((buttons[i][0].getText().equals(String.valueOf(player)) &&
                    buttons[i][1].getText().equals(String.valueOf(player)) &&
                    buttons[i][2].getText().equals(String.valueOf(player))) ||
                    (buttons[0][i].getText().equals(String.valueOf(player)) &&
                            buttons[1][i].getText().equals(String.valueOf(player)) &&
                            buttons[2][i].getText().equals(String.valueOf(player)))) {
                return true;
            }
        }

        return (buttons[0][0].getText().equals(String.valueOf(player)) &&
                buttons[1][1].getText().equals(String.valueOf(player)) &&
                buttons[2][2].getText().equals(String.valueOf(player))) ||
                (buttons[0][2].getText().equals(String.valueOf(player)) &&
                        buttons[1][1].getText().equals(String.valueOf(player)) &&
                        buttons[2][0].getText().equals(String.valueOf(player)));
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) return false;
            }
        }
        return true;
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        currentPlayer = 'X';
    }

    public static void main(String[] args) {
        new Game();
    }
}
