import java.util.*; 
import java.io.*;
import java.lang.String;
/*Rimpy Amal Saha
 */  
class ChessPlay
{
  private Scanner sheet;  //For using the interaction panel
  private char[][] board;
  private Pieces[][] Board;
  private Pieces output;
  LinkedList<Character> capturedByWhite;//for testing purposes
  LinkedList<Character> capturedByBlack;//for testing purposes
  private boolean CaptureMade=false;
  private boolean PawnMoved=false;
  private ChessMoves cm;
  private String human;//white or black
  private String computer;
  private int flag=0;
  private int plies=4;
  private int glob_moves[];
  public static void main(String[]args)
  {
    ChessPlay ob=new ChessPlay();
  }
  /*
   * Initialising everything and taking input about GA parameters from the user is done here*/
  public ChessPlay()
  {
    cm=new ChessMoves();
    capturedByWhite = new LinkedList<Character>();
    capturedByBlack = new LinkedList<Character>();    
    sheet=new Scanner(System.in);    
    System.out.print("Enter filename: (provided is Board.txt)");
    String filename=sheet.next();
    loadState(filename);
    load();
  }//Constructor
  
  private void loadState(String filename) 
  {    
    /* char temp[][]={{'R','N','B','Q','K','B','N','R'},
     {'P','P','P','P','P','P','P','P'},
     {'-','-','-','-','-','-','-','-'},
     {'-','-','-','-','-','-','-','-'},
     {'-','-','-','-','-','-','-','-'},
     {'-','-','-','-','-','-','-','-'},
     {'p','p','p','p','p','p','p','p'},
     {'r','n','b','q','k','b','n','r'}};//Comment this one out and use the below one to test, so you don't have to make this again
     glob_moves=new int[0];//Add your moves here do one move from white knight from position 21 to 33 would look like {2,1,3,3}
     //for more than one move it would look like {2,1,3,3,2,8,1,6}so human move first then computer move. also the coordinates 
     //here would be like that of a regular array so the array above is what the input in console looks like, but changing moves 
     //array here would look lile {7,0,6,0,1,1,1,3} these may not be legal moves, it is just an example. it will always have 
     // 4 numbers storing from and to position
     /*char temp[][]=
     {{'-','-','-','-','K','-','-','-'},
     {'-','-','-','-','-','-','p','-'},
     {'-','-','-','-','-','-','-','-'},
     {'-','-','-','-','-','-','-','-'},
     {'-','-','-','-','-','-','-','-'},
     {'-','-','-','-','-','-','-','-'},
     {'-','-','-','-','-','-','-','-'},
     {'-','-','-','-','k','-','-','-'}};*///Use this array instead to see if the pawn is promoted or castling is done :)
    //When run, it asks if you would like to set up the board, it is like creating a board in real life :) But this is easier 
// Change the array as you want, add the moves as described above to array glob_moves;
    // board=temp;
    glob_moves=new int[0];
    char[][] temp2 = null;
    try
    {
      FileInputStream fi = new FileInputStream(filename);
      InputStreamReader in = new InputStreamReader(fi,"UTF-8");
      BufferedReader br = new BufferedReader(in);
      LinkedList<char[]> lines = new LinkedList<>();
      String line=br.readLine();
      while(line!= null) 
      {
        lines.add(line.toCharArray());
        line=br.readLine();
      }
      
      temp2 = new char[lines.size()][];
      br.close();
      
      for(int i = 0; i < lines.size(); i++) 
      {
        temp2[i] = lines.get(i);
      }
      
    }
    catch(IOException e) 
    {
      System.out.println(e.getMessage());
    }
    board=temp2; 
    
    System.out.println("Enter number of plies");
    plies=Integer.parseInt(sheet.next());
  }//loadstate
  //The following method holds the same board, but it can hold the values which would be easier for us when we calcualte
  //material values for board evaluation
  public void load()
  {
    Board=new Pieces[8][8];
    for(int i=0;i<Board.length;i++)
    {
      for(int j=0;j<Board[0].length;j++)
      {
        Board[i][j]=new Pieces(board[i][j],0);       //we can change the values appropriately later, this is just an example, CHECK AGAIN!!!!!!!!!!
      }
    }
    int arr[]=new int[0];
    LinkedList<Pieces> list;
    System.out.println("Enter what you would like to be white or black"); 
    System.out.println("Enter 1 for white");
    System.out.println("Enter 2 for black");
    int h=Integer.parseInt(sheet.next());
    if(h==1)
    {
      human="white";
      computer="black";
    }
    else
    {
      computer="white";
      human="black";
    }
    System.out.println("Please note that you would be entering the column number and row number as displayed on the sides of the board, as they are made to mimic a real board");
    display(Board);
    boolean done=false;
    int e;
    int choice =1;
    String side=human;
    System.out.println("Would you like to set up the board manually, without minimax to check a particular board? Please note that if the number of moves you choose to make manually are not even in number you might not end up on side of the board you chose");
    System.out.println("Enter 1 for yes");
    System.out.println("Enter 2 for no");
    choice = Integer.parseInt(sheet.next());
    
    while(choice==1)//to set up initial board on console OR CAN BE DONE BY PROVIDING A DIFFERENT FILE NAME LIKE Board.txt
    {
      list = cm.board_iteration ( Board, side,glob_moves );
      Board=Humanturn(Board,human,list);
      display(Board);
      System.out.println("Would you like to continue setting up?"); 
      System.out.println("Enter 1 for yes");
      System.out.println("Enter 2 for no");
      choice = Integer.parseInt(sheet.next());
      if(side=="white")
        side="black";
      else
        side="white";
    }
    
    while(done!=true)//stalemate,draw, checkmate, or the 50 movr rule draw
    {
      flag=0;//to check if there was an illegal move sent by user
      list = cm.board_iteration ( Board, human,glob_moves );//list of moves possible from human side
      
      if(cm.checkmate(list,Board)!=true&&cm.stalemate(list,Board)!=true&& cm.drawn(Board)!=true)
      {
        while(flag==0)
        {
          Board=Humanturn(Board,human,list);
        }
        display(Board);
      }
      
      else if(cm.checkmate(list,Board)==true)//check mate from the last turn so, human turn
      {
        System.out.println("Computer Lost :( ...you've won!!");
        done=true;
      } 
      else if(cm.stalemate(list,Board)==true)
      {
        System.out.println("Stalemate :|");
        done=true;
      } 
      else
      {
        System.out.println("Draw :|");
        done=true;
      }
      if(done!=true)//if from the human turn the computer is still not beaten, continue
      {
        list = cm.board_iteration ( Board, computer,glob_moves );//list of moves computer can make
        if(cm.checkmate(list,Board)!=true&&cm.stalemate(list,Board)!=true&& cm.drawn(Board)!=true)
        {
          e =minimaxPruned(Board,glob_moves,plies,0,computer, -9999999, 9999999);//call minimax with alpha beta pruning function
          int x1=output.move[0];
          int y1=output.move[1];
          int x2=output.move[2];
          int y2=output.move[3];
          char queen, knight, rook, bishop, pawn,king;
          int dir;//dir is the direction a pawn moves in
          if(computer=="white")//to set variables according to which side is playing
          {
            pawn='p';
            queen='q';
            knight='n';
            rook='r';
            bishop='b';
            king='k';
            dir = -1;
          }
          else
          {
            pawn='P';
            queen='Q';
            knight='N';
            rook='R';
            bishop='B';
            king='K';
            dir =1;
          }
          if(Board[x1][y1].piece==pawn)//this is majorly for the 50 move rule check
          {
            PawnMoved=true;//was a pawn moved
          }
          if(Board[x2][y2].piece!='-')
          {
            CaptureMade=true;//was a capture made
            if(computer=="white")
              capturedByWhite.add(Board[x2][y2].piece);
            else
              capturedByBlack.add(Board[x2][y2].piece);
          }
          if(Board[x1][y1].piece==pawn && y1!=y2 && Board[x2][y2].piece=='-')
          {
            CaptureMade=true;//en passant capture21
            if(human=="white")
              capturedByWhite.add(Board[x2-dir][y2].piece);
            else
              capturedByBlack.add(Board[x2-dir][y2].piece);
          }
          Board=move(Board,output,computer,glob_moves);//make the move that we found from the alpha beta pruning algorithm
          display(Board);//display the board after the move has been made. and output is set within the algorithm
        }
        else if(cm.checkmate(list,Board)==true)//check if the human caused checkmate
        {
          System.out.println("Computer Lost :( ...you've won!!");
          done=true;
          break;
        } 
        else if(cm.stalemate(list,Board)==true)
        {
          System.out.println("Stalemate :|");
          done=true;
          break;
        } 
        else
        {
          System.out.println("Draw :|");
          done=true;
          break;
        }
      }
      if(glob_moves.length==200&&CaptureMade==false&&PawnMoved==false)//50 moves rule
      {
        System.out.println("Due to 50 moves rule, would you like call draw?");
        System.out.println("Enter 1 for yes");
        System.out.println("Enter 2 for no");
        choice =Integer.parseInt(sheet.next());
        if(choice ==1)
        {
          System.out.println("A draw !!");
          done=true;//end this loop!
        }
      }
    } 
  }//load
  
