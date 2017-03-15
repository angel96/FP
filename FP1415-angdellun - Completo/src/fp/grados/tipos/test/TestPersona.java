package fp.grados.tipos.test;

import java.time.LocalDate;

import fp.grados.tipos.Persona;
import fp.grados.tipos.PersonaImpl;

public class TestPersona {

	public static void main(String[] args) {

		
		Persona p = new PersonaImpl("12345678Z", "Vagina", "Ruperto", LocalDate.of(1996, 03, 12));
		System.out.println(p);
	}

}
