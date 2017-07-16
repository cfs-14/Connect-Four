/*
  The board class does need someway for the comp player to access the contents of the board though(1),
  and also perhaps the coordinates of the last placed piece of the human player(2).
  (1)There should be a scan method, in which all the pieces of the human player are given, and either the computer+, or the method itself must identify where the player is connecting, b/c the AI is influenced by that. So the computer needs to identify where the human is about to win.
  (2)The last placed piece by the human does not have more significance to the AI than where the human player is about to win.
   
   (3) if the used board in the inner class rolls over, then the reset method, should also reset the used board.
   (4) for the inner class, I need to filter the empty slots that are placeable, i.e. the slot below it is occupied.
   (5) recommend empty fruitless slots for chains that are two in length and are limited beyond three by bounds:
         so perhaps recommends the empty slot when if(counter == 2 && reverse empty/w/in bounds || k+2 within bounds/empty
   (corrected) When it's X|O|X vertically, it produces an empty slot, which is incorrect. check the k loop.
   (*) make a "To be reached" list of coordinates. These are empty corresponding slots in made chains that aren't placeable
      __ meaning they don't have a platform. These coordinates can be usd so that the player can try to make a chain for these.
      __This list will include all empty slots that are not placeable, and gap slots that are not placeable.
   (*)I believe a great AI will create chains with coinciding empty slots. 
   //to chek if empty slot is useable, i.e. there is a piece below it. 
   //_ simply if the empty slot is [x][y], check by doing:if([x-1][y] != ' '), then we can simply add these useable slots to the list. 
   
   unreset the checks for the beginnings of the methods.
   //For one, never will I need to set a piece next to another same, if its greater than priority two, when the next three slots are all useable.
   
*/

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.stream.*;
import java.util.function.*;

import java.lang.Math.*;
import java.util.*;

public class CFBoard
{
   private final static int ROW = 6;
   private final static int COL = 7;
   private int counter = 0; //for the number of gamepieces placed
   
   private int currCol = 0;
   
   Scanner in = new Scanner(System.in);
   
   public char[][] Board;
   public static char[][] subBoard;
   
   //constructor
   public CFBoard()
   {
      Board =  new char[ROW][COL];
      
      for(int i = 0; i < ROW; i++)
      {
         for(int j = 0; j < COL; j++)
         {
            Board[i][j] = ' ';
         }
      }
      
      subBoard =  new char[ROW][COL];
      
      for(int i = 0; i < ROW; i++)
      {
         for(int j = 0; j < COL; j++)
         {
            subBoard[i][j] = ' ';
         }
      }
      
   }

   
   
   //action methods
	public void reset()
	{
      
		for(int i = 0; i < ROW; i++)
		{
			for(int j = 0; j < COL; j++)
			{
				this.Board[i][j] = ' ';
			}
		}
	}
	//requires a method to read input for keys for selection, so keeps going until the enter key is pressed. while loop. returns from method, when ener key is pressed, and returns column number
   /*
   public void cursor()
   {
          
   }
   */
   
   //place methods
   public boolean place(char piece)
   {
   
      int choice;
      if(counter == ROW*COL) //when the number of pieces placed equals the number of slots, then the board is full
            {
                  //indicate that the slot is full, gives option to start over or quit in main.
                  /*perhaps the place() method can return a boolean. when it returns false is when it calls on the restart or quit method.
                  ex:
                  if(place)
                  else
                     askPlayer(); //ask if quit or start over.
                 */
             return false;
            }


      //vv this is for out of bounds, but if we are using the arrow keys, it will simply have in checking already ex:
      /*
         rightEdge = COL - 1;
         //cursor is on right edge,
         user presses right again, cursor++;
         //the cursor on the board is not revolving meaning it doesn't go back to column 0, but limited meaning it stays on the edge column
         if(cursor >= COL)
         {
            cursor = COL - 1;
            //so when the user is on the right edge and they press an out of bounds they stay on the last column they were on.
         }
      */
      
      System.out.println("Select your column: ");
      
      choice = in.nextInt() - 1;
      int check = 0;
      do
      {
         check++;
         while(choice < 0 || (COL-1) < choice)//(!)The user is picking numbers 1 - 7, not 0 - 6 index numbers.
         {//if out of range
            System.out.println("Your selections is out of range.Try again");
            choice = in.nextInt() - 1;
            check = 0;
         }
         while(this.Board[0][choice] != ' ') //if it's full asks for another selection
         {//checks the topmost row of the selected column if it's not empty.
            System.out.println("Your selections is full.Try again");
            choice = (in.nextInt() - 1);
            check = 0;
         }
      }while(check >= 2); //will work w/ 1 though.
      
      this.currCol = choice;     
         
       for(int i = ROW - 1; i >= 0; i--)
       {
             //check for soonest empty slot, soonest meaning from the bottom of the column to up.
             if(this.Board[i][this.currCol] == ' ') //if slot == empty
             {//place gamepiece there
                 //place visual here
                 this.Board[i][this.currCol] = piece;
                 System.out.println("Placed!"); //will remove later (!)***
                 counter++;
                 //this.printBoard();
                 //placed = true; //breaks out of outer while loop.

                  return true;
            }
        }
        return false; //not possible though.
   }
   
   
    public boolean place(int c, char piece)
   {
   
      int choice = c;
      if(counter == ROW*COL) //when the number of pieces placed equals the number of slots, then the board is full
            {
                  //indicate that the slot is full, gives option to start over or quit in main.
                  /*perhaps the place() method can return a boolean. when it returns false is when it calls on the restart or quit method.
                  ex:
                  if(place)
                  else
                     askPlayer(); //ask if quit or start over.
                 */
             return false;
            }


      //vv this is for out of bounds, but if we are using the arrow keys, it will simply have in checking already ex:
      /*
         rightEdge = COL - 1;
         //cursor is on right edge,
         user presses right again, cursor++;
         //the cursor on the board is not revolving meaning it doesn't go back to column 0, but limited meaning it stays on the edge column
         if(cursor >= COL)
         {
            cursor = COL - 1;
            //so when the user is on the right edge and they press an out of bounds they stay on the last column they were on.
         }
      */
      
      int check = 0;
      do
      {
         check++;
         while(choice < 0 || (COL-1) < choice)//(!)The user is picking numbers 1 - 7, not 0 - 6 index numbers.
         {//if out of range
            System.out.println("Your selections is out of range.Try again");
            choice = in.nextInt() - 1;
            check = 0;
         }
         while(this.Board[0][choice] != ' ') //if it's full asks for another selection
         {//checks the topmost row of the selected column if it's not empty.
            System.out.println("Your selections is full.Try again");
            choice = (in.nextInt() - 1);
            check = 0;
         }
      }while(check >= 2); //will work w/ 1 though.
      
      this.currCol = choice;     
         
       for(int i = ROW - 1; i >= 0; i--)
       {
             //check for soonest empty slot, soonest meaning from the bottom of the column to up.
             if(this.Board[i][this.currCol] == ' ') //if slot == empty
             {//place gamepiece there
                 //place visual here
                 this.Board[i][this.currCol] = piece;
                 System.out.println("Placed!"); //will remove later (!)***
                 counter++;
                 //this.printBoard();
                 //placed = true; //breaks out of outer while loop.

                  return true;
            }
        }
        return false; //not possible though.
   }

   
  //to get a test visual of the board
	public void printBoard() 
	{	
      System.out.printf("  ");
      for(int k = 0; k < COL * 5; k++)
         if(k % 5 == 0)
			   System.out.printf("%-5d", (k/5) + 1);
      System.out.println();
		  for(int k = 0; k < 18; k++)
			   System.out.printf("- ");
      System.out.println();
      
		for(int i = 0; i < ROW; i++)
       {
   			for(int j = 0; j < COL; j++)
         {
   				System.out.printf("| %c |", this.Board[i][j]);
         }
         System.out.println();
         for(int k = 0; k < COL * 5; k++)
   			        System.out.printf("-");
         System.out.println();
            
       }   
  }
   public boolean winVert(char piece)
   {
         for(int col = 0; col < COL; col++)
         {
            for(int row = ROW - 1; row >= 0 + (4-1); row--)
            {
               int counter = 0;
               for(int k = 0; k < 4; k++)
               {
                  if(this.Board[row - k][col] == piece)
                  {
                     counter++;
                  }
                  if(counter == 4)
                  {
                     System.out.printf("Player %c is a winner!\n", piece);
                     return true;
                  }
               }
            }
         }return false;
        
   }
   
   
   public boolean winHorz(char piece)
   {
         for(int col = 0; col <= COL - 4; col++)
         {
            for(int row = ROW - 1; row >= 0; row--)
            {
               int counter = 0;
               for(int k = 0; k < 4; k++)
               {
                  if(this.Board[row][col+k] == piece)
                  {
                     counter++;
                  }
                  if(counter == 4)
                  {
                     System.out.printf("Player %c is a winner!\n", piece);
                     return true;
                  }
               }
            }
         }  return false;
   }
   
  
   
