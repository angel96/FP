package fp.grados.utiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import fp.grados.tipos.Alumno;
import fp.grados.tipos.AlumnoImpl;
import fp.grados.tipos.AlumnoImpl2;
import fp.grados.tipos.Asignatura;
import fp.grados.tipos.AsignaturaImpl;
import fp.grados.tipos.Beca;
import fp.grados.tipos.BecaImpl;
import fp.grados.tipos.Categoria;
import fp.grados.tipos.Centro;
import fp.grados.tipos.CentroImpl;
import fp.grados.tipos.CentroImpl2;
import fp.grados.tipos.Departamento;
import fp.grados.tipos.DepartamentoImpl;
import fp.grados.tipos.DepartamentoImpl2;
import fp.grados.tipos.Despacho;
import fp.grados.tipos.DespachoImpl;
import fp.grados.tipos.Espacio;
import fp.grados.tipos.EspacioImpl;
import fp.grados.tipos.Grado;
import fp.grados.tipos.GradoImpl;
import fp.grados.tipos.GradoImpl2;
import fp.grados.tipos.Nota;
import fp.grados.tipos.Profesor;
import fp.grados.tipos.ProfesorImpl;
import fp.grados.tipos.ProfesorImpl2;
import fp.grados.tipos.TipoAsignatura;
import fp.grados.tipos.TipoBeca;
import fp.grados.tipos.TipoEspacio;

public class Grados<T> {

	public static <T> List<T> leeFichero(String nombreFichero,
			Function<String, T> funcion_deString_aT) {
		List<T> res = null;
		try {
			res = Files.lines(Paths.get(nombreFichero))
					.map(funcion_deString_aT).collect(Collectors.toList());
		} catch (IOException e) {
			System.out
					.println("Error en lectura del fichero: " + nombreFichero);
		}

		return res;
	}

	/**************************************** BOOLEAN *********************************************/
	private static Boolean setUsarJava8 = true;

	public static void setUsarJava8(Boolean b) {
		setUsarJava8 = b;
	}

	private static Boolean usarImplementacionMapProfesor = true;

	public static void setUsarImplementacionMapProfesor(Boolean b) {
		usarImplementacionMapProfesor = b;
	}

	/****************** ALUMNOIMPL **************************************/
	private static Set<Alumno> alumnoscreados = new HashSet<Alumno>();

	public static Alumno createAlumno(String dni, String nombre,
			String apellidos, LocalDate fechaNacimiento, String email) {
		Alumno a = setUsarJava8 ? new AlumnoImpl2(dni, nombre, apellidos,
				fechaNacimiento, email) : new AlumnoImpl(dni, nombre,
				apellidos, fechaNacimiento, email);

		alumnoscreados.add(a);

		return a;
	}

	public static Alumno createAlumno(Alumno alum) {

		Alumno a = createAlumno(alum.getDNI(), alum.getNombre(),
				alum.getApellidos(), alum.getFechaNacimiento(), alum.getEmail());

		copiaAsignaturas(alum, a);

		copiaNotas(alum, a);

		return a;
	}

	private static void copiaAsignaturas(Alumno alum, Alumno a) {
		for (Asignatura asig : alum.getAsignaturas()) {
			a.matriculaAsignatura(asig);
		}
	}

	private static void copiaNotas(Alumno alum, Alumno a) {
		for (Nota n : alum.getExpediente().getNotas()) {
			a.getExpediente().nuevaNota(n);

		}
	}

	public static Alumno createAlumno(String s) {

		Alumno a = setUsarJava8 ? new AlumnoImpl2(s) : new AlumnoImpl(s);
		alumnoscreados.add(a);
		return a;
	}

	public static List<Alumno> createAlumnos(String nombreFichero) {
		List<Alumno> res = leeFichero(nombreFichero, x -> createAlumno(x));
		return res;
	}

	public static Integer getNumAlumnosCreados() {
		return alumnoscreados.size();
	}

	public static Set<Alumno> getAlumnosCreados() {
		return new HashSet<Alumno>(alumnoscreados);
	}

	/******************************* ASIGNATURAIMPL ****************************/
	private static Map<String, Asignatura> asignaturascodigoscreadas = new HashMap<String, Asignatura>();

	// PARAMETROS
	public static Asignatura createAsignatura(String nombre, String codigo,
			Double creditos, TipoAsignatura tipo, Integer curso,
			Departamento departamento) {
		Asignatura a = new AsignaturaImpl(nombre, codigo, creditos, tipo,
				curso, departamento);
		asignaturascodigoscreadas.put(a.getCodigo(), a);
		return a;
	}

	// STRING
	public static Asignatura createAsignatura(String s) {
		Asignatura asig = new AsignaturaImpl(s);
		asignaturascodigoscreadas.put(asig.getCodigo(), asig);
		return asig;
	}

	// ARCHIVO
	public static List<Asignatura> createAsignaturas(String nombreFichero) {
		return leeFichero(nombreFichero, s -> createAsignatura(s));
	}

	public static Integer getNumAsignaturasCreadas() {
		return asignaturascodigoscreadas.size();
	}

