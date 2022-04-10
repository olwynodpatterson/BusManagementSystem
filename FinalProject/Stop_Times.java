import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Stop_Times {

	public int 	trip_id;
	public String arrival_time;
	public String departure_time;
	public int stop_id;
	public String stop_sequence;
	public int stop_headsign;
	public int pickup_type;
	public int drop_off_type;
	public double shape_dist_traveled;
	public ArrayList<Stop_Times> stop_times = new ArrayList<Stop_Times>();

	Stop_Times(){
		//default constructor
	}

	Stop_Times(int trip_id, String arrival_time, String departure_time){
		this.trip_id = trip_id;
		this.arrival_time = arrival_time;
		this.departure_time = departure_time;	
	}
	
	Stop_Times(int trip_id, int stop_id){
		this.trip_id = trip_id;
		this.stop_id = stop_id;		
	}


}
