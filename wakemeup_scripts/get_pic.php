<?php

require("Members.class.php");
require("utils.php");

$members = new Members();
if (isset($_POST["cookie"])) {
    $cookie = filter_var($_POST["cookie"],FILTER_SANITIZE_FULL_SPECIAL_CHARS);

    if(!empty($cookie)) {
        $members->cookie = $cookie;
        $found_member = $members->Search();

        if(!empty($found_member)) {
            foreach($found_member as $o_member) {
                $filename = $o_member["image"];
                $image = file_get_contents($filename);
            }
            if(!is_null($image))
            {
                $response["statut"] = array("success" => "true", "picture" => $image);

                header('Content-Type: application/json;charset=utf-8');
                echo json_encode($response, JSON_FORCE_OBJECT | JSON_PRETTY_PRINT);
                return;
            }
            sendError($response, "Profile pictures could not be revtrieved ... Path = " . $filename);
            retrun;
        }
        sendError($response, "Could NOT identify user with " . $cookie);
        retrun;
    }
    sendError($response, "Cookie is empty");
    retrun;
}
sendError($response, "Cookie is missing");
retrun;
