package domainapp.modules.simple.dominio.reportes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.isis.applib.value.Blob;

import domainapp.modules.simple.dominio.autorizacion.Autorizacion;
import domainapp.modules.simple.dominio.empresa.Empresa;
import domainapp.modules.simple.dominio.trabajador.Trabajador;
import domainapp.modules.simple.dominio.vehiculo.Vehiculo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class EjecutarReportes {

    public Blob ListadoAutorizacionesPDF(List<Autorizacion> autorizaciones) throws JRException, IOException{

        List<RepoAutorizacion> repoAutorizaciones = new ArrayList<RepoAutorizacion>();
        repoAutorizaciones.add(new RepoAutorizacion());

        for (Autorizacion autorizacion : autorizaciones) {
            RepoAutorizacion repoAutorizacion = new RepoAutorizacion(autorizacion.RepoIdAdeT(), autorizacion.RepoTitulo(), autorizacion.RepoUbicacion(), autorizacion.RepoEstado(), autorizacion.RepoApertura(), autorizacion.RepoCierre(), autorizacion.RepoTiempo());
            repoAutorizaciones.add(repoAutorizacion);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoAutorizaciones);
        return GenerarArchivoPDF("ListadoAutorizacionesDesing.jrxml", "Listado de Autorizaciones.pdf", ds);
    }

    public Blob ListadoTrabajadoresPDF(List<Trabajador> trabajadores) throws JRException, IOException{

        List<RepoTrabajador> repoTrabajadores = new ArrayList<RepoTrabajador>();
        repoTrabajadores.add(new RepoTrabajador());

        for (Trabajador trabajador : trabajadores) {
            RepoTrabajador repoTrabajador = new RepoTrabajador(trabajador.RepoCuil(), trabajador.RepoNombre(), trabajador.RepoApellido(), trabajador.RepoFechaNacimiento(), trabajador.RepoEmpresa(), trabajador.RepoEstado());
            repoTrabajadores.add(repoTrabajador);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoTrabajadores);
        return GenerarArchivoPDF("ListadoTrabajadoresDesing.jrxml", "Listado de Trabajadores.pdf", ds);
    }

    public Blob ListadoVehiculosPDF(List<Vehiculo> vehiculos) throws JRException, IOException{

        List<RepoVehiculo> repoVehiculos = new ArrayList<RepoVehiculo>();
        repoVehiculos.add(new RepoVehiculo());

        for (Vehiculo vehiculo : vehiculos) {
            RepoVehiculo repoVehiculo = new RepoVehiculo(vehiculo.RepoDominio(), vehiculo.RepoModelo(), vehiculo.RepoFechaAlta().toString("dd-MM-yyyy"), vehiculo.RepoEmpresa(), vehiculo.RepoEstado());
            repoVehiculos.add(repoVehiculo);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoVehiculos);
        return GenerarArchivoPDF("ListadoVehiculosDesing.jrxml","Listado de Vehiculos.pdf", ds);
    }

    public Blob ListadoEmpresasPDF(List<Empresa> empresas) throws JRException, IOException{

        List<RepoEmpresa> repoEmpresas = new ArrayList<RepoEmpresa>();
        repoEmpresas.add(new RepoEmpresa());

        for (Empresa empresa : empresas) {
            RepoEmpresa repoEmpresa = new RepoEmpresa(empresa.RepoNombreFantasia(), empresa.RepoRazonSocial(), empresa.RepoDireccion(), empresa.RepoTelefono(), empresa.RepoEstado());
            repoEmpresas.add(repoEmpresa);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoEmpresas);
        return GenerarArchivoPDF("ListadoEmpresasDesing.jrxml", "Listado de Empresas.pdf", ds);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Blob GenerarArchivoPDF(String archivoDesing, String nombreSalida, JRBeanCollectionDataSource ds) throws JRException, IOException{

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(archivoDesing);
        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ds", ds);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
        byte[] contentBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        return new Blob(nombreSalida, "application/pdf", contentBytes);
    }

}
