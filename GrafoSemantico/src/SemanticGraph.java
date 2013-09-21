
import java.io.*;
import java.util.*;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.util.Version;




/**
 * @author Diego Augusto de Faria Barros</br>
 * Classe Principal
 */
public class SemanticGraph {
	
	static Map<String,String> dicionario;
	
	static LinkedList<String> palavrasOrigem;
	static LinkedList<String> palavrasDestino;

	static File arquivoDicionario;
	static File arquivoParesPalavras;
	
	static File arquivoSaida;
	
	
	static void Inicializa(String[] parametros) {
		
		dicionario = new HashMap<String, String>();
		
		palavrasOrigem = new LinkedList<String>();
		palavrasDestino = new LinkedList<String>();
		
		arquivoDicionario = new File(parametros[0]);
		arquivoParesPalavras = new File(parametros[1]);
		
		
	} // Fim do método Inicializa
	
	
	/**
	 * Carrega o arquivo de dados dicionário com a lista de palavras e definições
	 */
	static void CarregaDicionario() {
		
		ArrayList<String> palavras = new ArrayList<String>();
		ArrayList<String> definicoes = new ArrayList<String>();
		
		String dicionarioPalavras = LerArquivo(arquivoDicionario);
		String[] camposDicionario = dicionarioPalavras.split("\n");
		
		// Obtém as palavras do dicionário
		for (String termo : camposDicionario) {
			String[] definicao = termo.split(" ", 2);
			palavras.add(definicao[0].trim());
			definicoes.add(definicao[1].trim());
		}
		
		// Remover StopWords
		System.out.println("NÃO ESQUEÇA, Remover STOP WORDS!!!");
		
		
		// Adiciona os termos no dicionário (hashmap)
		for (int i = 0; i < palavras.size(); i++)
			dicionario.put(palavras.get(i), definicoes.get(i));
		
		palavras.clear();
		definicoes.clear();
		
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
	 */
	static void AnalisaDicionario(String[] texto) {
		
		
		
		
		
	} // Fim do método AnalisaDicionario
	
	
	
	static String[] AnalisaTexto(String[] texto) {
		
		Analyzer analisador = new StandardAnalyzer(Version.LUCENE_44);
		
		System.out.println("Analisando: " + texto);
		
		
		
		
		
		return null;
	}
	
	
	/**
	 * Imprime todas as entradas do dicionario
	 */
	static void ImprimirDicionario() {
		
		for (Map.Entry<String, String> registro : dicionario.entrySet())
			System.out.println(registro.getKey().toString() + "\t\t" + registro.getValue().toString());
	} // Fim do método ImprimeDicionario
	
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
