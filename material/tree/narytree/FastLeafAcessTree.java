package material.tree.narytree;

import material.Position;
import material.tree.iterators.BFSIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FastLeafAcessTree<E>{
    LinkedTree<E> tree;
    Set<Position<E>> leaves;

    public void insert(Position<E> parent, E element){
        Position<E> pos = tree.add(element,parent);
        leaves.add(pos);
        leaves.remove(parent);
    }

}
