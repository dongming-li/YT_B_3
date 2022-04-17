<?php
	$con=mysqli_connect("mysql.cs.iastate.edu","dbu309ytb3","w#r4xdcb","db309ytb3");
	if(!$con){
		//echo "Failed To Connect to DB";
	}else{
		//echo "Successful Connection to DB.";
	}
	$result=mysqli_query($con,"SELECT * FROM Map");
	$array_result=array("maps"=>"many");
	while($row=mysqli_fetch_array($result)){
		$arrObj=array("mapid"=>$row["mapid"],"mapName"=>$row["mapName"],"username"=>$row["username"]);
		$array_result[] = $arrObj;
	}
	$myjsn=json_encode($array_result);
	echo $myjsn;
	mysqli_close($con);
?>