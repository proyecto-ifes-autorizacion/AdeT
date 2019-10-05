package domainapp.modules.simple.dominio.autorizacion;

import java.util.List;

import org.apache.isis.applib.annotation.*;

import javax.jdo.annotations.*;

import com.google.common.collect.Lists;

import domainapp.modules.simple.dominio.EstadoGeneral;
import domainapp.modules.simple.dominio.ObservadorGeneral;
import domainapp.modules.simple.dominio.SujetoGeneral;
import domainapp.modules.simple.dominio.empresa.Empresa;
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
        table = "Ejecutante"
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
                        + "FROM domainapp.modules.simple.dominio.autorizacion.Ejecutante "),
        @Query(
                name = "findByAutorizacionContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.autorizacion.Ejecutante "
                        + "WHERE autorizacion.indexOf(:autorizacion) >= 0 "),
        @Query(
                name = "findByAutorizacion", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.autorizacion.Ejecutante "
                        + "WHERE autorizacion == :autorizacion ")
})
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@Getter @Setter
public class Ejecutante implements Comparable<Ejecutante>, ObservadorGeneral, SujetoGeneral {

    @Column(allowsNull = "false")
    @Property()
    private Autorizacion autorizacion;

    @Column(allowsNull = "false")
    @Property()
    private Empresa empresa;

    @Persistent(table = "ejecutante_trabajador", defaultFetchGroup = "true")
    @Join(column = "ejecutante_id_oid")
    @Element(column = "trabajador_id_eid")
    @Column(allowsNull = "false")
    @Property()
    private List<Trabajador> trabajadores;

    @Persistent(table = "ejecutante_vehiculo", defaultFetchGroup = "true")
    @Join(column = "ejecutante_id_oid")
    @Element(column = "vehiculo_id_eid")
    @Column(allowsNull = "false")
    @Property()
    private List<Vehiculo> vehiculos;

    public Ejecutante(){}

    public Ejecutante(Autorizacion autorizacion, Empresa empresa){

        this.autorizacion = autorizacion;
        this.empresa = empresa;
    }

    public Ejecutante(Autorizacion autorizacion, Empresa empresa, List<Trabajador> trabajadores, List<Vehiculo> vehiculos){

        this.autorizacion = autorizacion;
        this.empresa = empresa;
        this.trabajadores = trabajadores;
        this.vehiculos = vehiculos;
    }

    public Empresa getEmpresa(){
        return this.empresa;
    }

    public List<Trabajador> getTrabajadores(){
        return this.trabajadores;
    }

    public List<Vehiculo> getVehiculos(){
        return this.vehiculos;
    }

    @Action()
    @ActionLayout(named = "Agregar")
    public Ejecutante AgregarTrabajador(
            
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Trabajador: ")        
            final Trabajador trabajador){

        this.trabajadores.add(trabajador);
        return this;
    }

    public List<Trabajador> choices0AgregarTrabajador() {
        List<Trabajador> trabajadores;
        trabajadores = trabajadorRepository.Listar(EstadoGeneral.Habilitado);
        trabajadores.removeAll(this.trabajadores);
        return trabajadores;
    }

    @Action()
    @ActionLayout(named = "Quitar")
    public Ejecutante QuitarTrabajador(

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Trabajador: ")
            final Trabajador trabajador){

        this.trabajadores.remove(trabajador);
        return this;
    }

    public List<Trabajador> choices0QuitarTrabajador() {
        return this.trabajadores;
    }

    @Action()
    @ActionLayout(named = "Agregar")
    public Ejecutante AgregarVehiculo(

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Vehiculo: ")
            final Vehiculo vehiculo){

        this.vehiculos.add(vehiculo);
        return this;
    }

    public List<Vehiculo> choices0AgregarVehiculo() {
        List<Vehiculo> vehiculos;
        vehiculos = vehiculoRepository.List(EstadoGeneral.Habilitado);
        vehiculos.removeAll(this.vehiculos);
        return vehiculos;
    }

    @Action()
    @ActionLayout(named = "Quitar")
    public Ejecutante QuitarVehiculo(

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Vehiculo: ")
            final Vehiculo vehiculo){

        this.vehiculos.remove(vehiculo);
        return this;
    }

    public List<Vehiculo> choices0QuitarVehiculo() {
        return this.vehiculos;
    }

    //region > compareTo, toString
    @Override
    public int compareTo(final Ejecutante other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "autorizacion");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "autorizacion");
    }

    @Override
    public void Actuliazar() {

    }

    @Override
    public void Notificar() {

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

}
