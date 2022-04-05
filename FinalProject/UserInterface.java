import java.util.Scanner;

//User Interface of the project
public class UserInterface {

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
				if () { //userInput in the list
					//need to read in all stops here and then search through that array list with a TST
					Stops userStop = new Stops();
					userStop.readInStops();
					ST TST = new ST();
					TST.get(userInputStop);
				}
				else {
					System.out.print("Error. Bus stop not in system.");
				}
				
			}
			else if (userInput.equals("C")) {
				// Search with arrival times
			}
			else{
				System.out.println("Error. Invalid input");
			}
		}
		input.close();
		System.out.print("Thank you & Goodbye");

	}

}
