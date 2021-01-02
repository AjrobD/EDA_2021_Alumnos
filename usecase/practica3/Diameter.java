package usecase.practica3;

import material.Position;
import material.tree.binarytree.BinaryTree;

import java.util.HashSet;
import java.util.Set;

public class Diameter {
	public int evalDiameter(BinaryTree<Integer> tree, Position<Integer> v1, Position<Integer> v2) {
		Set<Position<Integer>> set = new HashSet<>();
		Position<Integer> nAux1 = v1;
		Position<Integer> nAux2 = v2;
		Boolean contiene = false;
		if(nAux1==null&&nAux2==null){
			return 0;
		}
		else if (nAux1==nAux2){
			return 1;
		}
		int contador = 0;
		while(!tree.isRoot(nAux1)){
			set.add(nAux1);
			nAux1 = tree.parent(nAux1);
		}
		set.add(nAux1);
		boolean found = set.contains(nAux2);
		while (!tree.isRoot(nAux2) && !found) {
			contador++;
			nAux2 = tree.parent(nAux2);
			found = set.contains(nAux2);
		}
		while (!tree.isRoot(nAux2)) {
			contador--;
			nAux2 = tree.parent(nAux2);
		}
		return set.size()+contador;
	}
}
