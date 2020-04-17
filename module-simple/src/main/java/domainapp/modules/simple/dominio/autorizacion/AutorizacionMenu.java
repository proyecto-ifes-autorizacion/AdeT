package domainapp.modules.simple.dominio.autorizacion;

import java.io.IOException;
import java.util.List;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.value.Blob;

import domainapp.modules.simple.dominio.reportes.EjecutarReportes;
import net.sf.jasperreports.engine.JRException;

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
    @ActionLayout(named = "Crear Autorizacion")
    @MemberOrder(sequence = "1")
    public Autorizacion create() {

        iterador.reinicio();
        return autorizacionrepository.create();
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Autorizacion")
    @MemberOrder(sequence = "2")
    public List<Autorizacion> findByIdAdeT(

            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Estado: ")
            final EstadoAutorizacion estadoAutorizacion) {

        iterador.reinicio();
        return autorizacionrepository.Listar(estadoAutorizacion);
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listado de Autorizaciones (Todas)")
    @MemberOrder(sequence = "3")
    public List<Autorizacion> listAll() {

        iterador.reinicio();
        return autorizacionrepository.Listar();
    }

    @Action()
    @ActionLayout(named = "Listado Exportado")
    public Blob ExportarListado() throws JRException, IOException {
        EjecutarReportes ejecutarReportes = new EjecutarReportes();
        return ejecutarReportes.ListadoAutorizacionPDF(autorizacionrepository.Listar());
    }

    @Property(notPersisted = true, hidden = Where.EVERYWHERE)
    private IteradorEjecutante iterador = IteradorEjecutante.getInstance();

    @javax.inject.Inject
    AutorizacionRepository autorizacionrepository;

}
