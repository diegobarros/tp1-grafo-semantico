
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.pt.PortugueseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.apache.tika.language.*;

import grafos.*;

/**
 * @author Diego Augusto de Faria Barros</br>
 * Classe Principal
 */
public class SemanticGraph {
	
	static SimilaridadeSemantica similaridadeSemantica;
	
	// Dicionário e Mineração Textual
	static ArrayList<String> palavrasDicionario;
	static ArrayList<String> definicoesDicionario;
	static ArrayList<String> palavrasTokenizadasDicionario;
	static ArrayList<ArrayList<String>> definicoesTokenizadasDicionario;
	static LanguageIdentifier identficadorIdioma;
	
	// Grafos
	static ArrayList<Aresta> arestasGrafo;
	static Grafo grafo;
	
	
	// Palavras de Origem e Destino
	static LinkedList<String> palavrasOrigem;
	static LinkedList<String> palavrasDestino;
	
	static int numeroClusters;

	// Entrada/Saída dos Dados
	static File arquivoDicionario;
	static File arquivoParesPalavras;
	static File arquivoSimilaridades;
	static File arquivoGrupoPalavras;
	
	static Calendar calendario = Calendar.getInstance();
	static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
	
	static void Inicializa(String[] parametros) {

		palavrasDicionario = new ArrayList<String>();
		definicoesDicionario = new ArrayList<String>();
		
		arestasGrafo = new ArrayList<Aresta>();

		palavrasTokenizadasDicionario = new ArrayList<String>();
		definicoesTokenizadasDicionario = new ArrayList<ArrayList<String>>();
		
		palavrasOrigem = new LinkedList<String>();
		palavrasDestino = new LinkedList<String>();
		
		arquivoDicionario = new File(parametros[0]);
		arquivoParesPalavras = new File(parametros[1]);
		arquivoSimilaridades = new File(parametros[2]);
		arquivoGrupoPalavras = new File(parametros[3]);
		
		numeroClusters = Integer.parseInt(parametros[4]);
		
	} // Fim do método Inicializa
	
	
	/**
	 * Carrega o arquivo de dados dicionário com a lista de palavras e definições
	 */
	static void CarregaDicionario() {

		String dicionarioPalavras = LerArquivo(arquivoDicionario);
		String[] camposDicionario = dicionarioPalavras.split("\n");
		
		// Obtém as palavras do dicionário
		for (String termo : camposDicionario) {
			String[] definicao = termo.split(" ", 2);
			palavrasDicionario.add(definicao[0].trim());
			definicoesDicionario.add(definicao[1].trim());
		}
		
		
		try {
			
			String[] palavras = palavrasDicionario.toArray(new String[palavrasDicionario.size()]);
			String[] definicoes = definicoesDicionario.toArray(new String[definicoesDicionario.size()]);
			AnalisaDicionario(palavras, definicoes);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} // Fim do método Carrega Dicionário
	
	
	/**
	 * Carrega o arquivo com os pares de palavras que serão medidas as similaridades
	 */
	static void CarregaParesPalavras() {
		
		String arquivoDeParesPalavras = LerArquivo(arquivoParesPalavras);
		String[] paresPalavras = arquivoDeParesPalavras.split("\n");
		
		
		for (String par : paresPalavras) {
			String[] palavras = par.split("-");
			palavrasOrigem.add(palavras[0]);
			palavrasDestino.add(palavras[1]);
		}
		
		
	} // Fim do método CarregaParesPalavras
	
	
	/**
	 * Analisa cada definição do dicionário, eliminando 
	 * os termos desnecessários (Stop Words) </br>à busca
	 * @throws IOException 
	 */
	static void AnalisaDicionario(String[] palavras, String[] definicoes) throws IOException {
		
		
		identficadorIdioma = new LanguageIdentifier(definicoes[0]);
		String idiomaDefinicao = identficadorIdioma.getLanguage();
		System.out.println("    Idioma: " + idiomaDefinicao + "\n\n");
		
		System.out.println("[" + sdf.format(calendario.getTime()) + "] " + "Fazendo Pré-Processamento Textual: \n");
		
		for (int i = 0; i < palavras.length; i++) {

			ArrayList<String> listaTokensDefinicoes = new ArrayList<String>();

			String[] tokensPalavras = AnalisaTexto(palavras[i], idiomaDefinicao);
			String[] tokensDefinicoes = AnalisaTexto(definicoes[i], idiomaDefinicao);
			
			for(String definicao : tokensDefinicoes)
				listaTokensDefinicoes.add(definicao);
			
			palavrasTokenizadasDicionario.add(tokensPalavras[0]);
			definicoesTokenizadasDicionario.add(listaTokensDefinicoes);


		} // Fim for int i = 0
			
	} // Fim do método AnalisaDicionario
	
	
	/**
	 * Realiza o pré-processamento do texto do dicionário
	 * extraindo os tokens e fazendo o Stemming do texto.
	 * @param texto O texto que está sendo analisado
	 * @return Um vetor contendo todos os tokens do texto
	 * @throws IOException
	 */
	static String[] AnalisaTexto(String texto, String idioma) throws IOException {
		
		Analyzer analisador =  ObtemAnalisador(idioma);
		
		TokenStream tokenStream =  analisador.tokenStream("contents", new StringReader(texto));
		CharTermAttribute termo = tokenStream.addAttribute(CharTermAttribute.class);
		
		ArrayList<String> tokens = new ArrayList<String>();

		System.out.println("Analisando: " + texto);
		System.out.println("\t" + analisador.getClass().getName() + ":");
        System.out.print("\t\t");
         
        tokenStream.reset();
		
		while (tokenStream.incrementToken()) {
			System.out.print("[" + termo.toString() + "] ");
			tokens.add(termo.toString());
		}
		
		System.out.println("\n\n");
		tokenStream.end();
		tokenStream.close();
		analisador.close();
		
		return tokens.toArray(new String[tokens.size()]);
		
	} // Fim do método AnalisaTexto
	
	
	/**
	 * Obtém o analisador para o respectivo idioma do dicionário
	 * @param idioma O idioma do dicionário
	 * @return O analisador correspondente
	 */
	static Analyzer ObtemAnalisador(String idioma) {
		
		Analyzer analisador = null;
		int idIdioma = 0;
		
		if (idioma.equals("es"))
			idIdioma = 1;
		else
			idIdioma = 2;
		
		
		switch (idIdioma) {
		
			case 0:
				analisador = new EnglishAnalyzer(Version.LUCENE_44);
				break;
		
			case 1:
				analisador = new SpanishAnalyzer(Version.LUCENE_44);
				break;	
			
			case 2:
				analisador = new PortugueseAnalyzer(Version.LUCENE_44);
				break;

			default:
				analisador = new EnglishAnalyzer(Version.LUCENE_44);
				break;
		}
		
		return analisador;
		
	} // Fim do método ObtemAnalisador
	
	
	/**
	 * Cria as arestas do grafo
	 */
	static void CriaArestas() {
		
		System.out.println("[" + sdf.format(calendario.getTime()) + "] " + "Criando Arestas:\n\n");
		
		for (int u = 0; u < palavrasTokenizadasDicionario.size(); u++) {
			
			String palavraTokenizada = palavrasTokenizadasDicionario.get(u);
			
			for (int v = 0; v < definicoesTokenizadasDicionario.size(); v++) {
				
				ArrayList<String> definicaoTokenizada = definicoesTokenizadasDicionario.get(v);
				
				if(definicaoTokenizada.contains(palavraTokenizada) && (u != v)) {		
					
					arestasGrafo.add(new Aresta(u, v, 0.0, palavrasDicionario.get(u), palavrasDicionario.get(v)));
					System.out.println("[" + u + "] " + palavrasDicionario.get(u) + " ─→ " + "[" + v + "] " + palavrasDicionario.get(v));		
			
				} // Fim if
				
			} // Fim for int j = 0
			
			System.out.print("\n");
			
		} // Fim for int i = 0
				
	} // Fim do método CriaArestas
	
	

	/**
	 * Constrói o grafo de palavras
	 */
	static void ConstroiGrafo() {
		
		CriaArestas();
		
		int numeroVertices = palavrasDicionario.size();
		grafo = new Grafo(numeroVertices);
		
		
		for (Aresta aresta : arestasGrafo)
			grafo.AdicionaAresta(aresta);
		

		System.out.println("\n\n[" + sdf.format(calendario.getTime()) + "] " + "Grafo de Palavras - Lista de Adjacências:");
		System.out.println(grafo.ListaAdjString());

		
	} // Fim do método ConstroiGrafo
	
	
	/**
	 * Mede a similiridade semântica entre duas palavras no grafo
	 */
	static void MedeSimilaridadeSemantica() {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		String[] palavras1 = palavrasOrigem.toArray(new String[palavrasOrigem.size()]);
		String[] palavras2 = palavrasDestino.toArray(new String[palavrasDestino.size()]);
		
		similaridadeSemantica = new SimilaridadeSemantica(palavras1, palavras2, grafo, numeroClusters);
		similaridadeSemantica.CalculaSimilaridades();
		
		System.out.println("\n\n[" + sdf.format(calendario.getTime()) + "] " + "Similaridades Semânticas:\n");
		
		Double[] similaridades = similaridadeSemantica.getSimilaridades();
		
		for (Double similaridade : similaridades) {
			stringBuilder.append(String.format(Locale.ENGLISH, "%.1f \n", similaridade));
			System.out.println(similaridade);
		}
		
		SalvarArquivo(arquivoSimilaridades, stringBuilder.toString());
		
	} // Fim do método CalculaSimilaridadeSemantica
	
	
	static void ObtemGruposDePalavras() {
		
		similaridadeSemantica.CriaGrupoPalavrasSemelhantes();		
		ArrayList<String>[] grupoPalavras = similaridadeSemantica.getGrupoDePalavras();
		StringBuilder stringBuilder = new StringBuilder();
		
		
		for (int i = 0; i < grupoPalavras.length; i++) {
			
			
			for (String palavra : grupoPalavras[i])
				stringBuilder.append(palavra + ", ");
			

			stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(", "));
			stringBuilder.append("\n"); 
			
		} // Fm for int i
		
		
		String grupoDePalavras = stringBuilder.toString();
		System.out.println(grupoDePalavras);
		SalvarArquivo(arquivoGrupoPalavras, grupoDePalavras);
		
	} // Fim do método ObtemGruposDePalavras
	
	
	/**
	 * @param arquivo Arquivo de entrada
	 * @return Uma string com o conteúdo do arquivo
	 */
	static String LerArquivo(File arquivo) { 
		
		StringBuilder conteudo = new StringBuilder();
		
		try {
			
			BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
			String linha = leitor.readLine();
			
			while (linha != null) {
				
				if(!linha.equals("")) {
					conteudo.append(linha);
					conteudo.append("\n");
				}
				
				linha = leitor.readLine();
				
			} // Fim de while
			
			leitor.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return conteudo.toString();
		
	} // Fim do método LerArquivo
	
	
	/**
	 * Grava um novo arquivo no diretório do sistema
	 * @param arquivo Diretório onde o arquivo será gravado
	 * @param conteudo Conteúdo textual do arquivo
	 */
	static void SalvarArquivo(File arquivo, String conteudo) {
		
		try {
			BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo));
			escritor.write(conteudo);
			escritor.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} // Fim do método SalvarArquivo
	
