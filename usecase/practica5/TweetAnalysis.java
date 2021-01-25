package usecase.practica5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import material.ordereddictionary.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class TweetAnalysis {

	AbstractTreeOrderedDict<Integer,Tweet> dict;

	public TweetAnalysis(){
		Comparator<Integer> comparator = new TweetValueComparator();
		dict = new BSTOrderedDict<>(comparator);
	}
	/**
	 * Adds a new set of tweets to the tree from the given file
	 */
	public void addFile(String tweetFile) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(tweetFile));
		String line = null;
		String json = "";
		while ((line = bf.readLine()) != null) {
			json += line;
		}
		bf.close();
		JSONObject obj = new JSONObject(json);
		JSONArray tweets = obj.getJSONArray("statuses");
		System.out.println(tweets.length());
		for (int i=0;i<tweets.length();i++) {
			JSONObject t = tweets.getJSONObject(i);
			System.out.println("Tweet "+i+": "+t.get("text"));
			int puntuacion = t.getInt("retweet_count")+t.getInt("favorite_count");
			System.out.println("Tweet "+i+": "+ puntuacion);
			String user = t.getJSONObject("user").getString("screen_name");
			String text = t.getString("text");
			Integer retweets = t.getInt("retweet_count");
			Integer favorite = t.getInt("favorite_count");
			Tweet tweet = new Tweet(user,text,retweets,favorite);
			dict.insert(retweets+favorite,tweet);
		}
	}
	
	/**
	 * Recovers all the tweets with score larger or equal than min and smaller or equal than max
	 */
	public Iterable<Tweet> findTweets(int min, int max) {
		Iterable<Entry<Integer,Tweet>> range = dict.findRangeComp(min, max, new EntryComparator());
		List<Tweet> list = new ArrayList<>();
		for(Entry<Integer,Tweet> entry : range){
			list.add(entry.getValue());
		}
		return list;
	}
	
	/**
	 * Recovers all the tweets with score smaller or equal than percent*MAX_SCORE
	 */
	public Iterable<Tweet> worstTweets(double percent) {
		Integer best = dict.last().getKey();
		Double maxD = best*percent;
		Integer max = maxD.intValue();
		Iterable<Entry<Integer,Tweet>> range = dict.findRangeComp(0, max, new EntryComparator());
		List<Tweet> list = new ArrayList<>();
		for(Entry<Integer,Tweet> entry : range){
			list.add(entry.getValue());
		}
		return list;
	}
	
	/**
	 * Recovers all the tweets with score larger or equal than percent*MAX_SCORE
	 */
	public Iterable<Tweet> bestTweets(double percent) {
		Integer best = dict.last().getKey();
		Double minD = best*percent;
		Integer min = minD.intValue();
		Iterable<Entry<Integer,Tweet>> range = dict.findRangeComp(min, dict.last().getKey(), new EntryComparator());
		List<Tweet> list = new ArrayList<>();
		for(Entry<Integer,Tweet> entry : range){
			list.add(entry.getValue());
		}
		return list;
	}

	private class EntryComparator implements Comparator<Entry<Integer,Tweet>>{
		@Override
		public int compare(Entry<Integer,Tweet> o1, Entry<Integer,Tweet> o2) {
			if(o1.getKey()<o2.getKey()){
				return -1;
			}
			else if(o1.getKey()==o2.getKey()){
				return 0;
			}
			return 1;
		}
	}

}
