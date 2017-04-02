<?php 

   require("Friends.class.php");
   require("Members.class.php");
   require("Alarm.class.php");
	
   $friends = new Friends();
   $members  = new Members();
   $alarm = new Alarm();
   $response = array();

   if (isset($_POST["cookie"]) && isset($_POST["member"]) && isset($_POST["link"]) && isset($_POST["message"])) {

   	$cookie = filter_var($_POST["cookie"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);
	$to_member = filter_var($_POST["member"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);
	$link = filter_var($_POST["link"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);
	$msg = filter_var($_POST["message"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);

	$msg = (strlen($msg) > 139) ? substr($msg,0,136).'...' : $msg;

	if(!empty($cookie) && !empty($to_member) && !empty($link) && !empty($msg)){

		$members->cookie = $cookie;
		$found_member = $members->Search();

		if(!empty($found_member)){

			$members->pseudonyme = $to_member;
			$found_another = $members->Search();

			if(!empty($found_another)){

				foreach($found_member as $o_member){
					foreach ($found_another as $o_another) {

						$alarm->idUser = $o_another["id"];
						$alarm->chosen = "true";
						$found_clocksongs = $alarm->Search();

						foreach ($found_clocksongs as $o_clocksongs) {
							$alarm->id = $o_clocksongs["id"];
							$alarm->chosen = "false";

							//if($alarm->Save() !== null) OK else KO;
							$alarm->Save();
						}

						$alarm->idUser = $o_another["id"];
						$alarm->idVoter = $o_member["id"];
						$alarm->ytlink = $link;
						$alarm->msg = $msg;
						$alarm->enabled = "true";
						$alarm->chosen = "true";

						$creation = $alarm->Create();

						$response["statut"] = array("succes"=>"true");

						header('Content-Type: application/json;charset=utf-8');
						echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);
					}
				}

			}
			else{
				$response["statut"] = array("succes"=>"false","error"=>"sql other search error ".$cookie);
				header('Content-Type: application/json;charset=utf-8');
				echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);
			}
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
