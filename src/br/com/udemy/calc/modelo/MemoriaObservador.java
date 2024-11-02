package br.com.udemy.calc.modelo;

@FunctionalInterface
public interface MemoriaObservador {

	void valorAlterado ( String novoValor);
}
