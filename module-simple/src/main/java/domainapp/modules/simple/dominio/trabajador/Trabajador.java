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
                        + "FROM domainapp.modules.simple.dominio.trabajador.Trabajador "),
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
                        + "WHERE estado == :estado "),
        @Query(
                name = "findByEmpresa", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.trabajador.Trabajador "
                        + "WHERE empresa == :empresa "),
        @Query(
                name = "findByEmpresaAndEstado", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dominio.trabajador.Trabajador "
                        + "WHERE empresa == :empresa && estado == :estado")
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

    @Column(allowsNull = "false", length = largo)
    @Property()
    private String nombre;

    @Column(allowsNull = "false", length = largo)
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

    @NotPersistent()
    @Property(hidden = Where.EVERYWHERE)
    private final int largo = 40;

    @Programmatic()
    private String longitudExcesiva(final int longitud){
        return "Longitud Excedida en: " + (longitud-largo)+" "+((longitud-largo) == 1 ? "caracter.":"caracteres.");
    }

    public String title(){
        return getApellido()+", "+getNombre();
    }

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
    public List<Trabajador> getEjecucion(){
        return trabajadorRepository.Listar(EstadoGeneral.Ejecucion);
    }

    @NotPersistent
    public List<Trabajador> getHabilitado(){
        return trabajadorRepository.Listar(EstadoGeneral.Habilitado);
    }

    @NotPersistent
    public List<Trabajador> getInhabilitado(){
        return trabajadorRepository.Listar(EstadoGeneral.Inhabilitado);
    }

    @NotPersistent
    public List<Trabajador> getBorrado(){
        return trabajadorRepository.Listar(EstadoGeneral.Borrado);
    }

    @Action()
    @ActionLayout(named = "Editar")
    public Trabajador update(
            @ParameterLayout(named = "Cuil: ")
            String cuil,
            @ParameterLayout(named = "Nombre: ")
            String nombre,
            @ParameterLayout(named = "Apellido: ")
            String apellido,
            @ParameterLayout(named = "Fecha Nacimiento: ")
            LocalDate fechaNacimiento,
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Empresa: ")
            Empresa empresa){

        this.cuil = cuil;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.empresa = empresa;
        return this;
    }

    public String default0Update() {return getCuil();}

    public String default1Update() {return getNombre();}
    public TranslatableString validate1Update(final String nombre) {
        return nombre.length() <= largo ? null : TranslatableString.tr(longitudExcesiva(nombre.length()));
    }

    public String default2Update() {return getApellido();}
    public TranslatableString validate2Update(final String apellido) {
        return apellido.length() <= largo ? null : TranslatableString.tr(longitudExcesiva(apellido.length()));
    }

    public LocalDate default3Update() {return getFechaNacimiento();}

    public Empresa default4Update() {return getEmpresa();}
    public List<Empresa> choices4Update() {
        return empresaRepository.Listar(EstadoEmpresa.Habilitada);
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

    @Action()
    public Trabajador Inhabilitar(){
        CambiarEstado(EstadoGeneral.Inhabilitado);
        return this;
    }

    @Action()
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
