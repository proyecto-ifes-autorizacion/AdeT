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

    public void ListadoVehiculoPDF(List<Vehiculo> vehiculos){
        try {
            File rutaEntrada = new File(getClass().getResource("ListadoVehiculo.jrxml").getPath());
            String rutaSalida = System.getProperty("user.home") + File.separatorChar + "Downloads\\ListadoVehiculos.pdf";

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

}
