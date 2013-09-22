/**
 * 
 */
package grafos;

import java.util.Dictionary;

/**
 * A classe <tt>GrafoTextual</tt> representa um grafo não direcionado de vértices no formato textual de 0 (zero) até |V| - 1.
 * @author Diego Augusto de Faria Barros
 */
public class GrafoTextual extends Grafo {
	
	Dictionary<String, Integer> tabelaVertices;

	/**
	 * @param numeroDeVertices
	 */
	public GrafoTextual(int numeroDeVertices) {
		super(numeroDeVertices);
		
	}

	/**
	 * @param numeroDeVertices
	 * @param numeroDeArestas
	 */
	public GrafoTextual(int numeroDeVertices, int numeroDeArestas) {
		super(numeroDeVertices, numeroDeArestas);
		
	}

	/**
	 * @param grafo
	 */
	public GrafoTextual(Grafo grafo) {
		super(grafo);
	
	}

} // Fim da Classe GrafoTextual
