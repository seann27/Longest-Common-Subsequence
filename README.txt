This program calculates the longest common subsequence of two DNA sequences. This program is given an input file which calculates every possible combination (permutation) of pairs from the input file and computes the LCS for all of them. This means that there are (n*n-1)/2 possible combinations.

The list of classes, including the main driver, can be found in the classes folder. The classes are as follows:

dynamic_sequence_alignment -> DRIVER
dynamic_alignment
Node
Stack

The input required for this file requires a DNA per line with no headers or additional labels/prefixes. Each line should contain a new DNA sequence which is to be analyzed. The sequences are named within the program based on the order with which they are parsed. For example:

AACCGCCTG
GCTCG
TTTCGCTCAT

The sequence AACCGCCTG is on line one and is therefore named SEQ1 by the program. GCTCG is named SEQ2 and TTTCGCTCAT is named SEQ3.

The program is called with two command line arguments. The first argument is the input file and the second argument is the output directory. The output directory must be valid in order to store all of the text files which hold information about the LCS of a particular pair of DNA sequences.

Each output text file contains the sequences being compared, the number of invalid characters detected if any, the LCS optimal matrix which is used to calculate the LCS, the steps taken to traverse the matrix for the LCS, and the LCS itself.