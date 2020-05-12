/*

Alex Petralia
CSCI 241
Assignment 4
07/24/2017

Program Purpose:
Organize Flight times and ordering contained within a .txt file
and used different types of Priority Queues to organize and order
the flight times. Also uses heapSort for the organizing of each
flight request.

Notes:
--most of the logic is in the main method and TakeOffQueue class
--the main method has most of the code to execute the priority queues
  and the minHeap class remains seperate so we can reference to it
  only when we need to use the minHeap for adding or sorting
--I used the main method for most of the code as my logic needs references to the
  args[] and file parsing to do most of the logic

*/




import java.util.*;
import java.io.*;
import java.util.Scanner;


public class TakeoffQueue {
   //main method contains:
   //---Scanner logic
   //---execution logic for each Priority Queue
   public static void main(String[] args) throws FileNotFoundException{
      //---------------------------scanner logic---------------------------
      
      //multiple file copies because we need to iterate through the file
      //multiple times in some of the logic for the priority queues
      //and keep track of two copies at once if needed
      Scanner file = null;
      Scanner fileCopy = null;
      Scanner fileCopy2 = null;
      Scanner fileIn = new Scanner(args[0]);
      String fileInput1 = fileIn.nextLine();
      String fileInput = fileInput1.toString();
      try{
         file = new Scanner(new File(fileInput));
         fileCopy = new Scanner(new File(fileInput));
         fileCopy2 = new Scanner(new File(fileInput));
         
      }catch(Exception e){
         System.out.println("-------------------");
         System.out.println("Could not find file");
         System.out.println("-------------------");
      }
      //holds the value for each arg after arg[0]
      int inputNum = Integer.parseInt(args[1]);
      //--------------------------end of scanner logic----------------------
      
      //the main heap
      minHeap<String> mainHeap = new minHeap<String>();
      //new Heap object to use
      minHeap<Integer> tempQueue = new minHeap<Integer>();
      //keeps track of what line the file is on and uses the lineCount
      //to instantiate the size of the heap array we are going to use
      int lineCount = 0;
      while(fileCopy.hasNext()){
         for(int i = 0; i < 5; i++){
            String inp = fileCopy.next();
            if(i == 4){
               lineCount++;
            }
         
         }
      }
      //intitializes the array with array heapSize
      Integer[] array = new Integer[lineCount];
      //initialize values that get reset after each line 
      //in the Priority Queue Logic
      String flightNum = "";
      String reqTime = "";
      //a place-holder for a string you want to grab
      String intString = "";
      //keeps track of the index of the heap to be added to
      int count = 0;
      
      //---------------------------------Logic for Priority Queue 1-------------------------------------
      if(inputNum == 1){
         //parses through the file
         while(file.hasNext() == true){
            //reads file line by line, 5 strings per line
            for(int i = 0; i < 5; i++) {
               String in = file.next();
               //if index is at the flightNum
               if(i == 0){
                  flightNum = in;
               //if index is at the reqTime
               }else if(i == 3){
                  //grabs the request time as a String of only the numbers
                  reqTime = in; 
                  //if the reqTime is @ 12:00 ["12:00".length == 5]
                  if(reqTime.length() == 5){
                     intString = new StringBuilder().append(reqTime.charAt(0))
                     .append(reqTime.charAt(1)).append(reqTime.charAt(3))
                     .append(reqTime.charAt(4)).toString();
                  }else{
                     intString = new StringBuilder().append(reqTime.charAt(0))
                     .append(reqTime.charAt(2)).append(reqTime.charAt(3))
                     .toString();
                  }
               //System.out.println(intString);
               //if the index is at the end of the line
               }else if(i == 4){
                  int stringToInt = Integer.parseInt(intString);
                  //logic for if the time is between 12:00 & 12:59
                  if(stringToInt >= 1200){
                     stringToInt = (stringToInt - 1200);
                  }
                  //adds time as an element to the heap
                  //as this is the only type of element we need to
                  //to deal with for Priority Queue 1
                  mainHeap.insertToQueue(intString);
                  array[count] = stringToInt;
                  count++;
               }
            }   
            //resets flightNum and reqTime so
            //another request can use these variables
            //to point to these values
            flightNum = "";
            reqTime = "";
            intString = "";
         }
              
         //-----IMPORTANT-----: this is where the heap is sorted, after this line all the
         //flight request times will be sorted and the object is TempQueue with "array" also being sorted
         tempQueue.heapSort(array);

         //keeps track of what line the file is on
         //and makes the --print statements-- for
         //Priority queue 1
         //also, specifically, the logic for numbers in the 12:00 range
         //Note: the heap is already sorted, we are just looking for matches
         //in the sorted array of times so we can make the correct
         //print statements for the 12:00 range case
         Scanner fileIterator = null;
         String tempCode = "";
         for(int i = 0; i < array.length; i++){
            fileIterator = new Scanner(new File(fileInput));
            while(fileIterator.hasNext()){
               for(int j = 0; j < 5; j++){
                  String tempStrig = fileIterator.next();
                  if(j == 0){
                     tempCode = tempStrig;
                  //logic for finding a match
                  //also logic if the value is @ 12:00 - 12:59
                  }else if(j == 3){
                     String builder = "";
                     int length = String.valueOf(array[i]).length();
                     //if the # is @ 12:00
                     if(length < 3){
                        //value in the array
                        int tInt = array[i] + 1200;
                        String tStrig = String.valueOf(tInt);
                        //builds the string we must compare with (format ==> "##:##")
                        builder = new StringBuilder().append(tStrig.charAt(0))
                        .append(tStrig.charAt(1)).append(":").append(tStrig.charAt(2))
                        .append(tStrig.charAt(3)).toString();
                        //Final print statments if a match is found in the sorted "array"
                        //compared to the request times in the file
                        if(builder.equals(tempStrig)){
                           System.out.println(tempCode);
                        }
                     }
                  }
               }
            }
         }
         
         //Second Iteration for values NOT in the 12:00 range
         fileIterator = null;
         tempCode = "";
         for(int i = 0; i < array.length; i++){
            fileIterator = new Scanner(new File(fileInput));
            while(fileIterator.hasNext()){
               for(int j = 0; j < 5; j++){
                  String tempStrig = fileIterator.next();
                  if(j == 0){
                     tempCode = tempStrig;
                  //logic for finding a match
                  //also logic if the value is NOT in 12:00 range
                  }else if(j == 3){
                     String builder = "";
                     int length = String.valueOf(array[i]).length();
                     //if the # is NOT in 12:00 range
                     if(length == 3){
                        //value in the array
                        int tInt = array[i];
                        String tStrig = String.valueOf(tInt);
                        builder = new StringBuilder().append(tStrig.charAt(0))
                        .append(":").append(tStrig.charAt(1))
                        .append(tStrig.charAt(2)).toString();
                        //Final print statments if a match is found in the sorted "array"
                        //compared to the request times in the file
                        if(builder.equals(tempStrig)){
                           System.out.println(tempCode);
                        }
                     }
                  }
               }
            }
         }
         //de-Queue's the Priority queue by the length of
         //of the minHeap array
         for(int i = 0; i < lineCount; i++){
            mainHeap.deleteMin();
         }     
      }
         

      
      
      //------------------------------Logic for Priority Queue 2---------------------------------------------------------
      if(inputNum == 2){
         //parses through the file
         //keeps track of passenger count for each line(request)
         String pasCount = "";
         while(file.hasNext() == true){
            //reads file line by line, 5 strings per line
            for(int i = 0; i < 5; i++) {
               String in = file.next();
               //if index is at the flightNum
               if(i == 0){
                  flightNum = in;
               //if index is at the passengers count
               }else if(i == 4){
                  //grabs the request time as a String of only the numbers
                  pasCount = in; 
                  //converts pasCount to an Int
                  int stringToInt = Integer.parseInt(pasCount);
                  //adds passenger count as an element to the heap
                  //as this is the only type of element we need to
                  //to deal with for Priority Queue 2
                  mainHeap.insertToQueue(pasCount);
                  array[count] = stringToInt;
                  count++;
               }
            }   
            //resets flightNum and reqTime so
            //another request can use these variables
            //to point to these values
            flightNum = "";
            reqTime = "";
            intString = "";
         }
         //initializes the heapSort on the array of passenger counts
         tempQueue.heapSort(array);
         
         //keeps track of each array element index
         int arrayCounter = array.length - 1;
         //holds on to curent flight request code
         String[] strigArray = new String[array.length];
         String tempFlight = "";
         while(fileCopy2.hasNext()){
            for(int i = 0; i < 5; i++){
               String inp = fileCopy2.next();
               if(i == 0){
                  tempFlight = inp;        
               }else if(i == 4){
                  //iterate through array of passenger counts
                  //to find match and adds them in order
                  // to the string Array of the flight code
                  for(int j = 0; j < array.length; j++){
                     if(array[j] == Integer.parseInt(inp)){
                        strigArray[j] = tempFlight;
                     }
                  
                  }
                  //ending print statements with the finalized, ordered
                  //arrays with the flight code and number of passengers
               }
            }
         }
         int arCount = array.length - 1;
         for(int k = 0; k < array.length; k++){
            System.out.println(strigArray[arCount]+ " " + array[arCount]);
            arCount--; 
         }       
         //de-Queue's the Priority queue by the length of
         //of the minHeap array
         for(int i = 0; i < lineCount; i++){
            mainHeap.deleteMin();
         }
      }
   }
}

