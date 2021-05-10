import BasicIO.*;                // for IO classes
import static BasicIO.Formats.*; // for field formats
import static java.lang.Math.*;  // for math constants and functions
import java.util.LinkedList;
import java.util.Arrays; 

/*Rimpy Amal Saha
 * Muditaa Annauth
 */                                                     

public class ChessMoves {
  
  private char pawn,bishop,knight,queen,rook,king;             // char chess pieces of the current player
  private char o_pawn,o_bishop,o_knight,o_queen,o_rook,o_king; // char chess pieces of the opponent of the current player
  private final int  kingsWorth = 9000;
  private final int queensWorth=900;
  private final int rooksWorth=500;
  private final int bishopsWorth=300;
  private final int knightsWorth=300;
  private final int pawnsWorth=100;
  private Pieces [ ] [ ] board; 
  private int [ ] [ ] positions = { {-1,-1} , {-1,1}, {1,1}, {1,-1} };                              // list of directions a bishop can travel towards 
  private int [ ] [ ] positions1 = { {-1,0} , {0,1}, {1,0}, {0,-1} };                              // list of directions a rook can travel towards
  private int [ ] [ ] moves = { {-2,-1}, {-2,1}, {-1,2}, {1,2}, {2,1}, {2,-1}, {-1,-2}, {1,-2} };  // list of directions a knight can travel towards
  private int [ ] [ ] moves1 = { {-1,-1} , {-1,1}, {1,1}, {1,-1}, {-1,0} , {0,1}, {1,0}, {0,-1} }; // list of directions a queen or king can travel towards
  public int king_x,king_y;      // position of king on the board 
  public LinkedList<String> list_moves; 
  public LinkedList<Character> l; 
  public LinkedList<Pieces> list; 
  
  public ChessMoves ( ) {
    
  };  // constructor
  
  public void check_side ( String a ) {
    
    /** Depending on whose turn it is, the chess pieces will be assigned to the current player and its opponent to help decide on which
      * moves are possible in the board                              */
    
    if ( a == "white" ) {
      pawn = 'p'; 
      bishop = 'b'; 
      knight = 'n'; 
      queen = 'q'; 
      rook = 'r'; 
      king = 'k';
      o_pawn = 'P'; 
      o_bishop = 'B'; 
      o_knight = 'N'; 
      o_queen = 'Q'; 
      o_rook = 'R'; 
      o_king = 'K';
    }
    
    else if  ( a == "black" ) {
      pawn = 'P'; 
      bishop = 'B'; 
      knight = 'N'; 
      queen = 'Q'; 
      rook = 'R'; 
      king = 'K';
      o_pawn = 'p'; 
      o_bishop = 'b'; 
      o_knight = 'n'; 
      o_queen = 'q'; 
      o_rook = 'r'; 
      o_king = 'k';
    }
    
  };  //Check_side
  
  /** iterate though the board */ 
  // side = white or black 
  
