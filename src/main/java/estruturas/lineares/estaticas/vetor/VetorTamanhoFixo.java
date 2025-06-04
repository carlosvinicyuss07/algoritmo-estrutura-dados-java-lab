package estruturas.lineares.estaticas.vetor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class VetorTamanhoFixo<T> implements TDAVetor<T>,Iterable<T> {

    private int tamanho;
    private T[] elementos;

    @SuppressWarnings("unchecked")
    public VetorTamanhoFixo(int capacidade) {
        if (capacidade < 0) {
            throw new IllegalArgumentException("A capacidade do vetor não pode ser negativa");
        }
        this.tamanho = 0;
        this.elementos = (T[]) new Object[capacidade];
    }

    @Override
    public int tamanho() {
        return this.tamanho;
    }

    @Override
    public boolean estaVazio() {
        return this.tamanho == 0;
    }

    @Override
    public void verificarIndice(int indice, int minimo, int maximo) {
        if (indice < minimo || indice > maximo) {
            throw new IndexOutOfBoundsException(
                    String.format("Índice: %d, Tamanho: %d", indice, this.tamanho)
            );
        }
    }

    @Override
    public void verificarCapacidade() {
        if (this.tamanho == this.elementos.length) {
            throw new IllegalStateException("Capacidade máxima atingida.");
        }
    }

    @Override
    public int obterIndiceDo(T elemento) {
        for (int indice = 0; indice < tamanho; indice++) {
            if (Objects.equals(elemento, this.elementos[indice])) {
                return indice;
            }
        }
        return -1;
    }

    @Override
    public boolean contem(T elemento) {
        return obterIndiceDo(elemento) != -1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void limpar() {
        this.elementos = (T[]) new Object[this.tamanho];
        this.tamanho = 0;
    }

    @Override
    public T obter(int indice) throws IndexOutOfBoundsException {
        verificarIndice(indice, 0, this.tamanho - 1);
        return (T) this.elementos[indice];
    }

    @Override
    public void atualizar(int indice, T elemento) throws IndexOutOfBoundsException {
        verificarIndice(indice, 0, this.tamanho - 1);
        this.elementos[indice] = elemento;
    }

    @Override
    public void adicionar(T elemento) {
        verificarCapacidade();
        this.elementos[this.tamanho++] = elemento;
    }

    @Override
    public void adicionar(int indice, T elemento) throws IndexOutOfBoundsException {
        // verificações para garantir que é possível fazer a adição desejada
        verificarCapacidade();
        verificarIndice(indice, 0, this.tamanho);

        // mover todos os elementos
        System.arraycopy(this.elementos, indice, this.elementos, indice + 1, this.tamanho - indice);

        this.elementos[indice] = elemento;
        this.tamanho++;

    }

    @Override
    public void adicionarInicio(T elemento) {
        verificarCapacidade();
        adicionar(0, elemento);
    }

    @Override
    public T remover(int indice) throws IndexOutOfBoundsException {
        // verificação para garantir que é uma posição existente
        verificarIndice(indice, 0, this.tamanho - 1);

        // armazena o elemento para que possa ser retornado ao final
        T elementoRemovido = this.elementos[indice];

        /*
        move todos os elementos até a posição que deseja remover,
        fazendo com que a posição do elemento removido seja preenchida,
        efetivamente removendo-o logicamente
         */
        System.arraycopy(this.elementos, indice + 1, this.elementos, indice, this.tamanho - 1 - indice);
        this.elementos[this.tamanho - 1] = null;
        this.tamanho--;
        return elementoRemovido;
    }

    @Override
    public void removerElemento(T elemento) {
        int indice = obterIndiceDo(elemento);
        if (indice != -1) {
            remover(indice);
        }
    }

    @Override
    public void removerInicio() {
        if (estaVazio()) {
            throw new IllegalStateException("O vetor está vazio. Não é possível remover o primeiro elemento.");
        }
        remover(0);
    }

    @Override
    public void removerFim() {
        if (estaVazio()) {
            throw new IllegalStateException("O vetor está vazio. Não é possível remover o último elemento.");
        }
        this.elementos[this.tamanho - 1] = null;
        this.tamanho--;
    }

    @Override
    public String imprimir() {
        return Arrays.toString(Arrays.copyOf(this.elementos, this.tamanho));
    }

    @Override
    public Iterator<T> iterator() {
        return new VetorIterador();
    }

    private class VetorIterador implements Iterator<T> {
        private int ponteiroAtual = 0;

        @Override
        public boolean hasNext() {
            return ponteiroAtual < tamanho;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return elementos[ponteiroAtual++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remoção via iterador não suportada nesta implementação de vetor.");
        }
    }
}