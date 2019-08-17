package domainapp.modules.simple.dominio.empresa;

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
        objectType = "Empresa",
        repositoryFor = Empresa.class
)
@DomainServiceLayout(
        named = "Empresa",
        menuOrder = ""
)
public class EmpresaMenu {

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listar"
    )
    @MemberOrder(sequence = "1")
    public java.util.List<Empresa> listAll() {
        return empresarepository.listAll();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Buscar por nombre"
    )
    @MemberOrder(sequence = "2")
    public java.util.List<Empresa> findByNombreFantasia(
            final String nombreFantasia
    ) {
        return empresarepository.findByNombreFantasiaContains(nombreFantasia);
    }

    @Action(
    )
    @MemberOrder(sequence = "3")
    public Empresa create(
            @ParameterLayout(named = "Nombre de Fantasia: ")
            final String nombreFantasia,

            @ParameterLayout(named = "Razon Social: ")
            final String razonSocial,

            @ParameterLayout(named = "Direccion: ")
            final String direccion,

            @ParameterLayout(named = "Telefono: ")
            final String telefono)
    {
        return empresarepository.create(nombreFantasia,razonSocial,direccion,telefono);
    }

    @javax.inject.Inject
    EmpresaRepository empresarepository;
}
