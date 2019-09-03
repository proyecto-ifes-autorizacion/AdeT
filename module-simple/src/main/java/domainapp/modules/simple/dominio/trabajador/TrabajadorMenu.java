package domainapp.modules.simple.dominio.trabajador;

import java.util.List;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.*;

import domainapp.modules.simple.dominio.empresa.Empresa;
import domainapp.modules.simple.dominio.empresa.EmpresaRepository;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "Trabajador",
        repositoryFor = Trabajador.class
)
@DomainServiceLayout(
        named = "Trabajador",
        menuOrder = ""
)
public class TrabajadorMenu {

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public java.util.List<Trabajador> listAll() {
        return trabajadorrepository.Listar();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public java.util.List<Trabajador> findByCuil(
            final String cuil
    ) {
        return trabajadorrepository.findByCuilContains(cuil);
    }

    @Action(
    )
    @MemberOrder(sequence = "3")
    public Trabajador create(

            @ParameterLayout(named = "Cuil: ")
            final String cuil,

            @ParameterLayout(named = "Nombre: ")
            final String nombre,

            @ParameterLayout(named = "Apellido: ")
            final String apellido,

            @ParameterLayout(named = "Fecha Nacimiento: ")
            final LocalDate fechaNacimiento,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Empresa: ")
            final Empresa empresa) {

        return trabajadorrepository.create(cuil, nombre, apellido, fechaNacimiento, empresa);
    }

    public List<Empresa> choices4Create() {return empresaRepository.Listar();}

    @javax.inject.Inject
    TrabajadorRepository trabajadorrepository;

    @javax.inject.Inject
    EmpresaRepository empresaRepository;
}
