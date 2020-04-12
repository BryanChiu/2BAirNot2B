package XB3;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

public class WriteListingsToCSV {

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
