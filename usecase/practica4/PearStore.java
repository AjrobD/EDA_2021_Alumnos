package usecase.practica4;

import java.util.Objects;

public class PearStore{
    String nombre;
    int id;

    public PearStore(){
        nombre="";
        id=0;
    }

    public PearStore(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PearStore pearStore = (PearStore) o;
        return id == pearStore.id &&
                Objects.equals(nombre, pearStore.nombre);
    }

}
