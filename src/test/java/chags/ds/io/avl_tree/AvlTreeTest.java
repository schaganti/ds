package chags.ds.io.avl_tree;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;

import org.assertj.core.api.Condition;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Unit test for simple App.
 */
@DisplayName("Avl Tree TestCases")
public class AvlTreeTest {

	AvlTree avlTree;

	@BeforeEach
	public void beforeEach() {
		avlTree = new AvlTree();
	}

	@Nested
	@DisplayName("Test add tree contract")
	public class AddTreeContract {

		@Test
		@DisplayName("Should be able to add a node")
		public void addNode() {
			avlTree.add(1);
			assertThat(avlTree.find(1)).isNotNull();
		}

		@Test
		@DisplayName("Should be able to add lef node")
		public void addNodeShouldAddToLeft() {
			avlTree.add(1);
			avlTree.add(-1);
			avlTree.add(2);
			avlTree.add(-2);

			assertThat(avlTree.find(1)).isNotNull();
			assertThat(avlTree.find(-1)).isNotNull();
			assertThat(avlTree.find(-2)).isNotNull();

			// binaryTree.processNodes(System.out::println, BinaryTree.PREFIX);
		}

		@Test
		@DisplayName("Add and verify binary tree contract")
		public void testBinaryTreeContract() {

			IntStream.of(1, 2, 3, 4, 5, -1, -20, 30, 7, 1, 15, -20, 3, 33).forEach(avlTree::add);
			avlTree.balanceTree(avlTree.rootNode);
			IntStream.of(1, 2, 3, 4, 5, -1, -20, 30, 7, 1, 15, -20, 3, 33).forEach(this::assertTreeContract);
		}

		public void assertTreeContract(int val) {
			Node node = avlTree.find(val);

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

	@Nested
	@DisplayName("Test Delete Operations")
	public class DeleteTreeUseCases {
		@Test
		@DisplayName("Should delete leaf node")
		public void testDeleteLeafNode() {
			IntStream.of(1, 2, 3, -1).forEach(avlTree::add);
			avlTree.delete(3);
			assertThat(avlTree.find(3)).isNull();
			avlTree.delete(-1);
			assertThat(avlTree.find(-1)).isNull();
			IntStream.of(1, 2).forEach(v -> {
				assertThat(avlTree.find(v)).isNotNull();
			});
		}

		@Test
		@DisplayName("Should delete node with no right child and only left child")
		public void testDeleteNoRightChild() {

			avlTree = new AvlTree();

			IntStream.of(4, 2, 1, 3, 8, 6, 5, 7).forEach(avlTree::add);

			avlTree.delete(8);

			IntStream.of(4, 2, 1, 3, 6, 5, 7).forEach(v -> {
				assertThat(avlTree.find(v)).isNotNull().as("Expecting not null for {}", v);
				// assertThat(avlTree.find(8)).isNull();
			});
		}

		@Test
		@DisplayName("Should delete node with right child that has another right child")
		public void testDeleteWithRightChildHavingRightChild() {
			IntStream.of(4, 2, 1, 3, 6, 5, 7, 8).forEach(avlTree::add);
			avlTree.delete(6);
			assertThat(avlTree.find(4).right.value).isEqualTo(7);
			assertThat(avlTree.find(7).left.value).isEqualTo(5);
		}

		@Test
		@DisplayName("Should delete node with right child having a left child")
		public void testDeleteWithRightChildHavingLeftChild() {
			IntStream.of(4, 2, 1, 3, 6, 5, 8, 7).forEach(avlTree::add);
			avlTree.delete(6);
			assertThat(avlTree.find(4).right.value).isEqualTo(7);
			assertThat(avlTree.find(7).left.value).isEqualTo(5);
		}
	}

	@Nested
	@DisplayName("Avl tree balancing contract test")
	@TestInstance(Lifecycle.PER_CLASS)
	public class AvlTreeBalancingContract {

		AvlTree avlTree;

		@BeforeAll
		public void before() {
			avlTree = new AvlTree();
			IntStream.of(1, 2, -1, 3, -3).forEach(avlTree::add);
		}

		@DisplayName("Should return tree hieght from current node")
		@ParameterizedTest
		@CsvSource(value = { "1,2,2", "2,0,1", "3,0,0", "-1,1,0", "-3,0,0" })
		@Ignore
		public void shouldReturnTreeHeight(int nodeValue, int leftHeight, int rightHeight) {
			Node node = avlTree.find(nodeValue);
			assertThat(node).isNotNull();
			assertThat(node.leftHeight()).isEqualTo(leftHeight);
			assertThat(node.rightHeight()).isEqualTo(rightHeight);
		}

		@Test
		@DisplayName("Test right rotation after insertion")
		public void testRightRotation() {
			AvlTree avlTree = new AvlTree();
			IntStream.of(4, 2, 1, 3).forEach(avlTree::add);
			assertThat(avlTree.rootNode.value).isEqualTo(2);
			assertThat(avlTree.rootNode.left.value).isEqualTo(1);
			assertThat(avlTree.rootNode.right.value).isEqualTo(4);
		}

		@Test
		@DisplayName("Test left rotation after insertion")
		public void testLeftRotation() {

			AvlTree avlTree = new AvlTree();

			IntStream.of(1, 3, 2, 4).forEach(avlTree::add);
			assertThat(avlTree.rootNode.value).isEqualTo(3);
			assertThat(avlTree.rootNode.left.value).isEqualTo(1);
			assertThat(avlTree.rootNode.right.value).isEqualTo(4);
		}

		@DisplayName("Test right rotation after deletion")
		@Test
		public void testRightRotationDeletion() {

			AvlTree avlTree = new AvlTree();
			IntStream.of(5, 4, 7, 6, 8).forEach(avlTree::add);
			avlTree.delete(4);
			assertThat(avlTree.rootNode.value).isEqualTo(7);
			assertThat(avlTree.rootNode.left.value).isEqualTo(5);
			assertThat(avlTree.rootNode.left.right.value).isEqualTo(6);
			assertThat(avlTree.rootNode.right.value).isEqualTo(8);
		}

	}

}
