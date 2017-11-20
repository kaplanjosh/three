package threes.engine;
import java.util.Random;

import java.util.HashSet;

public class BoardUtil {

	public static int[][] deserializeBoard(String s) {
		if (s.length()!=16) {
			return null;
		} 
		int [][] boardArray = new int[4][4];

		int i = 0;
		int j = 0;
		for (int ij = 0; ij < 16; ij++) {
			
			boardArray[i][j] = Integer.parseInt(s.substring(ij,ij+1),16);
			j++;
			if (j==4) {
				i++;
				j=0;
			}
		}
		return boardArray;
	}

	public static int[][] initializeBaseBoard() {
		int emptyCount = 0;
		int oneCount = 0;
		int twoCount = 0;
		int threeCount = 0;
		int numCount = 0;
		boolean hasFour = false;
		
		int boardstate[][]= new int[4][4];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Random rand = new Random();
				boolean nailedIt = false;
				while (!nailedIt) {
					int k = rand.nextInt(2);
					if (emptyCount < 7 && k == 0) {
						boardstate[i][j] = 0;
						emptyCount++;
						nailedIt = true;
					} else {
						int  n = rand.nextInt(3) +1;
	
						if (numCount < 9) {
							if (n == 1 && oneCount < 4) {
								if (oneCount == 3 && hasFour) break;
								if (oneCount == 3) hasFour=true;
								boardstate[i][j] = 1;
								oneCount++;
								numCount++;
								nailedIt = true;					
							} else if (n == 2 && twoCount < 4) {
								if (twoCount == 3 && hasFour) break;
								if (twoCount == 3) hasFour=true;
								boardstate[i][j] = 2;
								twoCount++;
								numCount++;
								nailedIt = true;					
							} else if (n == 3 && threeCount < 4) {
								if (threeCount == 3 && hasFour) break;
								if (threeCount == 3) hasFour=true;
								boardstate[i][j] = 3;
								numCount++;
								threeCount++;
								nailedIt = true;
							}
						}
//						System.out.println("i " + i + " j " + j + " n "+ n + " empty " + emptyCount + " num " + numCount);
					}
				}
			}
		}
		return boardstate;
	}

	//pick a random tile location based on a bitmask of valid options. 
	public static int randomValidTileDrop(int resultVector) {
		Random r = new Random();
		int drop = r.nextInt(4);
		int infinite = 0;
		boolean dropped = false;
		//check until we get a valid column
		while (!dropped && infinite < 6) {
				int dropPow = new Double(Math.pow(2, drop)).intValue();
				int yes = dropPow & resultVector;
				if (yes > 0) {
						dropped = true;
				} else {
						drop = (drop+1) % 4;
				}
				
				infinite++;
		}
		if (infinite > 4) return -1;
		return drop;
	}
	// 	public static Board moveResult(Board b, Game.Direction dir) {
// 		return moveResult(b,dir,-1);
// 	}
// 	public static Board moveResult(Board b, Game.Direction dir, int nextTile) {
// 		Board next = new Board(b.getBoardstate());
// 		int moveResult;
// 		moveResult = next.moveLeft();
// 		if (moveResult<=0) return null;
// 		if (nextTile != -1)
// 			next.addTile(moveResult, Utilities.moveDirToInt(dir), nextTile);
// 		return next;
// 	}
	
// 	public static int[] countTiles(int[][] boardstate, int maxTile) {
// 		int[] tileCount = new int[maxTile+1];

// 		for (int i=0; i<=maxTile; i++) {
// 			tileCount[i]=0;
// 		}
// 		for (int i=0; i<4; i++) {
// 			for (int j=0; j<4; j++) {
// 				int thisTile = boardstate[i][j];
// 				tileCount[thisTile]++;
// 			}
// 		}
// 		return tileCount;
// 	}
	
