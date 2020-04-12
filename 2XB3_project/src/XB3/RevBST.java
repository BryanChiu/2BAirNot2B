package XB3;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;

import XB3.RedBlackBST.Node;



public class RevBST extends RedBlackBST<Listing, Integer> {
	public static final String treeType = "revenue"; 

	RevBST(){
		super();
	}
	
	public Queue<Listing> getRangeToDelete(Filter someFilter) {
		String rel = someFilter.getRelation();
		if (rel.equals(">")) {
			return keys(min().getRevenue(),  new BigDecimal(String.valueOf(someFilter.getValue())) );
		}
		
		if (rel.equals("<")) {
			return keys(new BigDecimal(String.valueOf(someFilter.getValue())), max().getRevenue() );
		}
		return null;
	}
	
	public Queue<Listing> keys(BigDecimal lo, BigDecimal hi) {
		if (lo == null)
			throw new IllegalArgumentException("first argument to keys() is null");
		if (hi == null)
			throw new IllegalArgumentException("second argument to keys() is null");
		Queue<Listing> queue = new LinkedList<Listing>();
		// if (isEmpty() || lo.compareTo(hi) > 0) return queue;
		keys(root, queue, lo, hi);
		return queue;
	}

	private void keys(Node x, Queue<Listing> queue, BigDecimal lo, BigDecimal hi) {
		if (x == null)
			return;
		int cmplo = lo.compareTo(x.key.getRevenue());
		int cmphi = hi.compareTo(x.key.getRevenue());
		if (cmplo < 0)
			keys(x.left, queue, lo, hi);
		if (cmplo <= 0 && cmphi >= 0)
			queue.add(x.key);
		if (cmphi > 0)
			keys(x.right, queue, lo, hi);
	}

	
}
