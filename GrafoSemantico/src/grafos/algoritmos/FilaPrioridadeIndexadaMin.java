
package grafos.algoritmos;

import java.util.*;

/**
 * A classe <tt>FilaPrioridadeIndexadaMin</tt> representa uma fila de prioridade de chaves genéricas.
 * A mesma suporta as operações <em>inserir</em>, <em>excluirMinimo</em>,
 * <em>excluir</em> e <em>alterarChave</em>.
 * Essa classe também suporta os métodos para obter a chave mínima,
 * testar se a fila de prioridade está vazia e iterar através das chaves.
 * 
 * <p>
 * Essa implementação utiliza um heap binário junto com uma vetor para associar as chaves
 * com inteiros em uma dada série.</p>
 * 
 * <p>
 * As operações <em>inserir</em>, <em>excluirMinimo</em>,
 * <em>excluir</em>, <em>alterarChave</em>, <em>diminuirChave</em>, e 
 * <em>alterarChave</em> possuem um tempo de execução logarítmo.
 * </p>
 * 
 * <p>
 * As operações <em>estaVazia</em>, <em>Tamanho</em>,
 * <em>IndiceMin</em>, <em>ChaveMinima</em> e, <em>ChaveDe</em>, 
 * possuem um tempo de execução constante.
 * </p>
 * 
 * @author Diego Augusto de Faria Barros
 *
 */
public class FilaPrioridadeIndexadaMin<Key extends Comparable<Key>> implements Iterable<Integer> {
	
	private int numeroElementosMax;		// Número máximo de elementos na fila
	private int numeroElementos;		// Nùmero de elementos na fila
	
	private int[] heapBinario;			// Heap binário usando indexação baseada em 1
	private int[] heapBinarioInvertido;	// Inverso de heapBinario - heapBinarioInvertido[heapBinario[i]] = heapBinario[heapBinarioInvertido[i]] = i
	
	private Key[] chaves;				// chaves[i] = prioridade de i

	/**
	 * Inicializa uma fila de prioridade vazia com indices entre 0 e numeroElementosMax - 1.
	 * @param numeroElementosMax As chaves na fila de prioridade estão indexadas de 0 até numeroElementosMax - 1
     * @throws java.lang.IllegalArgumentException se numeroElementosMax < 0
	 */
	public FilaPrioridadeIndexadaMin(int numeroElementosMax) {
		
		if (numeroElementosMax < 0)
			throw new IllegalArgumentException();
		
		
		this.numeroElementosMax = numeroElementosMax;
		chaves = (Key[]) new Comparable[numeroElementosMax + 1];
		heapBinario = new int[numeroElementosMax + 1];
		heapBinarioInvertido = new int[numeroElementosMax + 1];
		
		
		for (int i = 0; i <= numeroElementosMax; i++)
			heapBinarioInvertido[i] = -1;
		

	} // Fim do construtor
	
	
	/**
	 * A fila de prioridade está vazia?
	 * @return true se a fila de prioridade estiver vazia e false caso contrário
	 */
	public boolean EstaVazia() {
		return numeroElementos == 0;
	} // Fim do método EstaVazia
	
	
	/**
	 * A fila de prioridade contém tal índice?
	 * @param indice Um índice inteiro
     * @throws java.lang.IndexOutOfBoundsException ao menos que (0 < indice < NMAX)
	 */
	public boolean Contem(int indice) {
		
		if (indice < 0 || indice >= numeroElementosMax)
			throw new IndexOutOfBoundsException();
		
		return heapBinarioInvertido[indice] != -1;
			
	} // Fim do método Contem
	
	
	/**
	 * Retorna o número de chaves na fila de prioridade.
	 * @return Retorna o número de chaves na fila de prioridade.
	 */
	public int Tamanho() {
		return numeroElementos;
		
	} // Fim do método Tamanho
	
	
	/**
	 * Associa uma chave ao índice
	 * @param indice Um índice inteiro
	 * @param chave A chave para associar ao índice
	 * @throws java.lang.IndexOutOfBoundsException ao menos que 0 < indice < NMAX
	 * @throws java.util.IllegalArgumentException se já existe um item associado ao índice
	 */
	public void Insere(int indice, Key chave) {
		
		if (indice < 0 || indice >= numeroElementosMax)
			throw new IndexOutOfBoundsException();
		
		if (Contem(indice))
			throw new IllegalArgumentException("A fila de prioridade já contém este índice");
		
		numeroElementos++;
		heapBinarioInvertido[indice] = numeroElementos;
		heapBinario[numeroElementos] = indice;
		chaves[indice] = chave;
		
		Navega(numeroElementos);
		
	} // Fim do método Insere
	
	
	
