import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//User Interface of the project
public class UserInterface {


	public static ArrayList<Stops> readInStopsForSP(String filename){
		ArrayList<Stops> stops = new ArrayList();
		try {
			File stopsFile = new File(filename);
			Scanner sc1 = new Scanner(stopsFile);
			sc1.useDelimiter(",");
			sc1.nextLine();
			while (sc1.hasNext()) {
				if (sc1.hasNextInt()) {
					int stopID = sc1.nextInt();
					sc1.next();
					String stopName = sc1.next();
					stops.add(new Stops(stopID, stopName));
					sc1.nextLine();
				}
			}
			return stops;
		} catch(FileNotFoundException e) {
			stops = null;
			return null;
		}
	}

	public static ArrayList<Stop_Times> readInStopTimesForSP(String filename){
		ArrayList<Stop_Times> stopTimes = new ArrayList();
		try {
			File stopTimesFile = new File(filename);
			Scanner sc2 = new Scanner(stopTimesFile);
			sc2.useDelimiter(",");
			sc2.nextLine();
			while(sc2.hasNextLine()){
				if (sc2.hasNextInt()) {
					int tripID = sc2.nextInt();
					String arrivalTime = sc2.next();
					sc2.next();
					int stopID = sc2.nextInt();
					if(validTime(arrivalTime)) {
						stopTimes.add(new Stop_Times(tripID, stopID));
						sc2.nextLine();
					}
					else {
						sc2.nextLine();
					}					
				}
			}
			return stopTimes;
		} catch(FileNotFoundException e) {
			stopTimes = null;
			return null;
		}		
	}


	public static ArrayList<Transfers> readInTransfersForSP(String filename){
		ArrayList<Transfers> transfers = new ArrayList();
		try {
			File transfersFile = new File(filename);
			Scanner sc3 = new Scanner(transfersFile);
			sc3.useDelimiter(",|\\n");
			sc3.nextLine(); //skips first line of text file
			while(sc3.hasNextLine()){
				if(sc3.hasNextInt()) {
					int fromStopID = sc3.nextInt();
					int toStopID = sc3.nextInt();
					int transferType = sc3.nextInt();
					//String minTransferTime = sc3.next();
					transfers.add(new Transfers(fromStopID, toStopID, transferType)); //deal wit this
					sc3.nextLine();
				}
			}
			return transfers;

		} catch (FileNotFoundException e) {
			transfers = null;
			return null;
		}		
	}

	public static EdgeWeightedDigraph createGraph(ArrayList<Stops> stops, ArrayList<Stop_Times> stopTimes, ArrayList<Transfers> transfers) {
		int vertices = stopTimes.size();
		EdgeWeightedDigraph EWD = new EdgeWeightedDigraph(vertices);
		
		//setting edges from stopTimes.txt
		for(int i = 0; i < stopTimes.size()-1; i++)
		{
			if(stopTimes.get(i).trip_id == stopTimes.get(i+1).trip_id)
			{
				//if 2 consecutive stops have same tripID - add edge (cost 1 as comes from stop_times.txt)
				DirectedEdge edge = new DirectedEdge(stopTimes.get(i).stop_id, stopTimes.get(i+1).stop_id, 1);
				EWD.addEdge(edge);
			}
		}

		//setting edges from transfers.txt
		for(int i = 0; i < transfers.size(); i++)
		{
			if(transfers.get(i).transfer_type == 0)
			{
				DirectedEdge edge = new DirectedEdge(transfers.get(i).from_stop_id, transfers.get(i).to_stop_id, 2);
				EWD.addEdge(edge);
			}
			else if(transfers.get(i).transfer_type == 2)
			{
				DirectedEdge edge = new DirectedEdge(transfers.get(i).from_stop_id, transfers.get(i).to_stop_id, (transfers.get(i).min_transfer_time / 100));
				EWD.addEdge(edge);
			}
		}
		return EWD;

	}



	public static boolean validTime(String time) {
		String timeFormat = "((\\s?)[0-9]|[01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
		Pattern pattern = Pattern.compile(timeFormat);
		if (time == null) {
			return false;
		}
		Matcher matcher = pattern.matcher(time);
		return matcher.matches();

	}

	private static ArrayList readInStopTimes(String filename) {
		ArrayList<Stop_Times> stopTimes = new ArrayList();
		try {
			File file = new File(filename);
			Scanner sc = new Scanner(file);
			sc.useDelimiter(",");
			sc.nextLine();
			while (sc.hasNext()) {
				if (sc.hasNextInt()) { 
					int tripID = sc.nextInt();
					String arrivalTime = sc.next();
					String departureTime = sc.next();
					if(validTime(arrivalTime)) {
						if(arrivalTime.charAt(0) == ' ') {
							String updatedArrivalTime = arrivalTime.replace(' ', '0');						
							stopTimes.add(new Stop_Times(tripID, updatedArrivalTime, departureTime));
							sc.nextLine();
						}
						else {
							stopTimes.add(new Stop_Times(tripID, arrivalTime, departureTime));
							sc.nextLine();
						}
					}

					else sc.nextLine();					
				}
			}
			sc.close();
			return stopTimes;
		} catch (FileNotFoundException e) {
			stopTimes = null;
			return null;
		}

	}

