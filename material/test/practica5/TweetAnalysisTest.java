package material.test.practica5;

import org.junit.jupiter.api.Test;
import usecase.practica4.PearRegister;
import usecase.practica5.Tweet;
import usecase.practica5.TweetAnalysis;

import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


class TweetAnalysisTest {
    private static TweetAnalysis tweets;

    @org.junit.jupiter.api.BeforeAll
    static void addFile() {
        tweets = new TweetAnalysis();
        String[] paths = {
                "C:/Users/Usuario/Desktop/URJC/Primer Cuatrimestre/EDA/EDA_2021_Alumnos/usecase/practica5/tweets/darksouls.json",
                "C:/Users/Usuario/Desktop/URJC/Primer Cuatrimestre/EDA/EDA_2021_Alumnos/usecase/practica5/tweets/eclipse.json",
                "C:/Users/Usuario/Desktop/URJC/Primer Cuatrimestre/EDA/EDA_2021_Alumnos/usecase/practica5/tweets/infinitywar.json",
                "C:/Users/Usuario/Desktop/URJC/Primer Cuatrimestre/EDA/EDA_2021_Alumnos/usecase/practica5/tweets/playstation.json",
                "C:/Users/Usuario/Desktop/URJC/Primer Cuatrimestre/EDA/EDA_2021_Alumnos/usecase/practica5/tweets/puigdemont.json",
                "C:/Users/Usuario/Desktop/URJC/Primer Cuatrimestre/EDA/EDA_2021_Alumnos/usecase/practica5/tweets/urjc.json"
        };
        for(String path: paths){
            try {
                tweets.addFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void findTweets(){
        int cont = 0;
        for(Tweet tweet :tweets.findTweets(50,10000)){
            assert(tweet.getRetweets()+tweet.getFavorite()>=50&&tweet.getRetweets()+ tweet.getFavorite()<=10000);
            cont++;
        }
        System.out.println(cont);
    }

    @Test
    void worstTweets(){
        int cont = 0;
        for(Tweet tweet :tweets.worstTweets(0.1)){
            assert(tweet.getRetweets()+tweet.getFavorite()<=12172*0.1);
            cont++;
        }
        System.out.println(cont);
    }

    @Test
    void bestTweets(){
        int cont = 0;
        for(Tweet tweet :tweets.bestTweets(0.1)){
            assert(tweet.getRetweets()+tweet.getFavorite()>=12172*0.1);
            cont++;
        }
        System.out.println(cont);
    }

}
