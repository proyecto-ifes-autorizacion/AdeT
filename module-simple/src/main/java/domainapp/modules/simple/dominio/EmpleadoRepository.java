package domainapp.modules.simple.dominio;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Empleado.class
)
public class EmpleadoRepository {

    @Programmatic
    public java.util.List<Empleado> listAll() {
        return container.allInstances(Empleado.class);
    }

    @Programmatic
    public Empleado findByCuil(
            final String cuil
    ) {
        return container.uniqueMatch(
                new org.apache.isis.applib.query.QueryDefault<>(
                        Empleado.class,
                        "findByCuil",
                        "cuil", cuil));
    }

    @Programmatic
    public java.util.List<Empleado> findByCuilContains(
            final String cuil
    ) {
        return container.allMatches(
                new org.apache.isis.applib.query.QueryDefault<>(
                        Empleado.class,
                        "findByCuilContains",
                        "cuil", cuil));
    }

    @Programmatic
    public Empleado create(final String cuil, final String nombre, final String apellido) {
        Empleado empleado = new Empleado(cuil, nombre, apellido);
        repositoryService.persist(empleado);
        return empleado;
    }

    @Programmatic
    public Empleado findOrCreate(
            final String cuil,
            final String nombre,
            final String apellido
    ) {
        Empleado empleado = findByCuil(cuil);
        if (empleado == null) {
            empleado = create(cuil,nombre,apellido);
        }
        return empleado;
    }

    @javax.inject.Inject
    org.apache.isis.applib.DomainObjectContainer container;

    @javax.inject.Inject
    RepositoryService repositoryService;

}
