package material.exam_excercises.Ene2020;

public class Visita {
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
}
