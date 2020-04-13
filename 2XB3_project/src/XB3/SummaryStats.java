package XB3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Class that represents a summary of data from appropriate listings
 * 
 * @author Michael Yohannes
 */

public class SummaryStats {
	private String summaryType;
	private HashMap<String, DescriptiveStatistics> mapZipCode = new HashMap<>();
	private HashMap<String, DescriptiveStatistics> mapNeighbor = new HashMap<>();

    /**
     * Initializes the listing and statistics accordingly in the revenue BST. 
     * 
     * @param revBST revenue BST.
     */
	SummaryStats(RevBST revBST) {

		for (Listing listing : revBST.inorder()) {
			if (mapNeighbor.containsKey(listing.getNeighborhood())) {
				DescriptiveStatistics rev = mapNeighbor.get(listing.getNeighborhood());
				rev.addValue((int) listing.getRevenue().doubleValue());
				mapNeighbor.put(listing.getNeighborhood(), rev);
			} else {
				DescriptiveStatistics rev = new DescriptiveStatistics();
				rev.addValue((int) listing.getRevenue().doubleValue());
				mapNeighbor.put(listing.getNeighborhood(), rev);
			}

			if (mapZipCode.containsKey(listing.getZipcode().substring(0, 3).toUpperCase())) {
				DescriptiveStatistics rev = mapZipCode.get(listing.getZipcode().substring(0, 3).toUpperCase());
				rev.addValue((int) listing.getRevenue().doubleValue());
				mapZipCode.put(listing.getZipcode().substring(0, 3).toUpperCase(), rev);
			} else {
				DescriptiveStatistics rev = new DescriptiveStatistics();
				rev.addValue((int) listing.getRevenue().doubleValue());
				mapZipCode.put(listing.getZipcode().substring(0, 3).toUpperCase(), rev);
			}
		}

	}

    /**
     * Shows all of the neighborhood data and the mean and standard deviation data.
     */
	public void showNeighborhoods() {
		ArrayList<NeighborhoodSummary> neighborArray = new ArrayList<>();
		for (String neighborhood : mapNeighbor.keySet()) {
			neighborArray.add(new NeighborhoodSummary(neighborhood, mapNeighbor.get(neighborhood)));
		}

		Collections.sort(neighborArray);

		System.out.println("\n###############Better Performers By Neighborhood###############\n");
		for (int i = neighborArray.size() - 1; i > 0 && i > neighborArray.size() - 5; i--) {
			System.out.println(
				neighborArray.get(i).getNeighborhood() + 
				":\nmean Revenue=" + 
				(int) neighborArray.get(i).getNeighborhoodData().getMean() +
				"\nstdDev=" + 
				(int) neighborArray.get(i).getNeighborhoodData().getStandardDeviation()
			);
		}
	}
	
    /**
     * Shows all of the zipcode data and the mean and standard deviation data.
     */
	public void showZipCodes() {
		ArrayList<ZipCodeSummary> zipcodeArray = new ArrayList<>();
		for (String zipcode: mapZipCode.keySet()) {
			zipcodeArray.add(new ZipCodeSummary(zipcode, mapZipCode.get(zipcode)));
		}

		Collections.sort(zipcodeArray);
		
		System.out.println("\n###############Better Performers By Zip Code###############\n");
		for (int i = zipcodeArray.size() - 1; i > 0 && i > zipcodeArray.size() - 5; i--) {
			System.out.println(
					zipcodeArray.get(i).getZipcode() + 
					":\nmean Revenue=" + 
					(int) zipcodeArray.get(i).getZipcodeData().getMean() +
					"\nstdDev=" + 
					(int) zipcodeArray.get(i).getZipcodeData().getStandardDeviation());

		}
		

	}
	
}
