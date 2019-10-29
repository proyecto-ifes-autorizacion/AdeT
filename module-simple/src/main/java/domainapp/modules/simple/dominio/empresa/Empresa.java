package domainapp.modules.simple.dominio.empresa;

import java.util.Collection;
import java.util.List;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.i18n.TranslatableString;

import javax.jdo.annotations.*;

import com.google.common.collect.Lists;

import domainapp.modules.simple.dominio.SujetoGeneral;
import domainapp.modules.simple.dominio.trabajador.Trabajador;
import domainapp.modules.simple.dominio.trabajador.TrabajadorRepository;
import domainapp.modules.simple.dominio.vehiculo.Vehiculo;
import domainapp.modules.simple.dominio.vehiculo.VehiculoRepository;
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

    @Column(allowsNull = "false", length = 40)
    @Property()
    @Title()
    private String nombreFantasia;

    @Column(allowsNull = "false", length = 40)
    @Property()
    private String razonSocial;

    @Column(allowsNull = "false", length = 40)
    @Property()
    private String direccion;

    @Column(allowsNull = "false", length = 40)
    @Property()
    private String telefono;

    @Column(allowsNull = "false")
    @Property()
    private EstadoEmpresa estado;

    @Persistent(mappedBy = "empresa", defaultFetchGroup = "true")
    @Column(allowsNull = "true")
    @Property()
    private List<Trabajador> trabajadores;

    @Persistent(mappedBy = "empresa", defaultFetchGroup = "true")
    @Column(allowsNull = "true")
    @Property()
    private List<Vehiculo> vehiculos;

    public String iconName(){
        if (this.estado == EstadoEmpresa.Habilitada){
            return "Habilitada";
        } else if (this.estado == EstadoEmpresa.Inhabilitada){
            return "Inhabilitada";
        } else {
            return "Borrada";
        }
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

    @NotPersistent
    public List<Empresa> getHabilitada(){
        return empresaRepository.Listar(EstadoEmpresa.Habilitada);
    }

    @NotPersistent
    public List<Empresa> getInhabilitada(){
        return empresaRepository.Listar(EstadoEmpresa.Inhabilitada);
    }

    @NotPersistent
    public List<Empresa> getBorrada(){
        return empresaRepository.Listar(EstadoEmpresa.Borrada);
    }

    public String getNombreFantasia(){
        return this.nombreFantasia;
    }

    @Action()
    @ActionLayout(named = "Editar")
    public Empresa update(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Nombre Fantasia: ")
            final String nombreFantasia,

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Razon Social: ")
            final String razonSocial,

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Direccion: ")
            final String direccion,

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Telefono: ")
            final String telefono){

        this.nombreFantasia = nombreFantasia;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.telefono = telefono;
        return this;
    }

    public String default0Update() {return getNombreFantasia();}

    public String default1Update() {return getRazonSocial();}

    public String default2Update() {return getDireccion();}

    public String default3Update() {return getTelefono();}

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
            trabajador.Actualizar();
        }
        for (Vehiculo vehiculo : vehiculos){
            vehiculo.Actualizar();
        }
    }

    @Programmatic
    public EstadoEmpresa ObtenerEstado(){
        return this.estado;
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

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    VehiculoRepository vehiculoRepository;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    EmpresaRepository empresaRepository;
}
