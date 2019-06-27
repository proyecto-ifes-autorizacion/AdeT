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

import lombok.Getter;
import lombok.Setter;

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
                name = "findByCuilContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.Empleado "
                        + "WHERE cuil.indexOf(:cuil) >= 0 "),
        @Query(
                name = "findByCuil", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.Empleado "
                        + "WHERE cuil == :cuil ")
})
@Unique(name = "Empleado_cuil_UNQ", members = { "cuil" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@Getter @Setter

public class Empleado implements Comparable<Empleado> {

    @Column(allowsNull = "false", length = 13)
    @Property()
    private String cuil;

    @Column(allowsNull = "false", length = 40)
    @Property()
    private String nombre;

    @Column(allowsNull = "false", length = 40)
    @Property()
    private String apellido;

    public Empleado(final String cuil, final String nombre, final String apellido){

        this.cuil = cuil;
        this.nombre = nombre;
        this.apellido = apellido;

    }

    //region > compareTo, toString
    @Override
    public int compareTo(final Empleado other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "cuil");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "cuil");
    }
    //endregion
}