  public int position (String side, int i, int j, char piece)//Some pieces are good in certain places while, like pawn promotion
  {
    if (side=="white")//A white piece 
    {
      int[][] kings_position={{-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-20,-30,-30,-40,-40,-30,-30,-20},
        {-10,-20,-20,-20,-20,-20,-20,-10},
        { 20, 20, 0 , 0 , 0 , 0 , 20, 20},
        { 20, 30, 10, 0 , 0 , 10, 30, 20}};//All these take care of what a piece is worth at which point
      int[][] queens_position={{-20,-10,-10, -5, -5,-10,-10,-20},
        {-10, 0 , 0 , 0 , 0 , 0 , 0 ,-10},
        {-10, 0 , 5 , 5 , 5 , 5 , 0 ,-10},
        {-5 ,0,5,5,5,5,0,5},
        { 0 ,0,5,5,5,5,0,-5},
        {-10,5,5,5,5,5,0,-10},
        {-10,0,5,0,0,0,0,-10},
        {-20,-10,-10,-5,-5,-10,-10,-20}};
      int[][] rooks_position= {{0,0,0,0,0,0,0,0},
        {5,10,10,10,10,10,10,10,5},
        {-5,0,0,0,0,0,0,-5},
        {-5,0,0,0,0,0,0,-5},
        {-5,0,0,0,0,0,0,-5},
        {-5,0,0,0,0,0,0,-5},
        {-5,0,0,0,0,0,0,-5},
        {0,0,0,5,5,0,0,0}};
      int[][] bishops_position={{-20,-10,-10,-10,-10,-10,-10,-20},
        {-10,0,0,0,0,0,0,-10},
        {-10,0,5,10,10,5,0,-10},
        {-10,5,5,10,10,5,5,-10},
        {-10,0,10,10,10,10,0,-10},
        {-10,10,10,10,10,10,10,-10},
        {-10,5,0,0,0,0,5,-10},
        {-20,-10,-10,-10,-10,-10,-10,-20}};
      int[][] knights_position={{-50,-40,-30,-30,-30,-30,-40,-50},
        {-40,-20,0,0,0,0,-20,-40},
        {-30,0,10,15,15,10,0,-30},
        {-30,5,15,20,20,15,5,-30},
        {-30,0,15,20,20,15,0,-30},
        {-30,5,10,15,15,10,5,-30},
        {-40,-20,0,5,5,0,-20,-40},
        {-50,-40,-30,-30,30,-30,-40,-50}};
      int[][] pawns_position= {{0,0,0,0,0,0,0,0},
        {50,50,50,50,50,50,50,50},
        {10,10,20,30,30,20,10,10},
        {5,5,10,25,25,10,5,5},
        {0,0,0,20,20,0,0,0},
        {5,-5,-10,0,0,-10,-5,5},
        {5,10,10,-20,-20,10,10,5},
        {0,0,0,0,0,0,0,0}};
      
      if(piece==pawn)
        return (pawnsWorth+pawns_position[i][j]); //add the worth to its positional value
      else if(piece==knight)
        return (knightsWorth+knights_position[i][j]);
      else if(piece==bishop)
        return (bishopsWorth+bishops_position[i][j]);
      else if(piece==rook)
        return (rooksWorth+rooks_position[i][j]);
      else if(piece==queen)
        return (queensWorth+queens_position[i][j]);
      else if(piece==king)
        return (kingsWorth+kings_position[i][j]);
      
    }
    
    else if(side == "black")
    {
      int[][] kings_position={{20, 30, 10, 0 , 0 , 10, 30, 20},
        {20, 20, 0 , 0 , 0 , 0 , 20, 20},
        {-10,-20,-20,-20,-20,-20,-20,-10},
        {-20,-30,-30,-40,-40,-30,-30,-20},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        { -30,-40,-40,-50,-50,-40,-40,-30}};
      int[][] queens_position={{-20,-10,-10,-5,-5,-10,-10,-20},
        {-10,0,5,0,0,0,0,-10},
        {-10,5,5,5,5,5,0,-10},
        { 0 ,0,5,5,5,5,0,-5},
        {-5 ,0,5,5,5,5,0,5},
        {-10, 0 , 5 , 5 , 5 , 5 , 0 ,-10},
        {-10, 0 , 0 , 0 , 0 , 0 , 0 ,-10},
        {-20,-10,-10, -5, -5,-10,-10,-20}};
      int[][] rooks_position= {{0,0,0,5,5,0,0,0},
        {-5,0,0,0,0,0,0,-5},
        {-5,0,0,0,0,0,0,-5},
        {-5,0,0,0,0,0,0,-5},
        {-5,0,0,0,0,0,0,-5},
        {-5,0,0,0,0,0,0,-5},
        {5,10,10,10,10,10,10,10,5},
        {0,0,0,0,0,0,0,0}};
      int[][] bishops_position={{-20,-10,-10,-10,-10,-10,-10,-20},
        {-10,5,0,0,0,0,5,-10},
        {-10,10,10,10,10,10,10,-10},
        {-10,0,10,10,10,10,0,-10},
        {-10,5,5,10,10,5,5,-10},
        {-10,0,5,10,10,5,0,-10},
        {-10,0,0,0,0,0,0,-10},
        {-20,-10,-10,-10,-10,-10,-10,-20}};
      int[][] knights_position={{-50,-40,-30,-30,30,-30,-40,-50},
        {-40,-20,0,5,5,0,-20,-40},
        {-30,5,10,15,15,10,5,-30},
        {-30,0,15,20,20,15,0,-30},
        {-30,5,15,20,20,15,5,-30},
        {-30,0,10,15,15,10,0,-30},
        {-40,-20,0,0,0,0,-20,-40},
        {-50,-40,-30,-30,-30,-30,-40,-50}};
      int[][] pawns_position= {{0,0,0,0,0,0,0,0},
        {5,10,10,-20,-20,10,10,5},
        {5,-5,-10,0,0,-10,-5,5},
        {0,0,0,20,20,0,0,0},
        {5,5,10,25,25,10,5,5},
        {10,10,20,30,30,20,10,10},
        {50,50,50,50,50,50,50,50},
        {0,0,0,0,0,0,0,0}};
      if(piece==pawn)
        return (pawnsWorth+pawns_position[i][j]);
      else if(piece==knight)
        return (knightsWorth+knights_position[i][j]);
      else if(piece==bishop)
        return (bishopsWorth+bishops_position[i][j]);
      else if(piece==rook)
        return (rooksWorth+rooks_position[i][j]);
      else if(piece==queen)
        return (queensWorth+queens_position[i][j]);
      else if(piece==king)
        return (kingsWorth+kings_position[i][j]);
      
    }
    return 0;
  } //position
  
  /*returns evaluation of sides that are playing*/
  public int evaluate(Pieces[][] board, String side, int moves[])
  {
    check_side(side); 
    int row;
    if(side=="white")
    {
      row=7;
    }
    else
    {
      row=0;
    }
    int sum=0;
    if(checkmate(board_iteration ( board, side, moves ),board))
    {
      sum = -9999999;
    }
    else if((stalemate(board_iteration ( board, side, moves ),board))||drawn(board))
    {
      sum = -5555555;
    }
    
    else
    {
    char p;
    int val=0;//value of a piece at a particular position on the board
    for(int i =0; i<board.length;i++)
    {
      for(int j =0; j< board[0].length;j++)
      {
        val = possesion(side,board,i,j);
        p = board[i][j].piece; 
        if(p==pawn)//don't check mobility, protect for a pawn since that encourages overuse of the pawn and the positions function also basically covers everything
        {
          sum=sum+ val*threats(i,j,board,moves)+val*protect(i,j,board,moves)+val*mobility(i,j,board,moves);//pawn promotion is taken care of by possesion
          //We also avoid evaluating a pawn for mobility and possibility of en-passant as this might encourage the use of pawn too much
          if(doubledPawn(j,board,pawn)==true)
          {
            sum=sum-7;
          }
          if(IsolatedPawn(j,board,pawn)==true)
          {
            sum=sum-2;
          } 
        }
        else if ( p == bishop ) 
        { 
          sum = sum+val*threats(i,j,board,moves)+val*protect(i,j,board,moves)+val*mobility(i,j,board,moves); 
        }
        
        else if ( p == knight ) 
        {
          sum = sum+val*threats(i,j,board,moves)+val*protect(i,j,board,moves)+val*mobility(i,j,board,moves); 
        }
        
        else if ( p == queen ) 
        {
          sum = sum+val*threats(i,j,board,moves)+val*protect(i,j,board,moves)+val*mobility(i,j,board,moves);  
        }
        
        else if ( p == rook ) 
        {
          sum = sum+val*threats(i,j,board,moves)+val*protect(i,j,board,moves)+val*mobility(i,j,board,moves);
           if(board[i][j].isCastling==false && board[i][j].hasCastled==false)//could have castled
          {
            if(board[row][0].piece!=rook && board[row][4].piece==king && board[row][4].hasMoved==false)// but a rook has moved
              sum=sum-8; 
            else if(board[row][7].piece!=rook && board[row][4].piece==king && board[row][4].hasMoved==false)// but a rook has moved
              sum=sum-12; 
            else if((board[row][0].piece==rook && board[row][0].hasMoved==false) && (board[row][7].piece==rook&&board[row][7].hasMoved==false) && (board[row][4].piece==king && board[row][4].hasMoved==true))
              sum=sum-15;//but the king has moved
            else if((board[row][0].piece==rook && board[row][0].hasMoved==false) && (board[row][7].piece==rook&&board[row][7].hasMoved==false) && (board[row][4].piece!=king))
              sum=sum-15;//but the rook has moved
          }    
        }
        
        else if ( p == king ) //we don't want pieces next to it at all if possible
        {
          sum = sum-(val*threats(i,j,board,moves))+(val*protect(i,j,board,moves))+mobility(i,j,board,moves);//we don't want to encourage king going out of safety
        } 
      }
    }
    }
    
    return sum;
  }//evaluate
  
