
/**
 * Validate dates precedence before submit
 */
$('form').submit(function() {

	let introductionDate = $('#introductionDate').val();
	let discontinueDate = $('#discontinueDate').val();
	
	console.log("introductionDate : " + introductionDate);
	console.log("discontinueDate : " + discontinueDate);
	
	/*console.log(introductionDate.length);
	console.log(discontinued.length);*/
	
	if (introductionDate && discontinueDate) {
	
		if( (Date.parse(introductionDate) > Date.parse(discontinueDate))) {
			$('#discontinueDate').val("");
			return false;
		}
	}
	
	alert("test");

    return true;
});