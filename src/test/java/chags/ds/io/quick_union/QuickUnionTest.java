package chags.ds.io.quick_union;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QuickUnionTest {

	QuickUnion quickUnion;

	@BeforeEach
	public void beforeAll() {
		quickUnion = new QuickUnion(10);
	}

	@Test
	@DisplayName("Union operation should connect two simple components without any roots")
	public void testUnion() {
		quickUnion.union(4, 3);
		quickUnion.union(3, 8);
		quickUnion.union(6, 5);
		quickUnion.union(9, 4);
		quickUnion.union(2, 1);
		assertThat(quickUnion.connected(4, 3)).isEqualTo(true);
		assertThat(quickUnion.connected(4, 3)).isEqualTo(true);
		assertThat(quickUnion.connected(3, 8)).isEqualTo(true);
		assertThat(quickUnion.connected(4, 8)).isEqualTo(true);
		assertThat(quickUnion.connected(6, 5)).isEqualTo(true);
		assertThat(quickUnion.connected(9, 4)).isEqualTo(true);
		assertThat(quickUnion.connected(2, 1)).isEqualTo(true);
		assertThat(quickUnion.connected(5, 4)).isEqualTo(false);
	}

	@Test
	@DisplayName("Union operation should connect two simple components without any roots")
	public void testWightedUnion() {

		quickUnion.union(4, 3);
		quickUnion.union(3, 8);
		quickUnion.union(6, 5);
		quickUnion.union(9, 4);
		quickUnion.union(2, 1);
		quickUnion.union(5, 0);
		quickUnion.union(7, 2);
		quickUnion.union(6, 1);
		quickUnion.union(7, 3);

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				assertThat(quickUnion.connected(i, j)).isTrue();
			}
		}
	}

}
