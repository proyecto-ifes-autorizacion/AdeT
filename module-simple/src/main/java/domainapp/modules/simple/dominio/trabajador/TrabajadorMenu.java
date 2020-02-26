package domainapp.modules.simple.dominio.trabajador;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.value.Blob;

import domainapp.modules.simple.dominio.empresa.Empresa;
import domainapp.modules.simple.dominio.empresa.EmpresaRepository;
import domainapp.modules.simple.dominio.reportes.EjecutarReportes;
import net.sf.jasperreports.engine.JRException;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "Trabajador",
        repositoryFor = Trabajador.class
)
@DomainServiceLayout(
        named = "Trabajador",
        menuOrder = ""
)
public class TrabajadorMenu {

    @Action()
    @ActionLayout(named = "Listado Exportado")
    public Blob ExportarListado() throws JRException, IOException {
        EjecutarReportes ejecutarReportes = new EjecutarReportes();
        return ejecutarReportes.ListadoTrabajadorPDF(trabajadorrepository.Listar());
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Trabajadores"
    )
    @MemberOrder(sequence = "1")
    public java.util.List<Trabajador> listAll() {
        return trabajadorrepository.Listar();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Buscar"
    )
    @MemberOrder(sequence = "2")
    public Trabajador findByCuil(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Trabajador: ")
            final Trabajador trabajador) {

        return trabajador;
    }

    public List<Trabajador> choices0FindByCuil() {return trabajadorrepository.Listar();}

    @Action(
    )
    @ActionLayout(named = "Crear")
    @MemberOrder(sequence = "3")
    public Trabajador create(

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
            final Empresa empresa) {

        return trabajadorrepository.create(cuil, nombre, apellido, fechaNacimiento, empresa);
    }

    public String validate0Create(final String cuil) {
        return ValidarCuil(cuil);
    }
    public List<Empresa> choices4Create() {return empresaRepository.Listar();}

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

    @javax.inject.Inject
    TrabajadorRepository trabajadorrepository;

    @javax.inject.Inject
    EmpresaRepository empresaRepository;
}
