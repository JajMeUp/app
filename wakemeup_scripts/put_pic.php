<?php 

   require("Members.class.php");

   $members  = new Members();

   if (isset($_POST["cookie"]) && isset($_POST["image"])) {

   	$cookie = filter_var($_POST["cookie"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);
   	// !!!
   	$image = $_POST["image"];

	//RegEx image base64

	if(!empty($cookie) && !empty($image)){

		$members->cookie = $cookie;
		$found_member = $members->Search();

		if(!empty($found_member)){

			foreach($found_member as $o_member){

				$filename = "pic"+$o_member["pseudonyme"];
				file_put_contents($filename, $image);
				$members->id = $o_member["id"];
				$members->image = $filename;
				$members->Save();
			}

			$response["statut"] = array("succes"=>"true");

			header('Content-Type: application/json;charset=utf-8');
			echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);
		}
		else {
			$response["statut"] = array("succes"=>"false","error"=>"sql self search error ".$cookie);
			header('Content-Type: application/json;charset=utf-8');
			echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);
		}
	}
	else {
		$response["statut"] = array("succes"=>"false");
		header('Content-Type: application/json;charset=utf-8');
		echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);
	}

   } else {
	$response["statut"] = array("succes"=>"false");
	header('Content-Type: application/json;charset=utf-8');
	echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);
   }
