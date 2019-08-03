package domainapp.modules.simple.dominio;

import java.util.List;

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
        objectType = "Marca",
        repositoryFor = Marca.class
)
@DomainServiceLayout(
        named = "",
        menuOrder = ""
)
public class MarcaMenu {

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<Marca> listAll() {
        return marcarepository.listAll();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public java.util.List<Marca> findByNombre(
            final String nombre
    ) {
        return marcarepository.findByNombreContains(nombre);
    }

    @Action(
    )
    @MemberOrder(sequence = "3")
    public Marca create(
            @ParameterLayout(named = "Marca: ")
            final String nombre) {
        final boolean baja = false;
        final List<Modelo> modelos = null;
        return marcarepository.create(nombre, baja, modelos);
    }

    @javax.inject.Inject
    MarcaRepository marcarepository;
}