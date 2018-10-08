package chags.ds.io.binary_tree;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class BinaryTreeTest {

	BinaryTree binaryTree;

	@BeforeEach
	public void beforeEach() {
		binaryTree = new BinaryTree();
	}

	@Test
	public void addNode() {
		binaryTree.add(1);
		assertThat(binaryTree.find(1)).isNotNull();
	}

	@Test
	public void addNodeShouldAddToLeft() {
		binaryTree.add(1);
		binaryTree.add(-1);
		binaryTree.add(2);
		binaryTree.add(-2);

		assertThat(binaryTree.find(1)).isNotNull();
		assertThat(binaryTree.find(-1)).isNotNull();
		assertThat(binaryTree.find(-2)).isNotNull();

		// binaryTree.processNodes(System.out::println, BinaryTree.PREFIX);
	}

	@Test
	public void testBinaryTreeContract() {

		IntStream.of(1, 2, 3, 4, 5, -1, -20, 30, 7, 1, 15, -20, 3, 33).forEach(binaryTree::add);
		IntStream.of(1, 2, 3, 4, 5, -1, -20, 30, 7, 1, 15, -20, 3, 33).forEach(this::assertTreeContract);
		binaryTree.processNodes(System.out::println, BinaryTree.INFIX);
	}

	@Test
	public void testDeleteLeafNode() {
		IntStream.of(1, 2, 3, -1).forEach(binaryTree::add);
		binaryTree.delete(3);
		assertThat(binaryTree.find(3)).isNull();
		binaryTree.delete(-1);
		assertThat(binaryTree.find(-1)).isNull();
		IntStream.of(1, 2).forEach(v -> {
			assertThat(binaryTree.find(v)).isNotNull();
		});
	}

	@Test
	public void testDeleteNoRightChild() {
		IntStream.of(4, 2, 1, 3, 8, 6, 5, 7).forEach(binaryTree::add);
		binaryTree.delete(8);
		assertThat(binaryTree.find(4).right.value).isEqualTo(6);
	}
	
	@Test
	public void testDeleteWithRightChildHavingRightChild() {
		IntStream.of(4, 2, 1, 3, 6, 5, 7, 8).forEach(binaryTree::add);
		binaryTree.delete(6);
		assertThat(binaryTree.find(4).right.value).isEqualTo(7);
		assertThat(binaryTree.find(7).left.value).isEqualTo(5);
	}

	@Test
	public void testDeleteWithRightChildHavingLeftChild() {
		IntStream.of(4, 2, 1, 3, 6, 5, 8, 7).forEach(binaryTree::add);
		binaryTree.delete(6);
		assertThat(binaryTree.find(4).right.value).isEqualTo(7);
		assertThat(binaryTree.find(7).left.value).isEqualTo(5);
	}


	public void assertTreeContract(int val) {
		Node node = binaryTree.find(val);

		Condition<Node> condition = new Condition<>(n -> {
			assertThat(n).isNotNull();
			assertThat(n.value).isEqualTo(val);
			if (n.left != null) {
				assertThat(n.value).isGreaterThan(n.left.value);
			}
			if (n.right != null) {
				assertThat(n.value).isLessThanOrEqualTo(n.right.value);
			}
			return true;
		}, "tree contract is broken", node);

		assertThat(node).is(condition);
	}

}
