package material.tree.iterators;

import material.Position;
import material.tree.binarytree.BinaryTree;

import java.util.Iterator;
import java.util.Stack;
import java.util.function.Predicate;

public class PreorderBinaryTreeIterator<E> implements Iterator<Position<E>>{
    private final Stack<Position<E>> nodeStack;
    private final BinaryTree<E> tree;

    public PreorderBinaryTreeIterator(BinaryTree<E> tree){
        nodeStack = new Stack<>();
        this.tree = tree;
        if (!tree.isEmpty())
            nodeStack.add(tree.root());
    }

    public PreorderBinaryTreeIterator(BinaryTree<E> tree, Position<E> start) {
        nodeStack = new Stack<>();
        this.tree = tree;
        nodeStack.add(start);
    }

    public PreorderBinaryTreeIterator(BinaryTree<E> tree, Position<E> start, Predicate<Position<E>> predicate) {
        throw new RuntimeException("Not yet implemented");
    }


    @Override
    public boolean hasNext() {
        return !nodeStack.empty();
    }

    @Override
    public Position<E> next() {
        Position<E> aux = nodeStack.pop();
        if(tree.hasRight(aux)){
            nodeStack.add(tree.right(aux));
        }
        if(tree.hasLeft(aux)){
            nodeStack.add(tree.left(aux));
        }
        return aux;
    }

    private void lookForward() {
        throw new RuntimeException("Not yet implemented");
    }

    private void pushChildrenInReverseOrder(BinaryTree<E> tree, Position<E> pop) {
        throw new RuntimeException("Not yet implemented");
    }

}
