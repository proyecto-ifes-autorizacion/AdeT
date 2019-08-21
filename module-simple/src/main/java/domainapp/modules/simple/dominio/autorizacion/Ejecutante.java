package domainapp.modules.simple.dominio.autorizacion;

import java.util.List;

import org.apache.isis.applib.annotation.*;

import javax.jdo.annotations.*;

import domainapp.modules.simple.dominio.empleado.Empleado;
import domainapp.modules.simple.dominio.empresa.Empresa;
import lombok.Getter;
import lombok.Setter;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "dominio",
        table = "Ejecutante"
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
                        + "FROM domainapp.modules.simple.dominio.autorizacion.Ejecutante "),
        @Query(
                name = "findByAutorizacionContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.autorizacion.Ejecutante "
                        + "WHERE autorizacion.indexOf(:autorizacion) >= 0 "),
        @Query(
                name = "findByAutorizacion", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.autorizacion.Ejecutante "
                        + "WHERE autorizacion == :autorizacion ")
})
@Unique(name = "Ejecutantes_autorizacion_UNQ", members = { "autorizacion" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@Getter @Setter
public class Ejecutante implements Comparable<Ejecutante> {

    @Column(allowsNull = "false")
    @Property()
    private Autorizacion autorizacion;

    @Column(allowsNull = "false")
    @Property()
    private Empresa empresa;

    @Column(allowsNull = "false")
    @Property()
    private List<Empleado> empleados;

    public Ejecutante(){}

    public Ejecutante(Autorizacion autorizacion, Empresa empresa){

        this.autorizacion = autorizacion;
        this.empresa = empresa;
    }

    public Ejecutante(Autorizacion autorizacion, Empresa empresa, List<Empleado> empleados){

        this.autorizacion = autorizacion;
        this.empresa = empresa;
        this.empleados = empleados;
    }

    //region > compareTo, toString
    @Override
    public int compareTo(final Ejecutante other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "autorizacion");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "autorizacion");
    }
    //endregion

}