	public static Set<Asignatura> getAsignaturasCreadas() {
		return new HashSet<Asignatura>(asignaturascodigoscreadas.values());
	}

	public static Set<String> getCodigosAsignaturasCreadas() {

		return new HashSet<String>(asignaturascodigoscreadas.keySet());
	}

	public static Asignatura getAsignaturaCreada(String codigo) {
		return asignaturascodigoscreadas.get(codigo);
	}

	/******************************* BECAIMPL ****************************/
	private static Set<Beca> becas = new HashSet<Beca>();

	public static Beca createBeca(String codigo, Double cuantia,
			Integer duracion, TipoBeca tipo) {
		Beca b1 = new BecaImpl(codigo, cuantia, duracion, tipo);
		becas.add(b1);
		return b1;
	}

	public static Beca createBeca(String codigo, TipoBeca tipo) {
		Beca b1 = new BecaImpl(codigo, tipo);
		becas.add(b1);
		return b1;
	}

	public static Beca createBeca(Beca original) {
		return createBeca(original.getCodigo(), original.getCuantiaTotal(),
				original.getDuracion(), original.getTipo());
	}

	public static Beca createBeca(String s) {
		Beca b2 = new BecaImpl(s);
		becas.add(b2);
		return b2;
	}

	public static List<Beca> createBecas(String nombreFichero) {
		List<Beca> res = leeFichero(nombreFichero, x -> createBeca(x));
		return res;
	}

	public static Integer getNumBecasCreadas() {

		return becas.size();
	}

	public static Integer getNumBecasTipo(TipoBeca b) {

		return (int) becas.stream().filter(x -> x.getTipo().equals(b)).count();

	}

	public static Set<Beca> getBecasCreadas() {
		return new HashSet<Beca>(becas);
	}

	/************************************ CENTROIMPL ********************************************/
	private static Set<Centro> centros = new HashSet<Centro>();

	public static Centro createCentro(String nombre, String direccion,
			Integer numeroPlantas, Integer numeroSotanos) {
		Centro c = setUsarJava8 ? new CentroImpl2(nombre, direccion,
				numeroPlantas, numeroSotanos) : new CentroImpl(nombre,
				direccion, numeroPlantas, numeroSotanos);

		centros.add(c);
		return c;
	}

	public static Centro createCentro(Centro centro) {
		Centro c = createCentro(centro.getNombre(), centro.getDireccion(),
				centro.getNumeroPlantas(), centro.getNumeroSotanos());

		tomaEspacios(centro, c);

		return c;
	}

	private static void tomaEspacios(Centro centro, Centro c) {	
		centro.getEspacios().stream().forEach(e-> c.nuevoEspacio(e));
	}

	public static Integer getNumCentrosCreados() {
		return centros.size();
	}

	public static Set<Centro> getCentrosCreados() {
		return new HashSet<Centro>(centros);
	}

	public static Integer getMaxPlantas() {

		return centros.isEmpty() ? null : centros.stream()
				.max(Comparator.comparing(Centro::getNumeroPlantas))
				.map(x -> x.getNumeroPlantas()).get();
	}

	public static Integer getMaxSotanos() {
		return centros.isEmpty() ? null : centros.stream()
				.max(Comparator.comparing(Centro::getNumeroSotanos))
				.map(x -> x.getNumeroSotanos()).get();

	}

	public static Double getMediaPlantas() {
		return centros.isEmpty() ? 0. : centros.stream()
				.mapToDouble(x -> x.getNumeroPlantas()).average().getAsDouble();
	}

	public static Double getMediaSotanos() {
		return centros.isEmpty() ? 0. : centros.stream()
				.mapToDouble(x -> x.getNumeroSotanos()).average().getAsDouble();
	}

	/************************************ DEPARTAMENTOIMPL **************************************/
	private static Set<Departamento> dptos = new HashSet<Departamento>();

	public static Departamento createDepartamento(String nombre) {
		Departamento dept = setUsarJava8 ? new DepartamentoImpl2(nombre)
				: new DepartamentoImpl(nombre);
		dptos.add(dept);
		return dept;
	}

	public static Integer getNumDepartamentosCreados() {
		return dptos.size();
	}

	public static Set<Departamento> getDepartamentosCreados() {
		return new HashSet<Departamento>(dptos);
	}

	/************************************ DespachoImpl ******************************************/
	private static SortedSet<Despacho> desps = new TreeSet<Despacho>();

	public static Despacho createDespacho(String name, Integer capacity,
			Integer stage) {
		Despacho desp = new DespachoImpl(name, capacity, stage);
		desps.add(desp);
		espacios.add(desp);
		return desp;
	}

	public static Despacho createDespacho(Despacho desp) {

		return createDespacho(desp.getNombre(), desp.getCapacidad(),
				desp.getPlanta());
	}

	public static Despacho createDespacho(String s) {
		Despacho result = new DespachoImpl(s);
		desps.add(result);
		espacios.add(result);
		return result;
	}

	public static List<Despacho> createDespachos(String nombreFichero) {
		return leeFichero(nombreFichero, x -> createDespacho(x));
	}

