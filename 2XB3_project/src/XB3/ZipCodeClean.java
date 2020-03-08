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

	private HashMap<String, LinkedList<Listing>> zipcodes = new HashMap<>();
	private ArrayList<Listing> cleanListings = new ArrayList<>();

	public void uncleanedData(ArrayList<Listing> dirtyData) {
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

	}

	public void cleanData() {
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
				if (listing.getAvail365() >= mean - sdev && listing.getAvail365() <= mean + sdev) {
					cleanListings.add(listing);
				}
			}
		}

	}

	public void writeCleanedListing() throws IOException {
		
		System.out.println(cleanListings.size());

		String[] header = { "id", "host_is_superhost", "neighbourhood_cleansed", "zipcode", "property_type",
				"accommodates", "bathrooms", "bedrooms", "price", "review_scores_rating", "availability_365" };

		File outFile = new File("cleaned.csv");
		CSVWriter writer = new CSVWriter(new FileWriter(outFile));
		writer.writeNext(header);

		for (Listing listing : cleanListings) {
			writer.writeNext(listing.toSeq());
		}

		writer.close();

	}

	public void writeCleanedData(ArrayList<Listing> dirtyData) {
		uncleanedData(dirtyData);
		cleanData();
		
		
	}

}
