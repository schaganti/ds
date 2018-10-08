package chags.ds.io.avl_tree;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter(value = AccessLevel.PACKAGE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Node {

	int value = -1;
	Node parent;
	Node left;
	Node right;

	@Override
	public String toString() {
		return String.format("Node(value=%d left=%d right=%d, parent=%d)", value, valueOf(left), valueOf(right),
				valueOf(parent));
	}

	public int leftHeight() {
		return maxHeight(left);
	}

	public int rightHeight() {
		return maxHeight(right);
	}

	boolean isBalanced() {
		int diff = rightHeight() - leftHeight();
		return diff <= 1 && diff >= -1;
	}

	public int valueOf(Node node) {
		if (node != null) {
			return node.value;
		}
		return -1;
	}

	private int maxHeight(Node node) {
		if (node == null) {
			return 0;
		}
		return 1 + Integer.max(maxHeight(node.left), maxHeight(node.right));
	}

}
