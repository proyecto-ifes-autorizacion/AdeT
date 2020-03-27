package domainapp.modules.simple.dominio.vehiculo.adicional;

import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;

import domainapp.modules.simple.dominio.SujetoGeneral;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import static org.apache.isis.applib.annotation.CommandReification.ENABLED;
import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "dominio",
        table = "Marca"
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
                        + "FROM domainapp.modules.simple.dominio.vehiculo.adicional.Marca "),
        @Query(
                name = "findByNombreContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.vehiculo.adicional.Marca "
                        + "WHERE nombre.indexOf(:nombre) >= 0 "),
        @Query(
                name = "findByNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.vehiculo.adicional.Marca "
                        + "WHERE nombre == :nombre "),
        @Query(
                name = "ListByBaja", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.vehiculo.adicional.Marca "
                        + "WHERE baja == :baja ")
})
@Unique(name = "Marca_nombre_UNQ", members = { "nombre" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@Getter @Setter
public class Marca implements Comparable<Marca>, SujetoGeneral {

    @Column(allowsNull = "false", length = 30)
    @Property()
    @Title()
    private String nombre;

    @Persistent(mappedBy = "marca", defaultFetchGroup = "true")
    @Column(allowsNull = "true")
    @Property()
    private List<Modelo> modelos;

    @Column(allowsNull = "false")
    @Property()
    @PropertyLayout(named = "Desactivada")
    private boolean baja;

    @NotPersistent()
    @CollectionLayout(named = "Marcas Activas")
    public List<Marca> getActivas(){

        return marcaRepository.ListByBaja(false);
    }

    @NotPersistent()
    @CollectionLayout(named = "Marcas Desactivadas")
    public List<Marca> getInactivas(){

        return marcaRepository.ListByBaja(true);
    }

    public String iconName(){
        return this.baja ? "off" : "on";
    }

    public Marca(){}

    public Marca(String nombre){

        this.nombre = nombre;
        this.baja = false;
        this.modelos = null;
    }

    public Marca(String nombre, boolean baja, List<Modelo> modelos){

        this.nombre = nombre;
        this.baja = baja;
        this.modelos = modelos;
    }

    public boolean getBaja(){
        return this.baja;
    }

    @Action()
    @ActionLayout(named = "Editar")
    public Marca Update(
            @Parameter(maxLength = 30)
            @ParameterLayout(named = "Marca: ")
            final String nombre){
        setNombre(nombre);
        return this;
    }

    public String default0Update() {return getNombre();}

    @Action()
    public Marca Activar(){
        CambiarEstado(false);
        return this;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE)
    public Marca Desactivar(){
        CambiarEstado(true);
        return this;
    }

    public boolean hideActivar() {return !this.baja;}
    public boolean hideDesactivar() {return this.baja;}

    @Programmatic
    public void CambiarEstado(boolean estado){
        this.baja = estado;
        Notificar();
    }

    @Action()
    @ActionLayout(named = "Agregar")
    public Marca AgregarModelos(
            @Parameter(maxLength = 30)
            @ParameterLayout(named = "Modelo")
            final String nombre){

        modelos.add(modeloRepository.create(nombre,this));
        return this;
    }

    @Override
    public void Notificar() {
        for (Modelo modelo: modelos) {
            modelo.Actualizar();
        }
    }

    //region > compareTo, toString
    @Override
    public int compareTo(final Marca other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "nombre");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "nombre");
    }
    //endregion

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    MarcaRepository marcaRepository;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    ModeloRepository modeloRepository;


}
