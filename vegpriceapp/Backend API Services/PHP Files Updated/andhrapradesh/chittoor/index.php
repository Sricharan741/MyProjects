<?php
include("../../simple_html_dom.php");
$dates_file="chittoor_daily_update_dates.txt";
$json_file="chittoor_json.json";
$state="Andhra Pradesh";
$district="Chittoor";
error_reporting(0);
getData("https://market.todaypricerates.com/Chittoor-vegetables-price-in-Andhra-Pradesh",$dates_file,$json_file,$state,$district);
?>