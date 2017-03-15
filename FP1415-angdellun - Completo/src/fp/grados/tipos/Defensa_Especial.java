package fp.grados.tipos;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Defensa_Especial {

	public static Map<String, List<Double>> notasPorAsignatura(List<Nota> notas) {
		
		return notas.stream().collect(Collectors.toMap(x -> x.getAsignatura().getCodigo(), x -> notas.stream()
				.filter(y -> y.equals(x)).mapToDouble(y -> y.getValor()).boxed().collect(Collectors.toList())));

	}
	public static String asignaturaCuatrimestralMasCreditos(List<Asignatura> ls){
//		return ls.stream().filter(x-> !x.getTipo().equals(TipoAsignatura.ANUAL)).max(Comparator.comparing(x-> x.getCreditos())).get().getNombre();
		Comparator<Asignatura> cmp = Comparator.comparing(x-> x.getCreditos());
		cmp.reversed();
		ls.sort(cmp);
		String res = "";
		if(ls.get(0).getTipo() != TipoAsignatura.ANUAL){
			res = ls.get(0).getNombre();
		} else {
			res = null;
		}
		return res;
		
	}
}
