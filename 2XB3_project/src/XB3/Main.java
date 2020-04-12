package XB3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

public class Main {

	public static void main(String[] args) throws Exception {

		ArrayList<Listing> dirtyListings = ReadListingsFromCSV.ReadListings("listings");
		ZipCodeClean.writeCleanedListings(dirtyListings);

		Scanner key = new Scanner(System.in);
		System.out.println("#####################Welcome  2BAirNot2B#####################");
		while (true) {
			System.out.println("Please enter 1 of the following modes\nBEGIN_QUERY\nBEGIN_OPTIMIZATION");
			System.out.println("Or enter FINISH to exit this application");
			String command = key.nextLine();
			if (command.toLowerCase().equals("finish")) {
				break;
			}

			switch (command.toUpperCase()) {
			case "BEGIN_QUERY":
				FilterLive liveFilter = new FilterLive();
				while (true) {
					System.out.println(
							"Please enter 1 of the following modes...\nBEGIN_ADD_QUERY\nBEGIN_REMOVE_QUERY\nEND_QUERY");
					command = key.nextLine();
					if (command.toUpperCase().equals("END_QUERY")) {
						break;
					}
					switch (command.toUpperCase()) {
					case "BEGIN_ADD_QUERY":
						while (true) {
							System.out.println(
									"Enter a valid query Examples...\nREVENUE > 10000, BATHROOMS < 6, or BEDROOMS > 2");
							System.out.println(
									"You can also view the top x earning listings of your query by entering SHOWTOP = x");
							System.out.println("You can go back a level with END_ADD_QUERY");
							command = key.nextLine();
							if (command.toUpperCase().equals("END_ADD_QUERY")) {
								break;
							}
							String[] str = command.split(" ");
							if (str[0].toUpperCase().equals("SHOWTOP")) {
								if (str.length == 3 && str[1].equals("=") && Integer.parseInt(str[2]) > 0) {
									liveFilter.showTop(Integer.parseInt(str[2]));
									continue;
								} else {
									System.out.println("Invalid input");
									continue;
								}
							}
							if (!Filter.validFilter(command)) {
								System.out.println("Not a valid filter");
								continue;
							}
							liveFilter.addFilter(command);
						}
						break;
					case "BEGIN_REMOVE_QUERY":

						while (true) {
							System.out.println(
									"Enter a query to remove query Examples...\nREVENUE > 10000, BATHROOMS < 6, or BEDROOMS > 2");
							System.out.println(
									"You can also view the top x earning listings of your query by entering SHOWTOP = x");
							System.out.println("You can go back a level with END_REMOVE_QUERY");
							command = key.nextLine();
							if (command.toUpperCase().equals("END_REMOVE_QUERY")) {
								break;
							}
							String[] str = command.split(" ");
							if (str[0].toUpperCase().equals("SHOWTOP")) {
								if (str.length == 3 && str[1].equals("=") && Integer.parseInt(str[2]) > 0) {
									liveFilter.showTop(Integer.parseInt(str[2]));
									continue;
								} else {
									System.out.println("Invalid input");
									continue;
								}
							}
							if (!Filter.validFilter(command)) {
								System.out.println("Not a valid filter");
								continue;
							}
							try {
								liveFilter.removeFilter(command);
							} catch (Exception e) {
								System.out.println("Filter does not exist");
							}

						}
						break;
					default:
						System.out.println(
								"Invalid input");
						break;
					}
				}
				break;

			case "BEGIN_OPTIMIZATION":
				while (true) {
					System.out.println("Please enter a valid Property Id Examples: 6056087, 40587262 ...");
					System.out.println("You can go back a level with END_OPTIMIZATION");
					command = key.nextLine();
					if (command.toUpperCase().equals("END_OPTIMIZATION")) {
						break;
					}
					try {
						Optimization opt = new Optimization(Integer.parseInt(command));
						opt.optimizeReport();
						opt.showTopZipCodes();
						opt.showTopNeighborhoods();
					} catch (Exception e) {
						System.out.println("Invalid property id");
						continue;
					}
				}
				break;
			default:
				System.out.println("Invalid input");
				break;
			}

		}
		System.out.println("#####################Exit  2BAirNot2B#####################");
	}
}
