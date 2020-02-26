package domainapp.modules.simple.dom.impl;

import org.apache.isis.applib.annotation.*;

import javax.jdo.annotations.*;

import lombok.Getter;
import lombok.Setter;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "impl",
        table = "vehiculo"
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
                        + "FROM domainapp.modules.simple.dom.impl.vehiculo "),
        @Query(
                name = "findByMarcaContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.vehiculo "
                        + "WHERE marca.indexOf(:marca) >= 0 "),
        @Query(
                name = "findByMarca", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.vehiculo "
                        + "WHERE marca == :marca ")
})
@Unique(name = "vehiculo_marca_UNQ", members = { "marca" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class vehiculo implements Comparable<vehiculo> {

    @Column(allowsNull = "false")
    @Property()
    @Getter @Setter
    private String marca;

    //region > compareTo, toString
    @Override
    public int compareTo(final vehiculo other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "marca");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "marca");
    }

    public void setMarca(final String marca) {
    }
    //endregion
}
