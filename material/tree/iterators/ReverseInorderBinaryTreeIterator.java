package material.tree.iterators;

import material.Position;
import material.tree.binarytree.BinaryTree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class ReverseInorderBinaryTreeIterator<T> implements Iterator<Position<T>> {
    private Deque<Position<T>> nodeStack = new LinkedList<>();
    private final BinaryTree<T> tree;

    public ReverseInorderBinaryTreeIterator(BinaryTree<T> tree) {
        this.tree = tree;
        if (!tree.isEmpty())
            goToLastInRight(tree.root());
    }


    public ReverseInorderBinaryTreeIterator(BinaryTree<T> tree, Position<T> node) {
        this.tree = tree;
        goToLastInRight(node);
    }

    private void goToLastInRight(Position<T> node) {
        nodeStack.addFirst(node);

        while (tree.hasRight(node)) {
            node = tree.right(node);
            nodeStack.addFirst(node);
        }
    }

    @Override
    public boolean hasNext() {
        return (!nodeStack.isEmpty());
    }

    /**
     * This method visits the nodes of a tree by following a breath-first search
     */
    @Override
    public Position<T> next() {
        Position<T> aux = nodeStack.removeFirst();
        if (tree.hasLeft(aux)) {
            goToLastInRight(tree.left(aux));
        }

        return aux;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not implemented.");
    }
}
