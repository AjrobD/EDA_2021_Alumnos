package material.linear;

import material.Position;

public class ArrayQueue<E> implements Queue<E>{

    private class Node<E> implements Position<E> {
        private E element;

        public Node(E e){
            element = e;
        }

        public E getElement() {
            return element;
        }
    }

    private Node<E> queue[];
    private int top;
    private int n;
    private int MAX_SIZE;

    public ArrayQueue(){
        MAX_SIZE = 5;
        top = 0;
        n=0;
        queue = (Node<E>[]) new Node[5];
    }

    public ArrayQueue(int size){
        MAX_SIZE = size;
        top = 0;
        n=0;
        queue = (Node<E>[]) new Node[size];
    }

    public int size(){
        return n;
    }

    public boolean isEmpty(){
        return n==0;
    }

    public E front() throws RuntimeException{
        if (isEmpty()){
            throw new RuntimeException("Queue is empty");
        }
        return queue[top].getElement();
    }

    public void enqueue(E element) {
        Node<E> nodo =  new Node<E>(element);
        if(n<MAX_SIZE){
            queue[(top+n)%MAX_SIZE] = nodo;
        }
        else{
            Node<E>[] queue2 = (Node<E>[]) new Node[MAX_SIZE*2];
            for (int i = 0; i < MAX_SIZE; i++){
                queue2[i] = queue[(top+i)%MAX_SIZE];
            }
            queue2[MAX_SIZE] = nodo;
            queue = queue2;
            top = 0;
            MAX_SIZE=MAX_SIZE*2;
        }
        n++;
    }

    public E dequeue() throws RuntimeException{
        if (isEmpty()){
            throw new RuntimeException("Queue is empty");
        }
        E elemento = queue[top].getElement();
        top=(top+1)%MAX_SIZE;
        n--;
        return elemento;
    }
}
