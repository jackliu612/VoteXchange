public class Block {
	private int xPos;
	private int yPos;

	public Block(int x, int y) {
		xPos = x;
		yPos = y;
	}

	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	public void setX(int x) {
		xPos = x;
	}

	public void setY(int y) {
		yPos = y;
	}
}
