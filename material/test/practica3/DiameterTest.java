package material.test.practica3;

import material.Position;
import material.tree.binarytree.BinaryTree;
import material.tree.binarytree.LinkedBinaryTree;
import usecase.practica3.Diameter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiameterTest {

    private BinaryTree<Integer> tree;
    private Position<Integer>[] pos;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        tree = new LinkedBinaryTree<>();
        pos = new Position[8];
        pos[0] = tree.addRoot(0);
        pos[1] = tree.insertLeft(pos[0],1 );
        pos[2] = tree.insertRight(pos[0],2 );
        pos[3] = tree.insertRight(pos[1],3 );
        pos[4] = tree.insertLeft(pos[2],4);
        pos[5] = tree.insertRight(pos[2],5);
        pos[6] = tree.insertLeft(pos[4],6 );
        pos[7] = tree.insertRight(pos[4],7 );
    }

    @org.junit.jupiter.api.Test
    void diameter() {
        Diameter dia = new Diameter();
        assertEquals(6, dia.evalDiameter(tree,pos[3],pos[6]));
        assertEquals(4, dia.evalDiameter(tree,pos[5],pos[6]));
        assertEquals(3, dia.evalDiameter(tree,pos[7],pos[6]));
        assertEquals(2, dia.evalDiameter(tree,pos[2],pos[5]));
        assertEquals(1, dia.evalDiameter(tree,pos[5],pos[5]));
    }

}
