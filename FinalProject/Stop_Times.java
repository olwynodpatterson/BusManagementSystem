import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//Class for reading in stop_times.txt file
public class Stop_Times {
	
	public int 	trip_id;
	public double arrival_time;
	public double departure_time;
	public int stop_id;
	public String stop_sequence;
	public int stop_headsign;
	public int pickup_type;
	public int drop_off_type;
	public double shape_dist_traveled;
	public ArrayList<Stop_Times> stop_times;
	
	Stop_Times(int trip_id, double arrival_time, double departure_time){
		this.trip_id = trip_id;
		this.arrival_time = arrival_time;
		this.departure_time = departure_time;	
	}
	
	private void Stop_Times() {
        try {
            String filename = "Input Files\stop_times.txt";
            File file = new File(filename);
            Scanner sc = new Scanner(file);
            sc.useDelimiter(",");
            sc.nextLine();
            while (sc.hasNext()) {
                if (sc.hasNextInt()) {
                    int tripID = sc.nextInt();
                    sc.next();
                    double arrivalTime = sc.nextDouble();
                    sc.next();
                    double departureTime = sc.nextDouble();
                    sc.next();
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
		// TODO Auto-generated method stub

	}

}
