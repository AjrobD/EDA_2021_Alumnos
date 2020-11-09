package material.tree.iterators;

import material.Position;
import material.tree.Tree;

import java.util.*;
import java.util.function.Predicate;

/**
 * Generic preorder iterator for trees.
 *
 * @param <E>
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro, JD. Quintana
 */
public class PostorderIterator<E> implements Iterator<Position<E>> {

    private class treePosDataPostOrder<E> {
        Position<E> treePos;
        boolean visitedAllBranches;

        treePosDataPostOrder(Position<E> treePos, Boolean visitedAllBranches) {
            this.treePos = treePos;
            this.visitedAllBranches = visitedAllBranches;
        }
    }

    private final Stack<treePosDataPostOrder<E>> nodeStack;
    private final Tree<E> tree;


    public PostorderIterator(Tree<E> tree){
        nodeStack = new Stack<treePosDataPostOrder<E>>();
        this.tree = tree;
        nodeStack.add(new treePosDataPostOrder<E>(tree.root(),false));
    }

    public PostorderIterator(Tree<E> tree, Position<E> start) {
        nodeStack = new Stack<>();
        this.tree = tree;
        nodeStack.add(new treePosDataPostOrder<E>(start,false));
    }

    public PostorderIterator(Tree<E> tree, Position<E> start, Predicate<Position<E>> predicate) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public boolean hasNext() {
        return !nodeStack.isEmpty();
    }

    @Override
    public Position<E> next() {
        if(!hasNext()) {
            throw new NoSuchElementException("No more nodes remain to iterate");
        }
        while(hasNext()) {
            treePosDataPostOrder<E> treePosData = nodeStack.peek();
            Position<E> posNode = treePosData.treePos;
            if (!treePosData.visitedAllBranches) {
                ArrayList<Position<E>> children = (ArrayList<Position<E>>) tree.children(posNode);
                Collections.reverse(children);
                for (Position<E> node : children) {
                    nodeStack.push(new treePosDataPostOrder<>(node, false));
                }
                treePosData.visitedAllBranches = true;
            }
            else{
                nodeStack.pop();
                return posNode;
            }
        }
        return null;
    }

    @Override
    public void remove(){
        throw new RuntimeException("Not yet implemented");
    }

}
