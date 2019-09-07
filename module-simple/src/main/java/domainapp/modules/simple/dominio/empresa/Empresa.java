package domainapp.modules.simple.dominio.empresa;

import java.util.List;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.i18n.TranslatableString;

import javax.jdo.annotations.*;

import domainapp.modules.simple.dominio.SujetoGeneral;
import domainapp.modules.simple.dominio.trabajador.Trabajador;
import domainapp.modules.simple.dominio.trabajador.TrabajadorRepository;
import domainapp.modules.simple.dominio.vehiculo.Vehiculo;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
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
                        + "WHERE nombreFantasia == :nombreFantasia "),
        @Query(
                name = "findByEstado", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.empresa.Empresa "
                        + "WHERE estado == :estado ")
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

    @Column(allowsNull = "false", length = largo)
    @Property()
    @Title()
    private String nombreFantasia;

    @Column(allowsNull = "false", length = largo)
    @Property()
    private String razonSocial;

    @Column(allowsNull = "false", length = largo)
    @Property()
    private String direccion;

    @Column(allowsNull = "false", length = largo)
    @Property()
    private String telefono;

    @Column(allowsNull = "false")
    @Property()
    private EstadoEmpresa estado;

    @Column(allowsNull = "true")
    @Property()
    private List<Trabajador> trabajadores;

    @Column(allowsNull = "true")
    @Property()
    private List<Vehiculo> vehiculos;

    @NotPersistent()
    @Property(hidden = Where.EVERYWHERE)
    private final int largo = 40;

    @Programmatic()
    private String longitudExcesiva(final int longitud){
        return "Longitud Excedida en: " + (longitud-largo)+" "+((longitud-largo) == 1 ? "caracter.":"caracteres.");
    }

    public Empresa(){}

    public Empresa(
            String nombreFantasia,
            String razonSocial,
            String direccion,
            String telefono){

        this.nombreFantasia = nombreFantasia;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.estado = EstadoEmpresa.Habilitada;
    }

    public Empresa(
            String nombreFantasia,
            String razonSocial,
            String direccion,
            String telefono,
            EstadoEmpresa estado,
            List<Trabajador> trabajadores,
            List<Vehiculo> vehiculos){

        this.nombreFantasia = nombreFantasia;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.estado = estado;
        this.trabajadores = trabajadores;
        this.vehiculos = vehiculos;
    }

    @Action()
    @ActionLayout(named = "Editar")
    public Empresa update(
            @ParameterLayout(named = "Nombre Fantasia: ")
            String nombreFantasia,
            @ParameterLayout(named = "Razon Social: ")
            String razonSocial,
            @ParameterLayout(named = "Direccion: ")
            String direccion,
            @ParameterLayout(named = "Telefono: ")
            String telefono){

        this.nombreFantasia = nombreFantasia;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.telefono = telefono;
        return this;
    }

    public String default0Update() {return getNombreFantasia();}

    public TranslatableString validate0Update(final String nombreFantasia) {
        return nombreFantasia.length() <= largo ? null : TranslatableString.tr(longitudExcesiva(nombreFantasia.length()));
    }

    public String default1Update() {return getRazonSocial();}

    public TranslatableString validate1Update(final String razonSocial) {
        return razonSocial.length() <= largo ? null : TranslatableString.tr(longitudExcesiva(razonSocial.length()));
    }

    public String default2Update() {return getDireccion();}

    public TranslatableString validate2Update(final String direccion) {
        return direccion.length() <= largo ? null : TranslatableString.tr(longitudExcesiva(direccion.length()));
    }

    public String default3Update() {return getTelefono();}

    public TranslatableString validate3Update(final String telefono) {
        return telefono.length() <= largo ? null : TranslatableString.tr(longitudExcesiva(telefono.length()));
    }

    @Programmatic
    public void CambiarEstado(EstadoEmpresa estado){
        this.estado = estado;
        Notificar();
    }

    @Action()
    public Empresa Habilitar(){
        CambiarEstado(EstadoEmpresa.Habilitada);
        return this;
    }

    @Action()
    public Empresa Inhabilitar(){
        CambiarEstado(EstadoEmpresa.Inhabilitada);
        return this;
    }

    @Action()
    public Empresa Borrar(){
        CambiarEstado(EstadoEmpresa.Borrada);
        return this;
    }

    public boolean hideHabilitar() {return this.estado == EstadoEmpresa.Habilitada;}
    public boolean hideInhabilitar() {return this.estado == EstadoEmpresa.Inhabilitada;}
    public boolean hideBorrar() {return this.estado == EstadoEmpresa.Borrada;}

    @Override
    public void Notificar() {

        for (Trabajador trabajador : trabajadores){
            trabajador.Actuliazar();
        }
        for (Vehiculo vehiculo : vehiculos){
            vehiculo.Actuliazar();
        }
    }

    @Programmatic
    public EstadoEmpresa ObtenerEstado(){
        return this.estado;
    }

    @Action()
    public Empresa Obtener(){
        setTrabajadores(trabajadorRepository.Listar(this));
        return this;
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
    TrabajadorRepository trabajadorRepository;

}
