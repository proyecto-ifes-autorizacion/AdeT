package domainapp.modules.simple.dominio.vehiculo;

import java.util.List;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.i18n.TranslatableString;

import javax.jdo.annotations.*;

import org.joda.time.LocalDate;

import domainapp.modules.simple.dominio.EstadoGeneral;
import domainapp.modules.simple.dominio.ObservadorAutorizacion;
import domainapp.modules.simple.dominio.ObservadorGeneral;
import domainapp.modules.simple.dominio.autorizacion.EstadoAutorizacion;
import domainapp.modules.simple.dominio.empresa.Empresa;
import domainapp.modules.simple.dominio.empresa.EmpresaRepository;
import domainapp.modules.simple.dominio.empresa.EstadoEmpresa;
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
                        + "WHERE dominio == :dominio "),
        @Query(
                name = "findByEmpresa", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.vehiculo.Vehiculo "
                        + "WHERE empresa == :empresa "),
        @Query(
                name = "findByEstado", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.vehiculo.Vehiculo "
                        + "WHERE estado == :estado "),
        @Query(
                name = "findByEmpresaAndEstado", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.vehiculo.Vehiculo "
                        + "WHERE empresa == :empresa && estado == :estado")
})

@Unique(name = "Vehiculo_dominio_UNQ", members = { "dominio" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@Getter @Setter
public class Vehiculo implements Comparable<Vehiculo>, ObservadorGeneral, ObservadorAutorizacion {

    @Column(allowsNull = "false", length = largo)
    @Property()
    @Title()
    private String dominio;

    @Column(allowsNull = "false", name = "modelo_id")
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

    @NotPersistent()
    @Property(hidden = Where.EVERYWHERE)
    private final int largo = 20;

    @Programmatic()
    private String longitudExcesiva(final int longitud){
        return "Longitud Excedida en: " + (longitud-largo)+" "+((longitud-largo) == 1 ? "caracter.":"caracteres.");
    }

    public String title(){
        return modelo.title()+", "+getDominio();
    }

    public Vehiculo(){}

    public Vehiculo(String dominio, Modelo modelo, LocalDate fechaAlta, Empresa empresa){

        this.dominio = dominio;
        this.modelo = modelo;
        this.fechaAlta = fechaAlta;
        this.empresa = empresa;
        this.estado = EstadoGeneral.Habilitado;
        this.bajaEmpresa = BajaEmpresa();
    }

    public Vehiculo(String dominio, Modelo modelo, LocalDate fechaAlta, Empresa empresa, EstadoGeneral estado, boolean bajaEmpresa){

        this.dominio = dominio;
        this.modelo = modelo;
        this.fechaAlta = fechaAlta;
        this.empresa = empresa;
        this.estado = estado;
        this.bajaEmpresa = bajaEmpresa;
    }

    @NotPersistent
    public List<Vehiculo> getEjecucion(){
        return vehiculoRepository.List(EstadoGeneral.Ejecucion);
    }

    @NotPersistent
    public List<Vehiculo> getHabilitado(){
        return vehiculoRepository.List(EstadoGeneral.Habilitado);
    }

    @NotPersistent
    public List<Vehiculo> getInhabilitado(){
        return vehiculoRepository.List(EstadoGeneral.Inhabilitado);
    }

    @NotPersistent
    public List<Vehiculo> getBorrado(){
        return vehiculoRepository.List(EstadoGeneral.Borrado);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(named = "Editar")
    public Vehiculo update(

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Empresa: ")
            final Empresa empresa,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Modelo: ")
            final Modelo modelo,

            @Parameter(maxLength = 20)
            @ParameterLayout(named = "Dominio: ")
            final String dominio,

            @ParameterLayout(named = "Fecha Alta: ")
            final LocalDate fechaAlta){

        this.dominio = dominio;
        this.modelo = modelo;
        this.fechaAlta = fechaAlta;
        this.empresa = empresa;
        return this;
    }

    public Empresa default0Update() {return getEmpresa();}
    public List<Empresa> choices0Update() {return empresaRepository.Listar();}

    public Modelo default1Update() {return getModelo();}
    public List<Modelo> choices1Update() {return modeloRepository.ListActivos();}

    public String default2Update() {return getDominio();}
    public TranslatableString validate2Update(final String dominio) {
        return dominio.length() <= largo ? null : TranslatableString.tr(longitudExcesiva(dominio.length()));
    }

    public LocalDate default3Update() {return getFechaAlta();}

    @Programmatic
    public void CambiarEstado(EstadoGeneral estado){this.estado = estado;}

    @Programmatic
    public Vehiculo Ejecutar(){
        CambiarEstado(EstadoGeneral.Ejecucion);
        return this;
    }

    @Action()
    public Vehiculo Habilitar(){
        CambiarEstado(EstadoGeneral.Habilitado);
        return this;
    }

    @Action()
    public Vehiculo Inhabilitar(){
        CambiarEstado(EstadoGeneral.Inhabilitado);
        return this;
    }

    @Action()
    public Vehiculo Borrar(){
        CambiarEstado(EstadoGeneral.Borrado);
        return this;
    }

    public boolean hideHabilitar() {return this.estado == EstadoGeneral.Habilitado;}
    public boolean hideInhabilitar() {return this.estado == EstadoGeneral.Inhabilitado;}
    public boolean hideBorrar() {return this.estado == EstadoGeneral.Borrado;}

    @Override
    public void Actualizar() {
        if (empresa.ObtenerEstado() == EstadoEmpresa.Habilitada){
            this.bajaEmpresa = false;
        } else {
            this.bajaEmpresa = true;
        }
    }

    @Override
    public void Actualizar(final EstadoAutorizacion estadoAutorizacion) {
        if (this.estado == EstadoGeneral.Habilitado && estadoAutorizacion == EstadoAutorizacion.Liberada){
            this.estado = EstadoGeneral.Ejecucion;
        } else if (this.estado == EstadoGeneral.Ejecucion && estadoAutorizacion != EstadoAutorizacion.Liberada){
            this.estado = EstadoGeneral.Habilitado;
        }
    }

    @Programmatic
    public boolean BajaEmpresa(){
        Actualizar();
        return this.bajaEmpresa;
    }

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

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    VehiculoRepository vehiculoRepository;

}
