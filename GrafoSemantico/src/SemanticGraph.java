
import java.io.*;
import java.util.*;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import grafos.*;

/**
 * @author Diego Augusto de Faria Barros</br>
 * Classe Principal
 */
public class SemanticGraph {
	
	// Dicionário e Mineração Textual
	static ArrayList<String> palavrasDicionario;
	static ArrayList<String> definicoesDicionario;
	static ArrayList<String> palavrasTokenizadasDicionario;
	static ArrayList<ArrayList<String>> definicoesTokenizadasDicionario;
	
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
	
	static File arquivoSaida;
	
	
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

		
		for (int i = 0; i < palavras.length; i++) {
			
			ArrayList<String> listaTokensDefinicoes = new ArrayList<String>();
			
			String[] tokensPalavras = AnalisaTexto(palavras[i]);
			String[] tokensDefinicoes = AnalisaTexto(definicoes[i]);
			
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
	static String[] AnalisaTexto(String texto) throws IOException {
		
		Analyzer analisador = new EnglishAnalyzer(Version.LUCENE_44);
		
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
	 * Cria as arestas do grafo
	 */
	static void CriaArestas() {
		
		System.out.println(".:: Criando Arestas ::.\n");
		
		for (int u = 0; u < palavrasTokenizadasDicionario.size(); u++) {
			
			String palavraTokenizada = palavrasTokenizadasDicionario.get(u);
			
			for (int v = 0; v < definicoesTokenizadasDicionario.size(); v++) {
				
				ArrayList<String> definicaoTokenizada = definicoesTokenizadasDicionario.get(v);
				
				if(definicaoTokenizada.contains(palavraTokenizada) && (u != v)) {		
					
					arestasGrafo.add(new Aresta(u, v, 0.0, palavrasDicionario.get(u), palavrasDicionario.get(v)));
					System.out.println("[" + u + "] " + palavrasDicionario.get(u) + " -> " + "[" + v + "] " + palavrasDicionario.get(v));		
			
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
		

		System.out.println("\n.:: Grafo - Lista de Adjacências ::.\n");
		System.out.println(grafo.ListaAdjString());

		
	} // Fim do método ConstroiGrafo
	
	
	/**
	 * Mede a similiridade semântica entre duas palavras no grafo
	 */
	static void MedeSimilaridadeSemantica() {
		
		String[] palavras1 = palavrasOrigem.toArray(new String[palavrasOrigem.size()]);
		String[] palavras2 = palavrasDestino.toArray(new String[palavrasDestino.size()]);
		
		SimilaridadeSemantica similaridadeSemantica = new SimilaridadeSemantica(palavras1, palavras2, grafo, numeroClusters);
		
	} // Fim do método CalculaSimilaridadeSemantica
	
	
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
	  

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Inicializa(args);
		
		CarregaDicionario();
		CarregaParesPalavras();
		
		ConstroiGrafo();
		MedeSimilaridadeSemantica();
		
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
 *              Belo Horizonte, 02 de Outubro de 2013	  	            *
 ************************************************************************
*/