   public boolean winDiagRt(char piece)
   {
      
      for(int col = 0; col <= COL - 4; col++) //so check one right one up
      {
      	for(int row = ROW - 1; row >= 0 + (4-1); row--) //r-4
      	{
      		int counter = 0;
      		for(int k = 0; k  <  4; k++) // [5][0] -> [4][1] -> []
      		{
      			if(this.Board[row-k][k+col] == piece)
      			{
      				counter++;
      			}
            if(counter == 4)
            {
                   System.out.printf("Player %c is a winner!\n", piece);
                     return true;
            }
      		}
      	 }
       }return false;
    }
    
    
     ////////////////////
     /*
   public void DiagLft(char piece)
   {
      
     for(int k = 0; k < 4; k++)
     {
           this.Board[3-k][3-k] = piece;//place piece where it's checking
     }
     
   }             
   */
   //////////////////// 
   
     /*
   public void vert(char piece)
   {
      for(int k = 0; k < 4; k++)
      {
         this.Board[ROW-1-k][2] = piece; //fill up the 3rd column from the bottom up.
      }
   }
               
   */
   /*
   public void testChain(char piece, char anti_piece)
   {
      for(int k = 0; k <= 2; k+=2)
      {
         this.Board[ROW-1-k][3] = piece;
      }
      this.Board[ROW-1-1][3] = anti_piece;
   }
   */            
      
   //////////////////////////////////////////////////////////
   
   public boolean winDiagLft(char piece)
   {
    
      
      for(int col = COL - 1; col >= 0 + (4-1); col--) //0 is the last index of the ending side.//(4-1), b/c zero is a true edge index unlike R and C. alternatively.
      {
      	for(int row = ROW - 1; row >= 0 + (4-1); row--)
      	{
      		int counter = 0;
      		for(int k = 0; k < 4; k++)
      		{
      			if(this.Board[row-k][col-k] == piece)
      			{
                  counter++;
      			}
            if(counter == 4)
            {
                   System.out.printf("Player %c is a winner!\n", piece);
                   return true;
            }
      		}	
      	}
      }return false;
   } 
   
   public boolean checkWin(char piece)
   {
      if(this.winHorz(piece) || this.winVert(piece) || this.winDiagRt(piece) || this.winDiagLft(piece))
      {
         return true;
      }
      else
         return false;
   }
      

   //(*)put in same package as CFBoard, and change this class to private
   public class BoardInfo //one instance of BoardInfo per game, that is why most vars are static
   { 
      /*variables*/
      
      
      //(*)(!) Create a method to check for empty slots, thus options to place the piece, when no chains are present.
      
       //used/accounted slots
       //(*)(!) turn back to private
       public CFBoard  used = new CFBoard();
   
       //coordinates of empty slot to place piece to block. [X][Y].
       //int[][] coord = new int[14][14]; //14 is estimate to for max number of blockable chains. //Same number b/c it account for the max number of obj. and each obj, has both dimensions.
       //(?) Public vv or getter methods? b/c need to pass to computer player
       public int[] priorities = new int[12];
       
       public  ArrayList<Coordinates> fullListCoordinates = new ArrayList<Coordinates>();
       
       public Map<Integer, List<Coordinates>> onesCoords = new HashMap<Integer, List<Coordinates>>();
         
       public Map<Integer, List<Coordinates>> antisCoords = new HashMap<Integer, List<Coordinates>>();
       
       public List<Coordinates> priority8 = new ArrayList<Coordinates>();
       
       public List<Integer> impCol = new ArrayList<Integer>();
       
       public List<Integer> avoidCol = new ArrayList<Integer>();
             
       //priority to list of Coordinates
       //private Map<Integer, List<Coordinates>>  mapCoords = new HashMap<Integer, List<Coordinates>>();
       //public ArrayList<Integer> Xgap, Ygap; //no need for a chains gap, b/c different in length.
       //List<List<Integer>> coordinates = new ArrayList<List<Integer>>(); //no initial cap, b/c not needed to know.
       //_coordinates.get(0).get(chain num); aot (as opposed to): Xcoordinate.get(chain num); Ycoordinate.get(chain num);
       ////(!)^^ Will need to make it a collection for all use, or an array for now (w/ size count);
       
       //List of opponent's chains(2-3 in length). Index+1(==size) accounts for numbers of chains, data is the length
       //Can have so that only  chains with empty slots are accounted for
       //int[] connects = new int[20] //20 is estimate to max num of poss chains (using min chains);
       public ArrayList<Integer> chains;     
   
       //counter for length of chain for loop
   
       //direction by direction
   
       /*So, the thing is going to go */
       //look for it in this class? Or iterate in the other class?
       //easier to iterate in the Board class itself.
       //so iterate through board
       
       BoardInfo()
       {
         ;
         /*Xcoordinate = new ArrayList<Integer>();
         Ycoordinate = new ArrayList<Integer>();
         Xgap = new ArrayList<Integer>();
         Ygap = new ArrayList<Integer>();
         
         */
         
         /*
         for(int i = 0; i < coord.length; i++)
            for(int j = 0; j < coord[0].length; j++)
               coord[i][j] = 0;
         for(int i = 0; i < connects.length; i++)
            connects[i] = 0;
         */
            
         
         //chains = new ArrayList<Integer>();
       }
       
       /*The following method gives information about the coordinates of the empty slots,
       but how it is implemented depends on how we want the computer to access the information.
       For example, the following method can return a two dimensional array of ints, which the 
       computer will have to work with.
       */
       
       public int[][] emptySlots()
       {
            List<Integer> xcoord = new ArrayList<Integer>();
            List<Integer> ycoord = new ArrayList<Integer>();
            
            List<List<Integer>> coordinates = new ArrayList<List<Integer>>(2);
            
            for(int row = 0; row < ROW; row++)
               for(int col = 0; col < COL; col++)
               {
                  if(Board[row][col] == ' ')
                  {
                     xcoord.add(row); //add x-coordinate to list
                     ycoord.add(col); //add y-coordinate to list
                    
                  }
               }
              
             int size = xcoord.size();
             int [][] coord = new int[2][size];
             
             for(int i = 0; i < 2; i++)
             {
               for(int j = 0; j < size; j++)
               {
                  if(i == 0)
                  
                     coord[i][j] = xcoord.get(j);
                  if(i == 1)
                     coord[i][j] = ycoord.get(j);
               }
              }
             
             return coord;
               
             //return int[][] coord = coordinates.toArray();
       }
       
