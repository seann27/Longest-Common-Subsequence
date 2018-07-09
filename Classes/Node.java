/**This is a node class used to store pointers within the 2D matrix used in
 * the dynamic_alignment class. The node holds a value and coordinates
 * to a previous node.
 *
 * @author Sean Black
 */
public class Node 
{
   int value;
   int pRow;
   int pCol;
   
   /**Constructor method which sets initial value and pointers.
    * 
    * @param value - Integer value of node being stored.
    * @param pRow - The row (first dimension index of matrix) of the node that
    * this node points to.
    * @param pCol - The column (second dimension index of matrix) of the node
    * that this node points to.
    */
   public Node(int value, int pRow, int pCol)
   {
      this.value = value;
      this.pRow = pRow;
      this.pCol = pCol;
   }
   
   /**This method returns an integer which specifies a row index
    * 
    * @return integer value specifying the first dimensional index
    */
   public int get_pRow()
   {
      return pRow;
   }
   
   /**This method returns an integer which specifies a column index
    * 
    * @return integer value specifying the second dimensional index
    */
   public int get_pCol()
   {
      return pCol;
   }
   
   /**This method returns an integer which specifies the value of the node
    * 
    * @return integer value of node
    */
   public int get_value()
   {
      return value;
   }        
           
}
