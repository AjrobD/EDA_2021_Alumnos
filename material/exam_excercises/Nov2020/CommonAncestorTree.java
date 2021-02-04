package material.exam_excercises.Nov2020;

import material.Position;
import material.tree.narytree.LinkedTree;
import material.tree.narytree.NAryTree;

import java.util.HashSet;
import java.util.Set;

public class CommonAncestorTree<E> {
    NAryTree<E> tree;

    public CommonAncestorTree(NAryTree<E> tree) {
        this.tree=tree;
    }

    public NAryTree<E> createCommonTreeAncestor(Position<E> p1, Position<E> p2) {
        Position<E> ancestor = findAncestor(p1,p2);
        NAryTree<E> newTree = new LinkedTree<>();
        Position<E> parent = newTree.addRoot(ancestor.getElement());
        newTree(ancestor,parent,newTree);
        return newTree;
    }

    private void newTree(Position<E> ancestor, Position<E> parent, NAryTree<E> newTree) {
        for(Position<E> child : tree.children(ancestor)){
            Position<E> aux = newTree.add(child.getElement(),parent);
            newTree(child,aux,newTree);
        }
    }

    private Position<E> findAncestor(Position<E> p1, Position<E> p2) {
        Set<Position<E>> ancestorsP1 = new HashSet<>();
        if(!tree.isRoot(p1)){
            Position<E> parent = p1;
            while(!tree.isRoot(parent)){
                ancestorsP1.add(parent);
                parent = tree.parent(parent);
            }
            ancestorsP1.add(parent);
        }
        else{
            return p1;
        }

        if(!tree.isRoot(p2)){
            Position<E> parent = p2;
            while(!tree.isRoot(parent)) {
                if (ancestorsP1.contains(parent)) {
                    return parent;
                }
                parent = tree.parent(parent);
            }
            return tree.root();
        }
        else{
            return p2;
        }
    }
}
