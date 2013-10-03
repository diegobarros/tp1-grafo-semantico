package grafos.algoritmos;

import java.util.Stack;

import grafos.Grafo;

public class BuscaEmProfundidade {
	
	private boolean[] marcado; 		// marcado[u] = existe um caminho s - u
	private int[] arestaPara;		// arestaPara[u] = última aresta no caminho s-u
	private int noFonte;			// Vértice fonte	
	private int contador;			// Número de vértices conectados à fonte
	

	/**
	 * Cria uma instância de BuscaEmProfundidade
	 * @param grafo O grafo onde será realizada a pesquisa
	 * @param noFonte O no fonte onde se inicia a busca
	 */
	public BuscaEmProfundidade(Grafo grafo, int noFonte) {
		
		this.noFonte = noFonte;
		arestaPara = new int[grafo.getNumeroDeVertices()];
		marcado = new boolean[grafo.getNumeroDeVertices()];
		BuscaProfundidade(grafo, noFonte);
		
	} // fim do construtor
	
	
	/**
	 * Número de vértices conectados à fonte
	 * @return Número de vértices conectados à fonte
	 */
	public int getContador() {	return contador;  }
	
	
	/**
	 * Executa o algoritmo de Busca em Profundidade
	 * @param grafo grafo O grafo onde será realizada a pesquisa
	 * @param u noFonte O no fonte onde se inicia a busca
	 */
	private void BuscaProfundidade(Grafo grafo, int u) { 
		
		contador++;
		marcado[u] = true;
		
		for (int v : grafo.AdjacenciasInt(u)) {
			
			if(!marcado[v]) {
				
				arestaPara[v] = u;
				BuscaProfundidade(grafo, v);
			
			} // Fim de if
			
		} // Fim de foreach
	
	} // Fim do métdo BuscaProfundidade
	
	/**
	 * Existe um caminho entre a fonte s (ou fontes) e o vértice v?
	 * @param u o vértice
	 * @return true se existe um cainho entre a fonte s (ou fontes), caso contrário, retorna false
	 */
	public boolean ExisteCaminhoPara(int u) {
		
		return marcado[u];
		
	} // Fim do método TemCaminhoPara
	
	
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
			
		for (i = u; i != noFonte; i = arestaPara[i])
			caminho.push(i);
			
		caminho.push(noFonte);
			
		return caminho;
		
		
	} // Fim do método CaminhoPara

} // Fim da classe BuscaEmProfundidade
