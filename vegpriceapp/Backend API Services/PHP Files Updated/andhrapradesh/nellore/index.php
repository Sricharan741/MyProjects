<?php
include("../../simple_html_dom.php");
$dates_file="nellore_daily_update_dates.txt";
$json_file="nellore_json.json";
$state="Andhra Pradesh";
$district="Nellore";
error_reporting(0);
getData("https://market.todaypricerates.com/Nellore-vegetables-price-in-Andhra-Pradesh",$dates_file,$json_file,$state,$district);
?>