package material.test.practica2;

import material.Position;
import material.tree.iterators.FrontIterator;
import material.tree.iterators.PreorderIterator;
import material.tree.iterators.PostorderIterator;
import material.tree.narytree.LinkedTree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IteratorTest {

    private LinkedTree<Integer> tree;
    private Position<Integer>[] pos;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        tree = new LinkedTree<>();
        pos = new Position[12];
        pos[0] = tree.addRoot(0);
        pos[1] = tree.add(1, pos[0]);
        pos[2] = tree.add(2, pos[0]);
        pos[3] = tree.add(3, pos[0]);
        pos[4] = tree.add(4, pos[0]);
        pos[5] = tree.add(5, pos[1]);
        pos[6] = tree.add(6, pos[2]);
        pos[7] = tree.add(7, pos[2]);
        pos[8] = tree.add(8, pos[3]);
        pos[9] = tree.add(9, pos[7]);
        pos[10] = tree.add(10, pos[7]);
        pos[11] = tree.add(11, pos[7]);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        // Not required
    }


    @org.junit.jupiter.api.Test
    void add() {
        Position<Integer> p100 = tree.add(100, pos[0]);
        assertEquals(pos[0], tree.parent(p100));
        int n = 0;
        for (Position<Integer> child : tree.children(pos[0])) {
            n++;
        }
        assertEquals(5, n);
        Position<Integer> p200 = tree.add(200, pos[9]);
        assertEquals(pos[9], tree.parent(p200));
        assertTrue(tree.isInternal(pos[9]));
    }


    @org.junit.jupiter.api.Test
    void iterator() {
        int next = 0;
        for (Position<Integer> position : tree) {
            assertEquals(pos[next], position);
            next++;
        }
    }

    @org.junit.jupiter.api.Test
    void PreOrderiterator() {
        Position<Integer>[] preorder = new Position[12];
        preorder[0] = pos[0];
        preorder[1] = pos[1];
        preorder[2] = pos[5];
        preorder[3] = pos[2];
        preorder[4] = pos[6];
        preorder[5] = pos[7];
        preorder[6] = pos[9];
        preorder[7] = pos[10];
        preorder[8] = pos[11];
        preorder[9] = pos[3];
        preorder[10] = pos[8];
        preorder[11] = pos[4];
        int next = 0;
        PreorderIterator<Integer> iterator = new PreorderIterator<>(tree);
        while(iterator.hasNext()){
            Integer element = iterator.next().getElement();
            assertEquals(preorder[next].getElement(),element);
            next++;
        }
    }

    @org.junit.jupiter.api.Test
    void PostOrderiterator() {
        Position<Integer>[] postorder = new Position[12];
        postorder[0] = pos[5];
        postorder[1] = pos[1];
        postorder[2] = pos[6];
        postorder[3] = pos[9];
        postorder[4] = pos[10];
        postorder[5] = pos[11];
        postorder[6] = pos[7];
        postorder[7] = pos[2];
        postorder[8] = pos[8];
        postorder[9] = pos[3];
        postorder[10] = pos[4];
        postorder[11] = pos[0];
        int next = 0;
        PostorderIterator<Integer> iterator = new PostorderIterator<>(tree);
        while(iterator.hasNext()){
            Integer element = iterator.next().getElement();
            assertEquals(postorder[next].getElement(),element);
            next++;
        }
    }

    @org.junit.jupiter.api.Test
    void Frontiterator() {
        Position<Integer>[] preorder = new Position[7];
        preorder[0] = pos[4];
        preorder[1] = pos[5];
        preorder[2] = pos[6];
        preorder[3] = pos[8];
        preorder[4] = pos[9];
        preorder[5] = pos[10];
        preorder[6] = pos[11];
        int next = 0;
        FrontIterator<Integer> iterator = new FrontIterator<>(tree);
        while(iterator.hasNext()){
            Integer element = iterator.next().getElement();
            assertEquals(preorder[next].getElement(),element);
            next++;
        }
    }
}