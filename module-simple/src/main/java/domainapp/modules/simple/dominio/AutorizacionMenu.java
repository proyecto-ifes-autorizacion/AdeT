package domainapp.modules.simple.dominio;

import java.util.Date;
import java.util.List;

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
    public java.util.List<Autorizacion> findByTitulo(
            final String titulo
    ) {
        return autorizacionrepository.findByTituloContains(titulo);
    }

    @Action(
    )
    @MemberOrder(sequence = "3")
    public Autorizacion create(

            @ParameterLayout(named = "Titulo: ")
            final String titulo,

            @ParameterLayout(named = "Descripcion: ")
            final String descripcion,

            @ParameterLayout(named = "Ubicacion: ")
            final String ubicacion,

            @ParameterLayout(named = "Apertura: ")
            final Date apertura,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Creador: ")
            final Empleado creador,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Solicitante: ")
            final Empleado solicitante) {

        String motivoCancelacion = null;
        Date cierre = null;
        List<Empleado> ejecutantes = null;
        EstadoAutorizacion estado = EstadoAutorizacion.Abierta;
        return autorizacionrepository.create(titulo, descripcion, ubicacion, motivoCancelacion, apertura, cierre, creador, solicitante, ejecutantes, estado);
    }

    @Programmatic()
    public Date default3Update() {

        Date fecha = new Date();
        return fecha;
    }

    public List<Empleado> choices4Create() {

        return empleadoRepository.listAll();
    }

    public List<Empleado> choices5Create() {

        return empleadoRepository.listAll();
    }

    @javax.inject.Inject
    AutorizacionRepository autorizacionrepository;

    @javax.inject.Inject
    EmpleadoRepository empleadoRepository;
}
