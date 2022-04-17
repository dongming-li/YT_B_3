<?php
	$con=mysqli_connect("mysql.cs.iastate.edu","dbu309ytb3","w#r4xdcb","db309ytb3");
	if(!$con){
		//echo "Failed To Connect to DB";
	}else{
		//echo "Successful Connection to DB.";
	}
	
	$username = $_GET['username'];
	$banner = $_GET['banner'];
	
	// Set "username" banned status to '1'
	$result = mysqli_query($con,"UPDATE Actors SET Banned = '1' WHERE username='".$username."';");
    $row = mysqli_fetch_array($result);
	
	// Add 1 to admin ban count
	$admin = mysql_query($con, "SELECT Bans FROM Admin WHERE username='".$banner."';");
	$adminRow = mysqli_fetch_array($admin);
	$banCount = $adminRow[0] + 1;
	
	$result1= mysqli_query($con,"UPDATE Admin SET Bans = '".$banCount."' WHERE username='".$banner."';");
    $row1 = mysqli_fetch_array($result1);
	
	echo $username;
	echo $banner;
	echo $banCount;
    mysqli_close($con);
?>
