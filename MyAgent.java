import java.util.Random;

public class MyAgent extends Agent
{
    Random r;

    /**
     * Constructs a new agent, giving it the game and telling it whether it is Red or Yellow.
     * 
     * @param game The game the agent will be playing.
     * @param iAmRed True if the agent is Red, False if the agent is Yellow.
     */
    public MyAgent(Connect4Game game, boolean iAmRed)
    {
        super(game, iAmRed);
        r = new Random();
    }

    /**
     * The move method is run every time it is this agent's turn in the game. You may assume that
     * when move() is called, the game has at least one open slot for a token, and the game has not
     * already been won.
     * 
     * By the end of the move method, the agent should have placed one token into the game at some
     * point.
     * 
     * After the move() method is called, the game engine will check to make sure the move was
     * valid. A move might be invalid if:
     * - No token was place into the game.
     * - More than one token was placed into the game.
     * - A previous token was removed from the game.
     * - The color of a previous token was changed.
     * - There are empty spaces below where the token was placed.
     * 
     * If an invalid move is made, the game engine will announce it and the game will be ended.
     * 
     */
    public void move() // at least complete this method to make MyAgent work.
    {
        int i = randomMove(); // i is the column we want to put the token in
        int c = myGame.getColumnCount();
        int r = myGame.getRowCount();
        int w = iCanWin();
        int tw = theyCanWin();
        int bw = putBetween();
      
        int midColumn = c/2;
        
        
        
        //if((w>-1)&&(getLowestEmptyIndex(myGame.getColumn(w)))>-1){
        if(w>-1){
        
            moveOnColumn(w);
        
        }
        
        
        else if((tw>-1)&&(getLowestEmptyIndex(myGame.getColumn(tw)))>-1){
        
            moveOnColumn(tw);
        
        }
        
        else if(!myGame.getColumn(midColumn).getSlot(r-1).getIsFilled()){
        
            moveOnColumn(midColumn);
        
        } // to place the token in the middle column of the bottom row
        
        else if((bw>-1)&&(getLowestEmptyIndex(myGame.getColumn(bw)))>-1){
        
            moveOnColumn(bw);
        
        }
        /*
        else if(getLowestEmptyIndex(myGame.getColumn(0))>-1){
            
            moveOnColumn(0);
            
        }
        else if(getLowestEmptyIndex(myGame.getColumn(6))>-1){
            
            moveOnColumn(6);
            
        }*/
        
        
        else moveOnColumn(i);
    }

    /**
     * Drops a token into a particular column so that it will fall to the bottom of the column.
     * If the column is already full, nothing will change.
     * 
     * @param columnNumber The column into which to drop the token.
     */
    public void moveOnColumn(int columnNumber)
    {
        int lowestEmptySlotIndex = getLowestEmptyIndex(myGame.getColumn(columnNumber));   // Find the top empty slot in the column
                                                                                                  // If the column is full, lowestEmptySlot will be -1
        if (lowestEmptySlotIndex > -1)  // if the column is not full
        {
            Connect4Slot lowestEmptySlot = myGame.getColumn(columnNumber).getSlot(lowestEmptySlotIndex);  // get the slot in this column at this index
            if (iAmRed) // If the current agent is the Red player...
            {
                lowestEmptySlot.addRed(); // Place a red token into the empty slot
            }
            else // If the current agent is the Yellow player (not the Red player)...
            {
                lowestEmptySlot.addYellow(); // Place a yellow token into the empty slot
            }
        }
    }

    /**
     * Returns the index of the top empty slot in a particular column.
     * 
     * @param column The column to check.
     * @return the index of the top empty slot in a particular column; -1 if the column is already full.
     */
    public int getLowestEmptyIndex(Connect4Column column) {
        int lowestEmptySlot = -1;
        for  (int i = 0; i < column.getRowCount(); i++)
        {
            if (!column.getSlot(i).getIsFilled())
            {
                lowestEmptySlot = i;
            }
        }
        return lowestEmptySlot;
    }

