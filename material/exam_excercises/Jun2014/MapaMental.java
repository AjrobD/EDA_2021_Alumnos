package material.exam_excercises.Jun2014;

import material.Position;
import material.tree.narytree.LCRSTree;
import material.tree.narytree.LinkedTree;
import material.tree.narytree.NAryTree;

import java.util.ArrayList;
import java.util.Iterator;

public class MapaMental {
    private NAryTree<Etiqueta> mapa;

    public MapaMental(){
        mapa = new LinkedTree<Etiqueta>();
    }

    public void insertLabel(Position<Etiqueta> pos, Etiqueta etiqueta){
        mapa.add(etiqueta, pos);
    }

    public void removeLabel(Position<Etiqueta> pos){
        mapa.remove(pos);
    }

    public Position<Etiqueta> centralLabel(){
        return mapa.root();
    }
     public Iterable<Etiqueta> labels(Position<Etiqueta> etiqueta){
         Iterator<Position<Etiqueta>> iterator = mapa.iterator();
         ArrayList<Etiqueta> entregar = new ArrayList<>();
         recursiveLabel(entregar,etiqueta);
         return entregar;
     }

    private void recursiveLabel(ArrayList<Etiqueta> entregar, Position<Etiqueta> etiqueta) {
        entregar.add(etiqueta.getElement());
        for(Position<Etiqueta> child : mapa.children(etiqueta)){
            recursiveLabel(entregar,child);
        }
    }

}
