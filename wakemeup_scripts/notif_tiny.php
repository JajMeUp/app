<?php

   require("Messenger.php");
   require("Members.class.php");

   $members  = new Members();
   $messages = new Messenger();
   $response = array();
   $arr_senders = array();
   $arr_msg = array();

   if (isset($_POST["cookie"])) {

	$cookie = filter_var($_POST["cookie"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);

	if(!empty($_POST["cookie"])){

		$members->cookie = $cookie;
		$found_member = $members->Search();

		if(!empty($found_member)){

			foreach($found_member as $o_member){

	   			 $members->id = $o_member["id"];

	   			 $messages->idOther = $o_member["id"];
	   			 $found_messages = $messages->Search();

				if (!empty($found_messages)) {

					foreach($found_messages as $o_message){

						if ($o_message["read"] === "false")
						{
							$members->id = $o_message["idUser"];
							$members->find();

							 if ($members !== null) {
							 	$arr_senders[] = $members->pseudonyme;
							 	$arr_msg[] = $o_message["msg"];
							 }

							 $messages->id = $o_message["id"];
							 $message->read = "true";
						}

					}

					$response["statut"] = array("succes"=>"true", "senders"=>$arr_senders, "messages"=>$arr_msg);

					header('Content-Type: application/json;charset=utf-8');
					echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);

				} else {
					$response["statut"] = array("succes"=>"false","error"=>"sql read error");
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
