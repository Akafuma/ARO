import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class Butin {
	private ArrayList<Objet> t;
	private int tailleSac;
	
	private int getTailleSac()
	{
		return tailleSac;
	}
	
	public void print()
	{
		System.out.println("Butin : ");
		for(int i = 0; i < t.size(); i++)
		{
			System.out.println("\t" + t.get(i));
		}
	}
	
	//Calcule une solution au problème de manière gloutonne, afin d'obtenir une borne minimale
	private int runHeuristic()
	{
		int currentWeight = 0;
		int xh = 0;
		int i = 0;
		
		while((t.get(i).getWeight() + currentWeight <= tailleSac) && (i < t.size()))
		{
			currentWeight += t.get(i).getWeight();
			xh += t.get(i).getValue();
			i++;
		}
		
		return xh;
	}
	
	//faire la relaxation linéaire d'un sac
	private double computeUpperBound(Sac sac)
	{
		int currentWeight = 0;
		int i = 0;
		int V = 0;
		ArrayList<Boolean> I = sac.getI();
		for(i = 0; i < I.size(); i++)//Calcul poids et valeur du sac
		{
			if(I.get(i))
			{
				currentWeight += t.get(i).getWeight();
				V += t.get(i).getValue();
			}
		}
		
		double upperBound = V;
		//relaxation
		while((i < t.size()) && (t.get(i).getWeight() + currentWeight <= tailleSac))
		{
			currentWeight += t.get(i).getWeight();
			upperBound += t.get(i).getValue();
			i++;
		}
		
		if(i >= t.size())
			return upperBound;
		
		//currentWeight + X * t.get(i).getWeight = tailleSac
		double frac = (double) (tailleSac - currentWeight) / t.get(i).getWeight();
		upperBound += frac * t.get(i).getValue();
		//on ignore les solutions entieres
		
		return upperBound;
	}
	
	//private calcul poids valide d'un sac candidat
	private int sacWeight(Sac s)
	{
		int W = 0;
		ArrayList<Boolean> I = s.getI();
		for(int i = 0; i < I.size(); i++)
		{
			if(I.get(i))
				W += t.get(i).getWeight();
		}		
		return W;
	}
	
	private int sacValue(Sac s)
	{
		int V = 0;
		ArrayList<Boolean> I = s.getI();
		for(int i = 0; i < I.size(); i++)
		{
			if(I.get(i))
				V += t.get(i).getValue();
		}		
		return V;
	}
	//private calcul relaxation linéaire d'un sac candidat
	
	public void solve()
	{
		int bestSol = runHeuristic();
		Sac sacSol = null;
		
		//System.out.println("Résultat heuristique : xh = " + bestSol);
		Stack<Sac> stack = new Stack<Sac>();
		int value, weight;
		double upperBound;
		
		Sac ini = new Sac(t.size());
		stack.push(ini.branch0());//faire la relaxation de branch0 avant de le push ?
		stack.push(ini.branch1());		
		
		while(!stack.empty())//Tant qu'il y a des noeuds a explorés
		{
			Sac s = stack.pop();
			value = sacValue(s);
			if(value > bestSol)
			{
				//System.out.println("Meilleure solution courante : " + value);
				sacSol = s;
				bestSol = value;
			}
			else
			{
				Sac s0 = s.branch0();
				if(s0 == null)
					continue;
				
				upperBound = computeUpperBound(s0);

				if(upperBound > bestSol)// /!\ >= ou > idk
				{
					stack.push(s0);
				}
				Sac s1 = s.branch1();
				if(s1 == null)
					continue;
				
				//Pour s1 on doit tester si le poids dépasse ou non, car on rajoute un objet dans le sac
				weight = sacWeight(s1);
				if(weight <= tailleSac)
				{
					upperBound = computeUpperBound(s1);
					if(upperBound > bestSol)// /!\ >= ou > idk
					{
						stack.push(s1);
					}
				}
			}
		}
		
		System.out.println("Solution : \n" + sacSol + "\nValeur optimal = " + bestSol);
	}
	
	public void loadFromFile(String filename)
	{
		t = new ArrayList<Objet>();
		System.out.println("Lecture du fichier " + filename);
		try
		{
			Scanner sc = new Scanner(new File(filename));
			int w, v;
			
			tailleSac = sc.nextInt();
			
			while(sc.hasNextLine())
			{
				w = sc.nextInt();
				v = sc.nextInt();
				
				t.add(new Objet(w, v));
			}
			sc.close();
			
			System.out.println("Ordonnancement par ratio décroissant...");
			Collections.sort(t);			
		}
		catch(IOException e)
		{
			System.out.println(e);
			System.exit(1);
		}
		System.out.println("Fichier lu");
	}	
}
