/**
 * 
 */
package grafos.algoritmos;

import grafos.Aresta;

/**
 * @author Diego Augusto de Faria Barros
 *
 */
public class QuickSortArestas {

	public QuickSortArestas() {	}
	
	
	/**
	 * Ordena um vetor de arestas em ordem crescente,
	 * @param arestas O vetor de arestas que será ordenado
	 */
	public static void Ordena(Aresta[]arestas) {
		
		Ordena(arestas, 0, arestas.length - 1);
		
		
	} // Fim do método Ordena
	
	
	/**
	 * @param arestas
	 * @param esq
	 * @param dir
	 */
	private static void Ordena(Aresta[] arestas, int esq, int dir) {
		
		if (dir <= esq)
			return;
		else {
			int j = Particiona(arestas, esq, dir);
			Ordena(arestas, esq, j - 1);
			Ordena(arestas, j + 1, dir);
			assert EstaOrdenado(arestas, esq, dir);
		}

	} // Fim do método Ordena
	
	
	/**
	 * Particiona o subvetor aresta[esq...dir] retornando um índice j
	 * Satisfazendo aresta[esq...j-1] <= aresta[j] <= aresta[j + 1...dir]
	 * @param arestas
	 * @param esq
	 * @param dir
	 * @return O índice ja da partição
	 */
	private static int Particiona(Aresta[] arestas, int esq, int dir) {
		
		int i = esq;
		int j = dir + 1;
		
		Aresta pivo = arestas[esq];
		
		while (true) {
			
			// Encontra o da esquerda ítem para trocá-lo
			while (Menor(arestas[++i], pivo))
				if (i == dir)
					break;
			
			// Encontra o ítem da direita para trocá-lo
			while(Menor(pivo, arestas[--j]))
				if (j == esq)
					break;
			
			
			// Verifica se os ponteiros se cruzam
			if (i >= j)
				break;
			
			Troca(arestas, i, j);
			
		} // Fim de while
		
		Troca(arestas, esq, j); // Coloca aresta = arestas[j] na posição
		
		return j;
		
	} // Fim do método Particiona
	
	
    /**
     * Compara duas arestas pelo peso
     * @param aresta1
     * @param aresta2
     * @return
     */
    private static boolean Menor(Aresta aresta1, Aresta aresta2) {
    	
    	return (aresta1.compareTo(aresta2) < 0);
    	
    } // Fim do método Menor
	
    
	/**
	 * Troca aresta[i] por aresta[j]
	 * @param aresta
	 * @param i
	 * @param j
	 */
	private static void Troca(Aresta[] arestas, int i, int j) {
		Aresta memoriaTroca = arestas[i];
		arestas[i] = arestas[j];
		arestas[j] = memoriaTroca;
	}
	
	@SuppressWarnings("unused")
	private static boolean EstaOrdenado(Aresta[] arestas) {
		return EstaOrdenado(arestas, 0, arestas.length - 1);
	}
	
	private static boolean EstaOrdenado(Aresta[] arestas, int esq, int dir){
		for (int i = esq + 1; i <= dir; i++)
			if (Menor(arestas[i], arestas[i-1]))
				return false;

		return true;
	}

} // Fim da classe QuickSortArestas
