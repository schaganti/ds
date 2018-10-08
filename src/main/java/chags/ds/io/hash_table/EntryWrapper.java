package chags.ds.io.hash_table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EntryWrapper {

	Entry entry;
	
	EntryWrapper next;
}
