package br.com.udemy.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
	
	// Aqui foi criado um Enum para facilitar o tipo de escolha
	private enum TipoComando {
		ZERAR, SINAL,NUMERO, DIV, MULT, SUB, SOMA, IGUAL, VIRGULA;
	};
	
	
	public static final Memoria instancia = new Memoria();
	
	// Padrão de projeto Observer aqui esta a lista dos observadores, nesse projeto o observador da memora será a classe Display do pacote visão
	private final List<MemoriaObservador> observadores = new ArrayList<>();

	private TipoComando ultimaOperacao = null; // observa o ultimo tipo de comando escolhido 
	private boolean substituir = false; // analisa se o valor do display deve ser substituido 
	private String textoAtual = ""; // refere-se ao valor atual das operacoes
	private String textoBuffer = ""; // guarda valores a serem operados

	private Memoria() {

	}

	// Utilizado Padrao de projeto Singleton
	public static Memoria getInstancia() {
		return instancia;
	}

	//Padrao de projeto aqui são adicionados os observadores
	public void adicionarObservador(MemoriaObservador observador) {
		observadores.add(observador);
	}

	//Devolve o Texto atual se for vazio é iniciado como zero
	public String getTextoAtual() {
		return textoAtual.isEmpty() ? "0" : textoAtual;
	}

	public void processarComando(String texto) {
	    TipoComando tipoComando = detectarTipoComando(texto);

	    if (tipoComando == null) {
	        return;
	    } else if (tipoComando == TipoComando.IGUAL) {
	        if (ultimaOperacao == TipoComando.DIV && textoAtual.equals("0")) {
	            // Divisão por zero, tratamento do erro
	            textoAtual = "E";
	        } else {
	            textoAtual = obterResultadoOperacao();
	            textoBuffer = "";
	            substituir = false;
	            ultimaOperacao = null;
	        }
	    } else if (tipoComando == TipoComando.ZERAR) {
	        textoAtual = "";
	        textoBuffer = "";
	        substituir = false;
	        ultimaOperacao = null;
	    } else if (tipoComando == TipoComando.SINAL && textoAtual.contains("-")) {
	        textoAtual = textoAtual.substring(1);
	    } else if (tipoComando == TipoComando.SINAL && !textoAtual.contains("-")) {
	        textoAtual = "-" + textoAtual;
	    } else if (tipoComando == TipoComando.NUMERO || tipoComando == TipoComando.VIRGULA) {
	        textoAtual = substituir ? texto : textoAtual + texto;
	        substituir = false;
	    } else {
	    	// Se o comando for alguma operação matematica será necessario guardar no Buffer o valor a ser operado tira-lo do display para que um novo valor seja digitado e assim ser feita a operação
	        substituir = true;
	        if (ultimaOperacao == TipoComando.DIV && textoAtual.equals("0")) {
	            // Divisão por zero, tratamento do erro
	            textoAtual = "E";
	        } else {
	        	// Trata uma possivel repetição de esolha de sinais sem operadores
	            try {
					textoAtual = obterResultadoOperacao();
				} catch (RuntimeException e) {
					 textoAtual = "E";
				}
	            
	            textoBuffer = textoAtual;
	            ultimaOperacao = tipoComando;
	        }
	    }

	    observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
	}


	
	private String obterResultadoOperacao() {
		if(ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL) {
			return textoAtual;
		}
		
		double numeroBuffer = Double.parseDouble(textoBuffer.replace(",", "."));
		double numeroAtual = Double.parseDouble(textoAtual.replace(",", "."));
		double resultado =0;
		
		if(ultimaOperacao  ==TipoComando.SOMA) {
			resultado = numeroBuffer + numeroAtual;
		} else if(ultimaOperacao  ==TipoComando.SUB) {
			resultado = numeroBuffer - numeroAtual;
		} else if(ultimaOperacao  ==TipoComando.MULT) {
			resultado = numeroBuffer * numeroAtual;
		} else if(ultimaOperacao  ==TipoComando.DIV) {
				resultado = numeroBuffer / numeroAtual;
		}
		
		String texto = Double.toString(resultado).replace(".", ",");
		boolean inteiro = texto.endsWith(",0");
		return inteiro ? texto.replace(",0", "") : texto;
	}

	private TipoComando detectarTipoComando(String texto) {
		if (textoAtual.isEmpty() && texto.equals("0")) {
			return null;
		}

		try {
			Integer.parseInt(texto);
			return TipoComando.NUMERO;
		} catch (NumberFormatException e) {

			if ("AC".equals(texto)) {
				return TipoComando.ZERAR;
			} else if ("/".equals(texto)) {
				return TipoComando.DIV;
			}else if ("*".equals(texto)) {
				return TipoComando.MULT;
			}else if ("+".equals(texto)) {
				return TipoComando.SOMA;
			}else if ("-".equals(texto)) {
				return TipoComando.SUB;
			}else if ("=".equals(texto)) {
				return TipoComando.IGUAL;
			}else if ("±".equals(texto)) {
					return TipoComando.SINAL;
			}else if (",".equals(texto) && !textoAtual.contains(",")) {
				return TipoComando.VIRGULA;
			}

		}
		return null;
	}

}
