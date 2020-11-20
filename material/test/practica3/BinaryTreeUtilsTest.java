package material.test.practica3;

import material.Position;
import material.tree.binarytree.BinaryTree;
import material.tree.binarytree.BinaryTreeUtils;
import material.tree.binarytree.LinkedBinaryTree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BinaryTreeUtilsTest {

    private BinaryTree<Integer> tree;
    private BinaryTreeUtils utils;
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
        utils = new BinaryTreeUtils(tree);
    }

    @org.junit.jupiter.api.Test
    void mirror(){
        BinaryTree<Integer> mirrortree = utils.mirror();
        assertEquals(pos[0].getElement(),mirrortree.root().getElement());
        assertEquals(pos[1].getElement(),mirrortree.right(mirrortree.root()).getElement());
        assertEquals(pos[2].getElement(),mirrortree.left(mirrortree.root()).getElement());
        assertEquals(pos[3].getElement(),mirrortree.left(mirrortree.right(mirrortree.root())).getElement());
        assertEquals(pos[4].getElement(),mirrortree.right(mirrortree.left(mirrortree.root())).getElement());
        assertEquals(pos[5].getElement(),mirrortree.left(mirrortree.left(mirrortree.root())).getElement());
        assertEquals(pos[6].getElement(),mirrortree.right(mirrortree.right(mirrortree.left(mirrortree.root()))).getElement());
        assertEquals(pos[7].getElement(),mirrortree.left(mirrortree.right(mirrortree.left(mirrortree.root()))).getElement());
    }

    @org.junit.jupiter.api.Test
    void contains(){
        assert utils.contains(4);
        assert !utils.contains(10);
    }

    @org.junit.jupiter.api.Test
    void level(){
        assertEquals(1,utils.level(pos[0]));
        assertEquals(2,utils.level(pos[1]));
        assertEquals(3,utils.level(pos[4]));
        assertEquals(4,utils.level(pos[7]));
        assertNotEquals(3,utils.level(pos[6]));


    }


}
