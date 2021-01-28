package material.tree.binarysearchtree;

import java.util.*;

import material.Position;

/**
 * Realization of a red-black Tree by extending a binary search tree.
 *
 * @author A. Duarte, J. Vélez
 */

public class RBTree<E> implements BinarySearchTree<E> {

//Esta clase es necesaria para guardar el color de los nodos BTNodes

    private class RBInfo<T> implements Comparable<RBInfo<T>>, Position<T>{

    	private boolean isRed; // we add a color field to a BTNode
        private T element;
        private Position<RBInfo<T>> pos;

        RBInfo(T element) {
            this.element = element;
        }

        public void setTreePosition(Position<RBInfo<T>> pos){
        	this.pos = pos;
        }

        public Position<RBInfo<T>> getTreePosition(){
        	return this.pos;
        }
    	public boolean isRed() {
    		return isRed;
    	}

    	public void setRed() {
    		isRed = true;
    	}

    	public void setBlack() {
    		isRed = false;
    	}

    	public void setColor(boolean color) {
    		isRed = color;
    	}

        public T getElement() {
            return element;
        }

        @Override
        public int compareTo(RBInfo<T> o) {
            if (element instanceof Comparable && o.element instanceof Comparable) {
                Comparable <T> c1 = (Comparable<T>) element;
                return c1.compareTo(o.element);

            } else {
                throw new ClassCastException("Element is not comparable");
            }
        }
    }


    private class RBTreeIterator<T> implements Iterator<Position<T>> {

        private Iterator<Position<RBInfo<T>>> it;

        public RBTreeIterator(Iterator<Position<RBInfo<T>>> iterator) {
            this.it = iterator;
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Position<T> next() {
            Position<RBInfo<T>> aux = it.next();
            return aux.getElement();
        }

        @Override
        public void remove() {
            it.remove();
        }
    }

    private LinkedBinarySearchTree<RBInfo<E>> bst = new LinkedBinarySearchTree<>();
    private ReestructurableBinaryTree<RBInfo<E>> resBT = new ReestructurableBinaryTree<>();

    public RBTree(){
        this(new DefaultComparator<>());
    }


    /**
     * Creates a BinarySearchTree with the given comparator.
     *
     * @param c the comparator used to sort the nodes in the tree
     */
    public RBTree(Comparator<E> c) {
        Comparator<RBInfo<E>> avlComparator = (o1,o2)->c.compare(o1.getElement(), o2.getElement());
        bst = new LinkedBinarySearchTree<>(avlComparator);
        resBT = new ReestructurableBinaryTree<>();
        bst.binTree = resBT;
    }

    @Override
    public Position<E> find(E value) {
        RBInfo<E> searchedValue = new RBInfo<>(value);
        Position<RBInfo<E>> output = bst.find(searchedValue);

        if (output == null) {
            return null;
        }
        return output.getElement();
    }

