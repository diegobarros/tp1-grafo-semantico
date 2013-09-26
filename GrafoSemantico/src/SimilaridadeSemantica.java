import java.util.ArrayList;

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
	 * Mede a similaridade entre os pares de palavras fornecidas como parametro
	 */
	private void CalculaSimilaridades() {
		
		for (int i = 0; i < palavrasOrigem.length; i++) {
			
			similaridades.add(Sim(palavrasOrigem[i], palavrasDestino[i]));
			
			
		} // Fim de for int i = 0
		
	} // Fim do método CalculaSimilaridades
	
	

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
		
		
		Integer[] verticesOrigem = grafoDePalavras.ObtemVerticesInteiros(palavrasOrigem);
		
		int fonte = 0;
		BuscaEmLargura buscaEmLargura = new BuscaEmLargura(grafoDePalavras, fonte);
		
		
		
		for (int u = 0; u <	 grafoDePalavras.getNumeroDeVertices(); u++) {
			
			if (buscaEmLargura.ExisteCaminhoPara(u)) {
				
				System.out.print(String.format("%d para %d (%d):  ", fonte, u, buscaEmLargura.DistanciaPara(u)));
				
				for (int  v : buscaEmLargura.CaminhoPara(u)) {
					if (v == fonte)
						System.out.print(v);
					else
						System.out.print("-" + v);
				}
				
				System.out.println();
				
			} else {
				System.out.println(String.format("%d para %d (-):  Não conectado\n", fonte, u));
			}
		}
		
		
		
		
		
		
		
		return 0;
		
	} // Fim do método Len

} // Fim da Classe SimilaridadeSemantica




//System.out.println(grafo.ImprimeListaAdj());
		//ArrayList<Integer> nosFontes = new ArrayList<Integer>();
		//for (int i = 0; i < grafo.getNumeroDeVertices(); i++)
			//nosFontes.add(i);
		
		
		/*
		BuscaEmLargura buscaEmLargura = new BuscaEmLargura(grafo, nosFontes);
		
		for (int u = 0; u <	 grafo.getNumeroDeVertices(); u++) {
			
			if (buscaEmLargura.ExisteCaminhoPara(u)) {
				
				System.out.print(String.format("%d para %d (%d):  ", nosFontes.get(u), u, buscaEmLargura.DistanciaPara(u)));
				
				for (int  v : buscaEmLargura.CaminhoPara(u)) {
					if (v == nosFontes.get(u))
						System.out.print(v);
					else
						System.out.print("-" + v);
				}
				
				System.out.println();
				
			} else {
				System.out.println(String.format("%d para %d (-):  Não conectado\n", nosFontes.get(u), u));
			}
		}*/
