<?php
include("../../simple_html_dom.php");
$dates_file="anantapur_daily_update_dates.txt";
$json_file="anantapur_json.json";
$state="Andhra Pradesh";
$district="Anantapur";
error_reporting(0);
getData("https://market.todaypricerates.com/Anantapur-vegetables-price-in-Andhra-Pradesh",$dates_file,$json_file,$state,$district);
?>