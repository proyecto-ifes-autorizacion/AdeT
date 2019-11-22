package domainapp.modules.simple.dominio.autorizacion;

import java.util.List;
import org.apache.isis.applib.annotation.*;
import domainapp.modules.simple.dominio.reportes.EjecutarReportes;

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

    @Action()
    @ActionLayout(named = "Listado Exportado")
    public List<Autorizacion> ExportarListado() {
        EjecutarReportes ejecutarReportes = new EjecutarReportes();
        ejecutarReportes.ListadoAutorizacionPDF(autorizacionrepository.Listar());
        return autorizacionrepository.Listar();
    }


    @Property(notPersisted = true, hidden = Where.EVERYWHERE)
    private IteradorEjecutante iterador = IteradorEjecutante.getInstance();

    @Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listado de Autorizaciones")
    @MemberOrder(sequence = "1")
    public List<Autorizacion> listAll() {

        iterador.reinicio();
        return autorizacionrepository.Listar();
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar")
    @MemberOrder(sequence = "2")
    public List<Autorizacion> findByIdAdeT(

            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Estado: ")
            final EstadoAutorizacion estadoAutorizacion) {

        iterador.reinicio();
        return autorizacionrepository.Listar(estadoAutorizacion);
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
