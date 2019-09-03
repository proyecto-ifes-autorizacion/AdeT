package domainapp.modules.simple.dominio.vehiculo;

import java.util.List;

import org.apache.isis.applib.annotation.*;

import javax.jdo.annotations.*;

import org.joda.time.LocalDate;

import domainapp.modules.simple.dominio.EstadoGeneral;
import domainapp.modules.simple.dominio.ObservadorGeneral;
import domainapp.modules.simple.dominio.empresa.Empresa;
import domainapp.modules.simple.dominio.empresa.EmpresaRepository;
import domainapp.modules.simple.dominio.vehiculo.adicional.Modelo;
import domainapp.modules.simple.dominio.vehiculo.adicional.ModeloRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "dominio",
        table = "Vehiculo"
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
                        + "FROM domainapp.modules.simple.dominio.vehiculo.Vehiculo "),
        @Query(
                name = "findByDominioContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.vehiculo.Vehiculo "
                        + "WHERE dominio.indexOf(:dominio) >= 0 "),
        @Query(
                name = "findByDominio", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.vehiculo.Vehiculo "
                        + "WHERE dominio == :dominio ")
})
@Unique(name = "Vehiculo_dominio_UNQ", members = { "dominio" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@Getter @Setter
public class Vehiculo implements Comparable<Vehiculo>, ObservadorGeneral {

    @Column(allowsNull = "false")
    @Property()
    private String dominio;

    @Column(allowsNull = "false")
    @Property()
    private Modelo modelo;

    @Column(allowsNull = "false")
    @Property()
    private LocalDate fechaAlta;

    @Column(allowsNull = "false")
    @Property()
    private Empresa empresa;

    @Column(allowsNull = "false")
    @Property()
    private EstadoGeneral estado;

    @Column(allowsNull = "false")
    @Property()
    private boolean bajaEmpresa;

    public Vehiculo(){}

    public Vehiculo(String dominio, Modelo modelo, LocalDate fechaAlta, Empresa empresa){

        this.dominio = dominio;
        this.modelo = modelo;
        this.fechaAlta = fechaAlta;
        this.empresa = empresa;
        this.estado = EstadoGeneral.Habilitado;
        this.bajaEmpresa = false;
    }

    public Vehiculo(String dominio, Modelo modelo, LocalDate fechaAlta, Empresa empresa, EstadoGeneral estado, boolean bajaEmpresa){

        this.dominio = dominio;
        this.modelo = modelo;
        this.fechaAlta = fechaAlta;
        this.empresa = empresa;
        this.estado = estado;
        this.bajaEmpresa = bajaEmpresa;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(named = "Editar")
    public Vehiculo update(

            @Parameter(maxLength = 20)
            @ParameterLayout(named = "Dominio: ")
            final String dominio,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Modelo: ")
            final Modelo modelo,

            @ParameterLayout(named = "Fecha Alta: ")
            final LocalDate fechaAlta,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Empresa: ")
            final Empresa empresa){

        this.dominio = dominio;
        this.modelo = modelo;
        this.fechaAlta = fechaAlta;
        this.empresa = empresa;
        return this;
    }

    public String default0Update() {return getDominio();}

    public Modelo default1Update() {return getModelo();}
    public List<Modelo> choices1Update() {return modeloRepository.ListActivos();}

    public LocalDate default2Update() {return getFechaAlta();}

    public Empresa default3Update() {return getEmpresa();}
    public List<Empresa> choices3Update() {return empresaRepository.Listar();}






    //region > compareTo, toString
    @Override
    public int compareTo(final Vehiculo other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "dominio");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "dominio");
    }
    //endregion

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    ModeloRepository modeloRepository;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    EmpresaRepository empresaRepository;

    @Override
    public void Actuliazar() {

    }
}
