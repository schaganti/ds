package chags.ds.io.quick_union;

import java.util.stream.IntStream;

public class QuickUnion {

	int[] id;
	int[] sz;

	public QuickUnion(int count) {
		id = new int[count];
		sz = new int[count];
		IntStream.range(0, count).forEach(i -> id[i] = i);
		IntStream.range(0, count).forEach(i -> sz[i] = 1);
	}

	public void union(int p, int q) {
		int pRoot = root(p);
		int qRoot = root(q);

		if (sz[pRoot] > sz[qRoot]) {
			id[pRoot] = qRoot;
			sz[qRoot] = sz[pRoot] + sz[qRoot];
		} else {
			sz[pRoot] = sz[pRoot] + sz[qRoot];
			id[qRoot] = pRoot;
		}
	}

	private int root(int i) {

		while (id[i] != i) {
			//the following line will compress the tree
			id[i] = id[id[i]];
			i = id[i];
		}
		return i;
	}

	public boolean connected(int p, int q) {
		return root(p) == root(q);
	}
}
