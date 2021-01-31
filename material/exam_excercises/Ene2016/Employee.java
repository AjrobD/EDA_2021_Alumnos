package material.exam_excercises.Ene2016;

public class Employee {
    private String empresa;
    private String nombre;
    private String cargo;
    private String descripcion;

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Employee(String empresa, String nombre, String cargo, String descripcion) {
        this.empresa = empresa;
        this.nombre = nombre;
        this.cargo = cargo;
        this.descripcion = descripcion;
    }
}
