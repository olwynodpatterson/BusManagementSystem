import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Adapted from book

public class TST<Value> 
{
	public void fileToTST(String filename) {
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
					if (stopName.startsWith("WB")|| stopName.startsWith("NB") || stopName.startsWith("SB") || stopName.startsWith("EB")) {
						//do something to split string and add prefix to end
						Stops stops = new Stops(stopID, stopName);
						put(stops.stop_name, stops);
						sc.nextLine();
					}
					else {
						Stops stops = new Stops(stopID, stopName);
						put(stops.stop_name, stops);
						sc.nextLine();
					}

				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			//stops = null;
		}
	}


	private Node root; // root of trie
	private class Node
	{
		char c; // character
		Node left, mid, right; // left, middle, and right subtries
		Stops val; // value associated with string
	}
	public Stops get(String key) 
	{
		Node x = get(root, key, 0);
		if (x == null) return null;
		return (Stops) x.val;
	}
	private Node get(Node x, String key, int d)
	{
		if (x == null) return null;
		char c = key.charAt(d);
		if (c < x.c) return get(x.left, key, d);
		else if (c > x.c) return get(x.right, key, d);
		else if (d < key.length() - 1)
			return get(x.mid, key, d+1);
		else return x;
	}

	public void put(String key, Stops val){
		root = put(root, key, val, 0); 
	}

	private Node put(Node x, String key, Stops val, int d)
	{
		char c = key.charAt(d);
		if (x == null) { x = new Node(); x.c = c; }
		if (c < x.c) x.left = put(x.left, key, val, d);
		else if (c > x.c) x.right = put(x.right, key, val, d);
		else if (d < key.length() - 1)
			x.mid = put(x.mid, key, val, d+1);
		else x.val = val;
		return x;
	}
}