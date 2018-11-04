/*Kurt Higa
 * kuhiga
 * cmps12b-2
 * 2-26-18
 * 
 * The purpose of this class it make a Queue ADT based on the linked list data structure
 */
public class Queue implements QueueInterface{

	 class Node {
	      Object item; 
	      Node next;

	      Node(Object item){
	         this.item = item;
	         next = null;
	      }
	   }
	 // Fields
	   private int numItems;        // number of items in this Queue
	   private Node front;			// index of front element
	   private Node back;			// index of back element

	   //Queue constructor
	   Queue() {
		   front = null;
		   back = null;
		   numItems = 0;
		}
	   
	   // isEmpty()
	   // pre: none
	   // post: returns true if this Queue is empty, false otherwise
	   public boolean isEmpty() {
		   return(numItems==0);
	   }

	   // length()
	   // pre: none
	   // post: returns the length of this Queue.
	   public int length() {
		   return numItems;
	   }

	   // enqueue()
	   // adds newItem to back of this Queue
	   // pre: none
	   // post: !isEmpty()
	   public void enqueue(Object newItem) {
		   if(front==null) {
			   front = new Node(newItem);
			   numItems++;
		   }else {
			   Node a = front;
			   while(a.next!=null) {
				   a = a.next;
			   }
			   a.next = new Node(newItem);
			   back = a.next;
			   numItems++;
		   }
	   }

	   // dequeue()
	   // deletes and returns item from front of this Queue
	   // pre: !isEmpty()
	   // post: this Queue will have one fewer element
	   public Object dequeue() throws QueueEmptyException{
		   if(isEmpty()) {
			   throw new QueueEmptyException("cannot dequeue() when queue is empty");
		   }else {
			   Node temp = front;
			   front = temp.next;
			   numItems--;
			   return temp.item;
		   }
	   }

	   // peek()
	   // pre: !isEmpty()
	   // post: returns item at front of Queue
	   public Object peek() throws QueueEmptyException{
		   if(isEmpty()) {
			   throw new QueueEmptyException("cannot peek() when queue is empty");
		   }else {
			   return front.item;
		   }
	   }

	   // dequeueAll()
	   // sets this Queue to the empty state
	   // pre: !isEmpty()
	   // post: isEmpty()
	  public void dequeueAll() throws QueueEmptyException{
		   if(isEmpty()) {
			   throw new QueueEmptyException("cannot dequeueAll() when queue is already empty");
		   }
			   front = null;
			   back = null;
			   numItems = 0;
		    
	   }

	   // toString()
	   // overrides Object's toString() method
	   public String toString() {
		   String str = "";
		   Node a = front;
		   while(a!=null) {
			   str += a.item + " ";
			   a = a.next;
		   }
		   return str;
	   }
	   


}
