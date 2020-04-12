package XB3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

public class ReadListingsFromCSV {
	
	public static ArrayList<Listing> ReadListings(String file) throws FileNotFoundException, IOException, CsvValidationException {
		ArrayList<Listing> listings = new ArrayList<>();
		CSVReaderHeaderAware reader = new CSVReaderHeaderAware(
				new FileReader(
						new File(
								(new File(".")).getParentFile(), file + ".csv")
						)
				);
		Map<String, String> listingData;
		
		while ((listingData = reader.readMap()) != null) {
			try {
				listings.add(new Listing(listingData, "revenue"));
			} catch (Exception nullPoException) {
				continue;
			}
//			listings.add(new Listing(listingData, "revenue"));
			
		}		
		reader.close();
//		System.out.println("Read: " + listings.size());
		return listings;
	}

}
