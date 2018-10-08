package chags.ds.io.binary_tree;

import java.util.function.Consumer;

import javax.xml.stream.events.NotationDeclaration;

public class BinaryTree {

	Node rootNode;

	public static final String INFIX = "INFIX";

	public static final String PREFIX = "PREFIX";

	public static final String POSTFIX = "POSTFIX";

	public void add(int value) {
		addInternal(value, rootNode);
	}

	public Node addInternal(int newValue, Node node) {

		if (node == null) {
			return createNewNode(newValue);
		}
		if (newValue < node.value) {
			node.left = addInternal(newValue, node.left);
		} else {
			node.right = addInternal(newValue, node.right);
		}
		return node;
	}

	private Node createNewNode(int value) {
		Node node = new Node();
		node.value = value;
		if (rootNode == null) {
			rootNode = node;
		}
		return node;
	}

	public Node find(int val) {
		return findInternal(val, rootNode);
	}

	private Node findInternal(int value, Node node) {

		if ((node != null && node.value == value) || node == null) {
			return node;
		}
		return value < node.value ? findInternal(value, node.left) : findInternal(value, node.right);
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
		//Node to delete has right child and the right child has left child
		else if (rightChild != null && rightChild.left != null) {
			parent.right = rightChild.left;
			rightChild.left.left = nodeToDelete.left;
		}
		
	}

	private boolean isLeafNode(Node nodeToDelete) {
		return nodeToDelete.left == null && nodeToDelete.right == null;
	}
}
