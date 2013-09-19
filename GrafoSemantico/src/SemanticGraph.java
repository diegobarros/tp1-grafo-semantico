import java.io.*;


/**
 * @author Diego Augusto de Faria Barros
 * Classe Principal
 */
public class SemanticGraph {
	

	static File arquivoEntrada;
	static File arquivoSaida;
	
	
	static void Inicializa() {

	} // Fim do método Inicializa
	
	
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
				conteudo.append(linha);
				conteudo.append("\n");
			}
			
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
		// TODO Auto-generated method stub

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
