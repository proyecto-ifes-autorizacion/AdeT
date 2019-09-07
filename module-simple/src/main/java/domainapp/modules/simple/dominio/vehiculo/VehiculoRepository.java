package domainapp.modules.simple.dominio.vehiculo;

import java.util.Date;
import java.util.List;

import javax.annotation.PreDestroy;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dominio.EstadoGeneral;
import domainapp.modules.simple.dominio.empresa.Empresa;
import domainapp.modules.simple.dominio.vehiculo.adicional.Modelo;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Vehiculo.class
)
public class VehiculoRepository {

    @Programmatic
    public List<Vehiculo> listAll() {
        return repositoryService.allInstances(Vehiculo.class);
    }

    @Programmatic
    public List<Vehiculo> ListEmpresa(Empresa empresa){

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Vehiculo.class,
                        "findByEmpresa",
                        "empresa", empresa));
    }

    @Programmatic
    public List<Vehiculo> ListEstado(EstadoGeneral estado){

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Vehiculo.class,
                        "findByEstado",
                        "estado", estado));
    }

    @Programmatic
    public Vehiculo findByDominio(final String dominio) {

        return repositoryService.uniqueMatch(
                new QueryDefault<>(
                    Vehiculo.class,
                    "findByDominio",
                    "dominio", dominio));
    }

    @Programmatic
    public List<Vehiculo> findByDominioContains(final String dominio) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Vehiculo.class,
                        "findByDominioContains",
                        "dominio", dominio));
    }

    @Programmatic
    public Vehiculo create(final String dominio, final Modelo modelo, final LocalDate fechaAlta, final Empresa empresa) {

        final Vehiculo vehiculo = new Vehiculo(dominio, modelo, fechaAlta, empresa);
        repositoryService.persist(vehiculo);
        return vehiculo;
    }

    @Programmatic
    public Vehiculo findOrCreate(
            final String dominio,
            final Modelo modelo,
            final LocalDate fechaAlta,
            final Empresa empresa) {

        Vehiculo vehiculo = findByDominio(dominio);
        if (vehiculo == null) {
            vehiculo = create(dominio, modelo, fechaAlta, empresa);
        }
        return vehiculo;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
}
