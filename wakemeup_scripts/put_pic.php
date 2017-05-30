<?php

require("Members.class.php");
require("utils.php");

$members  = new Members();

if (isset($_POST["cookie"]) && isset($_POST["image"])) {
    $cookie = filter_var($_POST["cookie"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);
    $image = $_POST["image"];

    if(!empty($cookie) && !empty($image)){
        $members->cookie = $cookie;
        $found_member = $members->Search();

        if(!empty($found_member)) {
            foreach($found_member as $o_member) {
                $filename = "pictures/" . $o_member["id"];
                file_put_contents($filename, $image);
                $members->id = $o_member["id"];
                $members->image = $filename;
                $members->Save();
            }

            $response["statut"] = array("success"=>"true");

            header('Content-Type: application/json;charset=utf-8');
            echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);
            return;
        }
        sendError($response, "Could not find user identified by " . $cookie);
        return;
    }
    sendError($response, "At least one of the required field is empty.");
    return;
}
sendError($response, "At least one of the required field is missing.");
return;