	/**
	 * Retorna um índice associado com uma chave mínima
	 * @return um índice associado com uma chave mínima
	 * @throws java.util.NoSuchElementException se a fila de prioridade estiver vazia
	 */
	public int IndiceMin() {
		
		if (numeroElementos == 0)
			throw new NoSuchElementException("Fila de prioridade Descarregada");
		
		return heapBinario[1];
		
	} // Fim do método MenorIndice
	
	
	/**
	 * Obtém a chave mínima
	 * @return A chave mínima
	 * @throws java.util.NoSuchElementException se a fila de prioridade estiver vazia
	 */
	public Key ChaveMin() {
		
		if (numeroElementos == 0)
			throw new NoSuchElementException("Fila de prioridade Descarregada");
		
		return chaves[heapBinario[1]];
		
	} // Fim do método ChaveMin
	
	/**
	 * Remove a chave mínima e retorna o índice associado a ela
	 * @return Um índice associado a chave mínima
	 * @throws java.util.NoSuchElementException se a fila de prioridade estiver vazia
	 */
	public int RemoveMin() {
		
		if (numeroElementos == 0)
			throw new NoSuchElementException("Fila de prioridade Descarregada");
		
		
		int minimo = heapBinario[1];
		
		Troca(1, numeroElementos--);
		Vertedouro(1);
		
		heapBinarioInvertido[minimo] = -1;				 
		chaves[heapBinario[numeroElementos + 1]] = null;	 
		heapBinario[numeroElementos + 1] = -1;				 
		
		return minimo;
		
	} // Fim do método RemoveMin
	
	
	/**
	 * Retorna a chave associada ao índice
	 * @param indice Um índice inteiro
	 * @return A chave associada ao índice
	 * @throws java.lang.IndexOutOfBoundsException ao menos que (0 < indice < NMAX)
	 * @throws java.util.NoSuchElementException nenhuma chave está associada ao índice
	 */
	public Key ChaveDe(int indice) {
		
		if (indice < 0 || indice >= numeroElementosMax)
			throw new IndexOutOfBoundsException();
		
		if(!Contem(indice))
			throw new NoSuchElementException("O índice não está na fila de prioridade!");
		else
			return chaves[indice];
		
	} // Fim do método ChaveDe
	
	
	
	/**
	 * Altera a chave associada ao índice ao valor especificado
	 * @param indice O índice da chave que será alterada
	 * @param chave Altera a chave associada  ao indice desta chave
	 * @throws java.lang.IndexOutOfBoundsException ao menos que (0 < indice < NMAX)
	 * @throws java.util.NoSuchElementException nenhuma chave está associada ao índice
	 */
	public void AlteraChave(int indice, Key chave) {
		
		if (indice < 0 || indice >= numeroElementosMax)
			throw new IndexOutOfBoundsException();
		
		if(!Contem(indice))
			throw new NoSuchElementException("O índice não está na fila de prioridade!");
		
		chaves[indice] = chave;
		
		Navega(heapBinarioInvertido[indice]);
		Vertedouro(heapBinarioInvertido[indice]);
		
	} // Fim do método AlteraChave
	
	
	/**
	 * Diminui a chave associada ao índice ao valor especificado
	 * @param indice O índice da chave que será diminuida
	 * @param chave Diminui a chave associada  ao indice desta chave
	 * @throws java.lang.IndexOutOfBoundsException ao menos que (0 < indice < NMAX)
	 * @throws java.util.NoSuchElementException nenhuma chave está associada ao índice
	 */
	public void DiminuiChave(int indice, Key chave) {
		
		if (indice < 0 || indice >= numeroElementosMax)
			throw new IndexOutOfBoundsException();
		
		if(!Contem(indice))
			throw new NoSuchElementException("O índice não está na fila de prioridade!");
		
		if (chaves[indice].compareTo(chave) <= 0)
			throw new IllegalArgumentException("Método chamado com um argumento que na diminui estritamente a chave!");
		
		chaves[indice] = chave;
		
		Navega(heapBinarioInvertido[indice]);
		
		
	} // Fim do método DiminuiChave
	
