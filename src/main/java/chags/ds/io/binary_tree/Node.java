package chags.ds.io.binary_tree;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter(value=AccessLevel.PACKAGE)
@Getter
@ToString(doNotUseGetters=true, exclude={"left", "right"})
public class Node {

	int value = -1;
	Node left;
	Node right;
}
