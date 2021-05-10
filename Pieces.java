/*Rimpy Amal Saha
 * 6600258
 * Muditaa Annauth
 * 6334965
 */  
class Pieces 
{
 char piece; 
 int value;
 int []move;
 boolean isCastling;
 boolean hasMoved;
 boolean isPawnPromoted;
 boolean isPawnPromotedtoQueen;
 boolean isPawnPromotedtoKnight;
 boolean isPawnPromotedtoRook;
 boolean isPawnPromotedtoBishop;
 boolean hasCastled;
 public Pieces(char piece,  int value) 
 {
  this.piece=piece; 
  this.value=value;
  this.isCastling=false;
  this.hasMoved=false;
  this.isPawnPromoted=false;
  this.isPawnPromotedtoQueen=false;
  this.isPawnPromotedtoKnight=false;
  this.isPawnPromotedtoRook=false;
  this.isPawnPromotedtoBishop=false;
  this.hasCastled=false;
 }
 public Pieces(char piece,  int value, int i, int j) 
 {
  this.piece=piece; 
  this.value=value;
  int arr[]={i,j};
  this.move=arr;
  this.isCastling=false;
  this.hasMoved=false;  
  this.isPawnPromoted=false;
  this.isPawnPromotedtoQueen=false;
  this.isPawnPromotedtoKnight=false;
  this.isPawnPromotedtoRook=false;
  this.isPawnPromotedtoBishop=false;
  this.hasCastled=false;
 }
}