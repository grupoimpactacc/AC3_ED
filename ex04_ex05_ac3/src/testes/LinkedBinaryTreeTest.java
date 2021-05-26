package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import source.*;
import exceptions.*;
import position.Position;

class LinkedBinaryTreeTest {

	@Test
	void BTTest() {
		LinkedBinaryTree<String> a = new LinkedBinaryTree<String>();
		a.addRoot("HXD");
		assertEquals("HXD", a.root().element(), "HXD");
		assertEquals("UCD", a.insertLeft(a.root(), "UCD").element(), "UCD");
		assertEquals("EUR", a.insertRight(a.root(), "EUR").element(), "EUR");
		assertEquals(false, a.isEmpty(), "Deve retornar false");
		assertThrows(InvalidPositionException.class, () -> { a.isInternal(null); });
		Position<String> UCD = a.left(a.root());
		assertEquals("SDK", a.insertLeft(UCD, "SDK").element(), "SDK");
		assertEquals("BRL", a.insertRight(UCD, "BRL").element(), "BRL");
		Position<String> SDK = a.left(UCD);
		
		LinkedBinaryTree<String> T = createTree();
		Position<String> raiz = T.root();
		LinkedBinaryTree<String> tree = createAnotherTree();
		Position<String> root = tree.root();
		assertEquals("*++-7111*/352**19-232917", tree.binaryPreorder(tree, root));
		assertEquals("711-1+35/2*+192329-*17**", tree.binaryPostorder(tree, root));
		assertEquals("7-11+1+3/5*2*19*23-29*17", tree.inorder(tree, root));
		assertEquals("*++-777-111111-+111++*/333/555/*222*+***191919*-232323-292929-**171717**", tree.eulerTour(tree, root));
		assertEquals("((((7-11)+1)+((3/5)*2))*((19*(23-29))*17))", tree.printExpression(tree, root));
		assertEquals(4, tree.countLeft(tree));
		assertEquals(6, tree.countRight(tree));
	}
	
	public LinkedBinaryTree<String> createAnotherTree() {		
		LinkedBinaryTree<String> tree = new LinkedBinaryTree<String>();
		BTNode<String> raiz, n0, n1, n2, n3, n4, n5, n6, n7;	
		tree.addRoot("*");
		raiz = (BTNode<String>) tree.root();
		n0 = (BTNode<String>) tree.insertLeft(raiz, "+");
		n1 = (BTNode<String>) tree.insertLeft(n0, "+");
		tree.insertRight(n1, "1");
		n3 = (BTNode<String>) tree.insertRight(n0, "*");
		tree.insertRight(n3, "2");
		n4 = (BTNode<String>) tree.insertLeft(n3, "/");
		tree.insertLeft(n4, "3");
		tree.insertRight(n4, "5");
		n2 = (BTNode<String>) tree.insertLeft(n1, "-");
		tree.insertLeft(n2, "7");
		tree.insertRight(n2, "11");
		n5 = (BTNode<String>) tree.insertRight(raiz, "*");
		tree.insertRight(n5, "17");
		n6 = (BTNode<String>) tree.insertLeft(n5, "*");
		tree.insertLeft(n6, "19");
		n7 = (BTNode<String>) tree.insertRight(n6, "-");
		tree.insertLeft(n7, "23");
		tree.insertRight(n7, "29");	
		return tree;
	}
	
	public LinkedBinaryTree<String> createTree() {
		LinkedBinaryTree<String> T = new LinkedBinaryTree<String>();
		BTNode<String> raiz, n1, n2;	
		T.addRoot("RAIZ");
		raiz = (BTNode<String>) T.root();
		T.insertLeft(raiz, "LRAIZ");
		n1 = (BTNode<String>) T.insertRight(raiz, "RRAIZ");
		T.insertLeft(n1, "LLRAIZ");
		n2 = (BTNode<String>) T.insertRight(n1, "RRRAIZ");	
		T.insertLeft(n2, "LRRRAIZ");
		T.insertRight(n2, "RLRRRAIZ");		
		return T;
	}
}
