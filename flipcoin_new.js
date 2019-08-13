	var play=0;
	var heads=0,tails=0;
	var trials=1;
	var i=0,j=1;
	var flips=0;
	function flipcount(flips)
	{
		if(i<=flips)
			return ++i;
		else {
			i=0;
			return 1;
		}
	}
	function trialcount(flips)
	{
		if(j==flips&&trials<10){
			j=1;i=0;
			return trials++;
		}
		else if(trials<=10){
			j++;
			return trials;
		}
	}
	function validateInput()
	{
		if(getIdValue("flip_value")==""){
			alert("Please Enter Number Of Tosses..!");
			i=0;j=1;
			return false;
		}
		else if(parseInt(getIdValue("flip_value"))<=0||parseInt(getIdValue("bet_value"))<=0)
		{
			alert("Error!Please Give Positive Values(>0).");
			i=0;j=1;
			return false;
		}
		else if(getIdValue("bet_value")=="")
		{
			alert("Please Enter Bet Value..!");
			i=0;j=1;
			return false;
		}
		else if(parseInt(getIdValue("bet_value"))>parseInt(getInner("u_wallet")))
		{
			alert("Please Enter Bet Value Less Than "+getInner("u_wallet"));
			i=0;j=1;
			return false;
		}
                else if(parseInt(getIdValue("bet_value"))>parseInt(getInner("comp_wallet")))
		{
			alert("Please Enter Bet Value Less Than "+getInner("comp_wallet"));
			i=0;j=1;
			return false;
		}
		else {
			document.getElementById("flip_value").disabled=true;
			return true;
		}
	}
	function toss()
	{
		var button=document.getElementById("b1");
		flips=parseInt(getIdValue("flip_value"));
		if(button.value=="Toss" || button.value=="Continue" || button.value=="Toss Again" ){
			var validation=validateInput();
			if(validation==true)
			{
				var flipno=flipcount(flips);
				var trialno=trialcount(flips);
				if(flipno<=flips && trialno<=10)
				{
					//var heads_probability=levelCheck();
					//document.getElementById("levelbox").disabled=true;
					document.getElementById("radio_val1").disabled=true;
					document.getElementById("radio_val2").disabled=true;
					document.getElementById("victory").currentTime=0;
					document.getElementById("victory").pause();
					document.getElementById("gameover").currentTime=0;
					document.getElementById("gameover").pause();
					if(button.value=="Toss Again"){
						setInner("heads",0);
						setInner("tails",0);
						setInner("tossno",0);
					}
					setInner("gameplayno",play+1);
					setInner("tossno",flipno);
					setInner("trials",trialno);
					setInner("trials1",trialno);
					coinflip();
					if(flipno<flips){
						setIdValue("b1","Continue");
						document.getElementById("statusimage").src="continue.png";
					}
					setTimeout(function(){
						if(flipno==flips){
							setInner("heads",heads);
							setInner("tails",tails);
							var guessed=document.getElementsByName("guess");
							if(guessed[0].checked){
								check(heads,tails,"HEADS",trials,trialno,flipno);
							}
							else if(guessed[1].checked){
								check(heads,tails,"TAILS",trials,trialno,flipno);
							}
							document.getElementById("radio_val1").disabled=false;
							document.getElementById("radio_val2").disabled=false;
							i=0;
						}
					},100);
				}
			}
		}
		else if(button.value=="Play Again"){
			reset();
		}
	}
	function coinflip(){
		var flipResult=Math.random();
		var object=document.getElementById("coin");
		object.className="";
		document.getElementById("coinflip").play();
		setTimeout(function(){
			if(flipResult<=0.5){
				heads++;
				setInner("heads",heads);
				object.className="heads";
				document.getElementById("heads_audio").volume=0.6;
				document.getElementById("heads_audio").play();
			}
			else{
				tails++;
				setInner("tails",tails);
				object.className="tails";
				document.getElementById("tails_audio").volume=0.6;
				document.getElementById("tails_audio").play();
			}
		},50);
	}
	function check(heads,tails,guess,trials,trialno,flipno)
	{
		var user_wallet=parseInt(getInner("u_wallet"));
		var comp_wallet=parseInt(getInner("c_wallet"));
		var score=parseInt(getInner("score_val"));
		var pool=parseInt(getInner("pool_val"));
		var bet_value=parseInt(getIdValue("bet_value"));
		if(heads>tails)
		{
			if(flipno!=1){
				document.getElementById("heads_win").volume=0.5;
				document.getElementById("heads_win").play();
			}
			if(guess=="HEADS")
				addScore(user_wallet,comp_wallet,score,pool,bet_value,trialno);
			else
				deleteScore(user_wallet,comp_wallet,score,pool,bet_value,trialno);
		}
		else if(heads==tails){
			document.getElementById("no_score").play();
			setIdValue("b1","Toss Again");
		}
		else{
			if(flipno!=1){
				document.getElementById("tails_win").volume=0.5;
				document.getElementById("tails_win").play();
			}
			if(guess=="TAILS")
				addScore(user_wallet,comp_wallet,score,pool,bet_value,trialno);
			else
				deleteScore(user_wallet,comp_wallet,score,pool,bet_value,trialno);
		}
		resetHeadsAndTails();
	}
	function addScore(user_wallet,comp_wallet,score,pool,bet_value,trialno)
	{
		user_wallet+=bet_value;
		comp_wallet-=bet_value;
		if(comp_wallet<0) comp_wallet=0;
		score+=bet_value;
		pool-=bet_value;
		if(pool<0) pool=0;
		setScore(user_wallet,comp_wallet,score,pool,bet_value,trialno);
	}
	function deleteScore(user_wallet,comp_wallet,score,pool,bet_value,trialno)
	{
		user_wallet-=bet_value;
		comp_wallet+=bet_value;
		if(user_wallet<0) user_wallet=0;
		score-=bet_value;
		pool+=bet_value;
		if(score<0) score=0;
		setScore(user_wallet,comp_wallet,score,pool,bet_value,trialno);
	}
	function setScore(user_wallet,comp_wallet,score,pool,bet_value,trialno)
	{
		setInner("u_wallet",user_wallet);
		setInner("c_wallet",comp_wallet);
		setInner("score_val",score);
		setInner("pool_val",pool);
		setInner("limit",user_wallet);
		document.getElementById("cashwin").volume=0.4;
		document.getElementById("cashwin").play();
		winnerOrLoser(user_wallet,comp_wallet,trialno);
	}
	function resetHeadsAndTails()
	{
		heads=0;
		tails=0;
	}
	function winnerOrLoser(user_wallet,comp_wallet,trialno)
	{
		if((user_wallet>comp_wallet&&comp_wallet==0)&&trialno<=10){
			document.getElementById("statusimage").src="Youwin.png";
			/*if(trialno>1)
				window.confirm("Congratulations YOU WON in "+(trialno)+ " trials.\nGame Over.\n Do you want to play again?");
			else
				window.confirm("Congratulations YOU WON in "+(trialno)+ " trial.\nGame Over.\n Do you want to play again?");*/
			setIdValue("b1","Play Again");
			document.getElementById("youwin").volume=0.7;
			document.getElementById("youwin").play();
			document.getElementById("victory").volume=0.2;
			document.getElementById("victory").play();
			createRow(play,trialno,user_wallet,comp_wallet,"WIN");
		}
		else if(user_wallet==comp_wallet&&trialno==10){
			setIdValue("b1","Play Again");
			//window.alert("Game Tied . Try Again..!\nDon't Lose Hope.");
			document.getElementById("statusimage").src="tie.png";
			document.getElementById("tie_game").play();
			createRow(play,trialno,user_wallet,comp_wallet,"TIE");
		}
		else if(trialno==10||user_wallet==0){
			setIdValue("b1","Play Again");
			document.getElementById("statusimage").src="YouLose.png";
			/*if(trialno==10)
				window.alert("Out Of Trials .\n You Lost The Game.\nDon't Worry Please Try Again..!");
			else
				window.alert("Your Wallet Is Empty .\n You Lost The Game.\nDon't Worry Please Try Again..!");*/
			document.getElementById("youlose").volume=0.7;
			document.getElementById("youlose").play();
			document.getElementById("gameover").volume=0.2;
			document.getElementById("gameover").play();
			createRow(play,trialno,user_wallet,comp_wallet,"LOSE");
		}
		else if(trialno<10){
			setIdValue("b1","Toss Again");
			document.getElementById("statusimage").src="tossagain.png";
		}
	}
	function createRow(play,trialno,user_wallet,comp_wallet,str)
	{
		var table=document.getElementById("data");
		var row=table.insertRow(play);
		var cell1=row.insertCell(0);
		var cell2=row.insertCell(1);
		var cell3=row.insertCell(2);
		var cell4=row.insertCell(3);
		var cell5=row.insertCell(4);
		cell1.style.width="72px";
		cell2.style.width="40px";
		cell3.style.width="80px";
		cell4.style.width="92px";
		cell5.style.width="40px";
		window.play=window.play+1;
		cell1.innerHTML=window.play;
		cell2.innerHTML=trialno;
		cell3.innerHTML=user_wallet;
		cell4.innerHTML=comp_wallet;
		cell5.innerHTML=str;
	}
	function reset()
	{
		setIdValue("b1","Toss");
		setInner("heads",0);
		setInner("tails",0);
		setInner("limit",100);
		setInner("u_wallet",100);
		setInner("c_wallet",100);
		setInner("score_val",0);
		setInner("pool_val",0);
		setIdValue("bet_value",50);
		setInner("trials",0);
		setInner("trials1",0);
		setInner("tossno",0);
		setInner("gameplayno",play+1);
		setInner("tossmax",getIdValue("flip_value"));
		document.getElementById("statusimage").src="toss.png";
		document.getElementById("flip_value").disabled=false;
		document.getElementsByName("guess")[0].checked=true;
		document.getElementById("radio_val1").disabled=false;
		document.getElementById("radio_val2").disabled=false;
		//document.getElementById("levelbox").disabled=false;
		trials=1;
		heads=0;
		tails=0;
		i=0;j=1;
		flips=0;
	}
	/*function levelCheck()
	{
		var level=document.getElementsByName("gamelevel");
		var guessed=document.getElementsByName("guess");
		if(level[0].selected){
			if(guessed[0].checked)
				return 0.7;
			else 
				return 0.3;
		}
		else if(level[1].selected){
				return 0.5;
		}
		else{
			if(guessed[0].checked)
				return 0.3;
			else 
				return 0.7;
		}
	}*/
	//To set flipvalue by onchange event
	function myFunction(flip) 
	{
		var f=document.getElementById("tossmax");
		var fbox=document.getElementById("flip_value").value;
		if(fbox=="")
			alert("Please enter a value > 0 ");
		else
			f.innerHTML=flip;
	}
	function getIdValue(s)
	{
		return document.getElementById(s).value;
	}
	function setIdValue(s,val)
	{
		document.getElementById(s).value=val;
	}
	function setInner(s,val)
	{
		document.getElementById(s).innerHTML=val;
	}
	function getInner(s)
	{
		 return document.getElementById(s).innerHTML;
	}
	function displayRules()
	{
		document.getElementById("rules").style.display="block";
	}
	function noDisplay()
	{
		document.getElementById("rules").style.display="none";
	}
