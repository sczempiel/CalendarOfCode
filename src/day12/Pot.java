package day12;

public class Pot {
	private int position;
	private boolean isPlanted;

	public Pot() {
	}

	public Pot(int position, boolean isPlanted) {
		this.position = position;
		this.isPlanted = isPlanted;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean isPlanted() {
		return isPlanted;
	}

	public void setPlanted(boolean isPlanted) {
		this.isPlanted = isPlanted;
	}

}
