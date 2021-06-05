<?php
include("../../simple_html_dom.php");
$dates_file="vizianagaram_daily_update_dates.txt";
$json_file="vizianagaram_json.json";
$state="Andhra Pradesh";
$district="Vizianagaram";
error_reporting(0);
getData("https://market.todaypricerates.com/Vizianagaram-vegetables-price-in-Andhra-Pradesh",$dates_file,$json_file,$state,$district);
?>