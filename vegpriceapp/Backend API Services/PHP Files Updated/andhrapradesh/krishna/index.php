<?php
include("../../simple_html_dom.php");
$dates_file="krishna_daily_update_dates.txt";
$json_file="krishna_json.json";
$state="Andhra Pradesh";
$district="Krishna";
error_reporting(0);
getData("https://market.todaypricerates.com/Machilipatnam-vegetables-price-in-Andhra-Pradesh",$dates_file,$json_file,$state,$district);//Machilipatnam Headquaters
?>