package br.com.calculadora.models;

@FunctionalInterface
public interface MemoriaObservador {
    void valorAlterado(String novoValor);
}
