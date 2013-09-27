import java.util.ArrayList;
import java.util.Arrays;

import grafos.Grafo;
import grafos.algoritmos.*;


/**
 * @author Diego Augusto de Faria Barros
 *
 */
public class SimilaridadeSemantica implements SimilaridadeSemanticaPalavras {
	
	private String palavra1;
	private String palavra2;
	
	private String[] palavrasOrigem;
	private String[] palavrasDestino;
	
	private Grafo grafoDePalavras;
	private ArrayList<String> rotulosVertices;
	BuscaEmLargura buscaEmLargura;
	

	private int numeroDeClusters;
	
	
	private ArrayList<Double> similaridades;
	
	
	/**
	 * @param palavra1
	 * @param palavra2
	 * @param grafoDePalavras
	 * @param numeroDeClusters
	 */
	public SimilaridadeSemantica(String palavra1, String palavra2,
			Grafo grafoDePalavras, int numeroDeClusters) {
		this.palavra1 = palavra1;
		this.palavra2 = palavra2;
		this.grafoDePalavras = grafoDePalavras;
		this.numeroDeClusters = numeroDeClusters;
	}
	
	

	/**
	 * @param palavrasOrigem
	 * @param palavrasDestino
	 * @param grafoDePalavras
	 * @param numeroDeClusters
	 */
	public SimilaridadeSemantica(String[] palavrasOrigem,
			String[] palavrasDestino, Grafo grafoDePalavras,
			int numeroDeClusters) {
		this.palavrasOrigem = palavrasOrigem;
		this.palavrasDestino = palavrasDestino;
		this.grafoDePalavras = grafoDePalavras;
		this.numeroDeClusters = numeroDeClusters;
		
		similaridades = new ArrayList<Double>(palavrasOrigem.length);
		
		String[] rotulos = grafoDePalavras.ObtemRotulosVertices();
		rotulosVertices = new ArrayList<String>(Arrays.asList(rotulos));
		
		CalculaSimilaridades();
		
	} // Fim do contrutor



	/**
	 * @return the palavra1
	 */
	public String getPalavra1() {
		return palavra1;
	}

	/**
	 * @param palavra1 the palavra1 to set
	 */
	public void setPalavra1(String palavra1) {
		this.palavra1 = palavra1;
	}

	/**
	 * @return the palavra2
	 */
	public String getPalavra2() {
		return palavra2;
	}

	/**
	 * @param palavra2 the palavra2 to set
	 */
	public void setPalavra2(String palavra2) {
		this.palavra2 = palavra2;
	}

	/**
	 * @return the numeroDeClusters
	 */
	public int getNumeroDeClusters() {
		return numeroDeClusters;
	}

	/**
	 * @param numeroDeClusters the numeroDeClusters to set
	 */
	public void setNumeroDeClusters(int numeroDeClusters) {
		this.numeroDeClusters = numeroDeClusters;
	}

	/**
	 * @return the grafoDePalavras
	 */
	public Grafo getGrafoDePalavras() {
		return grafoDePalavras;
	}
	
	/**
	 * @return the similaridades
	 */
	public Double[] getSimilaridades() {
		return similaridades.toArray(new Double[similaridades.size()]);
	}

	/**
	 * @param similaridades the similaridades to set
	 */
	public void setSimilaridades(ArrayList<Double> similaridades) {
		this.similaridades = similaridades;
	}

	/**
	 * Mede a similaridade entre os pares de palavras fornecidas como parametro
	 */
	private void CalculaSimilaridades() {
		
		System.out.println("\n.:: Menor Caminho - Busca em Largura ::.\n");
		
		for (int i = 0; i < palavrasOrigem.length; i++) {
			
			double similaridade = Sim(palavrasOrigem[i], palavrasDestino[i]);
			
			if (Double.isInfinite(similaridade))
				similaridades.add(0.0);
			else
				similaridades.add(similaridade);
	
		} // Fim de for int i = 0
		
	} // Fim do método CalculaSimilaridades

	
	
	/**
	 * Obtém valores únicos dos vértices para serem utilizados como fontes
	 * @param vertices Vetor de vértices
	 * @return Um vetor contendo os valores únicos para os vértices
	 */
	private Iterable<Integer> ObtemVerticesFonte(Integer[] vertices) {
		
		ArrayList<Integer> verticesFonte = new ArrayList<Integer>();
		
		for (int i = 0; i < vertices.length; i++) {
			
			if (!verticesFonte.contains(vertices[i]))
				verticesFonte.add(vertices[i]);
		}
		
		return verticesFonte;
		
	} // Fim do Método ObtemVerticesFonte
	
	
	
	/**
	 * Obtém o respectivo índice do rótulo do vértice no grafo
	 * @param rotulo Um string representando o rótulo do grafo
	 * @return O respectivo índice do rótulo do vértice no grafo caso contrário -1
	 */
	private int IndiceDoRotulo(String rotulo) {
		
		int indice = -1;
		
		for (int i = 0; i < rotulosVertices.size(); i++)
			if (rotulosVertices.get(i).equalsIgnoreCase(rotulo))
				indice = i;
		
		return indice;
		
	} // Fim do método IndiceDoRotulo
	
	

	/* (non-Javadoc)
	 * @see SimilaridadeSemanticaPalavras#Sim(java.lang.String, java.lang.String)
	 */
	@Override
	public double Sim(String w1, String w2) {
		return (1 / Len(w1, w2));
	}

	/* (non-Javadoc)
	 * @see SimilaridadeSemanticaPalavras#Len(java.lang.String, java.lang.String)
	 */
	@Override
	public double Len(String w1, String w2) {
		
		int verticeOrigem = IndiceDoRotulo(w1);
		int verticeDestino = IndiceDoRotulo(w2);
		int tamanhoMenorCaminho = 0;
		
		buscaEmLargura = new BuscaEmLargura(grafoDePalavras, verticeOrigem);
		
		if (buscaEmLargura.ExisteCaminhoPara(verticeDestino)) {
			
			
			tamanhoMenorCaminho = buscaEmLargura.DistanciaPara(verticeDestino);
			System.out.print(String.format("[%d] %s PARA [%d] %s (%d):  ", verticeOrigem, w1, verticeDestino, w2, tamanhoMenorCaminho));
			
			
			for (int  v : buscaEmLargura.CaminhoPara(verticeDestino)) {
				if (v == verticeOrigem)
					System.out.print(v);
				else
					System.out.print(v + " - ");
			}
			
			System.out.println();
			
		} else {
				System.out.println(String.format("\n[%d] %s Para [%d] %s (-):  Não conectado\n", verticeOrigem, w1, verticeDestino, w2));
			}
		
		return tamanhoMenorCaminho;
		
	} // Fim do método Len

} // Fim da Classe SimilaridadeSemantica