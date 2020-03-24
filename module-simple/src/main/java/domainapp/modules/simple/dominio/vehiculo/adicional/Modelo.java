package domainapp.modules.simple.dominio.vehiculo.adicional;

import java.util.Collection;
import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
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
import org.apache.isis.applib.annotation.Title;

import domainapp.modules.simple.dominio.ObservadorGeneral;
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
                        + "FROM domainapp.modules.simple.dominio.vehiculo.adicional.Modelo "),
        @Query(
                name = "findByNombreContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.vehiculo.adicional.Modelo "
                        + "WHERE nombre.indexOf(:nombre) >= 0 "),
        @Query(
                name = "findByNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.vehiculo.adicional.Modelo "
                        + "WHERE nombre == :nombre "),
        @Query(
                name = "ModeloByMarca", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.vehiculo.adicional.Modelo "
                        + "WHERE marca == :marca "),
        @Query(
                name = "ListByBaja", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.vehiculo.adicional.Modelo "
                        + "WHERE baja == :baja "),
        @Query(
                name = "ListActivo", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.vehiculo.adicional.Modelo "
                        + "WHERE baja == false && bajaMarca == false")

})
@Unique(name = "Modelo_nombre_UNQ", members = { "nombre" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@Getter @Setter

public class Modelo implements Comparable<Modelo>, ObservadorGeneral {

    @Column(allowsNull = "false", length = 30)
    @Property()
    private String nombre;

    @Column(allowsNull = "false")
    @Property()
    private Marca marca;

    @Column(allowsNull = "false")
    @Property()
    @PropertyLayout(named = "Modelo Desactivado")
    private boolean baja;

    @Column(allowsNull = "false")
    @Property()
    @PropertyLayout(named = "Marca Dasactivada")
    private boolean bajaMarca;


    @NotPersistent()
    @CollectionLayout(named = "Modelos Activos")
    public List<Modelo> getActivas(){

        return modeloRepository.ListByBaja(false);
    }

    @NotPersistent()
    @CollectionLayout(named = "Modelos Desactivados")
    public List<Modelo> getInactivas(){

        return modeloRepository.ListByBaja(true);
    }

    public String title(){

        return marca.getNombre()+" "+getNombre();
    }

    public String iconName(){
        if (this.baja){
            return "off";
        } else
            return this.bajaMarca ? "error" : "on";
    }

    public Modelo(String nombre, Marca marca, boolean baja, boolean bajaMarca){

        this.nombre = nombre;
        this.marca = marca;
        this.baja = baja;
        this.bajaMarca = bajaMarca;
    }

    public Modelo(String nombre, Marca marca){

        this.nombre = nombre;
        this.marca = marca;
        this.baja = false;
        this.bajaMarca = marca.getBaja();
    }

    public  Modelo(){}

    @Action()
    @ActionLayout(named = "Editar")
    public Modelo Update(
            @Parameter(maxLength = 30)
            @ParameterLayout(named = "Modelo: ")
            final String nombre){
        setNombre(nombre);
        return this;
    }

    public String default0Update() {
        return getNombre();
    }

    public Collection<Marca> choicesMarca() {
        return marcaRepository.listAll();
    }

    @Programmatic
    public void CambiarEstado(boolean estado){
        setBaja(estado);
    }

    @Action()
    public Modelo Activar(){
        CambiarEstado(false);
        return this;
    }

    @Action
    public Modelo Desactivar(){
        CambiarEstado(true);
        return this;
    }

    public boolean hideActivar() {return !this.baja;}
    public boolean hideDesactivar() {return this.baja;}

    @Override public void Actualizar() {
        setBajaMarca(marca.getBaja());
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

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    ModeloRepository modeloRepository;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    MarcaRepository marcaRepository;

}
