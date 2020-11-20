package material.tree.binarytree;

import material.Position;
import material.tree.iterators.PreorderBinaryTreeIterator;

import java.util.Stack;

public class BinaryTreeUtils<E> {
	
	private BinaryTree<E> tree;
	
	public BinaryTreeUtils(BinaryTree<E> tree) {
		this.tree = tree;
	}

	public BinaryTree<E> getTree() {
		return tree;
	}

	/**
	  * Given a tree the method returns a new tree where all left children 
	  * become right children and vice versa
	*/
	public BinaryTree<E> mirror() {
		BinaryTree<E> newTree = new LinkedBinaryTree<E>();
		Stack<Position<E>> nodeStack = new Stack<>();
		Position<E> root = newTree.addRoot(this.tree.root().getElement());
		nodeStack.add(root);
		PreorderBinaryTreeIterator<E> iterator = new PreorderBinaryTreeIterator<E>(this.tree);
		while(iterator.hasNext()&&!nodeStack.isEmpty()){
			Position <E> next = iterator.next();
			Position <E> pos = nodeStack.pop();
			if (this.tree.hasRight(next)) {
				E rightElem = this.tree.right(next).getElement();
				Position<E> posLeft = newTree.insertLeft(pos,rightElem);
				nodeStack.add(posLeft);
			}
			if(tree.hasLeft(next)){
				E leftElem = this.tree.left(next).getElement();
				Position<E> posRight =newTree.insertRight(pos,leftElem);
				nodeStack.add(posRight);
			}


		}
		return newTree;
	}
	/** 
	  * Determines whether the element e is the tree or not
	*/
	public boolean contains (E e) {
		Boolean found = false;
		PreorderBinaryTreeIterator<E> iterator = new PreorderBinaryTreeIterator<E>(this.tree);
		while(iterator.hasNext()&&!found){
			found = iterator.next().getElement()==e;
		}
		return found;
	}
	/** 
	  * Determines the level of a node in the tree (root is located at level 1)
	*/
	public int level(Position<E> p) {
		return levelUtil(this.tree.root(),p,1);
	}
	public int levelUtil(Position<E> node, Position<E> p, int level){
		if (node == null)
			return 0;

		if (node == p)
			return level;

		int downlevel=0;
		if(this.tree.hasLeft(node)){
			downlevel = levelUtil(this.tree.left(node), p, level + 1);
		}
		if (downlevel != 0)
			return downlevel;

		if(this.tree.hasRight(node)) {
			downlevel = levelUtil(this.tree.right(node), p, level + 1);
		}
		return downlevel;
	}

}