  public boolean doubledPawn(int colRank,Pieces[][] board, char piece)//minus the value at current position if a pawn is ahead
  {
    int cnt=0;
    
    for(int i =0;i<board.length;i++)
    {
      
      if( board[i][colRank].piece==piece)
      {
        cnt++; 
      }
      
    }
    
    if(cnt>=2)
      return true;
    
    else
      return false;       
  } //doubledPawn
  
  public boolean IsolatedPawn(int colRank,Pieces[][] board, char piece)
  {
    if (colRank>0)
    {
      for(int i =0;i<board.length;i++)
      {
        if( board[i][colRank-1].piece==piece)
        {
          return false;
        }
      }
    }
    if (colRank<(board.length -1))
    {
      for(int i =0;i<board.length;i++)
      {
        if( board[i][colRank+1].piece==piece)
        {
          return false;
        }
      }
    }
    return true;    
  }//IsolatedPawn
  
  /*MAterial Value*/
  public int possesion(String side, Pieces[][] board, int i, int j)
  {
    int eval_value=0;
    char p=board[i][j].piece;
    eval_value=eval_value+position(side,i,j,p);
    return eval_value;
  }//possesion
  
  /*How many spaces can a piece move*/
  public int mobility(int i, int j, Pieces board[][], int [] moves)//or how much is the piece developed, never called with pawn
  {
    int eval_value=0;
    LinkedList<Pieces> mobList = new LinkedList<Pieces>();
    mobList = movesOfaPiece(i,j,board,moves);
    eval_value=mobList.size();
    return eval_value;
  }//mobility
   
  /*Find all moves that threaten*/
  public LinkedList<Pieces> threaten(int x, int y, Pieces board[][],int [] moves)
  {
    char p;
    Pieces allP;
    LinkedList<Pieces> opponentsList = new LinkedList<Pieces>();
    for ( int i=0 ; i<board.length ; i++ ) 
    {
      for ( int j=0 ; j<board[i].length ; j++ ) 
      {
        p = board[i][j].piece; 
        if(p==o_pawn)
        {
          allP=new Pieces(p,pawnsWorth,i,j);
          opponentsList.add(allP);
        }
        else if(p==o_bishop)
        {
          allP=new Pieces(p,pawnsWorth,i,j);
          opponentsList.add(allP);
        }
        else if(p==o_knight)
        {
          allP=new Pieces(p,pawnsWorth,i,j);
          opponentsList.add(allP);
        }
        else if(p==o_queen)
        {
          allP=new Pieces(p,pawnsWorth,i,j);
          opponentsList.add(allP);
        }
        else if(p==o_rook)
        {
          allP=new Pieces(p,pawnsWorth,i,j);
          opponentsList.add(allP);
        }
        else if(p==o_king)
        {
          allP=new Pieces(p,pawnsWorth,i,j);
          opponentsList.add(allP);
        }
      }
      
    }
    return opponentsList;
  }//threaten
  
  /*How many places does a piece have an opportunity to capture a piece*/
  public int threats(int x, int y, Pieces board[][], int [] moves)
  {
    int eval_value=0;
    LinkedList<Pieces> List = new LinkedList<Pieces>();
    List = movesOfaPiece(x,y,board,moves);
    LinkedList<Pieces> opponentsList = threaten(x,y,board,moves);
    for(int i =0; i< List.size(); i++)
    {
      for(int j =0; j< opponentsList.size();j++)
      {
        if((List.get(i).move[2]==opponentsList.get(j).move[0]) && (List.get(i).move[3]==opponentsList.get(j).move[1]))//if one of the moves a possible capture or threat
        {
          if(opponentsList.get(j).piece==o_pawn)
          {
            eval_value = eval_value+1;
          }
          else if(opponentsList.get(j).piece==o_bishop)
          {
            eval_value = eval_value+3;
          }
          else if(opponentsList.get(j).piece==o_knight)
          {
            eval_value = eval_value+3;
          }
          else if(opponentsList.get(j).piece==o_queen)
          {
            eval_value = eval_value+8;
          }
          else if(opponentsList.get(j).piece==o_rook)
          {
            eval_value = eval_value+5;
          }
          //putting a king in check is great! but it generally does not remain for long, 
          //and since it probably close to the other side, it is better to get the pawn promoted
          else if(opponentsList.get(j).piece==o_king)
          {
            eval_value = eval_value+9;
          }
        }
      }
    }
    return eval_value;
  }//threats
  

