package domainapp.modules.simple.dominio.autorizacion;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.annotation.*;

import javax.jdo.annotations.*;

import domainapp.modules.simple.dominio.SujetoGeneral;
import domainapp.modules.simple.dominio.trabajador.Trabajador;
import lombok.Getter;
import lombok.Setter;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "dominio",
        table = "Autorizacion"
)
@DatastoreIdentity(
        strategy = IdGeneratorStrategy.IDENTITY,
        column = "id")
@Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@Queries({
        @Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.autorizacion.Autorizacion "),
        @Query(
                name = "findByIdAdeTContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.autorizacion.Autorizacion "
                        + "WHERE idAdeT.indexOf(:idAdeT) >= 0 "),
        @Query(
                name = "findByIdAdeT", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.autorizacion.Autorizacion "
                        + "WHERE idAdeT == :idAdeT ")
})
@Unique(name = "Autorizacion_idAdeT_UNQ", members = { "idAdeT" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@Getter @Setter
public class Autorizacion implements Comparable<Autorizacion>, SujetoGeneral {

    @Column(allowsNull = "false")
    @Property()
    private int idAdeT;

    @Column(allowsNull = "false", length = 40)
    @Property()
    private String titulo;

    @Column(allowsNull = "false", length = 4000)
    @Property()
    private String descripcion;

    @Column(allowsNull = "false", length = 60)
    @Property()
    private String ubicacion;

    @Column(allowsNull = "true")
    @Property()
    private Date apertura;

    @Column(allowsNull = "true")
    @Property()
    private Date cierre;

    @Column(allowsNull = "false")
    @Property()
    private EstadoAutorizacion estado;

    @Column(allowsNull = "true")
    @Property()
    private Trabajador creador;

    @Column(allowsNull = "true")
    @Property()
    private Trabajador solicitante;

//    @Column(allowsNull = "true")
//    @Property()
//    private Vehiculo solicitanteVehiculo;

    @Column(allowsNull = "true")
    @Property()
    private List<Ejecutante> ejecutantes;

    public Autorizacion(){}

    public Autorizacion(
            int idAdeT, EstadoAutorizacion estado, String titulo, String descripcion, String ubicacion){

        this.idAdeT = idAdeT;
        this.estado = estado;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
    }

    public Autorizacion(
            int idAdeT, EstadoAutorizacion estado, String titulo, String descripcion, String ubicacion,
            Date apertura, Date cierre, Trabajador solicitante, List<Ejecutante> ejecutantes){

        this.idAdeT = idAdeT;
        this.estado = estado;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.apertura = apertura;
        this.cierre = cierre;
        this.solicitante = solicitante;
        this.ejecutantes = ejecutantes;
    }

    //region > compareTo, toString
    @Override
    public int compareTo(final Autorizacion other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "idAdeT");
    }

    @Action()
    @ActionLayout(named = "Cambiar Estado")
    public void CambiarEstado(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Estado")
            final EstadoAutorizacion estadoAutorizacion){

        this.estado = estadoAutorizacion;
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "idAdeT");
    }

    @Override
    public void Notificar() {

    }
    //endregion

}
