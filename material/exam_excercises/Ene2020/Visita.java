package material.exam_excercises.Ene2020;

import java.util.Objects;

public class Visita implements Comparable{
    public String nombre;
    public String mes;
    public int dinero;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    public Visita(String nombre, String mes, int dinero) {
        this.nombre = nombre;
        this.mes = mes;
        this.dinero = dinero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visita visita = (Visita) o;
        return dinero == visita.dinero &&
                nombre.equals(visita.nombre) &&
                mes.equals(visita.mes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, mes, dinero);
    }

    @Override
    public int compareTo(Object o) {
        Visita visita = (Visita) o;
        if(dinero< visita.getDinero()){
            return -1;
        }
        else if(dinero== visita.getDinero()){
            return 0;
        }
        else{
            return 1;
        }
    }
}