  //returns the number of ally pieces protecting the current side's piece
  public int protect(int x, int y, Pieces board[][], int [] moves)
  {
    int eval_value=0;
    int rowFile;
    int colRank;   
    char storep=board[x][y].piece;
    LinkedList<Pieces> List = new LinkedList<Pieces>();
    char p =board[x][y].piece;
    String side;//must be removed once evaluated is ready
    
    if (Character.isLowerCase(p))//to return values to their original state
    {
      side="white";
    }
    else
    {
      side="black";
    }
    
    check_side(side);
    board[x][y].piece='-';
    List = board_iteration(board,side,moves);
    
    for(int i=0;i<List.size();i++)
    {
      if((List.get(i).move[2]==x)&&(List.get(i).move[3]==y))
      {
        eval_value=eval_value+1;
      }
    }
    
    board[x][y].piece=storep;
    
    return eval_value;
    
  }//protect
   
  
  /*moves a piece can make*/
  public LinkedList<Pieces> movesOfaPiece(int i, int j, Pieces board[][], int [] move)
  {
    char p =board[i][j].piece; 
    String side;//must be removed once evaluated is ready
    if (Character.isLowerCase(p))
    {
      side="white";
    }
    else
    {
      side="black";
    }
    check_side(side);
    LinkedList<Pieces> listy = new LinkedList<Pieces>();
    if ( p == pawn ) { 
      input(listy,board[i][j],pawn_check(i,j,side,move)); 
      
    }
    
    else if ( p == bishop ) { 
      input(listy,board[i][j],move_brq(i,j,positions,bishop)); 
      
    }
    
    else if ( p == knight ) {
      input(listy,board[i][j],move_knight_king(i,j,moves,knight)); 
      
    }
    
    else if ( p == queen ) {
      input(listy,board[i][j],move_brq(i,j,moves1,queen)); 
      
    }
    
    else if ( p == rook ) {
      input(listy,board[i][j],move_brq(i,j,positions1,rook)); 
      
    }
    
    else if ( p == king ) {
      input(listy,board[i][j],move_knight_king(i,j,moves1,king)); 
      
    } 
    LinkedList<Pieces> returnList=multi(listy);
    return returnList;
  }//movesOfaPiece ... a helper method
  
  // main part of the chess engine, iterating though a board to get a colour's chess pieces's possible moves, side is the colour playing and array move is the last moves  
  public LinkedList<Pieces> board_iteration ( Pieces [ ] [ ] array, String side, int [ ] move ) 
  {      
    char p; 
    int range=0; 
    list = new LinkedList<Pieces>();  // to store the pieces that can be moved in this board configuration 
    
    board = array;                    // assign board  
    
    check_side(side);                 // assign chess pieces and opposing chess pieces 
    
    // iterating through the board 
    for ( int i=0 ; i<board.length ; i++ ) {
      for ( int j=0 ; j<board[i].length ; j++ ) {
        p = board[i][j].piece; 
        
        if ( p == pawn ) { 
          input(list,board[i][j],pawn_check(i,j,side,move)); // adding to final list of moves
          
        }
        
        else if ( p == bishop ) { 
          input(list,board[i][j],move_brq(i,j,positions,bishop)); 
          
        }
        
        else if ( p == knight ) {
          input(list,board[i][j],move_knight_king(i,j,moves,knight)); 
          
        }
        
        else if ( p == queen ) {
          input(list,board[i][j],move_brq(i,j,moves1,queen)); 
          
        }
        
        else if ( p == rook ) {
          input(list,board[i][j],move_brq(i,j,positions1,rook)); 
          
        }
        
        else if ( p == king ) {
          input(list,board[i][j],move_knight_king(i,j,moves1,king)); 
          
        } 
        
      }; 
      
    };
    
    castling(side); 
    LinkedList<Pieces> list2=new LinkedList<Pieces>();
    list2=multi(list);
    return list2; 
  
  };  // board_iteration
  
  public LinkedList<Pieces> multi(LinkedList<Pieces> list)//returns a solved list
  {
    LinkedList<Pieces> allMoves = new LinkedList<Pieces>(); 
    int num_moves;//number of moves for one piece
    Pieces all_moves;
    int from_to[]=new int[4];
    int start=0;
    int cnt=0;//for promotion of pawn
    boolean left=false;//to check if the castled rook is included already
    boolean right=false;//to check if the castled rook is included already
    for(int i = 0; i < list.size();i++)
    {
      num_moves =(list.get(i).move.length)/4;//number of moves for one piece
      cnt=0;
      for(int j = 0;j < num_moves; j++)
      {
        start=0;
        all_moves = new Pieces(list.get(i).piece, list.get(i).value);
        all_moves.hasMoved=list.get(i).hasMoved;
        all_moves.isPawnPromoted=list.get(i).isPawnPromoted;
        all_moves.isPawnPromotedtoQueen=list.get(i).isPawnPromotedtoQueen;
        all_moves.isPawnPromotedtoKnight=list.get(i).isPawnPromotedtoKnight;
        all_moves.isPawnPromotedtoRook=list.get(i).isPawnPromotedtoRook;
        all_moves.isPawnPromotedtoBishop=list.get(i).isPawnPromotedtoBishop;
        if(all_moves.isPawnPromoted==true&&cnt==0)
        {
          all_moves.isPawnPromotedtoQueen=true;
          cnt++;
        }
        else if(all_moves.isPawnPromoted==true&&cnt==1)
        {
          all_moves.isPawnPromotedtoKnight=true;
          cnt++;
        }
        else if(all_moves.isPawnPromoted==true&&cnt==2)
        {
          all_moves.isPawnPromotedtoRook=true;
          cnt++;
        }
        else if(all_moves.isPawnPromoted==true&&cnt==3)
        {
          all_moves.isPawnPromotedtoBishop=true;
          cnt++;
        }
        all_moves.isCastling=list.get(i).isCastling;
        all_moves.move = new int[4];
        for(int k = (4*j); k< ((4*j)+4);k++)
        {
          from_to[start++]= list . get(i).move[k];
        }
        for(int k = 0; k < 4; k++)
        {
          all_moves.move[k]=from_to[k];
        }
        if(all_moves.isCastling==true&&all_moves.move[1]==0&&all_moves.move[3]==3&& left==false)
        {
          all_moves.isCastling=true;
          int kmove[]={all_moves.move[0],4,all_moves.move[0],2};
          all_moves.move=join_array(all_moves.move,kmove);
          left=true;
        }
         else if(all_moves.isCastling==true&&all_moves.move[1]==7&&all_moves.move[3]==5&& right==false)
        {
           all_moves.isCastling=true; 
           int kmove[]={all_moves.move[0],4,all_moves.move[0],6};
           all_moves.move=join_array(all_moves.move,kmove);
           right=true;
        }
         else
         {
           all_moves.isCastling=false;
         }
        allMoves.add(all_moves);
      }
    }
    return allMoves;
  }//multi
  
