<?php
/**
 * Created by PhpStorm.
 * User: cekke
 * Date: 9/21/2018
 * Time: 4:02 PM
 */
    require_once('Handler.php');
    $util = new Handler();
    $response = array();

    if(isset($_POST["action"]))
    {
        switch ($_POST["action"]){
            case "login":
                loginAction($util);
                break;

            case "register":
                registerAction($util);
                break;

            case "getItem":
                getItemAction($util);
                break;

            case "getItemList":
                getItemListAction($util);
                break;

            default:
                /* return some error json */
                jsonErrorPrint("Error: Ops Something went wrong!");
                break;
        }

    }
    die;

    function loginAction($sqlhandler) {
        /* do something here */
        if(isset($_POST["user"]) && isset($_POST["password"])) {
            $user = mysqli_real_escape_string($sqlhandler->getDbConn(),$_POST["user"]);
            $password = md5(mysqli_real_escape_string($sqlhandler->getDbConn(),$_POST["password"]));
            $query = "SELECT * FROM user WHERE username = '".$user."' AND password = '".$password."' ";

            $result = mysqli_query($sqlhandler->getDbConn(), $query);

            if(mysqli_num_rows($result)==0){
                jsonErrorPrint("Error: Invalid email or password!");
            }else {
                $row= mysqli_fetch_array($result,MYSQLI_NUM);
                $response['success'] = true;
                $response['message'] = "Welcome in Scrypto ".$user;
                $response['uid'] = $row[0];
                $response['username'] = $row[1];
                $response['email'] = $row[2];
                $response['password'] = $row[3];
                $response['date'] = $row[4];
                $response['permission'] = $row[5];
                json_encode($response);
                echo json_encode($response);
            }
        }else{
            jsonErrorPrint("Error: Empty fields are not allowed.");
        }
    }

    function registerAction($sqlhandler) {
        /* do something here */
        if(isset($_POST["username"]) && isset($_POST["email"]) && isset($_POST["password"])) {
            $user = mysqli_real_escape_string($sqlhandler->getDbConn(),$_POST["username"]);
            $email = mysqli_real_escape_string($sqlhandler->getDbConn(),$_POST["email"]);
            $password = md5($_POST["password"]);

            $countquery = "SELECT COUNT(*) FROM user WHERE username='".$user."'";
            $query = "INSERT INTO user (uid, username, email, password, data, permission) 
                      VALUES (NULL, '".$user."', '".$email."', '".$password."', CURRENT_DATE, 'c');";

            if($sqlhandler->insertUniqueQuery($countquery,$query)==-1)
                jsonErrorPrint("Error: Wrong couple User/Psw! please Retry.");
            else
                jsonSuccessPrint("Welcome in my App ".$user);

        }else{
            jsonErrorPrint("Error: Empty fields are not allowed.");
        }
    }

    function getItemAction($sqlhandler) {
        /* do something here */
        $id = $_POST['id'];
        if(isset($_POST['id'])){
            $query = "SELECT * FROM item WHERE cid='".$id."'";
            $result = mysqli_query($sqlhandler->getDbConn(), $query);

            if(mysqli_num_rows($result)==0){
                jsonErrorPrint("Error: Problems loading the cryptovaluta. Seems it doesn't exist yet!");
            }else {
                $row= mysqli_fetch_array($result,MYSQLI_NUM);
                $response['success'] = true;
                $response['message'] = "Valuta loaded correctly!";
                $response['cid'] = $row[0];
                $response['name'] = $row[1];
                $response['description'] = $row[2];
                $response['date'] = $row[3];
                $response['url'] = $row[4];
                json_encode($response);
                echo json_encode($response);
            }
        }else{
            jsonErrorPrint("Error: Problems loading the cryptovaluta! Please retry.");
        }
    }

    function getItemListAction($sqlhandler) {
        /* do something here */
        $sql = "SELECT * 
                FROM item ";
        $result = mysqli_query($sqlhandler->getDbConn(), $sql);

        if(mysqli_num_rows($result)==0){
            jsonErrorPrint("Error: Problems loading the cryptovaluta. Seems the list is empty!");
        }else {

            $json_array = array();
            while($row = mysqli_fetch_assoc($result))
            {
                $json_array[] = array_map(null, $row);
            }
            echo json_encode(array('cryptoValute' => $json_array));

        }
    }

    function jsonErrorPrint($message){
        /* do something here */
        $response['success'] = false;
        $response['message'] = json_encode($message);
        json_encode($response);
        echo json_encode($response);
    }

    function jsonSuccessPrint($message){
        /* do something here */
        $response['success'] = true;
        $response['message'] = json_encode($message);
        json_encode($response);
        echo json_encode($response);
    }