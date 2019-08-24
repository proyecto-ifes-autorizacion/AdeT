package domainapp.modules.simple.dominio.autorizacion;

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
    public Autorizacion findByIdAdeT(
            final int idAdeT
    ) {
        return repositoryService.uniqueMatch(
                new QueryDefault<>(
                        Autorizacion.class,
                        "findByIdAdeT",
                        "idAdeT", idAdeT));
    }

    @Programmatic
    public List<Autorizacion> findByIdAdeTContains(
            final int idAdeT
    ) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Autorizacion.class,
                        "findByIdAdeTContains",
                        "idAdeT", idAdeT));
    }

    @Programmatic
    public Autorizacion create(final int idAdeT, final EstadoAutorizacion estadoAutorizacion, final String titulo, final String descripcion, final String ubicacion) {
        Autorizacion autorizacion = new Autorizacion(idAdeT, estadoAutorizacion, titulo, descripcion, ubicacion);
        repositoryService.persist(autorizacion);
        return autorizacion;
    }

    @Programmatic
    public Autorizacion findOrCreate(final int idAdeT, final EstadoAutorizacion estadoAutorizacion, final String titulo, final String descripcion, final String ubicacion) {
        Autorizacion autorizacion = findByIdAdeT(idAdeT);
        if (autorizacion == null) {
            autorizacion = create(idAdeT, estadoAutorizacion, titulo, descripcion, ubicacion);
        }
        return autorizacion;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
}
