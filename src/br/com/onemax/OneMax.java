/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onemax;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Amilton
 */
public class OneMax {

    static final int TAM_GENES = 24;
    static final int TAMP_POP = 48;
    static final int TAM_TORNEIO = 20;
    static final int GERACOES = 1000;
    static final double MUTACAO = 0.2;
    static final double PROB_CRUZ = 0.7;

    static List<List<Integer>> populacao = new ArrayList<>();

    /**
     * 
     */
    public static void inicializaPopulacao() {
        for (int i = 0; i < TAMP_POP; i++) {
            List<Integer> individuo = new ArrayList<>();
            for (int j = 0; j < TAM_GENES; j++) {
                int tmp = Math.abs(new Random().nextInt());
                int num = 0;
                if (((int) tmp) % 2 == 0) {
                    num = 1;
                }
                individuo.add(num);
            }
            populacao.add(individuo);
        }
    }

    /**
     * 
     */
    public static void imprimir() {
        for (int i = 0; i < TAMP_POP; i++) {
            System.out.println(populacao.get(i));
        }
    }

    /**
     * 
     * @param individuo
     * @return 
     */
    public static int calculaPontuacao(List<Integer> individuo) {
        int count = 0;
        for (int i = 0; i < individuo.size(); i++) {
            count = count + individuo.get(i);
        }
        return count;
    }

    /**
     * 
     * @param individuo 
     */
    public static void mutacao(List<Integer> individuo) {
        // rand num;
        int gene = (int) (Math.random() * TAM_GENES);
        if (individuo.get(gene) == 0) {
            individuo.set(gene, 1);
        } else {
            individuo.set(gene, 0);
        }
    }

    /**
     * 
     * @param pai1
     * @param pai2
     * @param filho 
     */
    public static void cruzamento(int pai1, int pai2, List<Integer> filho) {
        int ponto = (int) (Math.random() * TAM_GENES);
        for (int i = 0; i <= ponto; i++) {
            filho.add(populacao.get(pai1).get(i));
        }
        for (int i = ponto + 1; i < TAM_GENES; i++) {
            filho.add(populacao.get(pai2).get(i));
        }
    }

    /**
     *
     * @return
     */
    public static int obterMelhor() {
        int indice_melhor = 0;
        int score_melhor = calculaPontuacao(populacao.get(0));
        for (int i = 1; i < TAMP_POP; i++) {
            int score = calculaPontuacao(populacao.get(i));
            if (score > score_melhor) {
                indice_melhor = i;
                score_melhor = score;
            }
        }
        return indice_melhor;
    }

    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        inicializaPopulacao();
        imprimir();

        for (int i = 0; i < GERACOES; i++) {
            for (int j = 0; j < TAM_TORNEIO; j++) {
                double prob = Math.random();
                if (prob < PROB_CRUZ) {
                    int auxPai1 = (int) (Math.random() * TAMP_POP);
                    int auxPai2 = (int) (Math.random() * TAMP_POP);

                    while (auxPai1 == auxPai2) {
                        auxPai2 = (int) (Math.random() * TAMP_POP);
                    }

                    int indice_pai1 = auxPai1 - 1 > 0 ? auxPai1 - 1 : auxPai1;
                    int indice_pai2 = auxPai2 - 1 > 0 ? auxPai2 - 1 : auxPai2;
                    List<Integer> filho = new ArrayList<>();
                    cruzamento(indice_pai1, indice_pai2, filho);

                    prob = Math.random();

                    if (prob < MUTACAO) {
                        mutacao(filho);
                    }

                    int scorePai = calculaPontuacao(populacao.get(indice_pai1));
                    int scoreFilho = calculaPontuacao(filho);

                    if (scoreFilho > scorePai) {
                        populacao.set(indice_pai1, filho);
                    }
                }
            }
            System.out.println("Geração " + i);

            int indiceMelhor = obterMelhor();
            System.out.println("Melhor individuo : " + indiceMelhor);
            int scoreMelhor = calculaPontuacao(populacao.get(indiceMelhor));
            System.out.println("Escore do melhor : " + scoreMelhor);
            System.out.println("Genes do melhor : " + populacao.get(indiceMelhor));
            if (scoreMelhor == TAM_GENES) {
                break;
            }
        }
        System.out.println("Genes quando o AG termina");
        imprimir();
    }
}
