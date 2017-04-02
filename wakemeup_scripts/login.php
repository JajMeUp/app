<?php 

   require("Members.class.php");
	
   $members  = new Members();
   $once = false;
   $response = array();

   if (isset($_POST["user"]) && isset($_POST["password"])) {

	$user = filter_var($_POST["user"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);
	$mdp = filter_var($_POST["password"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);

	if(!empty($user) && !empty($mdp)){


		$members->username = $user;
		$found_member = $members->Search();

		if(!empty($found_member)){

			foreach($found_member as $o_member){

				$members->id = $o_member["id"];

				if (password_verify($mdp, $o_member["password"])) {

					$once = true;
					$pseudo = $o_member["pseudonyme"];
					$salt = openssl_random_pseudo_bytes(32);
					$cookie = hash('sha256', $user.$o_member["pseudonyme"].$o_member["password"].$salt);
					$members->id = $o_member["id"];
					$members->cookie = $cookie;
					$members->Save();

					$response["statut"] = array("succes"=>"true","cookie"=>$cookie, "pseudonyme"=>$pseudo);

					header('Content-Type: application/json;charset=utf-8');
					echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);
				}
			}
			if($once === false){
				$response["statut"] = array("succes"=>"false", "error"=>"sql match error");
				header('Content-Type: application/json;charset=utf-8');
				echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT); 
			}
		}
		else {
			$response["statut"] = array("succes"=>"false","error"=>"sql match error");
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
