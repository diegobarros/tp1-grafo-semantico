import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import grafos.Aresta;
import grafos.Grafo;
import grafos.algoritmos.*;


/**
 * @author Diego Augusto de Faria Barros
 *
 */
public class SimilaridadeSemantica implements ISimilaridadeSemantica {
	
	private String palavra1;
	private String palavra2;
	
	private String[] palavrasOrigem;
	private String[] palavrasDestino;
	
	private Grafo grafoDePalavras;
	private Grafo grafoDePalavrasAGM;
	private Grafo grafoDePalavrasPonderado;
	
	private ArrayList<String> rotulosVertices;
	BuscaEmLargura buscaEmLargura;
	
	private ArrayList<Double> similaridades;
	
	private ArrayList<String>[] grupoDePalavras;
	private int numeroDeClusters;
	
	/**
	 * @param palavra1
	 * @param palavra2
	 * @param grafoDePalavras
	 * @param numeroDeClusters
	 */
	@SuppressWarnings("unchecked")
	public SimilaridadeSemantica(String palavra1, String palavra2,
			Grafo grafoDePalavras, int numeroDeClusters) {
		
		this.palavra1 = palavra1;
		this.palavra2 = palavra2;
		this.grafoDePalavras = grafoDePalavras;
		this.numeroDeClusters = numeroDeClusters;
		
		String[] rotulos = grafoDePalavras.ObtemRotulosVertices();
		rotulosVertices = new ArrayList<String>(Arrays.asList(rotulos));
		
		grupoDePalavras = ((ArrayList<String>[]) new ArrayList[numeroDeClusters]);
		for (int i = 0; i < grupoDePalavras.length; i++)
			grupoDePalavras[i] = new ArrayList<String>();
		
	}

	
	/**
	 * @param palavrasOrigem
	 * @param palavrasDestino
	 * @param grafoDePalavras
	 * @param numeroDeClusters
	 */
	@SuppressWarnings("unchecked")
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
		
		grupoDePalavras = ((ArrayList<String>[]) new ArrayList[numeroDeClusters]);
		for (int i = 0; i < grupoDePalavras.length; i++)
			grupoDePalavras[i] = new ArrayList<String>();
		
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
	 * @return the grupoDePalavras
	 */
	public ArrayList<String>[] getGrupoDePalavras() {
		return grupoDePalavras;
	}


