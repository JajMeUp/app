<?php 

   require("Friends.class.php");
   require("Members.class.php");
	
   $friends = new Friends();
   $members  = new Members();
   $response = array();

   if (isset($_POST["cookie"]) && isset($_POST["friend"])) {

   	$cookie = filter_var($_POST["cookie"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);
	$new_friend = filter_var($_POST["friend"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);

	if(!empty($cookie) && !empty($new_friend)){

		$members->cookie = $cookie;
		$found_member = $members->Search();

		if(!empty($found_member)){

			$members->pseudonyme = $new_friend;
			$found_another = $members->Search();

			if(!empty($found_another)){

				foreach($found_member as $o_member){
					foreach ($found_another as $o_another) {

						$friends->idUser = $o_member["id"];
						$friends->idAmi = $o_another["id"];
						$friends->Create();

						$friends->idUser = $o_another["id"];
						$friends->idAmi = $o_member["id"];
						$friends->pending = "true";
						$friends->Create();

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
