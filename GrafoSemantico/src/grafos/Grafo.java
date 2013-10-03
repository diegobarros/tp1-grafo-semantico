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
	private ArrayList<Integer>[] listaAdjInt;
	
	private ArrayList<Aresta> arestas;

	/**
	 * Cria um novo grafo vazio com V vértices e 0 Arestas
	 * 
	 * @param numeroDeVertices Número Total de Vértices do Grafo
	 * @throws java.lang.IllegalArgumentException SE V < 0
	 */
	@SuppressWarnings("unchecked")
	public Grafo(int numeroDeVertices) {
		
		if (numeroDeVertices >= 0) {
			
			this.numeroDeVertices = numeroDeVertices;
			this.numeroDeArestas = 0;
			arestas = new ArrayList<Aresta>();
			
			listaAdj = (ArrayList<Aresta>[]) new ArrayList[numeroDeVertices];
			listaAdjInt = (ArrayList<Integer>[]) new ArrayList[numeroDeVertices];
			
			for (int u = 0; u < numeroDeVertices; u++) {
				listaAdj[u] = new ArrayList<Aresta>();
				listaAdjInt[u] = new ArrayList<Integer>();
			}
			
				
			
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
		arestas.add(aresta);
		AdicionaAresta(u, v);

	} // Fim do método AdicionaAresta
	
	
	public void RemoveAresta(Aresta aresta) {
		
		
		int u = aresta.NoFonte();
		int v = aresta.OutroVertice(u);
		/*
		
		
		if (listaAdj[u].contains(aresta) && arestas.contains(aresta)) {
		
			int indiceU = listaAdj[u].indexOf(aresta);
			int indiceV = listaAdj[v].indexOf(aresta);
			int indiceAresta = arestas.indexOf(aresta);
			
			listaAdj[u].remove(indiceU);	// Adiciona uma aresta de u para v
			listaAdj[v].remove(indiceV); 	// Adiciona outra resta de v para u
			arestas.remove(indiceAresta);
		}*/
		
		RemoveAresta(u, v);
		numeroDeArestas--;				// Incrementa o número de arestasv
		
	} // Fim do método RemoveAresta
	
	
	/**
	 * Adiciona uma aresta não direcionada de u → v  no Grafo
	 * @param u Vértice origem da aresta
	 * @param v Vértice destino da aresta
	 * @throws java.lang.IndexOutOfBoundsException ao menos que ambos 0 <= u < V E 0 <= v < V
	 */
	public void RemoveAresta(int u, int v) {
		
		if (u < 0 || u >= numeroDeVertices)
			throw new IndexOutOfBoundsException();
		else if (v < 0 || v >= numeroDeVertices)
			throw new IndexOutOfBoundsException();
		else {
			
			if (listaAdjInt[u].contains(v)) {
				
				int indice = listaAdjInt[u].indexOf(v);
				int outroIndice = listaAdjInt[v].indexOf(u);
				
				listaAdjInt[u].remove(indice);		// Adiciona uma aresta de u para v
				listaAdjInt[v].remove(outroIndice);	// Adiciona uma aresta de u para v
			}
			
		} // Fim de if/else
		
	} // Fim do método AdicionaAresta

	
	
	
	
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
			
			if (!listaAdjInt[u].contains(v)) {

				listaAdjInt[u].add(v);	// Adiciona uma aresta de u para v
				listaAdjInt[v].add(u); 	// Adiciona outra resta de v para u
			}

		} // Fim de if/else
		
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
	
	public Iterable<Integer> AdjacenciasInt(int u) {
		return listaAdjInt[u];
	}
	
	/**
	 * Obtém a lista dos vizinhos do vértice u como um Iterable
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
	
	
	/**
	 * Obtém os rótulos relacionados a cada uma dos vértices do grafo
	 * @return Os rótulos dos vértices do grafo
	 */
	public String[] ObtemRotulosVertices() {
		
		ArrayList<String> palavras = new ArrayList<String>();

		for (int u = 0; u < listaAdj.length; u++) {
			
			for (Aresta aresta : arestas) {

				if (aresta.getU() == u && !palavras.contains(aresta.getRotuloVerticeInicial()))
					palavras.add(aresta.getRotuloVerticeInicial());
				else if (aresta.getV() == u && !palavras.contains(aresta.getRotuloVerticeFinal()))
					palavras.add(aresta.getRotuloVerticeFinal());
					
			} // Fim foreach
			
		} // Fim for int u = 0
		
		return palavras.toArray(new String[palavras.size()]);
		
	} // Fim do método ObtemResultadosVertices
	
	
	
	/**
	 * Dado um vetor de rótulos, retorna os valores inteiros referente a eles
	 * @param rotulosVertices
	 * @return
	 */
	public Integer[] ObtemVerticesInteiros(String[] rotulosVertices) {
		
		ArrayList<Integer> verticesInteiros = new ArrayList<Integer>();
		String[] nomesDosVertices = ObtemRotulosVertices();
		
		for (int i = 0; i < rotulosVertices.length; i++) {
			
			for (int j = 0; j < nomesDosVertices.length; j++) {
				
				if(rotulosVertices[i].equalsIgnoreCase(nomesDosVertices[j]))
					verticesInteiros.add(j);
				
			} // Fim for int j = 0

		} // Fim for int i = 0
		
		return verticesInteiros.toArray(new Integer[verticesInteiros.size()]);
		
		
	} // Fim do método ObtemVerticesInteiros
	
	/**
	 * Obtém uma string que representa a lista de adjacências do grafo
	 * @return String que representa a lista de adjacências do grafo
	 */
	public String ListaAdjString() {
		
		String[] rotulosArestas = ObtemRotulosVertices();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n");
		
		for (int u = 0; u < numeroDeVertices; u++) {
			
			stringBuilder.append(rotulosArestas[u] + "  ");
			
			for (int v : listaAdjInt[u])
				stringBuilder.append(rotulosArestas[v] + " ");
			
			stringBuilder.append("\n");
			
		} // Fim for int u = 0
		
		stringBuilder.append("\n");
		
		return stringBuilder.toString();
		
	} // Fim do método ListaAdjString
	
	
	public String ListaAdjInt() {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n");
		
		for (int u = 0; u < numeroDeVertices; u++) {
			
			stringBuilder.append(u + "  ");
			
			for (int v : listaAdjInt[u])
				stringBuilder.append(v + " ");
			
			stringBuilder.append("\n");
			
		} // Fim for int u = 0
		
		stringBuilder.append("\n");
		
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
