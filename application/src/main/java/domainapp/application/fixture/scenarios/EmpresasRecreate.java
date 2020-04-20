package domainapp.application.fixture.scenarios;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.simple.dominio.empresa.Empresa;
import lombok.Getter;

public class EmpresasRecreate extends FixtureScript {

    public final List<String> NombresDeFantasia = Collections.unmodifiableList(Arrays.asList("Halliburton Argentina S.r.l.", "Schlumberger Neuquen", "Weatherford", "NABORS", "Meier & Fischer", "Ingeniería SIMA SA"));
    public final List<String> RazonesSociales = Collections.unmodifiableList(Arrays.asList("Halliburton Argentina S.r.l.", "Schlumberger Neuquen", "Weatherford", "NABORS", "Meier & Fischer", "Ingeniería SIMA SA"));
    public final List<String> Direcciones = Collections.unmodifiableList(Arrays.asList("San Fernando y Tinogasta, Neuquen", "Tte. Juan Solalique 412-440, Neuquén", "Juan José Lastra 5500, Neuquén", "Dr. Teodoro Luis Planas 4955, Neuquén", "Carlos Pellegrini 2560, Neuquén", "PIN Este - Manzana R Lote M1, Neuquén"));
    public final List<String> Telefonos = Collections.unmodifiableList(Arrays.asList("0299 449-1100", "0299 446-6239", "0299 444-1290", "Sin Dato", "0299 441-3826", "0299 449-0999"));
    public final List<String> Estados = Collections.unmodifiableList(Arrays.asList("Habilitada", "Habilitada", "Habilitada", "Habilitada", "Habilitada", "Habilitada"));

    public EmpresasRecreate() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

//    @Getter
//    private Integer cantidad;

//    private final List<Empresa> empresaObjects = Lists.newArrayList();
//
//    @Programmatic
//    public List<Empresa> getEmpresaObjects() {
//        return empresaObjects;
//    }

    @Override
    protected void execute(final ExecutionContext ec) {

        //final int cantidad = defaultParam("cantidad", ec, 1);

        ec.executeChild(this, new EmpresaTearDown());

        for (int i = 0; i < NombresDeFantasia.size(); i++) {
            final EmpresaCreate fs = new EmpresaCreate();
            fs.setNombreFantasia(NombresDeFantasia.get(i));
            fs.setRazonSocial(RazonesSociales.get(i));
            fs.setDireccion(Direcciones.get(i));
            fs.setTelefono(Telefonos.get(i));

            ec.executeChild(this, fs.getNombreFantasia(), fs);
            //empresaObjects.add(fs.getEmpresaObject());
        }
    }

}
