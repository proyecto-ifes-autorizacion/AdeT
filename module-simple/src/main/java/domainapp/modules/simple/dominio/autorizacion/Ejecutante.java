package domainapp.modules.simple.dominio.autorizacion;

import java.util.List;

import org.apache.isis.applib.annotation.*;

import javax.jdo.annotations.*;

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
                        + "WHERE autorizacion == :autorizacion "),
        @Query(
                name = "findByAutorizacionAndEmpresa", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.autorizacion.Ejecutante "
                        + "WHERE autorizacion == :autorizacion && empresa == :empresa ")
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

    public String title(){
        return this.empresa.getNombreFantasia();
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

    @Programmatic()
    public void AgregarTrabajador(final Trabajador trabajador){
        this.trabajadores.add(trabajador);
    }

    @Programmatic()
    public void QuitarTrabajador(final Trabajador trabajador){
        this.trabajadores.remove(trabajador);
    }

    @Programmatic()
    public void AgregarVehiculo(final Vehiculo vehiculo){
        this.vehiculos.add(vehiculo);
    }

    @Programmatic()
    public void QuitarVehiculo(final Vehiculo vehiculo){
        this.vehiculos.remove(vehiculo);
    }

    //Metodo para notificar a las entidades dependientes
    @Override
    public void Actuliazar() {

    }

    @Override
    public void Notificar() {

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
    //endregion

}