    /**
     * Returns a random valid move. If your agent doesn't know what to do, making a random move
     * can allow the game to go on anyway.
     * 
     * @return a random valid move.
     */
    public int randomMove()
    {
        int i = r.nextInt(myGame.getColumnCount()); // get random number among the total number of column
        while (getLowestEmptyIndex(myGame.getColumn(i)) == -1)
        {
            i = r.nextInt(myGame.getColumnCount());
        }
        return i;
    }

    /**
     * Returns the column that would allow the agent to win.
     * 
     * You might want your agent to check to see if it has a winning move available to it so that
     * it can go ahead and make that move. Implement this method to return what column would
     * allow the agent to win.
     *
     * @return the column that would allow the agent to win.
     */
    public int iCanWin()
    {
        int c = myGame.getColumnCount();
        int r = myGame.getRowCount();
      
        for(int column = 0;column<c;column++) //
        {
            for(int row = 0;row<r && (row+3<r);row++)
            {
                
                    if(myGame.getColumn(column).getSlot(row+1).getIsRed() && 
                    myGame.getColumn(column).getSlot(row+2).getIsRed() && 
                    myGame.getColumn(column).getSlot(row+3).getIsRed() && 
                    !myGame.getColumn(column).getSlot(row).getIsFilled()) 
                    {
                        return column;
                    } // check in vertical line CASE1
                  
                              
            }
        }
        
        for(int column = 0;(column<c) && (column+3<c);column++) //
        {
            for(int row = 0;row<r;row++)
            {
                
                if(row == r-1)
                {
                    if(myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+1).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row).getIsRed() &&
                        !myGame.getColumn(column+3).getSlot(row).getIsFilled()) 
                        {
                            return column+3;
                        } // check in horizontal line in the last row CASE2
                    
                    else if(myGame.getColumn(column+1).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+3).getSlot(row).getIsRed() &&
                        !myGame.getColumn(column).getSlot(row).getIsFilled())
                        {
                            
                            return column;
                    
                        } // check in horizontal line in the last row CASE6
                        
                    else if(myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+3).getSlot(row).getIsRed() &&
                        !myGame.getColumn(column+1).getSlot(row).getIsFilled())
                        {
                            
                            return column+1;
                    
                        } // check in horizontal line in the last row CASE8
                        
