package material.test.practica3;

import material.Position;
import material.tree.binarytree.ArrayBinaryTree;
import material.tree.binarytree.BinaryTree;
import material.tree.iterators.InorderBinaryTreeIterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinaryTreeTest {
    private BinaryTree<Integer> tree;
    private Position<Integer>[] pos;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        tree = new ArrayBinaryTree<>();
        pos = new Position[16];
        pos[1] = tree.addRoot(1);
        pos[2] = tree.insertLeft(pos[1],2 );
        pos[3] = tree.insertRight(pos[1],3 );
        pos[5] = tree.insertRight(pos[2],5 );
        pos[6] = tree.insertLeft(pos[3],6);
        pos[7] = tree.insertRight(pos[3],7);
        pos[12] = tree.insertLeft(pos[6],12 );
        pos[13] = tree.insertRight(pos[6],13 );
    }

    @org.junit.jupiter.api.Test
    void subTree(){
        BinaryTree<Integer> subtree = tree.subTree(pos[3]);
        InorderBinaryTreeIterator<Integer> iteratorNew = new InorderBinaryTreeIterator<>(subtree);
        Integer i = 1;
        while(iteratorNew.hasNext()){
            switch (i){
                case 1:
                    assertEquals(iteratorNew.next().getElement(),pos[12].getElement());
                    break;
                case 2:
                    assertEquals(iteratorNew.next().getElement(),pos[6].getElement());
                    break;
                case 3:
                    assertEquals(iteratorNew.next().getElement(),pos[13].getElement());
                    break;
                case 4:
                    assertEquals(iteratorNew.next().getElement(),pos[3].getElement());
                    break;
                case 5:
                    assertEquals(iteratorNew.next().getElement(),pos[7].getElement());
                    break;
            }
            i++;
        }
    }

}
