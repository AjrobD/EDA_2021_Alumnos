package material.tree.binarysearchtree;

import java.util.*;

import material.Position;


/**
 * AVLTree class - implements an AVL Tree by extending a binary search tree.
 *
 * @author A. Duarte, J. Vélez
 */
public class AVLTree<E> implements BinarySearchTree<E> {

	//We need this class to store the height of each BTNode
    private class AVLInfo<T> implements Comparable<AVLInfo<T>>, Position<T>{

        private int height;
        private T element;
        private Position<AVLInfo<T>> pos;

        AVLInfo(T element) {
            this.element = element;
            this.pos = null;
            this.height = 1;
        }

        public void setTreePosition(Position<AVLInfo<T>> pos){
        	this.pos = pos;
        }

        public Position<AVLInfo<T>> getTreePosition(){
        	return this.pos;
        }


        public void setHeight(int height) {
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        public T getElement() {
            return element;
        }

        @Override
        public int compareTo(AVLInfo<T> o) {
            if (element instanceof Comparable && o.element instanceof Comparable) {
                Comparable <T> c1 = (Comparable<T>) element;
                return c1.compareTo(o.element);

            } else {
                throw new ClassCastException("Element is not comparable");
            }
        }

        @Override
        public String toString(){
            return this.getElement().toString();
        }
    }


    private class AVLTreeIterator<T> implements Iterator<Position<T>> {

        private Iterator<Position<AVLInfo<T>>> it;

        public AVLTreeIterator(Iterator<Position<AVLInfo<T>>> iterator) {
            this.it = iterator;
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Position<T> next() {
            Position<AVLInfo<T>> aux = it.next();
            return aux.getElement();
        }

        @Override
        public void remove() {
            it.remove();
        }
    }

    private LinkedBinarySearchTree<AVLInfo<E>> bst;
    private ReestructurableBinaryTree<AVLInfo<E>> resBT;


    public AVLTree(){
    	this(new DefaultComparator<>());
    }

    /**
     * Creates a BinarySearchTree with the given comparator.
     *
     * @param c the comparator used to sort the nodes in the tree
     */
    public AVLTree(Comparator<E> c) {
        Comparator<AVLInfo<E>> avlComparator = (o1,o2)->c.compare(o1.getElement(), o2.getElement());
        bst = new LinkedBinarySearchTree<>(avlComparator);
        resBT = new ReestructurableBinaryTree<>();
        bst.binTree = resBT;
    }



    @Override
    public Position<E> find(E value) {
        AVLInfo<E> searchedValue = new AVLInfo<>(value);
        Position<AVLInfo<E>> output = bst.find(searchedValue);

        if (output == null) {
            return null;
        }
        return output.getElement();
    }

    @Override
    public Iterable<Position<E>> findAll(E value) {
        AVLInfo<E> searchedValue = new AVLInfo<>(value);
    	List<Position<E>> aux = new ArrayList<> ();
        for (Position<AVLInfo<E>> n : bst.findAll(searchedValue)){
			aux.add(n.getElement());
        }
        return aux;
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }


    @Override
    public int size() {
        return bst.size();
    }



    /**
     * Returns whether a node has balance factor between -1 and 1.
     */
    private boolean isBalanced(Position<AVLInfo<E>> p) {

        AVLInfo<E> right = bst.binTree.right(p).getElement();
        AVLInfo<E> left = bst.binTree.left(p).getElement();

        final int leftHeight = (left == null) ? 0 : left.getHeight();
        final int rightHeight = (right == null) ? 0 : right.getHeight();

        final int bf = leftHeight - rightHeight;
        return ((-1 <= bf) && (bf <= 1));
    }

    /**
     * Return a child of p with height no smaller than that of the other child.
     */
    private Position<AVLInfo<E>> tallerChild(Position<AVLInfo<E>> p) {

        Position<AVLInfo<E>> right = bst.binTree.right(p);
        Position<AVLInfo<E>> left = bst.binTree.left(p);

        final int leftHeight = (left.getElement() == null) ? 0 : left.getElement().getHeight();
        final int rightHeight = (right.getElement() == null) ? 0 : right.getElement().getHeight();

        if (leftHeight > rightHeight) {
            return left;
        } else if (leftHeight < rightHeight) {
            return right;
        }

        // equal height children - break tie using parent's type
        if (bst.binTree.isRoot(p)) {
            return left;
        }

        if (p == bst.binTree.left(bst.binTree.parent(p))) {
            return left;
        } else {
            return right;
        }
    }

    private void calculateHeight(Position<AVLInfo<E>> p) {
        Position<AVLInfo<E>> left = bst.binTree.left(p);
        Position<AVLInfo<E>> right = bst.binTree.right(p);

        final int leftHeight = (left.getElement() == null) ? 0 : left.getElement().getHeight();
        final int rightHeight = (right.getElement() == null) ? 0 : right.getElement().getHeight();

        p.getElement().setHeight(1 + Math.max(leftHeight, rightHeight));
    }

