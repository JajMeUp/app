<?php 

   require("Members.class.php");
   require("Friends.class.php");

   $friends = new Friends();
   $members  = new Members();
   $response = array();
   $arr_world = array();
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

	   		$members->mode = "world";
	   		$world_member = $members->Search();

	   		if(!empty($world_member)){
				foreach($world_member as $o_world){
					 if ($o_world["id"] !== $requestor) {

					 	$friends->idUser = $requestor;
					 	$friends->idAmi = $o_world["id"];
					 	$friends->pending = "false";
					 	$friends->hasAccepted = "true";
					 	$friend_member = $friends->Search();
					 	if(empty($friend_member))
					 	{
					 		$arr_world[] = $o_world["pseudonyme"];
					 	}
					 } 
				}

				$response["statut"] = array("succes"=>"true", "world"=>$arr_world);

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