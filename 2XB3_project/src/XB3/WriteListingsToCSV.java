package XB3;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

/**
 * Writes all the listings to an output CSV file
 * 
 * @author Michael Yohannes
 */
public class WriteListingsToCSV {

    /**
     * Writes all the listings to an output CSV file, based on the listings provided in the input parameter.
     * 
     * @param file name of output file
     * @param listings listings corresponding to cleaned data from ZipCodeClean class
     * @throws IOException if the file cannot be created, or other similar file issues
     */
	public static void WriteListings(ArrayList<Listing> listings, String file) throws IOException {
		String[] header = { "id", "host_is_superhost", "neighbourhood_cleansed", "zipcode", "property_type",
				"accommodates", "bathrooms", "bedrooms", "price", "review_scores_rating", "availability_365" };

		CSVWriter writer = new CSVWriter(new FileWriter(new File(file + ".csv")));
		writer.writeNext(header);

		for (Listing listing : listings) {
			writer.writeNext(listing.toSeq());
		}

		writer.close();

	}

}