  // This checks where the pawn is at on the board and what move it can undergo  
  public int [ ] pawn_check ( int x, int y, String player, int [ ] a ) {
    
    int m=0,n=0;   // m is one square up or down, n is 2 squares up or down
    int row1=0;    // row1 is the row where the pawn can undergo pawn promotion if it has reached there
    int row2=0;    // row2 is the row where the pawn may undergo en passant
    int init_row=0;  // init_row is the row where the pawn is originally at (normal chess setup)
    int [ ] output = { }; 
     
    if ( player == "black" ) {
      init_row = 1;
      row1 = 6; 
      row2 = 4; 
      m = 1; 
      n = 2; 
      
    } 
    
    else if ( player == "white" ) { 
      init_row = 6; 
      row1 = 1; 
      row2 = 3; 
      m = -1; 
      n = -2; 
      
    }
    
    if ( x == row1 ) {
      output = pawn_promotion(x,y,m); 
      
    }
    
    // if pawn has not yet reached the pawn promotion row 
    else if ( x < row1 || x > row1 ) {
      output = pawn_move(x,y,m,n,init_row); 
      
      if ( x == row2 ) {
         output = en_passant(x,y,a,output,m);  
        
      }
      
    }
    
    return output; 
    
  }; //pawn_check
  
  // movement of pawn including a possible capture is checked for 
  public int [ ] pawn_move ( int x, int y, int m, int n , int g) {     
    
    char b = ' ';  
    
    int [ ] output = { }; 
    boolean a1; 
    
    // pawn will always move 1 square if free space is available 
    if ( board[x+m][y].piece == '-' ) 
    { 
      board[x][y].piece = '-';                      // this part moves the piece, checks if king is in check to allow or not for the move
      board[x+m][y].piece = pawn; 
      a1 = check_king(board); 
      change(x,y,x+m,y,'-'); 
      
      if ( a1 == false ) { 
        int [ ] change = {x,y,x+m,y};              // move of the pawn
        output = join_array(change,output);        // move of the pawn added to the array 
        
      }  
      
    }
    
    // if pawn has not been moved, 2 square move allowed, with value of n     
    if ( x == g  ) { 
      if ( board[x+n][y].piece == '-' ) {
        board[x][y].piece = '-'; 
        board[x+n][y].piece = pawn; 
        a1 = check_king(board); 
        change(x,y,x+n,y,'-');                      // board is changed back to its original configuration
        
        if ( a1 == false ) {
          int [ ] change = {x,y,x+n,y};  
          output = join_array(change,output);   // move of the pawn added to the array 
          
        }
        
      }
      
    }
    
    int a = 1; 
    int num = 0; 
    
    // capture is possible with the current pawn, that is at board[x+m][y+1] and board[x+m][y-1], there is an opponent 
    while ( num < 2 ) {
      if ( capture_check(x+m,y+a,pawn) == true ) {
        b = board[x+m][y+a].piece; 
        board[x][y].piece = '-'; 
        board[x+m][y+a].piece = pawn;   
        a1 = check_king(board); 
        change(x,y,x+m,y+a,b);                      // board is changed back to its original configuration
        
        if ( a1 == false ) { 
          int [ ] change = {x,y,x+m,y+a};  
          output = join_array(change,output);     // move of the pawn added to the array 
          
        } 
        
      } 
      
      a = -1; 
      num++; 
      
    }
    
    board[x][y].move = output; 
    
    return output; 
    
  };  // pawn_move
  
  // this function promotes a pawn when it reaches a certain row
  public int [ ] pawn_promotion ( int x, int y, int m ) {
    
    int [ ] output = { };  
    
    int count=0; 
    boolean a1;   
    
    // whether there is a free space one square ahead 
    if ( board[x+m][y].piece == '-' ) { 
      board[x][y].piece = '-'; 
      board[x+m][y].piece = pawn; 
      a1 = check_king(board); 
      change(x,y,x+m,y,'-');                          // board is changed back to its original configuration
      
      if ( a1 == false ) {
        board[x][y].isPawnPromoted = true; 
        while ( count < 4 ) {                       // four times the moves are added to show for possibility of four different promotions 
          int [ ] change = {x,y,x+m,y}; 
          output = join_array(change,output); 
          count++; 
          
        } 
        
      } 
      
    } 
    
    return output; 
    
  };  // pawn_promotion
  
