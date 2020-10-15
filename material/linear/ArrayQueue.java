package material.linear;

import material.Position;

public class ArrayQueue<E>{

    private class Node<E> implements Position<E> {
        private E element;
        public E getElement() {
            return element;
        }
    }

    private Node<E>[] queue;
    private int top;
    private int n;
    private int MAX_SIZE;

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
            throw new RuntimeException("This queue is empty");
        }
        return queue[top].getElement();
    }

    public void enqueue(E element) {
        Node<E> nodo = (Node<E>) element;
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
            n++;
            MAX_SIZE=MAX_SIZE*2;
        }
    }

    public E dequeue() throws RuntimeException{
        if (isEmpty()){
            throw new RuntimeException("This queue is empty");
        }
        E elemento = queue[top].getElement();
        top=(top+1)%MAX_SIZE;
        n--;
        return elemento;
    }
}
