package material.test.practica3;

import material.Position;
import material.tree.binarytree.ArrayBinaryTree;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BinaryTreeTest2 {
    private ArrayBinaryTree<Integer> tree;
    private Position<Integer>[] pos;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        tree = new ArrayBinaryTree<>();
        pos = new Position[7];
        pos[0]=tree.addRoot(1);
        pos[1]= tree.insertLeft(pos[0],2);
        pos[2]= tree.insertRight(pos[0],3);
        pos[3]= tree.insertLeft(pos[1],4);
        pos[4]= tree.insertRight(pos[1],5);
        pos[5]= tree.insertLeft(pos[2],6);
        pos[6]= tree.insertRight(pos[2],7);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        // Not required
    }

    @org.junit.jupiter.api.Test
    void size() {
        assertEquals(7, tree.size());
        tree.remove(pos[5]);
        assertEquals(6, tree.size());
        //Vaciamos el arbol
        tree.remove(pos[3]);
        tree.remove(pos[4]);
        tree.remove(pos[6]);
        tree.remove(pos[1]);
        tree.remove(pos[2]);
        tree.remove(pos[0]);
        assertEquals(0, tree.size());
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        assertFalse(tree.isEmpty());
        //Vaciamos el arbol
        tree.remove(pos[3]);
        tree.remove(pos[4]);
        tree.remove(pos[5]);
        tree.remove(pos[6]);
        tree.remove(pos[1]);
        tree.remove(pos[2]);
        tree.remove(pos[0]);
        assertTrue(tree.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void left(){
        assertEquals(pos[3],tree.left(pos[1]));
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> tree.left(pos[6]));
        assertEquals("No left child", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void right(){
        assertEquals(pos[4],tree.right(pos[1]));
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> tree.right(pos[6]));
        assertEquals("No right child", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void hasLeft(){
        assertTrue(tree.hasLeft(pos[2]));
        assertFalse(tree.hasLeft(pos[3]));
    }

    @org.junit.jupiter.api.Test
    void hasRight(){
        assertTrue(tree.hasRight(pos[2]));
        assertFalse(tree.hasRight(pos[3]));
    }

    @org.junit.jupiter.api.Test
    void sibling(){
        assertEquals(pos[3],tree.sibling(pos[4]));
        Position<Integer> posAux;
        posAux = tree.insertLeft(pos[5],8);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> tree.sibling(posAux));
        assertEquals("No sibling", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void isComplete(){
        assertTrue(tree.isComplete());
        tree.remove(pos[5]);
        assertFalse(tree.isComplete());
    }
}
