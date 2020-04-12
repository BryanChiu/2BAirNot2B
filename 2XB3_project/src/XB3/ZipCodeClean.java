package XB3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import com.opencsv.CSVWriter;

public class ZipCodeClean {

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
	
	public static boolean validFields(Listing listing) {
		return 	listing.getBathrooms() != null &&
				listing.getBedrooms() != null &&
				listing.getAvail365() > 0 &&
				listing.getReviewRating() != null;
	}

	public static void writeCleanedListings(ArrayList<Listing> dirtyData) throws IOException {
		HashMap<String, LinkedList<Listing>> zipcodes = uncleanedData(dirtyData);
		ArrayList<Listing> cleanListings = cleanData(zipcodes);
//		for (Listing listing : cleanListings) {
//			System.out.println(listing.getId() + ": " + listing.getRevenue());
//		}
		WriteListingsToCSV.WriteListings(cleanListings, "cleaned");
		
		
	}

}