// 	public static double tileDistance(int[][] boardstate, int tile) {
// 		int i1=-1,j1=-1,i2=-1,j2=-1;
// 		for (int i=0; i<4; i++) {
// 			for (int j=0; j<4; j++) {
// 				if (boardstate[i][j] == tile) {
// 					if (i1 < 0) {
// 						i1=i;
// 						j1=j;
// 					} else if (i2 < 0) {
// 						i2=i;
// 						j2=j;
// 					}
// 				}
// 			}
// 		}
// 		double d = Math.abs(i1-i2) + Math.abs(j1-j2);
// 		return d;
// 	}
// 	public static boolean isDeadBoard(Board b) {
// 		int[][] state = b.getBoardstate();
// 		//check rows
// 		for (int row = 0; row<4; row++) {
// 			int ia = state[row][0];
// 			int ib = state[row][1];
// 			int ic = state[row][2];
// 			int id = state[row][3];
// 			if (ia*ib*ic*id==0) return false;
// 			if (ia>=3 && ia==ib) return false;
// 			if (ib>=3 && ib==ic) return false;
// 			if (ic>=3 && ic==id) return false;
// 			if (ia+ib==3) return false;
// 			if (ib+ic==3) return false;
// 			if (ic+id==3) return false;
// 		}
// 		//check cols
// 		for (int col = 0; col<4; col++) {
// 			int ia = state[0][col];
// 			int ib = state[1][col];
// 			int ic = state[2][col];
// 			int id = state[3][col];
// 			if (ia>=3 && ia==ib) return false;
// 			if (ib>=3 && ib==ic) return false;
// 			if (ic>=3 && ic==id) return false;
// 			if (ia+ib==3) return false;
// 			if (ib+ic==3) return false;
// 			if (ic+id==3) return false;
// 		}
// 		return true;
// 	}
// 	public static int countOptions(Board b) {
// 		return countRowsMove(b) + countColsMove(b);
// 	}
// 	public static int countRowsMove(Board b) {
// 		int[][] state = b.getBoardstate();
// 		int movables = 0;
// 		int mergables = 0;
// 		//check rows
// 		for (int row = 0; row<4; row++) {
// 			int ia = state[row][0];
// 			int ib = state[row][1];
// 			int ic = state[row][2];
// 			int id = state[row][3];

// 			if (ia>=3 && ia==ib) mergables++;
// 			else if (ib>=3 && ib==ic) mergables++;
// 			else if (ic>=3 && ic==id) mergables++;
// 			else if (ia+ib==3 && ia*ib==2) mergables++;
// 			else if (ib+ic==3 && ib*ic==2) mergables++;
// 			else if (ic+id==3 && ic*id==2) mergables++;
// 			else if (ia*ib*ic*id==0) movables++;
// 		}
// 		return movables + mergables;
// 	}
	
// 	public static int countColsMove(Board b) {
// 		int[][] state = b.getBoardstate();
// 		int movables = 0;
// 		int mergables = 0;

// 		//check cols
// 		for (int col = 0; col<4; col++) {
// 			int ia = state[0][col];
// 			int ib = state[1][col];
// 			int ic = state[2][col];
// 			int id = state[3][col];
// 			if (ia>=3 && ia==ib) mergables++;
// 			else if (ib>=3 && ib==ic) mergables++;
// 			else if (ic>=3 && ic==id) mergables++;
// 			else if (ia+ib==3 && ia*ib==2) mergables++;
// 			else if (ib+ic==3 && ib*ic==2) mergables++;
// 			else if (ic+id==3 && ic*id==2) mergables++;
// 			else if (ia*ib*ic*id==0) movables++;
// 		}
// 		return movables + mergables;
// 	}	
// 	private static boolean canMoveLeft(Board b) {
// 		return canMoveRow(b,true);
// 	}
// 	private static boolean canMoveRight(Board b) {
// 		return canMoveRow(b,false);
// 	}
// 	private static boolean canMoveRow(Board b, boolean left) {
// 		int col = left ? 0 : 3;
// 		for (int i = 0; i < 4; i++) {
// 			if (canMoveRowFrom(b,i,col,left)) return true;
// 		}
// 		return false;
// 	}	
// 	private static boolean canMoveUp(Board b) {
// 		return canMoveCol(b,true);
// 	}
// 	private static boolean canMoveDown(Board b) {
// 		return canMoveCol(b,false);
// 	}
// 	private static boolean canMoveCol(Board b, boolean up) {
// 		int row = up ? 0 : 3;
// 		for (int j = 0; j < 4; j++) {
// 			if (canMoveColFrom(b,row,j,up)) return true;
// 		}
// 		return false;
// 	}
// 	private static boolean canMoveRowFrom(Board b, int row, int col, boolean left) {
// 		if (left && col == 3) return false;
// 		if (!left && col == 0) return false;
// 		int flip = left ? 1 : -1;
// 		int[][] boardstate = b.getBoardstate();
		
