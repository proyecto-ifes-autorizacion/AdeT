package domainapp.application.fixture.scenarios;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.simple.dominio.empresa.EmpresaRepository;
import domainapp.modules.simple.dominio.trabajador.Trabajador;
import domainapp.modules.simple.dominio.trabajador.TrabajadorRepository;
import lombok.Getter;

public class TrabajadoresRecreate extends FixtureScript {

    public final List<String> cuils = Collections.unmodifiableList(Arrays.asList(
            "27-16763197-9",
            "20-24638284-6",
            "27-15978291-2",
            "27-28669063-1",
            "20-28117068-1",
            "27-23417478-4",
            "20-20072768-9",
            "20-29071937-1",
            "20-30493115-9",
            "27-39475942-0",
            "27-25794244-1",
            "27-32945899-8",
            "20-15307036-7",
            "27-32005294-1",
            "20-34840344-5",
            "27-22442885-8",
            "20-23842197-2",
            "20-19184735-3",
            "27-37429026-8",
            "20-39198712-8",
            "20-27990205-1",
            "27-19815627-1",
            "20-33330626-2",
            "27-34165350-6",
            "20-36403927-8",
            "20-34772750-0",
            "20-24427215-0",
            "20-15043016-1",
            "20-29414687-1",
            "27-18366097-0",
            "20-39079882-1",
            "20-17785316-8",
            "27-23338485-3",
            "27-33676776-3",
            "20-15796454-6",
            "27-33167280-8",
            "20-33179880-6",
            "27-32244035-8",
            "27-24497869-4",
            "20-38766471-1",
            "27-17219698-5",
            "27-22137723-2",
            "20-38080763-5",
            "20-20911473-9",
            "27-22544058-0",
            "27-39451402-4",
            "27-34967068-7",
            "20-20199884-7",
            "27-32237186-2",
            "20-16596101-9",
            "20-17594452-8"));
    public final List<String> nombres = Collections.unmodifiableList(Arrays.asList(
            "Maria",
            "Jose",
            "Josefa",
            "Isabel",
            "Manuel",
            "Carmen",
            "Jesus",
            "Angel",
            "Miguel",
            "Maria Jose",
            "Antonia",
            "Ana",
            "Carlos",
            "Maria Angeles",
            "Alejandro",
            "Ana Maria",
            "Francisco Javier",
            "Rafael",
            "Laura",
            "Juan Jose",
            "Luis",
            "Maria Teresa",
            "Juan Antonio",
            "Elena",
            "Sergio",
            "Fernando",
            "Juan Carlos",
            "Andres",
            "Jose Manuel",
            "Raquel",
            "Ramon",
            "Raul",
            "Mercedes",
            "Irene",
            "Alvaro",
            "Beatriz",
            "Emilio",
            "Angela",
            "Julia",
            "Julian",
            "Rocio",
            "Amparo",
            "Adrian",
            "Ruben",
            "Rosa",
            "Ascension",
            "Andrea",
            "Pascual",
            "Maria Jesus",
            "Mario",
            "Adriana"));
    public final List<String> apellidos = Collections.unmodifiableList(Arrays.asList(
            "Garcia",
            "Martinez",
            "Lopez",
            "Sanchez",
            "Gonzalez",
            "Gomez",
            "Fernandez",
            "Moreno",
            "Jimenez",
            "Perez",
            "Rodriguez",
            "Navarro",
            "Ruiz",
            "Diaz",
            "Serrano",
            "Hernandez",
            "Muñoz",
            "Saez",
            "Romero",
            "Rubio",
            "Alfaro",
            "Molina",
            "Lozano",
            "Castillo",
            "Picazo",
            "Ortega",
            "Morcillo",
            "Cano",
            "Marin",
            "Cuenca",
            "Garrido",
            "Torres",
            "Corcoles",
            "Gil",
            "Ortiz",
            "Calero",
            "Valero",
            "Cebrian",
            "Rodenas",
            "Alarcon",
            "Blazquez",
            "Nuñez",
            "Pardo",
            "Moya",
            "Tebar",
            "Requena",
            "Arenas",
            "Ballesteros",
            "Collado",
            "Ramirez",
            "Sanchez"));
    public final List<LocalDate> fechasDeNacimiento = Collections.unmodifiableList(Arrays.asList(
            LocalDate.parse("2003-3-5"),
            LocalDate.parse("1989-2-21"),
            LocalDate.parse("2011-10-7"),
            LocalDate.parse("2011-5-31"),
            LocalDate.parse("1970-6-17"),
            LocalDate.parse("1996-6-6"),
            LocalDate.parse("1983-3-17"),
            LocalDate.parse("1975-2-19"),
            LocalDate.parse("2010-5-23"),
            LocalDate.parse("1973-3-31"),
            LocalDate.parse("2001-7-31"),
            LocalDate.parse("1991-3-26"),
            LocalDate.parse("1992-12-1"),
            LocalDate.parse("1986-10-10"),
            LocalDate.parse("2009-4-20"),
            LocalDate.parse("2013-5-18"),
            LocalDate.parse("2005-1-7"),
            LocalDate.parse("1994-5-5"),
            LocalDate.parse("2017-6-4"),
            LocalDate.parse("1986-9-24"),
            LocalDate.parse("1999-6-9"),
            LocalDate.parse("1990-1-15"),
            LocalDate.parse("1989-6-21"),
            LocalDate.parse("2012-4-12"),
            LocalDate.parse("1993-12-23"),
            LocalDate.parse("1991-6-8"),
            LocalDate.parse("2020-10-13"),
            LocalDate.parse("2017-11-12"),
            LocalDate.parse("1989-9-10"),
            LocalDate.parse("1987-8-22"),
            LocalDate.parse("1977-9-11"),
            LocalDate.parse("1980-3-5"),
            LocalDate.parse("1973-6-25"),
            LocalDate.parse("2002-10-14"),
            LocalDate.parse("1998-2-3"),
            LocalDate.parse("1990-6-10"),
            LocalDate.parse("1988-9-1"),
            LocalDate.parse("2016-11-2"),
            LocalDate.parse("2011-3-30"),
            LocalDate.parse("1989-1-11"),
            LocalDate.parse("1978-8-24"),
            LocalDate.parse("2006-5-2"),
            LocalDate.parse("2001-5-5"),
            LocalDate.parse("1974-4-17"),
            LocalDate.parse("2004-10-19"),
            LocalDate.parse("1984-2-28"),
            LocalDate.parse("2008-9-5"),
            LocalDate.parse("2012-1-13"),
            LocalDate.parse("1991-9-3"),
            LocalDate.parse("2007-9-28"),
            LocalDate.parse("2003-7-11") ));

