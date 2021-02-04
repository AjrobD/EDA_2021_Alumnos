package material.exam_excercises.Jun2012;

import material.Position;
import material.tree.binarysearchtree.AVLTree;
import material.tree.binarysearchtree.BinarySearchTree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class parejas {
    public static void parejas(Integer[] array, Integer m){
        BinarySearchTree<Integer> tree = new AVLTree<>();
        for(Integer in : array){
            int temp = m - in;
            for(Position<Integer> pair : tree.findAll(temp)){
                System.out.println(in+" "+pair.getElement());
            }
            tree.insert(in);
        }
    }

    public static void main(String[] args){
        Integer[] array = {1,3,12,23,1,2,2};
        parejas.parejas(array,4);
    }
}


