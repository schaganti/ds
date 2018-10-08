package chags.ds.io.avl_tree;

import java.util.function.Consumer;

public class AvlTree {

	Node rootNode;

	public static final String INFIX = "INFIX";

	public static final String PREFIX = "PREFIX";

	public static final String POSTFIX = "POSTFIX";

	public void add(int value) {

		if (rootNode == null) {
			rootNode = new Node();
			rootNode.value = value;
			return;
		}
		addInternal(rootNode, value);
		balanceTree(find(value));
	}

	private Node addInternal(Node parent, int val) {

		if (parent == null) {
			return new Node(val, null, null, null);
		}

		Node newNode = val < parent.value ? addInternal(parent.left, val) : addInternal(parent.right, val);

		if (newNode != null) {
			newNode.parent = parent;
			if (parent.value > newNode.value) {
				parent.left = newNode;
			} else {
				parent.right = newNode;
			}
		}
		return null;
	}

	void balanceTree(Node node) {

		while (node != null) {

			int balancingFactor = node.leftHeight() - node.rightHeight();
			
			if (balancingFactor > 1) {
				node = rightBalance(node);
			} else if (balancingFactor < -1) {
				node = leftBalance(node);
			}
			if (node.parent == null) {
				rootNode = node;
			}
			node = node.parent;
		}
	}

	private Node leftBalance(Node oldRoot) {
		Node newRoot = oldRoot.right;
		oldRoot.right = newRoot.left;
		newRoot.left = oldRoot;
		adjustParentNodes(oldRoot, newRoot);
		return newRoot;
	}

	private Node rightBalance(Node oldRoot) {
		Node newRoot = oldRoot.left;
		oldRoot.left = newRoot.right;
		newRoot.right = oldRoot;
		adjustParentNodes(oldRoot, newRoot);
		return newRoot;
	}

	private void adjustParentNodes(Node oldRoot, Node newRoot) {
		newRoot.parent = oldRoot.parent;
		
		if(oldRoot.parent != null) {
			if(newRoot.value < oldRoot.parent.value) {
				oldRoot.parent.left = newRoot;
			} else {
				oldRoot.parent.right = newRoot;
			}
		}
		oldRoot.parent = newRoot;
	}

	public Node find(int val) {

		return findInternal(rootNode, val);
	}

	private Node findInternal(Node node, int val) {

		if (node == null || (node != null && node.value == val)) {
			return node;
		}
		return val < node.value ? findInternal(node.left, val) : findInternal(node.right, val);
	}

	public void processNodes(Consumer<Node> consumer, String order) {
		if (INFIX.equals(order)) {
			inOrder(rootNode, consumer);
		}
		if (POSTFIX.equals(order)) {
			postOrder(rootNode, consumer);
		}
		if (PREFIX.equals(order)) {
			preOrder(rootNode, consumer);
		}
	}

	public void print(String order) {
		this.preOrder(rootNode, System.out::println);
	}

	private void preOrder(Node node, Consumer<Node> consumer) {
		if (node == null) {
			return;
		}
		preOrder(node.left, consumer);
		preOrder(node.right, consumer);
		consumer.accept(node);
	}

	private void postOrder(Node node, Consumer<Node> consumer) {
		if (node == null) {
			return;
		}
		postOrder(node.right, consumer);
		postOrder(node.left, consumer);
		consumer.accept(node);

	}

	public void inOrder(Node node, Consumer<Node> consumer) {
		if (node == null) {
			return;
		}
		inOrder(node.left, consumer);
		consumer.accept(node);
		inOrder(node.right, consumer);
	}

	public Node delete(int i) {
		return deleteInternal(i, rootNode);
	}

	public Node deleteInternal(int value, Node node) {

		if ((node != null && node.value == value) || node == null) {
			return node;
		}

		Node nodeToDelete = value < node.value ? deleteInternal(value, node.left) : deleteInternal(value, node.right);

		if (nodeToDelete != null) {
			deleteNode(node, nodeToDelete);
		}
		return nodeToDelete;
	}

	private void deleteNode(Node parent, Node nodeToDelete) {

		Node rightChild = nodeToDelete.right;
		Node leftChild = nodeToDelete.left;

		if (isLeafNode(nodeToDelete)) {
			if (parent.left == nodeToDelete) {
				parent.left = null;
			} else if (parent.right == nodeToDelete) {
				parent.right = null;
			}
		}
		// Node to delete has left child and no right child
		else if (rightChild == null && leftChild != null) {
			if (parent.left == nodeToDelete) {
				parent.left = leftChild;
			}
			if (parent.right == nodeToDelete) {
				parent.right = leftChild;
			}
		}
		// Node to delete has right child and the right child has no left
		// child
		else if (rightChild != null && rightChild.left == null) {
			parent.right = rightChild;
			rightChild.left = leftChild;
		}
		// Node to delete has right child and the right child has left child
		else if (rightChild != null && rightChild.left != null) {
			parent.right = rightChild.left;
			rightChild.left.left = nodeToDelete.left;
		}
		balanceTree(parent);
	}

	private boolean isLeafNode(Node nodeToDelete) {
		return nodeToDelete.left == null && nodeToDelete.right == null;
	}
}
