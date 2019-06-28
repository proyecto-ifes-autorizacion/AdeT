package domainapp.modules.simple.dominio;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

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

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(named = "eliminar")
    public void delete(Empleado empleado) {
        final String title = titleService.titleOf(empleado);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.remove(empleado);
    }

    @javax.inject.Inject
    org.apache.isis.applib.DomainObjectContainer container;

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;


}
