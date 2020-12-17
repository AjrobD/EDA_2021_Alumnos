package material.maps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Separate chaining table implementation of hash tables. Note that all
 * "matching" is based on the equals method.
 *
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro, Sergio Pérez
 */
public class HashTableMapSC<K, V> implements Map<K, V> {
    //TODO: Ejercicio para los alumnos, implementar todo

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
            value=val;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (o.getClass() != this.getClass()) {
                return false;
            }
            HashEntry<T,U> ent;
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

    private class HashTableMapIterator<T, U> implements Iterator<Entry<T, U>> {
        private int posMap;
        private int posList;
        private ArrayList<HashEntry<T, U>>[] bucket;

        public HashTableMapIterator(ArrayList<HashEntry<T, U>>[] b, int numElems) {
            this.bucket = b;
            if (numElems == 0) {
                this.posMap = bucket.length;
            } else {
                this.posMap = 0;
                this.posList = 0;
                goToNextElement(0,0);
            }
        }

        private void goToNextElement(int startMap, int startList) {
            this.posMap = startMap;
            this.posList = startList;
            while ((this.posMap < bucket.length)&&(this.bucket[this.posMap] == null)) {
                this.posMap++;
            }
            while ((this.posMap < bucket.length) && this.posList < bucket[this.posMap].size() && bucket[posMap].get(posList) == null) {
                this.posList++;
            }
        }

        @Override
        public boolean hasNext() {
            return posMap<bucket.length && posList < bucket[posMap].size();
        }

        @Override
        public Entry<T, U> next() {
            if (hasNext()) {
                int currentPosMap = posMap;
                int currentPosList = posList;
                if(posList+1 >= bucket[posMap].size()){
                    goToNextElement(posMap+1,0);
                }
                else{
                    goToNextElement(posMap,posList+1);
                }
                return this.bucket[currentPosMap].get(currentPosList);
            } else {
                throw new IllegalStateException("The map has not more elements");
            }
        }

        @Override
        public void remove() {
            //NO ES NECESARIO IMPLEMENTARLO
            throw new UnsupportedOperationException("Not implemented.");

        }

        /**
         * Returns the index of the next position starting starting from a given index.
         * (if the parameter is already a valid position then does nothing)
         */
        private int goToNextBucket(int i) {
            while(i<bucket.length && bucket[i]==null){
                i++;
            }
            return i;
        }
    }

    private class HashTableMapKeyIterator<T, U> implements Iterator<T> {
        public HashTableMapIterator<T, U> it;

        public HashTableMapKeyIterator(HashTableMapIterator<T, U> it) {
            this.it = it;
        }

        @Override
        public T next() {
            return it.next().getKey();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public void remove() {
            //NO ES NECESARIO IMPLEMENTARLO
            throw new RuntimeException("Not yet implemented");
        }
    }

    private class HashTableMapValueIterator<T, U> implements Iterator<U> {
        public HashTableMapIterator<T, U> it;

        public HashTableMapValueIterator(HashTableMapIterator<T, U> it) {
            this.it = it;
        }

