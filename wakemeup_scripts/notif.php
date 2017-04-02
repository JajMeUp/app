<?php 

   require("Friends.class.php");
   require("Members.class.php");

   $friends = new Friends();
   $members  = new Members();
   $response = array();
   $arr_friends = array();

   if (isset($_POST["cookie"])) {

	$cookie = filter_var($_POST["cookie"],FILTER_SANITIZE_FULL_SPECIAL_CHARS); 

	if(!empty($_POST["cookie"])){

		$members->cookie = $cookie;
		$found_member = $members->Search();

		if(!empty($found_member)){

			foreach($found_member as $o_member){

	   			 $members->id = $o_member["id"];

	   			 $friends->idUser = $o_member["id"];
	   			 $found_friends = $friends->Search();

				if (!empty($found_friends)) {

					foreach($found_friends as $o_friend){

						if($o_friend["pending"] === "true")
						{
							$members->id = $o_friend["idAmi"];
						 	$members->find();

						 	if ($members !== null) {
						 		$arr_friends[] = $members->pseudonyme;
						 	} 
						}

					}

					$response["statut"] = array("succes"=>"true", "amis"=>$arr_friends);

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