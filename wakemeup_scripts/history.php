<?php 

   require("Members.class.php");
   require("Alarm.class.php");

   $alarm = new Alarm();
   $members  = new Members();
   $response = array();
   $arr_voter = array();
   $arr_ytlink = array();
   $requestor = "";

   if (isset($_POST["cookie"])) {

	$cookie = filter_var($_POST["cookie"],FILTER_SANITIZE_FULL_SPECIAL_CHARS); 

	if(!empty($_POST["cookie"])){

		$members->cookie = $cookie;
		$found_member = $members->Search();

		if(!empty($found_member)){

			foreach($found_member as $o_member){

	   			 $requestor = $o_member["id"];
	   		}

	   		$alarm->idUser = $requestor;
	   		$alarm_member = $alarm->Search();

	   		if(!empty($alarm_member)){
				foreach($alarm_member as $o_alarm){
					 if ($o_alarm["chosen"] === "false") {

					 	$arr_ytlink[] = $o_alarm["ytlink"];

					 	$members->id = $o_alarm["idVoter"];
					 	$members->find();

						if ($members !== null) {
							$arr_voter[] = $members->pseudonyme;
						}
					 } 
				}

				$response["statut"] = array("succes"=>"true", "voters"=>$arr_voter, "ytlinks"=>$arr_ytlink);

				header('Content-Type: application/json;charset=utf-8');
				echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);

			}
			else {
					$response["statut"] = array("succes"=>"false","error"=>"sql read error");
					header('Content-Type: application/json;charset=utf-8');
					echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);
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