// 		if (boardstate[row][col] == 0) {
// 			return true;
// 		} else if (boardstate[row][col] == 1) {
// 			if (boardstate[row][col+flip] == 2) {
// 				return true;
// 			} else {
// 				return canMoveRowFrom(b,row,col+flip,left);
// 			}
// 		} else if (boardstate[row][col] == 2) {
// 			if (boardstate[row][col+flip] == 1) {
// 				return true;
// 			} else {
// 				return canMoveRowFrom(b,row,col+flip,left);
// 			}
// 		} else if (boardstate[row][col] == boardstate[row][col+flip]) {
// 			return true;
// 		} else {
// 			return canMoveRowFrom(b,row,col+flip,left);
// 		}
// 	}
// 	private static boolean canMoveColFrom(Board b, int row, int col, boolean up) {
// 		if (up && row == 3) return false;
// 		if (!up && row == 0) return false;
// 		int flip = up ? 1 : -1;
// 		int[][] boardstate = b.getBoardstate();

// 		if (boardstate[row][col] == 0) {
// 			return true;
// 		} else if (boardstate[row][col] == 1) {
// 			if (boardstate[row+flip][col] == 2) {
// 				return true;
// 			} else {
// 				return canMoveColFrom(b,row+flip,col,up);
// 			}
// 		} else if (boardstate[row][col] == 2) {
// 			if (boardstate[row+flip][col] == 1) {
// 				return true;
// 			} else {
// 				return canMoveColFrom(b,row+flip,col,up);
// 			}
// 		} else if (boardstate[row][col] == boardstate[row+flip][col]) {
// 			return true;
// 		} else {
// 			return canMoveColFrom(b,row+flip,col,up);
// 		}
// 	}
// 	public static double[] getOneLevelPerc(String boardStr) {
// 		//serialize board
// 		Board[] boards = new Board[4];
// 		int[] hits = new int[4];
// 		double[] perc = new double[4];
// 		int[] moveRes = new int[4];

// 		//move board (4x)
// 		for (int i = 0; i < 4; i ++) {
// 			boards[i] = new Board(boardStr);
// 			moveRes[i] = boards[i].moveDir(i);
// 			if (moveRes[i] < 0) moveRes[i] = 0;
// 			hits[i] = 0;
// 			perc[i] = 0;
// 		}
		
		
// 		for (Integer tile = 1; tile < 4; tile++) {
// 			//drop the tile in all possible locations
// 			int i = 0;
// 			boolean saved = false;
// 			while (!saved && i < 4) {
// 				boolean[] yes = new boolean[4];
// 				int dropPow = 1<<i;
// //				int dropPow = new Double(Math.pow(2, i)).intValue();
// 				for (int ii = 0; ii < 4; ii ++) {
// 					yes[ii] = (dropPow & moveRes[ii]) > 0;
// 				}
				
