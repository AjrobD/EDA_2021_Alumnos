package material.tree.iterators;

import material.Position;
import material.tree.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;
import java.util.function.Predicate;

/**
 * Generic preorder iterator for trees.
 *
 * @param <E>
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro, JD. Quintana
 */
public class PreorderIterator<E> implements Iterator<Position<E>> {

    private final Stack<Position<E>> nodeStack;
    private final Tree<E> tree;

    public PreorderIterator(Tree<E> tree){
    nodeStack = new Stack<>();
        this.tree = tree;
        if (!tree.isEmpty())
                nodeStack.add(tree.root());
    }

    public PreorderIterator(Tree<E> tree, Position<E> start) {
        nodeStack = new Stack<>();
        this.tree = tree;
        nodeStack.add(start);
    }

    public PreorderIterator(Tree<E> tree, Position<E> start, Predicate<Position<E>> predicate) {
        throw new RuntimeException("Not yet implemented");
    }


    @Override
    public boolean hasNext() {
        return !nodeStack.empty();
    }

    @Override
    public Position<E> next() {
        Position<E> aux = nodeStack.pop();
        ArrayList<Position<E>> children = (ArrayList<Position<E>>) tree.children(aux);
        Collections.reverse(children);
        for (Position<E> node : children) {
            nodeStack.push(node);
        }
        return aux;
    }

    private void lookForward() {
        throw new RuntimeException("Not yet implemented");
    }

    private void pushChildrenInReverseOrder(Tree<E> tree, Position<E> pop) {
        throw new RuntimeException("Not yet implemented");
    }

}
