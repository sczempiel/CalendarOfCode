package day24;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import util.AdventUtils;

public class Day24Task2Main {

	private static final String INF_TARGET = "Infection group %d would deal defending group %d %d damage";
	private static final String IMMUNE_TARGET = "Immune System group %d would deal defending group %d %d damage";
	private static final String INF_ATTACK = "Infection group %d attacks defending group %d, killing %d units";
	private static final String IMMUNE_ATTACK = "Immune System group %d attacks defending group %d, killing %d units";
	private static final String COUNT_UNITS = "Group %d contains %d units";

	private static List<Units> immuneSystem;
	private static List<Units> infection;
	private static List<Units> allUnits;
	private static int boost = 0;

	private static final boolean debug = false;

	public static void main(String[] args) {

		try {
			List<String> input = AdventUtils.getStringInput(24);
			int totalHP = 0;
			while (infection == null || infection.size() > 0) {
				boost++;
				System.out.println(boost);
				immuneSystem = new ArrayList<>();
				infection = new ArrayList<>();
				allUnits = new ArrayList<>();

				boolean isImmuneSystem = true;
				int id = 0;
				for (int j = 1; j < input.size(); j++) {
					String line = input.get(j);
					if (line.isEmpty()) {
						continue;
					}
					if (line.startsWith("Infection:")) {
						isImmuneSystem = false;
						continue;
					}
					String[] parts = line.split(" units each with ");
					int unitCount = Integer.parseInt(parts[0]);

					parts = parts[1].split(" hit points ");
					int hitPoints = Integer.parseInt(parts[0]);

					List<DamageType> weakness = new ArrayList<>();
					List<DamageType> immunity = new ArrayList<>();
					if (parts[1].startsWith("(")) {
						parts = parts[1].substring(1).split("\\) ");
						String[] typeDef = parts[0].split(";");
						if (typeDef[0].startsWith("immune")) {
							String[] immunes = typeDef[0].substring(10).split(", ");
							for (String i : immunes) {
								immunity.add(DamageType.valueOf(i));
							}
						} else {
							String[] weaks = typeDef[0].substring(8).split(", ");
							for (String w : weaks) {
								weakness.add(DamageType.valueOf(w));
							}
						}
						if (typeDef.length == 2) {
							if (typeDef[1].startsWith(" immune")) {
								String[] immunes = typeDef[1].substring(11).split(", ");
								for (String i : immunes) {
									immunity.add(DamageType.valueOf(i));
								}
							} else {
								String[] weaks = typeDef[1].substring(9).split(", ");
								for (String w : weaks) {
									weakness.add(DamageType.valueOf(w));
								}
							}
						}
					}

					parts = parts[1].substring(25).split(" damage at initiative ");

					int init = Integer.parseInt(parts[1]);

					parts = parts[0].split(" ");

					int dmg = Integer.parseInt(parts[0]);
					DamageType dmgType = DamageType.valueOf(parts[1]);

					Units units = new Units(id++, isImmuneSystem);
					units.setHitPoints(hitPoints);
					units.setCount(unitCount);
					units.setDamage(dmg);
					units.setDmgType(dmgType);
					units.setImmune(immunity);
					units.setWeakness(weakness);
					units.setInit(init);

					if (isImmuneSystem) {
						immuneSystem.add(units);
						units.setDamage(units.getDamage() + boost);
					} else {
						infection.add(units);
					}
					allUnits.add(units);
				}

				int last = allUnits.stream().mapToInt(Units::getCount).sum();
				while (immuneSystem.size() > 0 && infection.size() > 0) {
					turn();
					totalHP = allUnits.stream().mapToInt(Units::getCount).sum();
					if(last == totalHP) {
						break;
					}
					last = totalHP;
				}
			}

			System.out.println("---------------");

			AdventUtils.publishResult(24, 1, totalHP);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void turn() {
		allUnits = allUnits.stream().filter(units -> units.getCount() > 0).sorted(new TargetSelectionComparator())
				.collect(Collectors.toList());
		if (debug) {
			System.out.println("Immune System:");
			for (Units units : immuneSystem) {
				Formatter format = new Formatter();
				System.out.println(format.format(COUNT_UNITS, units.getId(), units.getCount()));
				format.close();
			}
			System.out.println("Infection:");
			for (Units units : infection) {
				Formatter format = new Formatter();
				System.out.println(format.format(COUNT_UNITS, units.getId(), units.getCount()));
				format.close();
			}
			System.out.println("");
		}
		Map<Integer, Units> unitTargets = new HashMap<>();
		for (Units units : allUnits) {

			Units currentTarget = null;
			List<Units> targets;

			if (units.isImmuneSystem()) {
				targets = infection;
			} else {
				targets = immuneSystem;
			}

			for (Units target : targets) {
				int damage = calcDmg(units, target);
				if (unitTargets.containsValue(target) || damage == 0) {
					continue;
				}

				if (debug) {
					String text = null;
					Formatter format = new Formatter();
					if (units.isImmuneSystem()) {
						text = IMMUNE_TARGET;
					} else {
						text = INF_TARGET;
					}
					System.out.println(format.format(text, units.getId(), target.getId(), damage));
					format.close();
				}

				if (currentTarget == null) {
					currentTarget = target;
					continue;
				}

				int damageCurrent = calcDmg(units, currentTarget);
				if (damage > damageCurrent) {
					currentTarget = target;
				} else if (damage < damageCurrent) {
					continue;
				}

				int effecDamage = target.getEffectivePower();
				int effecDamageCurrent = currentTarget.getEffectivePower();
				if (effecDamage > effecDamageCurrent) {
					currentTarget = target;
				} else if (effecDamage < effecDamageCurrent) {
					continue;
				}

				if (target.getInit() > currentTarget.getInit()) {
					currentTarget = target;
				}
			}

			unitTargets.put(units.getId(), currentTarget);
		}

		Collections.sort(allUnits, new AttackComparator());
		if (debug) {
			System.out.println("");
		}

		for (Units units : allUnits) {
			Units target = unitTargets.get(units.getId());

			if (target == null) {
				continue;
			}
			if (debug) {
				String text = null;
				Formatter format = new Formatter();
				if (units.isImmuneSystem()) {
					text = IMMUNE_ATTACK;
				} else {
					text = INF_ATTACK;
				}
				System.out.println(format.format(text, units.getId(), target.getId(), attack(units, target)));
				format.close();
			} else {
				attack(units, target);
			}
		}

		if (debug) {
			System.out.println("---------------");
		}
		immuneSystem = immuneSystem.stream().filter(units -> units.getCount() > 0).collect(Collectors.toList());
		infection = infection.stream().filter(units -> units.getCount() > 0).collect(Collectors.toList());
	}

	private static int calcDmg(Units attack, Units defend) {
		if (defend.getImmune().contains(attack.getDmgType())) {
			return 0;
		}
		int mult = 1;
		if (defend.getWeakness().contains(attack.getDmgType())) {
			mult = 2;
		}

		return attack.getCount() * attack.getDamage() * mult;
	}

	private static int attack(Units attack, Units defend) {
		int dmg = calcDmg(attack, defend);

		int killed = 0;
		while (dmg >= defend.getHitPoints() && defend.getCount() > 0) {
			dmg -= defend.getHitPoints();
			defend.setCount(defend.getCount() - 1);
			killed++;
		}
		return killed;
	}

}
