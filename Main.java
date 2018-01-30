
public class Main {
	
	public static void main(String args[])
	{
		String filename = "toy0.txt";
		if(args.length > 0)
		{
			filename = args[0];
		}
		
		Butin b = new Butin();
		b.loadFromFile(filename);
		b.print();
		System.out.println("Lancement de l'algorithme");
		b.solve();
	}
}
