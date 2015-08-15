package main;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import anotations.Dulce;
import anotations.FrutoSeco;

public class Inicio {

	public static void main(String[] args) {

		BolsitaMarcaA bolsitaMarcaA = new BolsitaMarcaA();
		int caloriasTotalesA = contarCaloriasTotalesFrutosSecos(bolsitaMarcaA);
		System.out.println("Calorias totales de los frutos secos de una bolsita de la marca 'A': " + caloriasTotalesA);

		System.out.println("");

		BolsitaMarcaB bolsitaMarcaB = new BolsitaMarcaB();
		int caloriasTotalesB = contarCaloriasTotalesFrutosSecos(bolsitaMarcaB);
		System.out.println("Calorias totales de los frutos secos de una bolsita de la marca 'B': " + caloriasTotalesB);
	
		System.out.println("");
		
		System.out.println("Valor por defecto dentro de la declaración de la anotación Dulce: "+ Dulce.VALOR_POR_DEFECTO);
	}

	/**
	 * Función que cuenta las calorías totales de cualquier variable anotadas con FrutoSeco
	 * 
	 * @param bolsita
	 *            objeto de la clase Bolsita del que extraer las calorías de únicamente los FrutosSecos
	 * @return cantidad de calorías totales de únicamente los FrutosSecos
	 */
	public static int contarCaloriasTotalesFrutosSecos(final Object bolsita) {
		Class<?> claseBolsita = bolsita.getClass();

		int caloriasTotales = 0;

		final Field[] variables = claseBolsita.getDeclaredFields(); // Incluye variables privadas
		for (final Field variable : variables) {

			final Annotation anotacionObtenida = variable.getAnnotation(FrutoSeco.class);

			if (anotacionObtenida != null && anotacionObtenida instanceof FrutoSeco) {
				final FrutoSeco anotacionFrutoSeco = (FrutoSeco) anotacionObtenida;

				int calorias = anotacionFrutoSeco.calorias();

				int cantidad = 0;
				try {
					variable.setAccessible(true);
					cantidad = variable.getInt(bolsita);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}

				String nombreFrutoSeco = variable.getName();

				System.out.println("	-Hay " + cantidad + " de " + nombreFrutoSeco + ", y cada una tiene " + calorias + " calorías");

				caloriasTotales += (cantidad * calorias);
			}
		}

		return caloriasTotales;
	}
}
