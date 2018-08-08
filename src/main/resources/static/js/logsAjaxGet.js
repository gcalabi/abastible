$(document).ready(function() {
	
	ajaxGet();
	
	// DO GET
	function ajaxGet(){
		$.ajax({
			type : "GET",
			url : "./api/data/getLog",
			success: function(result){
				$.each(result, function(i, records){
					
					var logRow = '<tr>' +
					                    '<td>' + records.timestamp + '</td>' +
										'<td>' + records.encriptedData + '</td>' +
										'<td>' + records.data + '</td>' +
									  '</tr>';
					
					$('#logTable tbody').append(logRow);
					
		        });
				
				$( "#logTable tbody tr:odd" ).addClass("info");
				$( "#logTable tbody tr:even" ).addClass("success");
			},
			error : function(e) {
				alert("ERROR: ", e);
				console.log("ERROR: ", e);
			}
		});	
	}
})