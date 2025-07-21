package estruturas.lineares.dinamicas.lista;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaSimplesmenteEncadeada<T> implements IListaSimplesmenteEncadeada<T>, Iterable<T> {

    // private int tamanho;

    private INoListaSimplesmenteEncadeada<T> primeiroNo;
    private INoListaSimplesmenteEncadeada<T> ultimoNo;
    private int quantidadeNos;

    public ListaSimplesmenteEncadeada() {
        this.limpar();
    }

    @Override
    public INoListaSimplesmenteEncadeada<T> obterPrimeiroNo() {
        return this.primeiroNo;
    }

    @Override
    public INoListaSimplesmenteEncadeada<T> obterUltimoNo() {
        return this.ultimoNo;
    }

    @Override
    public void limpar() {
        this.primeiroNo = null;
        this.ultimoNo = null;
        this.quantidadeNos = 0;
    }

    @Override
    public int tamanho() {
        return this.quantidadeNos;
    }

    @Override
    public boolean contem(T dado) {
        INoListaSimplesmenteEncadeada<T> noAtual = this.primeiroNo;
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
        if (this.quantidadeNos == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int posicao(T dado) {
        INoListaSimplesmenteEncadeada<T> noAtual = this.primeiroNo;
        int posicao = 0;
        while (noAtual != null) {
            if (noAtual.obterDado().equals(dado)) {
                return posicao;
            }
            noAtual = noAtual.obterProximoNo();
            posicao++;
        }
        return -1; // Retorna -1 se o dado não for encontrado na lista
    }

    @Override
    public void adicionar(T dado) {
        INoListaSimplesmenteEncadeada<T> novoNo = new NoListaSimplesmenteEncadeada<>(dado);
        if (this.primeiroNo == null) {
            this.primeiroNo = novoNo;
            this.ultimoNo = novoNo;
        } else {
            novoNo.definirProximoNo(this.primeiroNo);
            this.primeiroNo = novoNo;
        }
        quantidadeNos++;
    }

    @Override
    public void adicionar(T dado, int posicao) {
        INoListaSimplesmenteEncadeada<T> novoNo = new NoListaSimplesmenteEncadeada<T>(dado);
        INoListaSimplesmenteEncadeada<T> noAtual = this.primeiroNo;
        if (posicao < 0 || posicao > quantidadeNos) {
            throw new IndexOutOfBoundsException();
        }
        else if (posicao == 0) {
            this.adicionar(dado);
        } else if (posicao == quantidadeNos) {
            this.adicionarFim(dado);
        }
        else {
            int i = 1;
            while (i < quantidadeNos) {
                if (i == posicao) {
                    novoNo.definirProximoNo(noAtual.obterProximoNo());
                    noAtual.definirProximoNo(novoNo);
                    quantidadeNos++;
                    break;
                }
                noAtual = noAtual.obterProximoNo();
                i++;
            }
        }
    }

    @Override
    public void adicionarFim(T dado) {
        INoListaSimplesmenteEncadeada<T> novoNo = new NoListaSimplesmenteEncadeada<>(dado);
        if (this.primeiroNo == null) {
            this.primeiroNo = novoNo;
            this.ultimoNo = novoNo;
        } else {
            this.ultimoNo.definirProximoNo(novoNo);
            this.ultimoNo = novoNo;
        }
        quantidadeNos++;
    }

    @Override
    public void removerInicio() {
        if (!this.estaVazia()) {
            this.primeiroNo = this.primeiroNo.obterProximoNo();
            quantidadeNos--;
        }
    }

    @Override
    public void removerFim() {
        if (!this.estaVazia()) {
            INoListaSimplesmenteEncadeada<T> noAtual = this.primeiroNo;
            if (quantidadeNos == 1) {
                this.limpar();
            } else {
                while (noAtual.obterProximoNo() != this.ultimoNo) {
                    noAtual = noAtual.obterProximoNo();
                }
                noAtual.definirProximoNo(null);
                this.ultimoNo = noAtual;
                quantidadeNos--;
            }
        }
    }

    @Override
    public void remover(T dado) {
        if (contem(dado)) {
            if (primeiroNo == null) {
                return;
            }
            if (primeiroNo.obterDado().equals(dado)) {
                removerInicio();
                return;
            }

            INoListaSimplesmenteEncadeada<T> noAtual = this.primeiroNo.obterProximoNo();
            INoListaSimplesmenteEncadeada<T> noAnterior = this.primeiroNo;

            while (noAtual != null) {
                if (noAtual.obterDado().equals(dado)) {
                    if (noAtual == ultimoNo) {
                        removerFim();
                    } else {
                        noAnterior.definirProximoNo(noAtual.obterProximoNo());
                        quantidadeNos--;
                    }
                    return;
                }
                noAnterior = noAtual;
                noAtual = noAtual.obterProximoNo();
            }
        }
        System.out.println("O dado '" + dado + "' não foi encontrado na lista.");
    }

    @Override
    public String imprimir() {
        StringBuilder stringBuilder = new StringBuilder();
        INoListaSimplesmenteEncadeada<T> noAtual = this.primeiroNo;
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

            INoListaSimplesmenteEncadeada<T> noAtual = primeiroNo;

            @Override
            public boolean hasNext() {
                if (noAtual != null) {
                    return true;
                } else {
                    this.noAtual = primeiroNo;
                    return false;
                }
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
