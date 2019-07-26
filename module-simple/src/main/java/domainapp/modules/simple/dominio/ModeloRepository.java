package domainapp.modules.simple.dominio;

import java.util.List;

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
        repositoryFor = Modelo.class
)
public class ModeloRepository {

    @Programmatic
    public List<Modelo> listAll() {
        return repositoryService.allInstances(Modelo.class);
    }

    @Programmatic
    public Modelo findByNombre(
            final String nombre
    ) {
        return container.uniqueMatch(
                new org.apache.isis.applib.query.QueryDefault<>(
                        Modelo.class,
                        "findByNombre",
                        "nombre", nombre));
    }

    @Programmatic
    public java.util.List<Modelo> findByNombreContains(
            final String nombre
    ) {
        return container.allMatches(
                new org.apache.isis.applib.query.QueryDefault<>(
                        Modelo.class,
                        "findByNombreContains",
                        "nombre", nombre));
    }

    @Programmatic
    public Modelo create(final String nombre) {
        Modelo modelo = new Modelo(nombre);
        repositoryService.persist(modelo);
        return modelo;
    }

    @Programmatic
    public Modelo findOrCreate(final String nombre)
    {
        Modelo modelo = findByNombre(nombre);
        if (modelo == null) {
            modelo = create(nombre);
        }
        return modelo;
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(named = "eliminar")
    public void delete(Modelo modelo) {
        final String title = titleService.titleOf(modelo);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.remove(modelo);
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
