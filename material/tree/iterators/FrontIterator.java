package material.tree.iterators;

import material.Position;
import material.tree.Tree;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

/**
 * Front iteartor for trees.
 *
 * @param <T>
 * @author jvelez, JD. Quintana
 */
public class FrontIterator<T> implements Iterator<Position<T>> {

    private final Queue<Position<T>> nodeQueue;
    private final Tree<T> tree;

    public FrontIterator(Tree<T> tree) {
        nodeQueue = new ArrayDeque<>();
        this.tree = tree;
        if (!tree.isEmpty())
            nodeQueue.add(tree.root());
    }


    public FrontIterator(Tree<T> tree, Position<T> node) {
        nodeQueue = new ArrayDeque<>();
        this.tree = tree;
        nodeQueue.add(node);
    }

    @Override
    public boolean hasNext() {
        return (nodeQueue.size() != 0);
    }

    /**
     * This method visits the nodes of a tree by following a breath-first search
     */
    @Override
    public Position<T> next() {
        Position<T> aux = nodeQueue.remove();
        for (Position<T> node : tree.children(aux)) {
            nodeQueue.add(node);
        }
        if(tree.isLeaf(aux)){
            return aux;
        }
        else{
            return next();
        }
    }

    @Override
    public void remove() {
        throw new RuntimeException("Not yet implemented");
    }
}
