$(document).ready(function() {
	
	ajaxGet();
	
	// DO GET
	function ajaxGet(){
		$.ajax({
			type : "GET",
			url : "./api/data/getDataAll",
			success: function(result){
				$.each(result, function(i, records){
					
					var logRow = '<tr>' +
					                    '<td>' + records.timestampRegistro + '</td>' +
										'<td>' + records.timestampMedicion + '</td>' +
										'<td>' + records.serial + '</td>' +
										'<td>' + records.iMei + '</td>' +
										'<td>' + records.valor + '</td>' +
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