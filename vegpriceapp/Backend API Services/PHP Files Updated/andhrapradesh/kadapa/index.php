<?php
include("../../simple_html_dom.php");
$dates_file="kadapa_daily_update_dates.txt";
$json_file="kadapa_json.json";
$state="Andhra Pradesh";
$district="Kadapa";
error_reporting(0);
getData("https://market.todaypricerates.com/Kadapa-vegetables-price-in-Andhra-Pradesh",$dates_file,$json_file,$state,$district);
?>