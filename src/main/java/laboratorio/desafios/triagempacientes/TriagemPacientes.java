package laboratorio.desafios.triagempacientes;

import java.util.ArrayList;
import java.util.List;

import estruturas.lineares.dinamicas.fila.IFila;
import estruturas.lineares.dinamicas.fila.FilaComListaDuplamenteEncadeada;
import estruturas.lineares.dinamicas.pilha.IPilha;
import estruturas.lineares.dinamicas.pilha.PilhaComListaSimplesmenteEncadeada;

public class TriagemPacientes implements ITriagem {

    private final IFila<IPaciente> listaEspera = new FilaComListaDuplamenteEncadeada<>();

    private final IPilha<IPaciente> listaPrioridade = new PilhaComListaSimplesmenteEncadeada<>();

    @Override
    public void admitirPaciente(IPaciente paciente) {
        listaEspera.enfileirar(paciente);
    }

    @Override
    public void processarTriagemEmLote() {
        IFila<IPaciente> naoCriticos = new FilaComListaDuplamenteEncadeada<>();
        IPilha<IPaciente> criticosNaoOrdenados = new PilhaComListaSimplesmenteEncadeada<>();

        while (!listaEspera.estaVazia()) {
            IPaciente p = listaEspera.desenfileirar();
            if (p.obterGravidade().ehCritico()) {
                criticosNaoOrdenados.empilhar(p);
            } else {
                naoCriticos.enfileirar(p);
            }
        }

        if (criticosNaoOrdenados.estaVazia()) {
            while (!naoCriticos.estaVazia()) {
                listaEspera.enfileirar(naoCriticos.desenfileirar());
            }
            return;
        }

        List<IPaciente> listaCriticos = new ArrayList<>();
        while (!criticosNaoOrdenados.estaVazia()) {
            listaCriticos.add(criticosNaoOrdenados.desempilhar());
        }

        IPaciente[] arrayCriticos = listaCriticos.toArray(new IPaciente[0]);
        if (arrayCriticos.length > 0) {
            quickSort(arrayCriticos, 0, arrayCriticos.length - 1);
        }
        while (!listaPrioridade.estaVazia()) {
            listaPrioridade.desempilhar();
        }
        for (int i = arrayCriticos.length - 1; i >= 0; i--) {
            listaPrioridade.empilhar(arrayCriticos[i]);
        }

        while (!naoCriticos.estaVazia()) {
            listaEspera.enfileirar(naoCriticos.desenfileirar());
        }
    }

    @Override
    public void tratarProximoPacienteCritico() {
        if (listaPrioridade.estaVazia()) {
            System.out.println("Nenhum paciente crítico aguardando tratamento.");
            return;
        }
        IPaciente p = listaPrioridade.desempilhar();
        System.out.println("Iniciando tratamento: " + p.toString());
    }

    @Override
    public String obterStatusTriagem() {
        StringBuilder sb = new StringBuilder();

        IFila<IPaciente> tempFila = new FilaComListaDuplamenteEncadeada<>();
        sb.append("Lista de Espera: ");
        boolean primeiro = true;
        while (!listaEspera.estaVazia()) {
            IPaciente p = listaEspera.desenfileirar();
            if (!primeiro) sb.append(" -> ");
            sb.append(p.toString());
            primeiro = false;
            tempFila.enfileirar(p);
        }
        while (!tempFila.estaVazia()) {
            listaEspera.enfileirar(tempFila.desenfileirar());
        }
        sb.append(System.lineSeparator());

        IPilha<IPaciente> tempPilha = new PilhaComListaSimplesmenteEncadeada<>();
        sb.append("Lista de Prioridade: ");
        primeiro = true;
        while (!listaPrioridade.estaVazia()) {
            IPaciente p = listaPrioridade.desempilhar();
            if (!primeiro) sb.append(" -> ");
            sb.append(p.toString());
            primeiro = false;
            tempPilha.empilhar(p);
        }
        while (!tempPilha.estaVazia()) {
            listaPrioridade.empilhar(tempPilha.desempilhar());
        }
        sb.append(System.lineSeparator());

        return sb.toString();
    }

    private void quickSort(IPaciente[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(IPaciente[] arr, int low, int high) {
        IPaciente pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compararPacientes(arr[j], pivot) <= 0) {
                i++;
                IPaciente tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        IPaciente tmp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = tmp;
        return i + 1;
    }

    private int compararPacientes(IPaciente a, IPaciente b) {
        int nivelA = a.obterGravidade().obterNivel();
        int nivelB = b.obterGravidade().obterNivel();
        if (nivelA != nivelB) {
            return Integer.compare(nivelA, nivelB);
        }
        return Integer.compare(a.obterHorarioChegada(), b.obterHorarioChegada());
    }
}