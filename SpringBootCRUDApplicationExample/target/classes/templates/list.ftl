<div class="generic-container">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Institutions </span></div>
		<div class="panel-body">
	        <div class="formcontainer">
	            <div class="alert alert-success" role="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
	            <div class="alert alert-danger" role="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
	            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
	                <input type="hidden" ng-model="ctrl.institution.id" />
	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="uname">Name</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.institution.name" id="uname" class="username form-control input-sm" placeholder="Enter your name" required ng-minlength="3"/>
	                        </div>
	                    </div>
	                </div>

	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-2 control-lable" for="age">Description</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.institution.desc" id="desc" class="form-control input-sm" placeholder="Enter the Desc." required ng-minlength="5" />
	                        </div>
	                    </div>
	                </div>
	
	               

	                <div class="row">
	                    <div class="form-actions floatRight">
	                        <input type="submit"  value="{{!ctrl.institution.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid || myForm.$pristine">
	                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
	                    </div>
	                </div>
	            </form>
    	    </div>
		</div>	
    </div>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Institutions </span></div>
		<div class="panel-body">
			<div class="table-responsive">
		        <table class="table table-hover">
		            <thead>
		            <tr>
		                <th>ID</th>
		                <th>NAME</th>
		                <th>DESCRIPTION</th>
		                <th width="100"></th>
		                <th width="100"></th>
		            </tr>
		            </thead>
		            <tbody>
		            <tr ng-repeat="u in ctrl.getAllInstitutions()">
		                <td>{{u.id}}</td>
		                <td>{{u.name}}</td>
		                <td>{{u.desc}}</td>
		                <td><button type="button" ng-click="patientctrl.getAllPatients()" class="btn btn-success custom-width">Patients</button></td>
		                 <td><button type="button" ng-click="ctrl.editInstitution(u.id)" class="btn btn-success custom-width">Edit</button></td>
		                <td><button type="button" ng-click="ctrl.removeInstitution(u.id)" class="btn btn-danger custom-width">Remove</button></td>
		            </tr>
		            </tbody>
		            
		                <div class="table-responsive">
		               
		        	<table class="table table-hover">
		        	 <div class="panel-heading"><span class="lead">List of Patients </span></div>
			            <thead>
			            <tr>
			                <th>ID</th>
			                <th>NAME</th>
			                <th>DOB</th>
			            </tr>
			            </thead>
			            <tbody>
		                	<tr ng-repeat="ur in patientctrl.getAllPatients()">
				                <td>{{ur.id}}</td>
				                <td>{{ur.name}}</td>
				                <td>{{ur.dob}}</td>
				                </tr>
				           </tbody>
		        </table>      
				             
		               
		        </table>		
			</div>
		</div>
    </div>
    
  