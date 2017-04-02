<?php
require_once("easyCRUD.class.php");

class Alarm Extends Crud {

  # The table you want to perform the database actions on
  protected $table = 'alarm';

  # Primary Key of the table
  protected $pk  = 'id';

}