  /*The following function minimax algorithm with alpha beta pruning that finds the best next step after checking user 
   * provided number of plies*/
  public int minimaxPruned(Pieces [][]board,int[] lastMoves,int maxDepth,int depth, String side, int alpha, int beta)
  {
    int bestValue=0;
    int value;
    if(depth==maxDepth)
    {
      return (cm.evaluate(board, side, lastMoves));
    }
    if(side==computer)//maximising player
    {
      bestValue=-9999999; 
      LinkedList<Pieces> list=cm.board_iteration(board, side, lastMoves);//get all moves
      if(cm.checkmate(list,board))//check if our current situation is checkmate or draw or stalemate
      {
        bestValue = -9999999;//and set it to least value possible
      }
      else if((cm.stalemate(list,board))||cm.drawn(board))
      {
        bestValue = -5555555;
      }
      else
      {
        for(int i=0;i<list.size();i++)
        {
          Pieces[][] b=move(board,list.get(i),side,lastMoves);//make the move to evaluate the board
          int moves[]=cm.join_array(lastMoves,list.get(i).move);// an array of moves that has moves to get to this board arrangement
          value=minimaxPruned(b,moves,maxDepth,depth+1,human,alpha,beta);//RECURSIVE call using adding 1 to depth as the tree is explored
          if(value>bestValue)
          {
            bestValue=value;
          }
          if(alpha<bestValue)//is there a better value found in a subtree
          {
            if(depth==0)
              output=list.get(i);
            alpha=bestValue;
          }
          if(beta<=alpha)
            break;
        }
      }
      return bestValue;
    }
    
    else//minimizing player
    {
      bestValue=9999999;
      LinkedList<Pieces> list=cm.board_iteration(board, side, lastMoves);
      if(cm.checkmate(list,board))
      {
        bestValue = -9999999;
      }
      else if((cm.stalemate(list,board))||cm.drawn(board))
      {
        bestValue = -5555555;
      }
      else
      {
        for(int i=0;i<list.size();i++)
        {
          Pieces[][] b=move(board,list.get(i),side,lastMoves);
          int moves[]=cm.join_array(lastMoves,list.get(i).move);
          value=minimaxPruned(b,moves,maxDepth,depth+1,computer,alpha,beta);//RECURSIVE call for all possible moves from current board from the human side
          if(value<bestValue)
          {
            bestValue=value;
          }
          if(beta>bestValue)
          {
            if(depth==0)
              output=list.get(i);
            beta=bestValue;
          }
          if(beta<=alpha)
            break;
        }
      }
      return bestValue;
    }
    
  }
  /*The following function can set the initial board state or just take human turns*/
  public Pieces[][] Humanturn( Pieces[][] Board, String side,LinkedList<Pieces> list)
  {
    System.out.println("The numbers on bottom are files and on the side are ranks, so to move a knight on 21 just write 21");
    System.out.println("Enter fileRank(columnRow) of the piece to move (Make sure you have no space in between like 12) same order as e5 so column first then row");
    int rankFile=Integer.parseInt(sheet.next());
    int x1=rankFile%10;//rank to x or rows
    rankFile=rankFile/10; 
    int y1=rankFile;
    System.out.println("Enter fileRank(columnRow) of where to piece to move (Make sure you have no space in between like 12) same order as e5 so column first then row");
    rankFile=Integer.parseInt(sheet.next());
    int x2=rankFile%10;//file to yor columns
    rankFile=rankFile/10;
    int y2=rankFile;
    int f1=0;
    int f2=0;
    int f3=0;
    int f4=0;
    char queen, knight, rook, bishop, pawn,king;
    int dir;//dir is the direction a pawn moves in
    if(side=="white")
    {
      pawn='p';
      queen='q';
      knight='n';
      rook='r';
      bishop='b';
      king='k';
      dir = -1;
    }
    else
    {
      pawn='P';
      queen='Q';
      knight='N';
      rook='R';
      bishop='B';
      king='K';
      dir =1;
    }
    int row[]={8,7,6,5,4,3,2,1};//since the input given is different from the normal array inputs
    int col[]={1,2,3,4,5,6,7,8};
    for(int i=0;i<8;i++)//to convert the input of board from - to to array coordinates
    {
      if(x1==row[i]&&f1==0)//to find the array coordinates according the file and rank given
      {
        x1=i;
        f1=1;
      }
      if(x2==row[i]&&f2==0)
      {
        x2=i;
        f2=1;
      }
      if(y1==col[i]&&f3==0)
      {
        y1=i;
        f3=1;
      }
      if(y2==col[i]&&f4==0)
      {
        y2=i;
        f4=1;
      }
    }
    Pieces m=new Pieces(Board[x1][y1].piece,0);//make a pieces object for the move piece    
    int a1[]={x1,y1,x2,y2};    
    m.move=a1;
    int flag2=0;
    if((m.piece=='p'||m.piece=='P')&&(x2==0||x2==7))
    {
      flag2=1;
      System.out.println("Please enter what you would like the pawn to be promoted to"); 
      System.out.println("1 for queen"); 
      System.out.println("2 for knight"); 
      System.out.println("3 for rook"); 
      System.out.println("4 for bishop"); 
      int choice =Integer.parseInt(sheet.next());//for pawn promotion by humans
      if(choice==1)
      {
        m.isPawnPromotedtoQueen=true; 
      }
      else if (choice==2)
      {
        m.isPawnPromotedtoKnight=true; 
      }
      else if (choice==3)
      {
        m.isPawnPromotedtoRook=true; 
      }
      else if (choice==4)
      {
        m.isPawnPromotedtoBishop=true;
      }
    }
    flag=0;//if the move give was wrong send a 1 back
    for(int i=0;i<list.size();i++)
    {
      if(arrayEqual(a1,list.get(i).move)&&flag2==0)
      {
        if(Board[x1][y1].piece==pawn)//is the piece moved a pawn
        {
          PawnMoved=true; 
        }
        if(Board[x2][y2].piece!='-')//or did it move on a space where a piece was
        {
          CaptureMade=true; 
          if(side=="white")
            capturedByWhite.add(Board[x2][y2].piece);
          else
            capturedByBlack.add(Board[x2][y2].piece);
        }
        if(Board[x1][y1].piece==pawn && y1!=y2 && Board[x2][y2].piece=='-')//en passant 
        {
          CaptureMade=true;
          if(side=="white")
            capturedByWhite.add(Board[x2-dir][y2].piece);
          else
            capturedByBlack.add(Board[x2-dir][y2].piece);
        }
        Board=move(Board,list.get(i),side,glob_moves);
        glob_moves=cm.join_array(glob_moves,a1);
        flag=1;
        break;
      }
      else if(arrayEqual(a1,list.get(i).move)&&flag2==1)
      {
        Board=move(Board,m,side,glob_moves);
        glob_moves=cm.join_array(glob_moves,a1);
        flag=1;
        break;
      }
    }
    if(flag==0)
    {
      System.out.println("Illegal move, check your side and if you've entered in the order column then row"); 
    }
    return Board;
  }//HumanTurn
  /*The following function checks if the move is present in the list of legal moves*/
  public boolean arrayEqual(int a[],int b[])
  {
    if(b.length==8)
    {
      if(a[0]==b[4]&&a[1]==b[5]&&a[2]==b[6]&&a[3]==b[7])
      {
        return true;
      }
      else
        return false;
    }
    else
    {
      if(a[0]==b[0]&&a[1]==b[1]&&a[2]==b[2]&&a[3]==b[3])
      {
        return true;
      }
      else
        return false;
    }
  }//arrayEquals
  
