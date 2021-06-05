<?php
include("../../simple_html_dom.php");
$dates_file="visakhapatnam_daily_update_dates.txt";
$json_file="visakhapatnam_json.json";
$state="Andhra Pradesh";
$district="Visakhapatnam";
error_reporting(0);
getData("https://market.todaypricerates.com/Visakhapatnam-vegetables-price-in-Andhra-Pradesh",$dates_file,$json_file,$state,$district);
?>