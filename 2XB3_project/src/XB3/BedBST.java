package XB3;

import java.util.LinkedList;
import java.util.Queue;

/**
 * BST for listings based on number of bedrooms
 *
 * @author Michael Yohannes
 */

public class BedBST extends RedBlackBST<Listing, Integer> {
	public static final String treeType = "bedrooms";
	
	/**
	 * Initializes an empty listings BST.
	 */
	BedBST(){
		super();
	}
	
	/**
	 * Returns all keys in the RedBlackBST before/after a certain cutoff, as a Queue
	 *
	 * @param someFilter filter
	 * @return either all keys before/after {@code someFilter} (inclusive) as a {@code Queue}, or {@code null}
	 */
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
	
	/**
	 * Returns all keys in the RedBlackBST in the given range, as a Queue
	 *
	 * @param lo minimum endpoint
	 * @param hi maximum endpoint
	 * @return all keys in the symbol table between {@code lo} (inclusive) and {@code hi} (inclusive) as a {@code Queue}
	 * @throws IllegalArgumentException if either {@code lo} or {@code hi} is {@code null}
	 */
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

	// add the keys between lo and hi in the subtree rooted at x
	// to the queue
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