        @Override
        public U next() {
            return it.next().getValue();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    protected class HashEntryIndex {

        int indexMap;
        int indexList;
        boolean found;

        public HashEntryIndex(int indexMap, int indexList, boolean f) {
            this.indexMap = indexMap;
            this.indexList = indexList;
            this.found = f;
        }

        //Easy visualization
        @Override
        public String toString() {
            return "(" + this.indexMap+ ", " + this.indexList + ", " + this.found + ")";
        }
    }

    protected int n; // number of entries in the dictionary
    protected int prime, capacity; // prime factor and capacity of bucket array
    protected long scale, shift; // the shift and scaling factors
    protected ArrayList<HashEntry<K,V>>[] bucket;// bucket array of Lists

    /**
     * Creates a hash table with prime factor 109345121 and capacity 1000.
     */
    public HashTableMapSC() {
        n=0;
        prime=109345121;
        capacity=1000;
        bucket = (ArrayList<HashEntry<K, V>>[]) new ArrayList[capacity];
        Random rand = new Random();
        this.scale = rand.nextInt(prime - 1) + 1;
        this.shift = rand.nextInt(prime);
    }

    /**
     * Creates a hash table with prime factor 109345121 and given capacity.
     *
     * @param cap initial capacity
     */
    public HashTableMapSC(int cap) {
        n=0;
        prime=109345121;
        capacity=cap;
        bucket = (ArrayList<HashEntry<K, V>>[]) new ArrayList[capacity];
        Random rand = new Random();
        this.scale = rand.nextInt(prime - 1) + 1;
        this.shift = rand.nextInt(prime);
    }

    /**
     * Creates a hash table with the given prime factor and capacity.
     *
     * @param p   prime number
     * @param cap initial capacity
     */
    public HashTableMapSC(int p, int cap) {
        n=0;
        prime=p;
        capacity=cap;
        bucket = (ArrayList<HashEntry<K, V>>[]) new ArrayList[capacity];
        Random rand = new Random();
        this.scale = rand.nextInt(prime - 1) + 1;
        this.shift = rand.nextInt(prime);
    }

    /**
     * Hash function applying MAD method to default hash code.
     *
     * @param key Key
     * @return the hash value
     */
    protected int hashValue(K key) {
        return (int) ((scale*Math.abs(key.hashCode())+shift)%prime)%capacity;
    }


    @Override
    public int size() {
        return n;
    }

    @Override
    public boolean isEmpty() {
        return n==0;
    }

    @Override
    public V get(K key) {
        HashEntryIndex i = findEntry(key);
        if(i.found){
            return bucket[i.indexMap].get(i.indexList).getValue();
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        HashEntryIndex i = findEntry(key);
        if(i.found){
            return bucket[i.indexMap].get(i.indexList).setValue(value);
        }
        if(n >= capacity*3/4){
            rehash(capacity*2);
            i = findEntry(key);
        }
        HashEntry<K,V> newEntry = new HashEntry<K,V>(key,value);
        if(bucket[i.indexMap]==null){
            bucket[i.indexMap] = new ArrayList<>();
        }
        bucket[i.indexMap].add(newEntry);
        n++;
        return null;
    }

    @Override
    public V remove(K key) {
        HashEntryIndex i = findEntry(key);
        if(!i.found){
            return null;
        }
        n--;
        V toReturn = bucket[i.indexMap].remove(i.indexList).getValue();
        if(bucket[i.indexMap].size()==0){
            bucket[i.indexMap]=null;
        }
        return toReturn;
    }


    @Override
    public Iterator<Entry<K, V>> iterator() {
        Iterator<Entry<K, V>> it = new HashTableMapIterator(this.bucket,n);
        return it;

    }

    @Override
    public Iterable<K> keys() {
        return new Iterable<K>() {
            public Iterator<K> iterator() {
                return new HashTableMapKeyIterator<K, V>(new HashTableMapIterator<K,V>(bucket, n));
            }
        };
    }

    @Override
    public Iterable<V> values() {
        return new Iterable<V>() {
            public Iterator<V> iterator() {
                return new HashTableMapValueIterator<K, V>(new HashTableMapIterator<K,V>(bucket, n));
            }
        };
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        return new Iterable<Entry<K, V>>() {
            public Iterator<Entry<K, V>> iterator() {
                return new HashTableMapIterator<K,V>(bucket, n);
            }
        };
    }

    /**
     * Determines whether a key is valid.
     *
     * @param k Key
     */
    protected void checkKey(K k) throws IllegalStateException{
        if (k == null) {
            throw new IllegalStateException("Invalid key: null.");
        }
    }


    /**
     * Increase/reduce the size of the hash table and rehashes all the entries.
     */
    protected void rehash(int newCap) {
        ArrayList<HashEntry<K,V>>[] old = bucket;
        bucket = (ArrayList<HashEntry<K, V>>[]) new ArrayList[newCap];
        Random rand = new Random();
        scale = rand.nextInt(prime-1)+1;
        shift = rand.nextInt(prime);
        for(ArrayList<HashEntry<K,V>> l : old){
            if (l != null) {
                for(HashEntry<K,V> e : l){
                    if (e != null) {
                        int indexMap = findEntry(e.getKey()).indexMap;
                        if(bucket[indexMap]==null){
                            bucket[indexMap] = new ArrayList<>();
                        }
                        bucket[indexMap].add(e);
                    }
                }
            }
        }
    }

    /**
     * Looks for a given key in the map an returns a HashEntryIndex object
     * with information about where the key was found (if found) or which
     * is the first free location where it could be placed (if not found).
     *
     * @param key the key we are looking for
     * @return A HashEntryIndex
     * @throws IllegalStateException for a null key
     */
    protected HashEntryIndex findEntry(K key) throws IllegalStateException{
        checkKey(key);
        int hashcode = hashValue(key);
        int posList = 0;
        while( bucket[hashcode] !=null && posList<bucket[hashcode].size() && !bucket[hashcode].get(posList).getKey().equals(key)){
            posList++;
        }
        if(bucket[hashcode] == null || posList>=bucket[hashcode].size()){
            return new HashEntryIndex(hashcode,posList,false);
        }
        else{
            return new HashEntryIndex(hashcode,posList,true);
        }
    }
}
