package estruturas.naolineares.dinamicas.arvorebinaria;

import java.util.function.Consumer;

public class ArvoreBinariaSimples<T> implements IArvoreBinaria<T> {

    private INoArvoreBinaria<T> noRaiz;

    public ArvoreBinariaSimples() {}

    public ArvoreBinariaSimples(INoArvoreBinaria<T> noRaiz) {
        this.noRaiz = noRaiz;
    }

    @Override
    public INoArvoreBinaria<T> obterNoRaiz() {
        return this.noRaiz;
    }

    @Override
    public void definirNoRaiz(INoArvoreBinaria<T> noRaiz) {
        this.noRaiz = noRaiz;
    }

    @Override
    public int tamanho() {
        return tamanho(this.noRaiz);
    }

    private int tamanho(INoArvoreBinaria<T> no) {
        if (no == null) return 0;
        return 1 + tamanho(no.obterNoEsquerdo()) + tamanho(no.obterNoDireito());
    }

    @Override
    public int altura() {
        return altura(this.noRaiz);
    }

    private int altura(INoArvoreBinaria<T> no) {
        if (no == null) return -1;
        int hEsquerdo = altura(no.obterNoEsquerdo());
        int hDireito = altura(no.obterNoDireito());
        return 1 + Math.max(hEsquerdo, hDireito);
    }

    @Override
    public int grau() {
        return grau(this.noRaiz);
    }

    private int grau(INoArvoreBinaria<T> no) {
        if (no == null) return 0;

        int grauAtual = 0;
        if (no.obterNoEsquerdo() != null) grauAtual++;
        if (no.obterNoDireito() != null) grauAtual++;

        return Math.max(grauAtual,
                Math.max(grau(no.obterNoEsquerdo()), grau(no.obterNoDireito())));
    }

    @Override
    public int arestas() {
        int totalNos = tamanho();
        if (totalNos == 0) return 0;
        return totalNos - 1;
    }

    public int nivelNo(T dado){
        return nivelNo(this.noRaiz, dado, 0);
    }

    private int nivelNo(INoArvoreBinaria<T> no, T dado, int nivel) {
        if (no == null) return -1;

        if (no.obterDado().equals(dado))
            return nivel;

        int nivelEsquerdo = nivelNo(no.obterNoEsquerdo(), dado, nivel + 1);
        if (nivelEsquerdo != -1) return nivelEsquerdo;

        return nivelNo(no.obterNoDireito(), dado, nivel + 1);
    }

    @Override
    public void percorrerPreOrdem(Consumer<T> acao) {
        percorrerPreOrdem(this.noRaiz,acao);
    }

    private void percorrerPreOrdem(INoArvoreBinaria<T> no, Consumer<T> acao) {
        if (no == null) return;
        acao.accept(no.obterDado());
        percorrerPreOrdem(no.obterNoEsquerdo(), acao);
        percorrerPreOrdem(no.obterNoDireito(), acao);
    }

    @Override
    public void percorrerEmOrdem(Consumer<T> acao) {
        percorrerEmOrdem(this.noRaiz, acao);
    }

    private void percorrerEmOrdem(INoArvoreBinaria<T> no, Consumer<T> acao) {
        if (no == null) return;
        percorrerEmOrdem(no.obterNoEsquerdo(), acao);
        acao.accept(no.obterDado());
        percorrerEmOrdem(no.obterNoDireito(), acao);
    }

    @Override
    public void percorrerPosOrdem(Consumer<T> acao) {
        percorrerPosOrdem(this.noRaiz, acao);
    }

    private void percorrerPosOrdem(INoArvoreBinaria<T> no, Consumer<T> acao) {
        if (no == null) return;
        percorrerPosOrdem(no.obterNoEsquerdo(), acao);
        percorrerPosOrdem(no.obterNoDireito(), acao);
        acao.accept(no.obterDado());
    }

    @Override
    public void percorrerEmOrdemNivel(Consumer<T> acao) {
        if (noRaiz == null) return;

        java.util.Queue<INoArvoreBinaria<T>> fila = new java.util.LinkedList<>();
        fila.add(noRaiz);

        while (!fila.isEmpty()) {
            INoArvoreBinaria<T> atual = fila.poll();
            acao.accept(atual.obterDado());

            if (atual.obterNoEsquerdo() != null)
                fila.add(atual.obterNoEsquerdo());

            if (atual.obterNoDireito() != null)
                fila.add(atual.obterNoDireito());
        }
    }
}
