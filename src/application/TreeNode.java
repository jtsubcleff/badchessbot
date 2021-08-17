package application;

import java.util.HashSet;

public class TreeNode {
	Piece[][] board;
	HashSet<TreeNode> children = new HashSet<>();
	TreeNode parent;
	int score = 9945;
	boolean sameColor;
	boolean isMate;
	Move path;
	public TreeNode(Piece[][] b, TreeNode p, boolean sc, Move pa) {
		board = b;
		parent = p;
		sameColor = sc;
		path = pa;
	}
	
}
