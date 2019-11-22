package domainapp.modules.simple.dominio.reportes;


public class RepoAutorizacion {

    private int numero;
    private String titulo;
    private String ubicacion;
    private String estado;
    private String apertura;
    private String cierre;
    private String tiempo;

    public RepoAutorizacion(int numero, String titulo, String ubicacion, String estado, String apertura, String cierre, String tiempo){
        this.numero = numero;
        this.titulo = titulo;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.apertura = apertura;
        this.cierre = cierre;
        this.tiempo = tiempo;
    }

    public RepoAutorizacion(){}

    public int getNumero(){ return this.numero; }
    public String getTitulo(){ return this.titulo; }
    public String getUbicacion() { return this.ubicacion; }
    public String getEstado(){ return this.estado; }
    public String getApertura(){ return this.apertura; }
    public String getCierre(){ return this.cierre; }
    public String getTiempo(){ return this.tiempo; }
}
