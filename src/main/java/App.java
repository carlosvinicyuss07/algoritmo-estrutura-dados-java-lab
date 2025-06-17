import estruturas.lineares.estaticas.vetor.VetorTamanhoVariavel;

import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        System.out.println("====== INICIANDO TESTES DO VETOR DE TAMANHO VARIÁVEL (sem Class<T> nos construtores) ======\n");

        // --- Cenário 1: Operações Básicas e adicionarTodos sem redimensionamento ---
        System.out.println("--- Cenário 1: Adição em Lote sem Redimensionamento ---");

        VetorTamanhoVariavel<String> vetorNomes = new VetorTamanhoVariavel<>(5); // Capacidade inicial 5
        System.out.println("Vetor de Nomes (Capacidade 5): " + vetorNomes.imprimir() + " (Tamanho: " + vetorNomes.tamanho() + ")");

        vetorNomes.adicionar("Alice"); // Tamanho: 1
        vetorNomes.adicionar("Bob");   // Tamanho: 2
        System.out.println("Após adicionar Alice e Bob: " + vetorNomes.imprimir() + " (Tamanho: " + vetorNomes.tamanho() + ")");

        String[] novosNomes1 = {"Carol", "David"};
        System.out.println("Adicionando em lote: " + Arrays.toString(novosNomes1));
        vetorNomes.adicionarTodos(novosNomes1); // Tamanho: 4
        System.out.println("Vetor após adicionarTodos: ");
        System.out.println(vetorNomes.imprimir());

        System.out.println("\n====== FIM DOS TESTES ======");
    }
}