import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**This class handles the primary algorithm for calculating the longest
 * common subsequence between two DNA sequences. This algorithm first 
 * generates a matrix, using a sequence for each dimension of the matrix. It
 * then initializes the matrix, setting all values within the first row and 
 * the first column equal to 0. The matrix is then iterated over from left to
 * right, top to bottom, scoring each cell according to the following rules:
 * 
 * If the corresponding characters match, then the value of the cell is equal 
 * to the value of the upper left cell plus one. Otherwise, the highest value
 * out of the surrounding three cells is taken. If there is a tie, the default
 * cell that is used to get the value from is the upper left diagonal. If 
 * there is a tie between the left cell and the upper cell, the upper cell's
 * value is taken. Each time a value is taken, a pointer is also stored to
 * the cell in which the value came from.
 * 
 * Once this matrix has been populated, the cells are then traced back by
 * starting with the last node in the matrix and following the pointers to
 * the very front of the matrix. Each time a node that is visited represents
 * a character match, the character is pushed onto a stack. This stack
 * represents the longest common subsequence.
 *
 * @author Sean Black
 */
public class dynamic_alignment 
{
   //Variables used to write to the text file
   BufferedWriter bw;
   File subsequence_build;
   String fileName;
   
   //Variables associated with sequence 1
   char[]seq1;
   String sequence1;
   String seq1_name;
   int length_seq1;
   int size1;
   int errors1 = 0;
   
   //Variables associated with sequence 2
   char[]seq2;
   String sequence2;
   String seq2_name;
   int length_seq2;
   int size2;
   int errors2 = 0;
   
   //Utiliy variables
   int stackSize;
   Node[][]matrix;
   Stack subseq;
   
   /**This is the constructor for the dynamic_alignment object. It requires
    * input of the two sequences being computed, the file name, the sequence
    * names, and throws an I/O exception if there is an invalid output
    * directory.
    * 
    * @param seq1 First sequence passed as a string
    * @param seq2 Second sequence passed as a string
    * @param fileName Name of the text file that is created for this object
    * @param seq1_name Name of the first sequence as it appears within input
    * file
    * @param seq2_name Name of the second sequence as it appears within input
    * file
    * @throws IOException IO exception that is thrown if the output directory
    * is invalid.
    */
   public dynamic_alignment(String seq1, String seq2, String fileName, 
           String seq1_name, String seq2_name) throws IOException
   {
      //initialize variables for first sequence
      this.seq1 = seq1.toCharArray();
      this.sequence1 = seq1;
      this.seq1_name = seq1_name;
      this.length_seq1 = seq1.length();
      this.size1 = length_seq1+1;
      
      //initialize variables for second sequence
      this.seq2 = seq2.toCharArray();
      this.sequence2 = seq2;
      this.seq2_name = seq2_name;
      this.length_seq2 = seq2.length();
      this.size2 = length_seq2+1;
      
      //define maximum length of subsequence and size of matrix
      if(length_seq1>length_seq2)
      {
         stackSize = length_seq1;
      }
      else
      {
         stackSize = length_seq2;
      }
      this.subseq = new Stack(stackSize);
      matrix = new Node[size1][size2];
      
      //initialize IO elements
      this.fileName = fileName;
      this.subsequence_build = new File(this.fileName);
      this.bw = new BufferedWriter (new FileWriter (subsequence_build));
   }
   
   /**This method populates the first row and first column with nodes that
    * equal 0. This method also contains error checking and counts the
    * amount of invalid characters encountered while parsing each sequence.
    */
   public void initialize()
   {
      int n=0;
      
      //populating the column and analyzing first sequence
      for (int i=0; i<size2; i++)
      {
         matrix[n][i] = new Node(0,-1,-1);
         int j=i;
         if (j<size2-1)
         {
            if(seq2[j]!='A'&&seq2[j]!='C'&&seq2[j]!='T'&&seq2[j]!='G')
            {
               errors2++;
            } 
         }   
      }
      
      //populating the row and analyzing the second sequence
      for (int i=0; i<size1; i++)
      {
         matrix[i][n] = new Node(0,-1,-1);
         int j=i;
         if (j<size1-1)
         {
            if(seq1[j]!='A'&&seq1[j]!='C'&&seq1[j]!='T'&&seq1[j]!='G')
            {
               errors1++;
            }   
         }
      }
      
   }
   
   /**Populates the two dimensional Node matrix by calculating each cell,
    * iterating from left to right, top to bottom.
    * 
    */
   public void populate_matrix()
   {
      for (int i=1; i<size1; i++)
      {
         for (int j=1; j<size2; j++)
         {
            calculate(i,j);
         }   
      }   
   }        
   
   /**This method calculates the value and the pointers of the new node
    * being analyzed. The way this method is structured, if the characters
    * match, the upper left cell is incremented by one and this value along
    * with the left cell and the upper cell are analyzed. The new node is set
    * equal to the upper left cell by default in the event of a three way tie.
    * This is to ensure that if there is a character match, the upper left
    * cell will be used. If there is not a character match and there is a 
    * three way tie, the upper left cell is taken. If there is a two way
    * tie between the upper left cell and the left or upper cell, the upper
    * left cell takes priority. If there is a tie between the upper cell and
    * the left cell, the upper cell takes priority.
    * 
    * @param i Row index of the new node being calculated
    * @param j Column index of the new node being calculated
    */
   public void calculate(int i, int j)
   {
      int a = matrix[i-1][j].get_value(); //upper cell value
      int b = matrix[i][j-1].get_value(); //left cell value
      int c = matrix[i-1][j-1].get_value();  //upper left cell value
      
      //if there is a character match
      if (seq1[i-1]==seq2[j-1])
      {
         c = c+1;
      }
      
      int temp = c;
      int temp_pRow = i-1;
      int temp_pCol = j-1;
      
      //if upper cell is largest
      if (temp < a)
      {
         temp = a;
         temp_pRow = i-1;
         temp_pCol = j;
      }
      
      //if left cell is largest
      if (temp < b)
      {
         temp = b;
         temp_pRow = i;
         temp_pCol = j-1;
      }
      
      //set node in matrix
      matrix[i][j] = new Node(temp,temp_pRow,temp_pCol);
   }     
   
