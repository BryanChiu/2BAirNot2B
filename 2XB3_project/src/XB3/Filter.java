package XB3;

public class Filter {
	private String query;
	private String relation;
	private String value;

	Filter(String[] filterKeys) {
		query = filterKeys[0].toLowerCase();
		relation = filterKeys[1].toLowerCase();
		value = filterKeys[2].toLowerCase();
	}

	public String getQuery() {
		return query;
	}

	public String getRelation() {
		return relation;
	}

	public String getValue() {
		return value;
	}
	
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
