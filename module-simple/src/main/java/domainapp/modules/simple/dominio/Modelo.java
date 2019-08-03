package domainapp.modules.simple.dominio;

import java.util.Collection;

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

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.title.TitleService;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import static org.apache.isis.applib.annotation.CommandReification.ENABLED;
import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "dominio",
        table = "Modelo"
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
                        + "FROM domainapp.modules.simple.dominio.Modelo "),
        @Query(
                name = "findByNombreContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.Modelo "
                        + "WHERE nombre.indexOf(:nombre) >= 0 "),
        @Query(
                name = "findByNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.Modelo "
                        + "WHERE nombre == :nombre ")
})
@Unique(name = "Modelo_nombre_UNQ", members = { "nombre" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@Getter @Setter

public class Modelo implements Comparable<Modelo> {

    @Column(allowsNull = "false", length = 40)
    @Property()
    @Title()
    private String nombre;

    @Column(allowsNull = "false")
    @Property()
    private Marca marca;

    @Column(allowsNull = "false")
    @Property()
    private boolean baja;

    public Modelo(String nombre, boolean baja, Marca marca){

        this.nombre = nombre;
        this.marca = marca;
        this.baja = baja;
    }

    public  Modelo(){}

    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "nombre")
    @ActionLayout(named = "Editar")
    public Modelo UpdateNombre(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Modelo: ")
            final String nombre){
        setNombre(nombre);
        return this;
    }

    public String default0UpdateNombre() {
        return getNombre();
    }

    public Collection<Marca> choicesMarca() {
        return marcaRepository.listAll();
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(named = "Activar/Desactivar")
    public Modelo UpdateBaja(){
        setBaja(!this.baja);
        return this;
    }

    //region > compareTo, toString
    @Override
    public int compareTo(final Modelo other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "nombre");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "nombre");
    }
    //endregion

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(named = "eliminar")
    public void delete() {
        modeloRepository.delete(this);
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
    }

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    ModeloRepository modeloRepository;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    TitleService titleService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    MessageService messageService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    MarcaRepository marcaRepository;
}