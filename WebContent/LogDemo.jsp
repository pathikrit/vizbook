<!DOCTYPE html>
<html>
	<head><title>Log Demo</title></head>
	
	<body onload="setInterval('fetchLogs()', 2000)">
		<div id="log1" class="logs"></div>		
	</body>
	
	<script language="javascript" SRC="ajax.js"></script>				
	<script type="text/javascript">		 			
		function fetchLogs() {
			sendRequest("POST", "LogDemo",
				function(responseText) {	
                    var logDiv = document.getElementById("log1");  			
					var html = logDiv.innerHTML;								
					logDiv.innerHTML = html + responseText;
				}
			);
		}
	</script>	
</html>