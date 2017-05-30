<?php

    function sendError($response, $errorText) {
        $response["statut"] = array("success"=>"false", "error" => $errorText);
		header('Content-Type: application/json;charset=utf-8');
		echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);
    }

 ?>
