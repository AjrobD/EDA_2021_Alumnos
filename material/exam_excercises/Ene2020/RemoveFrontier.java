package material.exam_excercises.Ene2020;

import material.Position;
import material.tree.narytree.NAryTree;

public class RemoveFrontier<E> {
    public void removeFrontier(NAryTree<E> tree){
        Position<E> parent = tree.root();
        if(tree.isLeaf(parent)){
            tree.remove(parent);
        }
        else {
            removeFrontierRecursive(tree, parent);
        }
    }
    public void removeFrontierRecursive(NAryTree<E> tree, Position<E> parent){
        for(Position<E> pos : tree.children(parent)){
            if(tree.isLeaf(pos)){
                tree.remove(pos);
            }
            else{
                removeFrontierRecursive(tree,pos);
            }
        }
    }
}