//-----------------------------------------------Logic for the minHeap------------------------------------------------------------------------
@SuppressWarnings("unchecked")
//used "Comparable" interface to organize copies of the original minHeap
class minHeap<queue extends Comparable<queue>>{
   //Heap Values to be used
   private static final int valTotal = 2;
   //size of the heap
   private int heapSize;
   //the array/heap
   private queue[] heap;
   //Heap constructor
   public minHeap(){
      heap = (queue[]) new Comparable[valTotal];
      heapSize = 0;
   }

   //takes in an array of elements and instantiates the minHeap creation
   public minHeap(queue[] array){
      heapSize = array.length;
      heap = (queue[]) new Comparable[array.length+1];
      //non-0 index based input
      System.arraycopy(array, 0, heap, 1, array.length);
      createMinHeap();
   }

   //creates the minHeap
   private void createMinHeap(){
      //instantiates the minHeap by traversing down the heap
      for (int k = heapSize/2; k > 0; k--){
         iterateDownHeap(k);
      }
   }
   
   //iterates a given int down the array if comparables are not in order
   private void iterateDownHeap(int k){
      queue tempQueue = heap[k];
      int child;
      //looks forward for the child in the minHeap
      for (; 2*k <= heapSize; k = child){
         child = 2*k;
         //if statements for < and > cases from each child node
         if(child != heapSize &&heap[child].compareTo(heap[child + 1]) > 0){
            child++;
         }
         //replaces child with current value comparison
         if(tempQueue.compareTo(heap[child]) > 0){
            heap[k] = heap[child];
         //else stop the function, if no more
         //true comparisons
         }else{
            break;
         }
      }
      heap[k] = tempQueue;
   }

