package algoritmos.recursivos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fibonacci {

    public static int fibonacciRecursivo(int n) {
        if ((n == 0) || (n == 1)) {
            return n;
        } else {
            return fibonacciRecursivo(n - 1) + fibonacciRecursivo(n - 2);
        }
    }

    static int[] cache;
    public static int fibonacciRecursivoMemorizado(int n) {
        if (n <= 1) {
            return n;
        }
        if (cache[n] != -1) {
            return cache[n];
        }
        cache[n] = fibonacciRecursivoMemorizado(n - 1) + fibonacciRecursivoMemorizado(n - 2);
        return cache[n];
    }

    public static int fibonacciIterativo(int n) {
        int i, fibonacci = 0;
        int fibAnterior = 0;
        int fibPosterior = 1;
        if ((n == 0) || (n == 1)) {
            return n;
        } else {
            for (i = 2; i <= n; i++) {
                fibonacci = fibAnterior + fibPosterior;
                fibAnterior = fibPosterior;
                fibPosterior = fibonacci;
            }
            return fibonacci;
        }
    }

    public static void main(String[] args) {
        int n = 30;

        //Medição do tempo para versão recursiva
        long inicioRecursivo = System.nanoTime();
        int resultadoRecursivo = fibonacciRecursivo(n);
        long fimRecursivo = System.nanoTime();
        long tempoRecursivo = fimRecursivo - inicioRecursivo;

        //Medição do tempo para versão iterativa
        long inicioIterativo = System.nanoTime();
        int resultadoIterativo = fibonacciIterativo(n);
        long fimIterativo = System.nanoTime();
        long tempoIterativo = fimIterativo - inicioIterativo;

        cache = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            cache[i] = -1;
        }

        //Medição do tempo para versão recursiva memorizada
        long inicioRecursivoMemorizado = System.nanoTime();
        int resultadoRecursivoMemorizado = fibonacciRecursivoMemorizado(n);
        long fimRecursivoMemorizado = System.nanoTime();
        long tempoRecursivoMemorizado = fimRecursivoMemorizado - inicioRecursivoMemorizado;

        //Apresentação dos Resultados Medidos
        System.out.println("Resultado recursivo: " + resultadoRecursivo);
        System.out.println("Tempo recursivo (ns): " + tempoRecursivo);
        System.out.println("Resultado iterativo: " + resultadoIterativo);
        System.out.println("Tempo iterativo (ns): " + tempoIterativo);
        System.out.println("Resultado recursivo memorizado: " + resultadoRecursivoMemorizado);
        System.out.println("Tempo recursivo memorizado (ns): " + tempoRecursivoMemorizado);
        System.out.println("Diferença p/ o Recursivo Comum (ns): " + (tempoRecursivo - tempoRecursivoMemorizado));
        System.out.println("Diferença p/ o Iterativo (ns): " + (tempoIterativo - tempoRecursivoMemorizado));
    }

}
