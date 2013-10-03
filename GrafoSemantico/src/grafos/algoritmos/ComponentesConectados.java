package grafos.algoritmos;

import grafos.Grafo;

public class ComponentesConectados {
	
	private boolean[] marcado;
	private int[] idComponenteConectado;
	private int[] numeroVerticesComponente;
	private int numeroComponentesConectados;
	
	
	/**
	 * Cria uma nova instancia de Componentes conectados
	 * @param grafo
	 */
	public ComponentesConectados(Grafo grafo) {
		
		marcado = new boolean[grafo.getNumeroDeVertices()];
		idComponenteConectado = new int[grafo.getNumeroDeVertices()];
		numeroVerticesComponente = new int[grafo.getNumeroDeVertices()];
		
		for (int u = 0; u < grafo.getNumeroDeVertices(); u++) {
			
			if (!marcado[u]) {
				BuscaProfundidade(grafo, u);
				numeroComponentesConectados++;
			}
			
		} // Fim for int i = 0
		
	} // Fim do construtor
	
	
	/**
	 * Executa o algoritmo de Busca em Profundidade
	 * @param grafo grafo O grafo onde será realizada a pesquisa
	 * @param u noFonte O no fonte onde se inicia a busca
	 */
	private void BuscaProfundidade(Grafo grafo, int u) { 
		
		
		marcado[u] = true;
		idComponenteConectado[u] = numeroComponentesConectados;
		numeroVerticesComponente[numeroComponentesConectados]++;
		
		for (int v : grafo.AdjacenciasInt(u)) {
			
			if(!marcado[v]) 
				BuscaProfundidade(grafo, v);
			
		} // Fim de foreach
	
	} // Fim do métdo BuscaProfundidade

	
	/**
	 * ID do componente conectado que possui u
	 * @param u
	 * @return
	 */
	public int IDComponenteConectado(int u) {
		
		return idComponenteConectado[u];
		
	} // Fim do método IDComponenteConectado
	
	
	
	/**
	 * Obtém o tamanho do componente conectado
	 * @param u
	 * @return
	 */
	public int TamanhoComponenteConectado(int u) {
		
		return numeroVerticesComponente[idComponenteConectado[u]];
		
	} // Fim do método TamanhoComponenteConectado
	
	
	/**
	 * Obtém o Numero de Componentes Conectados
	 * @return O Número de Componentes Conectados
	 */
	public int NumeroComponentesConectados() {
		
		return numeroComponentesConectados;
		
	} // Fim do método NumeroComponentesConectados
	
	
	/**
	 * Os vértices u e v estão no mesmo componente conectado? 
	 * @param u
	 * @param v
	 * @return
	 */
	public boolean EstaoConectados(int u, int v) {
		
		return IDComponenteConectado(u) == IDComponenteConectado(v);
		
	} // Fim do método EstaoConectados
	
	
	
} // Fim da classe Componentes Conectados
