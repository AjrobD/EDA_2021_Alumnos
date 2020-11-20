package usecase.practica3;

import material.Position;
import material.tree.binarytree.BinaryTree;

import java.util.HashSet;
import java.util.Set;

public class Diameter {
	/*
	public int evalDiameter(BinaryTree<Integer> tree, Position<Integer> v1, Position<Integer> v2) {
		Position<Integer> nAux = v1;
		int cont = 1;
		while(nAux!=null){
			Pair<Boolean,Integer> par = isAncestor(tree,nAux,v2);
			if(par.getKey()){
				return cont+par.getValue();
			}
			cont++;
			nAux = tree.parent(nAux);
		}
		return 0;
	}

	public Pair<Boolean,Integer> isAncestor(BinaryTree<Integer> tree, Position<Integer> v1, Position<Integer> v2){
		int cont = 1;
		Position<Integer> nAux = v2;
		while(!tree.isRoot(nAux)&&tree.parent(nAux)!=null){
			if(tree.parent(nAux)==v1){
				return new Pair<>(true,cont);
			}
			nAux = tree.parent(nAux);
			cont++;
		}
		return new Pair<>(false,0);
	}
	*/
	public int evalDiameter(BinaryTree<Integer> tree, Position<Integer> v1, Position<Integer> v2) {
		Set<Position<Integer>> set = new HashSet<>();
		Position<Integer> nAux1 = v1;
		Position<Integer> nAux2 = v2;
		Boolean contiene = false;
		if(nAux1==null&&nAux2==null){
			return 0;
		}
		while(nAux1!=nAux2&&!contiene){
			set.add(nAux1);
			if(!tree.isRoot(nAux1)){
				nAux1 = tree.parent(nAux1);
			}
			set.add(nAux2);
			if(!tree.isRoot(nAux2)){
				nAux2 = tree.parent(nAux2);
			}
			contiene = set.contains(nAux1)||set.contains(nAux2);
		}
		if(nAux1==nAux2){
			if(!set.contains(nAux1)){
				return set.size()+1;
			}
		}
		return set.size();
	}
}
