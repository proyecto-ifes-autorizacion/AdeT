package domainapp.modules.simple.dominio;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;

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
            @ParameterLayout(named="Cuil: ")
            final String cuil,

            @ParameterLayout(named="Nombre: ")
            final String nombre,

            @ParameterLayout(named="Apellido: ")
            final String apellido)
    {
        return empleadorepository.create(cuil, nombre, apellido);
    }

    @javax.inject.Inject
    EmpleadoRepository empleadorepository;
}
