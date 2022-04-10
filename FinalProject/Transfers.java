import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//Class for reading in transfers.txt
public class Transfers {

	public int from_stop_id;
	public int to_stop_id;
	public int transfer_type;
	public int min_transfer_time;
	public ArrayList<Transfers> transfers = new ArrayList<Transfers>();

	Transfers(){
		//default constructor
	}

	Transfers(int from_stop_id, int to_stop_id, int transfer_type){ //Transfer constructor without minTransferTime 
		this.from_stop_id = from_stop_id;
		this.to_stop_id = to_stop_id;
		this.transfer_type = transfer_type;


	}

}
