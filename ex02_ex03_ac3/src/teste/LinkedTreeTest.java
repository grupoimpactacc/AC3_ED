package teste;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import position.Position;
import source.LinkedTree;
import source.TreeNode;
import source.TreePosition;
import tad_lista_de_nodos.NodePositionList;
import tad_lista_de_nodos.PositionList;

class LinkedTreeTest {

	@Test
	void test() {
		TreePosition<String> raiz;
		Position<Position<String>> p, s;
		PositionList<Position<String>> filhos;
		LinkedTree<String> T = criarArvoreT();
		System.out.println(T.parentheticRepresentation(T, T.root()));
		System.out.println(T.toStringPostOrder(T, T.root()));
		assertFalse(T.isEmpty());
		assertEquals(4, T.height1(T), "Altura da Árvore T");
		assertEquals(4, T.height2(T, T.root()), "Altura da Árvore T");
		assertEquals("[Eletronics R'Us, P&D, Vendas, Internacional, Canadá, América do Sul, "
		+ "Ultramar, África, Europa, Ásia, Austrália, Nacional, Compras, Manufatura, TV, CD, Tuner]",
		T.toString(), "Pré-ordem da Árvore T ");
		raiz = T.root();
		filhos = raiz.getChildren();
		p = filhos.first();
		assertEquals("P&D", p.element().element(), "P&D");
		assertTrue(T.isExternal(p.element()));
		assertEquals(raiz, T.parent(p.element()), "Deve ser a raiz");
		s = filhos.next(p);
		assertEquals("Vendas", s.element().element(), "Vendas");
		assertTrue(T.isInternal(s.element()));
		assertEquals(1, T.depth(T, s.element()), "");
		T.replace(p.element(), "Pesquisa e Desenvolvimento");
		assertEquals("[Eletronics R'Us, Pesquisa e Desenvolvimento, Vendas, Internacional, Canadá, América do Sul, "
		+ "Ultramar, África, Europa, Ásia, Austrália, Nacional, Compras, Manufatura, TV, CD, Tuner]",
		T.toString(), "Pré-ordem da Árvore T ");
		assertTrue(T.isRoot(raiz));
		T.swapElements(p.element(), s.element());
		assertEquals("[Eletronics R'Us, Vendas, Pesquisa e Desenvolvimento, Internacional, Canadá, América do Sul, "
		+ "Ultramar, África, Europa, Ásia, Austrália, Nacional, Compras, Manufatura, TV, CD, Tuner]",
		T.toString(), "Pré-ordem da Árvore T ");
	}
	
	private TreeNode<String> criarFilho(TreeNode<String> p, String n) {
		PositionList<Position<String>> filhos;
		TreeNode<String> aux;
		filhos = p.getChildren();
		aux = new TreeNode<String>();
		aux.setElement(n);
		aux.setParent(p);
		aux.setChildren(new NodePositionList<Position<String>>());
		filhos.addLast(aux);
		return aux;
	}
	
	public LinkedTree<String> criarArvoreT() {
		LinkedTree<String> T = new LinkedTree<String>();
		TreeNode<String> raiz, v, m, i, u;
		T.addRoot("Eletronics R'Us");
		raiz = (TreeNode<String>) T.root();
		raiz.setChildren(new NodePositionList<Position<String>>());
		criarFilho(raiz, "P&D");
		v = criarFilho(raiz, "Vendas");
		criarFilho(raiz, "Compras");
		m = criarFilho(raiz, "Manufatura");
		i = criarFilho(v, "Internacional");
		criarFilho(v, "Nacional");
		criarFilho(i, "Canadá");
		criarFilho(i, "América do Sul");
		u = criarFilho(i, "Ultramar");
		criarFilho(u, "África");
		criarFilho(u, "Europa");
		criarFilho(u, "Ásia");
		criarFilho(u, "Austrália");
		criarFilho(m, "TV");
		criarFilho(m, "CD");
		criarFilho(m, "Tuner");
		return T;
	}
}
