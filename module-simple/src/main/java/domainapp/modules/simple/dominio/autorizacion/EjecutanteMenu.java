package domainapp.modules.simple.dominio.autorizacion;

import org.apache.isis.applib.annotation.*;

import domainapp.modules.simple.dominio.empresa.Empresa;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "Ejecutante",
        repositoryFor = Ejecutante.class
)
@DomainServiceLayout(
        named = "",
        menuOrder = ""
)
public class EjecutanteMenu {

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public java.util.List<Ejecutante> listAll() {
        return ejecutanterepository.listAll();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public java.util.List<Ejecutante> findByAutorizacion(
            final Autorizacion autorizacion
    ) {
        return ejecutanterepository.findByAutorizacionContains(autorizacion);
    }

    @Action(
    )
    @MemberOrder(sequence = "3")
    public Ejecutante create(
            final Autorizacion autorizacion,
            final Empresa empresa) {
        return ejecutanterepository.create(autorizacion, empresa);
    }

    @javax.inject.Inject
    EjecutanteRepository ejecutanterepository;
}
