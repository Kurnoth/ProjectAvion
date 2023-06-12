package ProjectAvion.src;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.Math;

public abstract class Data {
	static private int i = 0;
	/*static double tab[][] = { 
			{ 100, 48.2512, 3.7545, 150, 3000, 120 },
			{ 200, 48.5452, 3.6615, 140, 3500, 240 },
			{ 300, 47.5655, 2.6452, 180, 6000, 110 },
			{ 400, 48.5584, 3.6652, 165, 2000, 90 },
			{ 500, 18.5452, 2.7010, 160, 1200, 40 } };
		*/
	private static ArrayList<Avion> listeAvions = new ArrayList<Avion>();


	public static ArrayList<Avion> initAvions() {
		for ( int i = 0; i < ThreadLocalRandom.current().nextInt(3, 7 + 1); i++) {
			Avion a = new Avion((listeAvions.size() + 1) * 100,
				Math.round(ThreadLocalRandom.current().nextDouble(47, 49 + 1) * 10000d) / 10000d,
				Math.round(ThreadLocalRandom.current().nextDouble(2, 4 + 1) * 10000d) / 10000d,
				ThreadLocalRandom.current().nextInt(100, 200 + 1),
			    ThreadLocalRandom.current().nextInt(1000, 10000 + 1),
				ThreadLocalRandom.current().nextInt(0, 360 + 1));
			listeAvions.add(a);
		}
		System.out.println("StartInit");
		for (Avion a : listeAvions) {
			System.out.println(a);
		}
		System.out.println("EndInit");
		return listeAvions;		
	}

	public static Avion getNextAircraft() {
		
		if (listeAvions.size() == 0)
			listeAvions = initAvions();		
		if (listeAvions.size() - 1 > i) {
			i++;
			return listeAvions.get(i);
		}
		return null;
	}

	public static void setI(int i) {
		Data.i = i;
	}

	public void updatePosition(ArrayList<> planes) {
		//time, in second
		double time = 15;
		//earth radius, in meter
		double earthRadius = 6371000;

		for (Avion plane : planes) {
			//conversion speed, from nautical mile to meter per second
			double speedMeterPerSecond = plane.getVitesse() * 1852 / 3600;
			//conversion cap, from degree to radian
			double capRadian = Math.toRadians(plane.getCap());
			//distance calculation
			double distance = speedMeterPerSecond * time;
			//conversion latitude, from degree to radian
			double latitudeRadian = Math.toRadians(plane.getLatitude());
			//latitude calculation
			//lat_dest = arcsin(sin(lat_orig) * cos(dist/rayon Terre) + cos(lat_orig) * sin(dist/rayon Terre) * cos(cap))
			plane.setLatitude(Math.toDegrees(Math.arcsin(Math.sin(latitudeRadian) * Math.cos(distance/earthRadius) + Math.cos(latitudeRadian) * Math.sin(distance/earthRadius) * Math.cos(capRadian))));
			//longitude calculation
			//long_dest = long_orig + atan2(sin(cap) * sin(dist/rayon Terre) * cos(lat_orig), cos(dist/rayon Terre) - sin(lat_orig) * sin(lat_dest))
			plane.setLongitude(Math.toDegrees(Math.toRadians(plane.getLongitude()) + Math.atan2(Math.sin(capRadian) * Math.sin(distance/earthRadius) * Math.cos(latitudeRadian), Math.cos(distance/earthRadius) - Math.sin(latitudeRadian) * Math.sin(Math.toRadians(plane.getLatitude())))))
		}
	}
}