	/**
	 * Grava um novo arquivo no diretório do sistema
	 * @param arquivo Diretório onde o arquivo será gravado
	 * @param conteudo Conteúdo textual do arquivo
	 * @param acrescentarConteudo true para que o conteúdo ser anexado ao arquivo
	 */
	static void SalvarArquivo(File arquivo, String conteudo, boolean acrescentarConteudo) {
		
		try {
			
			BufferedWriter escritor;
			
			if(acrescentarConteudo)
				escritor = new BufferedWriter(new FileWriter(arquivo,true));
			else
				escritor = new BufferedWriter(new FileWriter(arquivo));
			
			escritor.write(conteudo);
			escritor.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} // Fim do método SalvarArquivo
	
	static void ImprimeCabeçalhoPrograma() {
		
		System.out.println("\n");
		System.out.println("╔══════════════════════════════════════════════════════════════════╗");
		System.out.println("║            	Universidade Federal de Minas Gerais               ║");
		System.out.println("║   	Programa de Pós-Graduação em Ciência da Computação         ║");
		System.out.println("║                Mestrado em Ciência da Computação                 ║");
		System.out.println("║                                                                  ║");
		System.out.println("║         TP1 - Caminhos Mínimos e Arvore Geradora Mínima          ║");
		System.out.println("║                                                                  ║");
		System.out.println("║   Disciplina: Projeto e Análise de Algoritmos                    ║");
		System.out.println("║  Professores: Luiz Chaimowicz, Wagner Meira,                     ║");
		System.out.println("║                  Gisele Pappa, Jussara Almeida                   ║");
		System.out.println("║                                                                  ║");
		System.out.println("║         Nome: Diego Augusto de Faria Barros                      ║");
		System.out.println("║                                                                  ║");
		System.out.println("║               Belo Horizonte, 07 de Outubro de 2013              ║");
		System.out.println("╚══════════════════════════════════════════════════════════════════╝");
		System.out.println("\n\n");
		
	} // Fim do método ImprimeCabecalhoPrograma
	
	
	static void ImprimeInformacoesMaquina() {
		
		System.out.println("\n[" + sdf.format(calendario.getTime()) + "] " + "Análise Semântica Terminada!\n");
		
		String nomeSO = System.getProperty("os.name");
		System.out.println("\nSistema Operacional: " + nomeSO);
		
		String tipoSO = System.getProperty("os.arch");
		System.out.println("Arquitetura: " + tipoSO);
		
		String versaoSO = System.getProperty("os.version");
		System.out.println("Versão do S.O: " + versaoSO);
		
        
        System.out.println("Processadores Disponíveis (Núcleos): " +   
                Runtime.getRuntime().availableProcessors());  
          
		
	} // Fim do método ImprimeInformacoesMaquina
	  

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ImprimeCabeçalhoPrograma();
		
		long tempoInicio = System.currentTimeMillis();
		
		System.out.println("[" + sdf.format(calendario.getTime()) + "] " + "Inicializando Parâmetros . . . ");
		Inicializa(args);
		
		System.out.println("[" + sdf.format(calendario.getTime()) + "] " + "Carregando Dicionário de Palavras . . . ");
		
		System.out.println("\n\n   Arquivo: " + arquivoDicionario.getName());
		CarregaDicionario();
	
		System.out.println("[" + sdf.format(calendario.getTime()) + "] " + "Carregando Pares de Palavras . . . ");
		CarregaParesPalavras();
		
		System.out.println("[" + sdf.format(calendario.getTime()) + "] " + "Construindo Grafo de Palavras . . . ");
		ConstroiGrafo();
		
		MedeSimilaridadeSemantica();
		ObtemGruposDePalavras();
		
		long tempoFim = System.currentTimeMillis();
		long tempoExecucao = tempoFim - tempoInicio;
		
		ImprimeInformacoesMaquina();

		System.out.println("\nTempo Total de Execução: " + tempoExecucao + " ms");
		
		
	} // Fim do método Main

} // Fim da classe SemanticGraph


/*
 ************************************************************************
 *            	Universidade Federal de Minas Gerais	     	        *
 *         Programa de Pós-Graduação em Ciência da Computação           *
 *                Mestrado em Ciência da Computação                     *
 *                                                                      *								
 *    Trabalho: TP1: Caminhos Mínimos e Arvore Geradora Mínima 			*
 *  																	*                                                                      
 * 	Disciplina: Projeto e Análise de Algoritmos                         *
 * Professores: Luiz Chaimowicz, Wagner Meira, 							*
 * 				Gisele Pappa, Jussara Almeida                           *
 *    			  														*
 * 	      Nome: Diego Augusto de Faria Barros                           *
 *   																	*
 *                                                                      *
 *              Belo Horizonte, 07 de Outubro de 2013	  	            *
 ************************************************************************
*/
