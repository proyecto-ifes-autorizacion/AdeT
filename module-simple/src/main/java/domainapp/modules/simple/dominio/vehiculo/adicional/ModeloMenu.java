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
        objectType = "Modelo",
        repositoryFor = Modelo.class
)
@DomainServiceLayout(
        named = "",
        menuOrder = ""
)
public class ModeloMenu {

    @Action()
    @ActionLayout(named = "Crear Modelo")
    @MemberOrder(sequence = "1")
    public Modelo create(
            @Parameter(maxLength = 30)
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


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Modelo")
    @MemberOrder(sequence = "2")
    public Modelo findByNombre(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Por Nombre: ")
            final Modelo modelo) {

        return modelo;
    }

    public List<Modelo> choices0FindByNombre() {return modelorepository.listAll();}

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listado de Modelos")
    @MemberOrder(sequence = "3")
    public java.util.List<Modelo> listAll() {
        return modelorepository.listAll();
    }

    @javax.inject.Inject
    ModeloRepository modelorepository;

    @javax.inject.Inject
    MarcaRepository marcaRepository;
}
