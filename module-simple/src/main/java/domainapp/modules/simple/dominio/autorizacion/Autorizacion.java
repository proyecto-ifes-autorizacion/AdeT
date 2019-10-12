package domainapp.modules.simple.dominio.autorizacion;

import java.util.Collection;
import java.util.List;

import org.apache.isis.applib.annotation.*;

import javax.jdo.annotations.*;

import com.google.common.collect.Lists;
import com.google.inject.internal.cglib.proxy.$Enhancer;

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
public class Autorizacion implements Comparable<Autorizacion>, SujetoGeneral {

    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Property(hidden = Where.EVERYWHERE)
    private int idAdeT;

    @Column(allowsNull = "true", length = 40)
    @Property(editing = Editing.ENABLED)
    private String titulo;

    public String disableTitulo() {
        return this.estado != EstadoAutorizacion.Abierta ? "x" : null;
    }

    @Column(allowsNull = "true", length = 4000)
    @Property(editing = Editing.ENABLED)
    private String descripcion;

    public String disableDescripcion() {
        return this.estado != EstadoAutorizacion.Abierta ? "x" : null;
    }

    @Column(allowsNull = "true", length = 60)
    @Property(editing = Editing.ENABLED)
    private String ubicacion;

    public String disableUbicacion() {
        return this.estado != EstadoAutorizacion.Abierta ? "x" : null;
    }

    @Column(allowsNull = "true")
    @Property()
    private LocalDateTime apertura;

    @Column(allowsNull = "true")
    @Property()
    private LocalDateTime cierre;

    @Column(allowsNull = "true", length = 80)
    @Property()
    private String cancelacion;

    public boolean hideCancelacion() {
        return !(this.estado == EstadoAutorizacion.Cancelada);
    }

    @Column(allowsNull = "false")
    @Property()
    private EstadoAutorizacion estado;

    @Column(allowsNull = "true", name = "sol_empresa_id")
    @Property()
    @PropertyLayout(named = "Empresa")
    private Empresa solicitanteEmpresa;

    @Column(allowsNull = "true", name = "sol_trabajador_id")
    @Property()
    @PropertyLayout(named = "Trabajador")
    private Trabajador solicitanteTrabajador;

    @Column(allowsNull = "true", name = "sol_vehiculo_id")
    @Property()
    @PropertyLayout(named = "Vehiculo")
    private Vehiculo solicitanteVehiculo;

    @Persistent(mappedBy = "autorizacion", dependentElement = "true")
    @Column(allowsNull = "true")
    @Property()
    private List<Ejecutante> ejecutantes;

    public String title() {
        return "Autorizacion: " + getIdAdeT();
    }

    public Autorizacion() {
    }

    public Autorizacion(EstadoAutorizacion estado) {

        this.estado = estado;
    }

    public Autorizacion(
            int idAdeT, EstadoAutorizacion estado, String titulo, String descripcion, String ubicacion,
            LocalDateTime apertura, LocalDateTime cierre, Empresa solicitanteEmpresa, Trabajador solicitanteTrabajador,
            Vehiculo solicitanteVehiculo, List<Ejecutante> ejecutantes) {

        this.idAdeT = idAdeT;
        this.estado = estado;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.apertura = apertura;
        this.cierre = cierre;
        this.solicitanteEmpresa = solicitanteEmpresa;
        this.solicitanteTrabajador = solicitanteTrabajador;
        this.solicitanteVehiculo = solicitanteVehiculo;
        this.ejecutantes = ejecutantes;
    }

    @Programmatic
    public void CambiarEstado(EstadoAutorizacion estado) {
        this.estado = estado;
    }

    @Action()
    public Autorizacion Liberar(

            @ParameterLayout(named = "Apertura: ") final LocalDateTime apertura) {

        this.apertura = apertura;
        CambiarEstado(EstadoAutorizacion.Liberada);
        Notificar();
        return this;
    }

    public LocalDateTime default0Liberar() {
        return LocalDateTime.now();
    }

    public String disableLiberar() {
        if (this.titulo == null) {
            return "Complete Titulo";
        } else if (this.descripcion == null) {
            return "Complete Descripcion";
        } else if (this.ubicacion == null) {
            return "Complete Ubicacion";
        } else if (this.solicitanteEmpresa == null) {
            return "Complete Empresa Solicitante";
        } else if (this.solicitanteTrabajador == null) {
            return "Complete Trabajador Solicitante";
        } else if (this.solicitanteVehiculo == null) {
            return "Complete Vehiculo Solicitante";
        } else {
            return null;
        }
    }

    public boolean hideLiberar() {
        return this.estado != EstadoAutorizacion.Abierta;
    }

    @Action()
    public Autorizacion Anular() {
        CambiarEstado(EstadoAutorizacion.Anulada);
        return this;
    }