	/**
	 * Aumenta a chave associada ao índice ao valor especificado
	 * @param indice O índice da chave que será aumentada
	 * @param chave Aumenta a chave associada  ao indice desta chave
	 * @throws java.lang.IndexOutOfBoundsException ao menos que (0 < indice < NMAX)
	 * @throws java.util.NoSuchElementException nenhuma chave está associada ao índice
	 */
	public void AumentaChave(int indice, Key chave) {
		
		if (indice < 0 || indice >= numeroElementosMax)
			throw new IndexOutOfBoundsException();
		
		if(!Contem(indice))
			throw new NoSuchElementException("O índice não está na fila de prioridade!");
		
		if (chaves[indice].compareTo(chave) <= 0)
			throw new IllegalArgumentException("Método chamado com um argumento que na diminui estritamente a chave!");
		
		chaves[indice] = chave;
		Vertedouro(heapBinarioInvertido[indice]);
		
	} // Fim do método DiminuiChave
	
	
	/**
	 * Remove a chave associada ao índice
	 * @param indice O índice da chave que será removida
	 * @throws java.lang.IndexOutOfBoundsException ao menos que (0 < indice < NMAX)
	 * @throws java.util.NoSuchElementException nenhuma chave está associada ao índice
	 */
	public void Excluir(int indice) {
		
		if (indice < 0 || indice >= numeroElementosMax)
			throw new IndexOutOfBoundsException();
		
		if(!Contem(indice))
			throw new NoSuchElementException("O índice não está na fila de prioridade!");
		
		int indiceExcluido = heapBinarioInvertido[indice];
		
		Troca(indiceExcluido, numeroElementos--);
		Navega(indiceExcluido);
		Vertedouro(indiceExcluido);
		
		chaves[indice] = null;
		heapBinarioInvertido[indice] = -1;
		
	} // Fim do método Excluir
	
	
	// FUNÇÕES AUXILIARES
	
	/**
	 * Dado 2 valores retorna o maior entre eles
	 * @param i Valor 1
	 * @param j Valor 2
	 * @return O maior valor entre 2 números inteiros
	 */
	private boolean Maior(int i, int j) {
		
		return chaves[heapBinario[i]].compareTo(chaves[heapBinario[j]]) > 0;
		
	} // Fim do método Maior
	
	
	/**
	 * Dados 2 valores inteiros, realiza a troca entre eles
	 * @param i Valor 1
	 * @param j Valor 2
	 */
	private void Troca(int i, int j) {
		
		int troca = heapBinario[i];
		heapBinario[i] = heapBinario[j];
		heapBinario[j] = troca;
		
		heapBinarioInvertido[heapBinario[i]] = i;
		heapBinarioInvertido[heapBinario[j]] = j;
		
	} // Fim do método Troca
	
	
	// FUNÇÕES AUXILIARES DO HEAP
	
	private void Navega(int k) {
		
		while ((k > 1) && (Maior(k / 2, k))) {
			Troca(k, k / 2);
			k = k / 2;
		}
		
	} // Fim do método Navegar
	
	
	private void Vertedouro(int k) {
		
		while (2 * k <= numeroElementos) {
			
			int j = 2 * k;
			
			if (j < numeroElementos && Maior(j, j + 1))
				j++;
			
			if(!Maior(k, j))
				break;
			
			Troca(k, j);
			
			k = j;
		}
		
	} // Fim do método Vertedouro
	
	@Override
	public Iterator<Integer> iterator() {
		return new HeapIterator();
	}
	
	
	
	private class HeapIterator implements Iterator<Integer> {
        // create a new pq
        private FilaPrioridadeIndexadaMin<Key> copia;

        // add all elements to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            copia = new FilaPrioridadeIndexadaMin<Key>(heapBinario.length - 1);
            for (int i = 1; i <= numeroElementos; i++)
            	copia.Insere(heapBinario[i], chaves[heapBinario[i]]);
        }

        public boolean hasNext()  { return !copia.EstaVazia();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copia.RemoveMin();
        }
        
	} // Fim da classe HeapIterator

} // Fim da classe FilaPrioridadeIndexadaMin
