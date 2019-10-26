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
    public List<Autorizacion> Listar() {
        return repositoryService.allInstances(Autorizacion.class);
    }

    @Programmatic
    public List<Autorizacion> Listar(EstadoAutorizacion estado){

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Autorizacion.class,
                        "findByEstado",
                        "estado", estado));
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
    public Autorizacion create() {
        final EstadoAutorizacion estado = EstadoAutorizacion.Abierta;
        Autorizacion autorizacion = new Autorizacion(estado);
        repositoryService.persist(autorizacion);
        return autorizacion;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
}
