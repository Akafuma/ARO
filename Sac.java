import java.util.ArrayList;

public class Sac {
	private ArrayList<Boolean> I;
	private int maxSize;
	//stocker le poids et la valeur serait utile
	
	public ArrayList<Boolean> getI()
	{
		return I;
	}

	@Override
	public String toString() {
		String s = "Sac [\n";
		int i;
		for(i = 0; i < I.size(); i++)
			s += "\t" + I.get(i) + "\n";
		s += "]";
		return s;
	}

	public Sac(int size)
	{
		I = new ArrayList<Boolean>();
		maxSize = size;
	}

	private Sac(Sac s)
	{
		I = new ArrayList<Boolean>(s.I);
		maxSize = s.maxSize;		
	}
	
	public Sac branch0()
	{
		if(I.size() >= maxSize) // => algorithme incorrecte
		{
			System.out.println("Feuille atteinte");
			return null;
		}
		
		Sac b0 = new Sac(this);
		b0.I.add(false);
		return b0;
	}
	
	public Sac branch1()
	{
		if(I.size() >= maxSize)
		{
			System.out.println("Feuille atteinte");
			return null;
		}
		
		Sac b1 = new Sac(this);
		b1.I.add(true);
		return b1;
	}
}