// 				if (yes[0]) {
// 					boards[0].overwriteTile(i, 3, tile);
// 					hits[0]++;
// 					perc[0] += BoardUtil.getDeadPercentage(boards[0].serialize(), null);
// 				}
// 				if (yes[1]) {
// 					boards[1].overwriteTile(3, i, tile);
// 					hits[1]++;
// 					perc[1] += BoardUtil.getDeadPercentage(boards[1].serialize(), null);
// 				}
// 				if (yes[2]) {
// 					boards[2].overwriteTile(i, 0, tile);
// 					hits[2]++;
// 					perc[2] += BoardUtil.getDeadPercentage(boards[2].serialize(), null);
// 				}
// 				if (yes[3]) {
// 					boards[3].overwriteTile(0, i, tile);
// 					hits[3]++;
// 					perc[3] += BoardUtil.getDeadPercentage(boards[3].serialize(), null);
// 				}
// 				i++;
// 			}
// 		}
// 		double[] retVal = new double[4];
// 		for (int i = 0; i < 4; i++) {
// 			if (hits[i] > 0) {
// 				retVal[i] = perc[i]/hits[i];
// 			} else {
// 				retVal[i] = 1;
// 			}
// 		}
// 		return retVal;
// 	}
// 	public static double getDeadPercentage(String boardStr, HashSet<Integer> checks) {
// 		//serialize board
// 		Board left = new Board(boardStr);
// 		Board up = new Board(boardStr);
// 		Board right = new Board(boardStr);
// 		Board down = new Board(boardStr);
		
// 		//move board (4x)
// 		int leftRes = left.moveLeft();
// 		int upRes = up.moveUp();
// 		int rightRes = right.moveRight();
// 		int downRes = down.moveDown();
// 		if (leftRes < 0) leftRes = 0;
// 		if (upRes < 0) upRes = 0;
// 		if (rightRes < 0) rightRes = 0;
// 		if (downRes < 0) downRes = 0;

// //		HashSet<Integer> checked = (HashSet<Integer>) checks.clone();
// 		HashSet<Integer> checked = new HashSet<Integer>();
// 		for (Integer tile = 1; tile < 4; tile++) {
// 			checked.add(tile);
// //		for (Integer tile : checks) {
// 			//drop the tile in all possible locations
// 			int i = 0;
// 			boolean saved = false;
// 			while (!saved && i < 4) {
// 				int dropPow = new Double(Math.pow(2, i)).intValue();
// 				boolean leftYes = (dropPow & leftRes) > 0;
// 				boolean upYes = (dropPow & upRes) > 0;
// 				boolean rightYes = (dropPow & rightRes) > 0;
// 				boolean downYes = (dropPow & downRes) > 0;
				
// 				if (leftYes) {
// 					left.overwriteTile(i, 3, tile);
// 					if (!BoardUtil.isDeadBoard(left)) {
// 						checked.remove(tile);
// 						saved = true;
// 					}
// 				}
// 				if (upYes) {
// 					up.overwriteTile(3, i, tile);
// 					if (!BoardUtil.isDeadBoard(up)) {
// 						checked.remove(tile);
// 						saved = true;
// 					}					
// 				}
// 				if (rightYes) {
// 					right.overwriteTile(i, 0, tile);
// 					if (!BoardUtil.isDeadBoard(right)) {
// 						checked.remove(tile);
// 						saved = true;
// 					}					
// 				}
// 				if (downYes) {
// 					down.overwriteTile(0, i, tile);
// 					if (!BoardUtil.isDeadBoard(down)) {
// 						checked.remove(tile);
// 						saved = true;
// 					}					
// 				}
// 				i++;
// 			}
// 		}
// //		System.out.print(boardStr + " ");
// //		for (Integer i : checked) System.out.print(i);
// //		System.out.println();
// 		return .32*checked.size();
// 	}
// 	public static int rowScore(Board b, int row) {
// 		int[][] boardstate = b.getBoardstate();
// 		int retVal = 0;
// 		for (int j = 0; j<4; j++) {
// 			int tile = boardstate[row][j];
// 			if (tile < 3) retVal += tile;
// 			else retVal += Math.pow(3, tile-2);			
// 		}
// 		return retVal;
// 	}
// 	public static int zeroCount(Board b) {
// 		int[][] bs = b.getBoardstate();
// 		int count = 0;
// 		for (int i=0; i<4; i++) {
// 			for (int j=0; j<4; j++) {
// 				if (bs[i][j] == 0) {
// 					count++;
// 				}
// 			}
// 		}
// 		return count;
// 	}
}
