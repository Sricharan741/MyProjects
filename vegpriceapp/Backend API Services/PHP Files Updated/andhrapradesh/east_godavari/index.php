<?php
include("../../simple_html_dom.php");
$dates_file="east_godavari_daily_update_dates.txt";
$json_file="east_godavari_json.json";
$state="Andhra Pradesh";
$district="East Godavari";
error_reporting(0);
getData("https://market.todaypricerates.com/Kakinada-vegetables-price-in-Andhra-Pradesh",$dates_file,$json_file,$state,$district); //Kakinada Headquaters
?>