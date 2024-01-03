public class Queue extends LinkedList 
{
    private int n;
    
    public Queue() {
        n=0;
    }
    
    public void enqueue(Object element) {
        addLast(element);
        n++;
    }
    
    public Object dequeue() {
        n--;
        return removeFirst();
    }
    
    public Object getFront() {
        return getFirst();
    }
    
    public boolean isEmpty() {
        return super.isEmpty();
    }
    
    public int size() {
        return n;
    }
}