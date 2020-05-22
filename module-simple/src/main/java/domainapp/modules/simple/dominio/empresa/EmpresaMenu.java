package domainapp.modules.simple.dominio.empresa;

import java.io.IOException;
import java.util.List;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.value.Blob;

import javax.jdo.annotations.*;

import com.google.common.collect.Lists;
import com.google.inject.internal.cglib.proxy.$MethodProxy;

import domainapp.modules.simple.dominio.reportes.EjecutarReportes;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;

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

    @Action()
    @ActionLayout(named = "Crear Empresa")
    @MemberOrder(sequence = "1")
    public Empresa create(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Nombre Fantasia: ")
            final String nombreFantasia,

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Razon Social: ")
            final String razonSocial,

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Direccion: ")
            final String direccion,

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Telefono: ")
            final String telefono) {

        return empresarepository.create(nombreFantasia, razonSocial, direccion, telefono);
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Empresa")
    @MemberOrder(sequence = "2")
    public Empresa findByNombreFantasia(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Por nombre: ")
            final Empresa empresa) {

        return empresa;
    }

    public List<Empresa> choices0FindByNombreFantasia() {return empresarepository.Listar();}


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listado de Empresas")
    @MemberOrder(sequence = "3")
    public List<Empresa> listAll() {
        List<Empresa> empresas = empresarepository.Listar();
        return empresas;
    }


    @Action()
    @ActionLayout(named = "Listado Exportado")
    public Blob ExportarListado() throws JRException, IOException {
        EjecutarReportes ejecutarReportes = new EjecutarReportes();
        return ejecutarReportes.ListadoEmpresasPDF(empresarepository.Listar());
    }

    @javax.inject.Inject
    EmpresaRepository empresarepository;
}
