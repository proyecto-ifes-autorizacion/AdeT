package domainapp.modules.simple.dominio.autorizacion;

public class IteradorEjecutante {

    private static IteradorEjecutante instacia = null;
    private int iterador;

    private IteradorEjecutante(){}

    private static void CrearInstancia() {
        if (instacia == null){
            instacia = new IteradorEjecutante();
        }
    }

    public static IteradorEjecutante getInstance(){
        CrearInstancia();
        return instacia;
    }

    public void reinicio(){ this.iterador = 0; }

    public int getIterador(){
        return this.iterador;
    }

    public void setIterador(int iterador){
        this.iterador = iterador;
    }
}
