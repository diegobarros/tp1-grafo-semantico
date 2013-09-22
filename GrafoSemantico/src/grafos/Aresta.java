/**
 * 
 */
package grafos;

/**
 * A class <tt>Aresta</tt> Representa uma aresta ponderada de
 * um grafo não direcionado
 * @author Diego Augusto de Faria Barros
 */
public class Aresta implements Comparable<Aresta> {
	
	private int u;
	private int v;
	private double peso;
	
	private String rotuloVerticeInicial;
	private String rotuloVerticeFinal;
	
	/**
	 *  Cria uma aresta entre u e v com um dado peso
	 * @param u Ponto (vértice) incial da aresta
	 * @param v Ponto (vértice) final da aresta
	 * @param peso
	 */
	public Aresta(int u, int v, double peso) {
		this.u = u;
		this.v = v;
		this.peso = peso;
	}

	/**
	 * @param u O ponto Inicial da Aresta
	 * @param v O ponto Final da Aresta
	 * @param peso O peso da Aresta
	 * @param rotuloVerticeIncial O ró
	 * @param rotuloVerticeFinal
	 */
	public Aresta(int u, int v, double peso, String rotuloVerticeIncial,
			String rotuloVerticeFinal) {
		this.u = u;
		this.v = v;
		this.peso = peso;
		this.rotuloVerticeInicial = rotuloVerticeIncial;
		this.rotuloVerticeFinal = rotuloVerticeFinal;
	}

	/**
	 * @return o Ponto inicial (vértice) da Aresta
	 */
	public int getU() {
		return u;
	}

	/**
	 * @return O ponto final (vértice) da Aresta
	 */
	public int getV() {
		return v;
	}

	/**
	 * @return O peso da aresta
	 */
	public double getPeso() {
		return peso;
	}

	/**
	 * @param O valor do peso
	 */
	public void setPeso(double peso) {
		this.peso = peso;
	}

	/**
	 * @return O rótulo do vértice inicial da Aresta
	 */
	public String getRotuloVerticeIncial() {
		return rotuloVerticeInicial;
	}

	/**
	 * @param  O rótulo do vértice final da aresta
	 */
	public void setRotuloVerticeIncial(String rotuloVerticeIncial) {
		this.rotuloVerticeInicial = rotuloVerticeIncial;
	}

	/**
	 * @return O rótulo do vértice final da aresta
	 */
	public String getRotuloVerticeFinal() {
		return rotuloVerticeFinal;
	}

	/**
	 * @param rotuloVerticeFinal O rótulo do vértice final da aresta
	 */
	public void setRotuloVerticeFinal(String rotuloVerticeFinal) {
		this.rotuloVerticeFinal = rotuloVerticeFinal;
	}

	
	/**
	 * Retorna o outro ponto da aresta dado um vértice.
	 * Ao menos que seja um self-loop
	 * @param vertice 
	 * @return O outro vértice da aresta
	 */
	public int OutroVertice(int vertice) {
		if (vertice == u)
			return v;
		else if (vertice == v)
			return u;
		else throw new IllegalArgumentException("Ponto Final Inválido ou Inexistente!");
	}
	
	@Override
	public int compareTo(Aresta aresta) {
		
		if (this.peso < aresta.getPeso())
			return -1;
		else if (this.peso > aresta.getPeso())
			return +1;
		else
			return 0;

	} // Fim do método CompareTo

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%d - %d %.5f", u, v, peso);
	}


} // Fim da Classe Aresta
