package material.exam_excercises.Ene2020;

import material.maps.AbstractHashTableMap;
import material.maps.HashTableMapDH;
import material.ordereddictionary.AVLOrderedDict;
import material.ordereddictionary.OrderedDictionary;

import java.util.ArrayList;
import java.util.List;

public class LaResistencia {
    public AbstractHashTableMap<String, List<Visita>> meses;
    public OrderedDictionary<Integer,Visita> dinero;

    public LaResistencia(){
        meses = new HashTableMapDH<>();
        dinero = new AVLOrderedDict<>();
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
        dinero.insert(din,visita);
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

}
