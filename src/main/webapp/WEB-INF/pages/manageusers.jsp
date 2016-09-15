<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- HEADERS NEEDED TO PREVENT BACK BUTTON ON LOGOUT. DO NOT REMOVE ME! -->
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link href="resources/css/main.css" rel="stylesheet">
<link href="resources/css/dataTable.css" rel="stylesheet">
<title>Manage Users</title>
<link rel="shortcut icon" type="image/png" href="/content/resources/img/favicon.png"/>
</head>
<body>
	<jsp:include page="navbar.jsp"></jsp:include>
	<div class="container page-content">
		<br/><br/>
		<button data-toggle="modal" data-target="#createContributor" style="cursor: pointer;" class="btn btn-primary" aria-hidden="true">
			&nbsp;Add a Contributor
		</button>
		<br/><br/><br/>
		<table id="userTable" class="table table-hover">
			<col width="15%">
	  		<col width="15%">
	  		<col width="15%">
	  		<col width="15%">
	  		<col width="10%">
	  		<col width="15%">
	  		<col width="15%">
	  		<col width="0%">
	  		<col width="0%">
	  		<col width="0%">
	  		<col width="0%">
	  		<col width="0%">
	  		<col width="0%">
		  	<thead>
				<tr>
					<th>Picture(Reset)</th>
					<th>Email</th>
					<th>Name</th>
					<th>Title</th>
					<th>Edit</th>
					<th>Activation</th>
					<th>Reset Password</th>
					<th hidden></th>
					<th hidden></th>
					<th hidden></th>
					<th hidden></th>
					<th hidden></th>
					<th hidden></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${userList}" var="user">
					<tr id="${user.userId}" onclick=grabId(this.id)>
						<td id="proPic${user.userId}">
							<button id="picButton${user.userId}" class="hideButton" name="resetProfile" value="${user.userId} data-toggle="modal" data-target="#confirmPictureMod"">
								<img src="${user.profilePicture}" width="100" height="100" alt="profile picture" />
							</button>
						</td>
						<td id="email${user.userId}"><c:out value="${user.email}" /></td>
						<td><c:out value="${user.lastName}, ${user.firstName}"/></td>
						<td id="job${user.userId}"><c:out value="${user.jobTitle}" /></td>
						<td>
							<span data-toggle="modal" data-target="#editUserProfile" id="${user.userId}" onclick=edit(this.id) style="cursor: pointer;" 
								class="glyphicon glyphicon-edit" aria-hidden="true">
							</span>
						</td>
						<td><c:choose>
								<c:when id="activeTest" test="${user.active}">
									<button id="deactivateUser${user.userId}" name="deactivate" value="${user.userId}" class="btn btn-primary form-control" data-toggle="modal" data-target="#confirmDeactivateMod">Deactivate</button>
								</c:when>
								<c:otherwise>
									<button id="activateUser${user.userId}" name="activate" value="${user.userId}" class="btn btn-primary form-control" data-toggle="modal" data-target="#confirmActivateMod">Activate</button>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<button id="resetPassButton${user.userId}" name="resetPass" value="${user.userId}" type="submit" class="btn btn-primary form-control" data-toggle="modal" data-target="#confirmPasswordMod">Reset Password</button>
						</td>
						<td id="userId${user.userId}" hidden><c:out value="${user.userId}" /></td>
						<td id="password${user.userId}" hidden><c:out value="${user.password}" /></td>
						<td id="description${user.userId}" hidden><c:out value="${user.description}" /></td>
						<td id="firstName${user.userId}" hidden><c:out value="${user.firstName}" /></td>
						<td id="lastName${user.userId}" hidden><c:out value="${user.lastName}" /></td>
						<td id="link${user.userId}" hidden><c:out value="${user.linkedInURL}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br/><br/><br/><br/><br/>


		<!-- Edit User Profile Modal -->
		<div id="createContributor" class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">&times;</button>
						<h3 class="modal-title">Create Contributor</h3>
					</div>
					<form action="createAccount.do" method="post" >
						<div class="modal-body">
							Email: <input name="email" type="text" class="form-control"/>			
						</div>
					<div class="modal-footer">
						<input id="createUserButton" type="submit" value="Submit Changes" class="btn btn-primary form-control" style="width: auto;" />
						<button id="closeCreateUser" type="button" class="btn btn-secondary" data-dismiss="modal">
							Close
						</button>					
					</div>
					</form>
				</div>
			</div>
		</div>
		
		<!-- Confirm Password Modal -->
		<div id="confirmPasswordMod" class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">&times;</button>
						<h3 class="modal-title">Confirm Password</h3>
					</div>
					<div class="modal-body">
						Are you sure you want to reset this users password?
						<br/>
					</div>
					<img id="loading" src="http://blogs.pjw6193.tech/content/resources/img/rev.gif" />
					<div class="modal-footer">
						<button id="confirmPassword" class="btn btn-primary form-control" style="width: auto;">
							Confirm
						</button>
						<button id="closeCreateUser" class="btn btn-secondary" data-dismiss="modal">
							Deny
						</button>					
					</div>
				</div>
			</div>
		</div>
		
		<!-- Confirm Activate Modal -->
		<div id="confirmActivateMod" class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">&times;</button>
						<h3 class="modal-title">Confirm Activation</h3>
					</div>
					<div class="modal-body">
						Are you sure you want to activate this user?
						<br/>
					</div>
					<img id="loadingActive" src="http://blogs.pjw6193.tech/content/resources/img/rev.gif" />
					<div class="modal-footer">
						<button id="confirmActivate" class="btn btn-primary form-control" style="width: auto;">
							Confirm
						</button>
						<button id="closeCreateUser" class="btn btn-secondary" data-dismiss="modal">
							Deny
						</button>					
					</div>
				</div>
			</div>
		</div>
		
		<!-- Confirm Deactivate Modal -->
		<div id="confirmDeactivateMod" class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">&times;</button>
						<h3 class="modal-title">Confirm Deactivation</h3>
					</div>
					<div class="modal-body">
						Are you sure you want to deactivate this user?
						<br/>
					</div>
					<img id="loadingDeactive" src="http://blogs.pjw6193.tech/content/resources/img/rev.gif" />
					<div class="modal-footer">
						<button id="confirmDeactivate" class="btn btn-primary form-control" style="width: auto;">
							Confirm
						</button>
						<button id="closeCreateUser" class="btn btn-secondary" data-dismiss="modal">
							Deny
						</button>					
					</div>
				</div>
			</div>
		</div>
		
		<!-- Confirm Picture Reset Modal -->
		<div id="confirmPictureMod" class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">&times;</button>
						<h3 class="modal-title">Confirm Picture Reset</h3>
					</div>
					<div class="modal-body">
						Are you sure you want to reset this users profile picture?
						<br/>
					</div>
					<img id="loadingManage" src="http://blogs.pjw6193.tech/content/resources/img/rev.gif" />
					<div class="modal-footer">
						<button id="confirmPicture" class="btn btn-primary form-control" style="width: auto;">
							Confirm
						</button>
						<button id="closeCreateUser" class="btn btn-secondary" data-dismiss="modal">
							Deny
						</button>					
					</div>
				</div>
			</div>
		</div>
	
		<!-- Create Contributor Modal -->
		<div id="editUserProfile" class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">&times;</button>
						<h3 class="modal-title">Edit User Profile</h3>
					</div>
					<form:form action="updateUserProfile.do" method="post" commandName="updateUserProfile">
						<div class="modal-body">
							<form:hidden path="password" id="selectedUserPassword" class="form-control" />
							<h4>Id</h4>
							<form:input path="userId" id="selectedUserId" class="form-control" readOnly="true" />
							<h4>Email</h4>
							<form:input path="email" id="selectedUserEmail" class="form-control" />
							<h4>First Name</h4>
							<form:input path="firstName" id="selectedUserFirst" class="form-control" />
							<h4>Last Name</h4>
							<form:input path="lastName" id="selectedUserLast" class="form-control" />
							<h4>Title</h4>
							<form:input path="jobTitle" id="selectedUserJob" class="form-control" />
							<h4>LinkedIn</h4>
							<form:input path="linkedInURL" id="selectedLinkedInURL" class="form-control" />
							<h4>Description</h4>
							<form:textarea path="description" id="selectedUserDesc" class="form-control" />				
						</div>
					<div class="modal-footer">
						<input id="editUserButton" type="submit" value="Submit Changes" class="btn btn-primary form-control" style="width: auto;" />
						<button id="closeEditUser" type="button" class="btn btn-secondary" data-dismiss="modal">
							Close
						</button>					
					</div>
					</form:form>
				</div>
			</div>
		</div>
		
		
	</div>

	
	<jsp:include page="footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
