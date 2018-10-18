<?php
/**
 * Created by PhpStorm.
 * User: cekke
 * Date: 9/21/2018
 * Time: 4:03 PM
 */

class Handler
{
    private $host="localhost";
    private $user="host_name";
    private $password="xxxxxxxxxxxx";
    private $database="db_name";
    private $db_conn;

    public function __construct()
    {
        $this->db_connect();
    }

    function __destruct() {
        if ($this->db_conn) {
            $this->db_disconnect();
        }
    }

    function db_connect(){
        if (!$this->db_conn) {
            $this->db_conn = mysqli_connect($this->host,$this->user, $this->password,$this->database);
            if (!$this->db_conn) {
                die('Errore nella connessione('.mysqli_connect_errno().')' .mysqli_connect_error());
            }
        }
    }

    public function db_disconnect(){
        if ($this->db_conn) {
            mysqli_close($this->db_conn);
        }
    }

    public function countQuery($query){
        $result=mysqli_query($this->db_conn,$query);
        $success=mysqli_fetch_array($result,MYSQLI_NUM);
        if($success[0]==0)
            return -1;
        else
            return $success[0];
    }

    public function insertUniqueQuery($count,$query){
        if($this->countQuery($count)==-1)
        {
            try{
                mysqli_autocommit($this->db_conn, false);
                if(!mysqli_query($this->db_conn,$query))
                    throw new Exception("fail to register in DB!");
            }catch(exception $e){
                mysqli_rollback($this->db_conn);
                //echo "Rollback ".$e->getMessage();
                mysqli_autocommit($this->db_conn, true);
                return -1;
            }
            mysqli_autocommit($this->db_conn, true);
            return 0;
        }else{
            return -1;
        }
    }

    public function insertQuery($query){
        try{
            mysqli_autocommit($this->db_conn, false);
            if(!mysqli_query($this->db_conn,$query))
                throw new Exception("fail to register in DB!");
        }catch(exception $e){
            mysqli_rollback($this->db_conn);
            //echo "Rollback ".$e->getMessage();
            mysqli_autocommit($this->db_conn, true);
            return -1;
        }
        mysqli_autocommit($this->db_conn, true);
        return 0;
    }

    /**
     * @return mixed
     */
    public function getDbConn()
    {
        return $this->db_conn;
    }




}