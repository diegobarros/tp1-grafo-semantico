package grafos.algoritmos;

import java.util.LinkedList;
import java.util.Stack;

import grafos.Aresta;
import grafos.Grafo;

/**
 * A classe <tt>BuscaEmLargura</tt> representa um tipo de dados para encontrar</br>
 * caminhos em um grafo com o menor número de arestas utilizando o algoritmo de </br>
 * Busca em Largura (Breadth-First Search). A mesma suporta a versão de única fonte</br>
 * (Menores caminhos de um vértice para todo os outros vértices) e uma versão de fonte múltipla</br>
 * (caminhos de qualquer um dos vértices do conjuntos de vértices para todo os outros vértices.
 * <p>
 * O construtor possuí um tempo de execução proporcional a O(V + E), onde V é o número de vértices</br>
 * e E é o número de arestas.
 * </p>
 * @author Diego Augusto de Faria Barros
 *
 */
public class BuscaEmLargura {
	
	private boolean[] marcado;			// marcado[u] = Existe um caminho de s - v
	private int[] arestaPara;			// arestaPara[u] = Aresta anterior no menor caminho de s-v
	private int[] distanciaPara;		// distTo[u] = Número de arestas do menor caminho s-v
	
	private static final int INFINITO = Integer.MAX_VALUE;
	
	
	/**
	 * Computa o menor caminho de s para qualquer outro vértice no grafo G
	 * @param grafo O grafo
	 * @param s O nó fonte
	 */
	public BuscaEmLargura(Grafo grafo, int noFonte) {
		
		marcado = new boolean[grafo.getNumeroDeVertices()];
		distanciaPara = new int[grafo.getNumeroDeVertices()];
		arestaPara = new int[grafo.getNumeroDeVertices()];
		BuscaLargura(grafo, noFonte);
		
		assert Verifica(grafo, noFonte);
		
	} // Fim do construtor
	

	/**
	 * Computa o menor caminho de qualquer dos nós fontes para todos os outros vértices no grafo
	 * @param grafo O grafo
	 * @param nosFontes Os nós fontes
	 */
	public BuscaEmLargura(Grafo grafo, Iterable<Integer> nosFontes) {
		
		marcado = new boolean[grafo.getNumeroDeVertices()];
		distanciaPara = new int[grafo.getNumeroDeVertices()];
		arestaPara = new int[grafo.getNumeroDeVertices()];
		
		
		for (int u = 0; u < grafo.getNumeroDeVertices(); u++)
			distanciaPara[u] = INFINITO;
		
		BuscaLargura(grafo, nosFontes);
		
		
	} // Fim do construtor
	
	
	/**
	 * Executa o algoritmo de Busca em Largura para única fonte
	 * @param grafo O Grafo
	 * @param noFonte O nó fonte do grafo
	 */
	private void BuscaLargura(Grafo grafo, int noFonte) {
		
		LinkedList <Integer> lista = new LinkedList <Integer>();
		
		
		for (int u = 0; u < grafo.getNumeroDeVertices(); u++)
			distanciaPara[u] = INFINITO;
		
		
		distanciaPara[noFonte] = 0;
		marcado[noFonte] = true;
		lista.add(noFonte);
		
		while (!lista.isEmpty()) {
			
			int u = lista.removeFirst();
			
			for (int v : grafo.AdjacenciasInt(u)) {
				
				if(!marcado[v]) {
					
					arestaPara[v] = u;
					distanciaPara[v] = distanciaPara[u] + 1;
					marcado[v] = true;
					lista.add(v);
					
				} // Fim de if
				
			} // Fim de foreach
				
		} // Fim while

	} // Fim do método BuscaEmLargura


