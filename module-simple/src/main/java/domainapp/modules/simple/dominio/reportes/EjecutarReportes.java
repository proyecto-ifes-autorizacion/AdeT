package domainapp.modules.simple.dominio.reportes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class EjecutarReportes {

    public Blob ListadoVehiculoPDF(List<Vehiculo> vehiculos) throws JRException, IOException{

        List<RepoVehiculo> repoVehiculos = new ArrayList<RepoVehiculo>();
        repoVehiculos.add(new RepoVehiculo());

        for (Vehiculo vehiculo : vehiculos) {
            RepoVehiculo repoVehiculo = new RepoVehiculo(vehiculo.RepoDominio(), vehiculo.RepoModelo(), vehiculo.RepoFechaAlta().toString("dd-MM-yyyy"), vehiculo.RepoEmpresa(), vehiculo.RepoEstado());
            repoVehiculos.add(repoVehiculo);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoVehiculos);
        return GenerarReporteTipoLista("ListadoVehiculo.jrxml","ListadoVehiculos", ds);
    }

    public Blob ListadoEmpresaPDF(List<Empresa> empresas) throws JRException, IOException{

        List<RepoEmpresa> repoEmpresas = new ArrayList<RepoEmpresa>();
        repoEmpresas.add(new RepoEmpresa());

        for (Empresa empresa : empresas) {
            RepoEmpresa repoEmpresa = new RepoEmpresa(empresa.RepoNombreFantasia(), empresa.RepoRazonSocial(), empresa.RepoDireccion(), empresa.RepoTelefono(), empresa.RepoEstado());
            repoEmpresas.add(repoEmpresa);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoEmpresas);
        return GenerarReporteTipoLista("ListadoEmpresa.jrxml", "ListadoEmpresas", ds);
    }

    public Blob ListadoTrabajadorPDF(List<Trabajador> trabajadores) throws JRException, IOException{

        List<RepoTrabajador> repoTrabajadores = new ArrayList<RepoTrabajador>();
        repoTrabajadores.add(new RepoTrabajador());

        for (Trabajador trabajador : trabajadores) {
            RepoTrabajador repoTrabajador = new RepoTrabajador(trabajador.RepoCuil(), trabajador.RepoNombre(), trabajador.RepoApellido(), trabajador.RepoFechaNacimiento(), trabajador.RepoEmpresa(), trabajador.RepoEstado());
            repoTrabajadores.add(repoTrabajador);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoTrabajadores);
        return GenerarReporteTipoLista("ListadoTrabajador.jrxml", "ListadoTrabajadores", ds);
    }

    public Blob ListadoAutorizacionPDF(List<Autorizacion> autorizaciones) throws JRException, IOException{

        List<RepoAutorizacion> repoAutorizaciones = new ArrayList<RepoAutorizacion>();
        repoAutorizaciones.add(new RepoAutorizacion());

        for (Autorizacion autorizacion : autorizaciones) {
            RepoAutorizacion repoAutorizacion = new RepoAutorizacion(autorizacion.RepoIdAdeT(), autorizacion.RepoTitulo(), autorizacion.RepoUbicacion(), autorizacion.RepoEstado(), autorizacion.RepoApertura(), autorizacion.RepoCierre(), autorizacion.RepoDuracion());
            repoAutorizaciones.add(repoAutorizacion);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoAutorizaciones);
        return GenerarReporteTipoLista("ListadoAutorizacion.jrxml", "ListadoAutorizaciones", ds);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Blob GenerarReporteTipoLista(String entrada, String salida, JRBeanCollectionDataSource ds) throws JRException, IOException{
        File rutaEntrada = new File(getClass().getResource(entrada).getPath());

        InputStream input = new FileInputStream(rutaEntrada);
        JasperDesign jasperDesign = JRXmlLoader.load(input);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ds", ds);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
        return ExportarReporte(jasperPrint, salida);
    }

    private static Blob ExportarReporte(JasperPrint jasperPrint, String nombreArchivo) throws JRException, IOException{
        File pdf = File.createTempFile("output.", ".pdf");
        JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf));

        byte[] fileContent = new byte[(int) pdf.length()];

        if (!(pdf.exists())) {
            try {
                pdf.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(pdf);
            fileInputStream.read(fileContent);
            fileInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return new Blob(nombreArchivo + ".pdf", "application/pdf", fileContent);

        } catch (Exception e) {
            byte[] result = new String("error en crear archivo").getBytes();
            return new Blob("error.txt", "text/plain", result);
        }
    }
}
