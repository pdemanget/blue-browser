/**
 * board game.
 * TODO ui-model
 
 - model.getBoard
 - model.getComputerPlay
 - model.play()...
 - 
 */
var Board=function Board(){
var speedDelay=100;
var computerDelay=500;

var debug=0;//0 default none, 3 trace
var gplayer=1;
var oth;//the board
var gameover=false;
var score=new Array(3);
var round=0;
var lang= "en";
var i18n={
	en:{
		playerNames:['','Black','White']
	}
}
this.model=false;//true to update model only
this.depth=0;//simulation depth, 0 mean real.
//==technical
//generation html
this.doTable = function doTable(){
var i,j;
for(i=0;i<8;i++){
document.write('<tr>');
	for(j=0;j<8;j++){
	//document.write('<td><img src="othello/empty.png" onclick="doCell(this,'+i+','+j+')"/></td>');
	document.write('<td onclick="board.doCell(this,'+i+','+j+')"><div id="cell'+i+'_'+j+'" ></div> </td>');
	}
document.write('</tr>');
}
initGame();
}

this.doCell=function(cell,i,j){
	doTestAndPlay(gplayer,i,j);
}

/**
 * play ui
 **/
function doTestAndPlay(player,i,j){
	app.game.started=true;
	this.model=false;
	if(gameover) return;
	//outprintappend("click"+i+j+player+gplayer);
	var nb = doPlay(player,i,j,false);
	if(nb>0){
		setOpponentPlayer();
		outprintappend("Round "+(round++) +". Got "+nb+" changes ", 2);	
		doNextPlayer();
	}
}

function setOpponentPlayer(){
	var opponent = 3-gplayer;
	if( isPlayable(opponent)){
		gplayer=opponent;
	} else {
		if(! isPlayable(gplayer)){
			stopGames();
			return 1;
		}
	}
	
}

function doNextPlayer(){
 	if(gameover) return;
	outprintappend(i18n[lang].playerNames[gplayer]+ " please");
	if(app.params['player'+gplayer]=='Computer'){
		//playComputer(gplayer);
//		this.model=true;

		//var turn = computer4.playComputerModel(gplayer,round);
		var turn = playComputer(gplayer);
		
		if (turn.length) {
			doTestAndPlay(gplayer,turn[0],turn[1]);
		}else{
			setOpponentPlayer();
			doNextPlayer();
		}
	}
}


var delay=0;
function doPlaySwitchUI(player,i,j,time){
	outprintappend("playUI"+player+i+j,2);
	//delay= delay?delay+speedDelay:(speedDelay);
	if (!speedDelay){
		document.getElementById("cell"+i+"_"+j).className="player"+player;
		return;
	}
	
	delay= delay?delay+speedDelay:(speedDelay);
	setTimeout(function(){
		document.getElementById("cell"+i+"_"+j).className="player"+player;
		delay=0;
		lastPlayDelay=1;
	},time||delay);
}

function outprint(msg){
	outprintappend(msg,0);
}
var outprintappend=this.outprintappend= function outprintappend(msg,level){
	level=level || 0;
	if(level && level>debug) return;
	console.log(msg);
	var message=document.getElementById('message');
    if(message){
		if (level<1)
			setTimeout(function(){
				message.innerHTML =msg;
			},delay);
		/* message.innerHTML = msg += '<br/>' + message.innerHTML;
		else */
	}
}
//==game API

var initGame=function initGame(){
this.board=oth = new Array(8);
var i,j;
for(i=0;i<8;i++){
	oth[i]=new Array(8);
	for(j=0;j<8;j++){
	oth[i][j]=0;
	}
}
doPlayAdd(1,3,3);
doPlayAdd(1,4,4);
doPlayAdd(2,3,4);
doPlayAdd(2,4,3);
}.bind(this);

function doPlaySwitch(player,i,j,i0,j0,nb){
var k;
for(k=0;k<nb;k++){
	i+=i0;j+=j0;
	doPlayAdd(player,i,j);
}
}
function doPlayAdd(player,i,j,time){
outprintappend("doPlayAdd (change piece color) "+i+","+j+" player"+player+" "+time,3);
oth[i][j]=player;
if(!this.model) doPlaySwitchUI(player,i,j,time);
}

var lastPlayDelay=1;
//TODO reset to 100, 500
/*
var speedDelay=0;
var computerDelay=0;
*/
/*
algorithm 
loop in 8 direction till border, find other color, till ours, if none, return unplayable code ie 0.
*/
function doPlay(player,i,j,simu){
if(oth[i][j]!=0) return 0;
var i0,j0, rslt=0;
for(i0=-1;i0<=1;i0++)
for(j0=-1;j0<=1;j0++)
	rslt+=doPlayDir(player,i,j,i0,j0,simu);
if(simu) return rslt;
outprintappend("doPlay (try player"+player+" )"+i+","+j+" "+simu,2);

if(rslt>0){
 doPlayAdd(player,i,j,lastPlayDelay);
 if(!this.model) {
	//debugger; a real play
	outprintappend("doPlayAdd (change piece color) "+i+","+j+" player"+player+" ",3);
 }
// lastPlayDelay=delay;
}
return rslt;
}

this.doPlay=doPlay;


function doPlayDir(player,i1,j1,i0,j0, simu){
var i=i1,j=j1;
if((i0==0)&&(j0==0)) return 0;
var rslt=0;
var opp=3-player;
i+=i0;j+=j0;
while(inLimit(i) && inLimit(j) && (oth[i][j]==opp)){
	i+=i0;j+=j0;rslt++;
}
if(inLimit(i) && inLimit(j)&& oth[i][j]==player){
	if(simu) return rslt;
	doPlaySwitch(player,i1,j1,i0,j0,rslt);
	return rslt;
}
return 0;
}

function isPlayable(player){
	for(i=0;i<8;i++)
	for(j=0;j<8;j++){
		if(doPlay(player,i,j,true)>0) return true;
	}
	return false;
}

function stopGames(){
	score[1]=0;
	score[2]=0;
	for(i=0;i<8;i++)
	for(j=0;j<8;j++){
		score[oth[i][j]]++;
	}
	app.params.name1=app.params.name1||app.params.player1;
	app.params.name2=app.params.name2||app.params.player2;
	//scores[app.params.name1]=score[1];
	//scores[app.params.name2]=score[2];
	if( score[1]>score[2]){
		app.scores[app.params.name1].win++;
		app.scores[app.params.name2].lost++;
	}else{
		app.scores[app.params.name1].lost++;
		app.scores[app.params.name2].win++;	
	}
	localStorage['scoresWin'+app.params.name1] = app.scores[app.params.name1].win;
	localStorage['scoresWin'+app.params.name2] = app.scores[app.params.name2].win;
	localStorage['scoresLost'+app.params.name1] = app.scores[app.params.name1].lost;
	localStorage['scoresLost'+app.params.name2] = app.scores[app.params.name2].lost;
	gameover=true;
	//outprint("The game is over. <br/> <b>Black:</b> "+score[1]+"<br/><b>White:</b> "+score[2]);
	outprint("Black:</b> "+score[1]+" - <b>White:</b> "+score[2]);
	var bigmsg='';
	var human=0;
	if( score[1]==score[2]){
		bigmsg="TIE";
	}
	else if (app.params.player1 == 'Human' && app.params.player2 != 'Human'){
		if( score[1]>score[2]) bigmsg="You WIN";
		else bigmsg="You LOSE";
	}
	else if (app.params.player2 == 'Human' && app.params.player1 != 'Human'){
		if( score[2]>score[1]) bigmsg="You WIN";
		else bigmsg="You LOSE";
	} else if( score[1]>score[2]) bigmsg="Black WIN";
	else bigmsg="White WIN"
	
	document.getElementById('gameover').innerHTML=bigmsg;
	setTimeout(function(){
		document.getElementById('gameover').style.display="table-cell";
	},delay);
	
}

function inLimit(i){return((0<=i) &&(i<8));}
//==extension
var myarray=[
[8,0,6,4,4,6,0,8], 
[0,0,4,4,4,4,0,0], 
[6,4,4,4,4,4,4,6], 
[4,4,4,4,4,4,4,4], 
[4,4,4,4,4,4,4,4], 
[6,4,6,4,4,6,4,6], 
[0,0,4,4,4,4,0,0], 
[8,0,6,4,4,6,0,8]
]
function playComputer(gplayer){
	delay=delay+computerDelay;
	lastPlayDelay=delay;
	outprintappend("play computer "+gplayer,2);
	//just play in priority the best notated cells.
	for(k=8;k>=-1;k--)
	for(i=0;i<8;i++)
	for(j=0;j<8;j++){
		if(myarray[i][j]>=k){
			nb = doPlay(gplayer,i,j,true);//simu
			if(nb>0){
				return [i,j];
				
			}
		}
	}
	
	//setOpponentPlayer();
	//doNextPlayer();
};

this.restart = function restart(){
	if(!app.game.started){
		doNextPlayer();
		return;
	}

	if(gameover || confirm("Current game wil be lost, are you sure?") ){
		window.location.reload();	
	}
};
this.setBoard=function(board){this.board=oth=board;};
this.setGplayer=function(p){gplayer=p;};
this.getGplayer=function(p){return gplayer};

this.setRound=function(p){round=p;};
this.getBoard=function(){return oth};
//this.gplayer=gplayer;
this.clone=function(){
	var res = new Board();
	
	res.setGplayer(gplayer);
	res.setRound(round);
	res.model=true;
	var oth2 = new Array(8);
	var i,j;
	for(i=0;i<8;i++){
		oth2[i]=new Array(8);
		for(j=0;j<8;j++){
		oth2[i][j]=oth[i][j];
		}
	}
	res.setBoard(oth2);
	res.parent=this;
	res.depth=this.depth+1;
	//debugger; check clone
	return res;
};
//no indentation for that level: just a class wrapper
return this;
};
var board = new Board();
