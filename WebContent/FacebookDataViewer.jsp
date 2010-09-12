<!DOCTYPE html>
<html>
	<head>
		<title>XML Import for Vizster</title>
		<link rel="stylesheet" type="text/css" href="style.css" />
	</head>
	
	<body onload="setInterval('fetchLogs()', 2000)">		
		<div id="console1" class="console"></div>
	</body>
	
	<script language="javascript" src="ajax.js"></script>				
	<script type="text/javascript">		 			
		function fetchLogs() {
			sendRequest("POST", "FacebookMain",
				function(responseText) {	
                    var logDiv = document.getElementById("console1");  			
					var html = logDiv.innerHTML;								
					logDiv.innerHTML = responseText + html;
				}
			);
		}
	</script>	
</html>