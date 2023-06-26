package ProjectAvion.src;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.Math;

public abstract class Data {

	private static final ArrayList<Avion> listeAvions = new ArrayList<>();


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

	public static void updatePosition() {
		//time, in second
		double time = 15;
		//earth radius, in meter
		double earthRadius = 6371000;

		for (Avion plane : listeAvions) {
			//conversion speed, from nautical mile to meter per second
			double speedMeterPerSecond = plane.getVitesse() * 1852.0 / 3600.0;
			//conversion cap, from degree to radian
			double capRadian = Math.toRadians(plane.getCap());
			//distance calculation
			double distance = speedMeterPerSecond * time;
			//conversion latitude, from degree to radian
			double latitudeRadian = Math.toRadians(plane.getLatitude());
			//latitude calculation
			//lat_dest = arcsin(sin(lat_orig) * cos(dist/rayon Terre) + cos(lat_orig) * sin(dist/rayon Terre) * cos(cap))
			plane.setLatitude(Math.toDegrees(Math.asin(Math.sin(latitudeRadian) * Math.cos(distance/earthRadius) + Math.cos(latitudeRadian) * Math.sin(distance/earthRadius) * Math.cos(capRadian))));
			//longitude calculation
			//long_dest = long_orig + atan2(sin(cap) * sin(dist/rayon Terre) * cos(lat_orig), cos(dist/rayon Terre) - sin(lat_orig) * sin(lat_dest))
			plane.setLongitude(Math.toDegrees(Math.toRadians(plane.getLongitude()) + Math.atan2(Math.sin(capRadian) * Math.sin(distance/earthRadius) * Math.cos(latitudeRadian), Math.cos(distance/earthRadius) - Math.sin(latitudeRadian) * Math.sin(Math.toRadians(plane.getLatitude())))));
			//round to 4 decimal places
			plane.setLatitude(Math.round(plane.getLatitude() * 10000.0) / 10000.0);
			plane.setLongitude(Math.round(plane.getLongitude() * 10000.0) / 10000.0);
		}
		
	}

	public static ArrayList<Avion> getListeAvions() {
		return listeAvions;
	}
}
