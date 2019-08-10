package domainapp.modules.simple.dominio;

import java.util.Date;
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
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.i18n.TranslatableString;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "dominio",
        table = "Empleado"
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
                        + "FROM domainapp.modules.simple.dominio.Empleado "),
        @Query(
                name = "findByCuilContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.Empleado "
                        + "WHERE cuil.indexOf(:cuil) >= 0 "),
        @Query(
                name = "findByCuil", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.Empleado "
                        + "WHERE cuil == :cuil ")
})
@Unique(name = "Empleado_cuil_UNQ", members = { "cuil" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@Getter @Setter

public class Empleado implements Comparable<Empleado> {

    @Column(allowsNull = "false", length = 13)
    @Property()
    private String cuil;

    @Column(allowsNull = "false", length = 40)
    @Property()
    private String nombre;

    @Column(allowsNull = "false", length = 40)
    @Property()
    private String apellido;

    @Column(allowsNull = "false")
    @Property()
    private Date fechaNacimiento;

    @Column(allowsNull = "false")
    @Property()
    private Empresa empresa;

    @Column(allowsNull = "false")
    @Property()
    private EstadoEmpleado estado;

    public String title(){

        return getApellido()+", "+getNombre();
    }

    @Action()
    public Empleado Ejecutar(){

        setEstado(EstadoEmpleado.Ejecucion);
        return this;
    }

    @Action()
    public Empleado Habilitar(){

        setEstado(EstadoEmpleado.Habilitado);
        return this;
    }

    @Action()
    public Empleado Inhabilitar(){

        setEstado(EstadoEmpleado.Inhabilitado);
        return this;
    }

    @Action()
    public Empleado Desactivar(){

        setEstado(EstadoEmpleado.Desactivado);
        return this;
    }

    public Empleado(final String cuil, final String nombre, final String apellido, final Date fechaNacimiento, final Empresa empresa, final EstadoEmpleado estado){

        this.cuil = cuil;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.empresa = empresa;
        this.estado = estado;
    }

    public Empleado(){}

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(named = "Editar")
    public Empleado update(

            @Parameter(maxLength = 13)
            @ParameterLayout(named = "Cuil: ")
            final String cuil,

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Nombre: ")
            final String nombre,

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Apellido: ")
            final String apellido,

            @ParameterLayout(named = "Fecha de Nacimiento: ")
            final Date fechaNacimiento,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Empresa: ")
            final Empresa empresa)
    {
        this.cuil = cuil;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.empresa = empresa;
        return this;
    }

    public String default0Update() {

        return getCuil();
    }
    public String default1Update() {

        return getNombre();
    }
    public String default2Update() {

        return getApellido();
    }
    public Date default3Update() {

        return getFechaNacimiento();
    }

    public Empresa default4Update() {

        return getEmpresa();
    }

    public List<Empresa> choices4Update() {

        return empresaRepository.listAll();
    }


    //validacion del CUIL, evaluar como optimizar este metodo
    public TranslatableString validate0Update(final String cuil) {
        return  cuil != null &&
                Character.isDigit(cuil.charAt(0)) &&
                Character.isDigit(cuil.charAt(1)) &&
                (Character.compare(cuil.charAt(2),'-') == 0)&&
                Character.isDigit(cuil.charAt(3)) &&
                Character.isDigit(cuil.charAt(4)) &&
                Character.isDigit(cuil.charAt(5)) &&
                Character.isDigit(cuil.charAt(6)) &&
                Character.isDigit(cuil.charAt(7)) &&
                Character.isDigit(cuil.charAt(8)) &&
                Character.isDigit(cuil.charAt(9)) &&
                Character.isDigit(cuil.charAt(10)) &&
                (Character.compare(cuil.charAt(11),'-') == 0)&&
                Character.isDigit(cuil.charAt(12)) ? null : TranslatableString.tr("Formato no valido xx-xxxxxxxx-x");
    }

    //region > compareTo, toString
    @Override
    public int compareTo(final Empleado other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "cuil");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "cuil");
    }
    //endregion

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(named = "eliminar")
    public void delete() {
        empleadorepository.delete(this);
    }

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    EmpleadoRepository empleadorepository;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    EmpresaRepository empresaRepository;

}