  // checks for whether the board configuration can undergo castling
  public void castling ( String player ) {
    
    boolean a1,a2,a3; 
    int m=0; 
    int stop=1;
    
    int b_right=1,b_left=1; 
    
    int [ ] output = { }; 
    
    // checks whose turn it is to see on which side of the board moves will occur  
    if ( player == "black" ) {
      m = 0; 
      
    } 
    
    else if ( player == "white" ) {
      m = 7; 
      
    } 
    
    // checks if king has moved before
    if (board[m][4].piece==king && board[m][4].hasMoved == false) {
      stop = 0; 
      
    }
    
    // checks if rook to the right has moved before
    // seperate rook checks, either both have moved or 1 has moved or none have moved
    if (board[m][7].piece == rook && board[m][7].hasMoved == false ) {
      b_right = 0; 
      
    }
    
    // checks if rook to the left has moved before
    if (board[m][0].piece == rook && board[m][0].hasMoved == false ) {
      b_left = 0; 
      
    }  
    
    // king has not moved and player is not in check  
    if ( check_king(board) == false && stop == 0 ) { 
      
      // rook to the left has not moved, checks for empty spaces between left rook and king       
      if ( board[m][1].piece == '-' && board[m][2].piece == '-' && board[m][3].piece == '-' && b_left == 0 ) {
        
        // each time the king is moved (two squares) and when the rook jumps, it is checked to see if it puts the king in check
        // if not in check, allow move 
        board[m][4].piece = '-'; 
        board[m][3].piece = king; 
        
        a1 = check_king(board); 
        
        board[m][3].piece = '-';
        board[m][2].piece = king;
        
        a2 = check_king(board); 
        
        board[m][0].piece = '-'; 
        board[m][3].piece = rook;
        
        a3 = check_king(board); 
        
        // board is changed back to its original configuration 
        change(m,4,m,2,'-'); 
        change(m,0,m,3,'-'); 
        
        // allow move 
        if ( a1 == false && a2 == false && a3 == false ) {
          board[m][0].isCastling = true;                    // i added is true to the rook
          board[m][4].isCastling = true; 
          int [ ] change2 = {m,0,m,3}; 
          output = join_array(change2,board[m][0].move);   // array of moves for this move
          board[m][0].move = output;                        
          
        } 
        
      } 
      
      // rook to the right has not moved, checks for empty spaces between right bishop and king 
      if ( board[m][5].piece == '-' && board[m][6].piece == '-' && b_right == 0 ) {
        
        int [ ] output1 = { }; 
        
        // each time the king is moved (two squares) and when the rook jumps, it is checked to see if it puts the king in check
        // if not in check, allow move 
        board[m][4].piece = '-'; 
        board[m][5].piece = king; 
        
        a1 = check_king(board); 
        
        board[m][5].piece = '-';
        board[m][6].piece = king;
        
        a2 = check_king(board); 
        
        board[m][7].piece = '-'; 
        board[m][5].piece = rook;
        
        a3 = check_king(board);
        
        // board is changed back to its original configuration
        change(m,4,m,6,'-'); 
        change(m,7,m,5,'-'); 
        
        // allow move
        if ( a1 == false && a2 == false && a3 == false ) {
          board[m][7].isCastling = true; 
          board[m][4].isCastling = true; 
          int [ ] change2 = {m,7,m,5}; 
          output1 = join_array(change2,board[m][7].move);  
          board[m][7].move = output1;  
          
        } 
        
      } 
      
    } 
    
  };  // castling
  
  // does the en passant move for a pawn
  public int [ ] en_passant ( int x, int y, int [ ] last_moves, int [ ] out, int m ) {
    
    int h = 0; 
    int stop = 0; 
    boolean a1; 
    int a=0,b=0,c=0,d=0; 
    
    int [ ] output = out;  
    
    // if a specific board configuration is given and pawn in the row to have en passant checked out, there will be an error since the array will be empty
    if ( last_moves.length != 0 ) {
      // last move positions obtained
      a = last_moves[last_moves.length-4]; 
      b = last_moves[last_moves.length-3]; 
      c = last_moves[last_moves.length-2]; 
      d = last_moves[last_moves.length-1]; 
    
    // checks if (a,b) has been moved prior to the last move  
    while ( h < (last_moves.length-4) ) {
      if ( a == last_moves[h] && b == last_moves[h+1] ) { 
        stop = 1;  
        
      }
      
      h = h+2; 
      
    }     
    
    // if piece is at this row, and opposing piece just moved, and piece is an opponent's pawn
    if ( x == c && stop == 0 && board[c][d].piece == o_pawn ) {                
      // checks on if opposing pawn is just on its right or left 
      if ( (y+1) == d || (y-1) == d ) {
        board[x][y].piece = '-'; 
        board[c][d].piece = '-'; 
        board[x+m][d].piece = pawn; 
        a1 = check_king(board); 
        board[c][d].piece = o_pawn; 
        change(x,y,x+m,d,'-');                                      // board is changed back to its original configuration
        
        if ( a1 == false ) {
          int [ ] change = {x,y,x+m,d};                            // moves added to array  
          output = join_array(change,output);                     
          
        } 
        
      } 
      
    }
    
    }
    
    return output; 
    
  };  // en_passant
  
  
  // given a position on the board(x,y), this checks if the a capture can be made there, by the chess piece p
  // Basically checks if the piece at (x,y) is one of its own or not
  public boolean capture_check ( int x, int y, char p  ) {
    
    if ( x >= 0 && x < board.length && y >= 0 && y < board.length ) {
      if ( Character.isUpperCase(p) ) {
        return Character.isLowerCase(board[x][y].piece);  
        
      }
      
      else if ( Character.isLowerCase(p) ) {
        return Character.isUpperCase(board[x][y].piece); 
        
      }
      
    }
    
    return false; 
    
  };  // capture_check
  