	public static String editName(String name) {
		String finalName = "";
		if(name.startsWith("FLAGSTOP") || name.startsWith("WB") || name.startsWith("NB") || name.startsWith("SB") || name.startsWith("EB")){
			String [] stopNameSplit = name.split(" ");
			for (int i = 1; i < stopNameSplit.length; i ++) {
				String stopNameTemp = stopNameSplit[i];
				finalName = finalName.concat(stopNameTemp + " ");
			}
			finalName = finalName.concat(stopNameSplit[0]);
			return finalName;
		}
		else return name;
	}

	public static TST fileToTST(String filename) {
		TST TST = new TST();
		try {
			File file = new File(filename);
			Scanner sc = new Scanner(file);
			sc.useDelimiter(",");
			sc.nextLine();
			while (sc.hasNext()) {
				if (sc.hasNextInt()) {
					int stopID = sc.nextInt();
					sc.next();
					String stopName = sc.next();
					String stopNameUpdate1 = editName(stopName);
					String stopNameUpdate2 = editName(stopNameUpdate1);
					Stops stops = new Stops(stopID, stopNameUpdate2);
					TST.put(stops.stop_name, stops);
					sc.nextLine();

				}
			}
			sc.close();
			return TST;
		} catch (FileNotFoundException e) {
			return null;
		}
	}



	public static void main(String[] args) {

		boolean quit = false;
		System.out.print("Welcome to the Vancover Bus Stop System. What would you like to do?\n"
				+ "\n A. Find shortest path between two bus stops\n B. Search for bus stop by full name"
				+ "\n C. Search for all trips with a given arrival time\n");

		Scanner initialInput = new Scanner(System.in);
		Scanner input = new Scanner(System.in);

		while(quit == false) {
			System.out.print("\nEnter A, B or C or 'quit' : ");
			String userInput = initialInput.next();
			if (userInput.equalsIgnoreCase("quit")) {
				quit = true;
			}
			else if (userInput.equalsIgnoreCase("A")) {
				//shortest path 
				System.out.print("Please enter a bus stop: ");
				if(input.hasNextInt()) {
					int userInputStop1 = input.nextInt();
					System.out.print("Please enter another bus stop: ");
					int userInputStop2 = input.nextInt();
					String stopsFile = "Input Files\\stops.txt";
					String stopTimesFile = "Input Files\\stop_times.txt";
					String transfersFile = "Input Files\\transfers.txt";
					
					EdgeWeightedDigraph EWD = createGraph(readInStopsForSP(stopsFile), readInStopTimesForSP(stopTimesFile), readInTransfersForSP(transfersFile)); 
					DijkstraSP SP = new DijkstraSP(EWD, userInputStop1);
					//use path to to get path and then dist to to get cost of that path
					Iterable<DirectedEdge> shortestPath = SP.pathTo(userInputStop2);
					System.out.println("\nThe shortest path between " + userInputStop1 + " and " + userInputStop2 + " is: ");
					System.out.println(shortestPath.toString());
					System.out.println("The total cost of the trip is: " + SP.distTo(userInputStop2));
				}
				else {
					System.out.print("Error. Invalid input.");
				}


			}
			else if(userInput.equalsIgnoreCase("B")) {
				//Search bus stops
				System.out.print("Enter bus stop full name or first few characters :");
				String userInputStop = input.nextLine().toUpperCase();
				String file = "Input Files\\stops.txt";
				TST<Stops> busStops = fileToTST(file);
				Iterable<String> stops = busStops.keysWithPrefix(userInputStop);
				int counter = 0;
				for (String stop : stops) {
					counter++;
					if(counter == 1) {
						System.out.println("Bus stop found:");
					}
					else if(counter == 0) {
						System.out.print("Sorry. There were no stops found.");
					}
					Stops stopOutput =  busStops.get(stop);	
					System.out.println("\nStop Name: " + stopOutput.stop_name + ", Stop ID: " + stopOutput.stop_id);
				}

			}
			else if (userInput.equalsIgnoreCase("C")) {
				// Search with arrival times
				System.out.print("Enter an arrival time as hh:mm:ss : ");
				String userInputTime = input.nextLine();
				String filename = "Input Files\\stop_times.txt";
				ArrayList<Stop_Times> arr = new ArrayList();
				//validTime(userInputTime);
				arr = readInStopTimes(filename);
				if(validTime(userInputTime)) {
					int countValid = 0;
					ArrayList<Stop_Times> equal = new ArrayList();
					for(int i=0; i<arr.size(); i++) {
						Stop_Times tempStop = new Stop_Times();
						tempStop =  arr.get(i);
						if(userInputTime.equals(tempStop.arrival_time)) { 						
							equal.add(tempStop);
							countValid++;
						}
					}					
					Integer [] sortArr = new Integer[equal.size()];
					for(int j = 0; j<equal.size(); j++) {
						sortArr[j] = equal.get(j).trip_id;
					}
					Insertion.sort(sortArr);
					for(int k = 0; k <sortArr.length; k++) {
						for(int i = 0; i <sortArr.length; i++) {
							if(sortArr[k] == equal.get(i).trip_id) {
								System.out.println("\nTrip ID: " + sortArr[k] + ", Arrival time: " + equal.get(i).arrival_time + ", Departure time: " + equal.get(i).departure_time);
							}
						}	
					}
					if (countValid == 0){
						System.out.print("Sorry. There were no stops found for that time.");
					}
				}
				else {
					System.out.println("Error. Invalid time");
				}
			}
			else{
				System.out.println("Error. Invalid input");
			}
		}
		initialInput.close();
		input.close();
		System.out.print("Thank you & Goodbye");

	}

}
