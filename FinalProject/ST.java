/******************************************************************************
 *  Compilation:  javac ST.java
 *  Execution:    java ST < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/35applications/tinyST.txt
 *  
 *  Sorted symbol table implementation using a java.util.TreeMap.
 *  Does not allow duplicates.
 *
 ******************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *  The {@code ST} class represents an ordered symbol table of generic
 *  key-value pairs.
 *  It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 *  <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods.
 *  It also provides ordered methods for finding the <em>minimum</em>,
 *  <em>maximum</em>, <em>floor</em>, and <em>ceiling</em>.
 *  It also provides a <em>keys</em> method for iterating over all of the keys.
 *  A symbol table implements the <em>associative array</em> abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be {@code null}�setting the
 *  value associated with a key to {@code null} is equivalent to deleting the key
 *  from the symbol table.
 *  <p>
 *  It requires that
 *  the key type implements the {@code Comparable} interface and calls the
 *  {@code compareTo()} and method to compare two keys. It does not call either
 *  {@code equals()} or {@code hashCode()}.
 *  <p>
 *  This implementation uses a <em>red-black BST</em>.
 *  The <em>put</em>, <em>get</em>, <em>contains</em>, <em>remove</em>,
 *  <em>minimum</em>, <em>maximum</em>, <em>ceiling</em>, and <em>floor</em>
 *  operations each take &Theta;(log <em>n</em>) time in the worst case,
 *  where <em>n</em> is the number of key-value pairs in the symbol table.
 *  The <em>size</em> and <em>is-empty</em> operations take &Theta;(1) time.
 *  Construction takes &Theta;(1) time.
 *  <p>
 *  For additional documentation, see
 *  <a href="https://algs4.cs.princeton.edu/35applications">Section 3.5</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *
 *  @param <Key> the generic type of keys in this symbol table
 *  @param <Value> the generic type of values in this symbol table
 */
public class ST<Key extends Comparable<Key>, Value> implements Iterable<Key> {

	private TreeMap<String, Stops> st;

	/**
	 * Initializes an empty symbol table.
	 */
	public ST() {
		st = new TreeMap<String, Stops>();
	}


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

	/**
	 * Returns the value associated with the given key in this symbol table.
	 *
	 * @param  key the key
	 * @return the value associated with the given key if the key is in this symbol table;
	 *         {@code null} if the key is not in this symbol table
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public Stops get(String key) {
		if (key == null) throw new IllegalArgumentException("calls get() with null key");
		return st.get(key);
	}

	/**
	 * Inserts the specified key-value pair into the symbol table, overwriting the old 
	 * value with the new value if the symbol table already contains the specified key.
	 * Deletes the specified key (and its associated value) from this symbol table
	 * if the specified value is {@code null}.
	 *
	 * @param  key the key
	 * @param  val the value
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void put(String key, Stops val) {
		if (key == null) throw new IllegalArgumentException("calls put() with null key");
		if (val == null) st.remove(key);
		else             st.put(key, val);
	}

	/**
	 * Removes the specified key and its associated value from this symbol table     
	 * (if the key is in this symbol table).
	 * This is equivalent to {@code remove()}, but we plan to deprecate {@code delete()}.
	 *
	 * @param  key the key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void delete(String key) {
		if (key == null) throw new IllegalArgumentException("calls delete() with null key");
		st.remove(key);
	}

	/**
	 * Removes the specified key and its associated value from this symbol table     
	 * (if the key is in this symbol table).
	 * This is equivalent to {@code delete()}, but we plan to deprecate {@code delete()}.
	 *
	 * @param  key the key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void remove(String key) {
		if (key == null) throw new IllegalArgumentException("calls remove() with null key");
		st.remove(key);
	}

	/**
	 * Returns true if this symbol table contain the given key.
	 *
	 * @param  key the key
	 * @return {@code true} if this symbol table contains {@code key} and
	 *         {@code false} otherwise
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public boolean contains(String key) {
		if (key == null) throw new IllegalArgumentException("calls contains() with null key");
		return st.containsKey(key);
	}

	/**
	 * Returns the number of key-value pairs in this symbol table.
	 *
	 * @return the number of key-value pairs in this symbol table
	 */
	public int size() {
		return st.size();
	}

	/**
	 * Returns true if this symbol table is empty.
	 *
	 * @return {@code true} if this symbol table is empty and {@code false} otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns all keys in this symbol table in ascending order,
	 * as an {@code Iterable}.
	 * <p>
	 * To iterate over all of the keys in the symbol table named {@code st},
	 * use the foreach notation: {@code for (Key key : st.keys())}.
	 *
	 * @return all keys in this symbol table in ascending order
	 */
	public Iterable<String> keys() {
		return st.keySet();
	}

	public String min() {
		if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
		return st.firstKey();
	}

	/**
	 * Returns the largest key in this symbol table.
	 *
	 * @return the largest key in this symbol table
	 * @throws NoSuchElementException if this symbol table is empty
	 */
	public String max() {
		if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
		return st.lastKey();
	}

	/**
	 * Returns the smallest key in this symbol table greater than or equal to {@code key}.
	 *
	 * @param  key the key
	 * @return the smallest key in this symbol table greater than or equal to {@code key}
	 * @throws NoSuchElementException if there is no such key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public String ceiling(String key) {
		if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
		String k = st.ceilingKey(key);
		if (k == null) throw new NoSuchElementException("argument to ceiling() is too large");
		return k;
	}

	/**
	 * Returns the largest key in this symbol table less than or equal to {@code key}.
	 *
	 * @param  key the key
	 * @return the largest key in this symbol table less than or equal to {@code key}
	 * @throws NoSuchElementException if there is no such key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public String floor(String key) {
		if (key == null) throw new IllegalArgumentException("argument to floor() is null");
		String k = st.floorKey(key);
		if (k == null) throw new NoSuchElementException("argument to floor() is too small");
		return k;
	}


	@Override
	public Iterator<Key> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Unit tests the {@code ST} data type.
	 *
	 * @param args the command-line arguments
	 */
	//    public static void main(String[] args) {
	//        ST<String, Integer> st = new ST<String, Integer>();
	//        for (int i = 0; !StdIn.isEmpty(); i++) {
	//            String key = StdIn.readString();
	//            st.put(key, i);
	//        }
	//        for (String s : st.keys())
	//            StdOut.println(s + " " + st.get(s));
	//    }
}