  // bishop, rook and queen can move as long as there is empty space, then it eithers stop due to end of board, to capture opponent or 
  // because it reached one of it own, x and y is the position, pos contains the directions of motion, p is chess char piece
  public int [ ] move_brq ( int x, int y, int [ ] [ ] pos, char p ) {
    
    int a,b; 
    boolean a1; 
    char f; 
    
    int [ ] output = { }; 
    
    for ( int g=0 ; g<pos.length ; g++ ) {
      a = x + pos[g][0];  
      b = y + pos[g][1]; 
      
      // checks along empty spaces
      while ( a >= 0 && a < board.length && b >= 0 && b < board.length && board[a][b].piece == '-' ) { 
        board[x][y].piece = '-'; 
        board[a][b].piece = p; 
        a1 = check_king(board); 
        change(x,y,a,b,'-');      // board is changed back to its original configuration
        
        if ( a1 == false ) { 
          int [ ] change = {x,y,a,b}; 
          output = join_array(change,output); // moves added to an array
          
        }         
        
        a = a + pos[g][0]; 
        b = b + pos[g][1]; 
        
      }
      
      // checks if there is a board position after the empty spaces and if it contains a chess piece that can be captured 
      if ( capture_check(a,b,p) == true ) { 
        f = board[a][b].piece; 
        board[x][y].piece = '-'; 
        board[a][b].piece = p; 
        a1 = check_king(board); 
        change(x,y,a,b,f);     // board is changed back to its original configuration
        
        if ( a1 == false ) {
          int [ ] change = {x,y,a,b}; 
          output = join_array(change,output); // moves added to an array
          
        }
        
      }
      
    }; 
    
    return output; 
    
  };  // move_brq
  
  // knight and king can move to positions directly, whether to capture or not, the function is for both
  // x and y is the position, pos contains the directions of motion, p is chess char piece
  public int [ ] move_knight_king ( int x, int y, int [ ] [ ] pos, char p ) {
    
    int a,b; 
    boolean a1; 
    char f; 
    
    int [ ] output = { }; 
    
    for ( int g=0 ; g<pos.length ; g++ ) {
      a = x + pos[g][0]; 
      b = y + pos[g][1]; 
      
      // checks if position is an empty space or has an opponent's chess piece
      if ( a >= 0 && a < board.length && b >= 0 && b < board.length && ( board[a][b].piece == '-' || capture_check(a,b,p) == true ) ) {
        f = board[a][b].piece;  
        board[x][y].piece = '-'; 
        board[a][b].piece = p;           
        a1 = check_king(board); 
        change(x,y,a,b,f);           // board is changed back to its original configuration
        
        if ( a1 == false ) {
          int [ ] change = {x,y,a,b}; 
          output = join_array(change,output); // moves added to an array 
          
        }
        
        a = a + pos[g][0]; 
        b = b + pos[g][1]; 
        
      } 
      
    }; 
    
    return output; 
    
  };  // move_knight_king 
  
  
  // check if king of the player is in check to see if a certain move is possible or not 
   public boolean check_king ( Pieces [ ] [ ] table ) {
    
    int p; 
    int q; 
    int count=0; 
    
    int [ ] [ ] array; 
    int [ ] [ ] knight_attack = { {-2,-1}, {-2,1}, {2,-1}, {2,1}, {-1,-2}, {-1,2}, {1,-2}, {1,2} }; // positions from which the king can be attacked by a knight 
    
    LinkedList<int [ ] [ ]> list_directions = new LinkedList<int [ ] [ ]>(); // list of moves made possible by a chess piece
    LinkedList<Character> list1 = new LinkedList<Character>(); // list of chess pieces whose moves are stored in list_directions in the same order
    
    findKing(queen,table);          // finds position of king at this board 
    
    list_directions.add(knight_attack); // stores the knights positions
    list_directions.add(positions);     // stores the pawn positions(from which there can be a possible pawn attack) 
    list1.add(o_knight);                // stores the char knight 
    list1.add(o_pawn);                  // stores the char pawn 
    
    // goes through the positions from which it can be attacked by a knight, then a pawn 
    // since knights and pawns can only move to one position, they are checked one after another in the same loop 
    while ( count < list_directions.size() ) {
      array = list_directions.get(count); 
      
      for ( int v=0 ; v<array.length ; v++ ) {    
        p = king_x + array[v][0]; 
        q = king_y + array[v][1]; 
        
        if ( p >= 0 && p < table.length && q >= 0 && q < table.length ) { 
          if ( table[p][q].piece == list1.get(count) ) { 
            return true; 
            
          } 
          
        } 
        
      }; 
      
      count++; 
      
    } 
    
    list_directions.clear(); 
    list1.clear();
    
    list_directions.add(moves1); // stores the directions from which it can be attacked by a queen
    list_directions.add(positions); // stores the directions from which it can be attacked by a bishop
    list_directions.add(positions1); // stores the directions from which it can be attacked by a rook
    list1.add(o_queen);            // stores the char queen
    list1.add(o_bishop);           // stores the char bishop
    list1.add(o_rook);             // stores the char rook
    
    count = 0; 
    
    // goes through the positions along which it can be attacked by a queen, a bishop then a rook  
    // since queens, bishop, and rooks can move along several positions until it reaches the end of the board or a chess piece, they are checked one after another in the same loop 
    while ( count < list_directions.size() ) { 
      array = list_directions.get(count); 
      
      for ( int v=0 ; v<array.length ; v++ ) {
        p = king_x + array[v][0]; 
        q = king_y + array[v][1];  
        
        // checks along empty spaces
        while ( p >= 0 && p <= table.length-1 && q >= 0 && q <= table.length-1 && table[p][q].piece == '-' ) {
          p = p + array[v][0]; 
          q = q + array[v][1];  
          
        } 
        
        // checks if there is a board position after the empty spaces and if it contains the chess piece that is being checked for
        if ( p >= 0 && p < table.length && q >= 0 && q < table.length && table[p][q].piece == list1.get(count) ) {      
          return true; 
          
        } 
        
      }; 
      
      count++; 
      
    }  
    
    return false; 
    
  };  // check_king
  
  
  /** A way to find the position of the king in a given board, given a chess char piece of the color playing at
    * the moment                                                                                                 */ 
  
