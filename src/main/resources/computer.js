/**
 * Advanced computer mode.
 * minimax algorithm
 * depends on board.doPlay, board.doTestAndPlay
 Todo board.getRound(), board.getBoard(i,j) board.getScore() board.clone()
 *
 * archi
 playComputerModel(board)
 scoreBoard(board)
 
 c.ScoreBoard(board):
 ^ i,j
 note[i,j]=c.scoreCell(board,i,j)
 
 c.scoreCell(board,i,j):
 isLeaf(note en dur)
 else
 b2=b.clone();
 b2.playModel(i,j);
 c.scoreBoard(b2)
 
 
 */
var Computer=function Computer(pboard, depth){
var MIN=-10000;
var MAX= 10000;

this.gplayer=pboard.gplayer;
this.board=pboard;
this.depth=depth;

function inLimit(i){return((0<=i) &&(i<8));}
//==extension
var cellBonus=[
[8,0,6,4,4,6,0,8], 
[0,0,4,4,4,4,0,0], 
[6,4,4,4,4,4,4,6], 
[4,4,4,4,4,4,4,4], 
[4,4,4,4,4,4,4,4], 
[6,4,6,4,4,6,4,6], 
[0,0,4,4,4,4,0,0], 
[8,0,6,4,4,6,0,8]
];
var m=-65;
var cellScore=this.cellScore=[
[m,m,m,m,m,m,m,m],
[m,m,m,m,m,m,m,m],
[m,m,m,m,m,m,m,m],
[m,m,m,m,m,m,m,m],
[m,m,m,m,m,m,m,m],
[m,m,m,m,m,m,m,m],
[m,m,m,m,m,m,m,m],
[m,m,m,m,m,m,m,m]
];
maxIndex = function(arr){
	var mx=m;
	var res=[];
	for(i=0;i<8;i++)
	for(j=0;j<8;j++){
		if (arr[i][j]>mx){
			mx=arr[i][j];
			res[0]=i;
			res[1]=j;
		}
	}
	return res;
}
min = function(arr){
	var res=MAX;
	for(i=0;i<8;i++)
	for(j=0;j<8;j++){
		if (arr[i][j]<res){
			res=arr[i][j];
		}
	}
	return res;
}
max = function(arr){
	var res=MIN;
	for(i=0;i<8;i++)
	for(j=0;j<8;j++){
		if (arr[i][j]>res){
			res=arr[i][j];
		}
	}
	return res;
}

this.playComputerModel = function(gplayer, round){
	board.outprintappend("playComputerModel player"+gplayer+" round "+round,3);
	this.gplayer=gplayer;
	this.scoreBoard(board.clone(),depth);
	//play the best notated cell given by min max algorithm.
	var indexMax=maxIndex(cellScore);
	return indexMax;//return result, let board make the ui updates.
	/*
	var nb = doTestAndPlay(gplayer,indexMax[0],indexMax[1]);
	setOpponentPlayer();
	doNextPlayer();
	*/
};
this.scoreBoard = function(board,depth){
	/*board=board||this.board;
	depth=depth||this.depth;*/
	board.outprintappend("board player"+this.gplayer+" depth "+depth,3);
	/*delay=delay+computerDelay;
	lastPlayDelay=delay;
	outprintappend("play computer "+gplayer,2);*/
	//just play in priority the best notated cells.
	//optim inutile sur l'ordre des cell for(k=8;k>=-1;k--)
	for(i=0;i<8;i++)
	for(j=0;j<8;j++){
	//	if(cellBonus[i][j]>=k){
			//TODO exit if score outside alpha or beta;
			this.scoreCell(board,i,j,depth);
	//	}
	}
	//debugger;//voir le résultat du board
}

this.scoreCell = function(board,i,j,depth){
	board.outprintappend("cell player"+this.gplayer+" depth "+depth,3);
	var myTurn= board.gplayer==this.gplayer;
	var nb=	board.doPlay(board.getGplayer(),i,j,true);
	if(nb==0) return;
	if(depth==0){
		return cellScore[i][j]=myTurn?cellBonus[i][j]:-cellBonus[i][j];
	}else{
		//cloner,jouer, calculer les score du clone
		var board2=board.clone();
		board2.doPlay(this.gplayer,i,j,false);
		var c2=new Computer(board2,depth-1);
		c2.scoreBoard(board2,depth-1);
		cellScore[i][j]=myTurn?max(c2.cellScore):min(c2.cellScore);
	}
}

//no indentation for that level: just a class wrapper
return this;
};
var computer4 = new Computer(board,0);
