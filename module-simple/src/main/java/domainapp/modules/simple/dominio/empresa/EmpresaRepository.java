package domainapp.modules.simple.dominio.empresa;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Empresa.class
)
public class EmpresaRepository {

    @Programmatic
    public List<Empresa> listAll() {

        return repositoryService.allInstances(Empresa.class);
    }

    @Programmatic
    public Empresa findByNombreFantasia(final String nombreFantasia) {

        return repositoryService.uniqueMatch(
                new QueryDefault<>(
                        Empresa.class,
                        "findByNombreFantasia",
                        "nombreFantasia", nombreFantasia));
    }

    @Programmatic
    public java.util.List<Empresa> findByNombreFantasiaContains(final String nombreFantasia) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Empresa.class,
                        "findByNombreFantasiaContains",
                        "nombreFantasia", nombreFantasia));
    }

    @Programmatic
    public Empresa create(final String nombreFantasia, final String razonSocial, final String direccion, final String telefono) {
        Empresa empresa = new Empresa(nombreFantasia,razonSocial,direccion,telefono);
        repositoryService.persist(empresa);
        return empresa;
    }

    @Programmatic
    public Empresa findOrCreate(
            final String nombreFantasia,
            final String razonSocial,
            final String direccion,
            final String telefono
    ) {
        Empresa empresa = findByNombreFantasia(nombreFantasia);
        if (empresa == null) {
            empresa = create(nombreFantasia, razonSocial,direccion,telefono);
        }
        return empresa;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
}
