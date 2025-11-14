package estruturas.naolineares.dinamicas.arvorebinaria;

public class AppArvoreBinaria {
    public static void main(String[] args) {

        // Criando Nós
        INoArvoreBinaria<String> raiz = new NoArvoreBinaria<>("A");
        INoArvoreBinaria<String> noB = new NoArvoreBinaria<>("B");
        INoArvoreBinaria<String> noC = new NoArvoreBinaria<>("C");
        INoArvoreBinaria<String> noD = new NoArvoreBinaria<>("D");
        INoArvoreBinaria<String> noE = new NoArvoreBinaria<>("E");
        INoArvoreBinaria<String> noF = new NoArvoreBinaria<>("F");
        INoArvoreBinaria<String> noG = new NoArvoreBinaria<>("G");
        INoArvoreBinaria<String> noH = new NoArvoreBinaria<>("H");

        raiz.definirNoEsquerdo(noB);
        raiz.definirNoDireito(noC);
        noB.definirNoEsquerdo(noE);
        noB.definirNoDireito(noD);
        noC.definirNoDireito(noF);
        noF.definirNoEsquerdo(noG);
        noF.definirNoDireito(noH);

        // Cria a Árvore
        IArvoreBinaria<String> arvore = new ArvoreBinariaSimples<>(raiz);

        // Verificando Propriedades
        System.out.println("=== PROPRIEDADES DA ÁRVORE ===");

        System.out.println("Tamanho (total de nós): " + arvore.tamanho());
        // Esperado: 8

        System.out.println("Altura da árvore: " + arvore.altura());
        // Esperado: 3
        // Caminho mais longo: A -> C -> F -> G (ou H)

        System.out.println("Maior grau da árvore: " + arvore.grau());
        // Esperado: 2 (porque alguns nós têm 2 filhos)

        System.out.println("Arestas da árvore: " + arvore.arestas());
        // Esperado: 7 (nós - 1)

        System.out.println("Nível do nó F: " + arvore.nivelNo("F"));
        // Esperado: 2

        System.out.println("Nível do nó H: " + arvore.nivelNo("H"));
        // Esperado: 3

        System.out.println("Nível do nó A: " + arvore.nivelNo("A"));
        // Esperado: 0 (raiz)

        System.out.println("Nível de nó inexistente (X): " + arvore.nivelNo("X"));
        // Esperado: -1

        // ------------------------------
        // ===== TESTANDO PERCURSOS =====
        // ------------------------------

        System.out.println("\n=== PERCURSOS ===");

        System.out.print("Pré-Ordem: ");
        arvore.percorrerPreOrdem(System.out::print);
        // Esperado: A B E D C F G H

        System.out.print("\nEm Ordem: ");
        arvore.percorrerEmOrdem(System.out::print);
        // Esperado: E B D A C G F H

        System.out.print("\nPós-Ordem: ");
        arvore.percorrerPosOrdem(System.out::print);
        // Esperado: E D B G H F C A

        System.out.print("\nPor Nível (BFS): ");
        arvore.percorrerEmOrdemNivel(System.out::print);
        // Esperado: A B C E D F G H

    }
}
