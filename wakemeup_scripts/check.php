<?php 

   require("Members.class.php");

   $members  = new Members();
   $response = array();

   if (isset($_POST["cookie"])) {

	$cookie = filter_var($_POST["cookie"],FILTER_SANITIZE_FULL_SPECIAL_CHARS); 

	if(!empty($_POST["cookie"])){

		$members->cookie = $cookie;
		$found_member = $members->Search();

		if(!empty($found_member)){

			foreach($found_member as $o_member){

				$response["statut"] = array("succes"=>"true", "pseudo"=>$o_member["pseudonyme"]);

				header('Content-Type: application/json;charset=utf-8');
				echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);
			}

		}
		else{
			$response["statut"] = array("succes"=>"false", "error"=>"not exist");

			header('Content-Type: application/json;charset=utf-8');
			echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);	
		}

	}
	else {
		$response["statut"] = array("succes"=>"false","error"=>"empty error");
		header('Content-Type: application/json;charset=utf-8');
		echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);
	}

   } else {
	$response["statut"] = array("succes"=>"false","error"=>"isset error");
	header('Content-Type: application/json;charset=utf-8');
	echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);
   }