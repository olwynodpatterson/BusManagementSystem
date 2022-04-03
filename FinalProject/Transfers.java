import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//Class for reading in transfers.txt
public class Transfers {

	public int from_stop_id;
	public int to_stop_id;
	public int transfer_type;
	public String min_transfer_time;
	public ArrayList<Transfers> transfers = new ArrayList<Transfers>();

	Transfers(){
		//default constructor
	}

	Transfers(int from_stop_id, int to_stop_id, int transfer_type){ //Transfer constructor without minTransferTime 
		this.from_stop_id = from_stop_id;
		this.to_stop_id = to_stop_id;
		this.transfer_type = transfer_type;
		
	}

	private void readInTransfers() {
		try {
			String filename = "Input Files\\transfers.txt";
			File file = new File(filename);
			Scanner sc = new Scanner(file);
			sc.useDelimiter(",");
			sc.nextLine();
			while (sc.hasNext()) {
				if (sc.hasNextInt()) {
					int fromStopID = sc.nextInt();
					int toStopID = sc.nextInt();
					int transferType = sc.nextInt();
//					if (sc.hasNextInt()) {
//						sc.nextInt();
//						String minTransferTime = sc.next();
						//					int minTransferTime = sc.nextInt();
						//					if (sc.nextInt()) {
						//						 minTransferTime = 0;
						//					}
						//					else {
						//						 minTransferTime = sc.nextInt();
						//					}
						transfers.add(new Transfers(fromStopID, toStopID, transferType));
						sc.nextLine();
					}
				}	
			sc.close();
		} catch (FileNotFoundException e) {
			transfers = null;
		}
	}


	public static void main(String[] args) {
		Transfers test = new Transfers();
		test.readInTransfers();

	}

}
