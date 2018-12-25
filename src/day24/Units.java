package day24;

import java.util.ArrayList;
import java.util.List;

public class Units {
	private final int id;
	private final boolean immuneSystem;

	private int count;
	private int hitPoints;
	private int damage;
	private DamageType dmgType;
	private int init;

	private List<DamageType> weakness = new ArrayList<>();
	private List<DamageType> immune = new ArrayList<>();

	public Units(int id, boolean immuneSystem) {
		this.id = id;
		this.immuneSystem = immuneSystem;
	}

	public int getEffectivePower() {
		return count * damage;
	}

	public int getId() {
		return id;
	}

	public boolean isImmuneSystem() {
		return immuneSystem;
	}

	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public DamageType getDmgType() {
		return dmgType;
	}

	public void setDmgType(DamageType dmgType) {
		this.dmgType = dmgType;
	}

	public int getInit() {
		return init;
	}

	public void setInit(int init) {
		this.init = init;
	}

	public List<DamageType> getWeakness() {
		return weakness;
	}

	public void setWeakness(List<DamageType> weakness) {
		this.weakness = weakness;
	}

	public List<DamageType> getImmune() {
		return immune;
	}

	public void setImmune(List<DamageType> immune) {
		this.immune = immune;
	}

	public int getHitPoints() {
		return hitPoints;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Units [count=");
		builder.append(count);
		builder.append(", hitPoints=");
		builder.append(hitPoints);
		builder.append(", damage=");
		builder.append(damage);
		builder.append(", ");
		if (dmgType != null) {
			builder.append("dmgType=");
			builder.append(dmgType);
			builder.append(", ");
		}
		builder.append("init=");
		builder.append(init);
		builder.append(", ");
		if (weakness != null) {
			builder.append("weakness=");
			builder.append(weakness);
			builder.append(", ");
		}
		if (immune != null) {
			builder.append("immune=");
			builder.append(immune);
		}
		builder.append("]");
		return builder.toString();
	}

}
