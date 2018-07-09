/**This is a simple stack class which maintains the elements of the stack in
 * an array specified when the object is created. This stack class contains
 * the essential features of maintaining a stack, such as peek, push, pop,
 * isEmpty, isFull, and get size methods. Additional features include a
 * print stack method and a to string method which returns the stack in string
 * form from the beginning of the array up to the end of the stack. Error
 * handling is incorporated in all methods by printing system errors and
 * utilizing default values.
 *
 * @author Sean Black
 */
public class Stack 
{
   int size;
   int top;
   char[]bases;
   
   /**Constructor for the stack class. This constructor builds the object with
    * the size of the array implementation and initializes the top of the
    * stack to -1 to indicate an empty stack. It also initializes the stack
    * structure as a character array.
    * 
    * @param size - Restricted size of stack
    */
   public Stack (int size)
   {
      this.size = size;
      this.top = -1;
      this.bases = new char[size];
   }
   
   /**Pushes a character on top of the stack. Checks if stack is full and if
    * stack is full, prints an error message to the system and does not push
    * the character on the stack.
    * 
    * @param base - Character being pushed onto the stack
    */
   public void push(char base)
   {
      if(!isFull())
      {
         top++;
         bases[top]=base;
      }
      else
      {
         System.out.println("Error, stack is full size: "+top);
      }   
   }
   
   /**Pops a character off the stack, returning the character. Checks if the
    * stack is empty before popping the character and returns a default
    * whitespace character if stack is empty. Upon successfully popping a
    * character, the index indicating the top of the stack is decremented.
    * 
    * @return - Returns the character from the top of the stack and "removes"
    * this character from the stack.
    */
   public char pop()
   {
      if(!isEmpty())
      {
         top--;
         return bases[top+1];
      }
      else
      {
         System.out.println("Error!, Stack is empty.");
         return ' ';
      }
   }        
   
   /**Returns the character that is on the top of the stack. If the stack is
    * empty, a whitespace character is returned.
    * 
    * @return Returns the character that is at the top of the stack.
    */
   public char peek()
   {
      if (!isEmpty())
      {   
         return bases[top];
      }
      else
      {
         System.out.println("Error! Stack is empty!");
         return ' ';
      }   
   }
   
   /**Checks the stack to see if it is empty and returns true or false
    * depending on this conditional.
    * 
    * @return Returns true if the stack is empty and false if the stack is
    * not empty.
    */
   public boolean isEmpty()
   {
      if(top==-1)
      {
         return true;
      }
      else
      {
         return false;
      }   
   }
   
   /**Checks the stack to see if it is full and returns true or false
    * depending on this conditional.
    * 
    * @return Returns true if the stack is full and false if the stack is
    * not full.
    */
   public boolean isFull()
   {
      if(top==size-1)
      {
         return true;
      }
      else
      {
         return false;
      }   
   }
   
   /**Returns the index which indicates the last filled position of the
    * character array used to implement the stack. This value represents the
    * size of the structure.
    * 
    * @return Returns an integer indicating the size of the stack structure,
    * which represents values within the character array which are accessible
    * via push, pop, and peek methods.
    */
   public int get_size()
   {
      return top;
   }
   
   /**Iterates over the character array until the index pointing to the bottom
    * of the stack is reached. For every iteration this method prints the
    * character to the system.
    * 
    */
   public void print_stack()
   {
      for (int i=top; i>=0; i--)
      {
         System.out.print(bases[i]);
      }   
   }
   
   /**Iterates over the character array until the index pointing to the bottom
    * of the stack is reached. For every iteration, a string called sequence
    * appends the character within the array by concatenating itself with
    * the character.
    * 
    * @return 
    */
   public String returnSeq()
   {
      String sequence = "";
      for (int i=top; i>=0; i--)
      {
         sequence = sequence+bases[i];
      }
      return sequence;
   }        
}