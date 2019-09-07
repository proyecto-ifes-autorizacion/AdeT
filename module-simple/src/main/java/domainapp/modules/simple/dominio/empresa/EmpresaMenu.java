package domainapp.modules.simple.dominio.empresa;

import java.util.List;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.i18n.TranslatableString;

import javax.jdo.annotations.*;

import com.google.inject.internal.cglib.proxy.$MethodProxy;

import lombok.Getter;
import lombok.Setter;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "Empresa",
        repositoryFor = Empresa.class
)
@DomainServiceLayout(
        named = "",
        menuOrder = ""
)
public class EmpresaMenu {

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<Empresa> listAll() {
        List<Empresa> empresas = empresarepository.Listar();
        for (Empresa empresa : empresas){
            empresa.ObtenerTrabadoresYVehiculos();
        }
        return empresas;
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public java.util.List<Empresa> findByNombreFantasia(
            final String nombreFantasia
    ) {
        return empresarepository.findByNombreFantaciaContains(nombreFantasia);
    }

    @Action(
    )
    @MemberOrder(sequence = "3")
    public Empresa create(
            @ParameterLayout(named = "Nombre Fantasia: ")
            final String nombreFantasia,

            @ParameterLayout(named = "Razon Social: ")
            final String razonSocial,

            @ParameterLayout(named = "Direccion: ")
            final String direccion,

            @ParameterLayout(named = "Telefono: ")
            final String telefono) {

        return empresarepository.create(nombreFantasia, razonSocial, direccion, telefono);
    }

    @javax.inject.Inject
    EmpresaRepository empresarepository;
}