         //this methods updates the information about the board for the computer. Should be invoked before any action
         //_or info retreival.
         //basically gets all the Coordinates of the gameboard into one list: fullListCoordinates.
         public void checkBoard(char piece, char antipiece) //static might cause an error. If it doesn't work it's b/c I will need to pass the Board to this method.
         {
            //total of 11 priorites.
            
            //(*)(!) has to reset everytime it is called.
            
            CFBoard.subBoard = CFBoard.this.Board;
            
            if(!fullListCoordinates.isEmpty())
               fullListCoordinates.clear();
               
            if(!onesCoords.isEmpty())
               onesCoords.clear();
               
            if(!antisCoords.isEmpty())
               antisCoords.clear();
               
            if(!priority8.isEmpty())   
               priority8.clear();
               
            if(!impCol.isEmpty())
               impCol.clear();
               
            if(!avoidCol.isEmpty())
               avoidCol.clear();
            
            //mapCoords.clear();
            
            
            //Xgap.clear();
            //Ygap.clear();
            
            
            //(!) What this method does not yet do is merge gaps, specifically between pieces of one unit-length chains.
            //NTS (Note To Self) //this.BoardInfo info = this.new Boardinfo(); create a local class, not an inner class?
            // board to record information, vert, horz, diagLft, diagRt; not needed b/c methods have direct access to member vars w/o passing it.
            //there will be four iterations, better to put them in methods, so methods could be defined in this method
            
            //priority 1 - 5 for one's piece
            vertCheck(piece, false);
            horzCheck(piece, false);
            diagRtCheck(piece, false);
            diagLftCheck(piece, false);
            
            
            //priority 1 - 5 for antipiece
            vertCheck(antipiece, true);
            horzCheck(antipiece, true);
            diagRtCheck(antipiece, true);
            diagLftCheck(antipiece, true);
            
            char[][] sub = Board;
            //priority 6 & 7 create gap w/ piece or w/o piece already in chain
            findCoordsGap(piece, sub, false);
            
            
            
            //priority 9 & 10, build single block from existing
            
            
            priority9Nd10(piece, false);
            
            //build from non-existing blocks.
            
            //call the stream method
            
             
            //used board can be carried over to the next check method.
              
         }  
         
         /*vv This method, turns fullListCoordinates, and distributes them how they belong. It places it in the map: keeps the order of the
               Coordinates, and mapsToInt, or TreeMap, based on their priority number.
               
               It also populates the impCol's and the avoidCol's via stream as well.
               From this method onward, the fullListCoordinates won't be used, 
               only the mapCoords, and impCol & avoidCol, etc.
         
         */
         private void distributor()//doesn't need to take arguments, since they are in this class already, not needed outside this class.
         {
            //2 maps, one for placeable one's piece, the other for placeable anti's piece
            
            //map 1, stream. (!) sorted not necessary, but should try to.
            /*filter by placeability, then filter by antipiece or not.
            */
            onesCoords = fullListCoordinates.stream().filter(c -> c.getAntipiece() == false).filter(c -> c.getPlaceable(Board) == true)
                           .collect(Collectors.groupingBy(Coordinates :: getPriority));
            
            antisCoords = fullListCoordinates.stream().filter(c -> c.getAntipiece() == true).filter(c -> c.getPlaceable(Board) == true)
                           .collect(Collectors.groupingBy(Coordinates :: getPriority));
                           
            impCol = fullListCoordinates.stream().filter(c -> c.getAntipiece() == true).filter(c -> c.getPlaceable(Board) == false)
                           .filter((Coordinates p) -> (p.getSpacesLeft(Board) == 1) && (p.getPriority() <= 3 /*1,2, or 3*/))
                           .map(Coordinates :: getCol)
                           .collect(Collectors.toList());
                           
            avoidCol = fullListCoordinates.stream().filter(c -> c.getAntipiece() == true).filter(c -> c.getPlaceable(Board) == false)
                           .filter((Coordinates p) -> (p.getSpacesLeft(Board) % 2 == 1) && (p.getSpacesLeft() >= 2))
                           .sorted(Comparator.comparingInt(Coordinates :: getPriority).reversed())
                           .collect(Collectors.groupingBy((Coordinates c) -> c.getSpacesLeft(), TreeMap::new, Collectors.toList()))
                           
                           .values().stream() //at this point, each element is of type List<Coordinates>
                           .flatMap(Collection::stream)
                           
                           .sorted(Comparator.comparingInt((Coordinates p) -> p.getSpacesLeft()).reversed())
                           .map(Coordinates :: getCol)
                           .collect(Collectors.toList());
            
            priority8 = fullListCoordinates.stream().filter((Coordinates p) -> p.getAntipiece() == false)
                           .filter((Coordinates c) -> c.getPlaceable(Board) == false)
                           .filter((Coordinates p) -> p.getSpacesLeft(Board) % 2 == 0)
                           .sorted(Comparator.comparingInt(Coordinates :: getPriority))
                           .collect(Collectors.groupingBy((Coordinates c) -> c.getSpacesLeft(), TreeMap::new, Collectors.toList()))
                           
                           .values().stream().flatMap(Collection::stream).collect(Collectors.toList());
            
                           
                         
         
         }
         