    /**
     * Rebalance method called by insert and remove. Traverses the path from p
     * to the root. For each node encountered, we recompute its height and
     * perform a trinode restructuring if it's unbalanced.
     */
    private void rebalance(Position<AVLInfo<E>> zPos) {
        if (bst.binTree.isInternal(zPos)) {
            calculateHeight(zPos);
        }
        while (!bst.binTree.isRoot(zPos)) { // traverse up the tree towards the
            // root
            zPos = bst.binTree.parent(zPos);
            calculateHeight(zPos);
            if (!isBalanced(zPos)) {
                // perform a trinode restructuring at zPos's tallest grandchild
                Position<AVLInfo<E>> xPos = tallerChild(tallerChild(zPos));
                zPos = this.resBT.restructure(xPos, bst);
                calculateHeight(bst.binTree.left(zPos));
                calculateHeight(bst.binTree.right(zPos));
                calculateHeight(zPos);
            }
        }
    }

    /**
     * Inserts an item into the dictionary and returns the newly created entry.
     */
    @Override
    public Position<E> insert(E e) {
        AVLInfo<E> aux = new AVLInfo<>(e);
        Position<AVLInfo<E>> internalNode = bst.insert(aux);
        aux.setTreePosition(internalNode);
        rebalance(internalNode);
        return aux;
    }

    /**
     * Removes and returns an entry from the dictionary.
     */
    @Override
    public E remove(Position<E> pos) throws RuntimeException {
        AVLInfo<E> aux = checkPosition(pos);

        E toReturn = pos.getElement(); // entry to be returned
        Position<AVLInfo<E>> remPos = bst.getLeafToRemove(aux.getTreePosition());

        Position<AVLInfo<E>> actionPos = bst.binTree.sibling(remPos);
        bst.removeLeaf(remPos);
        rebalance(actionPos); // rebalance up the tree
        return toReturn;
    }

    @Override
	public Iterator<Position<E>> iterator() {
		Iterator<Position<AVLInfo<E>>> bstIt = bst.iterator();
		AVLTreeIterator <E> it = new AVLTreeIterator<E>(bstIt);
		return it;
	}

    /**
     * If v is a good tree node, cast to TreePosition, else throw exception
     */
    private AVLInfo<E> checkPosition(Position<E> p) throws RuntimeException {
        if (p == null) {
            throw new RuntimeException("The position of the AVL node is null");
        } else if (!(p instanceof AVLInfo)) {
            throw new RuntimeException("The position of the AVL node is not AVL");
        } else {
        	AVLInfo<E> aux = (AVLInfo<E>) p;
            return aux;
        }
    }

    /**
     * Ejercicio 1: findRange
     */

    public Iterable<Position<E>> findRange(E minValue, E maxValue) throws RuntimeException{
        List<Position<E>> range = new ArrayList<>();
        addToRange(range,this.bst.binTree.root().getElement(),minValue,maxValue);
        return range;
    }

    private void addToRange(List<Position<E>> range, Position<E> pos, E minValue, E maxValue){
        if(bst.comparator.compare(checkPosition(pos),new AVLInfo<E>(minValue))>=0&&this.bst.binTree.left(checkPosition(pos).getTreePosition()).getElement()!=null){
            addToRange(range,this.bst.binTree.left(checkPosition(pos).getTreePosition()).getElement(),minValue,maxValue);
        }
        if(bst.comparator.compare(checkPosition(pos),new AVLInfo<E>(minValue))>=0&&bst.comparator.compare(checkPosition(pos),new AVLInfo<E>(maxValue))<=0){
            range.add(pos);
        }
        if(bst.comparator.compare(checkPosition(pos),new AVLInfo<E>(maxValue))<=0&&this.bst.binTree.right(checkPosition(pos).getTreePosition()).getElement()!=null){
            addToRange(range,this.bst.binTree.right(checkPosition(pos).getTreePosition()).getElement(),minValue,maxValue);
        }
    }

    /**
     * Ejercicio 2: first, last, successors, predecessors
     */
    public Position<E> first() throws RuntimeException {
        return bst.first().getElement();
    }
    public Position<E> last() throws RuntimeException {
        return bst.last().getElement();
    }
    public Iterable<Position<E>> successors(Position<E> pos){
        AVLInfo<E> aux = checkPosition(pos);
        Position<AVLInfo<E>> position = aux.getTreePosition();
        List<Position<E>> sucesores = new ArrayList<>();
        if(this.bst.binTree.right(position).getElement()!=null){
            addToListUnder(this.bst.binTree.right(position).getElement(),sucesores,false);
        }
        if(!this.bst.binTree.isRoot(position)) {
            Position<E> parent = this.bst.binTree.parent(position).getElement();
            addToListUppBigger(pos, sucesores, true);
        }
        return sucesores;
    }

