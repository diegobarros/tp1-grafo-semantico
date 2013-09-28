/**
 * 
 */
package grafos.algoritmos;

import java.util.LinkedList;

import grafos.Aresta;
import grafos.Grafo;

/**
 * @author diego
 *
 */
public class Prim {
	
	private Aresta[] arestaPara;
	private double[] distanciaPara;
	private boolean[] marcado;
	
	private FilaPrioridadeIndexadaMin<Double> filaPrioridade;

	/**
	 * 
	 */
	public Prim(Grafo grafo) {
		
		arestaPara = new Aresta[grafo.getNumeroDeVertices()];
		distanciaPara = new double[grafo.getNumeroDeVertices()];
		marcado = new boolean[grafo.getNumeroDeVertices()];
		
		filaPrioridade = new FilaPrioridadeIndexadaMin<Double>(grafo.getNumeroDeVertices());
		
		for (int u = 0; u < grafo.getNumeroDeVertices(); u++)
			distanciaPara[u] = Double.POSITIVE_INFINITY;
		
		// Executa a partir de cada vértice para encontrar a
		// Árvore geradora mínima
		for (int u = 0; u < grafo.getNumeroDeVertices(); u++) 
			if (!marcado[u])
				AlgoritmoPrim(grafo, u);
		
		
		
	} // Fim do construtor grafo
	
	
	private void AlgoritmoPrim(Grafo grafo, int fonte) {
		
		distanciaPara[fonte] = 0.0;
		
		filaPrioridade.Insere(fonte, distanciaPara[fonte]);
		
		while (!filaPrioridade.EstaVazia()) {
			
			int u = filaPrioridade.RemoveMin();
			ScaneiaVertice(grafo, u);
			
		}
		
		
	} // Fim do método AlgoritmoPrim
	
	
	/**
	 * Scaneia o vértice u
	 * @param grafo
	 * @param u
	 */
	private void ScaneiaVertice(Grafo grafo, int u) {
		
		marcado[u] = true;
		
		for (Aresta aresta : grafo.Adjacencias(u)) {
			int v = aresta.OutroVertice(u);
			
			if (marcado[v])
				continue;
			
			if (aresta.getPeso() < distanciaPara[v]) {
				
				distanciaPara[v] = aresta.getPeso();
				arestaPara[v] = aresta;
				
				if (filaPrioridade.Contem(v))
					filaPrioridade.AlteraChave(v, distanciaPara[v]);
				else
					filaPrioridade.Insere(v, distanciaPara[v]);
				
			} // Fim de if
			
		} // Fim de foreach
		
	} // Fim do método ScaneiaVertice
	
	
	/**
	 * Retorna um iterator de arestas da Árvore Geradora Mínima
	 * @return um iterator de arestas da Árvore Geradora Mínima
	 */
	public Iterable<Aresta> ArestasArvoreGeradoraMin() {
		
		LinkedList<Aresta> arvoreGeradora = new LinkedList<Aresta>();
		
		for (int u = 0; u < arestaPara.length; u++) {
			
			Aresta aresta = arestaPara[u];
			
			if (aresta != null)
				arvoreGeradora.add(aresta);
			
		} // Fim for int u = 0
		
		return arvoreGeradora;
		
	} // Fim do método Arestas
	
	
	/**
	 * O peso da árvore geradora mínima
	 * @return o peso da árvore geradora mínima
	 */
	public double PesoArvoreGeradoraMin() {
		
		double peso = 0.0;
		
		for (Aresta aresta : ArestasArvoreGeradoraMin())
			peso += aresta.getPeso();
		
		return peso;
		
	} // Fim do método PesoArvoreGeradoraMin

} // Fim da Classe
