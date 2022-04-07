import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//User Interface of the project
public class UserInterface {

	public static void shortestPath(int stopID1, int stopID2)
	{
		ArrayList<Stops> stopsArray = new ArrayList<Stops>();
		ArrayList<Stop_Times> stopTimesArray = new ArrayList<Stop_Times>();
		ArrayList<Transfers> transfersArray = new ArrayList<Transfers>();
		try
		{
			File stopsFile = new File("Input\\stops.txt");
			Scanner sc1 = new Scanner(stopsFile);
			sc1.useDelimiter(",");
			sc1.nextLine();
			while (sc1.hasNext()) {
				if (sc1.hasNextInt()) {
					int stopID = sc1.nextInt();
					sc1.next();
					String stopName = sc1.next();
					stopsArray.add(new Stops(stopID, stopName));
				}
			}
			int numberOfVertices = stopsArray.size(); //number of stops
			EdgeWeightedDigraph graph = new EdgeWeightedDigraph(numberOfVertices);


			File stopTimesFile = new File("Input\\stop_times.txt");
			Scanner sc2 = new Scanner(stopTimesFile);
			sc2.useDelimiter(",");
			sc2.nextLine();
			while(sc2.hasNextLine()){
				if (sc2.hasNextInt()) {
					int tripID = sc2.nextInt();
					sc2.next();
					sc2.next();
					int stopID = sc2.nextInt();
					stopTimesArray.add(new Stop_Times(tripID, stopID));
				}
			}
			//setting edges from stop_times.txt
			for(int i = 0; i < stopTimesArray.size(); i++)
			{
				if(stopTimesArray.get(i).trip_id == stopTimesArray.get(i+1).trip_id)
				{
					//if 2 consecutive stops have same tripID - add edge (cost 1 as comes from stop_times.txt)
					DirectedEdge edge = new DirectedEdge(stopTimesArray.get(i).stop_id, stopTimesArray.get(i+1).stop_id, 1);
					graph.addEdge(edge);
				}
			}

			File transfersFile = new File("Input\\transfers.txt");
			Scanner sc3 = new Scanner(transfersFile);
			sc3.useDelimiter(",|\\n");
			sc3.nextLine(); //skips first line of text file
			while(sc3.hasNextLine()){
				if(sc3.hasNextInt()) {
					int fromStopID = sc3.nextInt();
					int toStopID = sc3.nextInt();
					int transferType = sc3.nextInt();
					String minTransferTime = sc3.next();
					transfersArray.add(new Transfers(fromStopID, toStopID, transferType, Integer.parseInt(minTransferTime)));
					sc3.nextLine();
				}
			}
			//setting edges from transfers.txt
			for(int i = 0; i < transfersArray.size(); i++)
			{
				if(transfersArray.get(i).transfer_type == 0)
				{
					DirectedEdge edge = new DirectedEdge(transfersArray.get(i).from_stop_id, transfersArray.get(i).to_stop_id, 2);
					graph.addEdge(edge);
				}
				else if(transfersArray.get(i).transfer_type == 2)
				{
					DirectedEdge edge = new DirectedEdge(transfersArray.get(i).from_stop_id, transfersArray.get(i).to_stop_id, (transfersArray.get(i).min_transfer_time / 100));
					graph.addEdge(edge);
				}
			}
		} catch(Exception e) {
		}
	}





//	private ArrayList<Integer> fileToArrayList (String filename) {
//		ArrayList<Integer> stopTimes = new ArrayList();
//		try {
//			File file = new File(filename);
//			Scanner sc = new Scanner(file);
//			sc.useDelimiter(",");
//			sc.nextLine();
//			while (sc.hasNext()) {
//				if (sc.hasNextInt()) { 
//					int tripID = sc.nextInt();
//					sc.next();
//					sc.next();
//					int fromStopID = sc.nextInt();
//					sc.nextLine();
//					stopTimes.add(tripID);
//				}
//			}
//
//		}catch (FileNotFoundException e) {
//		}
//		return stopTimes;
//	}


//
//	private void filetoEWG(String filename) {
//		try {
//			File file = new File(filename);
//			Scanner sc = new Scanner(file);
//			sc.useDelimiter(",");
//			sc.nextLine();
//			while (sc.hasNext()) {
//				if (sc.hasNextInt()) { 
//					int tripID = sc.nextInt();
//					sc.next();
//					sc.next();
//					int fromStopID = sc.nextInt();
//					sc.nextLine();
//				}
//			} 
//		}catch (FileNotFoundException e) {
//			//no file
//		}
//	}

//	private void fileToGraph(String filename) {
//		try {
//			File file = new File(filename);
//			Scanner sc = new Scanner(file);
//			sc.useDelimiter(",|\\n");
//			sc.nextLine();
//			while (sc.hasNext()) {
//				if (sc.hasNextInt()) {
//					int fromStopID = sc.nextInt();
//					int toStopID = sc.nextInt();
//					int transferType = sc.nextInt();
//					String minTransferTime = sc.next();
//
//					//transfers.add(new Transfers(fromStopID, toStopID, transferType, Integer.parseInt(minTransferTime)));
//					sc.nextLine();
//				}
//			}	
//			sc.close();
//		} catch (FileNotFoundException e) {
//			//transfers = null;
//		}
//	}

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


		Scanner input = new Scanner(System.in);

		while(quit == false) {
			System.out.print("\nEnter A, B or C or 'quit' : ");
			String userInput = input.nextLine();
			if (userInput.equals("quit")) {
				quit = true;
			}
			else if (userInput.equals("A")) {
				//shortest path 
				System.out.print("Please enter a bus stop: ");
				String userInputStop1 = input.nextLine();
				System.out.print("\nPlease enter another bus stop: ");
				String userInputStop2 = input.nextLine();
				String transfersFile = "Input Files\\transfers.txt";
				String stopTimesFile = "Input Files\\stop_times.txt";
				//Dijkstra SP = new Dijkstra(file);

				//EdgeWeightedDigraph EWG = new EdgeWeightedDigraph(8757); //calculated number of vertices outside programme



			}
			else if(userInput.equals("B")) {
				//Search bus stops
				System.out.print("Enter bus stop full name or first few characters :");
				String userInputStop = input.nextLine();
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
			else if (userInput.equals("C")) {
				// Search with arrival times
				System.out.print("Enter an arrival time as hh:mm:ss : ");
				String userInputTime = input.nextLine();
				String filename = "Input Files\\stop_times.txt";
				ArrayList<Stop_Times> arr = new ArrayList();
				validTime(userInputTime);
				arr = readInStopTimes(filename);
				if(validTime(userInputTime)) {
					int countValid = 0;
					for(int i=0; i<arr.size(); i++) {
						Stop_Times tempStop = new Stop_Times();
						tempStop =  arr.get(i);
						if(userInputTime.equals(tempStop.arrival_time)) {
							System.out.print("\nTrip ID: " + tempStop.trip_id + ", Arrival time: " + tempStop.arrival_time + ", Departure time: " + tempStop.departure_time);
							countValid++;
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
		input.close();
		System.out.print("Thank you & Goodbye");

	}

}