                    else if(myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+1).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+3).getSlot(row).getIsRed() &&
                        !myGame.getColumn(column+2).getSlot(row).getIsFilled())
                        {
                            
                            return column+2;
                    
                        } // check in horizontal line in the last row CASE10
                }
                else if(myGame.getColumn(column).getSlot(row).getIsRed() && 
                    myGame.getColumn(column+1).getSlot(row).getIsRed() && 
                    myGame.getColumn(column+2).getSlot(row).getIsRed() &&
                    !myGame.getColumn(column+3).getSlot(row).getIsFilled() &&
                    myGame.getColumn(column+3).getSlot(row+1).getIsFilled()) 
                    {
                        return column+3;
                    } // check in horizontal line CASE3
                    
                else if(myGame.getColumn(column+1).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+3).getSlot(row).getIsRed() &&
                        !myGame.getColumn(column).getSlot(row).getIsFilled() &&
                        myGame.getColumn(column).getSlot(row+1).getIsFilled())
                        {
                            
                            return column;
                    
                        } // check in horizontal line CASE7
                    
                else if(myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+3).getSlot(row).getIsRed() &&
                        !myGame.getColumn(column+1).getSlot(row).getIsFilled() &&
                        myGame.getColumn(column+1).getSlot(row+1).getIsFilled())
                        {
                            
                            return column+1;
                    
                        } // check in horizontal line CASE9
                        
                else if(myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+1).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+3).getSlot(row).getIsRed() &&
                        !myGame.getColumn(column+2).getSlot(row).getIsFilled() &&
                        myGame.getColumn(column+2).getSlot(row+1).getIsFilled())
                        {
                            
                            return column+2;
                    
                        } // check in horizontal line CASE11
            }
            
        }
       
        
         for(int column = 0;(column<c) &&(column+3<c);column++) //
        {
            for(int row = 0;(row+3<r);row++)
            {
                if(row==r-4)
                {
                
                     if(myGame.getColumn(column+1).getSlot(row+1).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row+2).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsRed() &&
                        !myGame.getColumn(column+3).getSlot(row+3).getIsFilled())
                        
                        {
                            return column+3;
                        } // check in diagonal up left line CASE12.2  
                
                     else if(myGame.getColumn(column+1).getSlot(row+1).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row+2).getIsRed() && 
                        myGame.getColumn(column+3).getSlot(row+3).getIsRed() &&
                        !myGame.getColumn(column).getSlot(row).getIsFilled() &&
                        myGame.getColumn(column).getSlot(row+1).getIsFilled()) 
                        {
                            return column;
                        } // check in diagonal up left line CASE4
                }
                
                
               else if(myGame.getColumn(column+1).getSlot(row+1).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row+2).getIsRed() && 
                        myGame.getColumn(column+3).getSlot(row+3).getIsRed() &&
                        !myGame.getColumn(column).getSlot(row).getIsFilled() &&
                        myGame.getColumn(column).getSlot(row+1).getIsFilled()) 
                        {
                            return column;
                        } // check in diagonal up left line CASE4
                        
                else if(myGame.getColumn(column+1).getSlot(row+1).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row+2).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsRed() &&
                        !myGame.getColumn(column+3).getSlot(row+3).getIsFilled() &&
                        myGame.getColumn(column+3).getSlot(row+4).getIsFilled()) 
                        {
                            return column+3;
                        } // check in diagonal up left line CASE12.1  
                        
                else if(myGame.getColumn(column+3).getSlot(row+3).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row+2).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsRed() &&
                        !myGame.getColumn(column+1).getSlot(row+1).getIsFilled() &&
                        myGame.getColumn(column+1).getSlot(row+2).getIsFilled()) 
                        {
                            return column+1;
                        } // check in diagonal up left line CASE14
                        
               else if(myGame.getColumn(column+3).getSlot(row+3).getIsRed() && 
                        myGame.getColumn(column+1).getSlot(row+1).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsRed() &&
                        !myGame.getColumn(column+2).getSlot(row+2).getIsFilled() &&
                        myGame.getColumn(column+2).getSlot(row+3).getIsFilled()) 
                        {
                            return column+2;
                        } // check in diagonal up left line CASE16
                
            }
            
        }
        
         for(int column = 0;column<c && (column>2); column++) //
        {
            for(int row = 0;(row<r) && (row+3<r);row++)
            {
                if(row==r-4)
                {
                
                     if(myGame.getColumn(column-1).getSlot(row+1).getIsRed() && 
                        myGame.getColumn(column-2).getSlot(row+2).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsRed() &&
                        !myGame.getColumn(column-3).getSlot(row+3).getIsFilled())
                        
                        {
                            return column-3;
                        } // check in diagonal up right line CASE13.2  
                        
                      else if(myGame.getColumn(column-1).getSlot(row+1).getIsRed() && 
                        myGame.getColumn(column-2).getSlot(row+2).getIsRed() && 
                        myGame.getColumn(column-3).getSlot(row+3).getIsRed() &&
                        !myGame.getColumn(column).getSlot(row).getIsFilled()&&
                        myGame.getColumn(column).getSlot(row+1).getIsFilled())
                        {
                            return column;
                        } // check in diagonal up right line CASE5
                
                }
              else if(myGame.getColumn(column-1).getSlot(row+1).getIsRed() && 
                    myGame.getColumn(column-2).getSlot(row+2).getIsRed() && 
                    myGame.getColumn(column-3).getSlot(row+3).getIsRed() &&
                    !myGame.getColumn(column).getSlot(row).getIsFilled()&&
                    myGame.getColumn(column).getSlot(row+1).getIsFilled())
                    {
                        return column;
                    } // check in diagonal up right line CASE5
                  
              else if(myGame.getColumn(column-1).getSlot(row+1).getIsRed() && 
                    myGame.getColumn(column-2).getSlot(row+2).getIsRed() && 
                    myGame.getColumn(column).getSlot(row).getIsRed() &&
                    !myGame.getColumn(column-3).getSlot(row+3).getIsFilled() &&
                    myGame.getColumn(column-3).getSlot(row+4).getIsFilled()) 
                    {
                        return column-3;
                    } // check in diagonal up right line CASE13.1  
                    
              else if(myGame.getColumn(column-3).getSlot(row+3).getIsRed() && 
                    myGame.getColumn(column-2).getSlot(row+2).getIsRed() && 
                    myGame.getColumn(column).getSlot(row).getIsRed() &&
                    !myGame.getColumn(column-1).getSlot(row+1).getIsFilled() &&
                    myGame.getColumn(column-1).getSlot(row+2).getIsFilled()) 
                    {
                        return column-1;
                    } // check in diagonal up right line CASE15  
                    
              else if(myGame.getColumn(column-3).getSlot(row+3).getIsRed() && 
                    myGame.getColumn(column-1).getSlot(row+1).getIsRed() && 
                    myGame.getColumn(column).getSlot(row).getIsRed() &&
                    !myGame.getColumn(column-2).getSlot(row+2).getIsFilled() &&
                    myGame.getColumn(column-2).getSlot(row+3).getIsFilled()) 
                    {
                        return column-2;
                    } // check in diagonal up right line CASE17  
            }
            
        }
        return -1;
    }

    /**
     * Returns the column that would allow the opponent to win.
     * 
     * You might want your agent to check to see if the opponent would have any winning moves
     * available so your agent can block them. Implement this method to return what column should
     * be blocked to prevent the opponent from winning.
     *
     * @return the column that would allow the opponent to win.
     */
    public int theyCanWin()
    {
        int c = myGame.getColumnCount();
        int r = myGame.getRowCount();
      
        
        
        for(int column = 0;(column<c) && (column+3<c);column++) //
        {
            for(int row = 0;row<r;row++)
            {
                
                if(row == r-1)
                {
                    if(!myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsFilled() && 
                        !myGame.getColumn(column+1).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+1).getSlot(row).getIsFilled() && 
                        !myGame.getColumn(column+2).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row).getIsFilled() &&
                        !myGame.getColumn(column+3).getSlot(row).getIsFilled()) 
                        {
                            return column+3;
                        } // check in horizontal line in the last row
                        
                        else if(!myGame.getColumn(column+1).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+1).getSlot(row).getIsFilled() && 
                        !myGame.getColumn(column+2).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row).getIsFilled() && 
                        !myGame.getColumn(column+3).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+3).getSlot(row).getIsFilled() &&
                        !myGame.getColumn(column).getSlot(row).getIsFilled())                                            
                        {
                            
                            return column;
                    
                        } // check in horizontal line in the last row CASE6
                        
                    else if(!myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsFilled() && 
                        !myGame.getColumn(column+2).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row).getIsFilled() && 
                        !myGame.getColumn(column+3).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+3).getSlot(row).getIsFilled() &&
                        !myGame.getColumn(column+1).getSlot(row).getIsFilled())                     
                        {
                            
                            return column+1;
                    
                        } // check in horizontal line in the last row CASE8
                        
                    else if(!myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsFilled() && 
                        !myGame.getColumn(column+1).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+1).getSlot(row).getIsFilled() && 
                        !myGame.getColumn(column+3).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+3).getSlot(row).getIsFilled() &&
                        !myGame.getColumn(column+2).getSlot(row).getIsFilled())
                        
                        {
                            
                            return column+2;
                    
                        } // check in horizontal line in the last row CASE10
                }
                else if(!myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsFilled() && 
                        !myGame.getColumn(column+1).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+1).getSlot(row).getIsFilled() && 
                        !myGame.getColumn(column+2).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row).getIsFilled() &&
                        !myGame.getColumn(column+3).getSlot(row).getIsFilled() &&
                        myGame.getColumn(column+3).getSlot(row+1).getIsFilled()) 
                        {
                            return column+3;
                        } // check in horizontal line CASE3
                    
                else if(!myGame.getColumn(column+1).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+1).getSlot(row).getIsFilled() && 
                        !myGame.getColumn(column+2).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row).getIsFilled() && 
                        !myGame.getColumn(column+3).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+3).getSlot(row).getIsFilled() &&
                        !myGame.getColumn(column).getSlot(row).getIsFilled() &&
                        myGame.getColumn(column).getSlot(row+1).getIsFilled())
                        
                        {
                            
                            return column;
                    
                        } // check in horizontal line CASE7
                    
                else if(!myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsFilled() && 
                        !myGame.getColumn(column+2).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row).getIsFilled() && 
                        !myGame.getColumn(column+3).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+3).getSlot(row).getIsFilled() &&
                        !myGame.getColumn(column+1).getSlot(row).getIsFilled() &&
                        myGame.getColumn(column+1).getSlot(row+1).getIsFilled())
                        
                        {
                            
                            return column+1;
                    
                        } // check in horizontal line CASE9
                        
                else if( !myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsFilled() && 
                        !myGame.getColumn(column+1).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+1).getSlot(row).getIsFilled() && 
                        !myGame.getColumn(column+3).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+3).getSlot(row).getIsFilled() &&
                        !myGame.getColumn(column+2).getSlot(row).getIsFilled() &&
                        myGame.getColumn(column+2).getSlot(row+1).getIsFilled())
                       
                        {
                            
                            return column+2;
                    
                        } // check in horizontal line CASE11
            }
            
        }
       
        
         for(int column = 0;(column<c) &&(column+3<c);column++) //
        {
            for(int row = 0;row<r && (row+3<r);row++)
            {
               if(row==r-4)
                {
                
                     if(!myGame.getColumn(column+1).getSlot(row+1).getIsRed() && 
                        myGame.getColumn(column+1).getSlot(row+1).getIsFilled() && 
                        !myGame.getColumn(column+2).getSlot(row+2).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row+2).getIsFilled() && 
                        !myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsFilled() &&
                        !myGame.getColumn(column+3).getSlot(row+3).getIsFilled())
                        
                        {
                            return column+3;
                        } // check in diagonal up left line CASE12.2  
                
                     else if(!myGame.getColumn(column+1).getSlot(row+1).getIsRed() && 
                        myGame.getColumn(column+1).getSlot(row+1).getIsFilled() && 
                        !myGame.getColumn(column+2).getSlot(row+2).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row+2).getIsFilled() && 
                        !myGame.getColumn(column+3).getSlot(row+3).getIsRed() && 
                        myGame.getColumn(column+3).getSlot(row+3).getIsFilled() &&
                        !myGame.getColumn(column).getSlot(row).getIsFilled() &&
                        myGame.getColumn(column).getSlot(row+1).getIsFilled())
                         
                        {
                            return column;
                        } // check in diagonal up left line CASE4
                }
               else if(!myGame.getColumn(column+1).getSlot(row+1).getIsRed() && 
                    myGame.getColumn(column+1).getSlot(row+1).getIsFilled() && 
                    !myGame.getColumn(column+2).getSlot(row+2).getIsRed() && 
                    myGame.getColumn(column+2).getSlot(row+2).getIsFilled() && 
                    !myGame.getColumn(column+3).getSlot(row+3).getIsRed() && 
                    myGame.getColumn(column+3).getSlot(row+3).getIsFilled() &&
                    !myGame.getColumn(column).getSlot(row).getIsFilled() &&
                    myGame.getColumn(column).getSlot(row+1).getIsFilled())
                     
                    {
                        return column;
                    } // check in diagonal up left line CASE4
               else if(!myGame.getColumn(column+1).getSlot(row+1).getIsRed() && 
                        myGame.getColumn(column+1).getSlot(row+1).getIsFilled() && 
                        !myGame.getColumn(column+2).getSlot(row+2).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row+2).getIsFilled() && 
                        !myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsFilled() &&
                        !myGame.getColumn(column+3).getSlot(row+3).getIsFilled() &&
                        myGame.getColumn(column+3).getSlot(row+4).getIsFilled()) 
                        {
                            return column+3;
                        } // check in diagonal up left line CASE12.1  
                        
                else if(!myGame.getColumn(column+3).getSlot(row+3).getIsRed() && 
                        myGame.getColumn(column+3).getSlot(row+3).getIsFilled() && 
                        !myGame.getColumn(column+2).getSlot(row+2).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row+2).getIsFilled() && 
                        !myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsFilled() &&
                        !myGame.getColumn(column+1).getSlot(row+1).getIsFilled() &&
                        myGame.getColumn(column+1).getSlot(row+2).getIsFilled()) 
                        {
                            return column+1;
                        } // check in diagonal up left line CASE14
                        
               else if(!myGame.getColumn(column+3).getSlot(row+3).getIsRed() && 
                        myGame.getColumn(column+3).getSlot(row+3).getIsFilled() && 
                        !myGame.getColumn(column+1).getSlot(row+1).getIsRed() && 
                        myGame.getColumn(column+1).getSlot(row+1).getIsFilled() && 
                        !myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsFilled() &&
                        !myGame.getColumn(column+2).getSlot(row+2).getIsFilled() &&
                        myGame.getColumn(column+2).getSlot(row+3).getIsFilled()) 
                        {
                            return column+2;
                        } // check in diagonal up left line CASE16
            }
            
        }
        
         for(int column = 0;column<c && column>2; column++) //
        {
            for(int row = 0;(row+3<r);row++)
            {
               if(row==2)
                {
                
                     if(!myGame.getColumn(column-1).getSlot(row+1).getIsRed() && 
                        myGame.getColumn(column-1).getSlot(row+1).getIsFilled() && 
                        !myGame.getColumn(column-2).getSlot(row+2).getIsRed() && 
                        myGame.getColumn(column-2).getSlot(row+2).getIsFilled() && 
                        !myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsFilled() &&
                        !myGame.getColumn(column-3).getSlot(row+3).getIsFilled())
                        
                        {
                            return column-3;
                        } // check in diagonal up right line CASE13.2  
                        
                     else if(!myGame.getColumn(column-1).getSlot(row+1).getIsRed() && 
                        myGame.getColumn(column-1).getSlot(row+1).getIsFilled() && 
                        !myGame.getColumn(column-2).getSlot(row+2).getIsRed() && 
                        myGame.getColumn(column-2).getSlot(row+2).getIsFilled() && 
                        !myGame.getColumn(column-3).getSlot(row+3).getIsRed() && 
                        myGame.getColumn(column-3).getSlot(row+3).getIsFilled() &&
                        !myGame.getColumn(column).getSlot(row).getIsFilled()&&
                        myGame.getColumn(column).getSlot(row+1).getIsFilled())
                        {
                            return column;
                        } // check in diagonal up right line CASE5
                    
                }
                else if(!myGame.getColumn(column-1).getSlot(row+1).getIsRed() && 
                    myGame.getColumn(column-1).getSlot(row+1).getIsFilled() && 
                    !myGame.getColumn(column-2).getSlot(row+2).getIsRed() && 
                    myGame.getColumn(column-2).getSlot(row+2).getIsFilled() && 
                    !myGame.getColumn(column-3).getSlot(row+3).getIsRed() && 
                    myGame.getColumn(column-3).getSlot(row+3).getIsFilled() &&
                    !myGame.getColumn(column).getSlot(row).getIsFilled()&&
                    myGame.getColumn(column).getSlot(row+1).getIsFilled())
                    {
                        return column;
                    } // check in diagonal up right line CASE5
                    
                    
               else if(!myGame.getColumn(column-1).getSlot(row+1).getIsRed() && 
                        myGame.getColumn(column-1).getSlot(row+1).getIsFilled() && 
                        !myGame.getColumn(column-2).getSlot(row+2).getIsRed() && 
                        myGame.getColumn(column-2).getSlot(row+2).getIsFilled() && 
                        !myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsFilled() &&
                        !myGame.getColumn(column-3).getSlot(row+3).getIsFilled() &&
                        myGame.getColumn(column-3).getSlot(row+4).getIsFilled()) 
                        {
                            return column-3;
                        } // check in diagonal up right line CASE13.1  
                    
               else if(!myGame.getColumn(column-3).getSlot(row+3).getIsRed() && 
                         myGame.getColumn(column-3).getSlot(row+3).getIsFilled() && 
                        !myGame.getColumn(column-2).getSlot(row+2).getIsRed() && 
                        myGame.getColumn(column-2).getSlot(row+2).getIsFilled() && 
                        !myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsFilled() &&
                        !myGame.getColumn(column-1).getSlot(row+1).getIsFilled() &&
                        myGame.getColumn(column-1).getSlot(row+2).getIsFilled()) 
                        {
                            return column-1;
                        } // check in diagonal up right line CASE15  
                    
               else if(!myGame.getColumn(column-3).getSlot(row+3).getIsRed() && 
                        myGame.getColumn(column+3).getSlot(row+3).getIsFilled() && 
                        !myGame.getColumn(column-1).getSlot(row+1).getIsRed() && 
                        myGame.getColumn(column-1).getSlot(row+1).getIsFilled() && 
                        !myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsFilled() &&
                        !myGame.getColumn(column-2).getSlot(row+2).getIsFilled() &&
                        myGame.getColumn(column-2).getSlot(row+3).getIsFilled()) 
                        {
                            return column-2;
                        } // check in diagonal up right line CASE17
            }
            
        }
        
        for(int column = 0;column<c;column++) //
        {
            for(int row = 0;row+3<r;row++)
            {
                
                 
                    if(!myGame.getColumn(column).getSlot(row+1).getIsRed() && 
                    myGame.getColumn(column).getSlot(row+1).getIsFilled() && 
                    !myGame.getColumn(column).getSlot(row+2).getIsRed() && 
                    myGame.getColumn(column).getSlot(row+2).getIsFilled() && 
                    !myGame.getColumn(column).getSlot(row+3).getIsRed() && 
                    myGame.getColumn(column).getSlot(row+2).getIsFilled() && 
                    !myGame.getColumn(column).getSlot(row).getIsFilled()) 
                    {
                        return column;
                    } // check in vertical line
                  
                
                
            }
        }
        return -1;
    }
    
    public int putBetween()
    {  
        int c = myGame.getColumnCount();
        int r = myGame.getRowCount();
      
        for(int column = 0;(column+2)<c;column++) //
        {
            for(int row = 0;row<r;row++)
            {
                if(row==r-1)
                {
                    if(myGame.getColumn(column).getSlot(row).getIsRed() && 
                         myGame.getColumn(column+2).getSlot(row).getIsRed() &&
                        !myGame.getColumn(column+1).getSlot(row).getIsFilled()) 
                            {
                                return column+1;
                            } 
                    else if(!myGame.getColumn(column).getSlot(row).getIsRed() && 
                            myGame.getColumn(column).getSlot(row).getIsFilled() &&
                            !myGame.getColumn(column+2).getSlot(row).getIsRed() && 
                            myGame.getColumn(column+2).getSlot(row).getIsFilled() &&
                            !myGame.getColumn(column+1).getSlot(row).getIsFilled())
                            {
                                return column+1;
                            }
                }
                else if(myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row).getIsRed() &&
                        !myGame.getColumn(column+1).getSlot(row).getIsFilled() &&
                        myGame.getColumn(column+1).getSlot(row+1).getIsFilled())
                        {
                            return column+1;
                        } 
                else if(!myGame.getColumn(column).getSlot(row).getIsRed() && 
                        myGame.getColumn(column).getSlot(row).getIsFilled() &&
                        !myGame.getColumn(column+2).getSlot(row).getIsRed() && 
                        myGame.getColumn(column+2).getSlot(row).getIsFilled() &&
                        !myGame.getColumn(column+1).getSlot(row).getIsFilled() &&
                        myGame.getColumn(column+1).getSlot(row+1).getIsFilled())
                        {
                            return column+1;
                        }
            }
            
        }
        
        return -1;
    }
    
    /**
     * Returns the name of this agent.
     *
     * @return the agent's name
     */
    public String getName()
    {
        return "God";
    }
}