         public int decisionMaker(char piece, char antipiece)
         {
            //check the board anew, and get info esp. fullListCoordinates
            checkBoard(piece, antipiece);
            //separate info
            distributor(); //no arguments accepted for these, since can only be accessed from w/in class since only so needed
            
				 /*
   			 * Need to make it so that it does share the same variable for example passImp & passAvoid.
   			 * 
   			 */
   			
   			boolean once = false;
   			int finalCol = 0;
            boolean found = false;
   		   
   			while(!found)
   			{
   				//(!) if there is an incosistency, then it is probably that these bools are not inside the priority loop.
   				boolean passImp = true;
   				boolean passAvoid = true;
   			  
   				//first do priorities
   				/*
   				 * What this code does is:
   				 * 1. Checks if the given key has a value, skips w/o value
   				 * 2. it Gets the list of Coords at that priority level
   				 * 3. Gets the first Coord using index.
   				 * 4. Checks if the Coord is in imp col. if it is not, it is half the unlock of found.
   				 * 5. Checks Coord if in avoid col. if it is not, then it is the other half of the unlock found.
   				 * ..
   				 * 6. Loop is exited when Coord is not in avoid nor imp (in first try).
   				 * 7. if Coord conflicts with imp or avoid, then the Coord is not assigned a found(loop exit)
   				 * 8. Cont. In this case, the next Coord in the list for the same priority is tested. 
   				 * 9. If no valid Coordinates are found, then the loop will exit when the list is exhausted.
   				 * 10. Goes on the next phase, and will basically skip the other phases, b/c those don't have a value for that priority.
   				 */
   				for(int prrty = 0; prrty <= 11; prrty++)
   				{
   					
   					//o`ffense
   					if(onesCoords.get(prrty) != null)//if there is a value for this key, then proceed
   					{
                     
   						for(int index = 0; index < onesCoords.get(prrty).size() && !found; index++) //either goes through entire list, or finds the coordinate
   						{
   							//get List
   							List<Coordinates> listCoords = onesCoords.get(prrty);
   							//get Coord
   							Coordinates currCoord = listCoords.get(index);
   			
   							//test for impossible col
   							if(2 < prrty)
   							{
   								for(int k = 0; k < impCol.size(); k++)
      									if(currCoord.getCol() == impCol.get(k)) //if the column matches any of the impossible columns (list)
      										//mechanism to have to try the next one.
      										passImp = false;
      						}
      						
      						//test for aviod col
      						if(2 < prrty && once == false) // can include the once in the condition in the for loop i.e. !once
      						{
      						   for(int k = 0; k < avoidCol.size(); k++)
         							  //so if doesn't match with the avoid col's then it's good, b/c not all cols will usu be in the avoid cols
         							  //If it matches, if it's equal to false, then the index has to be increased. 
         							  if(currCoord.getCol() == avoidCol.get(k))
         								   passAvoid = false;
      						}
      						
      						//if it's the first iteration, then it needs to check for avoidable columns
      						if(once == false)
      						{
      							if(passImp && passAvoid)
      							{
      							   found = true; //so if it returns an int, it means it returns the col, when it's in a method. So, it should return null.
      							   //_outside the method, it will check if it's not == null, it returns, else the next priority check will happen, if 
      							   //_it's == null. 
      							   //can simply return here
      							   finalCol = currCoord.getCol();
      							}
      						}
      						else
      						{
      							if(passImp)
      							{
      							   found = true; //so if it returns an int, it means it returns the col, when it's in a method. So, it should return null.
      							   //_outside the method, it will check if it's not == null, it returns, else the next priority check will happen, if 
      							   //_it's == null. 
      							   //can simply return here
      							   finalCol = currCoord.getCol();
      							}
      						}
                        passImp = true;
   					      passAvoid = true;
   					   }/////
   					}//offensive
   					
   					passImp = true;
   					passAvoid = true;
   				   
   					//preventative
   					if(0 <= prrty && prrty <= 5)//this is not necessary, b/c anti has no values for keys above this.
                  {
                     if(antisCoords.get(prrty) != null)
      					{
      						for(int index = 0; (index < antisCoords.get(prrty).size()) && !found; index++) //either goes through entire list, or finds the coordinate
      						{
      						//get List
      						 	//List<Coordinates> listCoords = antiCoord.get(i);
       							//get Coord
       							//Coordinates currCoord = listCoords.get(index);
                           found = true;
                           finalCol = antisCoords.get(prrty).get(index).getCol();
       					   }
      					}
                  }
   					
   					//If it reaches priority of unplaceable one's Coords
   					if(prrty == 8 && !found)
   					{
   						if(priority8.size() > 0) //if it's not an empty list
   						{
   							for(int index = 0; index < priority8.size() && !found; index++) //either goes through entire list, or finds the coordinate
   							{
   								Coordinates currCoord = priority8.get(index);
                           
   								//test against imp col
   								for(int k = 0; k < impCol.size(); k++)
   								{
   									if(currCoord.getCol() == impCol.get(k))
   									{
   										passImp = false;
   									}
   								}
   								
   								//test against avoidCol
   								for(int k = 0; k < avoidCol.size() && !once; k++)
   								{
   									if(currCoord.getCol() == avoidCol.get(k))
   										passAvoid = false;
   								}
   								
   								if(!once) //if first try
   								{
   									if(passImp && passAvoid)
   									{
   										found = true;
   										finalCol = currCoord.getCol();
   									}
   								}
   								else
   								{
   									if(passImp)
   									{
   										found = true;
   										finalCol = currCoord.getCol();
   									}
   								}
                           
                           passImp = true;
   					         passAvoid = true;
   							}
   						}
   					}
                  
                  passImp = true;
   					passAvoid = true;
                  
                  
                  //random safe coordinate
                  if(prrty == 11 & !found)
                  {
                     //go through each col once in random order, testing for safety
                     //can either do List<Integer> or viables, or array of Used
                     List<Integer> available = new ArrayList<Integer>(7);
                     
                     for(int i = 0; i < 7; i++)
                        available.add(i);
                     
                     //while the list is not empty and hasn't been found
                     while(!available.isEmpty() && !found) //can either do available.size() == 0, or empty().  
                     {
                        //get random index, thus random avial num
                        int index = (int)(Math.random() * available.size());
                        int num = available.get(index);
                        //remove from List b/c taken into account hereafter
                        available.remove(index);
                        
                        //test against imp col
   							for(int k = 0; k < impCol.size(); k++)
   								if(num == impCol.get(k))
   									passImp = false;
   							
   							//test against avoidCol
   							for(int k = 0; k < avoidCol.size() && !once; k++)
   								if(num == avoidCol.get(k))
   									passAvoid = false;
   							
   							if(!once) //if first try
   								if(passImp && passAvoid)
   								{
   									found = true;
   									finalCol = num;
   								}
   							else
   								if(passImp)
   								{
   									found = true;
   									finalCol = num;
   								}
                           
                        passImp = true;
   					      passAvoid = true;   
                        
                     }            
   					}
   					//
   			   } //prrty               
   			  once = true;
   			}//while
			
			return finalCol;
		 }//L
         
         private void vertCheck(char piece, boolean antip)
         {
            used.reset();//reset the used grid of info. --> BoardInfo.CFBoard((Used)).reset();
            int counter = 0;
            
            vertChecker: 
            /*_Can do a merge, such as redo the dirCheck method in a(n) different/opposite direction. 
            And when the empty slots that are not doubles, meaning they don't nboth belong to the same exact chain 
            (this prevents empty slots that are shared by different chains) are discarded, then the resulting list is sorted and merged.
            
            The other alternative is for the other direction to be checked somewhere within this same module here and it is simply added to the list.
            */
            
            
            for(int col = 0; col < COL; col++) //change to: col <= COL-1
            {
               for(int row = ROW-1; row >= 0 + (2-1); row--)
               {     
                     iterate:
                     
                     for(int k = 0; (k < 3) && (row-k >= 0); k++) //checks the corresponding slots for the chain, to see if there is a chain
                     {  //The following ==/!= ifs can be condensed into one. 
                        
                        if(Board[row-k][col] == ' ') //space encountered.
                        {
                              //reset
                              counter = 0;
                              break iterate;
                        }      
                        if(Board[row-k][col] != ' ' && Board[row-k][col] != piece) //anti-piece encountered
                        {
                               used.Board[row-k][col] = 'B';
                               //reset
                               counter = 0;
                               break iterate;
                        }
                        if(Board[row-k][col] ==  piece) //if it finds the piece it's looking for
                        {
                             counter++;
                             used.Board[row-k][col] = 'U';
                        }
                        //if it no longer finds the next piece in the chain, then there is either an empty slot, or it's occupied by the opponent(blocked)
                        if(1 < counter && counter < 4)// k is at least 2.                                  
                        {
                              /*Find a chain w/ an empty slot at end*/
                              if(row-(k+1) >= 0)//bounds checking
                              {
                                 //check if the next corresponding chain slot is empty AND placeable(slot beneath is occupied), true: we record info, otherwise fruitless
                                 if(Board[row-(k+1)][col] == ' ')
                                 {
                                    //chains' information are stored in an ArrayList b/c don't need to keep track of current size w/ var, and neither the index b/c of class' code size and add() respectively
                                    //(!)Only return coordinates and perhaps chain info as references when done at end
                                    
                                    //take coordinates of empty slot to place piece
                                    
                                    //chain information recorded                          
                                    chains.add(counter);
                                    if(counter == 3)
                                       fullListCoordinates.add(new Coordinates(row-(k+1),col,1, antip));
                                    else //counter == 2
                                       fullListCoordinates.add(new Coordinates(row-(k+1),col,5, antip));
                                    //record used
                                    for(int m = 0; m < counter; m++)
                                    {
                                       //record used coordinates on board
                                       used.Board[row-m][col] = 'U';
                                    }
                                    //reset
                                       counter = 0;
                                       break iterate;
                                 }  
                              }
                         }  
                       }//for
                     }//ROW
                  }//COL
               }//vertcheck
               
