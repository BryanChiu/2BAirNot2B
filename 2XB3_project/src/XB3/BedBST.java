package XB3;

import java.util.LinkedList;
import java.util.Queue;

public class BedBST extends RedBlackBST<Listing, Integer> {
	public static final String treeType = "bedrooms";
	
	BedBST(){
		super();
	}
	
	public Queue<Listing> getRangeToDelete(Filter someFilter) {
		String rel = someFilter.getRelation();
		if (rel.equals(">")) {
			return keys(min().getBedrooms(), Double.parseDouble(someFilter.getValue()) );
		}
		
		if (rel.equals("<")) {
			return keys(Double.parseDouble(someFilter.getValue()), max().getBedrooms() );
		}
		
		return null;
	}
	
	public Queue<Listing> keys(Double lo, Double hi) {
		if (lo == null)
			throw new IllegalArgumentException("first argument to keys() is null");
		if (hi == null)
			throw new IllegalArgumentException("second argument to keys() is null");

		Queue<Listing> queue = new LinkedList<Listing>();
		// if (isEmpty() || lo.compareTo(hi) > 0) return queue;
		keys(root, queue, lo, hi);
		return queue;
	}

	private void keys(Node x, Queue<Listing> queue, Double lo, Double hi) {
		if (x == null)
			return;
		int cmplo = lo.compareTo(x.key.getBedrooms());
		int cmphi = hi.compareTo(x.key.getBedrooms());
		if (cmplo < 0)
			keys(x.left, queue, lo, hi);
		if (cmplo <= 0 && cmphi >= 0)
			queue.add(x.key);
		if (cmphi > 0)
			keys(x.right, queue, lo, hi);
	}


}
