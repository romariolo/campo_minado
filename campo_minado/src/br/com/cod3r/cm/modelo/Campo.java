package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class Campo {

	private final int linha;
	private final int coluna;

	private boolean aberto = false;
	private boolean marcado = false;
	private boolean minado = false;

	// Auto relacionamento
	// Relacionamento 1 - n
	private List<Campo> vizinhos = new ArrayList<>();

	// Construtor com visibilidade pacote
	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;

		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;

		if (deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}

	void alternarMarcacao() {
		if (!aberto) {
			marcado = !marcado;

		}
	}

	boolean abrir() {
		if (!aberto && !marcado) {
			aberto = true;
			if (minado) {
				throw new ExplosaoException();
			}
			if (vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());// Stream consumer
			}
			return true;
		}
		return false;
	}
	
	public boolean isMarcado() {
		return marcado;
	}

	boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);// Stream Mat
	}
	
	void minar() {
		minado = true;
	}
	
	public boolean isMinado() {
		return minado;
	}
	
	public boolean isAberto() {
		return aberto;
	}
	public boolean isFechado() {
		return !isAberto();
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	
	
	 void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	long minasNaVizinha() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	
	void reiciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	public String toString() {
		if(marcado) {
			return "x";
		}else if(aberto && minado) {
			return "*";
		}else if(aberto && minasNaVizinha() > 0) {
			return Long.toString(minasNaVizinha());
		}else if(aberto) {
			return "  ";
		}else {
			return "?";
		}
	}
}
