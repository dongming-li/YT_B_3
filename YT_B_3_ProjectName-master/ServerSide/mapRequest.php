<?php
	$con=mysqli_connect("mysql.cs.iastate.edu","dbu309ytb3","w#r4xdcb","db309ytb3");
	if(!$con){
		//echo "Failed To Connect to DB";
	}else{
		//echo "Successful Connection to DB.";
	}
	//GET a request to client for username and password
	//$mapName = $_GET['mapname'];
	$mapID=0;
    //retrive mapID given a username
    //$result = mysqli_query($con,"SELECT mapID FROM Map WHERE mapID=".$mapID.";");
    //$mapID = mysqli_fetch_array($result);
	//if row is null, just return null
	//if($mapID[0]==null){
	//	$myjsn=json_encode($mapID);
	//	echo $myjsn;
	//}else{
		$resultTiles=mysqli_query($con,"SELECT x,y,data FROM Maptile WHERE mapID=".$mapID.";");
		$array_result = array(
			"mapID"=>$mapID,
			"mapName"=>"default"
		);
		while ($row = mysqli_fetch_array($resultTiles)) {
			$arrobj=array("x"=>$row["x"],"y"=>$row["y"],"data"=>$row["data"]);
			$array_result[] = $arrobj;	
		}
		//$tilesArray=mysqli_fetch_array($resultTiles);
		$myjsn=json_encode($array_result);
		echo $myjsn;
	//}
    mysqli_close($con);
?>