          private void horzCheck(char piece, boolean antip)
          {
              used.reset();
              int counter = 0;
               
               horzCheck:
          
                for(int row = ROW-1; row >= 0; row--)
                {
                  for(int col = 0; col <= COL - 2; col++)
                  {
                           iterate:
                           for(int k = 0; (k < 3) && (col+k < COL); k++) //checks the corresponding slots for the chain, to see if there is a chain
                           {  
                              if(Board[row][col+k] == ' ')
                              {
                                 if(counter == 1 && (col+k+1 < COL)) //if poss gap found && bounds check
                                 
                                    gapFinder:
                                    if(Board[row][col+k+1] == piece) //if a gap found, keep checking. 
                                    {
                                       /*Types of gaps(3)*/
                                       
                                       /*doublesided empty gap*/
                                       if(col+k+2 < COL && col+k-2 >= 0)//bounds check
                                          if(Board[row][col+k+2] == ' ' && Board[row][col+k-2] == ' ')
                                          {
                                             fullListCoordinates.add(new Coordinates(row, col+k, 3, antip));
                                             break gapFinder;
                                          }
                                           
                                       /*pc-filled gap(checks either sided) && one-sided empty gap*/
                                       //bounds check, then checks if 
                                       if(col+k+2 < COL) //bounds check
                                          if(Board[row][col+k+2] == piece)//if either flank is equal to piece, we got a winner. 
                                             fullListCoordinates.add(new Coordinates(row, col+k, 2, antip));//count in priorities[3/4] b/c immediate winner
                                             //record gap's coordinates
                                          else if(Board[row][col+k+2] == ' ')
                                             fullListCoordinates.add(new Coordinates(row, col+k+2, 4, antip));
                                       if(col+k-2 >= 0)
                                          if(Board[row][col+k-2] == piece)
                                             fullListCoordinates.add(new Coordinates(row, col+k, 2, antip));
                                          else if(Board[row][col+k-2] == ' ')
                                             fullListCoordinates.add(new Coordinates(row, col+k-2, 4, antip));
                                    }     
                                 //reset
                                 counter = 0;
                                 break iterate;
                              }
                              if(Board[row][col+k] != ' ' && Board[row][col+k] != piece)
                              {
                                 used.Board[row][col+k] = 'B';
                                 //reset
                                 counter = 0;

                                 break iterate;
                              }
                              if(Board[row][col+k] ==  piece) //if it finds the piece it's looking for
                              {
                                   counter++;
                                   used.Board[row][col+k] = 'U';
                              }
                              //if it no longer finds the next piece in the chain, then there is either an empty slot, or it's occupied by the opponent(blocked)
                              if(1 < counter && counter < 4)//this is a better approach than ('if Board[][] == ' ') b/c we don't have to wait until an empty slot shows up to account for the reverse empty slot.                                   
                              {
                                    boolean accounted = false;
                                    /*Find a chain w/ an empty slot at end*/
                                    if(col+(k+1) < COL)//bounds checking
                                    {
                                       //check if the next corresponding chain slot is empty, true: we record info, otherwise fruitless
                                       if(Board[row][col+(k+1)] == ' ')
                                       {
                                             //take coordinates of empty slot to place piece
                                             
                                             //chain information recorded                          
                                             chains.add(counter);
                                             //record to priorites
                                             if(counter == 3)
                                                fullListCoordinates.add(new Coordinates(row, col+(k+1), 1, antip));
                                             else //counter == 2
                                                fullListCoordinates.add(new Coordinates(row, col+(k+1), 5, antip));
                                             accounted = true;
                                       }  
                                    }
                                    
                                    /*Find a chain w/ an empty slot at reverse end*/
                                    if(col-(2-1) >= 0) //bounds checking
                                    {
                                       //check reverse slot
                                       if(Board[row][col-1] == ' ') //no bounds checking b/c not poss to go out of bounds
                                       {
                                             //chain information recorded                          
                                             chains.add(counter);
                                             //record to priorites
                                             if(counter == 3)
                                                fullListCoordinates.add(new Coordinates(row, col-1, 1, antip));
                                             else //counter == 2
                                                fullListCoordinates.add(new Coordinates(row, col-1, 5, antip));
                                             accounted = true;
                                       }
                                    }
                                    if(accounted == true) //if useful chain accounted
                                    {
                                       //record used
                                       for(int m = 0; m < counter; m++)
                                       {
                                          //record used coordinates on board
                                          used.Board[row][col+m] = 'U';
                                       }
                                       counter = 0;
                                       accounted = false;
                                       //reset
                                       counter = 0;
                                       break iterate;
                                    }
                               }  
                           }
                     }//row
                  }//col
           }//horz finish
          
         private void diagRtCheck(char piece, boolean antip)
         {
                used.reset();
                int counter = 0;
              
                DiagRight:
          
                for(int col = 0; col <= COL-2; col++)
                {
                  for(int row = ROW-1; row >= 0 + (2-1); row--)
                  {
                           iterate:
                           for(int k = 0; (k < 3) && ((row-k >= 0) && (col+k < COL)); k++) //checks the corresponding slots for the chain, to see if there is a chain
                           {  
                              if(Board[row-k][col+k] == ' ')
                              {
                                 if(counter == 1 && (col+k+1 < COL) && (row-(k+1) >= 0))//if gap possible
                                 
                                    gapFinder:
                                    if(Board[row-(k+1)][col+(k+1)] == piece)//if gap found
                                    {
                                       /*check for double-sided empty gap*/
                                       if(((row-(k+2) >= 0) && (col+(k+2) < COL)) && //bounds check
                                         (((row-k)+2 < ROW) && ((col+k)-2 >= 0)))
                                          if(Board[row-(k+2)][col+(k+2)] == ' ' &&
                                             Board[(row-k)+2][(col+k)-2] == ' ')
                                          {
                                            fullListCoordinates.add(new Coordinates(row-k, col+k, 3, antip));
                                            break gapFinder;
                                          }
                                       
                                       /*pc-filled gap && one-sided empty gap*/
                                       if((row-(k+2) >= 0) && (col+(k+2) < COL)) //bounds check
                                       {
                                          if(Board[row-(k+2)][col+(k+2)] == piece)
                                             fullListCoordinates.add(new Coordinates(row-k,col+k, 2, antip));
                                          if(Board[row-(k+2)][col+(k+2)] == ' ')
                                             fullListCoordinates.add(new Coordinates(row-(k+2),col+(k+2), 4, antip));
                                       }
                                       if(((row-k)+2 < ROW) && ((col+k)-2 >= 0)) //bounds check //reverse end
                                       {
                                          if(Board[(row-k)+2][(col+k)-2] == piece)
                                             fullListCoordinates.add(new Coordinates(row-k, col+k, 2, antip));
                                          if(Board[(row-k)+2][(col+k)-2] == ' ')
                                             fullListCoordinates.add(new Coordinates((row-k)+2, (col+k)-2, 4, antip));
                                       } 
                                    }
                                 //reset
                                 counter = 0;
                                 break iterate;
                              }
                              if(Board[row-k][col+k] != ' ' && Board[row-k][col+k] != piece)
                              {
                                 used.Board[row-k][col+k] = 'B';
                                 //reset
                                 counter = 0;
                                 break iterate;
                              }
                              if(Board[row-k][col+k] ==  piece) //if it finds the piece it's looking for
                              {
                                   counter++;
                                   used.Board[row-k][col+k] = 'U';
                              }
                              //if it no longer finds the next piece in the chain, then there is either an empty slot, or it's occupied by the opponent(blocked)
                              if(1 < counter && counter < 4)                                    
                              {
                                    boolean accounted = false;
                                    
                                    /*Find a chain w/ an empty slot at end*/
                                    if((row-(k+1) >= 0) && (col+(k+1) < COL))//bounds checking
                                    {
                                       //check if the next corresponding chain slot is empty, true: we record info, otherwise fruitless
                                       if(Board[row-(k+1)][col+(k+1)] == ' ')
                                       {
                                            //take coordinates of empty slot to place piece
                                             //chain information recorded                          
                                             chains.add(counter);
                                             
                                             //record to priorites
                                             if(counter == 3)
                                                fullListCoordinates.add(new Coordinates(row-(k+1), col+(k+1), 1, antip));
                                             else //counter == 2
                                                fullListCoordinates.add(new Coordinates(row-(k+1), col+(k+1), 5, antip));
                                             accounted = true; 
                                       }  
                                    }
                                    
                                    /*Find a chain w/ an empty slot at reverse end*/
                                    if((row+(2-1) < ROW) && (col-(2-1) >= 0)) //bounds checking
                                    {
                                       //check reverse slot
                                       if(Board[row+1][col-1] == ' ') //no bounds checking b/c not poss to go out of bounds
                                       {
                                             //chain information recorded                          
                                             chains.add(counter);
                                             //record to priorites
                                             if(counter == 3)
                                                fullListCoordinates.add(new Coordinates(row+1, col-1, 1, antip));
                                             else //counter == 2
                                                fullListCoordinates.add(new Coordinates(row+1, col-1, 5, antip));
                                          accounted = true;
                                       }
                                    }
                                    if(accounted == true) //if useful chain accounted
                                    {
                                       //record used
                                       for(int m = 0; m < counter; m++)
                                       {
                                          //record used coordinates on board
                                          used.Board[row-m][col+m] = 'U';
                                       }
                                       accounted = false;
                                       //reset
                                       counter = 0;
                                       break iterate;
                                       
                                    }
                               }  
                           }
                       }// Row
                  } //Col
          }//Diag Right Finish
          
