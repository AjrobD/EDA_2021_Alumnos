package material.exam_excercises.Oct2020;

import material.Position;
import material.tree.binarytree.BinaryTree;
import material.tree.binarytree.LinkedBinaryTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GuessWho {

    public void loadGame(String path) {
        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
            String line = null;
            while ((line = bf.readLine()) != null) {
                // Process the line read
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Initializes an iterator through the game, starting at
    the first question
     */
    public void resetIterator() {
        throw new RuntimeException("Not yet implemented");
    }

    /*
    Returns true if there are more question to be read
    by the iterator, or false otherwise
     */
    public boolean hasMoreQuestions() {
        throw new RuntimeException("Not yet implemented");
    }

    /*
    Returns the next question to be read from the game
     */
    public String nextQuestion() {
        throw new RuntimeException("Not yet implemented");
    }

    /*
    Returns the maximum number of questions (without including the response)
    that can be performed in the game
     */
    public int longestGame() {
        throw new RuntimeException("Not yet implemented");
    }


    /*
    Returns the number of responses stored in this game
     */
    public int responses() {
        throw new RuntimeException("Not yet implemented");
    }

    public String solve(String path) {
        throw new RuntimeException("Not yet implemented");
    }

    public String showGame() {
        throw new RuntimeException("Not yet implemented");
    }

    
}
