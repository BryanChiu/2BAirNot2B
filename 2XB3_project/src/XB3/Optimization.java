package XB3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.management.openmbean.InvalidKeyException;

import org.omg.DynamicAny.DynAnyPackage.InvalidValue;

import com.opencsv.exceptions.CsvValidationException;

public class Optimization {
	private ArrayList<Listing> idSortedListings;
	private int myId;
	private Integer myIndex;
	private RevBST betterThanMyID = new RevBST();

	public Optimization(int id) throws FileNotFoundException, CsvValidationException, IOException, InvalidValue {
		ArrayList<Listing> cleanListings = ReadListingsFromCSV.ReadListings("cleaned");
		int n = cleanListings.size();
		this.myId = id;
		QuickSort ob = new QuickSort();
		ob.sort(cleanListings, 0, n - 1);
		this.idSortedListings = cleanListings;
		BinarySearch obj = new BinarySearch();
		myIndex = obj.binarySearch(idSortedListings, 0, n - 1, this.myId);

		for (int i = myIndex; i < idSortedListings.size(); i++) {
			betterThanMyID.put(Listing.changeTreeType(idSortedListings.get(i), "revenue"), 1);
		}

	}

	public void showTopZipCodes() {
		SummaryStats sumStats = new SummaryStats(betterThanMyID);
		sumStats.showZipCodes();

	}

	public void showTopNeighborhoods() {
		SummaryStats sumStats = new SummaryStats(betterThanMyID);
		sumStats.showNeighborhoods();
	}

	public void optimizeReport() {
		int numberOfBetter = betterThanMyID.size();

		int higherDayPrice = 0;
		int lowerDayPrice = 0;

		int higherBooked = 0;
		int lowerBooked = 0;

		int higherAccom = 0;
		int lowerAccom = 0;

		int higherReviewRating = 0;

		for (int i = myIndex; i < idSortedListings.size(); i++) {

			if (idSortedListings.get(myIndex).getDayPrice() > idSortedListings.get(i).getDayPrice()) {
				lowerDayPrice++;
			} else {
				higherDayPrice++;
			}

			if ((365 - idSortedListings.get(myIndex).getAvail365()) > (365 - idSortedListings.get(i).getDayPrice())) {
				lowerBooked++;
			} else {
				higherBooked++;
			}

			if (idSortedListings.get(myIndex).getAccommodates() > idSortedListings.get(i).getAccommodates()) {
				lowerAccom++;
			} else {
				higherAccom++;
			}

			if (idSortedListings.get(myIndex).getReviewRating() < idSortedListings.get(i).getReviewRating()) {
				higherReviewRating++;
			}
		}

		Double higherDayPricePerc = 100 * Double.valueOf(higherDayPrice) / numberOfBetter;
		Double lowerDayPricePerc = 100 * Double.valueOf(lowerDayPrice) / numberOfBetter;

		Double higherBookedPerc = 100 * Double.valueOf(lowerDayPrice) / numberOfBetter;
		Double lowerBookedPerc = 100 * Double.valueOf(lowerDayPrice) / numberOfBetter;

		Double higherAccomPerc = 100 * Double.valueOf(higherAccom) / numberOfBetter;
		Double lowerAccomPerc = 100 * Double.valueOf(lowerAccom) / numberOfBetter;

		Double higherReviewRatingPerc = 100 * Double.valueOf(higherReviewRating) / numberOfBetter;

		System.out.println("\n###############Optimization Report###############\n");
		if (higherDayPricePerc > lowerDayPricePerc) {
			System.out.println(String.format("%.1f", higherDayPricePerc) + "% of listings with higher revenue, have higher day price");
		} else {
			System.out.println(String.format("%.1f", lowerDayPricePerc) + "% of listings with higher revenue, have a lower day price");
		}

		if (higherBookedPerc > lowerBookedPerc) {
			System.out.println(String.format("%.1f", higherBookedPerc) + "% of listings with higher revenue, are booked more");
		} else {
			System.out.println(String.format("%.1f", lowerBookedPerc) + "% of listings with higher revenue, are booked less");
		}

		if (higherAccomPerc > lowerAccomPerc) {
			System.out.println(String.format("%.1f", higherAccomPerc) + "% of listings with higher revenue, can accomadate more people");
		} else {
			System.out.println(String.format("%.1f", lowerAccomPerc) + "% of listings with higher revenue, accomadate less people");
		}

		System.out.println(String.format("%.1f", higherReviewRatingPerc) + "% of listings with higher revenue, higher review score");

	}

	private static class BinarySearch {
		// Returns index of x if it is present in arr[l..
		// r], else return -1
		int binarySearch(ArrayList<Listing> arr, int l, int r, int x) {
			if (r >= l) {
				int mid = l + (r - l) / 2;

				// If the element is present at the
				// middle itself
				if (arr.get(mid).getId() == x)
					return mid;

				// If element is smaller than mid, then
				// it can only be present in left subarray
				if (arr.get(mid).getId() > x)
					return binarySearch(arr, l, mid - 1, x);

				// Else the element can only be present
				// in right subarray
				return binarySearch(arr, mid + 1, r, x);
			}

			// We reach here when element is not present
			// in array
			return -1;
		}
	}

	private static class QuickSort {
		/*
		 * This function takes last element as pivot, places the pivot element at its
		 * correct position in sorted array, and places all smaller (smaller than pivot)
		 * to left of pivot and all greater elements to right of pivot
		 */
		int partition(ArrayList<Listing> arr, int low, int high) {
			int pivot = arr.get(high).getId();
			int i = (low - 1); // index of smaller element
			for (int j = low; j < high; j++) {
				// If current element is smaller than the pivot
				if (arr.get(j).getId() < pivot) {
					i++;

					// swap arr[i] and arr[j]
					Listing temp = arr.get(i);
					arr.set(i, arr.get(j));
					arr.set(j, temp);
				}
			}

			// swap arr[i+1] and arr[high] (or pivot)
			Listing temp = arr.get(i + 1);
			arr.set(i + 1, arr.get(high));
			arr.set(high, temp);
			return i + 1;
		}

		/*
		 * The main function that implements QuickSort() arr[] --> Array to be sorted,
		 * low --> Starting index, high --> Ending index
		 */
		public void sort(ArrayList<Listing> arr, int low, int high) {
			if (low < high) {
				/*
				 * pi is partitioning index, arr[pi] is now at right place
				 */
				int pi = partition(arr, low, high);

				// Recursively sort elements before
				// partition and after partition
				sort(arr, low, pi - 1);
				sort(arr, pi + 1, high);
			}
		}

		/* A utility function to print array of size n */
		public static void printArray(ArrayList<Listing> cleanListings) {
			int n = cleanListings.size();
			for (int i = 0; i < n; ++i) {
				System.out.println(i + ": " + cleanListings.get(i).getId());
			}
		}

	}
}
