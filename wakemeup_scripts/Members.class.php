<?php
require_once("easyCRUD.class.php");

class Members Extends Crud {

  # The table you want to perform the database actions on
  protected $table = 'members';

  # Primary Key of the table
  protected $pk  = 'id';

}