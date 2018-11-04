/* Kurt Higa
 * kuhiga
 * cmps12b-2
 * 2-26-18
 * 
 * The purpose of this class is to test different components of the Queue ADT
 */
public class QueueTest {

	public static void main (String [] args) {
		
		Queue test = new Queue();

		System.out.println("testing isEmpty()");
		System.out.println("queue is empty: " + test.isEmpty() );
		System.out.println();
		Object a = null;
		Object b = null;
		
		test.enqueue(a);
		test.enqueue(b);
		
		System.out.println("testing length()");
		System.out.println("length of queue is: " + test.length());
		System.out.println();
		System.out.println("testing dequeueAll()");
		test.dequeueAll();
		System.out.println("queue is now empty: " + test.isEmpty());
		System.out.println();
		
		test.enqueue(a);
		test.enqueue(b);
		System.out.println("Testing dequeue()..should print 1");
		test.dequeue();
		System.out.println("queue length is: " + test.length());
		System.out.println();
		System.out.println("Testing peek()...should print null");
		System.out.println(test.peek());	
		
	}
}
