package material.tree.binarytree;

import material.Position;
import material.tree.iterators.BFSIterator;
import material.tree.iterators.InorderBinaryTreeIterator;

import java.util.*;

public class ArrayBinaryTree<E> implements BinaryTree<E> {

    protected class ABTPos<T> implements Position<T> {

        private T element;
        private int pos;

        public ABTPos(T element, int pos) {
            this.element = element;
            this.pos = pos;
        }

        @Override
        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }
    }

    private ABTPos<E>[] tree;

    public ArrayBinaryTree(){
        tree = new ABTPos[16];
    }

    @Override
    public Position<E> left(Position<E> p) throws RuntimeException {
        ABTPos<E> pos = checkPosition(p);
        if(2*pos.getPos()>=tree.length||tree[2*pos.getPos()]==null){
            throw new RuntimeException("No left child");
        }
        return tree[2*pos.getPos()];
    }

    @Override
    public Position<E> right(Position<E> p) throws RuntimeException {
        ABTPos<E> pos = checkPosition(p);
        if(2*pos.getPos()+1>=tree.length||tree[2*pos.getPos()+1]==null){
            throw new RuntimeException("No right child");
        }
        return tree[2*pos.getPos()+1];
    }

    @Override
    public boolean hasLeft(Position<E> p) {
        ABTPos<E> pos = checkPosition(p);
        if(2*pos.getPos()>=tree.length){
            return false;
        }
        return tree[2*pos.getPos()]!=null;
    }

    @Override
    public boolean hasRight(Position<E> p) {
        ABTPos<E> pos = checkPosition(p);
        if(2*pos.getPos()+1>=tree.length){
            return false;
        }
        return tree[2*pos.getPos()+1]!=null;
    }

    @Override
    public E replace(Position<E> p, E e) {
        ABTPos<E> pos = checkPosition(p);
        E temp = pos.getElement();
        pos.setElement(e);
        return temp;
    }

    @Override
    public Position<E> sibling(Position<E> p) throws RuntimeException {
        ABTPos<E> node = checkPosition(p);
        int position = node.getPos();
        if(position!=1){
            Position<E> sibPos;
            if((position%2==0)&&(position+1<tree.length)){
                sibPos = tree[position+1];
            }
            else{
                sibPos=tree[position-1];
            }
            if(sibPos!=null){
                return sibPos;
            }
        }
        throw new RuntimeException("No sibling");
    }

    @Override
    public Position<E> insertLeft(Position<E> p, E e) throws RuntimeException {
        ABTPos<E> father = checkPosition(p);
        int leftPos = father.getPos()*2;
        if(leftPos>=tree.length){
            ABTPos<E>[] newTree = new ABTPos[tree.length*3];
            for(int i=0;i<tree.length;i++){
                newTree[i] = tree[i];
            }
            tree = newTree;
        }
        if(tree[leftPos]!=null){
            throw new RuntimeException("Node already has a left child");
        }
        ABTPos<E> newNode = new ABTPos<>(e,leftPos);
        tree[leftPos] = newNode;
        return newNode;
    }

    @Override
    public Position<E> insertRight(Position<E> p, E e) throws RuntimeException {
        ABTPos<E> father = checkPosition(p);
        int rightPos = father.getPos()*2+1;
        if(rightPos>=tree.length){
            ABTPos<E>[] newTree = new ABTPos[tree.length*3];
            for(int i=0;i<tree.length;i++){
                newTree[i] = tree[i];
            }
            tree = newTree;
        }
        if(tree[rightPos]!=null){
            throw new RuntimeException("Node already has a right child");
        }
        ABTPos<E> newNode = new ABTPos<>(e,rightPos);
        tree[rightPos] = newNode;
        return newNode;
    }

    //no esta bien hecho, no se como cambiar los indices de todos los descendientes del nodo borrado
    @Override
    public E remove(Position<E> p) throws RuntimeException {
        ABTPos<E> node = checkPosition(p);
        int pos = node.getPos();
        E elem = node.getElement();
        if(pos*2+1<tree.length && pos*2<tree.length && tree[pos*2]!=null && tree[pos*2+1]!=null){
            throw new RuntimeException("Cannot remove node with two children");
        }
        if(pos==1&&size()==1){
            tree[pos]=null;
            return elem;
        }
        if(hasLeft(p)){
            if(pos%2==0)
                relocate(pos,pos/2,left(p));
            else
                relocate(pos,(pos-1)/2,left(p));
        }
        else if(hasRight(p)){
            if(pos%2==0)
                relocate(pos,pos/2,right(p));
            else
                relocate(pos,(pos-1)/2,right(p));
        }
        else{
            tree[pos]=null;
        }
        return elem;
    }

    @Override
    public void swap(Position<E> p1, Position<E> p2) {
        ABTPos<E> n1 = checkPosition(p1);
        ABTPos<E> n2 = checkPosition(p2);
        int pos1 = n1.getPos();
        int pos2 = n2.getPos();
        tree[pos2] = n1;
        n1.setPos(pos2);
        tree[pos1] = n2;
        n2.setPos(pos1);
    }

    @Override
    public BinaryTree<E> subTree(Position<E> v) {
        Queue<Position<E>> nodeQueueNew = new ArrayDeque<>();
        Queue<ABTPos<E>> nodeQueueOld = new ArrayDeque<>();
        BFSIterator iterator = new BFSIterator(this, v);
        BinaryTree<E> tree = new ArrayBinaryTree<>();
        while(iterator.hasNext()){
            Position<E> node = iterator.next();
            Position<E> firstNew = nodeQueueNew.peek();
            ABTPos<E> firstOld = nodeQueueOld.peek();

            if(node == v){
                nodeQueueNew.add(tree.addRoot(node.getElement()));
                nodeQueueOld.add(this.checkPosition(node));
            }
            else if((double)checkPosition(node).getPos()/2 == firstOld.getPos()){
                nodeQueueNew.add(tree.insertLeft(firstNew, node.getElement()));
                nodeQueueOld.add(this.checkPosition(node));
            }
            else if ((double)(checkPosition(node).getPos()-1)/2 == firstOld.getPos()) {
                nodeQueueNew.add(tree.insertRight(firstNew, node.getElement()));
                nodeQueueOld.add(this.checkPosition(node));
            }
            if (firstOld != null && ((double)(checkPosition(node).getPos()-1)/2 == firstOld.getPos() || ((double)(checkPosition(node).getPos()/2) == firstOld.getPos() && !this.hasRight((Position<E>) firstOld)))) {
                nodeQueueNew.remove();
                nodeQueueOld.remove();
            }

            this.tree[checkPosition(node).getPos()]=null;
        }
        return tree;
    }

    @Override
    public void attachLeft(Position<E> p, BinaryTree<E> tree) throws RuntimeException {
        if (tree == this) {
            throw new RuntimeException("Cannot attach a tree over himself");
        }
        if (this.hasLeft(p)) {
            throw new RuntimeException("Node already has a left child");
        }
        if (tree != null && !tree.isEmpty()) {
            Queue<Position<E>> nodeQueueNew = new ArrayDeque<>();
            Queue<Position<E>> nodeQueueOld = new ArrayDeque<>();
            BFSIterator iterator = new BFSIterator(tree);
            while (iterator.hasNext()) {
                Position<E> node = iterator.next();
                Position<E> firstNew = nodeQueueNew.peek();
                Position<E> firstOld = nodeQueueOld.peek();

                if (node == tree.root()) {
                    nodeQueueNew.add(insertLeft(p, node.getElement()));
                    nodeQueueOld.add(node);
                } else if (node == tree.left(firstOld)) {
                    nodeQueueNew.add(insertLeft(firstNew, node.getElement()));
                    nodeQueueOld.add(node);
                } else if (node == tree.right(firstOld)) {
                    nodeQueueNew.add(insertRight(firstNew, node.getElement()));
                    nodeQueueOld.add(node);
                }
                if (firstOld != null && (node == tree.right(firstOld) || (node == tree.left(firstOld) && !tree.hasRight(firstOld)))) {
                    nodeQueueNew.remove();
                    nodeQueueOld.remove();
                }
            }
        }
    }

    @Override
    public void attachRight(Position<E> p, BinaryTree<E> tree) throws RuntimeException {
        if (tree == this) {
            throw new RuntimeException("Cannot attach a tree over himself");
        }
        if (this.hasLeft(p)) {
            throw new RuntimeException("Node already has a left child");
        }
        if (tree != null && !tree.isEmpty()) {
            Queue<Position<E>> nodeQueueNew = new ArrayDeque<>();
            Queue<Position<E>> nodeQueueOld = new ArrayDeque<>();
            BFSIterator iterator = new BFSIterator(tree);
            while (iterator.hasNext()) {
                Position<E> node = iterator.next();
                Position<E> firstNew = nodeQueueNew.peek();
                Position<E> firstOld = nodeQueueOld.peek();

                if (node == tree.root()) {
                    nodeQueueNew.add(insertRight(p, node.getElement()));
                    nodeQueueOld.add(node);
                } else if (node == tree.left(firstOld)) {
                    nodeQueueNew.add(insertLeft(firstNew, node.getElement()));
                    nodeQueueOld.add(node);
                } else if (node == tree.right(firstOld)) {
                    nodeQueueNew.add(insertRight(firstNew, node.getElement()));
                    nodeQueueOld.add(node);
                }
                if (firstOld != null && (node == tree.right(firstOld) || (node == tree.left(firstOld) && !tree.hasRight(firstOld)))) {
                    nodeQueueNew.remove();
                    nodeQueueOld.remove();
                }
            }
        }
    }

    @Override
    public boolean isComplete() {
        for (Position<E> next : tree) {
            if (next!=null && this.isInternal(next) && (!this.hasLeft(next) || !this.hasRight(next)))
                return false;
        }
        return true;
    }

    @Override
    public int size() {
        int size = 0;
        for (Position<E> p : tree) {
            if(p!=null)
                size++;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return tree[1]==null;
    }

    @Override
    public Position<E> root() throws RuntimeException {
        if(isEmpty()){
            throw new RuntimeException("The tree is empty");
        }
        return tree[1];
    }

    @Override
    public Position<E> parent(Position<E> v) throws RuntimeException {
        ABTPos<E> node = checkPosition(v);
        Position<E> parentPos;
        if(node == checkPosition(this.root())){
            parentPos = null;
        }
        else{
            if(node.getPos()%2==0){
                parentPos = tree[node.getPos()/2];
            }
            else{
                parentPos = tree[(node.getPos()-1)/2];
            }
        }
        if (parentPos == null) {
            throw new RuntimeException("No parent");
        }
        return parentPos;
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        List<Position<E>> children = new ArrayList<>();
        if (hasLeft(v)) {
            children.add(left(v));
        }
        if (hasRight(v)) {
            children.add(right(v));
        }
        return Collections.unmodifiableCollection(children);
    }

    @Override
    public boolean isInternal(Position<E> v) {
        checkPosition(v);
        return (hasLeft(v) || hasRight(v));
    }

    @Override
    public boolean isLeaf(Position<E> v) throws RuntimeException {
        return !isInternal(v);
    }

    @Override
    public boolean isRoot(Position<E> v) {
        ABTPos<E> pos = checkPosition(v);
        return pos.getPos()==0;
    }

    @Override
    public Position<E> addRoot(E e) throws RuntimeException {
        if(!isEmpty()){
            throw new RuntimeException("Tree already has a root");
        }
        ABTPos<E> root = new ABTPos<E>(e,1);
        this.tree[1] = root;
        return root;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new InorderBinaryTreeIterator<>(this);
    }

    // Auxiliary methods

    /**
     * If v is a good binary tree node, cast to ABTPos, else throw exception
     */
    private ABTPos<E> checkPosition(Position<E> p) {
        if (!(p instanceof ABTPos)) {
            throw new RuntimeException("The position is invalid");
        }
        return (ABTPos<E>) p;
    }

    private void relocate(int father, int newFather, Position<E> son){
        ABTPos<E> node = (ABTPos<E>) son;
        int pos;
        if(father%2==0){
            pos=newFather*2;
        }
        else{
            pos=newFather*2+1;
        }
        int oldPos = node.getPos();
        tree[oldPos]=null;
        tree[pos]=node;
        node.setPos(pos);
        if(node.getPos()*2< tree.length&&tree[node.getPos()*2]!=null){
            if(pos%2==0)
                relocate(pos,pos/2,tree[node.getPos()*2]);
            else
                relocate(pos,(pos-1)/2,tree[node.getPos()*2]);
        }
        if(node.getPos()*2+1< tree.length&&tree[node.getPos()*2+1]!=null){
            if(pos%2==0)
                relocate(pos,pos/2,tree[node.getPos()*2+1]);
            else
                relocate(pos,(pos-1)/2,tree[node.getPos()*2+1]);
        }
    }
}
