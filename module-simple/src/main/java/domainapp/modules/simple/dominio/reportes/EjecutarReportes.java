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
        String ruta = System.getProperty("user.home") + File.separatorChar + "Downloads" + File.separatorChar + nombre;
        String adicion = "";
        int x = 0;
        while (ExisteArchivo(ruta, adicion)) {
            x++;
            adicion = "-" + x;
        }
        return ruta + adicion + ".pdf";
    }

    private boolean ExisteArchivo(String ruta, String adicion){
        File archivo = new File(ruta + adicion + ".pdf");
        return archivo.exists();
    }

    private void ExportarReporteTipoLista(String entrada, String salida, JRBeanCollectionDataSource ds){
        try {
            File rutaEntrada = Entrada(entrada);
            String rutaSalida = Salida(salida);

            InputStream input = new FileInputStream(rutaEntrada);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

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

    public void ListadoVehiculoPDF(List<Vehiculo> vehiculos){

        List<RepoVehiculo> repoVehiculos = new ArrayList<RepoVehiculo>();
        repoVehiculos.add(new RepoVehiculo());

        for (Vehiculo vehiculo : vehiculos) {
            RepoVehiculo repoVehiculo = new RepoVehiculo(vehiculo.RepoDominio(), vehiculo.RepoModelo(), vehiculo.RepoFechaAlta().toString("dd-MM-yyyy"), vehiculo.RepoEmpresa(), vehiculo.RepoEstado());
            repoVehiculos.add(repoVehiculo);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoVehiculos);
        ExportarReporteTipoLista("ListadoVehiculo.jrxml","ListadoVehiculos", ds);
    }

    public void ListadoEmpresaPDF(List<Empresa> empresas){

        List<RepoEmpresa> repoEmpresas = new ArrayList<RepoEmpresa>();
        repoEmpresas.add(new RepoEmpresa());

        for (Empresa empresa : empresas) {
            RepoEmpresa repoEmpresa = new RepoEmpresa(empresa.RepoNombreFantasia(), empresa.RepoRazonSocial(), empresa.RepoDireccion(), empresa.RepoTelefono(), empresa.RepoEstado());
            repoEmpresas.add(repoEmpresa);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoEmpresas);
        ExportarReporteTipoLista("ListadoEmpresa.jrxml", "ListadoEmpresas", ds);
    }

    public void ListadoTrabajadorPDF(List<Trabajador> trabajadores){

        List<RepoTrabajador> repoTrabajadores = new ArrayList<RepoTrabajador>();
        repoTrabajadores.add(new RepoTrabajador());

        for (Trabajador trabajador : trabajadores) {
            RepoTrabajador repoTrabajador = new RepoTrabajador(trabajador.RepoCuil(), trabajador.RepoNombre(), trabajador.RepoApellido(), trabajador.RepoFechaNacimiento(), trabajador.RepoEmpresa(), trabajador.RepoEstado());
            repoTrabajadores.add(repoTrabajador);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoTrabajadores);
        ExportarReporteTipoLista("ListadoTrabajador.jrxml", "ListadoTrabajadores", ds);
    }

    public void ListadoAutorizacionPDF(List<Autorizacion> autorizaciones){

        List<RepoAutorizacion> repoAutorizaciones = new ArrayList<RepoAutorizacion>();
        repoAutorizaciones.add(new RepoAutorizacion());

        for (Autorizacion autorizacion : autorizaciones) {
            RepoAutorizacion repoAutorizacion = new RepoAutorizacion(autorizacion.RepoIdAdeT(), autorizacion.RepoTitulo(), autorizacion.RepoUbicacion(), autorizacion.RepoEstado(), autorizacion.RepoApertura(), autorizacion.RepoCierre(), autorizacion.RepoTiempo());
            repoAutorizaciones.add(repoAutorizacion);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoAutorizaciones);
        ExportarReporteTipoLista("ListadoAutorizacion.jrxml", "ListadoAutorizaciones", ds);
    }

}
