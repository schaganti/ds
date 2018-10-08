package chags.ds.io.hash_table;

public class HashTable {

	private EntryWrapper[] entries;
	private HashGenerator hashGenerator;

	public HashTable() {
		entries = new EntryWrapper[10];
		hashGenerator = new HashGenerator() {
		};
	}

	public void put(String key, Object value) {

		int index = hash(key);
		EntryWrapper entryWrapper = new EntryWrapper(new Entry(key, value), null);
		if (entries[index] == null) {
			entries[index] = entryWrapper;
		} else {
			saveElementInChain(entries[index], entryWrapper);
		}

	}

	private void saveElementInChain(EntryWrapper parent, EntryWrapper child) {
		if (parent.next == null) {
			parent.next = child;
			return;
		}
		saveElementInChain(parent.next, child);
	}

	public Object get(String key) {
		EntryWrapper parent = findParent(entries[hash(key)], key);
		if (parent != null && parent.entry.key.equals(key)) {
			return parent.entry.value;
		} else if (parent != null) {
			return parent.next.entry.value;
		}
		return null;
	}

	public Object delete(String key) {

		int index = hash(key);

		EntryWrapper parent = findParent(entries[index], key);

		if (parent == null) {
			return null;
		}

		EntryWrapper entryToDelete = parent.next;

		if (parent.equals(entries[index])) {
			entries[index] = entryToDelete;
			return parent.entry.value;
		} else {
			Object valueToReturn = entryToDelete.entry.value;
			parent.next = entryToDelete.next;
			return valueToReturn;
		}
	}

	private EntryWrapper findParent(EntryWrapper rootEntryWrapper, String key) {

		if (rootEntryWrapper == null || rootEntryWrapper.entry.key.equals(key)) {
			return rootEntryWrapper;
		}

		while (rootEntryWrapper.next != null) {

			EntryWrapper nextEntry = rootEntryWrapper.next;

			if (nextEntry.entry.key.equals(key)) {
				return rootEntryWrapper;
			}
			rootEntryWrapper = nextEntry;
		}
		return null;
	}

	private int hash(String key) {
		return hashGenerator.hash(key, entries.length);
	}

}
