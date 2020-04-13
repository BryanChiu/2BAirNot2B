package XB3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

/**
 * Reads AirBnB listings from CSV
 * 
 * @author Michael Yohannes
 */

public class ReadListingsFromCSV {
	
    /**
     * Opens the CSV file and creates an ArrayList of Listing objects, and returns the array.

       @param file is a String corresponding to the filename. 
     * @return Returns an ArrayList of Listing objects.      
     * @throws FileNotFoundException Raises an error if the File is unable to be found.
     * @throws IOException Raises an error if an input/output exception occurs while writing to the file.
     * @throws CsvValidationException Raises an error if the CSV is unable to be validated appropriately. 
     */
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
			
		}		
		reader.close();
		return listings;
	}

}
