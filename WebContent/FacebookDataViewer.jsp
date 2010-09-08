<!DOCTYPE html>
<html>
	<head><title>Test</title></head>
	
	<body onload="setInterval('fetchLogs()', 2000)">    

	<form name="comment_form" method="post">
		<div id="user">
			<fb:login-button length='long' onlogin="update_user_box();"></fb:login-button>
		</div>
	</form>    

	<div id="log1" class="logs"></div>
	</body>
	<script type="text/javascript">
		function update_user_box() {			
			FB.XFBML.Host.parseDomTree();
		}
	</script>
	<script type="text/javascript" src="http://static.ak.connect.facebook.com/js/api_lib/v0.4/FeatureLoader.js.php" mce_src="http://static.ak.connect.facebook.com/js/api_lib/v0.4/FeatureLoader.js.php"> </script>
	<script type="text/javascript">
    	var api_key = "e19760c3ea4e06f07d417f30a59a81da";
    	var channel_path = "xd_receiver.htm";
    	FB.init(api_key, channel_path);
	</script>

	<script language="javascript" SRC="ajax.js"></script>				
	<script type="text/javascript">		 			
		function fetchLogs() {
			sendRequest("POST", "FacebookMain",
				function(responseText) {	
                    var logDiv = document.getElementById("log1");  			
					var html = logDiv.innerHTML;								
					logDiv.innerHTML = html + responseText;
				}
			);
		}
	</script>	
</html>