/**
 * 
 */
package grafos;

import java.util.ArrayList;
import java.util.Stack;

/**
 * A classe <tt>GrafoTextual</tt> representa um grafo não direcionado de vértices no formato textual de 0 (zero) até |V| - 1.
 * @author Diego Augusto de Faria Barros
 */
public class Grafo {
	

	private int numeroDeVertices;
	private int numeroDeArestas;
	
	private ArrayList<Aresta>[] listaAdj;


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
			
			listaAdj = (ArrayList<Aresta>[]) new ArrayList[numeroDeVertices];
			
			for (int u = 0; u < numeroDeVertices; u++)
				listaAdj[u] = new ArrayList<Aresta>();
			
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
				
				double peso = Math.round(100 * Math.random()) / 100.00;
				
				Aresta aresta = new Aresta(u, v, peso);
				AdicionaAresta(aresta);
				
			} // Fim for int i = 0
			
		} // fim de if 

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
	public void AdicionaAresta(Aresta aresta) {
		
		int u = aresta.NoFonte();
		int v = aresta.OutroVertice(u);

		listaAdj[u].add(aresta);	// Adiciona uma aresta de u para v
		listaAdj[v].add(aresta); 	// Adiciona outra resta de v para u
		numeroDeArestas++;			// Incrementa o número de arestasv	

	} // Fim do método AdicionaAresta


	/**
	 * Inicializa um novo grafo que é uma cópia exata de um grafo G
	 * @param grafo o grafo para copiar
	 */
	public Grafo(Grafo grafo) {
		
		this(grafo.getNumeroDeVertices());
		this.numeroDeArestas = grafo.getNumeroDeArestas();
		
		for (int u = 0; u < grafo.getNumeroDeVertices(); u++) {
			
			// Mantém a lista de Adj. na mesma ordem da original
			Stack<Aresta> pilha = new Stack<Aresta>();
			
			
			for (Aresta aresta : grafo.listaAdj[u])
				pilha.push(aresta);
			
			for (Aresta aresta : pilha)
				listaAdj[u].add(aresta);
			
		} // Fim for u = 0
		
	} // Fim do construtor Grafo
	
	public Iterable<Aresta> Adjacencias(int u) {
		return listaAdj[u];
	}
	
	/**
	 * Obtém a lista dos vizinhos do vértice u com um Iterable
	 * @param u Um dos vértices da aresta do grafo
	 * @return a lista dos vizinhos do vértice u com um Iterable
	 * @throws java.lang.IndexOutOfBoundsException ao menos que 0 <= u < |V|
	 */
	public Iterable<Aresta> Arestas() {
		
		ArrayList<Aresta> listaArestas = new ArrayList<Aresta>();
		
		for (int u = 0; u < numeroDeVertices; u++) {
			
			int selfLoops = 0;
			
			for (Aresta aresta : Adjacencias(u)) {
				
				if(aresta.OutroVertice(u) > u) 
					listaArestas.add(aresta);
				else if (aresta.OutroVertice(u) == u) {
					if ((selfLoops % 2 == 0))
						listaArestas.add(aresta);
					
					selfLoops++;
					
				} // Fim if/else
				
			} // Fim de foreach
			
		} // Fim for int i = 0
		
		return listaArestas;
		
	} // Fim do método VizinhosAdjacentes
	
	
	public String ImprimeListaAdj() {
		
		StringBuilder stringBuilder = new StringBuilder();
		ArrayList<String> palavras = new ArrayList<String>();
		
		for (int u = 0; u < listaAdj.length; u++) {
			
			for (Aresta aresta : listaAdj[u]) {

				if (!palavras.contains(aresta.getRotuloVerticeInicial()))
					palavras.add(aresta.getRotuloVerticeInicial());
				else if (!palavras.contains(aresta.getRotuloVerticeFinal()))
					palavras.add(aresta.getRotuloVerticeFinal());
			}
		}
		
		
		
		for (int u = 0; u < listaAdj.length; u++) {
			
			String palavra = palavras.get(u);
			stringBuilder.append("[" + u + "] " + palavra + " -> ");
			
			
 			
			for (Aresta aresta : Arestas()) {
				
				if (aresta.getRotuloVerticeInicial().equals(palavra))
					stringBuilder.append(aresta.getRotuloVerticeFinal() + "  ");
				else
					stringBuilder.append(aresta.getRotuloVerticeInicial() + "  ");
					
			}
			
			stringBuilder.append("\n");
		}
		
		return stringBuilder.toString();
	}

	/** 
	 * A representação em string do Grafo
	 * @return A lista de adjacências do grafo
	 */
	@Override
	public String toString() {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n");
		
		for (int u = 0; u < numeroDeVertices; u++) {
			
			stringBuilder.append(" ["+ u + "]   ");
			
			
			for (Aresta aresta : listaAdj[u])
				stringBuilder.append(aresta + " ");
			
			stringBuilder.append("\n");
			
		} // Fim for int u = 0
		
		stringBuilder.append("\n");
		
		return stringBuilder.toString();
		
	} // Fim do método toString();}

} // Fim da Classe GrafoTextual
