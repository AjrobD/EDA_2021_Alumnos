package usecase.practica4;

import material.maps.AbstractHashTableMap;
import material.maps.HashTableMapDH;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PearRegister {
    private class Entrada{
        private Product producto;
        private PearStore tienda;
        private int unidades;
        private Double rating;

        public Product getProducto() {
            return producto;
        }

        public void setProducto(Product producto) {
            this.producto = producto;
        }

        public PearStore getTienda() {
            return tienda;
        }

        public void setTienda(PearStore tienda) {
            this.tienda = tienda;
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

        public int hashCode(){
            int prod=0;
            switch(producto.getNombre())  {
                case "MakBukPro":
                    prod=1;
                    break;
                case "yMak":
                    prod=2;
                    break;
                case "yFon":
                    prod=3;
                    break;
                case "NewYMak":
                    prod=4;
                    break;
            }
            return tienda.getId()*prod*producto.getAño();
        }

        public Entrada(Product producto, PearStore tienda, int unidades, Double rating) {
            this.producto = producto;
            this.tienda = tienda;
            this.unidades = unidades;
            this.rating = rating;
        }

        public Entrada(Product producto, PearStore tienda) {
            this.producto = producto;
            this.tienda = tienda;
            this.unidades = 0;
            this.rating = 0.0;
        }
    }
    AbstractHashTableMap<Integer,Entrada> mapa;

    public PearRegister(){
        mapa = new HashTableMapDH<>();
    }

    public void loadFile(String pathToFile) throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(pathToFile));
        int numero = Integer.parseInt(reader.readLine());
        while(numero>0){
            String line = reader.readLine();
            String[] words = line.split(" ");
            Product producto = new Product(words[0],Integer.parseInt(words[1]));
            int n2 = Integer.parseInt(words[2]);
            while(n2>0){
                line = reader.readLine();
                words = line.split(" ");
                PearStore store = new PearStore(words[0],Integer.parseInt(words[1]));
                Entrada Entrada = new Entrada(producto,store,Integer.parseInt(words[2]),Double.parseDouble(words[3]));
                mapa.put(Entrada.hashCode(),Entrada);
                n2--;
            }
            numero--;
        }
    }

    public void addProduct(Product producto, Iterable<PearStore> stores){
        for(PearStore store: stores){
            Entrada entrada = new Entrada(producto,store);
            mapa.put(entrada.hashCode(),entrada);
        }
    }

    public void addSalesInPearStore(Product producto, PearStore store, int units, double score){
        Entrada fakeEntrada = new Entrada(producto,store);
        Entrada Entrada = mapa.get(fakeEntrada.hashCode());
        Entrada.setUnidades(units);
        Entrada.setRating(score);
    }

    public double getScoreOfProduct(Product producto){
        Iterable<Entrada> entries =mapa.values();
        Double totalScore=0.0;
        int n=0;
        for(Entrada entrada : entries){
            if(entrada.getProducto().equals(producto)){
                totalScore+=entrada.getRating();
                n++;
            }
        }
        return totalScore/n;
    }

    public PearStore getGreatestSeller(Product producto){
        Iterable<Entrada> entries =mapa.values();
        int maxUnidades=0;
        PearStore maxTienda = new PearStore();
        for(Entrada entrada : entries){
            if(entrada.getProducto().equals(producto)){
                if(entrada.getUnidades()>maxUnidades){
                    maxUnidades = entrada.getUnidades();
                    maxTienda = entrada.getTienda();
                }
            }
        }
        return maxTienda;
    }

    public int getUnits(Product producto){
        Iterable<Entrada> entries =mapa.values();
        int totalUnits=0;
        for(Entrada entrada : entries){
            if(entrada.getProducto().equals(producto)){
                totalUnits+= entrada.getUnidades();
            }
        }
        return totalUnits;
    }

    public boolean productExists(Product product){
        Iterable<Entrada> entries =mapa.values();
        for(Entrada entrada : entries){
            if(entrada.getProducto().equals(product)){
                return true;
            }
        }
        return false;
    }
}
