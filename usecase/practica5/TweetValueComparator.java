package usecase.practica5;

import java.util.Comparator;

public class TweetValueComparator implements Comparator<Integer> {

    TweetValueComparator(){super();};

    @Override
    public int compare(Integer o1, Integer o2) {
        if(o1<o2){
            return -1;
        }
        else if(o1==o2){
            return 0;
        }
        return 1;
    }
}
