package material.exam_excercises.Ene2016;

import material.Position;
import material.maps.AbstractHashTableMap;
import material.maps.HashTableMapDH;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class URJCInvest {
    private AbstractHashTableMap<String,OrganizationChart> torre;

    public URJCInvest(){
        torre = new HashTableMapDH<>();
    }

    public OrganizationChart searchCompany(String nombre){
       return torre.get(nombre);
    }

    public Iterable<Employee> getGrantHolders(String nombre){
        List<Employee> becarios = new ArrayList<>();
        OrganizationChart empresa = torre.get(nombre);
        Iterator<Position<Employee>> it = empresa.getTree().iterator();
        while(it.hasNext()){
            Position<Employee> employee = it.next();
            if(empresa.getTree().isLeaf(employee)){
                becarios.add(employee.getElement());
            }
        }
        return becarios;
    }

    public Iterable<Employee> getChiefs(String nombre, String employee){
        OrganizationChart empresa = torre.get(nombre);
        Position<Employee> emp = findEmployee(empresa,employee);
        Iterable<Employee> chiefs = findChiefs(empresa,emp);
        return chiefs;
    }

    private Iterable<Employee> findChiefs(OrganizationChart empresa, Position<Employee> emp) {
        List<Employee> chiefs = new ArrayList<>();
        while(!empresa.getTree().isRoot(emp)){
            chiefs.add(empresa.getTree().parent(emp).getElement());
            emp = empresa.getTree().parent(emp);
        }
        return chiefs;
    }

    public Position<Employee> findEmployee(OrganizationChart empresa, String name) throws RuntimeException{
        for(Position<Employee> emp : empresa.getTree()){
            if(emp.getElement().equals(name)){
                return emp;
            }
        }
        throw new RuntimeException("Employee not found");
    }

    public Iterable<Employee> getEmployees(String cargo){
        List<Employee> empleados = new ArrayList<>();
        for(OrganizationChart empresa : torre.values()){
            for(Position<Employee> emp: empresa.getTree()){
                if(emp.getElement().getCargo().equals(cargo)){
                    empleados.add(emp.getElement());
                }
            }
        }
        return empleados;
    }
}