    public final List<Integer> empresasIds = Collections.unmodifiableList(Arrays.asList(
            6,
            4,
            6,
            3,
            3,
            6,
            1,
            4,
            6,
            1,
            5,
            5,
            1,
            1,
            3,
            5,
            2,
            2,
            4,
            1,
            6,
            5,
            2,
            1,
            3,
            1,
            3,
            5,
            4,
            5,
            5,
            3,
            1,
            2,
            1,
            6,
            6,
            4,
            6,
            3,
            5,
            3,
            5,
            4,
            2,
            4,
            6,
            1,
            2,
            2,
            3));

    public TrabajadoresRecreate() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    //@Getter
    //private Integer cantidad;

//    private final List<Trabajador> trabajadorObjects = Lists.newArrayList();
//
//    @Programmatic
//    public List<Trabajador> getTrabajadorObjects() {
//        return trabajadorObjects;
//    }

    @Override
    protected void execute(final ExecutionContext ec) {

        //final int cantidad = defaultParam("cantidad", ec, 1);

        ec.executeChild(this, new TrabajadorTearDown());

        for (int i = 0; i < cuils.size(); i++) {
            final TrabajadorCreate fs = new TrabajadorCreate();
            fs.setCuil(cuils.get(i));
            fs.setNombre(nombres.get(i));
            fs.setApellido(apellidos.get(i));
            fs.setFechaDeNacimiento(fechasDeNacimiento.get(i) );
            fs.setEmpresa(empresaRepository.Listar().get(empresasIds.get(i)-1));

            ec.executeChild(this, fs.getNombre(), fs);
            //trabajadorObjects.add(fs.getTrabajadorObject());

        }
    }

    @javax.inject.Inject
    EmpresaRepository empresaRepository;

}
