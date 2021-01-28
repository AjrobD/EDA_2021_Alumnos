package usecase.practica5;

import java.util.Comparator;

public class TweetValueComparator implements Comparator<TreeEntry> {

    TweetValueComparator(){super();};

    @Override
    public int compare(TreeEntry o1, TreeEntry o2) {
        if(o1.getPuntuacion()<o2.getPuntuacion()){
            return -1;
        }
        else if(o1.getPuntuacion()==o2.getPuntuacion()){
            return 0;
        }
        return 1;
    }
}
