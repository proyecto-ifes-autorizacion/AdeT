package domainapp.modules.simple.dominio.trabajador;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.i18n.TranslatableString;

import javax.jdo.annotations.*;

import com.google.common.collect.Lists;

import org.joda.time.LocalDate;

import domainapp.modules.simple.dominio.EstadoGeneral;
import domainapp.modules.simple.dominio.ObservadorAutorizacion;
import domainapp.modules.simple.dominio.ObservadorGeneral;
import domainapp.modules.simple.dominio.autorizacion.EstadoAutorizacion;
import domainapp.modules.simple.dominio.empresa.Empresa;
import domainapp.modules.simple.dominio.empresa.EmpresaRepository;
import domainapp.modules.simple.dominio.empresa.EstadoEmpresa;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "dominio",
        table = "Trabajador"
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
                        + "FROM domainapp.modules.simple.dominio.trabajador.Trabajador "
                        + "ORDER BY apellido ASC, nombre ASC"),
        @Query(
                name = "findByCuilContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.trabajador.Trabajador "
                        + "WHERE cuil.indexOf(:cuil) >= 0 "),
        @Query(
                name = "findByCuil", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.trabajador.Trabajador "
                        + "WHERE cuil == :cuil "),
        @Query(
                name = "findByEstado", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.trabajador.Trabajador "
                        + "WHERE estado == :estado "
                        + "ORDER BY apellido ASC, nombre ASC"),
        @Query(
                name = "findByEmpresa", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.trabajador.Trabajador "
                        + "WHERE empresa == :empresa "
                        + "ORDER BY apellido ASC, nombre ASC"),
        @Query(
                name = "findByEmpresaAndEstado", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.trabajador.Trabajador "
                        + "WHERE empresa == :empresa && estado == :estado "
                        + "ORDER BY apellido ASC, nombre ASC")
})
@Unique(name = "Trabajador_cuil_UNQ", members = { "cuil" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@Getter @Setter
public class Trabajador implements Comparable<Trabajador>, ObservadorGeneral, ObservadorAutorizacion {

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
    private LocalDate fechaNacimiento;

    @Column(allowsNull = "false")
    @Property()
    private Empresa empresa;

    @Column(allowsNull = "false")
    @Property()
    private EstadoGeneral estado;

    @Column(allowsNull = "false")
    @Property(hidden = Where.ALL_TABLES)
    private boolean bajaEmpresa;

    public String title(){
        return getApellido()+", "+getNombre();
    }

    public String iconName(){
        if (this.estado == EstadoGeneral.Ejecucion){
            return "Ejecucion";
        } else if (this.estado == EstadoGeneral.Habilitado){
            return "Habilitado";
        } else if (this.estado == EstadoGeneral.Inhabilitado){
            return "Inhabilitado";}
        else {
            return "Borrado";
        }
    }

    public String RepoCuil(){ return this.cuil; }
    public String RepoNombre(){ return this.nombre; }
    public String RepoApellido(){ return this.apellido; }
    public String RepoFechaNacimiento(){ return this.fechaNacimiento.toString("dd-MM-yyyy"); }
    public String RepoEmpresa(){ return this.empresa.getNombreFantasia(); }
    public String RepoEstado() {return this.estado.toString(); }

    public Trabajador(){}

    public Trabajador(
            final String cuil,
            final String nombre,
            final String apellido,
            final LocalDate fechaNacimiento,
            final Empresa empresa){

        this.cuil = cuil;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.empresa = empresa;
        this.estado = EstadoGeneral.Habilitado;
        this.bajaEmpresa = BajaEmpresa();
    }

    public Trabajador(
            final String cuil,
            final String nombre,
            final String apellido,
            final LocalDate fechaNacimiento,
            final Empresa empresa,
            final EstadoGeneral estado,
            final boolean bajaEmpresa){

        this.cuil = cuil;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.empresa = empresa;
        this.estado = estado;
        this.bajaEmpresa = bajaEmpresa;
    }

    @NotPersistent
    @CollectionLayout(named = "Trabajadores en Ejecucion")
    public List<Trabajador> getEjecucion(){
        return trabajadorRepository.Listar(EstadoGeneral.Ejecucion);
    }

    @NotPersistent
    @CollectionLayout(named = "Trabajadores Habilitados")
    public List<Trabajador> getHabilitado(){
        return trabajadorRepository.Listar(EstadoGeneral.Habilitado);
    }

    @NotPersistent
    @CollectionLayout(named = "Trabajadores Inhabilitados")
    public List<Trabajador> getInhabilitado(){
        return trabajadorRepository.Listar(EstadoGeneral.Inhabilitado);
    }

    @NotPersistent
    @CollectionLayout(named = "Trabajadores Borrados")
    public List<Trabajador> getBorrado(){
        return trabajadorRepository.Listar(EstadoGeneral.Borrado);
    }

    @Action()
    @ActionLayout(named = "Editar")
    public Trabajador update(
            @Parameter(maxLength = 13)
            @ParameterLayout(named = "Cuil: ")
            final String cuil,

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Nombre: ")
            final String nombre,

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Apellido: ")
            final String apellido,

            @ParameterLayout(named = "Fecha Nacimiento: ")
            final LocalDate fechaNacimiento,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Empresa: ")
            final Empresa empresa){

        this.cuil = cuil;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.empresa = empresa;
        return this;
    }

    public String default0Update() {return getCuil();}

    public String validate0Update(final String cuil) {
        return ValidarCuil(cuil);
    }
    
    public String default1Update() {return getNombre();}

    public String default2Update() {return getApellido();}

    public LocalDate default3Update() {return getFechaNacimiento();}

    public Empresa default4Update() {return getEmpresa();}
    public List<Empresa> choices4Update() {
        return empresaRepository.Listar(EstadoEmpresa.Habilitada);
    }

    @Programmatic
    private String ValidarCuil(final String cuil){
        if (Character.isDigit(cuil.charAt(0)) &&
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
                Character.isDigit(cuil.charAt(12))){
            return null;
        } else {
            return "Formato no valido XX-XXXXXXXX-X";
        }
    }

    @Programmatic
    public void CambiarEstado(EstadoGeneral estado){
        this.estado = estado;
    }
    
    @Programmatic
    public Trabajador Ejecutar(){
        CambiarEstado(EstadoGeneral.Ejecucion);
        return this;
    }

    @Action()
    public Trabajador Habilitar(){
        CambiarEstado(EstadoGeneral.Habilitado);
        return this;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE)
    public Trabajador Inhabilitar(){
        CambiarEstado(EstadoGeneral.Inhabilitado);
        return this;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE)
    public Trabajador Borrar(){
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
    public int compareTo(final Trabajador other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "cuil");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "cuil");
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

}
