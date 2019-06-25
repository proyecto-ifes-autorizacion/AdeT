package domainapp.modules.simple.dominio;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
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
    public Empleado findByDni(
            final int dni
    ) {
        return container.uniqueMatch(
                new org.apache.isis.applib.query.QueryDefault<>(
                        Empleado.class,
                        "findByDni",
                        "dni", dni));
    }

    @Programmatic
    public java.util.List<Empleado> findByDniContains(
            final int dni
    ) {
        return container.allMatches(
                new org.apache.isis.applib.query.QueryDefault<>(
                        Empleado.class,
                        "findByDniContains",
                        "dni", dni));
    }

    @Programmatic
    public Empleado create(final int dni, final String nombre, final String apellido) {
//        final Empleado empleado = container.newTransientInstance(Empleado.class);
//        empleado.setDni(dni);
//        empleado.setNombre(nombre);
//        empleado.setApellido(apellido);
//        container.persistIfNotAlready(empleado);
          final Empleado empleado = new Empleado(dni, nombre, apellido);
          repositoryService.persist(empleado);
        return empleado;
    }

    @Programmatic
    public Empleado findOrCreate(
            final int dni, final String nombre, final String apellido
    ) {
        Empleado empleado = findByDni(dni);
        if (empleado == null) {
            empleado = create(dni, nombre, apellido);
        }
        return empleado;
    }

    @javax.inject.Inject
    org.apache.isis.applib.DomainObjectContainer container;

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;
}
