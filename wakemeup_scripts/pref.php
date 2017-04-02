<?php 

   require("Members.class.php");
	
   $members  = new Members();
   $response = array();
   $one_member = "";

   if (isset($_POST["cookie"]) && isset($_POST["pref"])) {

   	$cookie = filter_var($_POST["cookie"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);
	$pref = filter_var($_POST["pref"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);

	if(!empty($cookie) && !empty($pref)){

		if($pref === "world" || $pref === "friends" || $pref === "private"){

			$members->cookie = $cookie;
			$found_member = $members->Search();

			if(!empty($found_member)){

				foreach($found_member as $o_member){
					$one_member = $o_member["id"];
				}
				$members->id = $one_member;
				$members->mode = $pref;

				$members->Save();

				$response["statut"] = array("succes"=>"true");

				header('Content-Type: application/json;charset=utf-8');
				echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);

			}
			else {
				$response["statut"] = array("succes"=>"false","error"=>"sql search error ".$cookie);
				header('Content-Type: application/json;charset=utf-8');
				echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);
			}
		}
		else {
			$response["statut"] = array("succes"=>"false");
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