   /**This method starts at the end of the matrix and traverses the matrix
    * via node pointers, storing characters that match in both sequences at
    * the same coordinates within the matrix.
    * 
    * @throws IOException Throws exception if the output directory is invalid.
    */
   public void build_subsequence() throws IOException
   {
      //write header
      writeln("---------------------------------");
      writeln("-----SUBSEQUENCE BUILD STEPS-----");
      writeln("---------------------------------");
      Node temp;
      int i = size1-1;
      int j = size2-1;
      
      //record steps
      while(i>0&&j>0)
      {
         String row = "i: "+i+"\t";
         String col = "j: "+j+"\t";
         String val = "|  value: "+seq1[i-1];
         String neg = "|  value: -";
         
         temp = matrix[i][j];
         if (temp.pRow==(i-1)&&temp.pCol==(j-1)&&
                 temp.get_value()==matrix[i-1][j-1].get_value()+1)
         {
            //store matching characters
            subseq.push(seq1[i-1]);
            writeln(row+col+val);
         }
         else
         {
            writeln(row+col+neg);
         }   
         i = temp.pRow;
         j = temp.pCol;
      }
   }
   
   /**Returns the string representation of the stack used to store the 
    * subsequence.
    * 
    * @return String representation of the subsequence stack.
    */
   public String get_subsequence()
   {
      return subseq.returnSeq();
   }        
   
   /**This method writes the formatted matrix to the text file.
    * 
    * @throws IOException Throws exception if the output directory is
    * invalid.
    */
   public void print_matrix() throws IOException
   {
      //write header
      bw.newLine();
      writeln("---------------------------");
      writeln("-----CALCULATED MATRIX-----");
      writeln("---------------------------");
      bw.write("      ");
      
      //write sequence 2 across top
      for(int k=0; k<length_seq2; k++)
      {
         bw.write(seq2[k]+"  ");
      }
      
      bw.newLine();
      
      //print matrix from left to right, top to bottom
      for(int i=0; i<size1; i++)
      {
         if (i>0)
         {   
            //write sequence 1 character for every row
            bw.write(seq1[i-1]+" ");
         }
         else
         {
            bw.write("  ");
         }   
         for(int j=0; j<size2; j++)
         {
            bw.write(String.format("%-3s","|"+matrix[i][j].get_value()));
         }
         bw.newLine();
      }
   }
   
   /**This method is a utility method which writes string to the output
    * file and then writes a new line.
    * 
    * @param string String to be written to file.
    * @throws IOException Throws exception if output directory is invalid.
    */
   public void writeln(String string) throws IOException
   {
      bw.write(string);
      bw.newLine();
   }
   
   /**This method encompasses the above methods to initialize the matrix,
    * populate the matrix, print the matrix, and then compute, return, and
    * write the longest common subsequence. This information is printed to
    * the system as well as written to the output file. This method also
    * notifies user of any errors detected with invalid characters within
    * the sequence input and also notes this in the output file.
    * 
    * @throws IOException Throws IO exception if output directory is invalid.
    */
   public void run_LCS() throws IOException
   {
      //write header
      writeln("==============================================");
      writeln("========= LONGEST COMMON SUBSEQUENCE =========");
      writeln("==============================================");
      bw.newLine();
      writeln(seq1_name+": "+sequence1);
      writeln(seq2_name+": "+sequence2);
      
      //initalize matrix
      initialize();
      System.out.println("initialized...");
      
      /**
       * Start error checking
       */
      System.out.println(errors1+" invalid characters detected "
              + "in sequence 1");
      if(errors1>0)
      {
         System.out.println("Warning! "+errors1+" invalid characters detected"
                 + " in "+seq1_name);
         bw.newLine();
         writeln("************************************");
         writeln(seq1_name+" contains "+errors1+" invalid characters!");
         writeln("************************************");
      }
      
      System.out.println(errors2+" invalid characters detected in "
              + "sequence 2");
      if(errors2>0)
      {
         System.out.println("Warning! "+errors2+" invalid characters detected"
                 + " in "+seq2_name);
         bw.newLine();
         writeln("************************************");
         writeln(seq2_name+" contains "+errors2+" invalid characters!");
         writeln("************************************");
      }
      /**
       * End error checking
       */
      
      /**Populate matrix
       * Print matrix
       * Traverse matrix
       * Write steps and subsequence to file
       * Close file
       */
      populate_matrix();
      System.out.println("matrix populated...");
      print_matrix();
      System.out.println("matrix printed...");
      bw.newLine();
      build_subsequence();
      System.out.println("subsequence built...");
      bw.newLine();
      bw.newLine();
      writeln(seq1_name+": "+sequence1);
      writeln(seq2_name+": "+sequence2);
      writeln("LONGEST COMMON SUBSEQUENCE: "+get_subsequence());
      writeln("LCS Size: "+matrix[size1-1][size2-2].get_value());
      bw.close();
   }        
     
}
