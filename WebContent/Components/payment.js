$(document).ready(function(){
	$("#alertSuccess").hide();
	$("#alertError").hide();
}); 

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	var status = validatePaymentForm();
	if(status != true)
		{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
		}
	
// If valid------------------------
	
	//$("#formPayment").submit
	var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "paymentAPI",
		type : type,
		data : $("#formPayment").serialize(),
		dataType : "text",
		complete : function(response, status) 
		{
			onPaymentSaveCompelet(response.responseText, status);
		}
	});
});
	
	function onPaymentSaveCompelet(response, status) {
		if (status == "success") 
		{
			var resultSet = JSON.parse(response);
			
			if (resultSet.status.trim() == "success") 
			{
				$("#alertSuccess").text("Successfully saved.");
				$("#alertSuccess").show();
				
				$("#divPaymentGrid").html(resultSet.data);
				
			} else if (resultSet.status.trim() == "error") {
				
				$("#alertError").text(resultSet.data);
				$("#alertError").show();
			}
		} else if (status == "error") {
			$("#alertError").text("Error while saving.");
			$("#alertError").show();
		} else {
			$("#alertError").text("Unknown error while saving..");
			$("#alertError").show();
		}
		$("#hidPaymentIDSave").val("");
		$("#formPayment")[0].reset();
	}
	



//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) 
		{
			$("#hidPaymentIDSave").val($(this).closest("tr").find('#hidPaymentIDUpdate').val());
			$("#paymentName").val($(this).closest("tr").find('td:eq(0)').text());
			$("#paymentDate").val($(this).closest("tr").find('td:eq(1)').text());
			$("#paymentType").val($(this).closest("tr").find('td:eq(2)').text());
			$("#paymentAmmount").val($(this).closest("tr").find('td:eq(3)').text());
		});


//remove
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "paymentAPI",
		type : "DELETE",
		data : "paymentId=" + $(this).data("paymentid"),
		dataType : "text",
		complete : function(response, status) 
		{
			onPaymentDeleteComplete(response.responseText, status);
		}
	});
});


function onPaymentDeleteComplete(response, status) {
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success") 
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			
			$("#divPaymentGrid").html(resultSet.data);
		
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

//CLIENTMODEL=========================================================================
function validatePaymentForm() {

	if ($("#paymentName").val().trim() == "") {
		return "Insert Payment Name.";
	}

	if ($("#paymentDate").val().trim() == "") {
		return "Insert Payment Date.";
	}

	if ($("#paymentType").val().trim() == "") {
		return "Insert Payment Type.";
	}

	if ($("#paymentAmmount").val().trim() == "") {
		return "Insert Ammount.";
	}


	
	return true;
}


