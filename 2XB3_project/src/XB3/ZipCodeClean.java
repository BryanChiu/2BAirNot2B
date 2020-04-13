package XB3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Cleans listings based on zipcode and writes to new CSV
 * 
 * @author Michael Yohannes
 */

public class ZipCodeClean {

    /**
     * Writes the listing to the output CSV.
     * 
	 * @param dirtyData uncleaned data
	 * @throws IOException if the CSV file can not be opened
     */
	public static void writeCleanedListings(ArrayList<Listing> dirtyData) throws IOException {
		HashMap<String, LinkedList<Listing>> zipcodes = uncleanedData(dirtyData);
		ArrayList<Listing> cleanListings = cleanData(zipcodes);
		WriteListingsToCSV.WriteListings(cleanListings, "cleaned");
	}

    /**
     * Returns a HashMap with the key being the zipcode and a linked list of listings which correspond to that zip code.
     * 
     * @param dirtyData an ArrayList of Listings with uncleaned data. 
     * @return a HashMap with the key being the zipcode and a linked list of listings which correspond to that zip code
     */
	public static HashMap<String, LinkedList<Listing>> uncleanedData(ArrayList<Listing> dirtyData) {
		HashMap<String, LinkedList<Listing>> zipcodes = new HashMap<>();
		for (Listing listing : dirtyData) {
			String FSA = listing.getZipcode().toLowerCase();
			if (FSA.length() < 3) {
				continue;
			} else {
				FSA = FSA.substring(0, 3);
				if (zipcodes.get(FSA) == null) {
					LinkedList<Listing> sameFSA = new LinkedList<Listing>();
					sameFSA.add(listing);
					zipcodes.put(FSA, sameFSA);
				} else {
					LinkedList<Listing> sameFSA = zipcodes.get(FSA);
					sameFSA.add(listing);
					zipcodes.put(FSA, sameFSA);
				}

			}

		}
		
		return zipcodes;
	}

    /**
     * Returns an ArrayList of Listings after processing the mean and standard deviation.
     *
     * @param zipcodes hashmap of string to listing linked list, corresponding to zip codes
     * @return an ArrayList of Listings after processing the mean and standard deviation
     */
	public static ArrayList<Listing> cleanData(HashMap<String, LinkedList<Listing>> zipcodes) {
		ArrayList<Listing> cleanListings = new ArrayList<>();
		Set<String> keys = zipcodes.keySet();

		for (String key : keys) {
			LinkedList<Listing> sameCode = zipcodes.get(key);
			DescriptiveStatistics desc = new DescriptiveStatistics();

			for (Listing listing : sameCode) {
				desc.addValue(listing.getAvail365());
			}

			Double mean = desc.getMean();
			Double sdev = desc.getStandardDeviation();

			for (Listing listing : sameCode) {
				if (listing.getAvail365() >= mean - sdev && listing.getAvail365() <= mean + sdev && validFields(listing)) {
					cleanListings.add(listing);
				}
			}
		}
		
		return cleanListings;

	}
	
    /**
     * Determines whether all the fields are valid in a listing.
     *
     * @param listing listing to check
     * @return whether all the fields are valid in a listing
     */
	public static boolean validFields(Listing listing) {
		return 	listing.getBathrooms() != null &&
				listing.getBedrooms() != null &&
				listing.getAvail365() > 0 &&
				listing.getReviewRating() != null;
	}
}
