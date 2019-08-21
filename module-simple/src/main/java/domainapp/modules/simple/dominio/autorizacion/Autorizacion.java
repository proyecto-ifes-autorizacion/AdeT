package domainapp.modules.simple.dominio.autorizacion;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.annotation.*;

import javax.jdo.annotations.*;

import domainapp.modules.simple.dominio.empleado.Empleado;
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
public class Autorizacion implements Comparable<Autorizacion> {

    @Column(allowsNull = "false")
    @Property()
    private int idAdeT;

    @Column(allowsNull = "false")
    @Property()
    private AutorizacionEstado autorizacionEstado;

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

//    @Column(allowsNull = "false")
//    @Property()
//    private Empleado creador;

    @Column(allowsNull = "true")
    @Property()
    private Empleado solicitante;

//    @Column(allowsNull = "true")
//    @Property()
//    private Vehiculo solicitanteVehiculo;

    @Column(allowsNull = "true")
    @Property()
    private List<Ejecutante> ejecutantes;

    public Autorizacion(){}

    public Autorizacion(
            int idAdeT, AutorizacionEstado autorizacionEstado, String titulo, String descripcion, String ubicacion){

        this.idAdeT = idAdeT;
        this.autorizacionEstado = autorizacionEstado;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
    }

    public Autorizacion(
            int idAdeT, AutorizacionEstado autorizacionEstado, String titulo, String descripcion, String ubicacion,
            Date apertura, Date cierre, Empleado solicitante, List<Ejecutante> ejecutantes){

        this.idAdeT = idAdeT;
        this.autorizacionEstado = autorizacionEstado;
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

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "idAdeT");
    }
    //endregion

}
