package domainapp.modules.simple.dominio.autorizacion;

import java.util.List;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dominio.empresa.Empresa;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Ejecutante.class
)
public class EjecutanteRepository {

    @Programmatic
    public List<Ejecutante> listAll() {
        return repositoryService.allInstances(Ejecutante.class);
    }

    @Programmatic
    public Ejecutante findByAutorizacion(final Autorizacion autorizacion) {

        return repositoryService.uniqueMatch(
                new QueryDefault<>(
                        Ejecutante.class,
                        "findByAutorizacion",
                        "autorizacion", autorizacion));
    }

    @Programmatic
    public java.util.List<Ejecutante> findByAutorizacionContains(final Autorizacion autorizacion) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Ejecutante.class,
                        "findByAutorizacionContains",
                        "autorizacion", autorizacion));
    }

    @Programmatic
    public Ejecutante create(final Autorizacion autorizacion, final Empresa empresa) {

        Ejecutante ejecutante = new Ejecutante(autorizacion, empresa);
        repositoryService.persist(ejecutante);
        return ejecutante;
    }

    @Programmatic
    public Ejecutante findOrCreate(final Autorizacion autorizacion, final Empresa empresa) {

        Ejecutante ejecutante = findByAutorizacion(autorizacion);
        if (ejecutante == null) {
            ejecutante = create(autorizacion, empresa);
        }
        return ejecutante;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
}
