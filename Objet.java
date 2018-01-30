
public class Objet implements Comparable<Objet>{
	private int weight;
	private int value;
	private double ratio;
	
	public Objet(int w, int v)
	{
		weight = w;
		value = v;
		ratio = (double) v / w;
	}

	public int getWeight() {
		return weight;
	}

	public int getValue() {
		return value;
	}

	public double getRatio() {
		return ratio;
	}	
	
	@Override
	public String toString() {
		return "Objet [weight=" + weight + ", value=" + value + ", ratio=" + ratio + "]";
	}

	@Override
	public int compareTo(Objet o)
	{
		if(this.ratio > o.ratio)//Si ratio plus grand alors avant l'objet
			return -1;
		else if(this.ratio < o.ratio)//Si ratio plus petit alors après l'objet
			return 1;
		else//Sinon ils sont égaux
			return 0;
	}
}
