/**
 * 
 */
package grafos;

import java.util.ArrayList;
import java.util.Stack;

/**
 * A classe <tt>Grafo</tt> representa um grafo não direcionado de vértices denominados de 0 (zero) até |V| - 1.
 * @author Diego Augusto de Faria Barros
 */
public class Grafo {
	
	
	private int numeroDeVertices;
	private int numeroDeArestas;
	
	private ArrayList<Integer>[] listaAdj;


	/**
	 * Cria um novo grafo vazio com V vértices e 0 Arestas
	 * 
	 * @param numeroDeVertices Número Total de Vértices do Grafo
	 * @throws java.lang.IllegalArgumentException SE V < 0
	 */
	public Grafo(int numeroDeVertices) {
		
		if (numeroDeVertices >= 0) {
			
			this.numeroDeVertices = numeroDeVertices;
			this.numeroDeArestas = 0;
			
			listaAdj = (ArrayList<Integer>[]) new ArrayList[numeroDeVertices];
			
			for (int u = 0; u < listaAdj.length; u++)
				listaAdj[u] = new ArrayList<Integer>();
			
		} else {
			throw new IllegalArgumentException("O númeo de Vértices deve ser positivo!");
		}
		
	} // Fim do Construtor
	

	/**
	 * Inicializa um grafo aleatório com V vértices e E Arestas
	 * Tempo de execução esperado é de O(V + E)
	 * 
	 * @param numeroDeVertices Número Total de Vértices do Grafo
	 * @param numeroDeArestas  Número Total de Arestas do Grafo
	 * @throws java.lang.Iljava.lang.IllegalArgumentException SE V < 0 ou E < 0
	 */
	public Grafo(int numeroDeVertices, int numeroDeArestas) {
		
		this(numeroDeVertices);
		
		if(numeroDeArestas >= 0) {
			
			for (int i = 0; i < numeroDeArestas; i++) {
				
				int u = (int) (Math.random() * numeroDeVertices);
				int v = (int) (Math.random() * numeroDeVertices);
				AdicionaAresta(u, v);
				
			} // Fim for int i = 0
			
		} // Fim de if 

	} // Fim do Construtor
	
	
	/**
	 * Obtém o número de Vértices o Grafo
	 * @return o número de vértices do Grafo
	 */
	public int getNumeroDeVertices() { return numeroDeVertices; }

	/**
	 * Obtém o número de Arestas o Grafo
	 * @return o número de arestas do Grafo
	 */
	public int getNumeroDeArestas() { return numeroDeArestas; }

	
	/**
	 * Adiciona uma aresta não direcionada de u → v  no Grafo
	 * @param u Vértice origem da aresta
	 * @param v Vértice destino da aresta
	 * @throws java.lang.IndexOutOfBoundsException ao menos que ambos 0 <= u < V E 0 <= v < V
	 */
	public void AdicionaAresta(int u, int v) {
		
		if (u < 0 || u >= numeroDeVertices)
			throw new IndexOutOfBoundsException();
		else if (v < 0 || v >= numeroDeVertices)
			throw new IndexOutOfBoundsException();
		else {
			
			numeroDeArestas++;	// Incrementa o número de arestas
			listaAdj[u].add(v);	// Adiciona uma aresta de u para v
			listaAdj[v].add(u); // Adiciona outra resta de v para u
			
		} // Fim de if/else
		
	} // Fim do método AdicionaAresta


	/**
	 * Inicializa um novo grafo que é uma cópia exata de um grafo G
	 * @param grafo o grafo para copiar
	 */
	public Grafo(Grafo grafo) {
		
		this(grafo.getNumeroDeVertices());
		this.numeroDeArestas = grafo.getNumeroDeArestas();
		
		for (int u = 0; u < grafo.numeroDeVertices; u++) {
			
			// Mantém a lista de Adj. na mesma ordem da original
			Stack<Integer> pilha = new Stack<Integer>();
			
			for (int v : grafo.listaAdj[u])
				pilha.push(v);
			
			for (int v : pilha)
				listaAdj[u].add(v);
			
		} // Fim for u = 0
		
	} // Fim do construtor Grafo
	
	
	/**
	 * Obtém a lista dos vizinhos do vértice u com um Iterable
	 * @param u Um dos vértices da aresta do grafo
	 * @return a lista dos vizinhos do vértice u com um Iterable
	 * @throws java.lang.IndexOutOfBoundsException ao menos que 0 <= u < |V|
	 */
	public Iterable<Integer> VizinhosAdjacentes(int u) {
		
		if (u < 0 || u >= numeroDeVertices)
			throw new IndexOutOfBoundsException();
		else
			return listaAdj[u];
		
	} // Fim do método VizinhosAdjacentes

	/** 
	 * A representação em string do Grafo
	 * @return A lista de adjacências do grafo
	 */
	@Override
	public String toString() {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n");
		
		for (int u = 0; u < numeroDeVertices; u++) {
			
			stringBuilder.append(u + "  ");
			
			for (int v : listaAdj[u])
				stringBuilder.append(v + " ");
			
			stringBuilder.append("\n");
			
		} // Fim for int u = 0
		
		stringBuilder.append("\n");
		
		return stringBuilder.toString();
		
	} // Fim do método toString();
	
	
} // Fim da classe Grafo
