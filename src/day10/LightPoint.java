package day10;

public class LightPoint {
	private Point position;
	private Point velocity;

	public static LightPoint parseString(String raw) {
		LightPoint lightPoint = new LightPoint();
		String[] parts = raw.split("<");
		String[] position = parts[1].split(">")[0].split(",");
		String[] velocity = parts[2].substring(0, parts[2].length() - 1).split(",");

		lightPoint.setPosition(new Point(Integer.parseInt(position[0].trim()), Integer.parseInt(position[1].trim())));
		lightPoint.setVelocity(new Point(Integer.parseInt(velocity[0].trim()), Integer.parseInt(velocity[1].trim())));

		return lightPoint;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Point getVelocity() {
		return velocity;
	}

	public void setVelocity(Point velocity) {
		this.velocity = velocity;
	}

	@Override
	public String toString() {
		return "LightPoint [position=" + position + ", velocity=" + velocity + "]";
	}
}
