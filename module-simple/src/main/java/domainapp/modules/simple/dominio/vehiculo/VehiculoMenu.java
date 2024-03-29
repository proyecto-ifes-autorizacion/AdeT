package domainapp.modules.simple.dominio.vehiculo;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.value.Blob;

import domainapp.modules.simple.dominio.empresa.Empresa;
import domainapp.modules.simple.dominio.empresa.EmpresaRepository;
import domainapp.modules.simple.dominio.reportes.EjecutarReportes;
import domainapp.modules.simple.dominio.vehiculo.adicional.Modelo;
import domainapp.modules.simple.dominio.vehiculo.adicional.ModeloRepository;
import net.sf.jasperreports.engine.JRException;

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

    @Action()
    @ActionLayout(named = "Crear")
    @MemberOrder(sequence = "1")
    public Vehiculo create(

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Empresa: ")
            final Empresa empresa,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Modelo: ")
            final Modelo modelo,

            @Parameter(maxLength = 20)
            @ParameterLayout(named = "Dominio: ")
            final String dominio,

            @ParameterLayout(named = "Fecha Alta: ")
            final LocalDate fechaAlta) {

        return vehiculorepository.create(dominio, modelo, fechaAlta, empresa);
    }

    public List<Empresa> choices0Create() {return empresaRepository.Listar();}

    public List<Modelo> choices1Create() {return modeloRepository.ListActivos();}


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listado de Vehiculos")
    @MemberOrder(sequence = "2")
    public List<Vehiculo> listAll() {return vehiculorepository.List();}


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Vehiculo")
    @MemberOrder(sequence = "3")
    public Vehiculo findByDominio(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Vehiculo: ")
            final Vehiculo vehiculo) {

        return vehiculo;
    }

    public List<Vehiculo> choices0FindByDominio() {return vehiculorepository.List();}


    @Action()
    @ActionLayout(named = "Listado Exportado")
    public Blob ExportarListado() throws JRException, IOException {
        EjecutarReportes ejecutarReportes = new EjecutarReportes();
        return ejecutarReportes.ListadoVehiculosPDF(vehiculorepository.List());
    }

    @javax.inject.Inject
    VehiculoRepository vehiculorepository;

    @javax.inject.Inject
    ModeloRepository modeloRepository;

    @javax.inject.Inject
    EmpresaRepository empresaRepository;
}
