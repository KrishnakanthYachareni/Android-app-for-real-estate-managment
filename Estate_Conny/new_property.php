<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['contact']) && isset($_POST['name']) && isset($_POST['location']) && isset($_POST['status']) && isset($_POST['price']) 
               && isset($_POST['description'])) {
    
	$contact = $_POST['contact'];
    $name = $_POST['name'];
    $location = $_POST['location'];
    $status = $_POST['status'];
	$price = $_POST['price'];
    $description = $_POST['description'];
    $activation = 'No';

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql inserting a new row
    $result = mysql_query("INSERT INTO property(contact, name, location, status, price, description, activation) VALUES('$contact','$name', '$location', '$status','$price','$description','$activation')");

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Property Successfully Added.";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
        
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>