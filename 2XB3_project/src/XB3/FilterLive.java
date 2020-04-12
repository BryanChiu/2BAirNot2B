package XB3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

public class FilterLive {
	private ArrayList<HashMap<Filter, Integer>> liveFilters = new ArrayList<>();
	private Integer numfiltersApllied = 0;
	private HashMap<String, RedBlackBST<Listing, Integer>> liveTrees = new HashMap<>();
	private final static String[] liveTreeTypes = { "revenue", "bathrooms", "bedrooms" };

	FilterLive() throws IOException, CsvValidationException {
		liveTrees.put("revenue", new RevBST());
		liveTrees.put("bedrooms", new BedBST());
		liveTrees.put("bathrooms", new BathBST());
		ArrayList<Listing> cleanListings = ReadListingsFromCSV.ReadListings("cleaned");
//		System.out.println(cleanListings.size());

		for (Listing listing : cleanListings) {
			for (String tree : liveTreeTypes) {
				liveTrees.get(tree).put(Listing.changeTreeType(listing, tree), 1);
			}
		}
	}

	public HashMap<String, RedBlackBST<Listing, Integer>> getLiveTrees() {
		return liveTrees;
	}

	public void addFilter(String someFilter) throws Exception {
		String[] strSplit = someFilter.split(" ");
		Filter newFilter = new Filter(strSplit);
		HashMap<Filter, Integer> filterMap = new HashMap<>();
		filterMap.put(newFilter, numfiltersApllied);
		liveFilters.add(filterMap);
		ArrayList<Listing> Ffile;
		switch (newFilter.getQuery()) {
		case "revenue":
			Ffile = new ArrayList<Listing>(((RevBST) liveTrees.get("revenue")).getRangeToDelete(newFilter));
			deleteListings(Ffile);
			WriteListingsToCSV.WriteListings(Ffile, "F" + numfiltersApllied);
			break;

		case "bedrooms":
			Ffile = new ArrayList<Listing>(((BedBST) liveTrees.get("bedrooms")).getRangeToDelete(newFilter));
			deleteListings(Ffile);
			WriteListingsToCSV.WriteListings(Ffile, "F" + numfiltersApllied);
			break;

		case "bathrooms":
			Ffile = new ArrayList<Listing>(((BathBST) liveTrees.get("bathrooms")).getRangeToDelete(newFilter));
			deleteListings(Ffile);
			WriteListingsToCSV.WriteListings(Ffile, "F" + numfiltersApllied);
			break;

		default:
			System.out.println("default");
			break;
		}

		if (!liveTreeInvariant()) {
			throw new Exception("Trees are not balanced");
		}

		numfiltersApllied++;
		
		System.out.println(toString());

	}
	
	public void deleteListings(ArrayList<Listing> rangeToDelete) {
		for (Listing listing : rangeToDelete) {
			for (String key : liveTrees.keySet()) {
				liveTrees.get(key).delete(Listing.changeTreeType(listing, key));
			}
		}
	}

	public void removeFilter(String someFilter) throws Exception {
		String[] strSplit = someFilter.split(" ");
		Filter filter = new Filter(strSplit);
		int FfileIndex = 10;
		int seqIndex = 10;

		for (int i = 0; i < liveFilters.size(); i++) {
			if (liveFilters.get(i).containsKey(filter)) {
				FfileIndex = liveFilters.get(i).get(filter);
				seqIndex = i;
			}
		}

		addAnFFile(seqIndex, FfileIndex);
		System.out.println(toString());
	}

	public void addAnFFile(Integer seqInteger, Integer FfileInteger)
			throws FileNotFoundException, CsvValidationException, IOException {
		ArrayList<Listing> Ffile = ReadListingsFromCSV.ReadListings("F" + FfileInteger);
		Set<Integer> indecesToDelete = new HashSet<Integer>();
		int cmp;
		for (int i = seqInteger; i < liveFilters.size(); i++) {
			for (Filter filter : liveFilters.get(i).keySet()) {
				switch (filter.getQuery()) {
				case "revenue":
					for (int j = 0; j < Ffile.size(); j++) {
						cmp = Listing.changeTreeType(Ffile.get(i), "revenue").getRevenue()
								.compareTo(new BigDecimal(filter.getValue()));
						if (filter.getRelation().equals(">") && cmp < 0) {
							indecesToDelete.add(j);
						}

						if (filter.getRelation().equals("<") && cmp > 0) {
							indecesToDelete.add(j);
						}
					}
					break;

				case "bedrooms":
					for (int j = 0; j < Ffile.size(); j++) {
						cmp = Listing.changeTreeType(Ffile.get(i), "bedrooms").getBedrooms()
								.compareTo(Double.valueOf(filter.getValue()));
						if (filter.getRelation().equals(">") && cmp < 0) {
							indecesToDelete.add(j);
						}

						if (filter.getRelation().equals("<") && cmp > 0) {
							indecesToDelete.add(j);
						}
					}
					break;

				case "bathrooms":
					for (int j = 0; j < Ffile.size(); j++) {
						cmp = Listing.changeTreeType(Ffile.get(i), "bathrooms").getBathrooms()
								.compareTo(Double.valueOf(filter.getValue()));
						if (filter.getRelation().equals(">") && cmp < 0) {
							indecesToDelete.add(j);
						}

						if (filter.getRelation().equals("<") && cmp > 0) {
							indecesToDelete.add(j);
						}
					}
					break;

				default:
					System.out.println("default");
					break;
				}

			}
		}

		for (Integer integer : indecesToDelete) {
			Ffile.remove(integer);
		}

		for (Listing listing : Ffile) {
			for (String key : liveTrees.keySet()) {
				liveTrees.get(key).put(Listing.changeTreeType(listing, key), 1);
			}

		}

	}

	public boolean liveTreeInvariant() {
		int revSize = liveTrees.get("revenue").size();
		boolean sameSize = true;
		for (String key : liveTreeTypes) {
			sameSize = sameSize && liveTrees.get(key).size() == revSize;
		}
		return sameSize;
	}
	
	public void showTop(int x) {
		RedBlackBST<Listing, Integer> revBST =  liveTrees.get("revenue");
		for (Listing listing : revBST.revInorder(x) ) {
			System.out.println(listing);
		}
	}
	
	public String toString() {
		RedBlackBST<Listing, Integer> revBST =  liveTrees.get("revenue");
		return "\n"+ revBST.size() + " listings matching your query";
	}

}
