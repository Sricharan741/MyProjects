<?php
include("../../simple_html_dom.php");
$dates_file="west_godavari_daily_update_dates.txt";
$json_file="west_godavari_json.json";
$state="Andhra Pradesh";
$district="West Godavari";
error_reporting(0);
getData("https://market.todaypricerates.com/Eluru-vegetables-price-in-Andhra-Pradesh",$dates_file,$json_file,$state,$district);
?>