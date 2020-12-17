package material.maps;

/**
 * @param <K> The hey
 * @param <V> The stored value
     */
public class HashTableMapQP<K, V> extends AbstractHashTableMap<K, V> {

    public HashTableMapQP(int size) {
        super(size);
    }

    public HashTableMapQP() {
        super();
    }

    public HashTableMapQP(int p, int cap) {
        super(p,cap);
    }

    @Override
    protected int offset(K key, int i) {
        int c1 = 2;
        int c2 = 3;
        return c1*i+c2*i^2;
        /*Random ran = new Random();
        int c1 = ran.nextInt(5);
        int c2 = ran.nextInt(4)+1;*/

    }

}