         private void diagLftCheck(char piece, boolean antip)
         {
                used.reset();
                int counter = 0;
                boolean accounted = false;
              
                DiagLft:
          
                for(int col = COL-1; col >= 0+(2-1); col--)//start from right column
                {
                  for(int row = ROW-1; row >= 0+(2-1); row--)//start from bottom row
                  {
                           iterate:
                           for(int k = 0; (k < 3) && ((row-k >= 0) && (col-k >= 0)); ++k) //checks the corresponding slots for the chain, to see if there is a chain
                           {  
                              if(Board[row-k][col-k] == ' ')
                              {
                                 if(counter == 1 && (row-(k+1) >= 0 ) && (col-(k+1) >= 0 )) //if poss gap found & bounds check
                                       
                                       gapFinder:
                                       if(Board[row-(k+1)][col-(k+1)] == piece) //gap found
                                       {
                                          //categorize type of gap
                                          /*dbl-sided empty gap*/
                                          if((row-(k+2) >= 0 && col-(k+2) >= 0) && //bounds check
                                            ((row-k)+2 < ROW && (col-k)+2 < COL))
                                            if(Board[row-(k+2)][col-(k+2)] == ' ' &&
                                               Board[(row-k)+2][(col-k)+2] == ' ')
                                            {
                                               fullListCoordinates.add(new Coordinates(row-k, col-k, 3, antip));
                                               break gapFinder;
                                            }
                                          /*one-sided empty gap & pc-filled gap*/
                                          if(row-(k+2) >= 0 && col-(k+2) >= 0)
                                          {
                                             if(Board[row-(k+2)][col-(k+2)] == piece)
                                                fullListCoordinates.add(new Coordinates(row-k, col-k, 2, antip));
                                             if(Board[row-(k+2)][col-(k+2)] == ' ')
                                                fullListCoordinates.add(new Coordinates(row-(k+2), col-(k+2), 4, antip));
                                          }
                                          if((row-k)+2 < ROW && (col-k)+2 < COL)
                                          {
                                             if(Board[(row-k)+2][(col-k)+2] == piece)
                                                fullListCoordinates.add(new Coordinates(row-k, col-k, 2, antip));
                                             if(Board[(row-k)+2][(col-k)+2] == ' ')
                                                fullListCoordinates.add(new Coordinates((row-k)+2, (col-k)+2, 4, antip));
                                          } 
                                    }
                                 //reset
                                 counter = 0;
                                 break iterate;
                              }
                              if(Board[row-k][col-k] != ' ' && Board[row-k][col-k] != piece)
                              {
                                 used.Board[row-k][col-k] = 'B';
                                 //reset
                                 counter = 0;
                                 break iterate;
                              }
                              if(Board[row-k][col-k] ==  piece) //if it finds the piece it's looking for
                              {
                                   counter++;
                                   used.Board[row-k][col-k] = 'U';
                              }
                              //if it no longer finds the next piece in the chain, then there is either an empty slot, or it's occupied by the opponent(blocked)
                              if(1 < counter && counter < 4)                                    
                              {
                                       /*Find a chain w/ an empty slot at end*/
                                       if((row-(k+1) >= 0) && (col-(k+1) >= 0))//bounds checking
                                       {
                                          //check if the next corresponding chain slot is empty, true: we record info, otherwise fruitless
                                          if(Board[row-(k+1)][col-(k+1)] == ' ')
                                          {
                                                //take coordinates of empty slot to place piece
                                                //chain information recorded                          
                                                chains.add(counter);
                                                //record to priorites
                                                if(counter == 3)
                                                   fullListCoordinates.add(new Coordinates(row-(k+1), col-(k+1), 1, antip));
                                                else //counter == 2
                                                   fullListCoordinates.add(new Coordinates(row-(k+1), col-(k+1), 5, antip));
                                                accounted = true; 
                                          }  
                                       }
                                       
                                       /*Find a chain w/ an empty slot at reverse end*/
                                       if((row+(2-1) < ROW) && (col+(2-1) < COL)) //bounds checking
                                       {
                                          //check reverse slot
                                          if(Board[row+1][col+1] == ' ') //no bounds checking b/c not poss to go out of bounds
                                          {
                                                //take coordinates of empty slot to place piece
                                                //chain information recorded                          
                                                chains.add(counter);
                                                //record to priorites
                                                if(counter == 3)
                                                   fullListCoordinates.add(new Coordinates(row+1, col+1, 1, antip));
                                                else //counter == 2
                                                   fullListCoordinates.add(new Coordinates(row+1, col+1, 5, antip));
                                                accounted = true;
                                          }
                                       }
                                    if(accounted == true) //if useful chain accounted
                                    {
                                       //record used
                                       for(int m = 0; m < counter; m++)
                                       {
                                          //record used coordinates on board
                                          used.Board[row-m][col-m] = 'U';
                                       }
                                       accounted = false;
                                       
                                       //reset
                                       counter = 0;
                                       break iterate;
                                    }
                               } 
                           }//k 
                       }//ROW
                   }//COL
         }//diagLftFinish
         
         //vv Priority 6 & 7. (!) record the coordinates to the fullList of Coordinates. 
         public void findCoordsGap(char piece, char[][] gameboard, boolean antip)
         {
            //create a copy of the gameBoard, so that we may distort it.
            char[][] copy  = gameboard;
            
            //if we don't find any it will be that all lists == 0;
            //6*7
            cr8HorzGap(piece, copy);
            copy = flipBoard(copy);
            cr8HorzGap(piece, copy);
            //record from copy of board
            for(int row = 0; row < copy.length; row++)
               for(int col = 0; col < copy[0].length; col++)
               {
                  if(copy[row][col] == 'H')
                     fullListCoordinates.add(new Coordinates(row, col, 6, antip));
                  if(copy[row][col] == 'P')
                     fullListCoordinates.add(new Coordinates(row, col, 7, antip));
               }
            
            
            //reset
            copy = flipBoard(copy);
            
            //vert builder single existing block
            //no longer needed
            //vertBuilder(piece, copy);
            
            
            for(int row = 0; row < copy.length; row++)
               for(int col = 0; col < copy[0].length; col++)
               {
                  if(copy[row][col] == 'P')
                     fullListCoordinates.add(new Coordinates(row, col, 7, antip));
               }
               
            //reset
            copy = gameboard;   
               
            //Drt
            cr8DRtGap(piece, copy);
            copy = flipBoard(copy);
            copy = flipDwnBoard(copy);
            cr8DRtGap(piece, copy);
             
            //move back to normal
            copy = flipBoard(copy);
            copy = flipDwnBoard(copy);
            
            //record from copy of board
            for(int row = 0; row < copy.length; row++)
               for(int col = 0; col < copy[0].length; col++)
               {
                  if(copy[row][col] == 'H')
                     fullListCoordinates.add(new Coordinates(row, col, 6, antip));
                  if(copy[row][col] == 'P')
                     fullListCoordinates.add(new Coordinates(row, col, 7, antip));
               }
            
            //reset
            copy = gameboard;
            
            cr8DLftGap(piece, copy);
            copy = flipBoard(copy);
            copy = flipDwnBoard(copy);
            cr8DLftGap(piece, copy);  
            
            //move back to normal orientation
            copy = flipBoard(copy);
            copy = flipDwnBoard(copy);
            
            for(int row = 0; row < copy.length; row++)
               for(int col = 0; col < copy[0].length; col++)
               {
                  if(copy[row][col] == 'H')
                     fullListCoordinates.add(new Coordinates(row, col, 6, antip));
                  if(copy[row][col] == 'P')
                     fullListCoordinates.add(new Coordinates(row, col, 7, antip));
               }
               
                       
         }
         /*The process of these helper methods is
         1. They find viable one sided gaps, save these gap spots on the copy of the gameBoard they receive as args.
         2.The copy of the gameBoard is flipped. 
         3. It passes through the same method again, this time with the flipped gameboard.
         4. (*)Still needs to check if the pieces are placeable. The placeability could happen here, or it can be done when all the coordinates are sorted by priority,
            they can be checked for placeability, and put in their correct spot. The latter method would be better. 
         5. When it encounters the 'P' on the flipped gameBoard, it checks if it's double ended open and marks these slots approp.
         6. The gameboard, is flipped back, and another method parses through the board, and records the coordinates appropriately
            'P' || 'H'.
         7. The copy of the gameboard is cleared. 
         8. Repeat for next direction.
         */
         //Priority 6 and 7. checks board for pieces. then checks corr. slots in chain if empty or one's piece.
         //|x| |P| |.....| |x| |H| |
         
