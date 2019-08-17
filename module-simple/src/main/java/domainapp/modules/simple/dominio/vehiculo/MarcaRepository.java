package domainapp.modules.simple.dominio.vehiculo;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Marca.class
)
public class MarcaRepository {

    @Programmatic
    public List<Marca> listAll() {
        return repositoryService.allInstances(Marca.class);
    }

    @Programmatic
    public List<Marca> ListByBaja(final boolean baja){

        return repositoryService.allMatches(new QueryDefault<>(
                Marca.class,
                "ListByBaja",
                "baja", baja));
    }

    @Programmatic
    public Marca findByNombre(final String nombre) {

        return repositoryService.uniqueMatch(
                new QueryDefault<>(
                        Marca.class,
                        "findByNombre",
                        "nombre", nombre));
    }

    @Programmatic
    public java.util.List<Marca> findByNombreContains(
            final String nombre
    ) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Marca.class,
                        "findByNombreContains",
                        "nombre", nombre));
    }

    @Programmatic
    public Marca create(final String nombre, final boolean baja, final List<Modelo> modelos) {
        final Marca marca = new Marca(nombre, baja, modelos);
        repositoryService.persist(marca);
        return marca;
    }

    @Programmatic
    public void delete(Marca marca){

        repositoryService.remove(marca);
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

}
