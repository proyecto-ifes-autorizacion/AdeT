package domainapp.modules.simple.dominio.reportes;

import java.security.SecureRandom;

public class RepoEmpresa {

    private String nombreFantasia;
    private String razonSocial;
    private String direccion;
    private String telefono;
    private String estado;

    public RepoEmpresa(String nombreFantasia, String razonSocial, String direccion, String telefono, String estado){
        this.nombreFantasia = nombreFantasia;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.estado = estado;
    }

    public RepoEmpresa(){}

    public String getNombreFantasia(){ return this.nombreFantasia; }
    public String getRazonSocial(){ return this.razonSocial; }
    public String getDireccion(){ return this.direccion; }
    public String getTelefono(){ return this.telefono; }
    public String getEstado(){ return this.estado; }
}
