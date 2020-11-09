package usecase.practica2;

import material.Position;
import material.tree.narytree.LinkedTree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GameOfThrones {

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

    private FamilyMember getMember(String id, ArrayList<FamilyMember> lista){
        for(FamilyMember member: lista){
            if(id.equals(member.getId())){
                return member;
            }
        }
        return null;
    }

    private Position<FamilyMember> getMemberPosition (String id, ArrayList<Position<FamilyMember>> lista){
        for(Position<FamilyMember> member: lista){
            if(id.equals(member.getElement().getId())){
                return member;
            }
        }
        return null;
    }

    private LinkedTree<FamilyMember> getTree(FamilyMember padre, ArrayList<LinkedTree<FamilyMember>> arboles){
        for(LinkedTree<FamilyMember> arbol: arboles){
            for(Position<FamilyMember> miembro: arbol){
                if(miembro.getElement().getId().equals(padre.getId())){
                    return arbol;
                }
            }
        }
        return null;
    }

    public void loadFile(String pathToFile) throws FileNotFoundException {
        BufferedReader reader;
        ArrayList<FamilyMember> todos = new ArrayList<>();
        ArrayList<Position<FamilyMember>> todosPosition = new ArrayList<>();
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
                todos.add(miembro);
                //read next line
                line = reader.readLine();
            }
            int numero = Integer.parseInt(reader.readLine());
            for(int i=0;i<numero;i++){
                String id = reader.readLine();
                FamilyMember member = getMember(id,todos);
                LinkedTree<FamilyMember> arbol = new LinkedTree<>();
                todosPosition.add(arbol.addRoot(member));
                arboles.add(arbol);
            }
            line = reader.readLine();
            while (line != null){
                String[] words = line.split(" -> ");
                Position<FamilyMember> padre = getMemberPosition(words[0],todosPosition);
                FamilyMember hijo = getMember(words[1],todos);
                LinkedTree<FamilyMember> tree = getTree(padre.getElement(),arboles);
                todosPosition.add(tree.add(hijo, padre));
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
        Queue<ArrayList<Position<FamilyMember>>> candidatos = new LinkedList<>();
        ArrayList<Position<FamilyMember>> hijos = (ArrayList<Position<FamilyMember>>) familia.children(padre);
        candidatos.add(hijos);
        ArrayList<Position<FamilyMember>> candidato = candidatos.remove();
        while(candidato!=null){
            int edadMayor = 0;
            FamilyMember heredero = null;
            for(Position<FamilyMember> hijo : candidato){
                if(hijo.getElement().getGenero().equals("M")&&hijo.getElement().getEdad()>edadMayor){
                    edadMayor=hijo.getElement().getEdad();
                    heredero = hijo.getElement();
                }
                candidatos.add((ArrayList<Position<FamilyMember>>) familia.children(hijo));
            }
            if(heredero!=null){
                return heredero.getNombre();
            }
            candidato = candidatos.remove();
        }
        return "";
    }

    public void changeFamily(String memberName, String newParent){
        Position<FamilyMember> origen = null;
        Position<FamilyMember> destino = null;

        //consigo el position del que quiero cambiar
        LinkedTree<FamilyMember> familia = this.getFamily(memberName.split(" ")[1]);
        Position<FamilyMember> padre = familia.root();
        Queue<ArrayList<Position<FamilyMember>>> candidatos = new LinkedList<>();
        ArrayList<Position<FamilyMember>> hijos = (ArrayList<Position<FamilyMember>>) familia.children(padre);
        candidatos.add(hijos);
        ArrayList<Position<FamilyMember>> candidato = candidatos.remove();
        while(candidato!=null&&origen==null){
            for(Position<FamilyMember> hijo : candidato){
                if(hijo.getElement().getNombre().equals(memberName)){
                    origen = hijo;
                }
            }
            candidato = candidatos.remove();
        }
        //consigo el position del nuevo padre
        familia = this.getFamily(newParent.split(" ")[1]);
        padre = familia.root();
        candidatos = new LinkedList<>();
        hijos = (ArrayList<Position<FamilyMember>>) familia.children(padre);
        candidatos.add(hijos);
        candidato = candidatos.remove();
        while(candidato!=null&&destino==null){
            for(Position<FamilyMember> hijo : candidato){
                if(hijo.getElement().getNombre().equals(newParent)){
                    destino = hijo;
                }
            }
            candidato = candidatos.remove();
        }

        familia.moveSubtree2(origen,destino);
        //esta mal porque moveSubtree2 es una operación que no comprueba nada y que no esta pensada para cambiar entre arboles
        //dado que no cambia cosas como el size
    }

    //esta mal porque usa getFamily, que busca por apellidos, pero si hemos cambiado a uno de familia sus apellidos no coincidirán
    public boolean areFamily(String name1, String name2){
        FamilyMember root1 = null;
        FamilyMember root2 = null;

        LinkedTree<FamilyMember> familia = this.getFamily(name1.split(" ")[1]);
        root1 = familia.root().getElement();

        familia = this.getFamily(name2.split(" ")[1]);
        root2 = familia.root().getElement();

        return (root1.getNombre().equals(root2.getNombre()));
    }

    //metodo que te de la familia pero en vez de buscar por apellido encuentre el nodo correcto
    private LinkedTree<FamilyMember> getFamilia(String nombre){
        throw new RuntimeException("No implementado");
    }
}
