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
            final String titulo,
            final String descripcion,
            final String ubicacion) {

        AutorizacionEstado autorizacionEstado = AutorizacionEstado.Abierta;
        return autorizacionrepository.create(idAdeT, autorizacionEstado, titulo, descripcion, ubicacion);
    }

    @javax.inject.Inject
    AutorizacionRepository autorizacionrepository;
}
