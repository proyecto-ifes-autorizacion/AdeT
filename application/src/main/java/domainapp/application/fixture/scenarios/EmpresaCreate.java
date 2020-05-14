package domainapp.application.fixture.scenarios;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.simple.dominio.empresa.Empresa;
import domainapp.modules.simple.dominio.empresa.EmpresaMenu;
import lombok.Getter;
import lombok.Setter;

public class EmpresaCreate extends FixtureScript {

    private String nombreFantasia;
    private String razonSocial;
    private String direccion;
    private String telefono;

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public void setNombreFantasia(final String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(final String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(final String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(final String telefono) {
        this.telefono = telefono;
    }


    private Empresa empresaObject;

    public Empresa getEmpresaObject() {
        return empresaObject;
    }


    @Override
    protected void execute(final ExecutionContext ec) {

        String nombreFantasia = checkParam("nombreFantasia", ec, String.class);
        String razonSocial = checkParam("razonSocial", ec, String.class);
        String direccion = checkParam("direccion", ec, String.class);
        String telefono = checkParam("telefono", ec, String.class);

        this.empresaObject = wrap(menu).create(nombreFantasia, razonSocial, direccion, telefono);

        // also make available to UI
        ec.addResult(this, empresaObject);
    }

    @javax.inject.Inject
    EmpresaMenu menu;
}