         //vv Checks next 3 slots for emptyness or piece, to get slot +2 over to return. 'P' == slot for one sided, 'H' for two sided.
         private void cr8HorzGap(char piece, char[][] gameBoard) //create gaps. 
         {
                boolean passed = true;
            
                for(int row = ROW-1; row >= 0; row--)
                {
                  for(int col = 0; col <= COL - 4; col++)
                  {
                     if(gameBoard[row][col] == piece)
                     {
                        for(int k = 1; k < 4; k++)
                        {
                           if((gameBoard[row][col+k] != ' ' && //check if following 3 slots are empty or spaces
                               gameBoard[row][col+k] != piece) ||
                              (gameBoard[row][col+k] != 'P' &&
                               gameBoard[row][col+k] != 'H'))
                           {
                              passed = false;
                           }
                        }
                        if(gameBoard[row][col+2] == ' ' && passed)
                           gameBoard[row][col+2] = 'P';   
                     }
                     passed = false;
                          
                     if(gameBoard[row][col] == 'P')
                     {
                        for(int k = 1; k < 4; k++)
                        {
                           if((gameBoard[row][col+k] != ' ' && //check if following 3 slots are empty or spaces
                               gameBoard[row][col+k] != piece) ||
                              (gameBoard[row][col+k] != 'P' &&
                               gameBoard[row][col+k] != 'H'))
                           {
                              passed = false;
                           }
                        }
                        if(gameBoard[row][col+2] == piece && passed) //check if Potential piece has 
                                 gameBoard[row][col] = 'H';
                     }
                   }
                 }                   
         }
         /*
         //There is no more build single block.
         //single block build only exists for vert. and it's the last method here
         
         vv This method checks for one's piece to place on top of it... don't know why it starts at row = 3.. 
         private void vertBuilder(char piece, char[][] gameBoard)
         {
            
            for(int col = 0; col < COL; col++)
            {
               for(int row = 3; row < ROW; row++)
               {
                  if(gameBoard[row][col] == piece && gameBoard[row-1][col] == ' ') //can build even on ones that have more than 1 piece on them.
                  {
                     gameBoard[row-1][col] = 'P';
                     break;
                  }
               }
            }
         }
         */
         
         private void cr8DRtGap(char piece, char[][] gameBoard) //create gaps. 
         {
                boolean passed = true;
            
                for(int col = 0; col <= COL-4; col++)
                {
                  for(int row = ROW-1; row >= 0 + (4-1); row--)
                  {
                     if(gameBoard[row][col] == piece)
                     {
                        for(int k = 1; k < 4; k++)
                        {
                           if((gameBoard[row-k][col+k] != ' ' && //check if following 3 slots are empty or spaces
                               gameBoard[row-k][col+k] != piece) ||
                              (gameBoard[row-k][col+k] != 'P' &&
                               gameBoard[row-k][col+k] != 'H'))
                           {
                              passed = false;
                           }
                        }
                        if(gameBoard[row-2][col+2] == ' ' && passed)
                           gameBoard[row-2][col+2] = 'P';   
                     }
                     passed = false;
                          
                     if(gameBoard[row][col] == 'P')
                     {
                        for(int k = 1; k < 4; k++)
                        {
                           if((gameBoard[row-k][col+k] != ' ' && //check if following 3 slots are empty or spaces
                               gameBoard[row-k][col+k] != piece) ||
                              (gameBoard[row-k][col+k] != 'P' &&
                               gameBoard[row-k][col+k] != 'H'))
                           {
                              passed = false;
                           }
                        }
                        //since the board is reversed
                        if(gameBoard[row-2][col+2] == piece && passed) //check if Potential piece has 
                           gameBoard[row][col] = 'H';
                     }
                   }
                 }                   
         }
         
         private void cr8DLftGap(char piece, char[][] gameBoard) //create gaps. 
         {
                boolean passed = true;
            
                for(int col = COL-1; col >= 0 + (4-1); col--)
                {
                  for(int row = ROW-1; row >= 0 + (4-1); row--)
                  {
                     if(gameBoard[row][col] == piece)
                     {
                        for(int k = 1; k < 4; k++)
                        {
                           if((gameBoard[row-k][col-k] != ' ' && //check if following 3 slots are empty or spaces
                               gameBoard[row-k][col-k] != piece) ||
                              (gameBoard[row-k][col-k] != 'P' &&
                               gameBoard[row-k][col-k] != 'H'))
                           {
                              passed = false;
                           }
                        }
                        if(gameBoard[row-2][col-2] == ' ' && passed)
                           gameBoard[row-2][col-2] = 'P';   
                     }
                     passed = false;
                          
                     if(gameBoard[row][col] == 'P')
                     {
                        for(int k = 1; k < 4; k++)
                        {
                           if((gameBoard[row-k][col-k] != ' ' && //check if following 3 slots are empty or spaces
                               gameBoard[row-k][col-k] != piece) ||
                              (gameBoard[row-k][col-k] != 'P' &&
                               gameBoard[row-k][col-k] != 'H'))
                           {
                              passed = false;
                           }
                        }
                        if(gameBoard[row-2][col-2] == piece && passed) //check if Potential piece has 
                           gameBoard[row][col] = 'H';
                     }
                   }
                 }                   
         }
         
         //Flips board horizontally(mirrored)
         private char[][] flipBoard(char[][] theBoard)
         {
            int len = theBoard[0].length; //col
            int height = theBoard.length; //row
            char[][] flipped = new char[height][len];
            
            for(int i = 0; i < len; i++)//col
               for(int k = 0; k < height; k++)//row
                  flipped[k][(len-1)-i] = theBoard[k][i];
                  
                  
            return flipped;   
         }
         
         //Flips board vertically (mirrored)
         private char[][] flipDwnBoard(char[][] theBoard)
         {
            int length = theBoard[0].length; //col
            int height = theBoard.length; //row
            
            char[][] flipped = new char[height][length];
            
            for(int i = 0; i < length; i++)//col
               for(int k = 0; k < height; k++)//row
                  flipped[(height-1)-k][i] = theBoard[k][i];
                  
                  
            return flipped;   
         }
         
