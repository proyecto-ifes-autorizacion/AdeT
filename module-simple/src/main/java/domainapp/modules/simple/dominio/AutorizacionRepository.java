package domainapp.modules.simple.dominio;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Autorizacion.class
)
public class AutorizacionRepository {

    @Programmatic
    public List<Autorizacion> listAll() {

        return repositoryService.allInstances(Autorizacion.class);
    }

    @Programmatic
    public Autorizacion findByTitulo(
            final String titulo
    ) {
        return repositoryService.uniqueMatch(
                new QueryDefault<>(
                        Autorizacion.class,
                        "findByTitulo",
                        "titulo", titulo));
    }

    @Programmatic
    public List<Autorizacion> findByTituloContains(
            final String titulo
    ) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Autorizacion.class,
                        "findByTituloContains",
                        "titulo", titulo));
    }

    @Programmatic
    public Autorizacion create(
            final String titulo,
            final String descripcion,
            final String ubicacion,
            final String motivoCancelacion,
            final Date apertura,
            final Date cierre,
            final Empleado creador,
            final Empleado solicitante,
            final List<Empleado> ejecutantes,
            final EstadoAutorizacion estado) {

        Autorizacion autorizacion = new Autorizacion(
                titulo,
                descripcion,
                ubicacion,
                motivoCancelacion,
                apertura,
                cierre,
                creador,
                solicitante,
                ejecutantes,
                estado);
        repositoryService.persist(autorizacion);
        return autorizacion;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
}
