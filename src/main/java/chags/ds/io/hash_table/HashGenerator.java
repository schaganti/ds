package chags.ds.io.hash_table;

public interface HashGenerator {

	public default int hash(String key, int maxIndex) {
		return key.length() % maxIndex;
	}
}
