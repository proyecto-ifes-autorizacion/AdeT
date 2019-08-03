package domainapp.modules.simple.dominio;

import org.apache.isis.applib.annotation.*;

import javax.jdo.annotations.*;


import lombok.Getter;
import lombok.Setter;
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
        repositoryFor = Empresa.class
)
public class EmpresaRepository {

    @Programmatic
    public java.util.List<Empresa> listAll() {
        return container.allInstances(Empresa.class);
    }

    @Programmatic
    public Empresa findByNombreFantasia(
            final String nombreFantasia
    ) {
        return container.uniqueMatch(
                new org.apache.isis.applib.query.QueryDefault<>(
                        Empresa.class,
                        "findByNombreFantasia",
                        "nombreFantasia", nombreFantasia));
    }

    @Programmatic
    public java.util.List<Empresa> findByNombreFantasiaContains(
            final String nombreFantasia
    ) {
        return container.allMatches(
                new org.apache.isis.applib.query.QueryDefault<>(
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
    org.apache.isis.applib.DomainObjectContainer container;

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;
}
