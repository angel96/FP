package fp.grados.tipos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import fp.grados.excepciones.ExcepcionExpedienteOperacionNoPermitida;

public class ExpedienteImpl implements Expediente {

	private List<Nota> nota;

	public ExpedienteImpl() {
		this.nota = new ArrayList<Nota>();

	}

	/********************************************* METODOS *********************************************************/

	public List<Nota> getNotas() {
		return new ArrayList<Nota>(nota);
	}

	public void nuevaNota(Nota n) {

		Integer index = getNotas().indexOf(n);
		checkNotas(n);
		if (index.equals(-1)) {
			nota.add(n);

		} else {

			nota.set(index, n);

		}

	}

	public Double getNotaMedia() {
		Double result = 0.0;
		List<Nota> notas = new ArrayList<Nota>();
		for (Nota n : getNotas()) {
			if (n.getValor() >= 5) {
				notas.add(n);
			}
		}
		for (Nota n2 : notas) {
			result += n2.getValor() / notas.size();
		}
		return result;
	}

	public int hashCode() {
		return getNotas().hashCode();
	}

	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof Expediente) {
			Expediente ex = (Expediente) o;
			result = getNotas().equals(ex.getNotas());
		}
		return result;
	}

	public List<Nota> getNotasOrdenadasPorAsignatura() {
		Comparator<Nota> cmp = Comparator.comparing(x -> x.getAsignatura());
		cmp = cmp.thenComparing(Comparator.naturalOrder());
		List<Nota> result = new ArrayList<Nota>();
		result.addAll(getNotas());
		Collections.sort(result, cmp);
		return result;
	}

	public Nota getMejorNota() {

		Comparator<Nota> cmp = Comparator.comparing(Nota::getMencionHonor)
				.thenComparing(Nota::getValor).reversed()
				.thenComparing(Nota::getConvocatoria)
				.thenComparing(Nota::getCursoAcademico);

		SortedSet<Nota> notas = new TreeSet<Nota>(cmp);
		notas.addAll(this.getNotas());
		
		return notas.first();

	}

	/******************************************************* CHECKERS **************************************************/
	private void checkNotas(Nota n) {

		Integer contar = 0;
		for (Nota n2 : getNotas()) {
			if (n.getCursoAcademico().equals(n2.getCursoAcademico())
					&& n.getAsignatura().equals(n2.getAsignatura())) {
				contar++;
				if (contar == 2) {
					throw new ExcepcionExpedienteOperacionNoPermitida(
							"No valido para mas de dos convocatorias");
				}
			}

		}
	}

	/************************************************ TOSTRING ***************************************/
	public String toString() {

		return getNotas().toString();

	}

}
