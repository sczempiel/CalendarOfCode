package day12;

public class Pot {
	private long position;
	private boolean isPlanted;

	public Pot() {
	}

	public Pot(long position, boolean isPlanted) {
		this.position = position;
		this.isPlanted = isPlanted;
	}

	public long getPosition() {
		return position;
	}

	public void setPosition(long position) {
		this.position = position;
	}

	public boolean isPlanted() {
		return isPlanted;
	}

	public void setPlanted(boolean isPlanted) {
		this.isPlanted = isPlanted;
	}

}
