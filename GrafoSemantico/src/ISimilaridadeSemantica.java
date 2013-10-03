/**
 *  Interface similaridade semântica entre as palavras
 *  com medida baseada em aresta [Jiang and Conrath 1997]
 * @author Diego Augusto de Faria Barros
 */
public interface ISimilaridadeSemantica {
	
	/**
	 * Mede a similaridade semântica entre as palavras
	 * medida baseada em aresta [Jiang and Conrath 1997]
	 * @param w1 Primeiro termo (palavra)
	 * @param w2 Segundo termo (palavra)
	 * @return Similaridade Semântica
	 */
	public double Sim(String w1, String w2);
	
	
	/**
	 * Cálculo do tamanho do menor caminho entre w1 e w2
	 * @param w1 Primeiro termo (palavra)
	 * @param w2 Segundo termo (palavra)
	 * @return O tamanho do Menor caminho entre w1 e w2
	 */
	public double Len(String w1, String w2);

} // Fim da Interface SimilaridadeSemantica
