package domainapp.modules.simple.dominio.vehiculo;

import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.*;

import domainapp.modules.simple.dominio.empresa.Empresa;
import domainapp.modules.simple.dominio.empresa.EmpresaRepository;
import domainapp.modules.simple.dominio.vehiculo.adicional.Modelo;
import domainapp.modules.simple.dominio.vehiculo.adicional.ModeloRepository;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "Vehiculo",
        repositoryFor = Vehiculo.class
)
@DomainServiceLayout(
        named = "",
        menuOrder = ""
)
public class VehiculoMenu {

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<Vehiculo> listAll() {
        return vehiculorepository.listAll();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public List<Vehiculo> findByDominio(final String dominio) {

        return vehiculorepository.findByDominioContains(dominio);
    }

    @Action(
    )
    @MemberOrder(sequence = "3")
    public Vehiculo create(
            @ParameterLayout(named = "Dominio: ")
            final String dominio,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Modelo: ")
            final Modelo modelo,

            @ParameterLayout(named = "Fecha Alta: ")
            final LocalDate fechaAlta,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Empresa: ")
            final Empresa empresa) {

        return vehiculorepository.create(dominio, modelo, fechaAlta, empresa);
    }

    public List<Modelo> choices1Create() {return modeloRepository.ListActivos();}

    public List<Empresa> choices3Create() {return empresaRepository.Listar();}

    @javax.inject.Inject
    VehiculoRepository vehiculorepository;

    @javax.inject.Inject
    ModeloRepository modeloRepository;

    @javax.inject.Inject
    EmpresaRepository empresaRepository;
}
