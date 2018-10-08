package chags.ds.io.hash_table;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Hash table test cases")
@TestInstance(Lifecycle.PER_CLASS)
public class HashTableTest {

	HashTable hashTable;

	@BeforeEach
	public void before() {
		hashTable = new HashTable();
	}

	@ParameterizedTest(name = "Store key={0} and val={1}")
	@DisplayName("Hashtable should add and retrieve elements by key")
	@CsvSource({ "key,val", "loooooooooooooooongkey, val" })
	public void testAddGetElement(String key, String val) {
		hashTable.put(key, val);
		assertThat(hashTable.get(key)).isEqualTo(val);
	}

	@Test
	@DisplayName("Hash table should store multiple elements resulting in the same hash - hash collision")
	public void testAddGetElementShouldResolveCacheCollision() {

		hashTable.put("key1", "val1");
		hashTable.put("key2", "val2");
		hashTable.put("key3", "val3");
		hashTable.put("key11", "val11");
		assertThat(hashTable.get("key1")).isEqualTo("val1");
		assertThat(hashTable.get("key2")).isEqualTo("val2");
		assertThat(hashTable.get("key11")).isEqualTo("val11");
	}

	@Nested
	@DisplayName("Stack Delete Usecases")
	class DeleteUseCase {

		@BeforeEach
		public void before() {
			hashTable.put("key1", "val1");
			hashTable.put("key2", "val2");
			hashTable.put("key3", "val3");
			hashTable.put("key4", "val4");
		}

		@Test
		@DisplayName("Should delete middle elements from hash table")
		public void deleteMiddleElement() {

			Object deletedElement = hashTable.delete("key3");
			assertThat(deletedElement).isEqualTo("val3");
			assertThat(hashTable.get("key3")).isNull();
			assertThat(hashTable.get("key1")).isNotNull();
			assertThat(hashTable.get("key2")).isNotNull();
			assertThat(hashTable.get("key4")).isNotNull();
		}

		@Test
		@DisplayName("Should delete first element from hash table")
		public void deleteFirstElement() {

			Object deletedElement = hashTable.delete("key1");
			assertThat(deletedElement).isEqualTo("val1");
			assertThat(hashTable.get("key1")).isNull();
			assertThat(hashTable.get("key2")).isNotNull();
			assertThat(hashTable.get("key3")).isNotNull();
			assertThat(hashTable.get("key4")).isNotNull();
		}

		@Test
		@DisplayName("Should delete first element from hash table")
		public void deleteLastElement() {

			Object deletedElement = hashTable.delete("key4");
			assertThat(deletedElement).isEqualTo("val4");
			assertThat(hashTable.get("key4")).isNull();
			assertThat(hashTable.get("key1")).isNotNull();
			assertThat(hashTable.get("key2")).isNotNull();
			assertThat(hashTable.get("key3")).isNotNull();
		}

	}

}
