<?php
	$con=mysqli_connect("mysql.cs.iastate.edu","dbu309ytb3","w#r4xdcb","db309ytb3");
	if(!$con){
		//echo "Failed To Connect to DB";
	}else{
		//echo "Successful Connection to DB.";
	}
	//GET a request to client for username and password
	$username = $_GET['username'];
    $password = $_GET['password'];
       //retrive actortype given a username
    $result = mysqli_query($con,"SELECT username, password FROM Actors WHERE username='".$username."' AND password='".$password."' AND banned=0;");
    $row = mysqli_fetch_array($result);
	$admin = mysqli_query($con, "SELECT username FROM Admin WHERE username='".$username."';");
	$adminRow = mysqli_fetch_array($admin);
	if($row!=null){
		if ($adminRow[0]!=null) {
			$myobj = (object)[];
			$myobj->message="Success";
			$myobj->message1 = "Admin";
			$myobj->username=$row[0];
			$myobj->password=$row[1];
			$myjsn=json_encode($myobj); }
		else {
			$myobj = (object)[];
			$myobj->message="Success";
			$myobj->message1 = "NA";
			$myobj->username=$row[0];
			$myobj->password=$row[1];
			$myjsn=json_encode($myobj);
		}
	}else{
		$myobj = (object)[];
	$myobj->message="Failure";
	$myobj->username="";
	$myobj->password="";
	$myjsn=json_encode($myobj);
	}
	echo $myjsn;
    mysqli_close($con);
?>
