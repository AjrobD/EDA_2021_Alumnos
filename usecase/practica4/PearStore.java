package usecase.practica4;

import java.util.Objects;

public class PearStore{
    String nombre;
    int id;
    private int unidades;
    private Double rating;

    public PearStore(){
        nombre="";
        id=0;
    }

    public PearStore(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
        unidades=0;
        rating=0.0;
    }

    public PearStore(String nombre, int id, int unidades, Double rating) {
        this.nombre = nombre;
        this.id = id;
        this.unidades = unidades;
        this.rating = rating;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PearStore pearStore = (PearStore) o;
        return id == pearStore.id &&
                Objects.equals(nombre, pearStore.nombre);
    }

}
