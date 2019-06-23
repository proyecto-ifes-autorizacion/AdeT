package domainapp.modules.simple.dominio;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
@DomainObject(
        objectType = "Empleado"
)

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named = "Empleado",
        menuOrder = ""
)
public class EmpleadoMenu {

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public java.util.List<Empleado> listAll() {
        return empleadorepository.listAll();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public java.util.List<Empleado> findByDni(
            final int dni
    ) {
        return empleadorepository.findByDniContains(dni);
    }

    @Action(
    )
    @MemberOrder(sequence = "3")
    public Empleado create(

            @ParameterLayout(named = "DNI")
            final int dni,

            @ParameterLayout(named = "Nombre")
            final String nombre,

            @ParameterLayout(named = "Apellido")
            final  String apellido) {

        return empleadorepository.create(dni, nombre, apellido);
    }

    @javax.inject.Inject
    EmpleadoRepository empleadorepository;
}
