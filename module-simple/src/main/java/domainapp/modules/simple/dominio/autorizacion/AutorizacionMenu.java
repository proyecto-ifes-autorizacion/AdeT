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

    @Property(notPersisted = true, hidden = Where.EVERYWHERE)
    private IteradorEjecutante iterador = IteradorEjecutante.getInstance();

    @Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public java.util.List<Autorizacion> listAll() {

        iterador.reinicio();
        return autorizacionrepository.listAll();
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public java.util.List<Autorizacion> findByIdAdeT(
            final int idAdeT) {

        iterador.reinicio();
        return autorizacionrepository.findByIdAdeTContains(idAdeT);
    }

    @Action()
    @MemberOrder(sequence = "3")
    public Autorizacion create() {

        iterador.reinicio();
        return autorizacionrepository.create();
    }

    @javax.inject.Inject
    AutorizacionRepository autorizacionrepository;

}
