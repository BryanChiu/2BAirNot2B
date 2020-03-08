package XB3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

public class Main {

	public static void main(String[] args) throws CsvValidationException, IOException {
		File currentDir = new File(".");
		File parentDir = currentDir.getParentFile();
		File inFile = new File(parentDir, "listings.csv");
		ArrayList<Listing> listings = new ArrayList<>();
		FileReader file = new FileReader(inFile);
		CSVReaderHeaderAware reader = new CSVReaderHeaderAware(file);
		Map<String, String> listingData;

		while ((listingData = reader.readMap()) != null) {
			listings.add(new Listing(listingData));
		}
		
		ZipCodeClean cleaner = new ZipCodeClean();
		cleaner.uncleanedData(listings);
		cleaner.cleanData();
		cleaner.writeCleanedListing();
		
		

//		Writer Code-----------------------
//		String[] header = { "id", "host_is_superhost", "neighbourhood_cleansed", "zipcode", "property_type",
//				"accommodates", "bathrooms", "bedrooms", "price", "review_scores_rating", "availability_365" };
//
//		File outFile = new File("few.csv");
//		CSVWriter writer = new CSVWriter(new FileWriter(outFile));
//		writer.writeNext(header);
//		writer.writeNext(listings.get(0).toSeq());
//		writer.close();
//		Writer Code-----------------------

//		Double[] vals = { 2.0, 1.0, 5.5, 9.4 };
//
//		DescriptiveStatistics desc = new DescriptiveStatistics();
//		for (Double double1 : vals) {
//			desc.addValue(double1);
//		}
		
//		System.out.println(desc.getMean());
//		System.out.println(desc.getStandardDeviation());

	}
}
