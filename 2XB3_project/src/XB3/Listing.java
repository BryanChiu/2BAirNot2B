package XB3;

import java.math.BigDecimal;
import java.util.Map;

public class Listing implements Comparable<Listing> {

	private int id;
	private boolean superHost;
	private String neighborhood;
	private String zipcode;
	private String propType;
	private Double accommodates;
	private Double bathrooms;
	private Double bedrooms;
	private Double dayPrice;
	private Double reviewRating;
	private Double avail365;
//	private Double revenue;
	private BigDecimal revenue;
	private String treeType;
	private Map<String, String> listingData;
	
	Listing(Map<String, String> listingData, String treeType) {
		this.listingData = listingData;
		id = Integer.parseInt(listingData.get("id"));
		superHost = listingData.get("host_is_superhost").contains("t") ? true : false;
		neighborhood = listingData.get("neighbourhood_cleansed");
		zipcode = listingData.get("zipcode");
		propType = listingData.get("property_type");
		accommodates = validNumber(listingData.get("accommodates"));
		
		bathrooms = validNumber(listingData.get("bathrooms"));
		bathrooms = bathrooms + id/Math.pow(10, String.valueOf(id).length());
		
		bedrooms = validNumber(listingData.get("bedrooms"));
		bedrooms = bedrooms + id/Math.pow(10, String.valueOf(id).length());
		
		dayPrice = validNumber(listingData.get("price"));
		reviewRating = validNumber(listingData.get("review_scores_rating"));
		avail365 = validNumber(listingData.get("availability_365"));
		
		String dub = String.valueOf((365 - avail365) * dayPrice); 
		String dubStr = String.valueOf(dub).substring(0, dub.indexOf(".")) + "."+ String.valueOf(id);
		revenue = new BigDecimal(dubStr);
		
		this.treeType = treeType;

	}
	
	public static Listing changeTreeType(Listing listing, String treeType) {
		return new Listing(listing.getListingData(), treeType);
	}

	public Double validNumber(String val) {
		if (val.equals("")) {
			return null;
		} else {
			return Double.parseDouble(val);
		}

	}

	String[] header = { "id", "host_is_superhost", "neighbourhood_cleansed", "zipcode", "property_type", "accommodates",
			"bathrooms", "bedrooms", "price", "review_scores_rating", "availability_365" };

	public String[] toSeq() {
		String[] seq = new String[11];
		seq[0] = (String.valueOf(id));
		seq[1] = (String.valueOf(superHost ? "t" : "f"));
		seq[2] = (neighborhood);
		seq[3] = (zipcode);
		seq[4] = (propType);
		seq[5] = (String.valueOf(accommodates));
		seq[6] = bathrooms == null ? "" : (String.valueOf(bathrooms));
		seq[7] = bedrooms == null ? "" : (String.valueOf(bedrooms));
		seq[8] = (String.valueOf(dayPrice));
		seq[9] = reviewRating == null ? "" : (String.valueOf(reviewRating));
		seq[10] = (String.valueOf(avail365));
		return seq;

	}

	public int getId() {
		return id;
	}

	public boolean isSuperHost() {
		return superHost;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public String getZipcode() {
		return zipcode;
	}

	public String getPropType() {
		return propType;
	}

	public Double getAccommodates() {
		return accommodates;
	}

	public Double getBathrooms() {
		return bathrooms;
	}

	public Double getBedrooms() {
		return bedrooms;
	}

	public Double getDayPrice() {
		return dayPrice;
	}

	public Double getReviewRating() {
		return reviewRating;
	}

	public Double getAvail365() {
		return avail365;
	}

	public BigDecimal getRevenue() {
		return revenue;
	}
	
	public Map<String, String> getListingData() {
		return listingData;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("\n##############################\n");
		str.append("Property Id: " + getId() + "\n");
		str.append("Is Super Host: " + isSuperHost() + "\n");
		str.append("Neighborhood: " + getNeighborhood() + "\n");
		str.append("Zipcode: " + getZipcode() + "\n");
		str.append("Property Type: " + getPropType() + "\n");
		str.append("Accommodates: " +getAccommodates() + "\n");
		str.append("Bathrooms: " + (int)(getBathrooms() - id/Math.pow(10, String.valueOf(id).length())) + " \n");
		str.append("Bedrooms: " +(int)(getBedrooms() - id/Math.pow(10, String.valueOf(id).length())) + "\n");
		str.append("Day Price: " +getDayPrice() + "\n");
		str.append("Review Rating: " +getReviewRating()+ "\n");
		str.append("Revenue: " +getRevenue().intValue() + "\n");
		str.append("\n##############################\n");
		return str.toString();
	}

	@Override
	public int compareTo(Listing obj) {
		Listing that = (Listing) obj;

		if (treeType.equals("revenue")) {
			return compareRev(that);
		} 
		
		if (treeType.equals("bathrooms")) {
			return compareBath(that);
		} 
		
		if (treeType.equals("bedrooms")) {
			return compareBed(that);
		} 
		
		else {
			return 1;
		}

	}

	public int compareRev(Listing that) {
//		System.out.println( this.getRevenue() + ">" + that.getRevenue() + " " + this.getRevenue().compareTo(that.getRevenue()) );		
		return this.getRevenue().compareTo(that.getRevenue());

	}
	
	public int compareBed(Listing that) {
		if (this.getId() == that.getId()) {
			return 0;
		} else if (this.getBedrooms() > that.getBedrooms()) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public int compareBath(Listing that) {
		if (this.getId() == that.getId()) {
			return 0;
		} else if (this.getBathrooms() > that.getBathrooms()) {
			return 1;
		} else {
			return -1;
		}
	}

}