  /*make a move given as argument, after changing */
  public Pieces[][] move(Pieces chessBoard[][], Pieces move, String side, int moves[])
  {
    int x1=move.move[0]; 
    int y1=move.move[1]; 
    int x2=move.move[2]; 
    int y2=move.move[3]; 
    int x3,x4,y3,y4;
    
    char queen, knight, rook, bishop, pawn,king;
    int dir;//dir is the direction a pawn moves in
    Pieces temp;
    if(side=="white")
    {
      pawn='p';
      queen='q';
      knight='n';
      rook='r';
      bishop='b';
      king='k';
      dir = -1;
    }
    else
    {
      pawn='P';
      queen='Q';
      knight='N';
      rook='R';
      bishop='B';
      king='K';
      dir =1;
    }
    Pieces[][] board=new Pieces[8][8];
    for(int i=0;i<Board.length;i++)
    {
      for(int j=0;j<Board[0].length;j++)
      {
        board[i][j]=new Pieces(chessBoard[i][j].piece,0);//we can change the values appropriately later, this is just an example
      }
    }
    if(move.isCastling==true)//if the move is a castling move the...
    {
      x3=move.move[4];//shift the pieces as needed
      y3=move.move[5];
      x4=move.move[6];
      y4=move.move[7];
      Pieces kin=new Pieces(board[x3][y3].piece,0);
      board[x3][y3]=new Pieces('-',0);
      board[x4][y4]=new Pieces(kin.piece,0,x4,y4);
      board[x4][y4].hasMoved=true;
      board[x4][y4].hasCastled=true;
      temp = new Pieces(board[x1][y1].piece,0);
      temp.hasCastled=true;
    }
    else if(board[x1][y1].piece==pawn && y1!=y2 && board[x2][y2].piece=='-')//en passant
    {
      board[x2-dir][y2]=new Pieces('-',0);
      temp = new Pieces(board[x1][y1].piece,0);
    }
    else if(move.isPawnPromotedtoQueen)//piece promotion to queen
    {
      temp = new Pieces(queen,0);
    }
    else if(move.isPawnPromotedtoKnight)//to knight
    {
      temp = new Pieces(knight,0);
    }
    else if(move.isPawnPromotedtoRook)//to a rook
    {
      temp = new Pieces(rook,0);
    }
    else if(move.isPawnPromotedtoBishop)//to a bishop
    {
      temp = new Pieces(bishop,0);
    }
    else
    {
      temp = new Pieces(board[x1][y1].piece,0);
    }
    board[x1][y1]=new Pieces('-',0);
    board[x2][y2]=new Pieces(temp.piece,0,x2,y2);
    board[x2][y2].hasMoved=true;
    board[x2][y2].hasCastled=temp.hasCastled;
    return board;
  }//move
  /*Display the accurate chess board*/
  public void display(Pieces [][] Board)
  {
    int rank=8; 
    int flag=0;
    char arr[][]={{' ','_','_','_','_','_','_','_','_'},{' ','1','2','3','4','5','6','7','8'}};
    for(int i =0;i<capturedByBlack.size();i++)//pieces captured by black
    {
      System.out.print(capturedByBlack.get(i)+"\t");
      System.out.print("\t"); 
    }
    System.out.println();
    for(int i=0;i<8;i++)
    {
      for(int j=0;j<8;j++)
      {
        if(i<8&&j==0)
        {
          System.out.print((rank--)+"|\t"+Board[i][j].piece+"\t");//just the board with ranks
        }
        else
        {
          System.out.print(Board[i][j].piece+"\t");
        }
      }
      System.out.println();
    }
    for(int i=0;i<arr.length;i++)
    {
      for(int j=0;j<arr[0].length;j++)
      {
        System.out.print(arr[i][j]+"\t");//add file numbers as well
      }
      System.out.println();
    }
    for(int i =0;i<capturedByWhite.size();i++)
    {
      System.out.print(capturedByWhite.get(i)+"\t");//pieces captured by white
      System.out.print("\t"); 
    }
    System.out.println();
    System.out.println();
    System.out.println();
  }
}
