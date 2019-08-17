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
                name = "findByTituloContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.autorizacion.Autorizacion "
                        + "WHERE titulo.indexOf(:titulo) >= 0 "),
        @Query(
                name = "findByTitulo", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.autorizacion.Autorizacion "
                        + "WHERE titulo == :titulo ")
})
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@Getter @Setter
public class Autorizacion implements Comparable<Autorizacion> {

    @Unique()
    @Column(allowsNull = "false")
    @Property()
    private int idAdeT;

    @Column(allowsNull = "false", length = 40)
    @Property()
    private String titulo;

    @Column(allowsNull = "false", length = 4000)
    @Property()
    private String descripcion;

    @Column(allowsNull = "false", length = 120)
    @Property()
    private String ubicacion;

    @Column(allowsNull = "true", length = 4000)
    @Property()
    private String motivoCancelacion;

    @Column(allowsNull = "false")
    @Property()
    private Date apertura;

    @Column(allowsNull = "true")
    @Property()
    private Date cierre;

    @Column(allowsNull = "false")
    @Property()
    private Empleado creador;

    @Column(allowsNull = "false")
    @Property()
    private Empleado solicitante;

    @Column(allowsNull = "true")
    @Property()
    private List<Empleado> ejecutantes;

    @Column(allowsNull = "false")
    @Property()
    private Estado estado;

    public Autorizacion() {
    }

    public Autorizacion(
            final String titulo,
            final String descripcion,
            final String ubicacion,
            final String motivoCancelacion,
            final Date apertura,
            final Date cierre,
            final Empleado creador,
            final Empleado solicitante,
            final List<Empleado> ejecutantes,
            final Estado estado) {

        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.motivoCancelacion = motivoCancelacion;
        this.apertura = apertura;
        this.cierre = cierre;
        this.creador = creador;
        this.solicitante = solicitante;
        this.ejecutantes = ejecutantes;
        this.estado = estado;
    }

    //region > compareTo, toString
    @Override
    public int compareTo(final Autorizacion other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "titulo");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "titulo");
    }
    //endregion

}
