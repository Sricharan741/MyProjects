<?php
include("../../simple_html_dom.php");
$dates_file="kurnool_daily_update_dates.txt";
$json_file="kurnool_json.json";
$state="Andhra Pradesh";
$district="Kurnool";
error_reporting(0);
getData("https://market.todaypricerates.com/Kurnool-vegetables-price-in-Andhra-Pradesh",$dates_file,$json_file,$state,$district);
?>