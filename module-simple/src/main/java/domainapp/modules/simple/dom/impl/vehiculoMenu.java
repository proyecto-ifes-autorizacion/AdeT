package domainapp.modules.simple.dom.impl;

import org.apache.isis.applib.annotation.*;

import javax.jdo.annotations.*;

import lombok.Getter;
import lombok.Setter;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "vehiculo"
)
@DomainServiceLayout(
        named = "",
        menuOrder = "2"
)
public class vehiculoMenu {
    @Action(
    )
    @ActionLayout(
            named = "Agregar Vehiculo"
    )
    @MemberOrder(sequence = "1")
    public vehiculo create(
            @ParameterLayout(named = "Marca:")
            final String marca) {
        return vehiculorepository.create(marca);
    }



    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Buscar Vehiculo"
    )
    @MemberOrder(sequence = "2")
    public java.util.List<vehiculo> findByMarca(
            @ParameterLayout(named = "Marca:")
            final String marca
    ) {
        return vehiculorepository.findByMarcaContains(marca);
    }




    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Listar Vehiculos"
    )
    @MemberOrder(sequence = "3")
    public java.util.List<vehiculo> listAll() {

        return vehiculorepository.listAll();
    }


    @javax.inject.Inject
    vehiculoRepository vehiculorepository;
}
