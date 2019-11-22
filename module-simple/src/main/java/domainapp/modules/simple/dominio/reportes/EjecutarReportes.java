package domainapp.modules.simple.dominio.reportes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.isis.applib.services.i18n.TranslatableString;

import domainapp.modules.simple.dominio.autorizacion.Autorizacion;
import domainapp.modules.simple.dominio.empresa.Empresa;
import domainapp.modules.simple.dominio.trabajador.Trabajador;
import domainapp.modules.simple.dominio.vehiculo.Vehiculo;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class EjecutarReportes {

    private File Entrada(String nombre){
        return new File(getClass().getResource(nombre).getPath());
    }

    private String Salida(String nombre){
        return System.getProperty("user.home") + File.separatorChar + "Downloads" + File.separatorChar + nombre;
    }

    public void ListadoVehiculoPDF(List<Vehiculo> vehiculos){
        try {
            File rutaEntrada = Entrada("ListadoVehiculo.jrxml");
            String rutaSalida = Salida("ListadoVehiculos.pdf");

            List<RepoVehiculo> repoVehiculos = new ArrayList<RepoVehiculo>();
            repoVehiculos.add(new RepoVehiculo());

            for (Vehiculo vehiculo : vehiculos) {
                RepoVehiculo repoVehiculo = new RepoVehiculo(vehiculo.RepoDominio(), vehiculo.RepoModelo(), vehiculo.RepoFechaAlta().toString("dd-MM-yyyy"), vehiculo.RepoEmpresa(), vehiculo.RepoEstado());
                repoVehiculos.add(repoVehiculo);
            }
            
            InputStream input = new FileInputStream(rutaEntrada);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoVehiculos);

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("ds", ds);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
            JasperExportManager.exportReportToPdfFile(jasperPrint,rutaSalida);

            JRPdfExporter pdfExporter = new JRPdfExporter();
            pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream();
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfReportStream));
            pdfExporter.exportReport();

            pdfReportStream.close();
        } catch (Exception e) {
            TranslatableString.tr("Error al mostrar el reporte: "+e);
        }

    }

    public void ListadoEmpresaPDF(List<Empresa> empresas){
        try {
            File rutaEntrada = Entrada("ListadoEmpresa.jrxml");
            String rutaSalida = Salida("ListadoEmpresas.pdf");

            List<RepoEmpresa> repoEmpresas = new ArrayList<RepoEmpresa>();
            repoEmpresas.add(new RepoEmpresa());

            for (Empresa empresa : empresas) {
                RepoEmpresa repoEmpresa = new RepoEmpresa(empresa.RepoNombreFantasia(), empresa.RepoRazonSocial(), empresa.RepoDireccion(), empresa.RepoTelefono(), empresa.RepoEstado());
                repoEmpresas.add(repoEmpresa);
            }

            InputStream input = new FileInputStream(rutaEntrada);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoEmpresas);

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("ds", ds);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
            JasperExportManager.exportReportToPdfFile(jasperPrint,rutaSalida);

            JRPdfExporter pdfExporter = new JRPdfExporter();
            pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream();
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfReportStream));
            pdfExporter.exportReport();

            pdfReportStream.close();
        } catch (Exception e) {
            TranslatableString.tr("Error al mostrar el reporte: "+e);
        }

    }

    public void ListadoTrabajadorPDF(List<Trabajador> trabajadores){
        try {
            File rutaEntrada = Entrada("ListadoTrabajador.jrxml");
            String rutaSalida = Salida("ListadoTrabajadores.pdf");

            List<RepoTrabajador> repoTrabajadores = new ArrayList<RepoTrabajador>();
            repoTrabajadores.add(new RepoTrabajador());

            for (Trabajador trabajador : trabajadores) {
                RepoTrabajador repoTrabajador = new RepoTrabajador(trabajador.RepoCuil(), trabajador.RepoNombre(), trabajador.RepoApellido(), trabajador.RepoFechaNacimiento(), trabajador.RepoEmpresa(), trabajador.RepoEstado());
                repoTrabajadores.add(repoTrabajador);
            }

            InputStream input = new FileInputStream(rutaEntrada);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoTrabajadores);

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("ds", ds);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
            JasperExportManager.exportReportToPdfFile(jasperPrint,rutaSalida);

            JRPdfExporter pdfExporter = new JRPdfExporter();
            pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream();
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfReportStream));
            pdfExporter.exportReport();

            pdfReportStream.close();
        } catch (Exception e) {
            TranslatableString.tr("Error al mostrar el reporte: "+e);
        }

    }

    public void ListadoAutorizacionPDF(List<Autorizacion> autorizaciones){
        try {
            File rutaEntrada = Entrada("ListadoAutorizacion.jrxml");
            String rutaSalida = Salida("ListadoAutorizaciones.pdf");

            List<RepoAutorizacion> repoAutorizaciones = new ArrayList<RepoAutorizacion>();
            repoAutorizaciones.add(new RepoAutorizacion());

            for (Autorizacion autorizacion : autorizaciones) {
                RepoAutorizacion repoAutorizacion = new RepoAutorizacion(autorizacion.RepoIdAdeT(), autorizacion.RepoTitulo(), autorizacion.RepoUbicacion(), autorizacion.RepoEstado(), autorizacion.RepoApertura(), autorizacion.RepoCierre(), autorizacion.RepoTiempo());
                repoAutorizaciones.add(repoAutorizacion);
            }

            InputStream input = new FileInputStream(rutaEntrada);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoAutorizaciones);

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("ds", ds);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
            JasperExportManager.exportReportToPdfFile(jasperPrint,rutaSalida);

            JRPdfExporter pdfExporter = new JRPdfExporter();
            pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream();
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfReportStream));
            pdfExporter.exportReport();

            pdfReportStream.close();
        } catch (Exception e) {
            TranslatableString.tr("Error al mostrar el reporte: "+e);
        }

    }

}