	/**
	 * Mede a similaridade entre os pares de palavras fornecidas como parametro
	 */
	public void CalculaSimilaridades() {
		
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
	 * A partir da árvore geradora mínima do grafo de palavras
	 * cria grupos de palavras
	 */
	public void CriaGrupoPalavrasSemelhantes() {
		
		ConstroiGrafoPonderado();
		CriaArvoreGeradoraMinima();
		RemoveArestas();
		GeraGruposDePalavras();

	} // Fim do método CriaGrupoPalavrasSemelhantes
	
	
	
	/**
	 * Remove as k - 1 Arestas de peso mínimo do Grafo
	 */
	private void RemoveArestas() {

		LinkedList<Aresta> arestasPesoMinimo = new LinkedList<Aresta>();
		ArrayList<Aresta> arestasAGM = (ArrayList<Aresta>) grafoDePalavrasAGM.Arestas();
		Aresta[] arestasGrafoAGM = new Aresta[arestasAGM.size()];
		
		for (int i = 0; i < arestasGrafoAGM.length; i++)
			arestasGrafoAGM[i] = arestasAGM.get(i);

		// Ordena Arestas
		QuickSortArestas.Ordena(arestasGrafoAGM);
		
		System.out.println("\n\n QUICK SORT lol\n");
		for (int i = 0; i < arestasGrafoAGM.length; i++) {
			System.out.println(arestasGrafoAGM[i]);
		}
		
		
		// Obtém k Arestas de peso mínimo da AGM
		for (int i = 0; i < numeroDeClusters - 1; i++)
			arestasPesoMinimo.add(arestasGrafoAGM[i]);
		
		System.out.println("\n\ngrafo AGM");
		System.out.println(grafoDePalavrasAGM.ListaAdjInt());

		
		do {
			
			Aresta arestaMinima = arestasPesoMinimo.removeFirst();
			
			// Remove Arestas
			for (int u = 0; u < grafoDePalavrasAGM.getNumeroDeVertices(); u++) {
				
				for (Aresta aresta : grafoDePalavrasAGM.Adjacencias(u)) {
					
					if (arestaMinima.getU() == aresta.getU() && 
							arestaMinima.getV() == aresta.getV() && 
							arestaMinima.getPeso() == aresta.getPeso()) {
					
						grafoDePalavrasAGM.RemoveAresta(aresta);
					} 

				} // Fim de foreach
				
			} // Fim de for int u = 0
			
		} while (arestasPesoMinimo.size() > 0);
		
		System.out.println("\n\nArestas Mínimas");
		System.out.println(grafoDePalavrasAGM.ListaAdjInt());
		
	} // Fim do método RemoveAresta
	
	
	
	/**
	 * Cria os grupos de palavras similares a partir do número de clusteres (k)
	 * passado como parâmetro
	 */
	private void GeraGruposDePalavras() {
		
		String[] rotulos = grafoDePalavrasAGM.ObtemRotulosVertices();
		
		ComponentesConectados componentesConectados = new ComponentesConectados(grafoDePalavrasAGM);
		
		int totalComponentes = componentesConectados.NumeroComponentesConectados();
		System.out.println("\nNúmero de Componentes: " + totalComponentes);
		
		@SuppressWarnings("unchecked")
		LinkedList<Integer>[] listaComponentes = (LinkedList<Integer>[])new LinkedList[totalComponentes];
		
		for (int i = 0; i < totalComponentes; i++)
			listaComponentes[i] = new LinkedList<Integer>();
		
		for (int u = 0; u < grafoDePalavrasAGM.getNumeroDeVertices(); u++)
			listaComponentes[componentesConectados.IDComponenteConectado(u)].add(u);
		 
		
		for (int u = 0; u < totalComponentes; u++) {
			
			for (int v : listaComponentes[u])
				grupoDePalavras[u].add(rotulos[v]);

		} // Fim for int u = 0
		
		OrdenaGrupoPalavras();
		
	} // Fim do método GeraGrupoDePalavras
	
	
	/**
	 * Ordenas as palavras pertencente a cada grupo em ordem crescente (alfabética)
	 */
	private void OrdenaGrupoPalavras() {
		
		for (int k = 0; k < grupoDePalavras.length; k++) {
			
			
			int numeroPalavras = grupoDePalavras[k].size();
			ArrayList<String> palavrasOrdenadas = new ArrayList<String>(numeroPalavras);
			
			String[] palavras = grupoDePalavras[k].toArray(new String[numeroPalavras]);
			QuickSort.Ordena(palavras);
			
			
			for (int i = 0; i < palavras.length; i++)
				palavrasOrdenadas.add(palavras[i]);
			
			grupoDePalavras[k] = palavrasOrdenadas;
			
		} // Fim for int k = 0
		
	} // Fim do método OrdenaGrupoPalavras
	

	/**
	 * Cria a árvore geradora mínima para o grafo de palavras
	 */
	private void CriaArvoreGeradoraMinima(){

		Prim prim = new Prim(grafoDePalavrasPonderado);
		
		System.out.println("\n.:: Árvore Geradora Mínima - PRIM ::.\n");
		grafoDePalavrasAGM = new Grafo(grafoDePalavrasPonderado.getNumeroDeVertices());
		
		for (Aresta aresta : prim.ArestasArvoreGeradoraMin()) {
			
			grafoDePalavrasAGM.AdicionaAresta(aresta);
			System.out.println(aresta);
		}
		
		System.out.println(grafoDePalavrasAGM.ListaAdjString());
		System.out.println(grafoDePalavrasAGM.ListaAdjInt());
		System.out.println(String.format("%.5f\n", prim.PesoArvoreGeradoraMin()));
		
	} // Fim do método CriaArvoreGeradoraMinima
	
	
	/**
	 * Dado os pesos do cálculo de similaridade semântica</br>
	 * constrói um grafo ponderado a partir deles
	 */
	private void ConstroiGrafoPonderado() {
		
		ArrayList<Aresta> arestas = new ArrayList<Aresta>();
		
		grafoDePalavrasPonderado = new Grafo(grafoDePalavras.getNumeroDeVertices());
		
		System.out.println("\n.:: Criando Arestas Ponderada ::.\n");
		
		for (int i = 0; i < palavrasOrigem.length; i++) {

			int verticeOrigem = IndiceDoRotulo(palavrasOrigem[i]);
			int verticeDestino = IndiceDoRotulo(palavrasDestino[i]);
			double peso = similaridades.get(i);
			Aresta aresta = new Aresta(verticeOrigem, verticeDestino, peso, palavrasOrigem[i], palavrasDestino[i]);
			arestas.add(aresta);
			grafoDePalavrasPonderado.AdicionaAresta(aresta);
			
			System.out.println(aresta.toString());
			
		} // Fim for int i = 0
		
		System.out.println("\n.:: Grafo ponderado ::.\n");
		System.out.println(grafoDePalavrasPonderado.ListaAdjString());
		
	} // Fim do método ConstroiGrafoPonderado
	
	
	
	/**
	 * Obtém valores únicos dos vértices para serem utilizados como fontes
	 * @param vertices Vetor de vértices
	 * @return Um vetor contendo os valores únicos para os vértices
	 */
	@SuppressWarnings("unused")
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
	 * @see ISimilaridadeSemantica#Sim(java.lang.String, java.lang.String)
	 */
	@Override
	public double Sim(String w1, String w2) {
		return (1 / Len(w1, w2));
	}
	
	

	/* (non-Javadoc)
	 * @see ISimilaridadeSemantica#Len(java.lang.String, java.lang.String)
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