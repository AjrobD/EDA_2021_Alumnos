package material.exam_excercises.Dic2014;

import material.linear.List;
import material.maps.AbstractHashTableMap;
import material.maps.Entry;
import material.maps.HashTableMapDH;
import usecase.practica2.GameOfThrones;

import java.util.Hashtable;
import java.util.Set;

public class Traductor {
    private AbstractHashTableMap<String, AbstractHashTableMap<String,String>> mapa;

    public Traductor(){
        mapa = new HashTableMapDH<>();
    }

    public void añadir(String frase, String traducida, String idioma){
        AbstractHashTableMap<String,String> traducciones = mapa.get(frase);
        if(traducciones!=null){
            AbstractHashTableMap<String,String> nuevaTraduccion = new HashTableMapDH<>();
            nuevaTraduccion.put(idioma,traducida);
            mapa.put(frase,nuevaTraduccion);
        }
        else{
            traducciones.put(idioma,traducida);
        }
    }

    public String traducir(String frase, String idioma){
        AbstractHashTableMap<String,String> traducciones = mapa.get(frase);
        if(traducciones !=null ){
            return null;
        }
        String traduccion = traducciones.get(idioma);
        return traduccion;
    }

    public Iterable<Entry<String, String>> traducciones(String frase){
        AbstractHashTableMap<String,String> traducciones = mapa.get(frase);
        if(traducciones!=null){
            return null;
        }
        return traducciones.entries();
    }
}
