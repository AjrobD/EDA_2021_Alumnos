package material.exam_excercises.Ene2020;

import material.Position;
import material.tree.binarytree.BinaryTree;

import java.util.ArrayList;
import java.util.List;

public class Half<E> {
    public Iterable<Position<E>> findHalf(BinaryTree<E> tree){
        Integer altura = findHeight(tree,tree.root());
        List<Position<E>> devolver = new ArrayList<>();
        findHalfRecursive(tree, tree.root(),altura/2, altura, devolver);
        return  devolver;
    }

    private Integer findHeight(BinaryTree<E> tree,Position<E> pos){
        if(tree.isLeaf(pos)){
            return 0;
        }
        else{
            int hIzq = 0;
            int hDer = 0;
            if(tree.hasLeft(pos)){
                hIzq = findHeight(tree,tree.left(pos));
            }
            if(tree.hasRight(pos)){
                hDer = findHeight(tree,tree.right(pos));
            }
            if(hIzq>=hDer){
                return hIzq+1;
            }
            return hDer+1;
        }
    }

    private void findHalfRecursive(BinaryTree<E> tree, Position<E> pos, int altura, int i, List<Position<E>> devolver){
        if(i==altura){
            devolver.add(pos);
        }
        else{
            for(Position<E> child : tree.children(pos)){
                findHalfRecursive(tree,child,altura,i-1,devolver);
            }
        }
    }
}
