package domainapp.modules.simple.dominio.trabajador;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import domainapp.modules.simple.dominio.EstadoGeneral;
import domainapp.modules.simple.dominio.empresa.Empresa;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Trabajador.class
)
public class TrabajadorRepository {

    @Programmatic
    public List<Trabajador> listAll() {

        return repositoryService.allInstances(Trabajador.class);
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
    public java.util.List<Trabajador> findByCuilContains(final String cuil) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Trabajador.class,
                        "findByCuilContains",
                        "cuil", cuil));
    }

    @Programmatic
    public Trabajador create(final String cuil, final String nombre, final String apellido, final Date fechaNacimiento, final Empresa empresa, final EstadoGeneral estadoGeneral) {
        Trabajador trabajador = new Trabajador(cuil, nombre, apellido, fechaNacimiento, empresa, estadoGeneral);
        repositoryService.persist(trabajador);
        return trabajador;
    }

    @Programmatic
    public Trabajador findOrCreate(
            final String cuil,
            final String nombre,
            final String apellido,
            final Date fechaNacimiento,
            final Empresa empresa,
            final EstadoGeneral estadoGeneral)
    {
        Trabajador trabajador = findByCuil(cuil);
        if (trabajador == null) {
            trabajador = create(cuil, nombre, apellido, fechaNacimiento, empresa, estadoGeneral);
        }
        return trabajador;
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(named = "eliminar")
    public void delete(Trabajador trabajador) {
        final String title = titleService.titleOf(trabajador);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.remove(trabajador);
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;


}
