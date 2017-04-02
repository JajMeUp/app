<?php 

   require("Members.class.php");
	
   $members  = new Members();
   $response = array();

   if (isset($_POST["user"]) && isset($_POST["pseudonyme"]) && isset($_POST["password"])) {

   	$user = filter_var($_POST["user"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);
	$pseudonyme = filter_var($_POST["pseudonyme"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);
	$mdp = filter_var($_POST["password"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);

	if(!empty($user) && !empty($pseudonyme) && !empty($mdp)){

		$o_mdp = password_hash($mdp, PASSWORD_DEFAULT);

		$salt = openssl_random_pseudo_bytes(32);
		$cookie = hash('sha256', $user.$pseudonyme.$o_mdp.$salt);

		$members->username = $user;
		$members->pseudonyme  = $pseudonyme;
		$members->password = $o_mdp;
		$members->cookie = $cookie;

		$creation = $members->Create();

		$response["statut"] = array("succes"=>"true","cookie"=>$cookie);

		header('Content-Type: application/json;charset=utf-8');
		echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);
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
