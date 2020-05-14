package domainapp.application.fixture.scenarios;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.simple.dominio.empresa.Empresa;
import domainapp.modules.simple.dominio.trabajador.Trabajador;
import domainapp.modules.simple.dominio.trabajador.TrabajadorMenu;
import lombok.Getter;
import lombok.Setter;
import net.sf.cglib.core.Local;

public class TrabajadorCreate extends FixtureScript {

    private String cuil;
    private String nombre;
    private String apellido;
    private LocalDate fechaDeNacimiento;
    private Empresa empresa;

    public String getCuil() {
        return cuil;
    }

    public void setCuil(final String cuil) {
        this.cuil = cuil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(final String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(final LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(final Empresa empresa) {
        this.empresa = empresa;
    }


    private Trabajador trabajadorObject;

    public Trabajador getTrabajadorObject() {
        return trabajadorObject;
    }


    @Override
    protected void execute(final ExecutionContext ec) {

        String cuil = checkParam("cuil", ec, String.class);
        String nombre = checkParam("nombre", ec, String.class);
        String apellido = checkParam("apellido", ec, String.class);
        LocalDate fechaDeNacimiento = checkParam("fechaDeNacimiento", ec, LocalDate.class);
        Empresa empresa = checkParam("empresa", ec, Empresa.class);

        this.trabajadorObject = wrap(menu).create(cuil, nombre, apellido, fechaDeNacimiento, empresa);

        // also make available to UI
        ec.addResult(this, trabajadorObject);
    }

    @javax.inject.Inject
    TrabajadorMenu menu;
}