	/************************************ EspacioImpl ******************************************/
	private static SortedSet<Espacio> espacios = new TreeSet<Espacio>();

	public static Espacio createEspacio(TipoEspacio tipo, String name,
			Integer capacity, Integer stage) {
		Espacio e = new EspacioImpl(tipo, name, capacity, stage);
		espacios.add(e);
		return e;
	}

	public static Espacio createEspacio(Espacio e) {
		return createEspacio(e.getTipo(), e.getNombre(), e.getCapacidad(),
				e.getPlanta());
	}

	public static Espacio createEspacio(String s) {
		Espacio e = new EspacioImpl(s);
		espacios.add(e);
		return e;
	}

	public static List<Espacio> createEspacios(String nombreFichero) {
		List<Espacio> res = leeFichero(nombreFichero, x -> createEspacio(x));
		return res;
	}

	public static Integer getNumEspaciosCreados() {

		return espacios.size();
	}

	public static SortedSet<Espacio> getEspaciosCreados() {
		return new TreeSet<Espacio>(espacios);
	}

	public static Integer getPlantaMayorEspacio() {
		return espacios.isEmpty() ? null : espacios.stream()
				.max(Comparator.comparing(Espacio::getPlanta))
				.map(x -> x.getPlanta()).get();
	}

	public static Integer getPlantaMenorEspacio() {
		return espacios.isEmpty() ? null : espacios.stream()
				.min(Comparator.comparing(Espacio::getPlanta))
				.map(x -> x.getPlanta()).get();
	}

	/************************************
	 * GradoImpl
	 * 
	 ******************************************/
	private static Set<Grado> gradoscreados = new HashSet<Grado>();

	public static Grado createGrado(String nombre,
			Set<Asignatura> asignaturasObligatorias,
			Set<Asignatura> asignaturasOptativas,
			Double numerominimodecreditosoptativas) {
		Grado g;
		g = setUsarJava8 ? new GradoImpl2(nombre, asignaturasObligatorias,
				asignaturasOptativas, numerominimodecreditosoptativas)
				: new GradoImpl(nombre, asignaturasObligatorias,
						asignaturasOptativas, numerominimodecreditosoptativas);

		actualizaPobsGrado(g);
		return g;
	}

	private static void actualizaPobsGrado(Grado g) {

		gradoscreados.add(g);

	}

	public static Integer getNumGradosCreados() {
		return gradoscreados.size();
	}

	public static Set<Grado> getGradosCreados() {
		return new HashSet<Grado>(gradoscreados);
	}

	public static Double getMediaAsignaturasGrados() {
		Double result = 0.;

		result = gradoscreados
				.stream()
				.filter(x -> !gradoscreados.isEmpty())
				.mapToDouble(
						x -> x.getAsignaturasObligatorias().size()
								+ x.getAsignaturasOptativas().size()).sum()
				/ gradoscreados.size();

		return result;
	}

	public static Double getMediaAsignaturasObligatoriasGrados() {

		Double result = 0.;

		result = gradoscreados.stream().filter(x -> !gradoscreados.isEmpty())
				.mapToDouble(x -> x.getAsignaturasObligatorias().size()).sum()
				/ gradoscreados.size();

		return result;
	}

	public static Double getMediaAsignaturasOptativasGrados() {
		Double result = 0.;
		result = gradoscreados.stream().filter(x -> !gradoscreados.isEmpty())
				.mapToDouble(x -> x.getAsignaturasOptativas().size()).sum()
				/ gradoscreados.size();

		return result;
	}

	/************************************************ PROFESORIMPL **********************************/

	private static Set<Profesor> profesorescreados = new HashSet<Profesor>();

	public static Profesor createProfesor(String dni, String nombre,
			String apellidos, LocalDate fechaNacimiento, String email,
			Categoria categoria, Departamento departamento) {
		Profesor p;
		p = usarImplementacionMapProfesor ? new ProfesorImpl2(dni, nombre,
				apellidos, fechaNacimiento, email, categoria, departamento)
				: new ProfesorImpl(dni, nombre, apellidos, fechaNacimiento,
						email, categoria, departamento);

		profesorescreados.add(p);

		return p;

	}

	public static Profesor createProfesor(Profesor p) {

		Profesor prof = createProfesor(p.getDNI(), p.getNombre(),
				p.getApellidos(), p.getFechaNacimiento(), p.getEmail(),
				p.getCategoria(), p.getDepartamento());

		p.getAsignaturas()
				.stream()
				.forEach(
						x -> prof.imparteAsignatura(x,
								p.dedicacionAsignatura(x)));
		p.getTutorias()
				.stream()
				.forEach(
						x -> prof.nuevaTutoria(x.getHoraComienzo(),
								x.getDuracion(), x.getDiaSemana()));

		return prof;
	}

	public static Integer getNumProfesoresCreados() {
		return profesorescreados.size();
	}

	public static Set<Profesor> getProfesoresCreados() {
		return new HashSet<Profesor>(profesorescreados);
	}
}