         private char[][] clear(char[][] theBoard)
         {
            char[][] cleared = theBoard;
            
            for(int i = 0; i < cleared.length; i++)
               for(int j = 0; j < cleared[0].length; j++)
                  cleared[i][j] = ' ';
                  
            return cleared;
         }
         /*
         private void record(char[][] gameBoard)
         {
            for(int i = 0; i < gameBoard.length(); i++)
            {
               for(int k = ; ; )
               {
                  ;
               }
            }
         }
         */
         
         
         /*priority 9 & 10 which are 
            so basically it checks if the next four slots are empty or with a piece at the end.
            ...when it has a piece at the end, then it must place it at the right-most empty slots, and it assigned priority 8. O/W if 
            ....following four slots are empty, they are assigned priority 9.
            ->new Priority 8 will record the empty slot at either left or right.
			->will not check if there is a piece at the end, b/c priority 6 and 7 will do that. 
			
			NEW: So, will check only for empty slots. The horizontal one will be first and different from the rest.
			There will still be priority 8 and 9. 
			1: horz will check for double sided empty slots: first it will check for 4 empty slots, mark starting spots w/ p. then flip and checks for either p or empty slots for the next four empty slots, then if viable, if starting point is p, it is marked as 'H';
			2. all other dir checks will check for one sided empty slots, and mark the starting point only, for placement.
			3. H's will get higher priority.
			4. P's will get lower priority. 
         
         //priority 11 is now existent. it is a random slot, when no other slots are available. it is added directly in code, actually shouldn't exist, b/c vert will take care of it. 
			
			| |P| | |...| | | |$| | | |. 
         */        
         private void priority9Nd10(char piece, boolean anti)
         {
            char[][] copy = CFBoard.subBoard;
            char[][] og = copy;
            //The methods, auto-record the coordinates.
            //horizontal
            horz9Nd10(copy, og);
            
            //copy = flipBoard(copy); //not a mergance, but an overwrite.
            //og = flipBoard(og);//flip og, so that it works.
           
            horz9Nd10(flipBoard(copy), flipBoard(og));
            
            //flip board to normal
            //copy = flipBoard(copy);
           
            //record
            for(int row = 0; row < copy.length; row++)
               for(int col = 0; col < copy[0].length; col++)
               {
                  if(copy[row][col] == 'H')
                     fullListCoordinates.add(new Coordinates(row, col, 9, anti));
                  if(copy[row][col] == 'T')
                     fullListCoordinates.add(new Coordinates(row, col, 10, anti));
                  if(copy[row][col] == 'P')
                     fullListCoordinates.add(new Coordinates(row, col, 10, anti));
               }
            
            //reset
            copy = og;
            //print
            
            //vert ! self records
            vert9Nd10(piece, copy, anti);
            
         
         }
         
         private void horz9Nd10(char[][] cpy, char[][] original)
         {
            
            boolean viable = true;
            
            //go from left to right, until you reach the 4th to last col
            for(int col = 0; col <= COL - 4; col++)
               //start from the bottom row
                for(int row = ROW-1; row >= 0; row--)
                     //look for piece to start
                     if(cpy[row][col] == ' ' || cpy[row][col] == 'P' || cpy[row][col] == 'T'|| cpy[row][col] == 'H')
                     {
                        for(int k = 1; k < 4; k++)
                        {
                           //the question is, whether I would create more chances for myself, if I place at every other slot.
                           if((cpy[row][col+k] != ' ') && (cpy[row][col+k] != 'P') && (cpy[row][col+k] != 'T') && (cpy[row][col+k] != 'H'))
                              viable = false;
                        } 
                           if(viable)
                           {
                               //this is where if all empty, it can be made different by the above conditional, 
                              //_and the add statement, would be modified.
                              //one for land is H
                              if(cpy[row][col] == ' ' && (new Coordinates(row, col).getPlaceable(original) == true))
                              {  
                                 if(new Coordinates(row, col+1).getPlaceable(original) == true)
                                 {
                                    cpy[row][col+1] = 'T'; 
                                    cpy[row][col] = 'P';
                                 }
                                   
                              }
                              else if(cpy[row][col] == 'T' && (new Coordinates(row, col).getPlaceable(original) == true) &&
                                 cpy[row][col+1] != 'P' && (new Coordinates(row, col+1).getPlaceable(original) == true))
                              {
                                    cpy[row][col+1] = 'T'; 
                                    cpy[row][col] = 'P';
                              }
                                    
                              else if(cpy[row][col] == 'T' && (new Coordinates(row, col).getPlaceable(original) == true) &&
                                 cpy[row][col+1] == 'P' && (new Coordinates(row, col+1).getPlaceable(original) == true))
                                    cpy[row][col+1] = 'H';
                                 
                              else if(cpy[row][col] == 'H' && (new Coordinates(row, col).getPlaceable(original) == true))
                                 if(new Coordinates(row, col+1).getPlaceable(original) == true)
                                    cpy[row][col+1] = 'T';
                              
                           }
                     }     
                            
         }//method
         
         //rowXcol
         //check if empty space, no need to check. 
         //(*)It's important to note that although this is made left to right, if there are avoidCol's then it will place for the best one. 
         //(!)(!) Ideally, the avoidable col's will be like levels. so if any of the coords pass level one, that Coord is placed, rather than one coord test against all of them. 
         //perhaps, it would be better to check for vert pieces which have either the left or right sides open for diag, but it actually doesn't matter. Perhaps I can add a random outlet here. 
         private void vert9Nd10(char piece, char[][] gameboard, boolean anti)
         {
            boolean viable = true;
         
            for(int iteration = 1; iteration <=2; iteration++)
            {
               for(int col = 0; col < COL; col++) //change to: col <= COL-1
               {
                  for(int row = ROW-1; row >= 0 + (4-1); row--)
                  {  
                     if(iteration == 1)
                        if(gameboard[row][col] == piece)
                           for(int k = 1; k < 4; k++)
                              if(gameboard[row-k][col] != ' ')
                                 viable = false;
                                
                     if(iteration == 2)
                        if(gameboard[row][col] == ' ')
                           for(int k = 1; k < 4; k++)
                              if(gameboard[row-k][col] != ' ')
                                 viable = false;
                     
                     if(viable)
                     {
                        if(iteration == 1)
                           fullListCoordinates.add(new Coordinates(row, col, 7, anti)); //comes after gaps basically.
                        if(iteration == 2)
                           fullListCoordinates.add(new Coordinates(row, col, 10, anti));     
                     }      
                   }
                }
                viable = true;
             }
         }
         /*
         private void diagRt8Nd9(char piece, char[][] gameboard, boolean anti)
         {
               boolean viable = true;
               
               for(int col = 0; col <= COL-4; col++)
                  for(int row = ROW-1; row >= 0 + (4-1); row--)
                     if(gameboard[row][col] == ' ')
                     {
                        for(int k = 1; k < 4; k++)
                           if(gameboard[row-k][col+k] != ' ')
                                 viable = false;
                        
                        if(viable)
                           if(gameboard[row-3][col+3] == piece) //if last slot is a piece,
                              fullListCoordinates.add(new Coordinates(row-2, col+2, 8, anti);
                           
                        
                     }
                              
                              
                              
         }
         
         private void diagLft8Nd9(char piece, char[][] gameboard, boolean anti)
         {
            boolean viable = true;
            
            for(int col = COL-1; >= 0 + (4-1); col++)
                  for(int row = ROW-1; row >= 0 + (4-1); row--)
                     if(gameboard[row][col] == ' ')
                     {
                        for(int k = 1; k < 4; k++)
                           if(gameboard[row-k][col-k] != ' ' && gameboard[row-k][col-k] != piece)
                              viable = false;
                        
                        if(viable)
                           if(gameboard[row-3][col-3] == piece)
                              fullListCoordinates.add(new Coordinates(row-2, col-2, 8, anti);
                           else
                              fullListCoordinates.add(new Coordinates(row-2, col-2, 9, anti);
                      }
         }
         */
         /*
         public int[][] emptyNdGap() //finds empty and gaps that are overlapping. These spots take priority. 
         {
            int[][] e_gCoords = new int[2][20];
            int e_gSize = 0;
            
            boolean found = false;
            
            for(int i = 0; i < Xgap.size(); i++)
            {
               for(int j = 0; j < Xcoordinate.size(); j++)
               {
                  if(Xcoordinate.get(j) == Xgap.get(i) &&
                     Ycoordinate.get(j) == Ygap.get(i))
                     {
                        e_gCoords[0][e_gSize] = Xgap.get(i);
                        e_gCoords[1][e_gSize] = Ygap.get(i);
                        found = true;
                     }
                }
            }
            
            if(found)
               return e_gCoords;
            else
               return null;
         }*/
         
              
  } // class finish //BoardInfo
}//outermost class
     