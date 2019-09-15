package domainapp.modules.simple.dominio.autorizacion;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.schema.utils.jaxbadapters.PersistentEntityAdapter;

import javax.jdo.annotations.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.google.common.collect.Lists;

import org.joda.time.LocalDateTime;

import domainapp.modules.simple.dominio.EstadoGeneral;
import domainapp.modules.simple.dominio.SujetoGeneral;
import domainapp.modules.simple.dominio.empresa.Empresa;
import domainapp.modules.simple.dominio.empresa.EmpresaRepository;
import domainapp.modules.simple.dominio.empresa.EstadoEmpresa;
import domainapp.modules.simple.dominio.trabajador.Trabajador;
import domainapp.modules.simple.dominio.trabajador.TrabajadorRepository;
import domainapp.modules.simple.dominio.vehiculo.Vehiculo;
import domainapp.modules.simple.dominio.vehiculo.VehiculoRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "dominio",
        table = "Autorizacion"
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
                        + "FROM domainapp.modules.simple.dominio.autorizacion.Autorizacion "),
        @Query(
                name = "findByIdAdeTContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.autorizacion.Autorizacion "
                        + "WHERE idAdeT.indexOf(:idAdeT) >= 0 "),
        @Query(
                name = "findByIdAdeT", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.autorizacion.Autorizacion "
                        + "WHERE idAdeT == :idAdeT ")
})
@Unique(name = "Autorizacion_idAdeT_UNQ", members = { "idAdeT" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@Getter @Setter
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
public class Autorizacion implements Comparable<Autorizacion>, SujetoGeneral {

    @Column(allowsNull = "false")
    @Property(hidden = Where.EVERYWHERE)
    private int idAdeT;

    @Column(allowsNull = "false", length = 40)
    @Property()
    private String titulo;

    @Column(allowsNull = "false", length = 4000)
    @Property()
    private String descripcion;

    @Column(allowsNull = "false", length = 60)
    @Property()
    private String ubicacion;

    @Column(allowsNull = "true")
    @Property()
    private LocalDateTime apertura;

    @Column(allowsNull = "true")
    @Property()
    private LocalDateTime cierre;

    @Column(allowsNull = "false")
    @Property()
    private EstadoAutorizacion estado;

    @Column(allowsNull = "true")
    @Property()
    @PropertyLayout(named = "Trabajador")
    private Trabajador solicitante;

    @Column(allowsNull = "true")
    @Property()
    @PropertyLayout(named = "Vehiculo")
    private Vehiculo solicitanteVehiculo;

    @Persistent(mappedBy = "autorizacion", dependentElement = "true")
    @Column(allowsNull = "true")
    @Property()
    private List<Ejecutante> ejecutantes;

    public String title(){
        return "Autorizacion: " + getIdAdeT();
    }

    public Autorizacion(){}

    public Autorizacion(
            int idAdeT, String titulo, String descripcion, String ubicacion){

        this.idAdeT = idAdeT;
        this.estado = EstadoAutorizacion.Abierta;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
    }

    public Autorizacion(
            int idAdeT, EstadoAutorizacion estado, String titulo, String descripcion, String ubicacion,
            LocalDateTime apertura, LocalDateTime cierre, Trabajador solicitante, Vehiculo solicitanteVehiculo, List<Ejecutante> ejecutantes){

        this.idAdeT = idAdeT;
        this.estado = estado;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.apertura = apertura;
        this.cierre = cierre;
        this.solicitante = solicitante;
        this.solicitanteVehiculo = solicitanteVehiculo;
        this.ejecutantes = ejecutantes;
    }

    @Action()
    @ActionLayout(named = "Editar")
    public Autorizacion update(
            @ParameterLayout(named = "Titulo: ")
            final String titulo,

            @ParameterLayout(named = "Descripcion: ")
            final String descripcion,

            @ParameterLayout(named = "Ubicacion: ")
            final String ubicacion){

        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        return this;
    }

    public String default0Update() {return getTitulo();}
    public String default1Update() {return getDescripcion();}
    public String default2Update() {return getUbicacion();}

    @Programmatic
    public void CambiarEstado(EstadoAutorizacion estado){
        this.estado = estado;
    }

    @Action()
    public Autorizacion Liberar(){
        CambiarEstado(EstadoAutorizacion.Liberada);
        return this;
    }

    @Action()
    public Autorizacion Anular(){
        CambiarEstado(EstadoAutorizacion.Anulada);
        return this;
    }

    @Action()
    public Autorizacion Cancelar(){
        CambiarEstado(EstadoAutorizacion.Cancelada);
        return this;
    }

    @Action()
    public Autorizacion Cerrar(){
        CambiarEstado(EstadoAutorizacion.Cerrada);
        return this;
    }

    @Action()
    @ActionLayout(named = "Agregar")
    public Autorizacion AgregarSolicitante(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Trabajador")
            final Trabajador solicitante,

            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Vehiculo")
            final Vehiculo solicitanteVehiculo){

        this.solicitante = solicitante;
        this.solicitanteVehiculo = solicitanteVehiculo;
        return this;
    }

    public List<Trabajador> choices0AgregarSolicitante() {return trabajadorRepository.Listar(EstadoGeneral.Habilitado);}
    public List<Vehiculo> choices1AgregarSolicitante() {return vehiculoRepository.List(EstadoGeneral.Habilitado);}

    @Action()
    @ActionLayout(named = "Editar")
    public Autorizacion EditarSolicitante(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Trabajador")
            final Trabajador solicitante,

            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Vehiculo")
            final Vehiculo solicitanteVehiculo){

        this.solicitante = solicitante;
        this.solicitanteVehiculo = solicitanteVehiculo;
        return this;
    }

    public Trabajador default0EditarSolicitante() {return this.solicitante;}
    public List<Trabajador> choices0EditarSolicitante() {return trabajadorRepository.Listar(EstadoGeneral.Habilitado);}

    public Vehiculo default1EditarSolicitante() {return this.solicitanteVehiculo;}
    public List<Vehiculo> choices1EditarSolicitante() {return vehiculoRepository.List(EstadoGeneral.Habilitado);}


    @Action()
    @ActionLayout(named = "Agregar")
    public Autorizacion AgregarEjecutante(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Empresa")
            final Empresa empresa){

        ejecutantes.add(ejecutanteRepository.create(this, empresa));
        return this;
    }

    public List<Empresa> choices0AgregarEjecutante() {
        return empresaRepository.Listar(EstadoEmpresa.Habilitada);
    }

    //region > compareTo, toString
    @Override
    public int compareTo(final Autorizacion other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "idAdeT");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "idAdeT");
    }

    @Override
    public void Notificar() {

    }
    //endregion

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    EmpresaRepository empresaRepository;

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
    EjecutanteRepository ejecutanteRepository;

}
