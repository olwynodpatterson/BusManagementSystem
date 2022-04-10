import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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
	public ArrayList<Stops> stops = new ArrayList<Stops>(); //array list that will contain all the stops 

	Stops(){ //default constructor	
	}
	

	Stops(int stop_id, String stop_name){ 
		this.stop_id = stop_id;
		this.stop_name = stop_name;
	}


}
