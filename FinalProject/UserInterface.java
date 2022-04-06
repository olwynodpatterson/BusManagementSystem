import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//User Interface of the project
public class UserInterface {
	
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
						stopTimes.add(new Stop_Times(tripID, arrivalTime, departureTime));
						sc.nextLine();
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
				ArrayList<Stops> arr = new ArrayList();
				validTime(userInputTime);
				arr = readInStopTimes(filename);
				if(validTime(userInputTime)) {
					System.out.print("Valid time");
					for(int i=0; i<arr.size(); i++) {
						Stops tempStop = new Stops();
						tempStop =  arr.get(i);
					}

					
//					else {
//						System.out.print("Sorry. There were no stops found for that time.");
//					}
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
