package day3;

public class Claim {
	private int id;
	private int marginLeftEdge;
	private int marginTopEdge;
	private int width;
	private int height;

	public static Claim parseString(String raw) {
		Claim claim = new Claim();
		String[] parts = raw.split(" ");

		claim.setId(Integer.valueOf(parts[0].substring(1, parts[0].length())));

		String[] margins = parts[2].substring(0, parts[2].length() - 1).split(",");
		claim.setMarginLeftEdge(Integer.valueOf(margins[0]));
		claim.setMarginTopEdge(Integer.valueOf(margins[1]));

		String[] square = parts[3].split("x");
		claim.setWidth(Integer.valueOf(square[0]));
		claim.setHeight(Integer.valueOf(square[1]));

		return claim;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Claim other = (Claim) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMarginLeftEdge() {
		return marginLeftEdge;
	}

	public void setMarginLeftEdge(int marginLeftEdge) {
		this.marginLeftEdge = marginLeftEdge;
	}

	public int getMarginTopEdge() {
		return marginTopEdge;
	}

	public void setMarginTopEdge(int marginTopEdge) {
		this.marginTopEdge = marginTopEdge;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
