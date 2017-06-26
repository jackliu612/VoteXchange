import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameSquare extends JPanel {
	private boolean isSnake;

	public GameSquare() {
		isSnake = false;
	}

	public boolean isSnake() {
		return isSnake;
	}

	public void setSnake(boolean b) {
		isSnake = b;
	}
}
