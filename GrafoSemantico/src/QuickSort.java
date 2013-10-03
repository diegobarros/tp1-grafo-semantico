/**
 * 
 */

/**
 * @author Diego Augusto de Faria Barros
 *
 */
public class QuickSort {

	public QuickSort() {	}
	
	
	/**
	 * Ordena um vetor de arestas em ordem crescente,
	 * @param arestas O vetor de arestas que será ordenado
	 */
	@SuppressWarnings("rawtypes")
	public static void Ordena(Comparable[] vetor) {
		
		Ordena(vetor, 0, vetor.length - 1);
		
		
	} // Fim do método Ordena
	
	
	/**
	 * @param arestas
	 * @param esq
	 * @param dir
	 */
	@SuppressWarnings("rawtypes")
	private static void Ordena(Comparable[] vetor, int esq, int dir) {
		
		if (dir <= esq)
			return;
		else {
			int j = Particiona(vetor, esq, dir);
			Ordena(vetor, esq, j - 1);
			Ordena(vetor, j + 1, dir);
			assert EstaOrdenado(vetor, esq, dir);
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
	@SuppressWarnings("rawtypes")
	private static int Particiona(Comparable[] vetor, int esq, int dir) {
		
		int i = esq;
		int j = dir + 1;
		
		Comparable pivo = vetor[esq];
		
		while (true) {
			
			// Encontra o da esquerda ítem para trocá-lo
			while (Menor(vetor[++i], pivo))
				if (i == dir)
					break;
			
			// Encontra o ítem da direita para trocá-lo
			while(Menor(pivo, vetor[--j]))
				if (j == esq)
					break;
			
			
			// Verifica se os ponteiros se cruzam
			if (i >= j)
				break;
			
			Troca(vetor, i, j);
			
		} // Fim de while
		
		Troca(vetor, esq, j); // Coloca aresta = arestas[j] na posição
		
		return j;
		
	} // Fim do método Particiona
	
	
    /**
     * Compara duas arestas pelo peso
     * @param aresta1
     * @param aresta2
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static boolean Menor(Comparable aresta1, Comparable aresta2) {
    	
    	return (aresta1.compareTo(aresta2) < 0);
    	
    } // Fim do método Menor
	
    
	/**
	 * Troca aresta[i] por aresta[j]
	 * @param aresta
	 * @param i
	 * @param j
	 */
	private static void Troca(Object[] vetor, int i, int j) {
		Object memoriaTroca = vetor[i];
		vetor[i] = vetor[j];
		vetor[j] = memoriaTroca;
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	private static boolean EstaOrdenado(Comparable[] vetor) {
		return EstaOrdenado(vetor, 0, vetor.length - 1);
	}
	
	private static boolean EstaOrdenado(@SuppressWarnings("rawtypes") Comparable[] vetor, int esq, int dir){
		for (int i = esq + 1; i <= dir; i++)
			if (Menor(vetor[i], vetor[i-1]))
				return false;

		return true;
	}

} // Fim da classe QuickSort
