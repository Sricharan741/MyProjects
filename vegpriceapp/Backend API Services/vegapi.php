<?php
//include('functions.php');
include("simple_html_dom.php");

echo pricejson('http://www.manarythubazar.com/Telangana/Warangal/Excise%20Colony');

function pricejson($url){
	$b=file_get_contents($url);
	#echo $b;
	$html = new simple_html_dom();
	$small = new simple_html_dom();
	$txt=$html->load($b);
	#echo $txt;
	$stream='{"root":[';
	$res=$html->find('table');
	for($i=0;$i<count($res);$i++){
		$small->load($res[$i]);
		$inner=$small->find('td');
		//echo strip_tags($inner[1]).'==>'.str_replace('₹','',strip_tags($inner[3])).'</br>';	
		//$stream=$stream.'"'.strip_tags($inner[1]).'":"'.str_replace('₹','',strip_tags($inner[3])).'",';
		
		//eval("($myObj)->".(strip_tags($inner[1]))."=".str_replace('₹','',strip_tags($inner[3])));
		//echo '</br> stripped'.(strip_tags($res[$i])).'</br>';
		if ($i==count($res)-1){
			$stream=$stream.'{"id":"'.$i.'",'.'"name":'.'"'.strip_tags($inner[1]).'",'.'"value":'.'"'.str_replace('₹','',strip_tags($inner[3])).'"}';
		}else{
			
			$stream=$stream.'{"id":"'.$i.'",'.'"name":'.'"'.strip_tags($inner[1]).'",'.'"value":'.'"'.str_replace('₹','',strip_tags($inner[3])).'"},';
		}
	}
	$stream=$stream.']}';
	echo $stream;
	// $myJSON = json_encode($stream);
	// echo $myJSON;
	
}
function get_between($content,$start,$end){
    $r = explode($start, $content);
    if (isset($r[1])){
        array_shift($r);
        $ret = array();
        foreach ($r as $one) {
            $one = explode($end,$one);
            $ret[] = $one[0];
        }
        return $ret;
    } else {
        return array();
    }
}
?>
