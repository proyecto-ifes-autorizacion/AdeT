package domainapp.modules.simple.dom.impl;

import org.apache.isis.applib.annotation.*;

import javax.jdo.annotations.*;

import lombok.Getter;
import lombok.Setter;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = vehiculo.class
)
public class vehiculoRepository {

    @Programmatic
    public java.util.List<vehiculo> listAll() {
        return container.allInstances(vehiculo.class);
    }

    @Programmatic
    public vehiculo findByMarca(
            final String marca
    ) {
        return container.uniqueMatch(
                new org.apache.isis.applib.query.QueryDefault<>(
                        vehiculo.class,
                        "findByMarca",
                        "marca", marca));
    }

    @Programmatic
    public java.util.List<vehiculo> findByMarcaContains(
            final String marca
    ) {
        return container.allMatches(
                new org.apache.isis.applib.query.QueryDefault<>(
                        vehiculo.class,
                        "findByMarcaContains",
                        "marca", marca));
    }

    @Programmatic
    public vehiculo create(final String marca) {
        final vehiculo vehiculo = container.newTransientInstance(vehiculo.class);
        vehiculo.setMarca(marca);
        container.persistIfNotAlready(vehiculo);
        return vehiculo;
    }

    @Programmatic
    public vehiculo findOrCreate(
            final String marca
    ) {
        vehiculo vehiculo = findByMarca(marca);
        if (vehiculo == null) {
            vehiculo = create(marca);
        }
        return vehiculo;
    }

    @javax.inject.Inject
    org.apache.isis.applib.DomainObjectContainer container;
}
