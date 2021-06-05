<?php
include("../../simple_html_dom.php");
$dates_file="guntur_daily_update_dates.txt";
$json_file="guntur_json.json";
$state="Andhra Pradesh";
$district="Guntur";
error_reporting(0);
getData("https://market.todaypricerates.com/Guntur-vegetables-price-in-Andhra-Pradesh",$dates_file,$json_file,$state,$district);
?>