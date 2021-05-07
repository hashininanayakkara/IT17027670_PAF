<%@page import="com.payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Management</title>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script type="text/javascript" src="Components/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="Components/payment.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-7">
				<h1 class="m-3">Payment Management</h1>

				<form id="formPayment" name="formPayment" method="post"
					action="payment.jsp">

					Payment Name: 
					<input id="paymentName" name="paymentName"
						type="text" class="form-control form-control-sm"> <br>
					Payment Date: 
					<input id="paymentDate"
						name="paymentDate" type="date"
						class="form-control form-control-sm"> <br> 
						<label for="inventoryStore">Payment Type</label>
    <select class="form-control" id="paymentType" name="paymentType">
      <option value="">-- select --</option>
      <option value="1">Cash</option>
      <option value="2">Credit Card</option>
      <option value="3">Debit Card</option>
    </select><br>
					Payment Ammount: 
					<input id="paymentAmmount" name="paymentAmmount" type="text"
						class="form-control form-control-sm"> <br> 
					<input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidPaymentIDSave" name="hidPaymentIDSave" value="">

				</form>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

			</div>
		</div>




		<br>
		<div id="divPaymentGrid">

			<%
			payment paymentObj = new payment();
			out.print(paymentObj.readPayment());
			%>
		</div>
</body>
</html>