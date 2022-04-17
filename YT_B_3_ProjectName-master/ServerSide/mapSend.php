<?php
	error_log(print_r($_GET, true),0);
	$con=mysqli_connect("mysql.cs.iastate.edu","dbu309ytb3","w#r4xdcb","db309ytb3");
	if(!$con){
		//echo "Failed To Connect to DB";
	}else{
		//echo "Successful Connection to DB.";
	}
	$i=0;
	//recieving a jsonobject containing the user information needed
	//Create a mapid to store the map under
	$result=mysqli_query($con,"Select MAX(mapid) From Map");
	$resultArray=mysqli_fetch_array($result);
	$mapid=$resultArray[0]+1;
	$username=$_GET['username'];
	$mapName=$_GET['mapName'];
	//inserting map information into db//complete
	$success=mysqli_query($con,"Insert into Map(username,mapid,mapName) 
				values('".$username."',".$mapid.",'".$mapName."');");
	//iterate over all tiles, x,y,data and store in db.
	while(isset($_GET[(string)$i])){
		$jsonobj=$_GET[(string)$i];
		$arr=json_decode($jsonobj, true);
		//sql statement to store, mapid,x,y,data
		//switch case on data for terrain type
		switch ($arr['data']) {
			case 114: // open room "r"
					mysqli_query($con,"Insert into Maptile(mapid,x,y,data)
						values(".$mapid.",".$arr['x'].",".$arr['y'].",'".'r'."')");
					break;
			case 115: // down stairs "s"
					mysqli_query($con,"Insert into Maptile(mapid,x,y,data)
						values(".$mapid.",".$arr['x'].",".$arr['y'].",'".'s'."')");
					break;
			case 105: // impassable "i"
					mysqli_query($con,"Insert into Maptile(mapid,x,y,data)
						values(".$mapid.",".$arr['x'].",".$arr['y'].",'".'i'."')");
					break;
			case 102: // up stairs "f"
					mysqli_query($con,"Insert into Maptile(mapid,x,y,data)
						values(".$mapid.",".$arr['x'].",".$arr['y'].",'".'f'."')");
					break;
			case 'm':
					mysqli_query($con,"Insert into Maptile(mapid,x,y,data)
						values(".$mapid.",".$arr['x'].",".$arr['y'].",'".'m'."')");
						break;
			default :
					mysqli_query($con,"Insert into Maptile(mapid,x,y,data)
						values(".$mapid.",".$arr['x'].",".$arr['y'].",'".'r'."')");

		}

		$i++;
	}
	$myobj=new \stdClass();
	$myobj->message=$success;
	$myjsn=json_encode($myobj);
	echo $myjsn;
	mysqli_close($con);
?>
