import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//Class for reading in stops.txt file
public class Stops {
	public int stop_id;
	public int stop_code;
	public String stop_name;
	public String stop_desc;
	public double stop_lat;
	public double stop_lon;
	public String zone_id;  
	public String stop_url;
	public String location_type;  
	public String parent_station;

	public ArrayList<Stops> stops;

	Stops(int stop_id, String stop_name){ 
		this.stop_id = stop_id;
		this.stop_name = stop_name;
	}
	
	 private void Stops() {
	        try {
	            String filename = "Input Files\stops.txt";
	            File file = new File(filename);
	            Scanner sc = new Scanner(file);
	            sc.useDelimiter(",");
	            sc.nextLine();
	            while (sc.hasNext()) {
	                if (sc.hasNextInt()) {
	                    int stopID = sc.nextInt();
	                    sc.next();
	                    String stopName = sc.next();
	                    stops.add(new Stops(stopID, stopName));
	                    sc.nextLine();
	                }
	            }
	            sc.close();
	        } catch (FileNotFoundException e) {
	            stops = null;
	        }
	    }


	public static void main(String[] args) {


	}

}
