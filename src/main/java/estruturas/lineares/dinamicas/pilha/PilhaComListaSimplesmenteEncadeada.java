package estruturas.lineares.dinamicas.pilha;

import java.util.Iterator;
import java.util.Objects;

import estruturas.lineares.dinamicas.lista.IListaSimplesmenteEncadeada;
import estruturas.lineares.dinamicas.lista.ListaSimplesmenteEncadeada;

public class PilhaComListaSimplesmenteEncadeada<T> implements IPilha<T> {

    private final IListaSimplesmenteEncadeada<T> lista;

    public PilhaComListaSimplesmenteEncadeada() {
        this.lista = new ListaSimplesmenteEncadeada<>();
    }

    @Override
    public int tamanho() {
        return this.lista.tamanho();
    }

    @Override
    public boolean estaVazia() {
        return this.lista.estaVazia();
    }

    @Override
    public void limpar() {
        this.lista.limpar();
    }

    @Override
    public boolean contem(T dado) {
        return this.lista.contem(dado);
    }

    @Override
    public T obterTopo() {
        if (this.estaVazia()) {
            throw new IllegalStateException("Pilha vazia");
        }
        return this.lista.obterPrimeiroNo().obterDado();
    }

    @Override
    public void empilhar(T dado) {
        Objects.requireNonNull(dado, "O dado não pode ser nulo.");
        this.lista.adicionar(dado);
    }

    @Override
    public T desempilhar() {
        if (this.estaVazia()) {
            throw new IllegalStateException("A pilha está vazia.");
        }
        T dadoDoTopo = this.obterTopo();
        this.lista.removerInicio();
        return dadoDoTopo;
    }

    @Override
    public int distancia(T dado) {
        if (this.estaVazia() || dado == null) {
            return -1;
        }

        int distancia = 1;
        for (T elemento : this.lista) {
            if (Objects.equals(elemento, dado)) {
                return distancia;
            }
            distancia++;
        }
        return -1;
    }

    @Override
    public String imprimir() {
        if (this.estaVazia()) {
            return "[]";
        }
        return this.lista.imprimir();
    }

    @Override
    public Iterator<T> iterator() {
        return this.lista.iterator();
    }

}