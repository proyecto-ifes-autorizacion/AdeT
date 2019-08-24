package domainapp.modules.simple.dominio.empresa;

import java.util.List;

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

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;

import domainapp.modules.simple.dominio.SujetoGeneral;
import domainapp.modules.simple.dominio.trabajador.Trabajador;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "dominio",
        table = "Empresa"
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
                        + "FROM domainapp.modules.simple.dominio.empresa.Empresa "),
        @Query(
                name = "findByNombreFantasiaContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.empresa.Empresa "
                        + "WHERE nombreFantasia.indexOf(:nombreFantasia) >= 0 "),
        @Query(
                name = "findByNombreFantasia", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.empresa.Empresa "
                        + "WHERE nombreFantasia == :nombreFantasia ")
})
@Unique(name = "Empresa_nombreFantasia_UNQ", members = { "nombreFantasia" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@Getter @Setter

public class Empresa implements Comparable<Empresa>, SujetoGeneral {

    @Column(allowsNull = "false", length = 13)
    @Property()
    @Title()
    private String nombreFantasia;

    @Column(allowsNull = "false", length = 40)
    @Property()
    private String razonSocial;

    @Column(allowsNull = "false", length = 40)
    @Property()
    private String direccion;

    @Column(allowsNull = "false")
    @Property()
    private String telefono;

    @Column(allowsNull = "false")
    @Property()
    private EstadoEmpresa estado;

    @Column(allowsNull = "true")
    @Property()
    private List<Trabajador> trabajadores;

    public Empresa(
            final String nombreFantasia,
            final String razonSocial,
            final String direccion,
            final String telefono,
            final EstadoEmpresa estado,
            final List<Trabajador> trabajadores) {
        this.nombreFantasia = nombreFantasia;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.estado = estado;
        this.trabajadores = trabajadores;
    }

    public Empresa(
            final String nombreFantasia,
            final String razonSocial,
            final String direccion,
            final String telefono) {
        this.nombreFantasia = nombreFantasia;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.estado = EstadoEmpresa.Habilitada;
    }


    public Empresa(){}

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(named = "Editar")
    public Empresa update(

            @Parameter(maxLength = 13)
            @ParameterLayout(named = "Nombre de Fantasia: ")
            final String nombreFantasia,

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Razon Social: ")
            final String razonSocial,

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Direccion: ")
            final String direccion,

            @ParameterLayout(named = "Telefono: ")
            final String telefono)
    {
        this.nombreFantasia = nombreFantasia;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.telefono = telefono;
        return this;
    }

    public String default0Update() {

        return getNombreFantasia();
    }

    public String default1Update() {

        return getRazonSocial();
    }

    public String default2Update() {

        return getDireccion();
    }

    public String default3Update() {

        return getTelefono();
    }

    //region > compareTo, toString
    @Override
    public int compareTo(final Empresa other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "nombreFantasia");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "nombreFantasia");
    }
    //endregion

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    EmpresaRepository empresaRepository;

    @Override public void Notificar() {

    }
}
