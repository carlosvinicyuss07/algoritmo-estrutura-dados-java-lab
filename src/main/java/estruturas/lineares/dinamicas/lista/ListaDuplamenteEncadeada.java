package estruturas.lineares.dinamicas.lista;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaDuplamenteEncadeada<T> implements IListaDuplamenteEncadeada<T>,Iterable<T> {

    private INoListaDuplamenteEncadeada<T> noCabeca;
    private int quantidadeNos;

    public ListaDuplamenteEncadeada() {
        this.noCabeca = new NoListaDuplamenteEncadeada<T>(null);
        this.noCabeca.definirProximoNo(null);
        this.noCabeca.definirNoAnterior(null);
        this.quantidadeNos = 0;
    }

    @Override
    public INoListaDuplamenteEncadeada<T> obterPrimeiroNo() {
        return this.noCabeca.obterProximoNo();
    }

    @Override
    public INoListaDuplamenteEncadeada<T> obterUltimoNo() {
        return this.noCabeca.obterNoAnterior();
    }

    @Override
    public void limpar() {
        this.noCabeca.definirProximoNo(null);
        this.noCabeca.definirNoAnterior(null);
        this.quantidadeNos = 0;
    }

    @Override
    public int tamanho() {
        return this.quantidadeNos;
    }

    @Override
    public boolean contem(T dado) {
        INoListaDuplamenteEncadeada<T> noAtual = this.obterPrimeiroNo();
        while (noAtual != null) {
            if (noAtual.obterDado().equals(dado)) {
                return true;
            }
            noAtual = noAtual.obterProximoNo();
        }
        return false;
    }

    @Override
    public boolean estaVazia() {
        return this.quantidadeNos == 0;
    }


    @Override
    public void adicionar(T dado) {
        adicionarFim(dado);
    }

    @Override
    public void adicionar(T dado, int posicao) {
        if (posicao < 0 || posicao > tamanho()) {
            throw new IllegalArgumentException("Posição inválida.");
        }
        if (posicao == tamanho()) {
            adicionarFim(dado);
            return;
        }

        INoListaDuplamenteEncadeada<T> novoNo = new NoListaDuplamenteEncadeada<T>(dado);
        INoListaDuplamenteEncadeada<T> noAtual = this.noCabeca;

        for (int i = 0; i < posicao; i++) {
            noAtual = noAtual.obterProximoNo();
        }

        novoNo.definirProximoNo(noAtual.obterProximoNo());
        novoNo.definirNoAnterior(noAtual);
        noAtual.obterProximoNo().definirNoAnterior(novoNo);
        noAtual.definirProximoNo(novoNo);

        this.quantidadeNos++;

    }

    @Override
    public void adicionarFim(T dado) {
        INoListaDuplamenteEncadeada<T> novoNo = new NoListaDuplamenteEncadeada<T>(dado);

        if (estaVazia()) {
            this.noCabeca.definirProximoNo(novoNo);
            this.noCabeca.definirNoAnterior(novoNo);
            novoNo.definirNoAnterior(this.noCabeca);
            novoNo.definirProximoNo(null);
        } else {
            INoListaDuplamenteEncadeada<T> ultimoNo = this.obterUltimoNo();
            ultimoNo.definirProximoNo(novoNo);
            novoNo.definirNoAnterior(ultimoNo);
            this.noCabeca.definirNoAnterior(novoNo);
        }
        this.quantidadeNos++;
    }

    @Override
    public void removerInicio() {
        if (estaVazia()) {
            return;
        }

        INoListaDuplamenteEncadeada<T> noParaRemover = this.obterPrimeiroNo();
        this.noCabeca.definirProximoNo(noParaRemover.obterProximoNo());

        if (noParaRemover.obterProximoNo() != null) {
            noParaRemover.obterProximoNo().definirNoAnterior(this.noCabeca);
        } else {
            // o nó removido seria o único nesse caso, logo a lista fica vazia após sua remoção
            this.noCabeca.definirNoAnterior(null);
        }
        this.quantidadeNos--;

    }

    @Override
    public void removerFim() {
        if (estaVazia()) {
            return;
        }

        INoListaDuplamenteEncadeada<T> noParaRemover = this.obterUltimoNo();
        INoListaDuplamenteEncadeada<T> penultimoNo = noParaRemover.obterNoAnterior();

        penultimoNo.definirProximoNo(null);
        this.noCabeca.definirNoAnterior(penultimoNo);
        this.quantidadeNos--;
    }

    @Override
    public void remover(int posicao) {
        if (posicao < 0 || posicao >= tamanho()) {
            throw new IllegalArgumentException("Posição inválida.");
        }
        if (posicao == 0) {
            removerInicio();
            return;
        }
        if (posicao == tamanho() - 1) {
            removerFim();
            return;
        }

        INoListaDuplamenteEncadeada<T> noAtual = this.obterPrimeiroNo();
        for (int i = 0; i < posicao; i++) {
            noAtual = noAtual.obterProximoNo();
        }

        INoListaDuplamenteEncadeada<T> noAnterior = noAtual.obterNoAnterior();
        INoListaDuplamenteEncadeada<T> proximoNo = noAtual.obterProximoNo();

        noAnterior.definirProximoNo(proximoNo);
        proximoNo.definirNoAnterior(noAnterior);

        this.quantidadeNos--;
        
    }

    @Override
    public String imprimir() {
        StringBuilder stringBuilder = new StringBuilder();
        INoListaDuplamenteEncadeada<T> noAtual = this.obterPrimeiroNo();
        while (noAtual != null) {
            stringBuilder.append(noAtual.obterDado().toString());
            noAtual = noAtual.obterProximoNo();
            if (noAtual != null) {
                stringBuilder.append(" -> ");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            INoListaDuplamenteEncadeada<T> noAtual = obterPrimeiroNo();

            @Override
            public boolean hasNext() {
                return noAtual != null;
            }

            @Override
            public T next() throws NoSuchElementException {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else {
                    T dado = (T) noAtual.obterDado();
                    noAtual = noAtual.obterProximoNo();
                    return dado;
                }
            }
        };
    }

}