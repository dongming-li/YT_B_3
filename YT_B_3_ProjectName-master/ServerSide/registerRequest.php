<?php
	$con=mysqli_connect("mysql.cs.iastate.edu","dbu309ytb3","w#r4xdcb","db309ytb3");
	if(!$con){
		//echo "Failed To Connect to DB";
	}else{
		//echo "Successful Connection to DB.";
	}
	
	$username = $_GET['username'];
    $password = $_GET['password'];
	
	$result = mysqli_query($con,"SELECT * FROM Actors WHERE username='".$username."' AND password='".$password."';");
	$row = mysqli_fetch_array($result);
	if($row!=null){
        		$obj=(object)['username'=>"",'password'=>"",'message'=>"Failure"];
			echo json_encode($obj);
	}else{
				mysqli_query($con,"INSERT INTO Actors(username,password,banned) VALUES ('".$username."','".$password."', 0)");
			$obj=(object)['username'=>$username,'password'=>$password,'message'=>"Success"];
			echo json_encode($obj);
	}
	echo $myjsn;
    mysqli_close($con);
	
?>