import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AlgorithmsProgrammingQuestion1 {

	// This program calculates the number of inversions of a text file
	// (included) contains. This file contains all of the 100,000 integers
	// between 1 and 100,000 (inclusive) in a random order, with no integer
	// repeated. The task was to compute the number of inversions in the file
	// given, where the ith row of the file indicates the ith entry of an array.
	// I have solved this both with a brute-force method and a much more
	// efficient divide and conquer algorithm.

	public static void main(String[] args) {

		// The following method solves the assignment via brute-force.
		// You can comment the divideAndConquer method and uncomment the
		// bruteForce method to run the brute-force algorithm and obtain the
		// same result of 2,407,905,288 inversions.

		// bruteForce();

		// The following method solves the assignment via divide and conquer.
		divideAndConquer();

	}

	private static void bruteForce() {

		// This method solves the problem via the brute-force method.

		// Convert the text file located at the directory into an ArrayList. You
		// will have to change this path to match the location of your file.
		ArrayList<Integer> myIntArrayList = parseFile("/Users/jbutewicz/Desktop/IntegerArray.txt");

		// This is the counter that will count the number of inversions.
		long inversionCounter = 0;

		// The brute-force method involves two for loops. The first for loop
		// goes through the entire ArrayList of Integers.
		for (int i = 0; i < myIntArrayList.size(); i++) {

			// The second for loop goes through the ArrayList from the index
			// starting after the integer being checked to the end of the
			// ArrayList.
			for (int j = i + 1; j < myIntArrayList.size(); j++) {

				// If the value of index i is greater than the value of index j,
				// then we have an inversion in the ArrayList, so we increment
				// the counter.
				if (myIntArrayList.get(i) > myIntArrayList.get(j)) {
					inversionCounter++;
				}
			}
		}

		// Prints out the solution to the assignment.
		out.print(inversionCounter);
	}

	public static ArrayList<Integer> parseFile(String fileName) {

		// This method converts a text file of name fileName into an ArrayList
		// of Integers via a BufferedReader. The test file should be a list of
		// unique
		// integers separated by a new line in order to be processed correctly
		// by this method such as:
		// 54044
		// 14108
		// 79294
		// 29649
		// etc.

		BufferedReader bufferedReader = null;
		ArrayList<Integer> listOfInts = new ArrayList<Integer>();

		try {
			bufferedReader = new BufferedReader(new FileReader(fileName));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.isEmpty()) {
					listOfInts = new ArrayList<Integer>();
				} else {
					listOfInts.add(Integer.parseInt(line));
				}
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return listOfInts;
	}

	private static void divideAndConquer() {

		// This method solves the problem via the divide and conquer method.

		// Convert the text file to an ArrayList of Integers as before.
		ArrayList<Integer> myIntArrayList = parseFile("/Users/jbutewicz/Desktop/IntegerArray.txt");

		// Compute the number of inversions and print out the solution to the
		// assignment.
		System.out.println(countInversions(myIntArrayList));

	}

	public static long countInversions(ArrayList<Integer> myIntArrayList) {

		// As discussed in the class lectures we have to divide and conquer this
		// problem by splitting the counting into three smaller problems of
		// counting the left inversions, counting the right inversions, and
		// counting the split inversions.

		int mid = myIntArrayList.size() / 2;
		int k;

		long inversionCounterLeft, inversionCounterRight, inversionCounterSplit;

		// Corner case were ArrayList is not greater than one.
		if (myIntArrayList.size() <= 1)
			return 0;

		// Split the original ArrayList into a left a right ArrayList.
		ArrayList<Integer> leftArrayList = new ArrayList<Integer>();
		ArrayList<Integer> rightArrayList = new ArrayList<Integer>();

		for (k = 0; k < mid; k++)
			leftArrayList.add(myIntArrayList.get(k));

		for (k = 0; k < myIntArrayList.size() - mid; k++)
			rightArrayList.add(myIntArrayList.get(mid + k));

		// Recursively continue this process of dividing the list into smaller
		// and smaller sizes and getting the values of the left and right
		// inversions.
		inversionCounterLeft = countInversions(leftArrayList);
		inversionCounterRight = countInversions(rightArrayList);

		// Get the count for the split inversions.
		ArrayList<Integer> result = new ArrayList<Integer>();

		inversionCounterSplit = mergeAndCount(leftArrayList, rightArrayList,
				result);

		for (k = 0; k < myIntArrayList.size(); k++)
			myIntArrayList.set(k, result.get(k));

		// Return the total number on inversions.
		return (inversionCounterLeft + inversionCounterRight + inversionCounterSplit);

	}

	public static int mergeAndCount(ArrayList<Integer> left,
			ArrayList<Integer> right, ArrayList<Integer> result) {

		// Merge the two lists together.
		int a = 0, b = 0, splitCount = 0, i;

		while ((a < left.size()) && (b < right.size())) {
			if (left.get(a) <= right.get(b))
				result.add(left.get(a++));
			else {
				// Inversions have been found.
				result.add(right.get(b++));
				splitCount += left.size() - a;
			}
		}

		if (a == left.size())
			for (i = b; i < right.size(); i++)
				result.add(right.get(i));
		else
			for (i = a; i < left.size(); i++)
				result.add(left.get(i));

		return splitCount;
	}

}