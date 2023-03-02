package br.com.cod3r.cm.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class CampoTeste {
	private Campo campo;

	@BeforeEach
	void iniciarCampo() {
		campo = new Campo(3, 3);
	}

	@Test
	void testeVizinhoRealDistancia1Esquerda() {
		Campo vizinho = new Campo(3, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeVizinhoRealDistancia1Direita() {
		Campo vizinho = new Campo(3, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeVizinhoRealDistancia1Emcima() {
		Campo vizinho = new Campo(2, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeVizinhoRealDistancia1Embaixo() {
		Campo vizinho = new Campo(3, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeVizinhoRealDistanciaDiagonal() {
		Campo vizinho = new Campo(2, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeNaoVizinhoRealDistancia1() {
		Campo vizinho = new Campo(1, 1);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);

	}

	@Test
	void testePadraoAtributoMarcado() {

		assertFalse(campo.isMarcado());
	}

	@Test
	void testeAlterarMarcacao() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}

	@Test
	void testeAlterarMarcacaoduasChamadas() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}

	@Test
	void testeAbrirNaoMinadoNaoMarcado() {
		assertTrue(campo.abrir());
	}

	@Test
	void testeAbrirNaoMinadoMarcado() {
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
	}

	@Test
	void testeAbrirMinadoMarcado() {
		campo.alternarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
	}

	@Test
	void testeAbrirMinadoNaoMarcado() {
		campo.minar();

		assertThrows(ExplosaoException.class, () -> {
			campo.abrir();

		});
	}
	
	@Test
	void testeAbrirComVizinho() {
		
		Campo vizinho1 = new Campo(2, 2);
		Campo vizinhoDoVizinho1 = new Campo(1, 1);
		
		vizinho1.adicionarVizinho(vizinhoDoVizinho1);
		
		campo.adicionarVizinho(vizinho1);
		campo.abrir();
		
		assertTrue(vizinhoDoVizinho1.isAberto() && vizinho1.isAberto());
	}
	
	@Test
	void testeAbrirComVizinho2() {
		
		Campo vizinho11 = new Campo(1, 1);
		Campo vizinho12 = new Campo(1, 1);
		vizinho12.minar();
		
		
		Campo vizinhoDoVizinho22 = new Campo(2, 2);
		
		vizinhoDoVizinho22.adicionarVizinho(vizinho11);
		vizinhoDoVizinho22.adicionarVizinho(vizinho12);
		
		campo.adicionarVizinho(vizinhoDoVizinho22);
		campo.abrir();
		
		assertTrue(vizinhoDoVizinho22.isAberto() && vizinho11.isFechado());
	}
	

}
