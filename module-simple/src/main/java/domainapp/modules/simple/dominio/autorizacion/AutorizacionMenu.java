package domainapp.modules.simple.dominio.autorizacion;

import org.apache.isis.applib.annotation.*;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "Autorizacion",
        repositoryFor = Autorizacion.class
)
@DomainServiceLayout(
        named = "",
        menuOrder = ""
)
public class AutorizacionMenu {

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public java.util.List<Autorizacion> listAll() {
        return autorizacionrepository.listAll();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public java.util.List<Autorizacion> findByIdAdeT(
            final int idAdeT
    ) {
        return autorizacionrepository.findByIdAdeTContains(idAdeT);
    }

    @Action(
    )
    @MemberOrder(sequence = "3")
    public Autorizacion create(
            final int idAdeT,

            @ParameterLayout(named = "Titulo: ")
            final String titulo,

            @ParameterLayout(named = "Descripcion", multiLine = 10)
            final String descripcion,

            @ParameterLayout(named = "Ubicacion: ")
            final String ubicacion) {

        return autorizacionrepository.create(idAdeT, titulo, descripcion, ubicacion);
    }

    @javax.inject.Inject
    AutorizacionRepository autorizacionrepository;
}
