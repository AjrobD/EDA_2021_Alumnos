package material.exam_excercises.Ene2020;


import material.Position;
import material.tree.binarytree.ArrayBinaryTree;
import material.tree.binarytree.BinaryTree;

public class Main {
    public static void main(String[] args){
        LaResistencia resis = new LaResistencia();
        resis.addVisit("M.Rajoy","Marzo",12000);
        resis.addVisit("Antonio Larroca","Junio",500000);
        resis.addVisit("Jorge ENP","Junio",20000);
        resis.addVisit("Gerardo Picado","Septiembre",1000000);
        resis.addVisit("Santi Caballo","Diciembre",75000);
        resis.addVisit("Horse Luis","Septiembre",10000000);

        System.out.println(resis.overMedian());
        System.out.println(resis.moneyInMonth("Septiembre"));
        System.out.println(resis.visitInMonth("Septiembre"));

        BinaryTree<Integer> tree;
        Position<Integer>[] pos;

        tree = new ArrayBinaryTree<>();
        pos = new Position[16];
        pos[1] = tree.addRoot(1);
        pos[2] = tree.insertLeft(pos[1],2 );
        pos[3] = tree.insertRight(pos[1],3 );
        pos[5] = tree.insertRight(pos[2],5 );
        pos[6] = tree.insertLeft(pos[3],6);
        pos[7] = tree.insertRight(pos[3],7);
        pos[12] = tree.insertLeft(pos[6],12 );
        pos[13] = tree.insertRight(pos[6],13 );

        Half<Integer> half = new Half();
        for(Position<Integer> p : half.findHalf(tree)){
            System.out.println(p.getElement());
        }
    }
}
