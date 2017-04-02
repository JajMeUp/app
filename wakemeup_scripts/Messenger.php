<?php
require_once("easyCRUD.class.php");

class Messenger Extends Crud {

  # The table you want to perform the database actions on
  protected $table = 'messenger';

  # Primary Key of the table
  protected $pk  = 'id';

}