package usecase.practica4;

import java.util.Objects;

public class Product{
    String nombre;
    int año;

    public Product(String nombre, int año) {
        this.nombre = nombre;
        this.año = año;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return año == product.año &&
                Objects.equals(nombre, product.nombre);
    }
}
