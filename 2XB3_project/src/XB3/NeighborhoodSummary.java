package XB3;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class NeighborhoodSummary implements Comparable {
	private String neighborhood;
	DescriptiveStatistics neighborhoodData;

	NeighborhoodSummary(String neighborhood, DescriptiveStatistics neighborhoodData) {
		this.neighborhood = neighborhood;
		this.neighborhoodData = neighborhoodData;
	}

	@Override
	public int compareTo(Object obj) {
		NeighborhoodSummary that = (NeighborhoodSummary) obj;
		if (this.neighborhoodData.getMean() > that.neighborhoodData.getMean()) {
			return 1;
		} else if (this.neighborhoodData.getMean() < that.neighborhoodData.getMean()) {
			return -1;
		}
		return 0;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public DescriptiveStatistics getNeighborhoodData() {
		return neighborhoodData;
	}
	
	

}
