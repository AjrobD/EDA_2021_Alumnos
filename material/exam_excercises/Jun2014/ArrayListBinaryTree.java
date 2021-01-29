package material.exam_excercises.Jun2014;

import material.Position;
import material.tree.binarytree.BinaryTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class ArrayListBinaryTree<E> implements BinaryTree<E> {
    @Override
    public Position<E> left(Position<E> p) throws RuntimeException {
        return null;
    }

    @Override
    public Position<E> right(Position<E> p) throws RuntimeException {
        return null;
    }

    @Override
    public boolean hasLeft(Position<E> p) {
        return false;
    }

    @Override
    public boolean hasRight(Position<E> p) {
        return false;
    }

    @Override
    public E replace(Position<E> p, E e) {
        return null;
    }

    @Override
    public Position<E> sibling(Position<E> p) throws RuntimeException {
        return null;
    }

    @Override
    public Position<E> insertLeft(Position<E> p, E e) throws RuntimeException {
        return null;
    }

    @Override
    public Position<E> insertRight(Position<E> p, E e) throws RuntimeException {
        return null;
    }

    @Override
    public E remove(Position<E> p) throws RuntimeException {
        return null;
    }

    @Override
    public void swap(Position<E> p1, Position<E> p2) {

    }

    @Override
    public BinaryTree<E> subTree(Position<E> v) {
        return null;
    }

    @Override
    public void attachLeft(Position<E> p, BinaryTree<E> tree) throws RuntimeException {

    }

    @Override
    public void attachRight(Position<E> p, BinaryTree<E> tree) throws RuntimeException {

    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Position<E> root() throws RuntimeException {
        return null;
    }

    @Override
    public Position<E> parent(Position<E> v) throws RuntimeException {
        return null;
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        return null;
    }

    @Override
    public boolean isInternal(Position<E> v) {
        return false;
    }

    @Override
    public boolean isLeaf(Position<E> v) throws RuntimeException {
        return false;
    }

    @Override
    public boolean isRoot(Position<E> v) {
        return false;
    }

    @Override
    public Position<E> addRoot(E e) throws RuntimeException {
        return null;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return null;
    }

    public BTPos<E> checkPosition(Position<E> pos){
        return null;
    }

    private class BTPos<E> implements Position<E> {
        private int index;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        private int padre;
        private E element;
        private int left;
        private int right;

        public BTPos(int index, int padre, E element, int left, int right) {
            this.index = index;
            this.padre = padre;
            this.element = element;
            this.left = left;
            this.right = right;
        }

        public int getPadre() {
            return padre;
        }

        public void setPadre(int padre) {
            this.padre = padre;
        }

        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public int getRight() {
            return right;
        }

        public void setRight(int right) {
            this.right = right;
        }
    }

    private ArrayList<BTPos<E>> tree;
    private Set<Integer> empty;

    public ArrayListBinaryTree(ArrayList<BTPos<E>> tree) {
        this.tree = new ArrayList<>();
    }

    public void removeSubTree(Position<E> pos){
        BTPos<E> nodo = checkPosition(pos);
        if(nodo!=null) {
            BTPos<E> padre = tree.get(nodo.getPadre());
            if(padre.getLeft()==nodo.getIndex()){
                padre.setLeft(-1);
            }
            if(padre.getRight()==nodo.getIndex()){
                padre.setRight(-1);
            }
            recursiveRemove(nodo);
        }
    }

    private void recursiveRemove(BTPos<E> nodo) {
        if(nodo!=null){
            empty.add(nodo.getIndex());
            tree.remove(nodo);
            if(hasLeft(nodo)) {
                recursiveRemove(checkPosition(this.left(nodo)));
            }
            if(hasLeft(nodo)) {
                recursiveRemove(checkPosition(this.right(nodo)));
            }
        }
    }

    private Position<E> inserLeft(Position<E> padre, E element) throws RuntimeException{
        BTPos<E> padrePos = checkPosition(padre);
        if(padrePos.getLeft()!=-1){
            throw new RuntimeException("Node already has a left child");
        }
        else{
            int firstFree=-1;
            if(empty.iterator().hasNext()){
                firstFree = empty.iterator().next();
            }
            padrePos.setLeft(firstFree);
            BTPos<E> newNode = new BTPos<>(firstFree,padrePos.getIndex(),element,-1,-1);
            if(firstFree==-1){
                tree.add(newNode);
            }
            else{
                tree.add(firstFree,newNode);
            }
            return newNode;
        }
    }


}
