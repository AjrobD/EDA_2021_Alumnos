package usecase.practica4;

import material.maps.AbstractHashTableMap;
import material.maps.HashTableMapDH;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PearRegister {
    AbstractHashTableMap<Product, List<PearStore>> mapa;

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
            ArrayList<PearStore> tiendas = new ArrayList<PearStore>();
            while(n2>0){
                line = reader.readLine();
                words = line.split(" ");
                PearStore store = new PearStore(words[0],Integer.parseInt(words[1]),Integer.parseInt(words[2]),Double.parseDouble(words[3]));
                tiendas.add(store);
                n2--;
            }
            mapa.put(producto,tiendas);
            numero--;
        }
    }

    public void addProduct(Product producto, Iterable<PearStore> stores){
        List tiendas = (List) stores;
        mapa.put(producto,tiendas);
    }

    public void addSalesInPearStore(Product producto, PearStore store, int units, double score){
        List<PearStore> tiendas = mapa.get(producto);
        Iterator<PearStore> it = tiendas.iterator();
        Boolean found = false;
        PearStore tienda = null;
        while(it.hasNext()&&!found){
            tienda = it.next();
            found=tienda.equals(store);
        }
        if(tienda!=null) {
            tienda.setUnidades(units);
            tienda.setRating(score);
        }
    }

    public double getScoreOfProduct(Product producto){
        List<PearStore> stores = mapa.get(producto);
        Double totalScore=0.0;
        int n=0;
        for(PearStore store : stores){
            totalScore+=store.getRating();
            n++;
        }
        return totalScore/n;
    }

    public PearStore getGreatestSeller(Product producto){
        List<PearStore> stores = mapa.get(producto);
        int maxUnidades=0;
        PearStore maxTienda = new PearStore();
        for(PearStore store : stores){
            if(store.getUnidades()>maxUnidades){
                maxUnidades = store.getUnidades();
                maxTienda = store;
            }
        }
        return maxTienda;
    }

    public int getUnits(Product producto){
        List<PearStore> stores = mapa.get(producto);
        int totalUnits=0;
        for(PearStore store : stores){
            totalUnits+= store.getUnidades();
        }
        return totalUnits;
    }

    public boolean productExists(Product product){
        List<PearStore> stores = mapa.get(product);
        return stores!=null;
    }
}
