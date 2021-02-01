package material.exam_excercises.Jun2015;

import material.maps.AbstractHashTableMap;
import material.maps.Entry;
import material.maps.HashTableMapDH;
import material.maps.HashTableMapSC;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class HashTableMapES<K,V> {
    private class HashEntry<T, U> implements Entry<T, U> {

        protected T key;
        protected U value;

        public HashEntry(T k, U v) {
            key = k;
            value = v;
        }

        @Override
        public U getValue() {
            return value;
        }

        @Override
        public T getKey() {
            return key;
        }

        public U setValue(U val) {
            U oldValue = value;
            value = val;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {

            if (o.getClass() != this.getClass()) {
                return false;
            }

            HashEntry<T, U> ent;
            try {
                ent = (HashEntry<T, U>) o;
            } catch (ClassCastException ex) {
                return false;
            }
            return (ent.getKey().equals(this.key))
                    && (ent.getValue().equals(this.value));
        }

        /**
         * Entry visualization.
         */
        @Override
        public String toString() {
            return "(" + key + "," + value + ")";
        }
    }

    protected int n; // number of entries in the dictionary
    protected int prime, capacity; // prime factor and capacity of bucket array
    protected long scale, shift; // the shift and scaling factors
    protected ArrayList<HashEntry<K, V>>[] bucket;// bucket array

    public class MapIterator<K,V> implements Iterator<Entry<K,V>>{
        private int posList;
        private int posMap;
        private ArrayList<HashEntry<K, V>>[] bucket;

        public MapIterator(ArrayList<HashEntry<K, V>>[] bucket, int numElems){
            bucket = bucket;
            if(numElems==0){
                posMap=bucket.length;
            }
            else{
                goToNextElem(0,0);
            }

        }

        private void goToNextElem(int startMap, int startList) {
            posMap = startMap;
            posList = startList;
            while((this.posMap < bucket.length)&&bucket[posMap]==null){
                posMap++;
            }
            while ((this.posMap < bucket.length) && this.posList < bucket[this.posMap].size() && bucket[posMap].get(posList) == null){
                posList++;
            }
        }

        @Override
        public boolean hasNext() {
            return posMap<bucket.length && posList<bucket[posMap].size();
        }

        @Override
        public Entry<K, V> next() {
            int currentPosMap = posMap;
            int currentPosList = posList;
            if(posList+1 >= bucket[posMap].size()){
                goToNextElem(posMap+1,0);
            }
            else{
                goToNextElem(posMap,posList+1);
            }
            return this.bucket[currentPosMap].get(currentPosList);
        }
    }

    protected HashTableMapES() {
        this(109345121, 1000);
    }
    protected HashTableMapES(int p, int cap) {
        this.n = 0;
        this.prime = p;
        this.capacity = cap;
        this.bucket = (ArrayList<HashEntry<K, V>>[]) new ArrayList[capacity];
        Random rand = new Random();
        this.scale = rand.nextInt(prime - 1) + 1;
        this.shift = rand.nextInt(prime);
    }


}
