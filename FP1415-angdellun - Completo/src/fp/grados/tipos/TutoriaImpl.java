package fp.grados.tipos;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import fp.grados.excepciones.ExcepcionTutoriaNoValida;

public class TutoriaImpl implements Tutoria {

	private LocalTime HoraComienzo, HoraFin;
	private DayOfWeek DiaSemana;
	/************************************************CONSTRUCTORES***************************************/
	public TutoriaImpl(DayOfWeek DiaSemana, LocalTime HoraComienzo,
			LocalTime HoraFin) {
		
		checkDiaSemana(DiaSemana);
		this.DiaSemana = DiaSemana;
		this.HoraComienzo = HoraComienzo;
		this.HoraFin = HoraFin;
		checkDuracion(getDuracion());
	}

	public TutoriaImpl(DayOfWeek DiaSemana, LocalTime HoraComienzo,
			Integer Duracion) {

		checkDiaSemana(DiaSemana);
		this.DiaSemana = DiaSemana;
		this.HoraComienzo = HoraComienzo;
		this.HoraFin = HoraComienzo.plusMinutes(Duracion);
		checkDuracion(Duracion);

	}
	
	public TutoriaImpl (String s){
		String [] partes = s.split(",");
		if(partes.length != 3){
			throw new IllegalArgumentException("Cadena con formato no valido");
		}
		
		String dia = partes[0].trim();
		DayOfWeek DiaSemana = pasarDias(dia);
		checkDiaSemana(DiaSemana);
		LocalTime HoraComienzo = LocalTime.parse(partes[1].trim());
		LocalTime HoraFin = LocalTime.parse(partes[2].trim());
		Integer duracion = (int) HoraComienzo.until(HoraFin, ChronoUnit.MINUTES);
		checkDuracion(duracion);
		
		this.DiaSemana = DiaSemana;
		this.HoraComienzo = HoraComienzo;
		this.HoraFin = HoraFin;
		
	}	

	private DayOfWeek pasarDias(String dia) {

		DayOfWeek DiaSemana = null;			
		if(dia.equals("L")){
			DiaSemana = DayOfWeek.MONDAY;
		} else if (dia.equals("M")){
			DiaSemana = DayOfWeek.TUESDAY;
		} else if (dia.equals("X")){
			DiaSemana = DayOfWeek.WEDNESDAY;
		} else if (dia.equals("J")){
			DiaSemana = DayOfWeek.THURSDAY;
		} else if (dia.equals("V")){
			DiaSemana = DayOfWeek.FRIDAY;
		} else {
			throw new ExcepcionTutoriaNoValida("Caracter Incorrecto");
		}
		
		return DiaSemana;
	}

	public LocalTime getHoraComienzo() {
		return HoraComienzo;

	}

	public Integer getDuracion() {		
		return (int) HoraComienzo.until(HoraFin, ChronoUnit.MINUTES);
	}

	public LocalTime getHoraFin() {

		return HoraFin;
	}

	public String getD() {

		String D = null;

		if (DiaSemana == DayOfWeek.MONDAY) {

			D = "L";

		} else if (DiaSemana == DayOfWeek.TUESDAY) {

			D = "M";

		} else if (DiaSemana == DayOfWeek.WEDNESDAY) {

			D = "X";
		} else if (DiaSemana == DayOfWeek.THURSDAY) {

			D = "J";

		} else if (DiaSemana == DayOfWeek.FRIDAY) {
			D = "V";

		} else if (DiaSemana == DayOfWeek.SATURDAY
				|| DiaSemana == DayOfWeek.SUNDAY) {
			throw new ExcepcionTutoriaNoValida();
		}
		return D;
	}

	public DayOfWeek getDiaSemana() {

		return DiaSemana;
	}

	public int hashCode() {
		return getDiaSemana().hashCode() + getHoraComienzo().hashCode() * 31;
	}

	public boolean equals(Object o) {

		boolean result = false;

		if (o instanceof Tutoria) {
			Tutoria t = (Tutoria) o;

			result = getDiaSemana().equals(t.getDiaSemana())
					&& getHoraComienzo().equals(t.getHoraComienzo());

		}

		return result;
	}

	public int compareTo(Tutoria t) {
		int result;

		DayOfWeek DiaSemana1 = getDiaSemana();
		DayOfWeek DiaSemana2 = t.getDiaSemana();
		result = DiaSemana1.compareTo(DiaSemana2);
		if (result == 0) {
			LocalTime HoraComienzo1 = getHoraComienzo();
			LocalTime HoraComienzo2 = t.getHoraComienzo();
			result = HoraComienzo1.compareTo(HoraComienzo2);
		}

		return result;
	}
	/************************************************CHECKERS***************************************/
	private void checkDiaSemana(DayOfWeek DiaSemana) {

		if (DiaSemana == DayOfWeek.SATURDAY || DiaSemana == DayOfWeek.SUNDAY) {

			throw new ExcepcionTutoriaNoValida();

		}

	}

	private void checkDuracion(Integer Duracion) {

		if (Duracion < 15) {
			throw new ExcepcionTutoriaNoValida();
		}

	}
	/************************************************TOSTRING***************************************/
	public String toString() {

		return getD() + " " + HoraComienzo + "-" + HoraFin;
	}

}
