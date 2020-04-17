package domainapp.modules.simple.dominio.empresa;

import java.util.List;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

import javax.jdo.annotations.*;

import lombok.Getter;
import lombok.Setter;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Empresa.class
)
public class EmpresaRepository {

    @Programmatic
    public List<Empresa> Listar() {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Empresa.class,
                        "find"));
    }

    @Programmatic
    public List<Empresa> Listar(final EstadoEmpresa estado){

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Empresa.class,
                        "findByEstado",
                        "estado", estado));
    }

    @Programmatic
    public Empresa findByNombreFantacia(final String nombreFantasia) {

        return repositoryService.uniqueMatch(
                new QueryDefault<>(
                        Empresa.class,
                        "findByNombreFantasia",
                        "nombreFantasia", nombreFantasia));
    }

    @Programmatic
    public List<Empresa> findByNombreFantaciaContains(final String nombreFantasia) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Empresa.class,
                        "findByNombreFantasiaContains",
                        "nombreFantasia", nombreFantasia));
    }

    @Programmatic
    public Empresa create(
            final String nombreFantasia,
            final String razonSocial,
            final String direccion,
            final String telefono) {

        final Empresa empresa = new Empresa(nombreFantasia, razonSocial, direccion, telefono);
        repositoryService.persist(empresa);
        return empresa;
    }

    @Programmatic
    public Empresa findOrCreate(
            final String nombreFantasia,
            final String razonSocial,
            final String direccion,
            final String telefono) {

        Empresa empresa = findByNombreFantacia(nombreFantasia);
        if (empresa == null) {
            empresa = create(nombreFantasia, razonSocial, direccion, telefono);
        }
        return empresa;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
}
