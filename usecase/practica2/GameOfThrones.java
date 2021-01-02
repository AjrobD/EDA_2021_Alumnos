package usecase.practica2;

import material.Position;
import material.tree.narytree.LinkedTree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class GameOfThrones {

    public class Tuple<X, Y> {
        public final X x;
        public final Y y;
        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }

        public X getX() {
            return x;
        }

        public Y getY() {
            return y;
        }
    }

    ArrayList<LinkedTree<FamilyMember>> arboles;
    public GameOfThrones(){
        arboles = new ArrayList<>();
    }

    private class FamilyMember{
        private String id;
        private String nombre;
        private String genero;
        private int edad;

        public String getGenero() {
            return genero;
        }

        public void setGenero(String genero) {
            this.genero = genero;
        }

        public int getEdad() {
            return edad;
        }

        public void setEdad(int edad) {
            this.edad = edad;
        }

        public FamilyMember(String id, String nombre, String genero, int edad) {
            this.id = id;
            this.nombre = nombre;
            this.genero = genero;
            this.edad = edad;
        }

        public String getId() {
            return id;
        }

        public boolean equalsID(String id){
            return this.id == id;
        }

        public String getNombre(){
            return nombre;
        }


    }

    ArrayList<Position<FamilyMember>> getVarones(ArrayList<Position<FamilyMember>> candidatos){
        ArrayList<Position<FamilyMember>> varones = new ArrayList();
        for(Position<FamilyMember> miembro: candidatos){
            if(miembro.getElement().getGenero()=="M"){
                varones.add(miembro);
            }
        }
        return varones;
    }

    Position<FamilyMember> getMayor(ArrayList<Position<FamilyMember>> candidatos){
        Position<FamilyMember> candidato = null;
        for(Position<FamilyMember> miembro: candidatos){
            if(candidato==null||miembro.getElement().getEdad()>candidato.getElement().getEdad()){
                candidato=miembro;
            }
        }
        return candidato;
    }

    private Position<FamilyMember> getMemberPosition(String memberName, Position<FamilyMember> node, LinkedTree<FamilyMember> familia){
        if(node!=null){
            if(node.getElement().getNombre().equals(memberName)){
                return node;
            }
            else{
                for(Position<FamilyMember> child: familia.children(node)){
                    Position<FamilyMember> member = getMemberPosition(memberName,child,familia);
                    if(member!=null){
                        return member;
                    }
                }
            }
        }
        return null;
    }

    private LinkedTree<FamilyMember> findFamilyByName(String name){
        int n = arboles.size();
        int i = 0;
        boolean found = false;
        Position<FamilyMember> member = null;
        LinkedTree<FamilyMember> familia = null;
        while(i<n&&!found){
            familia = arboles.get(i);
            member = getMemberPosition(name,familia.root(),familia);
            found=member!=null;
            i++;
        }
        return familia;
    }

    public void loadFile(String pathToFile) throws FileNotFoundException {
        BufferedReader reader;
        HashMap<String,FamilyMember> todos = new HashMap<>();
        HashMap<String,Tuple<Position<FamilyMember>,LinkedTree<FamilyMember>>> todosPosition = new HashMap<>();
        try{
            reader = new BufferedReader(new FileReader(pathToFile));
            String line = reader.readLine();
            while (line != null && !line.equals("---------------------------------------------------------------------------------------")){
                String[] words = line.split(" ");
                String id = words[0];
                String nombre = words[2]+" "+words[3];
                String sexo = words[4].replaceAll("[()]", "");
                int edad = Integer.parseInt(words[5]);
                FamilyMember miembro = new FamilyMember(id,nombre,sexo,edad);
                todos.put(miembro.getId(),miembro);
                //read next line
                line = reader.readLine();
            }
            int numero = Integer.parseInt(reader.readLine());
            for(int i=0;i<numero;i++){
                String id = reader.readLine();
                FamilyMember member = todos.get(id);
                LinkedTree<FamilyMember> arbol = new LinkedTree<>();
                todosPosition.put(id,new Tuple<>(arbol.addRoot(member),arbol));
                arboles.add(arbol);
            }
            line = reader.readLine();
            while (line != null){
                String[] words = line.split(" -> ");
                Tuple<Position<FamilyMember>,LinkedTree<FamilyMember>> tupla = todosPosition.get(words[0]);
                Position<FamilyMember> padre = tupla.getX();
                LinkedTree<FamilyMember> tree = tupla.getY();
                FamilyMember hijo = todos.get(words[1]);
                todosPosition.put(hijo.getId(),new Tuple<>(tree.add(hijo, padre),tree));
                //read next line
                line = reader.readLine();
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public LinkedTree<FamilyMember> getFamily(String surname){
        int n = arboles.size();
        int i = 0;
        while(i<n){
            LinkedTree<FamilyMember> arbol = arboles.get(i);
            if(arbol.root().getElement().getNombre().split(" ")[1].equals(surname)){
                return arbol;
            }
            i++;
        }
        return null;
    }

    public String findHeir(String surname){
        LinkedTree<FamilyMember> familia = this.getFamily(surname);
        Position<FamilyMember> padre = familia.root();
        ArrayList<Position<FamilyMember>> hijos = (ArrayList<Position<FamilyMember>>) familia.children(padre);
        ArrayList<Position<FamilyMember>> varones = getVarones(hijos);
        if(varones.isEmpty()){
            return getMayor(hijos).getElement().getNombre();
        }
        else{
            return getMayor(varones).getElement().getNombre();
        }
    }

    public void changeFamily(String memberName, String newParent){
        Position<FamilyMember> origen = null;
        Position<FamilyMember> destino = null;

        //consigo el position del que quiero cambiar
        LinkedTree<FamilyMember> familia = this.getFamily(memberName.split(" ")[1]);
        Position<FamilyMember> padre = familia.root();
        origen = getMemberPosition(memberName,padre,familia);
        familia.remove(origen);

        //consigo el position del nuevo padre
        familia = this.getFamily(newParent.split(" ")[1]);
        padre = familia.root();
        destino = getMemberPosition(newParent,padre,familia);
        familia.add(origen.getElement(),destino);
    }

    //esta mal porque usa getFamily, que busca por apellidos, pero si hemos cambiado a uno de familia sus apellidos no coincidirán
    public boolean areFamily(String name1, String name2){
        LinkedTree<FamilyMember> family1 = findFamilyByName(name1);
        LinkedTree<FamilyMember> family2 = findFamilyByName(name2);
        return family1.root().equals(family2.root());
    }

    //metodo que te de la familia pero en vez de buscar por apellido encuentre el nodo correcto
    private LinkedTree<FamilyMember> getFamilia(String nombre){
        throw new RuntimeException("No implementado");
    }
}