    public Iterable<Position<E>> predecessors(Position<E> pos){
        AVLInfo<E> aux = checkPosition(pos);
        Position<AVLInfo<E>> position = aux.getTreePosition();
        List<Position<E>> predecesores = new ArrayList<>();
        if(this.bst.binTree.left(position).getElement()!=null){
            addToListUnder( this.bst.binTree.left(position).getElement(),predecesores,true);
        }
        if(!this.bst.binTree.isRoot(position)) {
            Position<E> parent = this.bst.binTree.parent(position).getElement();
            addToListUppLesser(pos, predecesores, true);
        }
        Collections.reverse(predecesores);
        return predecesores;
    }

    public void addToListUnder(Position<E> pos, List<Position<E>> list, Boolean lesser){
        AVLInfo<E> aux = checkPosition(pos);
        Position<AVLInfo<E>> position = aux.getTreePosition();
        if(lesser){
            if(this.bst.binTree.right(position).getElement()!=null){
                addToListUnder( this.bst.binTree.right(position).getElement(),list,lesser);
            }
            list.add(pos);
            if(this.bst.binTree.left(position).getElement()!=null){
                addToListUnder( this.bst.binTree.left(position).getElement(),list,lesser);
            }
        }
        else {
            if (this.bst.binTree.left(position).getElement() != null) {
                addToListUnder(this.bst.binTree.left(position).getElement(), list, lesser);
            }
            list.add(pos);
            if (this.bst.binTree.right(position).getElement() != null) {
                addToListUnder(this.bst.binTree.right(position).getElement(), list, lesser);
            }
        }
    }

    public void addToListUppLesser(Position<E> pos, List<Position<E>> list, Boolean fromLeft){
        AVLInfo<E> aux = checkPosition(pos);
        Position<AVLInfo<E>> position = aux.getTreePosition();
        if(this.bst.binTree.left(position).getElement()!=null&&!fromLeft){
            addToListUnder( this.bst.binTree.left(position).getElement(),list,true);
        }
        if(!this.bst.binTree.isRoot(position)) { //si es root no puedo mirar el parent
            Position<AVLInfo<E>> parent = this.bst.binTree.parent(position);
            if (this.bst.binTree.left(parent).equals(position)) { //si es hijo izquierdo
                addToListUppLesser(parent.getElement(), list, true);
            } else {//si no es hijo izquierdo
                list.add(parent.getElement());
                addToListUppLesser(parent.getElement(), list, false);
            }
        }
    }
    public void addToListUppBigger(Position<E> pos, List<Position<E>> list, Boolean fromRight){
        AVLInfo<E> aux = checkPosition(pos);
        Position<AVLInfo<E>> position = aux.getTreePosition();
        if(this.bst.binTree.right(position).getElement()!=null&&!fromRight){
            addToListUnder( this.bst.binTree.right(position).getElement(),list,false);
        }
        if(!this.bst.binTree.isRoot(position)) { //si es root no puedo mirar el parent
            Position<AVLInfo<E>> parent = this.bst.binTree.parent(position);
            if (this.bst.binTree.right(parent).equals(position)) { //si es hijo derecho
                addToListUppBigger(parent.getElement(), list, true);
            } else {//si no es hijo derecho
                list.add(parent.getElement());
                addToListUppBigger(parent.getElement(), list, false);
            }
        }
    }

    /**
     * metodo findRange para cuando el arbol ha sido creado sin el comparador
     */


    public Iterable<Position<E>> findRangeComp(E minValue, E maxValue, Comparator<E> comparator) throws RuntimeException{
        List<Position<E>> range = new ArrayList<>();
        addToRangeComp(range,this.bst.binTree.root().getElement(),minValue,maxValue,comparator);
        return range;
    }

    private void addToRangeComp(List<Position<E>> range, Position<E> pos, E minValue, E maxValue, Comparator<E> comparator){
        if(comparator.compare(pos.getElement(), minValue)>=0&&this.bst.binTree.left(checkPosition(pos).getTreePosition()).getElement()!=null){
            addToRangeComp(range,this.bst.binTree.left(checkPosition(pos).getTreePosition()).getElement(),minValue,maxValue,comparator);
        }
        if(comparator.compare(pos.getElement(),minValue)>=0&&comparator.compare(pos.getElement(), maxValue)<=0){
            range.add(pos);
        }
        if(comparator.compare(pos.getElement(),maxValue)<=0&&this.bst.binTree.right(checkPosition(pos).getTreePosition()).getElement()!=null){
            addToRangeComp(range,this.bst.binTree.right(checkPosition(pos).getTreePosition()).getElement(),minValue,maxValue,comparator);
        }
    }
}
