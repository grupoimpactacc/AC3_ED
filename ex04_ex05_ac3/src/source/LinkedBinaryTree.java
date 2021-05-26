package source;
import java.util.Iterator;
import exceptions.*;
import position.Position;
import tad_lista_de_nodos.NodePositionList;
import tad_lista_de_nodos.PositionList;

public class LinkedBinaryTree<E> implements BinaryTree<E> {
	protected BTPosition<E> root; 
	protected int size; 

	public LinkedBinaryTree() {
		root = null;
		size = 0;
	}

	public int size() { return size; }

	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		checkPosition(v); // método auxiliar
		return (hasLeft(v) || hasRight(v));
	}

	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		checkPosition(v);
		return (v == root());
	}
	
	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		return (vv.getLeft() != null);
	}

	public Position<E> root() throws EmptyTreeException {
		if (root == null) throw new EmptyTreeException("The tree is empty");
		return root;
	}

	public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> vv = checkPosition(v);
		Position<E> leftPos = (Position<E>) vv.getLeft();
		if (leftPos == null) throw new BoundaryViolationException("No left child");
		return leftPos;
	}
	
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> vv = checkPosition(v);
		Position<E> parentPos = (Position<E>) vv.getParent();
		if (parentPos == null) throw new BoundaryViolationException("No parent");
		return parentPos;
	}

	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		PositionList<Position<E>> children = new NodePositionList<Position<E>>();
		if (hasLeft(v)) children.addLast(left(v));
		if (hasRight(v)) children.addLast(right(v));
		return children;
	}
	
	public Iterable<Position<E>> positionsInorder() {
		PositionList<Position<E>> positions = new NodePositionList<Position<E>>();
		if (size != 0) inorderPositions(root(), positions); // atribui as posições usando caminhamento prefixado
		return positions;
	}
	

	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> positions = new NodePositionList<Position<E>>();
		if (size != 0) preorderPositions(root(), positions); // atribui as posições usando caminhamento prefixado
		return positions;
	}

	public Iterator<E> iterator() {
		Iterable<Position<E>> positions = positions();
		PositionList<E> elements = new NodePositionList<E>();
		for (Position<E> pos : positions) elements.addLast(pos.element());
		return elements.iterator(); // Um iterador sobre os elementos
	}

	public E replace(Position<E> v, E o) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		E temp = v.element();
		vv.setElement(o);
		return temp;
	}
	
	public Position<E> sibling(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> vv = checkPosition(v);
		BTPosition<E> parentPos = vv.getParent();
		if (parentPos != null) {
			BTPosition<E> sibPos;
			BTPosition<E> leftPos = parentPos.getLeft();
			if (leftPos == vv) sibPos = parentPos.getRight();
			else sibPos = parentPos.getLeft();
			if (sibPos != null) return sibPos;
		}
		throw new BoundaryViolationException("No sibling");
	}

	public Position<E> addRoot(E e) throws NonEmptyTreeException {
		if (!isEmpty()) throw new NonEmptyTreeException("Tree already has a root");
		size = 1;
		root = createNode(e, null, null, null);
		return root;
	}
	
	public Position<E> insertLeft(Position<E> v, E e) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		Position<E> leftPos = (Position<E>) vv.getLeft();
		if (leftPos != null) throw new InvalidPositionException("Node already has a left child");
		BTPosition<E> ww = createNode(e, vv, null, null);
		vv.setLeft(ww);
		size++;
		return ww;
	}
	
	public Position<E> insertRight(Position<E> v, E e) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		Position<E> rightPos = (Position<E>) vv.getRight();
		if (rightPos != null) throw new InvalidPositionException("Node already has a left child");
		BTPosition<E> ww = createNode(e, vv, null, null);
		vv.setRight(ww);
		size++;
		return ww;
	}
	
	public E remove(Position<E> v) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		BTPosition<E> leftPos = vv.getLeft();
		BTPosition<E> rightPos = vv.getRight();
		if (leftPos != null && rightPos != null) throw new InvalidPositionException("Cannot remove node with two children");
		BTPosition<E> ww; // o único filho de v, se houver
		if (leftPos != null) ww = leftPos;
		else if (rightPos != null) ww = rightPos;
		else // v é folha
			ww = null;
		if (vv == root) { // v é a raiz
			if (ww != null) ww.setParent(null);
			root = ww;
		} else { // v não é a raiz
			BTPosition<E> uu = vv.getParent();
			if (vv == uu.getLeft()) uu.setLeft(ww);
			else uu.setRight(ww);
			if (ww != null) ww.setParent(uu);
		}
		size--;
		return v.element();
	}
	
	public void attach(Position<E> v, BinaryTree<E> T1, BinaryTree<E> T2) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		if (isInternal(v)) throw new InvalidPositionException("Cannot attach from internal node");
		if (!T1.isEmpty()) {
			BTPosition<E> r1 = checkPosition(T1.root());
			vv.setLeft(r1);
			r1.setParent(vv); // T1 deve ser invalidada
		}
		if (!T2.isEmpty()) {
			BTPosition<E> r2 = checkPosition(T2.root());
			vv.setRight(r2);
			r2.setParent(vv); // T2 deve ser invalidada
		}
	}
	protected BTPosition<E> checkPosition(Position<E> v) throws InvalidPositionException {
		if (v == null || !(v instanceof BTPosition)) throw new InvalidPositionException("The position is invalid");
		return (BTPosition<E>) v;
	}

	protected BTPosition<E> createNode(E element, BTPosition<E> parent, BTPosition<E> left, BTPosition<E> right) {
		return new BTNode<E>(element, parent, left, right);
	}

	protected void preorderPositions(Position<E> v, PositionList<Position<E>> pos) throws InvalidPositionException {
		pos.addLast(v);
		if (hasLeft(v)) preorderPositions(left(v), pos); // recursão sobre o filho da esquerda
		if (hasRight(v)) preorderPositions(right(v), pos); // recursão sobre o filho da direita
	}
	
	public boolean isEmpty() { return (size == 0); }
	public boolean isExternal(Position<E> v) throws InvalidPositionException { return !isInternal(v); }
	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> vv = checkPosition(v);
		Position<E> rightPos = (Position<E>) vv.getRight();
		if (rightPos == null) throw new BoundaryViolationException("No right child");
		return rightPos;
	}

	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		return (vv.getRight() != null);
	}
	
	public void inorderPositions(Position<E> root, PositionList<Position<E>> positions) throws InvalidPositionException{	
    	if(hasLeft(root)) inorderPositions(left(root), positions);
        positions.addLast(root);
        if(hasRight(root)) inorderPositions(right(root), positions);
    } 
    
    public String binaryPreorder(LinkedBinaryTree<E> t, Position<E> v) {	
    	String retorno = "";
    	retorno += v.element();
    	if (hasLeft(v)) {
    		Position<E> tmp = left(v);
    		retorno += binaryPreorder(t, tmp);
    	}
    	if (hasRight(v)) {
    		Position<E> tmp1 = right(v);
    		retorno += binaryPreorder(t, tmp1);
    	}
    	return retorno;
    }
    
    public String binaryPostorder(LinkedBinaryTree<E> t, Position<E> v) {
    	String retorno = "";
    	if (hasLeft(v)) {
    		Position<E> tmp = left(v);
    		retorno += binaryPostorder(t, tmp);
    	}
    	if (hasRight(v)) {
    		Position<E> tmp1 = right(v);
    		retorno += binaryPostorder(t, tmp1);
    	}
    	retorno += v.element();
    	return retorno;
    }
    
    public String inorder(LinkedBinaryTree<E> t, Position<E> v) {	
    	String retorno = "";
    	if (hasLeft(v)) {
    		Position<E> tmp = left(v);
    		retorno += inorder(t, tmp);
    	}
    	retorno += v.element();
    	if (hasRight(v)) {
    		Position<E> tmp1 = right(v);
    		retorno += inorder(t, tmp1);
    	}
    	return retorno;
    }
    
    public String eulerTour(LinkedBinaryTree<E> t, Position<E> v) {	
    	String retorno = "";
    	retorno += v.element();
    	if (hasLeft(v)) {
    		Position<E> tmp = left(v);
    		retorno += eulerTour(t, tmp);
    	}
    	retorno += v.element();
    	if (hasRight(v)) {
    		Position<E> tmp1 = right(v);
    		retorno += eulerTour(t, tmp1);
    	}
    	retorno += v.element();
    	return retorno;
    }
    
    public String printExpression(LinkedBinaryTree<E> T, Position<E> v) {
    	String retorno = "";
        if(T.isInternal(v)) retorno += "("; 
        if(T.hasLeft(v))retorno += printExpression(T, T.left(v));
        if(T.isInternal(v)) {
            retorno += v.element().toString();
        } else {
        	retorno += v.element().toString();
        }
        if(T.hasRight(v))retorno += printExpression(T, T.right(v));
        if(T.isInternal(v))retorno +=  ")";
        return retorno;
	}
    
    public int countLeft(LinkedBinaryTree<E> t) {	
    	int h = 0;
    	for (Position<E> w : positions())
			if (isExternal(w) && w == left(parent(w)))
				h += 1;
		return h;
    }

    public int countRight(LinkedBinaryTree<E> t) {		
	int h = 0;
    	for (Position<E> w : positions())
			if (isExternal(w) && w == right(parent(w)))
				h += 1;
		return h;
    }
    
    public void makerBTSearch(LinkedBinaryTree<E> e, Position<E> v) {
		String s = "";
		for (Position<E> i : positionsInorder()) {
			s += i.element().toString() + ", ";
		}
		System.out.println(s.substring(0, s.length() - 2));
	}


}
