package usecase.practica5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import material.Position;
import material.ordereddictionary.*;
import material.tree.Tree;
import material.tree.binarysearchtree.BinarySearchTree;
import material.tree.binarysearchtree.LinkedBinarySearchTree;
import org.json.JSONArray;
import org.json.JSONObject;

public class TweetAnalysis {

	BinarySearchTree<TreeEntry> tree;

	public TweetAnalysis(){
		Comparator<TreeEntry> comparator = new TweetValueComparator();
		tree = new LinkedBinarySearchTree<TreeEntry>(comparator);
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
			TreeEntry entry = new TreeEntry(retweets+favorite,tweet);
			tree.insert(entry);
		}
	}
	
	/**
	 * Recovers all the tweets with score larger or equal than min and smaller or equal than max
	 */
	public Iterable<Tweet> findTweets(int min, int max) {
		TreeEntry minEntry = new TreeEntry(min);
		TreeEntry maxEntry = new TreeEntry(max);
		Iterable<Position<TreeEntry>> range = tree.findRange(minEntry, maxEntry);
		List<Tweet> list = new ArrayList<>();
		for(Position<TreeEntry> entry : range){
			list.add(entry.getElement().getTweet());
		}
		return list;
	}
	
	/**
	 * Recovers all the tweets with score smaller or equal than percent*MAX_SCORE
	 */
	public Iterable<Tweet> worstTweets(double percent) {
		Integer best = tree.last().getElement().getPuntuacion();
		Double maxD = best*percent;
		Integer max = maxD.intValue();
		TreeEntry minEntry = new TreeEntry(0);
		TreeEntry maxEntry = new TreeEntry(max);
		Iterable<Position<TreeEntry>> range = tree.findRange(minEntry, maxEntry);
		List<Tweet> list = new ArrayList<>();
		for(Position<TreeEntry> entry : range){
			list.add(entry.getElement().getTweet());
		}
		return list;
	}
	
	/**
	 * Recovers all the tweets with score larger or equal than percent*MAX_SCORE
	 */
	public Iterable<Tweet> bestTweets(double percent) {
		Integer best = tree.last().getElement().getPuntuacion();
		Double minD = best*percent;
		Integer min = minD.intValue();
		TreeEntry minEntry = new TreeEntry(min);
		TreeEntry maxEntry = new TreeEntry(best);
		Iterable<Position<TreeEntry>> range = tree.findRange(minEntry, maxEntry);
		List<Tweet> list = new ArrayList<>();
		for(Position<TreeEntry> entry : range){
			list.add(entry.getElement().getTweet());
		}
		return list;
	}

}
