<?php 

   require("Friends.class.php");
   require("Members.class.php");
   require("Message.class.php");

   $members  = new Members();
   $message = new Message();

   if (isset($_POST["cookie"]) && isset($_POST["person"]) && isset($_POST["message"])) {

   	$cookie = filter_var($_POST["cookie"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);
	$person = filter_var($_POST["person"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);
	$msg = filter_var($_POST["message"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);

	$msg = (strlen($msg) > 139) ? substr($msg,0,136).'...' : $msg;

	if(!empty($cookie) && !empty($person) && !empty($msg)){

		$members->cookie = $cookie;
		$found_member = $members->Search();

		if(!empty($found_member)){

			$members->pseudonyme = $person;
			$found_another = $members->Search();

			if(!empty($found_another)){

				foreach($found_member as $o_member){
					foreach ($found_another as $o_another) {

						$message->idSender = $o_member["id"];
						$message->idReceiver = $o_another["id"];
						$message->msg = $msg;
						$message->Create();

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
