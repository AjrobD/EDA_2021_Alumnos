package material.exam_excercises.Nov2020;

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
    private BinaryTree<String> juego;
    private Iterator<Position<String>> it;
    private List<Position<String>> pos;


    public void loadGame(String path) {
        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
            String line = null;
            int lineCount = 1;
            juego = new LinkedBinaryTree<>();
            pos = new ArrayList<>();
            pos.add(null);
            while ((line = bf.readLine()) != null) {
                if(!line.equals("NADA")) {
                    if (lineCount==1) {
                        pos.add(juego.addRoot(line));
                    } else {
                        int fatherPos = lineCount / 2;
                        Position<String> parent = pos.get(fatherPos);
                        if (lineCount % 2 == 0) {
                            pos.add(juego.insertLeft(parent, line));
                        } else {
                            pos.add(juego.insertRight(parent, line));
                        }
                    }
                }else{
                    pos.add(null);
                }
                lineCount++;
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
        it = juego.iterator();
    }

    /*
    Returns true if there are more question to be read
    by the iterator, or false otherwise
     */
    public boolean hasMoreQuestions() {
        return it.hasNext();
    }

    /*
    Returns the next question to be read from the game
     */
    public String nextQuestion() {
        return it.next().getElement();
    }

    /*
    Returns the maximum number of questions (without including the response)
    that can be performed in the game
     */
    public int longestGame() {
        return longestGameRecursive(juego.root());
    }

    private int longestGameRecursive(Position<String> pos) {
        if (juego.isLeaf(pos)){
            return 1;
        }
        else{
            int longIzq = 0;
            int longDer = 0;
            if(juego.hasLeft(pos)){
                longIzq = longestGameRecursive(juego.left(pos));
            }
            if(juego.hasRight(pos)){
                longDer = longestGameRecursive(juego.right(pos));
            }
            if(longIzq>=longDer){
                return longIzq+1;
            }
            return longDer+1;
        }
    }


    /*
    Returns the number of responses stored in this game
     */
    public int responses() {
        return responsesRecursive(juego.root());
    }

    private int responsesRecursive(Position<String> pos) {
        int totalRes = 0;
        if(juego.isLeaf(pos)){
            return 1;
        }
        else{
            for(Position<String> child : juego.children(pos)){
                totalRes = totalRes + responsesRecursive(child);
            }
        }
        return totalRes;
    }


    public String solve(String path) {
        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
            String line = null;
            Position<String> pos = juego.root();
            while ((line = bf.readLine()) != null) {
                if(line.equals("SI")){
                    if(juego.hasLeft(pos)){
                        pos = juego.left(pos);
                    }
                    else{
                        return "INCORRECTO";
                    }
                }
                else{
                    if(juego.hasRight(pos)){
                        pos = juego.right(pos);
                    }
                    else{
                        return "INCORRECTO";
                    }
                }
            }
            return pos.getElement();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String showGame() {
        String game = "";
        return showGameRecursive(juego.root(),0);
    }

    private String showGameRecursive(Position<String> root, int i) {
        String line = "";
        for(int j=0;j<i;j++){
            line="\t"+line;
        }
        line=line+root.getElement()+"\n";
        for(Position<String> child : juego.children(root)){
            String childLine = showGameRecursive(child,i+1);
            line = line + childLine;
        }
        return line;
    }


}
