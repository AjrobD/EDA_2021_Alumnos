package material.exam_excercises.Ene2014;

public class Viaje {
    private String origen;
    private String destino;
    private int duracion;
    public Viaje(String o, String d, int dur){
        origen = o;
        destino = d;
        duracion = dur;
    }
    public String getOrigen(){
        return origen;
    }
    public String getDestino(){
        return destino;
    }
    public int getDuracion(){
        return duracion;
    }
}