  public void findKing ( char p, Pieces [ ] [ ] matrix ) {
    
    for ( int i=0 ; i<matrix.length ; i++ ) {
      for ( int j=0; j<matrix[i].length ; j++ ) {
        if ( Character.isUpperCase(p) && matrix[i][j].piece == 'K' ) {
          king_x = i; 
          king_y = j; 
          
        } 
        
        else if ( Character.isLowerCase(p) && matrix[i][j].piece == 'k' ) {
          king_x = i; 
          king_y = j; 
          
        } 
        
      }; 
      
    };      
    
  };  // findKing 
  
  
  // checks if board is in stalemate after it has been iterated. The player whose turn it is has no legal moves to make and his king has not been checked 
  public boolean stalemate ( LinkedList l, Pieces [ ] [ ] b ) {
    
    if ( check_king(b) == false && l.size() == 0 ) {
      return true; 
      
    }  
    
    return false; 
    
  };  // stalemate
  
  
  // checks if board is in checkmate after it has been iterated for possible moves. If it returns true, the player whose turn it is, has been checkmated 
  public boolean checkmate ( LinkedList l, Pieces [ ] [ ] b ) {
    
    if ( check_king(b) == true && l.size() == 0 ) {
      return true; 
      
    } 
    
    return false; 
    
  };  // checkmate 
  
  
  // attaches an array to a pieces object and adds it to a list<Pieces>
  public void input ( LinkedList<Pieces> l, Pieces b, int [ ] c ) {
    
    if ( c.length != 0 ) 
    {
      b.move = c; 
      l.add(b); 
      
    } 
    
  };  // input
  
  
  // switches a board to its original configuration after a move has been attempted
  public void change ( int x, int y, int r, int s, char d ) {
    
    board[x][y].piece = board[r][s].piece; 
    
    board[r][s].piece = d;  
    
  };  // change
  
  // This function joins two arrays to each other
  public int [ ] join_array ( int [ ] a, int [ ] b ) {
    
    int [ ] y = new int[a.length+b.length]; 
    int c; 
    
    for ( c = 0 ; c < a.length ; c++ ) {
      y[c] = a[c]; 
      
    };  
    
    for ( int q = 0 ; q < b.length ; q++ ) {
      y[c] = b[q]; 
      c++; 
      
    }; 
    
    return y; 
    
  };  // join_array
  
  // This checks if the board has chess pieces that will lead to a draw. Possible scenarios include 'Kk','Kkb',Kkn',KkB','KkN','KBkb' with bishops on same coloured squares. 
  
  public boolean drawn ( Pieces [ ] [ ] b ) {
    
    int x1=0,x2=0,y1=0,y2=0; 
    LinkedList<String> s1 = new LinkedList<>();
    LinkedList<String> s2 = new LinkedList<>();
    l = new LinkedList<>(); 
    
    // This part just finds the alternate squares on the chess board and stores it in two separate lists, s1 and s2 to be used for check for a possible draw scenario 
    String [ ] c1 = {"0","2","4","6"}; 
    String [ ] c2 = {"1","3","5","7"}; 
    String [ ] a; 
    
    for ( int y=0 ; y<8 ; y++ ) {
      for ( int x=0 ; x<4 ; x++ ) {
        s1.add(String.valueOf(y)+String.valueOf(c1[x])); 
        s2.add(String.valueOf(y)+String.valueOf(c2[x])); 
        
      };
      
      a = c1; 
      c1 = c2; 
      c2 = a; 
      
    }; 
    
    // iterates through board to find chess pieces on it and adds it to list
    for ( int i=0 ; i<b.length ; i++ ) { 
      for ( int j=0 ; j<b[i].length ; j++ ) {
        if ( b[i][j].piece != '-' ) { 
          l.add(b[i][j].piece); 
          
        }
        
        // keeps track of whether of not a black bishop is on the board and keeps its position
        if ( b[i][j].piece == 'B' ) {
          x1 = i; 
          y1 = j; 
          
        } 
        
        // keeps track of whether of not a white bishop is on the board and keeps its position
        if ( b[i][j].piece == 'b' ) {
          x2 = i; 
          y2 = j;  
          
        } 
        
      }; 
      
    };
    
    // checks if chess pieces on board correspond to an instance of a game being drawn      
    if ( l.contains('K') && l.contains('k') && l.size() == 2 ) {
      return true; 
      
    }
    
    // checks if chess pieces on board correspond to an instance of a game being drawn
    else if ( l.contains('K') && l.contains('k') && l.contains('b') && l.size() == 3 ) {
      return true; 
      
    }
    
    // checks if chess pieces on board correspond to an instance of a game being drawn
    else if ( l.contains('K') && l.contains('k') && l.contains('n') && l.size() == 3 ) {
      return true; 
      
    }
    
    // checks if chess pieces on board correspond to an instance of a game being drawn
    else if ( l.contains('K') && l.contains('k') && l.contains('B') && l.size() == 3 ) {
      return true; 
      
    }
    
    // checks if chess pieces on board correspond to an instance of a game being drawn
    else if ( l.contains('K') && l.contains('k') && l.contains('N') && l.size() == 3 ) {
      return true; 
      
    }
    
    // checks if chess pieces on board correspond to an instance of a game being drawn and if in this instance the bishops are on same coloured squares
    
    if ( l.contains('K') && l.contains('B') && l.contains('k') && l.contains('b') && l.size() == 4 ) {
      if ( s1.contains(String.valueOf(x1)+String.valueOf(y1)) && s1.contains(String.valueOf(x2)+String.valueOf(y2)) ) {
        return true; 
        
      }
      
      else if ( s2.contains(String.valueOf(x1)+String.valueOf(y1)) && s2.contains(String.valueOf(x2)+String.valueOf(y2)) ) {
        return true; 
        
      } 
      
    } 
    
    return false; 
    
  };  // drawn   
  
}  // ChessMoves 
