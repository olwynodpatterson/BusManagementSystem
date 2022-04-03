import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

//Class for reading in stop_times.txt file
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

	private void readInStopTimes() {
		try {
			String filename = "Input Files\\stop_times.txt";
			File file = new File(filename);
			Scanner sc = new Scanner(file);
			sc.useDelimiter(",");
			sc.nextLine();
			while (sc.hasNext()) {
				if (sc.hasNextInt()) { 
					int tripID = sc.nextInt();
					String arrivalTime = sc.next();
					String departureTime = sc.next();
					stop_times.add(new Stop_Times(tripID, arrivalTime, departureTime));
					sc.nextLine();
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			stop_times = null;
		}
	}

	public static void main(String[] args) {
		Stop_Times test = new Stop_Times();
		test.readInStopTimes();


	}

}
