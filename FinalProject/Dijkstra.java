import java.io.File;
import java.util.Scanner;

public class Dijkstra {

	public double distTo[][];
	public int edgeTo[][];
	public int edgeCount;

	Dijkstra(String filename) {
		//read in file 
		try {
			File inputFile = new File(filename);
			Scanner sc = new Scanner(inputFile);
			int i = 0;

			while (sc.hasNextLine()) {
				String[] fileAsString = sc.nextLine().trim().split("\\s+");

				//getting number of intersections
				if (i == 0) {
					distTo = new double[Integer.parseInt(fileAsString[i])][Integer.parseInt(fileAsString[i])]; //initialise array of length of intersections
					edgeTo = new int[Integer.parseInt(fileAsString[i])][Integer.parseInt(fileAsString[i])];
					for (int j = 0; j < distTo.length; j++) {
						for (int k = 0; k < distTo[j].length; k++) {
							distTo[j][k] = Double.POSITIVE_INFINITY;
							if (j == k) {
								distTo[j][k] = 0;
							}
						}
					}
					//getting number of streets
				} else if (i == 1) {
					edgeCount = Integer.parseInt(fileAsString[i - 1]);

					//getting distances	
				} else {
					distTo[Integer.parseInt(fileAsString[0])][Integer.parseInt(fileAsString[1])] = Double.parseDouble(fileAsString[2]);
					edgeTo[Integer.parseInt(fileAsString[0])][Integer.parseInt(fileAsString[1])] = Integer.parseInt(fileAsString[0]);

				}
				i++;
			}
			for (int j = 0; j < distTo.length; j++) {
				dijkstraAlgorithmImplementation(j);
			}
			sc.close();
		} catch (Exception e) {
			distTo = new double[0][0];
			edgeTo = new int[0][0];
			return;
		}
	}

	public void dijkstraAlgorithmImplementation(int k) {

		boolean[] shortestPath = new boolean[distTo.length];
		shortestPath[k] = true;
		while (true) {
			int x = -1;
			for (int i = 0; i < distTo.length; i++) {
				if ((shortestPath[i] == false) && (distTo[k][i] != Double.POSITIVE_INFINITY)) {
					x = i;
					break; // break as new vertex is found
				}
			}
			if (x == -1) {
				return;
			}
			shortestPath[x] = true;

			for (int i = 0; i < distTo.length; i++) {
				if (distTo[k][x] + distTo[x][i] < distTo[k][i]) {
					distTo[k][i] = distTo[k][x] + distTo[x][i];
					shortestPath[i] = false;
					edgeTo[k][i] = x;
				}
			}
		}

	}
	
	public double costOfSP() {
//		if ((sA < 50 || sA > 100) || (sB < 50 || sB > 100) || (sC < 50 || sC > 100)) {
//			return -1;
//		}
//		int slowestSpeed = Math.min(sC, Math.min(sA, sB));
		double maxDistance = 0;

		for (int i = 0; i < distTo.length; i++) {
			for (int j = 0; j < distTo[i].length; j++) {
				if (distTo[i][j] == Double.POSITIVE_INFINITY) return -1;
				else if (distTo[i][j] > maxDistance) {
					maxDistance = distTo[i][j];
				}
			}
			return maxDistance;
		}
//		int maxTime = (int) Math.ceil((maxDistance * 1000) / slowestSpeed);
//		if (slowestSpeed <= 0 || maxDistance == 0) return -1;
		
		
		return 0;
		
	}

}
