package exercise.chapter2;

public class Apple {
	private Color color;
	private Integer weight;

	public Apple(Integer weight, Color color) {
		super();
		this.color = color;
		this.weight = weight;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
