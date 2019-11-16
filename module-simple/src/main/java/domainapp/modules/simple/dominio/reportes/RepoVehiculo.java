package domainapp.modules.simple.dominio.reportes;

public class RepoVehiculo {

    private String dominio;
    private String modelo;
    private String fechaAlta;
    private String empresa;
    private String estado;

    public RepoVehiculo(String dominio, String modelo, String fechaAlta, String empresa, String estado){
        this.dominio = dominio;
        this.modelo = modelo;
        this.fechaAlta = fechaAlta;
        this.empresa = empresa;
        this.estado = estado;
    }

    public RepoVehiculo(){}

    public String getDominio(){ return this.dominio; }
    public String getModelo(){ return  this.modelo; }
    public String getFechaAlta(){ return this.fechaAlta; }
    public String getEmpresa(){ return this.empresa; }
    public String getEstado(){ return this.estado; }

}