	/**
	 * Executa o algoritmo de Busca em Largura para única fonte
	 * @param grafo O Grafo
	 * @param noFonte O nó fonte do grafo
	 */
	private void BuscaLargura(Grafo grafo, Iterable<Integer> nosFontes) {
	
		LinkedList <Integer> lista = new LinkedList <Integer>();
	
		for (int noFonte : nosFontes) {
		
			marcado[noFonte] = true;
			distanciaPara[noFonte] = 0;
			lista.add(noFonte);
		
		} // Fim de foreach
	
		while (!lista.isEmpty()) {
		
			int u = lista.removeFirst();
		
			for (int v : grafo.AdjacenciasInt(u)) {
			
				if(!marcado[v]) {
				
					arestaPara[v] = u;
					distanciaPara[v] = distanciaPara[u] + 1;
					marcado[v] = true;
					lista.add(v);
				
				} // Fim de if
			
			} // Fim de foreach
			
		} // Fim while
	
	} // Fim do método BuscaLargura
	
	
	/**
	 * Existe um caminho entre a fonte s (ou fontes) e o vértice v?
	 * @param u o vértice
	 * @return true se existe um cainho entre a fonte s (ou fontes), caso contrário, retorna false
	 */
	public boolean ExisteCaminhoPara(int u) {
		
		return marcado[u];
		
	} // Fim do método TemCaminhoPara
	
	
	/**
	 * O tamanho do menor caminho, ou seja, o número de arestas entre a fonte e s (ou fontes) e o vértice u
	 * @param u O vértice
	 * @return O tamanho do menor caminho, ou seja, o número de arestas entre a fonte e s (ou fontes) e o vértice u
	 */
	public int DistanciaPara (int u) {
		
		return distanciaPara[u];
		
	} // Fim do método DistanciaPara

	
	
	/**
	 * O menor caminho entre a fonte (s) e v.
	 * Número de arestas no menor caminho entre a fonte s (ou fontes) e o vértice u
	 * @param u O vértice
	 * @return A sequência de vértices no caminho como um Iterable
	 */
	public Iterable <Integer> CaminhoPara(int u) {
		
		if (!ExisteCaminhoPara(u))
			return null;
			
		Stack <Integer> caminho = new Stack<Integer>();
			
		int i;
			
		for (i = u; distanciaPara[i] != 0; i = arestaPara[i])
			caminho.push(i);
			
		caminho.push(i);
			
		return caminho;
		
		
	} // Fim do método CaminhoPara
	
	/**
	 * Verifica as condições de otimalidade
	 * @param grafo O grafo
	 * @param noFonte O nó fonte
	 * @return 
	 */
	private boolean Verifica(Grafo grafo, int noFonte) {
		
		// Verifica se a distancia do nó fonte é 0
		if (distanciaPara[noFonte] != 0) {
			System.out.println("A distância do nó fonte " + noFonte + " para si mesmo é " + distanciaPara[noFonte]);
			return false;
		}
		
		// Verifica se para cada aresta u -> v a distanciaPara[v] <= distanciaPara[u] + 1
		// permitindo v ser alcançado a partir do nó fonte
		for (int u = 0; u < grafo.getNumeroDeVertices(); u++) {
			
			for (Aresta aresta : grafo.Adjacencias(u)) {
				
				if(ExisteCaminhoPara(u) != ExisteCaminhoPara(aresta.getV())) {
					System.out.println("Aresta " + u + " - " + aresta.getV());
					System.out.println("Existe Caminho Para (" + u + ") = " + ExisteCaminhoPara(u));
					System.out.println("Existe Caminho Para (" + aresta.getV() + ") = " + ExisteCaminhoPara(aresta.getV()));
					return false;
				}
				
				if (ExisteCaminhoPara(u) && (distanciaPara[aresta.getV()] > distanciaPara[u] + 1)) {
					System.out.println("Aresta " + u + " - " + aresta.getV());
					System.out.println("Distancia Para [" + u + "] = " + distanciaPara[u]);
					System.out.println("Distancia Para [" + aresta.getV() + "] = " + distanciaPara[aresta.getV()]);
					return false;
				}
					
			} // Fim foreach
			
		} // fim for int u = 0
		
		for (int v = 0; v < grafo.getNumeroDeVertices(); v++) {
			
			if (!ExisteCaminhoPara(v) || v == noFonte)
				continue;
			
			int u = arestaPara[v];
			
			if (distanciaPara[v] != distanciaPara[u] + 1) {
				
				System.out.println("Aresta do Menor Caminho " + u + " - " + v);
				System.out.println("Distancia Para [" + u + "] = " + distanciaPara[u]);
				System.out.println("Distancia Para [" + v + "] = " + distanciaPara[v]);
				return false;
				
			} // Fim de if
			
		} // Fim for int v = 0
		
		return true;
		
	} // Fim do método Verifica
	
	
} // Fim da classe BuscaEmLargura




