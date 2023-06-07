package ProjectAvion;

public abstract class Data {
	static private int i = 0;
	static double tab[][] = { 
			{ 100, 48.2512, 3.7545, 150, 3000, 120 },
			{ 200, 48.5452, 3.6615, 140, 3500, 240 },
			{ 300, 47.5655, 2.6452, 180, 6000, 110 },
			{ 400, 48.5584, 3.6652, 165, 2000, 90 },
			{ 500, 18.5452, 2.7010, 160, 1200, 40 } };

	public static Avion getNextAircraft() {
		
		// La variable i doit être remise à zero à chaque raffrichissement (probalement à implémenter dans une autre méthode )
		
		if (tab.length > i) {

			Avion a = new Avion((int) tab[i][0], tab[i][1], tab[i][2],
				 (int) tab[i][3], (int) tab[i][4], (int) tab[i][5]);
			i++;
			return a;
		}
		return null;
	}
	


}
