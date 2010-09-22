function getXMLObject() {
	var xmlHttp = false;
	try {
		xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");	//For old Micro$oft browsers
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");	//For Micro$oft IE 6.0+
	} catch (e) {
		if(typeof XMLHttpRequest != 'undefined') {
			xmlHttp = new XMLHttpRequest();	//For modern browsers 
		}
	}	
	return xmlHttp;
}
 
var xmlHttp = new getXMLObject();	

function sendRequest(method, uri, callback) {
	if (!method) {method = 'GET';}
	
	var timestamp = new Date(); //Prevent AJAX call caching
	
	xmlHttp.open(method.toUpperCase(), uri, true);	
	
	xmlHttp.onreadystatechange = function() {
		if(xmlHttp.readyState == 4) {
			if(xmlHttp.status == 200) {
				//TODO: Send back xmlHttp instead if multiple log jobs in a page
				callback(xmlHttp.responseText);
			} else { 
				// alert("Error during AJAX call. Please try again.");
			}
		}
	};
	
	xmlHttp.send(null);
}
