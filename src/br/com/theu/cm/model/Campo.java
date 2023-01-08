package br.com.theu.cm.model;

import br.com.theu.cm.excection.ExplosionException;

import java.util.ArrayList;
import java.util.List;

public class Campo {

    Private final int linha;

    Private final int coluna;

    Private boolean aberto;
    Private boolean marcado;
    Private boolean minado;

    List<Campo> redor = new ArrayList<>();

    Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    boolean adicionarRedor (Campo vizinho) {
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente != colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int deltaGeral = deltaColuna + deltaLinha;

        if (deltaGeral == 1 && !diagonal) {
            redor.add(vizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            redor.add(vizinho);
            return true;
        } else
            return false;
    }

    void Marcacao() {
        if(!aberto) {
            marcado = !marcado;
        }
    }

    boolean abrir() {

        if (!aberto && !marcado) {
            aberto = true;

            if (minado) {
                throw  new ExplosionException();
            }
            if (vizinhancaSegura()) {
                redor.forEach(v -> v.abrir());
            }

            return true;
        } else  {
            return false;
        }
    }

    boolean vizinhancaSegura () {
        return  redor.stream().noneMatch(v -> v.minado);
    }


}
