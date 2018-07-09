import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**This is the main method which reads the input file for sequences and
 * creates dynamic_alignment objects containing the strings of each
 * combination (permutation) of sequences. The file is first parsed in order
 * to determine how many sequences are in the file. This is used to
 * initialize an array of strings which contain all sequences within the
 * file. This array is then iterated over every possible set of pairs which
 * can be created from all elements within the array by using a nested
 * for-loop structure with the nest loop decreasing in size with every
 * iteration of the parent loop. For every pair of sequences, a text file
 * is created with the sequence names and stored in a directory specified
 * in the command line. This main method also prints to the system with
 * steps it is taking to build these text files.
 *
 * @author Sean Black
 */
public class dynamic_sequence_alignment 
{
   /**Main method which drives the program. This method handles reading in
    * a correct file and a correct output directory and generates text
    * files for all permutations of the sequences within the text file. This
    * method has error checking as it pertains to I/O exceptions.
    *  
    * @param args Command line arguments
    * @throws java.io.FileNotFoundException Throws this exception if the
    * input file could not be located.
    **/
   public static void main (String[]args) throws FileNotFoundException, 
           IOException
   {   
      String filepath = args[0]; //path of input file
      FileReader input;
      int filesize;
      Scanner inputFile;
      try (FileReader count = new FileReader(filepath)) {
         input = new FileReader(filepath);
         filesize = 0;
         try (Scanner countFile = new Scanner (count)) {
            inputFile = new Scanner (input);
            //counts number of sequences in file
            while (countFile.hasNextLine())
            {
               countFile.nextLine();
               filesize++;
            }
         }
      count.close();
      System.out.println("Number of sequences in file: "+filesize);
      
      //Initializes array of sequences
      String [] strings = new String[filesize];
      int counter=0;
      //Reads in sequences and populates array
      while (inputFile.hasNext())
      {
         strings[counter]=inputFile.nextLine();
         counter++;
      }
      input.close();
      inputFile.close();
      
      System.out.println("READING INPUT FILE...");
      
      System.out.print("READING OUTPUT DIRECTORY...");
      String outDir = args[1];
      System.out.println(outDir);
      
      int numberStrings = strings.length;
      
      //Nested array to iterate over every possible pair of sequences
      for (int i=0; i<numberStrings; i++)
      {
         for (int j=i; j<numberStrings; j++)
         {
            if(j!=i)
            {
               /**Builds a dynamic_alignment object by generating the filename
                * and passing in two strings, the filename, and sequence
                * names
                */
               String seqA_name = "SEQ"+(i+1);
               String seqB_name = "SEQ"+(j+1);
               String fileName = (outDir+seqA_name+"-"+seqB_name+
                       "_subsequence_build.txt");
               dynamic_alignment LCS = new dynamic_alignment(strings[i],
                       strings[j],fileName,seqA_name,seqB_name);
               
               System.out.println();
               System.out.println("========================================");
               System.out.println(seqA_name+": "+strings[i]);
               System.out.println(seqB_name+": "+strings[j]);
               System.out.println("========================================");
               
               //writes LCS information to text file
               LCS.run_LCS();
               
               System.out.println("LCS Build Completed.");
               System.out.println("========================================");
               System.out.println("LONGEST COMMON SUBSEQUENCE: "+
                       LCS.get_subsequence());
               System.out.println("========================================");
               System.out.println();
            }
         }   
      }   
   }catch(IOException e){
         System.out.println("ERROR! INVALID FILE ARGUMENT DETECTED! SYSTEM "
                 + "XITING.");
      }
   }
}
