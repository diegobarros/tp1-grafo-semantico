import grafos.Grafo;
import grafos.algoritmos.*;



/**
 * @author Diego Augusto de Faria Barros
 *
 */
public class SimilaridadeSemantica implements SimilaridadeSemanticaPalavras {
	
	private String palavra1;
	private String palavra2;
	
	private Grafo grafoDePalavras;

	private int numeroDeClusters;
	
	
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

	/* (non-Javadoc)
	 * @see SimilaridadeSemanticaPalavras#Sim(java.lang.String, java.lang.String)
	 */
	@Override
	public double Sim(String w1, String w2) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see SimilaridadeSemanticaPalavras#Len(java.lang.String, java.lang.String)
	 */
	@Override
	public double Len(String w1, String w2) {
		// TODO Auto-generated method stub
		return 0;
	}

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
