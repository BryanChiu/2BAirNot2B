package XB3;

import java.util.LinkedList;
import java.util.Queue;

import XB3.RedBlackBST.Node;

public class BathBST extends RedBlackBST<Listing, Integer> {
	public static final String treeType = "bathrooms";
	
	BathBST(){
		super();
	}
	
	public Queue<Listing> getRangeToDelete(Filter someFilter) {
		String rel = someFilter.getRelation();
		if (rel.equals(">")) {
			return keys(min().getBathrooms(), Double.parseDouble(someFilter.getValue()) );
		}
		
		if (rel.equals("<")) {
			return keys(Double.parseDouble(someFilter.getValue()), max().getBathrooms() );
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
		int cmplo = lo.compareTo(x.key.getBathrooms());
		int cmphi = hi.compareTo(x.key.getBathrooms());
		if (cmplo < 0)
			keys(x.left, queue, lo, hi);
		if (cmplo <= 0 && cmphi >= 0)
			queue.add(x.key);
		if (cmphi > 0)
			keys(x.right, queue, lo, hi);
	}


}

