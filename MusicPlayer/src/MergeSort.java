import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class MergeSort {
 
    private ArrayList<Song> strList;
 
    // Constructor
    public MergeSort(ArrayList<Song> input) {
        strList = input;
    }
     
    public void sort() {
        strList = mergeSort(strList);
    }
 
    public ArrayList<Song> mergeSort(ArrayList<Song> whole) {
        ArrayList<Song> left = new ArrayList<Song>();
        ArrayList<Song> right = new ArrayList<Song>();
        int center;
 
        if (whole.size() == 1) {    
            return whole;
        } else {
            center = whole.size()/2;
            // copy the left half of whole into the left.
            for (int i=0; i<center; i++) {
                    left.add(whole.get(i));
            }
 
            //copy the right half of whole into the new arraylist.
            for (int i=center; i<whole.size(); i++) {
                    right.add(whole.get(i));
            }
 
            // Sort the left and right halves of the arraylist.
            left  = mergeSort(left);
            right = mergeSort(right);
 
            // Merge the results back together.
            merge(left, right, whole);
        }
        return whole;
    }
 
    private void merge(ArrayList<Song> left, ArrayList<Song> right, ArrayList<Song> whole) {
        int leftIndex = 0;
        int rightIndex = 0;
        int wholeIndex = 0;
 
        // As long as neither the left nor the right ArrayList has
        // been used up, keep taking the smaller of left.get(leftIndex)
        // or right.get(rightIndex) and adding it at both.get(bothIndex).
        while (leftIndex < left.size() && rightIndex < right.size()) {
            if ( (left.get(leftIndex).compareTo(right.get(rightIndex))) < 0) {
                whole.set(wholeIndex, left.get(leftIndex));
                leftIndex++;
            } else {
                whole.set(wholeIndex, right.get(rightIndex));
                rightIndex++;
            }
            wholeIndex++;
        }
 
        ArrayList<Song> rest;
        int restIndex;
        if (leftIndex >= left.size()) {
            // The left ArrayList has been use up...
            rest = right;
            restIndex = rightIndex;
        } else {
            // The right ArrayList has been used up...
            rest = left;
            restIndex = leftIndex;
        }
 
        // Copy the rest of whichever ArrayList (left or right) was not used up.
        for (int i=restIndex; i<rest.size(); i++) {
            whole.set(wholeIndex, rest.get(i));
            wholeIndex++;
        }
    }
 
    public void show() {
        System.out.println("Sorted:");
        for (int i=0; i< strList.size();i++) {
            System.out.println(strList.get(i).getSongName());
        }
    }
 
    public static void main(String[] args) {
        ArrayList<Song> input = JsonHelperMethods.readSongsJSON(); //new ArrayList<Song>();
//        Scanner sc = new Scanner(System.in);
 
//        System.out.println("Enter your text, type done for exit:");
//        String strin = sc.nextLine();
 
//        while(!strin.equals("done")) {
//            input.add(strin);
//            strin = sc.nextLine();
//        }
        System.out.println("************************");
        MergeSort test = new MergeSort(input);
        test.sort();
        test.show();
        
        try {
            FileWriter myWriter = new FileWriter("filename.txt");
            
            for (int i=0; i< test.strList.size();i++) {
            	myWriter.write(test.strList.get(i).getSongName()+"\n");
            }
//            myWriter.write("Files in Java might be tricky, but it is fun enough!");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        
        
    }
}