import java.util.ArrayList;

public class Snake {
	private int xVelocity;
	private int yVelocity;
	private ArrayList<Block> body;

	public Snake(int x, int y) {
		xVelocity = 1;
		yVelocity = 0;
		body = new ArrayList<Block>();
		body.add(new Block(x, y));
	}

	public void grow() {
		for (int i = 0; i < 3; i++) {
			body.add(new Block(body.get(body.size() - 1).getX(), body.get(body.size() - 1).getY()));
		}
	}

	public void move() {
		for (int i = body.size() - 1; i > 0; i--) {
			body.get(i).setX(body.get(i - 1).getX());
			body.get(i).setY(body.get(i - 1).getY());
		}
		body.get(0).setX(body.get(0).getX() + xVelocity);
		body.get(0).setY(body.get(0).getY() + yVelocity);
	}

	public int getxVelocity() {
		return xVelocity;
	}

	public int getyVelocity() {
		return yVelocity;
	}

	public int getX() {
		return body.get(0).getX();
	}

	public int getY() {
		return body.get(0).getY();
	}

	public ArrayList<Block> getBody() {
		return body;
	}

	public void setX(int x) {
		body.get(0).setX(x);
	}

	public void setY(int y) {
		body.get(0).setY(y);
	}

	public void setXVelocity(int x) {
		xVelocity = x;
	}

	public void setYVelocity(int y) {
		yVelocity = y;
	}

}
