package XB3;

/**
 * Class that represents properties used to filter listings
 *
 * @author Michael Yohannes
 */

public class Filter {
	private String query;
	private String relation;
	private String value;

	/**
	 * Initializes filter based on three Strings
	 */
	Filter(String[] filterKeys) {
		query = filterKeys[0].toLowerCase();
		relation = filterKeys[1].toLowerCase();
		value = filterKeys[2].toLowerCase();
	}

	/**
	 * Returns the property to be filtered, as a String
	 *
	 * @return the property to be filtered, as a String
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Returns the relation to the property to be filtered, either "<" or ">"
	 *
	 * @return the relation to the property to be filtered, either "<" or ">"
	 */
	public String getRelation() {
		return relation;
	}

	/**
	 * Returns the value of the property to be filtered, as a String
	 *
	 * @return the value of the property to be filtered, as a String
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Is the Filter valid?
	 *
	 * @param str input String
	 * @return whether the input String can be parsed into a valid Filter
	 */
	public static boolean validFilter(String str) {
		String[] strArr = str.toLowerCase().split(" ");
		if (strArr.length != 3)
			return false;
		if ( !(strArr[0].equals("revenue") || strArr[0].equals("bathrooms") || strArr[0].equals("bedrooms")) )
			return false;
		if ( !(strArr[1].equals(">") || strArr[1].equals("<")) )
			return false;
		if ( Double.parseDouble(strArr[2]) < 0 )
			return false;
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		Filter that = (Filter) obj;

		return this.getValue().equals(that.getValue()) && this.getQuery().equals(that.getQuery())
				&& this.getValue().equals(that.getValue());
	}

	@Override
	public int hashCode() {

		int querySum = 0;
		for (int i = 0; i < this.getQuery().length(); i++) {
			querySum += (int) this.getQuery().charAt(i);
		}

		int relationNum = 0;

		if (this.getRelation().equals(">")) {
			relationNum = 1;
		}
		
		return querySum + relationNum + Integer.valueOf(this.getValue());

	}

}
