package domainapp.modules.simple.dominio.trabajador;

import java.util.List;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dominio.EstadoGeneral;
import domainapp.modules.simple.dominio.empresa.Empresa;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Trabajador.class
)
public class TrabajadorRepository {

    @Programmatic
    public List<Trabajador> Listar() {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Trabajador.class,
                        "find"));
    }

    @Programmatic
    public List<Trabajador> Listar(Empresa empresa){

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Trabajador.class,
                        "findByEmpresa",
                        "empresa", empresa));
    }
    
    @Programmatic
    public List<Trabajador> Listar(Empresa empresa, EstadoGeneral estado){

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Trabajador.class,
                        "findByEmpresaAndEstado",
                        "empresa", empresa,
                        "estado", estado));
    }

    @Programmatic
    public List<Trabajador> Listar(EstadoGeneral estado){

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Trabajador.class,
                        "findByEstado",
                        "estado", estado));
    }

    @Programmatic
    public Trabajador findByCuil(final String cuil) {

        return repositoryService.uniqueMatch(
                new QueryDefault<>(
                        Trabajador.class,
                        "findByCuil",
                        "cuil", cuil));
    }

    @Programmatic
    public List<Trabajador> findByCuilContains(final String cuil) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Trabajador.class,
                        "findByCuilContains",
                        "cuil", cuil));
    }

    @Programmatic
    public Trabajador create(final String cuil, final String nombre, final String apellido, final LocalDate fechaNacimiento, final Empresa empresa) {

        final Trabajador trabajador = new Trabajador(cuil, nombre, apellido, fechaNacimiento, empresa);
        repositoryService.persist(trabajador);
        return trabajador;
    }

    @Programmatic
    public Trabajador findOrCreate(final String cuil, final String nombre, final String apellido, final LocalDate fechaNacimiento, final Empresa empresa) {
        Trabajador trabajador = findByCuil(cuil);
        if (trabajador == null) {
            trabajador = create(cuil, nombre, apellido, fechaNacimiento, empresa);
        }
        return trabajador;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
}
