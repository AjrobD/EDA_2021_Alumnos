package material.linear;

import material.Position;

public class LinkedQueue<E> implements Queue<E>{
    private class DNode<E> implements Position<E> {

        private DNode<E>  next;
        private E element;

        /**
         * Constructor
         */
        public DNode(DNode<E> newNext, E elem) {
            next = newNext;
            element = elem;
        }

        @Override
        public E getElement() {
            return element;
        }

        public DNode<E> getNext() {
            return next;
        }

        public void setNext(DNode<E> newNext) {
            next = newNext;
        }

        public void setElement(E newElement) {
            element = newElement;
        }
    }

    private int size;
    private DNode<E> head,tail;

    public LinkedQueue(){
        size=0;
        head=null;
        tail=null;
    }
    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }
    public E front() throws RuntimeException{
        if(isEmpty()){
            throw new RuntimeException("This queue is empty");
        }
        return head.getElement();
    }

    public void enqueue(E element){
        DNode<E> nodo = new DNode<E>(null,element);
        if(tail==null){
            tail=nodo;
            head=nodo;
        }else{
            tail.setNext(nodo);
            tail = nodo;
        }
        size++;
    }

    public E dequeue() throws RuntimeException{
        if(isEmpty()){
            throw new RuntimeException("Queue is empty");
        }
        DNode<E> nodo = head;
        head = head.getNext();
        nodo.setNext(null);
        size--;
        return nodo.getElement();
    }

}
