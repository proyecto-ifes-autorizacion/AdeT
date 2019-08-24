package domainapp.modules.simple.dominio.vehiculo;

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

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "Modelo",
        repositoryFor = Modelo.class
)
@DomainServiceLayout(
        named = "",
        menuOrder = ""
)
public class ModeloMenu {

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public java.util.List<Modelo> listAll() {
        return modelorepository.listAll();
    }

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "4")
    public List<Modelo> ListActivos() {
        return modelorepository.ListActivos();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public java.util.List<Modelo> findByNombre(
            final String nombre
    ) {
        return modelorepository.findByNombreContains(nombre);
    }

    @Action(
    )
    @MemberOrder(sequence = "3")
    public Modelo create(
            @ParameterLayout(named = "Modelo: ")
            final String nombre,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Marca: ")
            final Marca marca){

        return modelorepository.create(nombre, marca);
    }

    public List<Marca> choices1Create() {

        return marcaRepository.listAll();
    }

    @javax.inject.Inject
    ModeloRepository modelorepository;

    @javax.inject.Inject
    MarcaRepository marcaRepository;
}
