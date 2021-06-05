<?php
include("../../simple_html_dom.php");
$dates_file="prakasam_daily_update_dates.txt";
$json_file="prakasam_json.json";
$state="Andhra Pradesh";
$district="Prakasam";
error_reporting(0);
getData("https://market.todaypricerates.com/Ongole-vegetables-price-in-Andhra-Pradesh",$dates_file,$json_file,$state,$district);
?>