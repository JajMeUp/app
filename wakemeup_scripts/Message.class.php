<?php
require_once("easyCRUD.class.php");

class Message Extends Crud {

  # The table you want to perform the database actions on
  protected $table = 'message';

  # Primary Key of the table
  protected $pk  = 'id';

}