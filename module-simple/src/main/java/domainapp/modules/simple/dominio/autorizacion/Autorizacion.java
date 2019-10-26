package domainapp.modules.simple.dominio.autorizacion;

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
                        + "WHERE idAdeT == :idAdeT "),
        @Query(
                name = "findByEstado", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.autorizacion.Autorizacion "
                        + "WHERE estado == :estado ")
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

    @Property(notPersisted = true)
    private String tiempo;

    public String getTiempo() {
        long diferancia;
        if (this.apertura != null && this.cierre != null){
            diferancia = (this.cierre.getDayOfYear() - this.apertura.getDayOfYear()) * 1440 +
                    (this.cierre.getHourOfDay() - this.apertura.getHourOfDay()) * 60 +
                    this.cierre.getMinuteOfHour() - this.apertura.getMinuteOfHour();
        } else if (this.apertura != null) {
            diferancia = (LocalDateTime.now().getDayOfYear() - this.apertura.getDayOfYear()) * 1440 +
                    (LocalDateTime.now().getHourOfDay() - this.apertura.getHourOfDay()) * 60 +
                    LocalDateTime.now().getMinuteOfHour() - this.apertura.getMinuteOfHour();
        } else {
            diferancia = 0;
        }
        return Long.toString((diferancia / 1440) % 24) + " D " + Long.toString((diferancia / 60) % 24) +" h " + Long.toString(diferancia % 60) + " m ";
    }

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
    public EstadoAutorizacion ObtenerEstado(){
        return this.estado;
    }

    @Programmatic
    private void CambiarEstado(EstadoAutorizacion estado) {
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
        } else if (this.ejecutantes.size() == 0) {
            return "Complete Ejecutante";
        } else if (VerificarEjecutantes()) {
            return "Complete los Trabajadores ejecutantes";
        } else {
            return null;
        }
    }

    public boolean hideLiberar() {
        return this.estado != EstadoAutorizacion.Abierta;
    }

    @Programmatic
    private boolean VerificarEjecutantes(){
        boolean resultado = false;
        for (Ejecutante ejecutante : ejecutantes) {
            if (ejecutante.getTrabajadores().size() == 0){
                resultado = true;
            }
        }
        return resultado;
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

            @ParameterLayout(named = "Cierre: ")
            final LocalDateTime cierre,

            @ParameterLayout(named = "Motivo cancelacion: ")
            final String cancelacion) {

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
        List<Empresa> empresas = empresaRepository.Listar(EstadoEmpresa.Habilitada);
        for (Ejecutante ejecutante : ejecutantes) {
            empresas.remove(ejecutante.getEmpresa());
        }
        return empresas;
    }

    public boolean hideAgregarEjecutante() {
        return this.estado != EstadoAutorizacion.Abierta;
    }

    public String disableAgregarEjecutante() {
        List<Empresa> empresas = empresaRepository.Listar(EstadoEmpresa.Habilitada);
        for (Ejecutante ejecutante : ejecutantes) {
            empresas.remove(ejecutante.getEmpresa());
        }
        return empresas.size() < 1 ? "No hay Empresas disponibles" : null;
    }

    @Action()
    @ActionLayout(named = "Quitar")
    public Autorizacion QuitarEjecutante(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Empresa")
            final Ejecutante ejecutante){

        ejecutanteRepository.delete(ejecutante);
        iterador.reinicio();
        return this;
    }

    public List<Ejecutante> choices0QuitarEjecutante() {
        return ejecutanteRepository.Listar(this);
    }

    public boolean hideQuitarEjecutante() {
        return this.estado != EstadoAutorizacion.Abierta;
    }

    public String disableQuitarEjecutante() {
        return ejecutanteRepository.Listar(this).size() < 1 ? "La lista de Empresas esta vacia" : null;
    }

    //************************************************************************
    //****************Propiedades de entidad ejecutante**********************
    //************************************************************************

    @Property(notPersisted = true)
    @PropertyLayout(named = "Empresa")
    private Empresa ejecutanteEmpresa;

    public Empresa getEjecutanteEmpresa(){
        return this.ejecutantes.size() > 0 ? this.ejecutantes.get(iterador.getIterador()).getEmpresa() : null;
    }

    @Collection(notPersisted = true)
    @CollectionLayout(named = "Trabajador")
    private List<Trabajador> ejecutanteTrabajadores;

    public List<Trabajador> getEjecutanteTrabajadores(){
        return this.ejecutantes.size() > 0 ? this.ejecutantes.get(iterador.getIterador()).getTrabajadores() : this.ejecutanteTrabajadores;
    }

    @Collection(notPersisted = true)
    @CollectionLayout(named = "Vehiculo")
    private List<Vehiculo> ejecutanteVehiculos;

    public List<Vehiculo> getEjecutanteVehiculos(){
        return this.ejecutantes.size() > 0 ? this.ejecutantes.get(iterador.getIterador()).getVehiculos() : this.ejecutanteVehiculos;
    }

    @Property(notPersisted = true, hidden = Where.EVERYWHERE)
    private IteradorEjecutante iterador = IteradorEjecutante.getInstance();

    @NotPersistent()
    @Property(hidden = Where.ALL_TABLES)
    private int indice;

    public int getIndice(){
        return iterador.getIterador();
    }

    @Action()
    public Autorizacion Siguiente(){
        iterador.setIterador(iterador.getIterador()+1);
        return this;
    }

    public String disableSiguiente() {
        return this.iterador.getIterador() < this.ejecutantes.size() - 1 ? null : "Ultima Posición";
    }

    public boolean hideSiguiente() {
        return this.ejecutantes.size() < 2;
    }

    @Action()
    public Autorizacion Anterior(){
        iterador.setIterador(iterador.getIterador()-1);
        return this;
    }

    public String disableAnterior() {
        return 0 < this.iterador.getIterador() ? null : "Primera Posición";
    }

    public boolean hideAnterior() {
        return this.ejecutantes.size() < 2;
    }

    @Action()
    @ActionLayout(named = "Agregar")
    public Autorizacion AgregarTrabajadorEjecutante(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Trabajador")
            final Trabajador trabajador){

        this.ejecutantes.get(iterador.getIterador()).AgregarTrabajador(trabajador);
        return this;
    }

    public List<Trabajador> choices0AgregarTrabajadorEjecutante() {
        List<Trabajador> trabajadores = trabajadorRepository.Listar(this.ejecutantes.get(iterador.getIterador()).getEmpresa(), EstadoGeneral.Habilitado);
        trabajadores.removeAll(this.ejecutantes.get(iterador.getIterador()).getTrabajadores());
        return trabajadores;
    }

    public boolean hideAgregarTrabajadorEjecutante() {
        if (this.estado != EstadoAutorizacion.Abierta){
            return true;
        } else {
            return this.ejecutantes.size() < 1;
        }
    }

    public String disableAgregarTrabajadorEjecutante() {
        List<Trabajador> trabajadores = trabajadorRepository.Listar(this.ejecutantes.get(iterador.getIterador()).getEmpresa(), EstadoGeneral.Habilitado);
        trabajadores.removeAll(this.ejecutantes.get(iterador.getIterador()).getTrabajadores());
        return trabajadores.size() < 1 ? "No hay mas trabajadores disponibles" : null;
    }

    @Action()
    @ActionLayout(named = "Quitar")
    public Autorizacion QuitarTrabajadorEjecutante(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Trabajador")
            final Trabajador trabajador){

        this.ejecutantes.get(iterador.getIterador()).QuitarTrabajador(trabajador);
        return this;
    }

    public List<Trabajador> choices0QuitarTrabajadorEjecutante() {
        return ejecutantes.get(iterador.getIterador()).getTrabajadores();
    }

    public boolean hideQuitarTrabajadorEjecutante() {
        if (this.estado != EstadoAutorizacion.Abierta){
            return true;
        } else {
            return this.ejecutantes.size() < 1;
        }
    }

    public String disableQuitarTrabajadorEjecutante() {
        return this.ejecutantes.get(iterador.getIterador()).getTrabajadores().size() < 1 ? "La lista de trabajadores esta vacia" : null;
    }

    @Action()
    @ActionLayout(named = "Agregar")
    public Autorizacion AgregarVehiculoEjecutante(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Vehiculo")
            final Vehiculo vehiculo){

        this.ejecutantes.get(iterador.getIterador()).AgregarVehiculo(vehiculo);
        return this;
    }

    public List<Vehiculo> choices0AgregarVehiculoEjecutante() {
        List<Vehiculo> vehiculos = vehiculoRepository.List(this.ejecutantes.get(iterador.getIterador()).getEmpresa(), EstadoGeneral.Habilitado);
        vehiculos.removeAll(this.ejecutantes.get(iterador.getIterador()).getVehiculos());
        return vehiculos;
    }

    public boolean hideAgregarVehiculoEjecutante() {
        if (this.estado != EstadoAutorizacion.Abierta){
            return true;
        } else {
            return this.ejecutantes.size() < 1;
        }
    }

    public String disableAgregarVehiculoEjecutante() {
        List<Vehiculo> vehiculos = vehiculoRepository.List(this.ejecutantes.get(iterador.getIterador()).getEmpresa(), EstadoGeneral.Habilitado);
        vehiculos.removeAll(this.ejecutantes.get(iterador.getIterador()).getVehiculos());
        return vehiculos.size() < 1 ? "No hay mas vehiculos disponibles" : null;
    }

    @Action()
    @ActionLayout(named = "Quitar")
    public Autorizacion QuitarVehiculoEjecutante(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Vehiculo")
            final Vehiculo vehiculo){

        this.ejecutantes.get(iterador.getIterador()).QuitarVehiculo(vehiculo);
        return this;
    }

    public List<Vehiculo> choices0QuitarVehiculoEjecutante() {
        return ejecutantes.get(iterador.getIterador()).getVehiculos();
    }

    public boolean hideQuitarVehiculoEjecutante() {
        if (this.estado != EstadoAutorizacion.Abierta){
            return true;
        } else {
            return this.ejecutantes.size() < 1;
        }
    }

    public String disableQuitarVehiculoEjecutante() {
        return this.ejecutantes.get(iterador.getIterador()).getVehiculos().size() < 1 ? "La lista de vehiculos esta vacia" : null;
    }

    //**********Metodo para notificar a las entidades dependientes**********
    @Override
    public void Notificar() {
        this.solicitanteTrabajador.Actualizar(this.estado);
        this.solicitanteVehiculo.Actualizar(this.estado);
        for (Ejecutante ejecuntate : ejecutantes) {
            ejecuntate.Actualizar();
        }
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
