package material.exam_excercises.Ene2014;

import material.maps.AbstractHashTableMap;
import material.maps.HashTableMapDH;
import material.tree.narytree.LinkedTree;

import java.util.*;

public class GestorViajes {
    private AbstractHashTableMap<String, List<Viaje>> ciudadesOrigen;
    private AbstractHashTableMap<String,List<Viaje>> ciudadesDestino;
    private List<Viaje> viajes;
    private Set<String> ciudades;

    public GestorViajes(){
        ciudadesOrigen = new HashTableMapDH<>();
        ciudadesDestino = new HashTableMapDH<>();
        viajes = new ArrayList<>();
        ciudades = new HashSet<>();
    }

    public void insertarViaje(Viaje viaje){
        viajes.add(viaje);
        addCiudadesOrigen(viaje.getOrigen(),viaje);
        addCiudadesDestino(viaje.getDestino(),viaje);

        ciudades.add(viaje.getDestino());
        ciudades.add(viaje.getOrigen());
    }

    private void addCiudadesDestino(String destino, Viaje viaje) {
        List<Viaje> destinos = ciudadesDestino.get(destino);
        if(destinos.isEmpty()){
            List<Viaje> nuevaCiudad = new ArrayList<>();
            nuevaCiudad.add(viaje);
            ciudadesDestino.put(destino,nuevaCiudad);
        }
        else{
            destinos.add(viaje);
        }
    }

    private void addCiudadesOrigen(String origen, Viaje viaje) {
        List<Viaje> origenes = ciudadesOrigen.get(origen);
        if(origenes.isEmpty()){
            List<Viaje> nuevaCiudad = new ArrayList<>();
            nuevaCiudad.add(viaje);
            ciudadesOrigen.put(origen,nuevaCiudad);
        }
        else{
            origenes.add(viaje);
        }
    }

    public Iterable<Viaje> getDestinos(String ciudad) {
        return ciudadesDestino.get(ciudad);
    }

    public Iterable<Viaje> getOrigenes(String ciudad){
        return ciudadesOrigen.get(ciudad);
    }

    public List<Viaje> getViajes(){
        return viajes;
    }

    public Set<String> getCiudades(){
        return ciudades;
    }


}
