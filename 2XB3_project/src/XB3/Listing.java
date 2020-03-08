package XB3;

import java.util.Map;

public class Listing {

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
	private Double revenue;

	Listing(Map<String, String> listingData) {
		id = Integer.parseInt(listingData.get("id"));
		superHost = listingData.get("host_is_superhost").contains("t") ? true : false;
		neighborhood = listingData.get("neighbourhood_cleansed");
		zipcode = listingData.get("zipcode");
		propType = listingData.get("property_type");
		accommodates = validNumber(listingData.get("accommodates"));
		bathrooms = validNumber(listingData.get("bathrooms")); 
		bedrooms = validNumber(listingData.get("bedrooms")); 
//		dayPrice = validNumber(listingData.get("price").substring(1)); 
		dayPrice = validNumber(listingData.get("price")); 
		reviewRating = validNumber(listingData.get("review_scores_rating")); 
		avail365 = validNumber(listingData.get("availability_365")); 
		revenue = avail365 == null || dayPrice == null ? null : (365 - avail365) * dayPrice;

	}

	public Double validNumber(String val) {
		if (val.equals("")) {
			return null;
		} else {
			return Double.parseDouble(val);
		}

	}
	
	String[] header = {
			"id", "host_is_superhost", "neighbourhood_cleansed","zipcode", "property_type",
			"accommodates", "bathrooms", "bedrooms", "price", "review_scores_rating",
			"availability_365"
			}; 
	
	public String[] toSeq() {
		String[] seq = new String[11];
		seq[0] = ( String.valueOf(id) );
		seq[1] = ( String.valueOf( superHost ? "t" : "f" ) );
		seq[2] = ( neighborhood );
		seq[3] = ( zipcode );
		seq[4] = ( propType );
		seq[5] = ( String.valueOf(accommodates) );
		seq[6] = ( String.valueOf(bathrooms) );
		seq[7] = ( String.valueOf(bedrooms) );		
		seq[8] = ( String.valueOf(dayPrice) );
		seq[9] = ( String.valueOf(reviewRating) );
		seq[10] = ( String.valueOf(avail365) );
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

	public double getAccommodates() {
		return accommodates;
	}

	public double getBathrooms() {
		return bathrooms;
	}

	public double getBedrooms() {
		return bedrooms;
	}

	public double getDayPrice() {
		return dayPrice;
	}

	public double getReviewRating() {
		return reviewRating;
	}
	
	public double getAvail365() {
		return avail365;
	}

	public double getRevenue() {
		return revenue;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(getId() + " ");
		str.append(isSuperHost() + " ");
		str.append(getNeighborhood() + " ");
		str.append(getZipcode() + " ");
		str.append(getPropType() + " ");
		str.append(getAccommodates() + " ");
		str.append(getBathrooms() + " ");
		str.append(getBedrooms() + " ");
		str.append(getDayPrice() + " ");
		str.append(getReviewRating() + " ");
		str.append(getRevenue() + " ");
		return str.toString();
	}

}
