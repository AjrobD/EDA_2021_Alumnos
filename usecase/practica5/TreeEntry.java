package usecase.practica5;

public class TreeEntry{
    private int puntuacion;
    private Tweet tweet;

    public TreeEntry(int puntuacion, Tweet tweet) {
        this.puntuacion = puntuacion;
        this.tweet = tweet;
    }

    public TreeEntry(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }
}
