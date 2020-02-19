package domainapp.modules.simple.dominio.vehiculo.adicional;

import java.util.List;

import com.google.common.collect.Lists;

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
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Marcas"
    )
    @MemberOrder(sequence = "1")
    public List<Marca> listAll() {
        List<Marca> marcas = marcarepository.listAll();
        return marcas;
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Buscar"
    )
    @MemberOrder(sequence = "2")
    public Marca findByNombre(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Marca: ")
            final Marca marca) {

        return marca;
    }

    public List<Marca> choices0FindByNombre() {return marcarepository.listAll();}

    @Action()
    @ActionLayout(named = "Crear")
    @MemberOrder(sequence = "3")
    public Marca create(
            @Parameter(maxLength = 30)
            @ParameterLayout(named = "Marca: ")
            final String nombre) {

        return marcarepository.create(nombre);
    }

    @javax.inject.Inject
    MarcaRepository marcarepository;
}