    public boolean hideAnular() {
        return this.estado != EstadoAutorizacion.Abierta;
    }

    @Action()
    public Autorizacion Cancelar(

            @ParameterLayout(named = "Cierre: ") final LocalDateTime cierre,

            @ParameterLayout(named = "Motivo cancelacion: ") final String cancelacion) {

        this.cierre = cierre;
        this.cancelacion = cancelacion;
        CambiarEstado(EstadoAutorizacion.Cancelada);
        Notificar();
        return this;
    }

    public LocalDateTime default0Cancelar() {
        return LocalDateTime.now();
    }

    public boolean hideCancelar() {
        return this.estado != EstadoAutorizacion.Liberada;
    }

    @Action()
    public Autorizacion Cerrar(

            @ParameterLayout(named = "Cierre: ") final LocalDateTime cierre) {

        this.cierre = cierre;
        CambiarEstado(EstadoAutorizacion.Cerrada);
        Notificar();
        return this;
    }

    public LocalDateTime default0Cerrar() {
        return LocalDateTime.now();
    }

    public boolean hideCerrar() {
        return this.estado != EstadoAutorizacion.Liberada;
    }

    @Action()
    public Autorizacion Abrir() {
        CambiarEstado(EstadoAutorizacion.Abierta);
        return this;
    }

    @Action()
    @ActionLayout(named = "Agregar")
    public Autorizacion AgregarEmpresaSolicitante(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Empresa")
            final Empresa empresa) {

        this.solicitanteEmpresa = empresa;
        return this;
    }

    public List<Empresa> choices0AgregarEmpresaSolicitante() {
        return empresaRepository.Listar(EstadoEmpresa.Habilitada);
    }

    public boolean hideAgregarEmpresaSolicitante() {
        if (this.estado == EstadoAutorizacion.Abierta) {
            return this.solicitanteEmpresa != null;
        } else {
            return true;
        }
    }

    @Action()
    @ActionLayout(named = "Editar")
    public Autorizacion EditarEmpresaSolicitante(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Empresa")
            final Empresa empresa) {

        if (this.solicitanteEmpresa != empresa) {

            this.solicitanteTrabajador = null;
            this.solicitanteVehiculo = null;
        }

        this.solicitanteEmpresa = empresa;
        return this;
    }

    public List<Empresa> choices0EditarEmpresaSolicitante() {
        return empresaRepository.Listar(EstadoEmpresa.Habilitada);
    }

    public Empresa default0EditarEmpresaSolicitante() {
        return this.solicitanteEmpresa;
    }

    public boolean hideEditarEmpresaSolicitante() {
        if (this.estado == EstadoAutorizacion.Abierta) {
            return this.solicitanteEmpresa == null;
        } else {
            return true;
        }
    }

    @Action()
    @ActionLayout(named = "Quitar")
    public Autorizacion QuitarEmpresaSolicitante(){

        this.solicitanteEmpresa = null;
        this.solicitanteTrabajador = null;
        this.solicitanteVehiculo = null;
        return this;
    }

    public boolean hideQuitarEmpresaSolicitante() {
        if (this.estado == EstadoAutorizacion.Abierta) {
            return this.solicitanteEmpresa == null;
        } else {
            return true;
        }
    }

    @Action()
    @ActionLayout(named = "Agregar")
    public Autorizacion AgregarTrabajadorSolicitante(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Trabajador")
            final Trabajador trabajador){

        this.solicitanteTrabajador = trabajador;
        return this;
    }

    public List<Trabajador> choices0AgregarTrabajadorSolicitante() {
        return trabajadorRepository.Listar(this.solicitanteEmpresa, EstadoGeneral.Habilitado);
    }

    public boolean hideAgregarTrabajadorSolicitante() {
        if (this.estado != EstadoAutorizacion.Abierta) {
            return true;
        } else if (this.solicitanteEmpresa == null) {
            return true;
        } else if (this.solicitanteTrabajador != null) {
            return true;
        } else {
            return false;
        }
    }

    @Action()
    @ActionLayout(named = "Editar")
    public Autorizacion EditarTrabajadorSolicitante(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Trabajador")
            final Trabajador trabajador){

        this.solicitanteTrabajador = trabajador;
        return this;
    }

    public List<Trabajador> choices0EditarTrabajadorSolicitante() {
        return trabajadorRepository.Listar(this.solicitanteEmpresa, EstadoGeneral.Habilitado);
    }

    public boolean hideEditarTrabajadorSolicitante() {
        if (this.estado != EstadoAutorizacion.Abierta) {
            return true;
        } else if (this.solicitanteEmpresa == null) {
            return true;
        } else if (this.solicitanteTrabajador == null) {
            return true;
        } else {
            return false;
        }
    }

    public Trabajador default0EditarTrabajadorSolicitante() {
        return this.solicitanteTrabajador;
    }

