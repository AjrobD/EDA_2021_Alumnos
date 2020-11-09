package material.tree.narytree;

import material.Position;
import material.tree.iterators.BFSIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * A linked class for a tree where nodes have an arbitrary number of children.
 *
 * @author Raul Cabido, Abraham Duarte, Jose Velez, Jesús Sánchez-Oro
 * @param <E> the elements stored in the tree
 */
public class LCRSTree<E> implements NAryTree<E> {

    private class TreeNode<T> implements Position<T> {

        private T element; // The element stored in the position
        private TreeNode<T> parent; // The parent of the node
        private TreeNode<T> leftChild; // The left child of the node
        private TreeNode<T> rightSibling; //The right sibling of the node
        private LCRSTree<T> myTree; // A reference to the tree where the node belongs


        public TreeNode(T element, TreeNode<T> parent, TreeNode<T> leftChild, TreeNode<T> rightSibling, LCRSTree<T> myTree) {
            this.element = element;
            this.parent = parent;
            this.leftChild = leftChild;
            this.rightSibling = rightSibling;
            this.myTree = myTree;
        }

        @Override
        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public TreeNode<T> getParent() {
            return parent;
        }

        public void setParent(TreeNode<T> parent) {
            this.parent = parent;
        }

        public TreeNode<T> getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(TreeNode<T> leftChild) {
            this.leftChild = leftChild;
        }

        public TreeNode<T> getRightSibling() {
            return rightSibling;
        }

        public void setRightSibling(TreeNode<T> rightSibling) {
            this.rightSibling = rightSibling;
        }

        public LCRSTree<T> getMyTree() {
            return myTree;
        }

        public void setMyTree(LCRSTree<T> myTree) {
            this.myTree = myTree;
        }


        public boolean isAncestor(TreeNode<T> nChild){
            TreeNode<T> nAux = nChild;
            while(nAux.parent != null){
                if(nAux.parent==this){
                    return true;
                }
                nAux = nAux.parent;
            }
            return false;
        }


    }
    private TreeNode<E> root; // The root of the tree
    private int size; // The number of nodes in the tree

    /**
     * Creates an empty tree.
     */
    public LCRSTree() {
        root = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public Position<E> root() throws RuntimeException {
        if (root == null) {
            throw new RuntimeException("The tree is empty");
        }
        return root;
    }

    @Override
    public Position<E> parent(Position<E> v) throws RuntimeException {
        TreeNode<E> node = checkPosition(v);
        Position parentPos = node.getParent();
        if(parentPos == null){
            throw new RuntimeException("The node has not parent");
        }
        return parentPos;
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        List<TreeNode<E>> children = new ArrayList<>();
        TreeNode<E> nodeAux = node.getLeftChild();
        while(nodeAux!=null){
            children.add(nodeAux);
            nodeAux = nodeAux.getRightSibling();
        }
        return children;
    }

    @Override
    public boolean isInternal(Position<E> v) {
        return !isLeaf(v);
    }

    @Override
    public boolean isLeaf(Position<E> v) throws RuntimeException {
        TreeNode<E> node = checkPosition(v);
        return node.getLeftChild()==null;
    }

    @Override
    public boolean isRoot(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        return node==root;
    }

    @Override
    public Position<E> addRoot(E e) throws RuntimeException {
        if (!isEmpty()) {
            throw new IllegalStateException("Tree already has a root");
        }
        size = 1;
        root = new TreeNode<E>(e,null,null,null,this);
        return root;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new BFSIterator<>(this);
    }

    @Override
    public E replace(Position<E> p, E e) {
        TreeNode<E> node = checkPosition(p);
        E temp = node.getElement();
        node.setElement(e);
        return temp;
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) {
        TreeNode<E> n1 = checkPosition(p1);
        TreeNode<E> n2 = checkPosition(p2);
        E temp = n1.getElement();
        n1.setElement(n2.getElement());
        n2.setElement(temp);
    }

    private TreeNode<E> checkPosition(Position<E> p)
            throws IllegalStateException {
        if (p == null || !(p instanceof TreeNode)) {
            throw new IllegalStateException("The position is invalid");
        }
        TreeNode<E> aux = (TreeNode<E>) p;

        if (aux.getMyTree() != this) {
            throw new IllegalStateException("The node is not from this tree");
        }
        return aux;
    }
    @Override
    public Position<E> add(E element, Position<E> p) {
        TreeNode<E> node = checkPosition(p);
        TreeNode<E> child = new TreeNode<>(element,node,null,null,this);
        if (node.getLeftChild()==null){
            node.setLeftChild(child);
        }
        else{
            TreeNode<E> aux = node.getLeftChild();
            while(aux.getRightSibling()!=null){
                aux=aux.getRightSibling();
            }
            aux.setRightSibling(child);
        }
        size++;
        return child;
    }

    @Override
    public void remove(Position<E> p) {
        TreeNode<E> node = checkPosition(p);
        if(node.getParent()!=null){
            if(node.getParent().getLeftChild()==node){
                node.getParent().setLeftChild(node.getRightSibling());
            }
            else{
                this.getLeftSibling(node).setRightSibling(node.getRightSibling());
            }
            Iterator<Position<E>> it = new BFSIterator<>(this, p);
            int cont = 0;
            while (it.hasNext()) {
                TreeNode<E> next = checkPosition(it.next());
                next.setMyTree(null);
                cont++;
            }
            size = size - cont;
        }
        else{
            root = null;
            size = 0;
        }
        node.setMyTree(null);
    }

    @Override
    public void moveSubtree(Position<E> pOrig, Position<E> pDest) throws RuntimeException {
        TreeNode<E> nOrig = checkPosition(pOrig);
        TreeNode<E> nDest = checkPosition(pDest);
        if(nOrig == root){
            throw new RuntimeException("Root node can't be moved");
        }
        else if(nOrig == nDest){
            throw new RuntimeException("Both positions are the same");
        }
        else if(nOrig.isAncestor(nDest)){
            throw new RuntimeException("Target position can't be a sub tree of origin");
        }
        if(nOrig.getParent().getLeftChild()==nOrig){
            nOrig.getParent().setLeftChild(nOrig.getRightSibling());
        }
        else{
            this.getLeftSibling(nOrig).setRightSibling(nOrig.getRightSibling());
        }
        nOrig.setRightSibling(nDest.getLeftChild());
        nDest.setLeftChild(nOrig);
        nOrig.setParent(nDest);
    }

    private TreeNode<E> getLeftSibling(TreeNode<E> node){
        TreeNode<E> aux = node.getParent().getLeftChild();
        TreeNode<E> leftSibling = null;
        while(aux!=node){
            leftSibling = aux;
            aux = aux.getRightSibling();
        }
        return leftSibling;
    }
}
