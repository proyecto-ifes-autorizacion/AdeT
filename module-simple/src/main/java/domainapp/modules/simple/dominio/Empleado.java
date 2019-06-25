package domainapp.modules.simple.dominio;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "dominio",
        table = "Empleado"
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
                        + "FROM domainapp.modules.simple.dominio.Empleado "),
        @Query(
                name = "findByDniContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.Empleado "
                        + "WHERE dni.indexOf(:dni) >= 0 "),
        @Query(
                name = "findByDni", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.Empleado "
                        + "WHERE dni == :dni ")
})
@Unique(name = "Empleado_dni_UNQ", members = { "dni" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)

@lombok.Getter @lombok.Setter
//@lombok.RequiredArgsConstructor

public class Empleado implements Comparable<Empleado> {

    @Column(allowsNull = "false")
    @lombok.NonNull
    @Property()
    private int dni;

    @Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property()
    private String nombre;

    @Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property()
    private String apellido;

    public Empleado(final int dni, final String nombre, final String apellido) {

        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    //region > compareTo, toString
    @Override
    public int compareTo(final Empleado other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "dni");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "dni");
    }
    //endregion

}