    @Override
    public Iterable<Position<E>> findAll(E value) {
    	RBInfo<E> searchedValue = new RBInfo<>(value);
    	List<Position<E>> aux = new ArrayList<> ();
        for (Position<RBInfo<E>> n : bst.findAll(searchedValue)){
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
	 * Inserts an element into the RBTree and returns the newly created postion.
	 */
	public Position<E> insert(E e) {
        RBInfo<E> aux = new RBInfo<>(e);

		Position<RBInfo<E>> posZ = bst.insert(aux);
		aux.setTreePosition(posZ);
		aux.setRed();

		if (this.bst.binTree.isRoot(posZ))
			aux.setBlack();
		else
			remedyDoubleRed(aux); // fix a double-red color violation
		return aux;
	}

	/** Remedies a double red violation at a given node caused by insertion. */
	protected void remedyDoubleRed(RBInfo<E> nodeZ) {
		Position<RBInfo<E>> posV = this.bst.binTree.parent(nodeZ.getTreePosition());
		RBInfo<E> nodeV = posV.getElement();

		if (this.bst.binTree.isRoot(posV))
			return;
		if (!nodeV.isRed())
			return;
		// we have a double red: posZ and posV
		Position<RBInfo<E>> uncleZ = this.bst.binTree.sibling(posV);
		final boolean blackUncle = (uncleZ.getElement() == null) || (!uncleZ.getElement().isRed);
		if (blackUncle) { // Case 1: trinode restructuring
			posV = this.resBT.restructure(nodeZ.getTreePosition(), this.bst);
			posV.getElement().setBlack();
			this.bst.binTree.left(posV).getElement().setRed();
			this.bst.binTree.right(posV).getElement().setRed();
		} else { // Case 2: recoloring
			nodeV.setBlack();
			this.bst.binTree.sibling(posV).getElement().setBlack();
			Position<RBInfo<E>> posU = this.bst.binTree.parent(posV);
			if (this.bst.binTree.isRoot(posU))
				return;
			RBInfo<E> nodeU = posU.getElement();
			nodeU.setRed();
			remedyDoubleRed(nodeU);
		}
	}

	/** Removes and returns an entry from the dictionary. */
	public E remove(Position<E> posV) throws RuntimeException {
		RBInfo<E> nodeV = this.checkPosition(posV); // may throw an InvalidPositionException
		boolean VisRed = nodeV.isRed();
        E toReturn = posV.getElement(); // entry to be returned
		//Witness to testify where nodeV originally was...
        Position<RBInfo<E>> leftWitness = this.bst.binTree.left(nodeV.getTreePosition());

        //Swap (if needed) nodeV with a leaf that can be removed
		Position<RBInfo<E>> posW = this.bst.getLeafToRemove(nodeV.getTreePosition());
		Position<RBInfo<E>> posR = this.bst.binTree.sibling(posW);

		//Update colors
        //LeaftToRemove swaps two Tree Positions but doesn't update its colors
        Position<RBInfo<E>> formerPosition = this.bst.binTree.parent(leftWitness);
        if(formerPosition != nodeV) {
            nodeV.setColor(formerPosition.getElement().isRed());
            formerPosition.getElement().setColor(VisRed != this.bst.binTree.isRoot(formerPosition)); //root should be black
        }

        //Base case: posW is a leaf and posR is Red.
        boolean baseCase = false;
        if(this.bst.binTree.isLeaf(posW) && !this.bst.binTree.isLeaf(posR)){
            //posR is red and now is set to black
            posR.getElement().setColor(false);
            baseCase = true;
        }

        //remove posW (leave) and its parent (nodeV)
		this.bst.removeLeaf(posW);
		if (!nodeV.isRed() &&  !baseCase){
			remedyDoubleBlack(posR);
		}



//		if (wasParentRed || this.bst.binTree.isRoot(posR) || isRedNodeR){
//			if (isRedNodeR)
//				nodeR.setBlack();
//		}
//		else {
//			remedyDoubleBlack(posR);
//		}

		return toReturn;
	}

	/** Remedies a double black violation at a given node caused by removal. */
	protected void remedyDoubleBlack(Position<RBInfo<E>> posR) {
		boolean oldColor;
		Position<RBInfo<E>>  posZ;
		RBInfo<E>  nodeZ;
		Position<RBInfo<E>> posX = this.bst.binTree.parent(posR);
		RBInfo<E> nodeX = posX.getElement();
		Position<RBInfo<E>> posY = this.bst.binTree.sibling(posR);
		RBInfo<E> nodeY = posY.getElement();

		final boolean blackNodeY = (nodeY == null) || (!nodeY.isRed());

		if (blackNodeY) {
			posZ = redChild(posY);
			if (posZ != null) { // Case 1: trinode restructuring
				oldColor = nodeX.isRed();
				posZ = this.resBT.restructure(posZ, this.bst);
				posZ.getElement().setColor(oldColor);
				if (posR.getElement() != null)
					posR.getElement().setBlack();
				this.bst.binTree.left(posZ).getElement().setBlack();
				this.bst.binTree.right(posZ).getElement().setBlack();
				return;
			}
			if (posR.getElement() != null)
				posR.getElement().setBlack();
			nodeY.setRed();
			if (!nodeX.isRed()) { // Case 2: recoloring
				if (!this.bst.binTree.isRoot(posX))
					remedyDoubleBlack(posX);
				return;
			}
			nodeX.setBlack();
			return;
		} // Case 3: adjustment
		if (posY == this.bst.binTree.right(posX))
			posZ = this.bst.binTree.right(posY);
		else
			posZ = this.bst.binTree.left(posY);
		this.resBT.restructure(posZ, this.bst);
		nodeY.setBlack();
		nodeX.setRed();
		remedyDoubleBlack(posR);
	}


	/** Returns a red child of a node. */
	protected Position<RBInfo<E>> redChild(Position<RBInfo<E>> pos) {
		Position<RBInfo<E>> child = this.bst.binTree.left(pos);
		final boolean redLeftChild = child.getElement() != null && child.getElement().isRed();
		if (redLeftChild){
			return child;
		}

		child = this.bst.binTree.right(pos);
		final boolean redRightChild = child.getElement() != null && child.getElement().isRed();
		if (redRightChild){
			return child;
		}
		return null;
	}



	@Override
	public Iterator<Position<E>> iterator() {
		Iterator<Position<RBInfo<E>>> bstIt = bst.iterator();
		RBTreeIterator <E> it = new RBTreeIterator<E>(bstIt);
		return it;
	}

	 /**
     * If v is a good tree node, cast to TreePosition, else throw exception
     */
    private RBInfo<E> checkPosition(Position<E> p) throws RuntimeException {
        if (p == null) {
            throw new RuntimeException("The position of the RB node is null");
        } else if (!(p instanceof RBInfo)) {
            throw new RuntimeException("The position of the RB node is not RB");
        } else {
        	RBInfo<E> aux = (RBInfo<E>) p;
            return aux;
        }
    }


	/**
	 * Ejercicio 1: findRange
	 */

	public Iterable<Position<E>> findRange(E minValue, E maxValue) throws RuntimeException{
		if(bst.comparator.compare(new RBInfo<E>(minValue), new RBInfo<E>(maxValue))>0){
			throw new RuntimeException("Invalid range. (min>max)");
		}
		List<Position<E>> range = new ArrayList<>();
		addToRange(range,this.bst.binTree.root().getElement(),minValue,maxValue);
		return range;
	}

	private void addToRange(List<Position<E>> range, Position<E> pos, E minValue, E maxValue){
		if(bst.comparator.compare(checkPosition(pos),new RBInfo<E>(minValue))>=0&&this.bst.binTree.left(checkPosition(pos).getTreePosition()).getElement()!=null){
			addToRange(range,this.bst.binTree.left(checkPosition(pos).getTreePosition()).getElement(),minValue,maxValue);
		}
		if(bst.comparator.compare(checkPosition(pos),new RBInfo<E>(minValue))>=0&&bst.comparator.compare(checkPosition(pos),new RBInfo<E>(maxValue))<=0){
			range.add(pos);
		}
		if(bst.comparator.compare(checkPosition(pos),new RBInfo<E>(maxValue))<=0&&this.bst.binTree.right(checkPosition(pos).getTreePosition()).getElement()!=null){
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
		List<Position<E>> sucesores = new ArrayList<>();
		recursiveSucesores(pos,sucesores,false);
		return sucesores;
	}

	public void recursiveSucesores(Position<E> pos, List<Position<E>> list, boolean fromRight){
		RBInfo<E> aux = checkPosition(pos);
		Position<RBInfo<E>> position = aux.getTreePosition();
		if (!fromRight) {
			list.add(pos);
			if (this.bst.binTree.right(position).getElement() != null) {
				addToListUnder(this.bst.binTree.right(position).getElement(), list, false);
			}
		}
		if (!this.bst.binTree.isRoot(position)) {
			Position<RBInfo<E>> posParent = this.bst.binTree.parent(position);
			Position<E> parent = this.bst.binTree.parent(position).getElement();
			recursiveSucesores(parent, list, this.bst.binTree.right(posParent).equals(position));
		}
	}

	public Iterable<Position<E>> predecessors(Position<E> pos){
		List<Position<E>> predecesor = new ArrayList<>();
		recursivePredecesores(pos,predecesor,false);
		return predecesor;
	}

	public void recursivePredecesores(Position<E> pos, List<Position<E>> list, boolean fromLeft){
		RBInfo<E> aux = checkPosition(pos);
		Position<RBInfo<E>> position = aux.getTreePosition();
		if (!fromLeft) {
			list.add(pos);
			if (this.bst.binTree.left(position).getElement() != null) {
				addToListUnder(this.bst.binTree.left(position).getElement(), list, true);
			}
		}
		if (!this.bst.binTree.isRoot(position)) {
			Position<RBInfo<E>> posParent = this.bst.binTree.parent(position);
			Position<E> parent = this.bst.binTree.parent(position).getElement();
			recursivePredecesores(parent, list, this.bst.binTree.left(posParent).equals(position));
		}
	}

	public void addToListUnder(Position<E> pos, List<Position<E>> list, Boolean lesser){
		RBInfo<E> aux = checkPosition(pos);
		Position<RBInfo<E>> position = aux.getTreePosition();
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


	/**
	 * metodo findRange para cuando el arbol ha sido creado sin el comparador
	 */

	public Iterable<Position<E>> findRangeComp(E minValue, E maxValue, Comparator<E> comparator) throws RuntimeException{
		if(comparator.compare(minValue, maxValue)>0){
			throw new RuntimeException("Invalid range. (min>max)");
		}
		List<Position<E>> range = new ArrayList<>();
		addToRangeComp(range,this.bst.binTree.root().getElement(),minValue,maxValue, comparator);
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

	/* Codigo antiguo: NO CORREGIR
	public Iterable<Position<E>> successors(Position<E> pos){
		RBInfo<E> aux = checkPosition(pos);
		Position<RBInfo<E>> position = aux.getTreePosition();
		List<Position<E>> sucesores = new ArrayList<>();
		if(this.bst.binTree.right(position).getElement()!=null){
			addToListUnder(this.bst.binTree.right(position).getElement(),sucesores, false);
		}
		if(!this.bst.binTree.isRoot(position)) {
			Position<E> parent = this.bst.binTree.parent(position).getElement();
			addToListUppBigger(pos, sucesores, true);
		}
		return sucesores;
	}

	public void addToListUppBigger(Position<E> pos, List<Position<E>> list, Boolean fromRight){
		RBInfo<E> aux = checkPosition(pos);
		Position<RBInfo<E>> position = aux.getTreePosition();
		if(this.bst.binTree.right(position).getElement()!=null&&!fromRight){
			addToListUnder( this.bst.binTree.right(position).getElement(),list,false);
		}
		if(!this.bst.binTree.isRoot(position)) { //si es root no puedo mirar el parent
			Position<RBInfo<E>> parent = this.bst.binTree.parent(position);
			if (this.bst.binTree.right(parent).equals(position)) { //si es hijo derecho
				addToListUppBigger(parent.getElement(), list, true);
			} else {//si no es hijo derecho
				list.add(parent.getElement());
				addToListUppBigger(parent.getElement(), list, false);
			}
		}
	}

	public Iterable<Position<E>> predecessors(Position<E> pos){
		RBInfo<E> aux = checkPosition(pos);
		Position<RBInfo<E>> position = aux.getTreePosition();
		List<Position<E>> predecesores = new ArrayList<>();
		if(this.bst.binTree.left(position).getElement()!=null){
			addToListUnder( this.bst.binTree.left(position).getElement(),predecesores, true);
		}
		if(!this.bst.binTree.isRoot(position)) {
			Position<E> parent = this.bst.binTree.parent(position).getElement();
			addToListUppLesser(pos, predecesores, true);
		}
		Collections.reverse(predecesores);
		return predecesores;
	}

	public void addToListUppLesser(Position<E> pos, List<Position<E>> list, Boolean fromLeft){
		RBInfo<E> aux = checkPosition(pos);
		Position<RBInfo<E>> position = aux.getTreePosition();
		if(this.bst.binTree.left(position).getElement()!=null&&!fromLeft){
			addToListUnder( this.bst.binTree.left(position).getElement(),list,true);
		}
		if(!this.bst.binTree.isRoot(position)) { //si es root no puedo mirar el parent
			Position<RBInfo<E>> parent = this.bst.binTree.parent(position);
			if (this.bst.binTree.left(parent).equals(position)) { //si es hijo izquierdo
				addToListUppLesser(parent.getElement(), list, true);
			} else {//si no es hijo izquierdo
				list.add(parent.getElement());
				addToListUppLesser(parent.getElement(), list, false);
			}
		}
	}


	 */

}
