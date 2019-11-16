package domainapp.modules.simple.dominio.reportes;

public class RepoTrabajador {

    private String cuil;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String empresa;
    private String estado;

    public RepoTrabajador(String cuil, String nombre, String apellido, String fechaNacimiento, String empresa, String estado){
        this.cuil = cuil;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.empresa = empresa;
        this.estado = estado;
    }

    public RepoTrabajador(){}

    public String getCuil(){ return this.cuil; }
    public String getNombre(){ return this.nombre; }
    public String getApellido(){ return this.apellido; }
    public String getFechaNacimiento(){ return this.fechaNacimiento; }
    public String getEmpresa(){ return this.empresa; }
    public String getEstado(){ return this.estado; }

}
