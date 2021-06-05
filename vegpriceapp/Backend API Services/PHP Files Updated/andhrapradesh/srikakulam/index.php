<?php
include("../../simple_html_dom.php");
$dates_file="srikakulam_daily_update_dates.txt";
$json_file="srikakulam_json.json";
$state="Andhra Pradesh";
$district="Srikakulam";
error_reporting(0);
getData("https://market.todaypricerates.com/Srikakulam-vegetables-price-in-Andhra-Pradesh",$dates_file,$json_file,$state,$district);
?>