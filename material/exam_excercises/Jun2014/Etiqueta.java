package material.exam_excercises.Jun2014;

public class Etiqueta {
    private String texto;
    private int tamaño;
    private Posicion posicion;
    private Color color;

    public Etiqueta(String texto, int tamaño, Posicion posicion, Color color) {
        this.texto = texto;
        this.tamaño = tamaño;
        this.posicion = posicion;
        this.color = color;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
