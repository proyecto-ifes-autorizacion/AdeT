package domainapp.modules.simple.dominio;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.i18n.TranslatableString;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "Empleado",
        repositoryFor = Empleado.class
)
@DomainServiceLayout(
        named = "",
        menuOrder = ""
)
public class EmpleadoMenu {

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listar"
    )
    @MemberOrder(sequence = "1")
    public java.util.List<Empleado> listAll() {
        return empleadorepository.listAll();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Buscar por Cuil"
    )
    @MemberOrder(sequence = "2")
    public java.util.List<Empleado> findByCuil(
            final String cuil
    ) {
        return empleadorepository.findByCuilContains(cuil);
    }

    @Action(
    )
    @MemberOrder(sequence = "3")
    public Empleado create(
            @ParameterLayout(named = "Cuil: ")
            final String cuil,

            @ParameterLayout(named = "Nombre: ")
            final String nombre,

            @ParameterLayout(named = "Apellido: ")
            final String apellido,

            @ParameterLayout(named = "Fecha de Nacimiento: ")
            final Date fechaNacimiento,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Empresa: ")
            final Empresa empresa){

        EstadoEmpleado estado = EstadoEmpleado.Habilitado;
        return empleadorepository.create(cuil, nombre, apellido, fechaNacimiento, empresa, estado);
    }

    public List<Empresa> choices4Create() {

        return empresaRepository.listAll();
    }

    //validacion del CUIL, evaluar como optimizar este metodo
    public TranslatableString validate0Create(final String cuil) {
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

    @javax.inject.Inject
    EmpleadoRepository empleadorepository;

    @javax.inject.Inject
    EmpresaRepository empresaRepository;
}