    @Action()
    @ActionLayout(named = "Quitar")
    public Autorizacion QuitarTrabajadorSolicitante(){

        this.solicitanteTrabajador = null;
        return this;
    }

    public boolean hideQuitarTrabajadorSolicitante() {
        if (this.estado != EstadoAutorizacion.Abierta) {
            return true;
        } else if (this.solicitanteEmpresa == null) {
            return true;
        } else if (this.solicitanteTrabajador == null) {
            return true;
        } else {
            return false;
        }
    }

    @Action()
    @ActionLayout(named = "Agregar")
    public Autorizacion AgregarVehiculoSolicitante(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Vehiculo")
            final Vehiculo vehiculo){

        this.solicitanteVehiculo = vehiculo;
        return this;
    }

    public List<Vehiculo> choices0AgregarVehiculoSolicitante() {
        return vehiculoRepository.List(this.solicitanteEmpresa, EstadoGeneral.Habilitado);
    }

    public boolean hideAgregarVehiculoSolicitante() {
        if (this.estado != EstadoAutorizacion.Abierta) {
            return true;
        } else if (this.solicitanteEmpresa == null) {
            return true;
        } else if (this.solicitanteVehiculo != null) {
            return true;
        } else {
            return false;
        }
    }

    @Action()
    @ActionLayout(named = "Editar")
    public Autorizacion EditarVehiculoSolicitante(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Vehiculo")
            final Vehiculo vehiculo){

        this.solicitanteVehiculo = vehiculo;
        return this;
    }

    public List<Vehiculo> choices0EditarVehiculoSolicitante() {
        return vehiculoRepository.List(this.solicitanteEmpresa, EstadoGeneral.Habilitado);
    }

    public boolean hideEditarVehiculoSolicitante() {
        if (this.estado != EstadoAutorizacion.Abierta) {
            return true;
        } else if (this.solicitanteEmpresa == null) {
            return true;
        } else if (this.solicitanteVehiculo == null) {
            return true;
        } else {
            return false;
        }
    }

    public Vehiculo default0EditarVehiculoSolicitante() {
        return this.solicitanteVehiculo;
    }

    @Action()
    @ActionLayout(named = "Quitar")
    public Autorizacion QuitarVehiculoSolicitante(){

        this.solicitanteVehiculo = null;
        return this;
    }

    public boolean hideQuitarVehiculoSolicitante() {
        if (this.estado != EstadoAutorizacion.Abierta) {
            return true;
        } else if (this.solicitanteEmpresa == null) {
            return true;
        } else if (this.solicitanteVehiculo == null) {
            return true;
        } else {
            return false;
        }
    }

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

    @Action()
    @ActionLayout(named = "Quitar")
    public Autorizacion QuitarEjecutante(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Empresa")
            final Ejecutante ejecutante){

        ejecutanteRepository.delete(ejecutante);
        return this;
    }

    public List<Ejecutante> choices0QuitarEjecutante() {

        return ejecutanteRepository.Listar(this);
    }

    //************************************************************************
    //****************Propiedades de entiadad ejecutante**********************
    //************************************************************************
//    @NotPersistent()
//    private static int indice;
//
//    public int defaultIndice() { return 0; }
//
//    @Programmatic
//    public int Cant(){
//        return this.ejecutantes.size();
//    }
//
//    @Action()
//    public Autorizacion Siguiente(){
//        this.ejecutantes.iterator().next();
//        this.indice = this.indice ++;
//        return this;
//    }
//
//    public String disableSiguiente() {
//        if (this.indice <= Cant()){
//            return null;
//        } else {
//            return "limite";
//        }
//    }
//
//    @Action()
//    public Autorizacion Anterior(){
//        this.indice = this.indice --;
//        return this;
//    }
//
//    public String disableAnterior() {
//        if (0 <= this.indice){
//            return null;
//        } else {
//            return "limite";
//        }
//    }
//
//    @NotPersistent()
//    private Empresa ejecutanteEmpresa;
//
//    public void getEjecutanteEmpresa(){
//        if (Cant() == 0){
//            this.ejecutanteEmpresa = null;
//        } else {
//            this.ejecutanteEmpresa = this.ejecutantes.get(indice).getEmpresa();
//        }
//    }
//
//    @NotPersistent()
//    private List<Trabajador> ejecutantesTrabajadores;
//
//    public void getEjecutantesTrabajadores(){
//        this.ejecutantesTrabajadores = ejecutanteRepository.Listar(this, ejecutanteEmpresa).getTrabajadores();
//    }

    //**********Metodo para notificar a las entidades dependientes**********
    @Override
    public void Notificar() {
        this.solicitanteTrabajador.Actuliazar(this.estado);
        this.solicitanteVehiculo.Actuliazar(this.estado);
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
    Ejecutante ejecutante;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    EjecutanteRepository ejecutanteRepository;

}
