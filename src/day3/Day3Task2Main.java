package day3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import util.AdventUtils;

public class Day3Task2Main {

	public static void main(String[] args) {
		try {
			List<Claim> claims = AdventUtils.getStringInput(3).stream().map(line -> Claim.parseString(line))
					.collect(Collectors.toList());

			List<List<String>> fabric = calcFabricSize(claims);

			Set<Integer> notOverlappingClaims = claims.stream().map(claim -> claim.getId()).collect(Collectors.toSet());

			for (Claim claim : claims) {
				for (int y = claim.getMarginTopEdge(); y < claim.getHeight() + claim.getMarginTopEdge(); y++) {
					List<String> line = fabric.get(y);
					for (int x = claim.getMarginLeftEdge(); x < claim.getWidth() + claim.getMarginLeftEdge(); x++) {
						String point = line.get(x);

						if (point.equals(".")) {
							point = String.valueOf(claim.getId());
						} else {
							if (!point.equals("x")) {
								notOverlappingClaims.remove(Integer.valueOf(point));
							}
							notOverlappingClaims.remove(claim.getId());
							point = "x";
						}

						line.set(x, point);
					}
				}
			}

			if (notOverlappingClaims.size() != 1) {
				AdventUtils.publishResult(3, 1, "failure: " + notOverlappingClaims.size());
			} else {
				AdventUtils.publishResult(3, 1, new ArrayList<>(notOverlappingClaims).get(0));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static List<List<String>> calcFabricSize(List<Claim> claims) {
		List<List<String>> fabric = new ArrayList<>();

		int maxFabricHeight = 0;
		int maxFabricWidth = 0;

		for (Claim claim : claims) {
			int fabricHeight = claim.getHeight() + claim.getMarginTopEdge();
			int fabricWidth = claim.getWidth() + claim.getMarginLeftEdge();

			if (fabricHeight > maxFabricHeight) {
				maxFabricHeight = fabricHeight;
			}

			if (fabricWidth > maxFabricWidth) {
				maxFabricWidth = fabricWidth;
			}
		}

		for (int y = 0; y < maxFabricHeight; y++) {
			List<String> line = new ArrayList<>();
			fabric.add(line);
			for (int x = 0; x < maxFabricWidth; x++) {
				line.add(".");
			}
		}

		return fabric;
	}

}
