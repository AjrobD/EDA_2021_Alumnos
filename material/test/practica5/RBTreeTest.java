package material.test.practica5;

import material.Position;
import material.tree.binarysearchtree.AVLTree;
import material.tree.binarysearchtree.RBTree;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RBTreeTest {
    private RBTree<Integer> tree;
    private Position<Integer>[] pos;

        /*
                    3
                   / \
                  2   5
                 /   /  \
                1   4    7
                         / \
                         6  8
         */

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        tree = new RBTree<>();
        pos = new Position[16];
        pos[4] = tree.insert(4);
        pos[2] = tree.insert(2);
        pos[3] = tree.insert(3);
        pos[1] = tree.insert(1);
        pos[5] = tree.insert(5);
        pos[7] = tree.insert(7);
        pos[8] = tree.insert(8);
        pos[6] = tree.insert(6);
    }
    @Test
    void first() {
        assertEquals(tree.first(),pos[1]);
    }
    @Test
    void last() {
        assertEquals(tree.last(),pos[8]);
    }
    @Test
    void predecessors1() {
        ArrayList<Position<Integer>> predecesores = (ArrayList<Position<Integer>>) tree.predecessors(pos[4]);
        assertEquals(predecesores.get(0),pos[3]);
        assertEquals(predecesores.get(1),pos[2]);
        assertEquals(predecesores.get(2),pos[1]);

    }
    @Test
    void predecessors2() {
        ArrayList<Position<Integer>> predecesores = (ArrayList<Position<Integer>>) tree.predecessors(pos[7]);
        assertEquals(predecesores.get(0),pos[6]);
        assertEquals(predecesores.get(1),pos[5]);
        assertEquals(predecesores.get(2),pos[3]);
        assertEquals(predecesores.get(3),pos[2]);
        assertEquals(predecesores.get(4),pos[1]);
        assertEquals(predecesores.get(5),pos[4]);

    }
    @Test
    void predecessors3() {
        ArrayList<Position<Integer>> predecesores = (ArrayList<Position<Integer>>) tree.predecessors(pos[6]);
        assertEquals(predecesores.get(0),pos[5]);
        assertEquals(predecesores.get(1),pos[3]);
        assertEquals(predecesores.get(2),pos[2]);
        assertEquals(predecesores.get(3),pos[1]);
        assertEquals(predecesores.get(4),pos[4]);

    }
    @Test
    void sucessors1() {
        ArrayList<Position<Integer>> sucesores = (ArrayList<Position<Integer>>) tree.successors(pos[4]);
        assertEquals(sucesores.get(0),pos[5]);
        assertEquals(sucesores.get(1),pos[7]);
        assertEquals(sucesores.get(2),pos[6]);
        assertEquals(sucesores.get(3),pos[8]);
    }
    @Test
    void sucessors2() {
        ArrayList<Position<Integer>> sucesores = (ArrayList<Position<Integer>>) tree.successors(pos[2]);
        assertEquals(sucesores.get(0),pos[3]);
        assertEquals(sucesores.get(1),pos[5]);
        assertEquals(sucesores.get(2),pos[4]);
        assertEquals(sucesores.get(3),pos[7]);
        assertEquals(sucesores.get(4),pos[6]);
        assertEquals(sucesores.get(5),pos[8]);
    }
    @Test
    void sucessors3() {
        ArrayList<Position<Integer>> sucesores = (ArrayList<Position<Integer>>) tree.successors(pos[3]);
        assertEquals(sucesores.get(0),pos[5]);
        assertEquals(sucesores.get(1),pos[4]);
        assertEquals(sucesores.get(2),pos[7]);
        assertEquals(sucesores.get(3),pos[6]);
        assertEquals(sucesores.get(4),pos[8]);
    }
    @Test
    void findRange1(){
        ArrayList<Position<Integer>> range = (ArrayList<Position<Integer>>) tree.findRange(2,6);
        assertEquals(range.get(0),pos[3]);
        assertEquals(range.get(1),pos[2]);
        assertEquals(range.get(2),pos[5]);
        assertEquals(range.get(3),pos[4]);
        assertEquals(range.get(4),pos[6]);
    }
    @Test
    void findRange2(){
        ArrayList<Position<Integer>> range = (ArrayList<Position<Integer>>) tree.findRange(3,10);
        assertEquals(range.get(0),pos[3]);
        assertEquals(range.get(1),pos[5]);
        assertEquals(range.get(2),pos[4]);
        assertEquals(range.get(3),pos[7]);
        assertEquals(range.get(4),pos[6]);
        assertEquals(range.get(5),pos[8]);
    }
    @Test
    void findRange3(){
        ArrayList<Position<Integer>> range = (ArrayList<Position<Integer>>) tree.findRange(-4,5);
        assertEquals(range.get(0),pos[3]);
        assertEquals(range.get(1),pos[2]);
        assertEquals(range.get(2),pos[1]);
        assertEquals(range.get(3),pos[5]);
        assertEquals(range.get(4),pos[4]);
    }

    @Test
    void findRange4(){
        ArrayList<Position<Integer>> range = (ArrayList<Position<Integer>>) tree.findRange(10,15);
        assertEquals(range.size(),0);
    }
    @Test
    void findRange5(){
        ArrayList<Position<Integer>> range = (ArrayList<Position<Integer>>) tree.findRange(-4,0);
        assertEquals(range.size(),0);
    }
    @Test
    void findRange6(){
        ArrayList<Position<Integer>> range = (ArrayList<Position<Integer>>) tree.findRange(-4,15);
        assertEquals(range.get(0),pos[3]);
        assertEquals(range.get(1),pos[2]);
        assertEquals(range.get(2),pos[1]);
        assertEquals(range.get(3),pos[5]);
        assertEquals(range.get(4),pos[4]);
        assertEquals(range.get(5),pos[7]);
        assertEquals(range.get(6),pos[6]);
        assertEquals(range.get(7),pos[8]);
    }
}
