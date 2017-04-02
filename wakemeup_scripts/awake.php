<?php 

   require("Alarm.class.php");
   require("Members.class.php");

   $alarm = new Alarm();
   $members  = new Members();
   $response = array();

   if (isset($_POST["cookie"])) {

	$cookie = filter_var($_POST["cookie"],FILTER_SANITIZE_FULL_SPECIAL_CHARS); 

	if(!empty($_POST["cookie"])){

		$members->cookie = $cookie;
		$found_member = $members->Search();

		if(!empty($found_member)){

			foreach($found_member as $o_member){

	   			 $members->id = $o_member["id"];

	   			 $alarm->idUser = $o_member["id"];
	   			 $alarm->chosen = "true";
	   			 $found_alarm = $alarm->Search();

				if (!empty($found_alarm)) {

					foreach($found_alarm as $o_alarm){

						$link = $o_alarm["ytlink"];
						$message = $o_alarm["msg"];

						$members->id = $o_alarm["idVoter"];
						$members->find();

						 if ($members !== null) {
						 	$pseudonyme = $members->pseudonyme;
						 }
						 else $pseudonyme = "";

						$response["statut"] = array("succes"=>"true", "link"=>$link, "message"=>$message, "voter"=>$pseudonyme);
					}

					header('Content-Type: application/json;charset=utf-8');
					echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);

				} else {
					$response["statut"] = array("succes"=>"false","error"=>"sql read error - no link");
					header('Content-Type: application/json;charset=utf-8');
					echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);
				}
			}

		}
		else{
			$response["statut"] = array("succes"=>"false","error"=>"sql search error ".$cookie);
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