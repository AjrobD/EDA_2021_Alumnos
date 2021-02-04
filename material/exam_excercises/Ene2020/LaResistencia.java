package material.exam_excercises.Ene2020;

import material.Position;
import material.maps.AbstractHashTableMap;
import material.maps.HashTableMapDH;
import material.ordereddictionary.AVLOrderedDict;
import material.ordereddictionary.OrderedDictionary;
import material.tree.binarysearchtree.AVLTree;
import material.tree.binarysearchtree.BinarySearchTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LaResistencia {
    public AbstractHashTableMap<String, List<Visita>> meses;
    public BinarySearchTree<Visita> dineroTree;

    public LaResistencia(){
        meses = new HashTableMapDH<>();
        dineroTree = new AVLTree<>();
    }

    public void addVisit(String nombre, String mes, Integer din){
        Visita visita = new Visita(nombre,mes,din);
        if(meses.get(mes)!=null){
            List<Visita> visitas = meses.get(mes);
            visitas.add(visita);
        }
        else{
            List<Visita> visitas = new ArrayList<Visita>();
            visitas.add(visita);
            meses.put(mes,visitas);
        }
        dineroTree.insert(visita);
    }

    public Integer moneyInMonth(String mes){
        int dineroTotal = 0;
        for(Visita visita : meses.get(mes)){
            dineroTotal = dineroTotal + visita.getDinero();
        }
        return dineroTotal;
    }

    public Iterable<String> visitInMonth(String mes){
        List<String> famosos = new ArrayList<>();
        for(Visita visita : meses.get(mes)){
            famosos.add(visita.getNombre());
        }
        return famosos;
    }

    public Iterable<String> overMedian(){
        List<String> famosos = new ArrayList<>();
        Iterator<Position<Visita>> it = dineroTree.iterator();
        Integer i = 0;
        Integer mediana = dineroTree.size() /2;
        if(dineroTree.size()%2==1){
            mediana++;
        }
        while(it.hasNext()){
            Position<Visita> visitaPosition = it.next();
            if(i>=mediana){
                famosos.add(visitaPosition.getElement().nombre);
            }
            i++;
        }
        return famosos;
    }

}
