import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class Driver extends JFrame {

	private final static int XSIZE = 25;
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static double width = screenSize.getWidth();
	static double height = screenSize.getHeight();
	private final static int YSIZE = (int) (XSIZE * width / height);
	private static int score, highScore;
	private JPanel contentPane;
	private static GameSquare[][] board;
	private static Timer timer;
	private static Snake snake;
	private static Block food;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Driver frame = new Driver();
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
	public Driver() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 1000, 1000);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(XSIZE, YSIZE, 0, 0));
		board = new GameSquare[XSIZE][YSIZE];
		for (int r = 0; r < XSIZE; r++) {
			for (int c = 0; c < YSIZE; c++) {
				board[r][c] = new GameSquare();
				board[r][c].setBackground(Color.WHITE);
				contentPane.add(board[r][c]);
			}
		}
		timer = new Timer(50, new Listener());
		snake = new Snake(XSIZE / 2, YSIZE / 2);
		score = 0;
		highScore = 0;
		createFood();
		addKeyListener(new Key());
		timer.start();
	}

	public void runGame() {
		clear();
		repaint();
		snake.move();
		rebound();
		if (isCollision(snake.getX(), snake.getY())) {
			gameOver();
		}
		foodCheck();
		draw();
	}

	private void gameOver() {
		// TODO Auto-generated method stub
		if (score > highScore) {
			highScore = score;
		}
		Object[] options = { "Play Again?", "Exit" };
		int n = JOptionPane.showOptionDialog(this, "Score: " + score + "\nHigh Score: " + highScore, "Game Over",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (n == 0) {
			for (Block b : snake.getBody()) {
				board[b.getX()][b.getY()].setBackground(Color.WHITE);
				board[b.getX()][b.getY()].setSnake(false);
			}
			snake = new Snake(XSIZE / 2, YSIZE / 2);
			score = 1;
		} else
			System.exit(0);
	}

	private void clear() {
		// TODO Auto-generated method stub
		board[snake.getBody().get(snake.getBody().size() - 1).getX()][snake.getBody().get(snake.getBody().size() - 1)
				.getY()].setBackground(Color.WHITE);
		board[snake.getBody().get(snake.getBody().size() - 1).getX()][snake.getBody().get(snake.getBody().size() - 1)
				.getY()].setSnake(false);
	}

	private static void draw() {
		// TODO Auto-generated method stub
		for (Block b : snake.getBody()) {
			board[b.getX()][b.getY()].setBackground(Color.BLACK);
			board[b.getX()][b.getY()].setSnake(true);
		}
		board[food.getX()][food.getY()].setBackground(Color.GREEN);
	}

	private static void foodCheck() {
		// TODO Auto-generated method stub
		if (snake.getX() == food.getX() && snake.getY() == food.getY()) {
			snake.grow();
			score++;
			createFood();
		}
	}

	private static void rebound() {
		// TODO Auto-generated method stub
		if (snake.getX() == XSIZE)
			snake.setX(0);
		if (snake.getX() == -1)
			snake.setX(XSIZE - 1);
		if (snake.getY() == YSIZE)
			snake.setY(0);
		if (snake.getY() == -1)
			snake.setY(YSIZE - 1);
	}

	private static void createFood() {
		int x = (int) (Math.random() * XSIZE);
		int y = (int) (Math.random() * YSIZE);
		while (isCollision(x, y) && x != food.getX() && y != food.getY()) {
			x = (int) (Math.random() * XSIZE);
			y = (int) (Math.random() * YSIZE);
		}
		food = new Block(x, y);
	}

	private static boolean isCollision(int x, int y) {
		return board[x][y].isSnake();
	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			runGame();
			repaint();
		}
	}

	private static class Key extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			// if (e.getKeyCode() == KeyEvent.VK_W) {
			// }
			// if (e.getKeyCode() == KeyEvent.VK_S) {
			// }
			// if (e.getKeyCode() == KeyEvent.VK_A) {
			// }
			// if (e.getKeyCode() == KeyEvent.VK_D) {
			// }
			Block b;
			if (snake.getBody().size() == 1) {
				b = new Block(-1, -1);
			} else {
				b = snake.getBody().get(1);
			}

			if (b.getX() != snake.getX() - 1 && e.getKeyCode() == KeyEvent.VK_UP
					&& (snake.getxVelocity() != 1 || snake.getBody().size() == 1)) {
				snake.setXVelocity(-1);
				snake.setYVelocity(0);
			}
			if (b.getX() != snake.getX() + 1 && e.getKeyCode() == KeyEvent.VK_DOWN
					&& (snake.getxVelocity() != -1 || snake.getBody().size() == 1)) {
				snake.setXVelocity(1);
				snake.setYVelocity(0);
			}
			if (b.getY() != snake.getY() - 1 && e.getKeyCode() == KeyEvent.VK_LEFT
					&& (snake.getyVelocity() != 1 || snake.getBody().size() == 1)) {
				snake.setXVelocity(0);
				snake.setYVelocity(-1);
			}
			if (b.getY() != snake.getY() + 1 && e.getKeyCode() == KeyEvent.VK_RIGHT
					&& (snake.getyVelocity() != -1 || snake.getBody().size() == 1)) {
				snake.setXVelocity(0);
				snake.setYVelocity(1);
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
		}
	}
}