   //sorts the heap array
   public void heapSort(queue[] array){
      heapSize = array.length;
      //create new queue[]
      heap = (queue[]) new Comparable[heapSize+1];
      //create a copy of the array used for the heap
      System.arraycopy(array, 
      0, heap, 1, heapSize);
      //create min heap with the instantiated heap in the class
      createMinHeap();
      for (int i = heapSize; i > 0; i--){
         //moves root element to the end of the heap array
         queue tempQueue = heap[i]; 
         heap[i] = heap[1];
         //ads index i'th element to heap
         heap[1] = tempQueue;
         //iterate down on the size
         heapSize--;
         //iterated down the heap by 1
         iterateDownHeap(1);
      }
      for(int k = 0; k < heap.length-1; k++){
         array[k] = heap[heap.length - 1 - k];
      }
   }

   //deletes the root
   public queue deleteMin() throws RuntimeException {
      if (heapSize == 0) { 
         throw new RuntimeException();
      }
      //min of the queue is set to the 1st index in the heap
      queue min = heap[1];
      //iterate down 1 on the heapSize
      heap[1] = heap[heapSize--];
      iterateDownHeap(1);
      //returns back the min in the inputed
      //queue
      return min;
	}

   //inserts a new element
   public void insertToQueue(queue x){
      if(heapSize == heap.length - 1){
         doubleSize();
      }
      //insert at the end of the heap array
      int pos = ++heapSize;

      //iterate up the heap
      for(; pos > 1 && x.compareTo(heap[pos/2]) < 0; pos = pos/2 ){
         heap[pos] = heap[pos/2];
      }
      heap[pos] = x;
   }
   
   //helper function for when the instantiated heap needs
   //a bigger size
   private void doubleSize(){
      queue [] old = heap;
      heap = (queue []) new Comparable[heap.length * 2];
      System.arraycopy(old, 1, heap, 1, heapSize);
   }
}
