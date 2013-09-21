
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


/**
 * @author Diego Augusto de Faria Barros
 * Classe Principal
 */
public class SemanticGraph {
	
	static HashMap<String, String> dicionario;

	static File arquivoDicionario;
	static File arquivoParesPalavras;
	
	static File arquivoSaida;
	
	
	static void Inicializa(String[] parametros) {
		
		dicionario = new HashMap<String, String>();
		
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
		System.out.println("");

		
	} // Fim do método Carrega Dicionário
	
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