$(document).ready(function(){

	function edit(userId){
	
		$("#selectedUserId").val($("#userId" + userId).html());
		$("#selectedUserEmail").val($("#email" + userId).html());
		$("#selectedUserFirst").val($("#firstName" + userId).html());
		$("#selectedUserLast").val($("#lastName" + userId).html());
		$("#selectedUserJob").val($("#job" + userId).html());
		$("#selectedUserDesc").val($("#description" + userId).html());
		$("#selectedUserPassword").val($("#password" + userId).html());
		$("#selectedLinkedInURL").val($("#link" + userId).html());
	}
	
	function grabId(userId){
		
		var currentUserId = userId;
		
		$("#confirmPassword").attr("value", currentUserId);
		$("#confirmActivate").attr("value", currentUserId);
		$("#confirmDeactivate").attr("value", currentUserId);
		$("#confirmPicture").attr("value", currentUserId);
	}

	const HOST = "localhost";
	
	$("#loading").hide();
	$('#loadingManage').hide();
	$('#loadingActive').hide();
	$('#loadingDeactive').hide();
	
	$('#userTable').DataTable({
		
		"dom": 'lftipr',
		"lengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]], 
   		"pagingType": "simple_numbers"		
	});
	
	$("#confirmPassword").click(function(){
		
		$("#loading").show();
		
		var id = $("#confirmPassword").val();
		
		$.get("https://" + HOST + ":7002/revblogs/resetUserPassword.do?resetPass=" + id, function(response){
		
			$("#loading").hide();
			location.reload();
		});
	});
	
	$("#confirmPicture").click(function(){
	
		$("#loadingManage").show();
		
		var id = $("#confirmPicture").val();
		
		$.get("https://" + HOST + ":7002/revblogs/resetProfile.do?resetProfile=" + id, function(response){
		
			$("#loadingManage").hide();
			location.reload();
		});
	});
	
	$("#confirmActivate").click(function(){
		
		$("#loadingActive").show();
		
		var id = $("#confirmActivate").val();
	
		$.get("https://" + HOST + ":7002/revblogs/activateUser.do?activate=" + id, function(response){
		
			$("#loadingActive").hide();
			location.reload();
		});
	});
	
	$("#confirmDeactivate").click(function(){
		
		$("#loadingDeactive").show();
		
		var id = $("#confirmDeactivate").val();
		
		$.get("https://" + HOST + ":7002/revblogs/deactivateUser.do?deactivate=" + id, function(response){
		
			$("#loadingDeactive").hide();
			location.reload();
		});
	});
});
</script>
<script type="text/javascript" src="resources/js/ui.js"></script>
</html>