package day15;

public class Fighter implements Comparable<Fighter> {

	private final int id;
	private int hitpoints = 200;
	// there are only elves and goblins
	private final boolean goblin;
	private Tile tile;
	private int damage = 3;

	public Fighter(int id, boolean goblin) {
		this.id = id;
		this.goblin = goblin;
	}

	public boolean hit(int damage) {
		hitpoints -= damage;
		return isAlive();
	}

	public boolean isAlive() {
		return hitpoints > 0;
	}

	@Override
	public int compareTo(Fighter that) {
		return this.tile.compareTo(that.tile);
	}

	public int getId() {
		return id;
	}

	public boolean isGoblin() {
		return goblin;
	}

	public int getHitpoints() {
		return hitpoints;
	}

	public void setHitpoints(int hitpoints) {
		this.hitpoints = hitpoints;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Fighter [id=");
		builder.append(id);
		builder.append(", hitpoints=");
		builder.append(hitpoints);
		builder.append(", goblin=");
		builder.append(goblin);
		builder.append(", ");
		if (tile != null) {
			builder.append("tile=");
			builder.append(tile.getY());
			builder.append(",");
			builder.append(tile.getX());
		}
		builder.append("]");
		return builder.toString();
	}

}
