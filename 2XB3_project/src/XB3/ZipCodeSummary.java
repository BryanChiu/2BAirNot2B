package XB3;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class ZipCodeSummary implements Comparable {
	private String zipcode;
	DescriptiveStatistics zipcodeData;

	ZipCodeSummary(String zipcode, DescriptiveStatistics zipcodeData) {
		this.zipcode = zipcode;
		this.zipcodeData = zipcodeData;
	}

	@Override
	public int compareTo(Object obj) {
		ZipCodeSummary that = (ZipCodeSummary) obj;
		if (this.zipcodeData.getMean() > that.zipcodeData.getMean()) {
			return 1;
		} else if (this.zipcodeData.getMean() < that.zipcodeData.getMean()) {
			return -1;
		}
		return 0;
	}

	public String getZipcode() {
		return zipcode;
	}

	public DescriptiveStatistics getZipcodeData() {
		return zipcodeData;
	}

}
