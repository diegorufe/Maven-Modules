function findByAttributeValue(attribute, value) {
	var all = document.getElementsByTagName('*');
	for (var i = 0; i < all.length; i++) {
		if (all[i].getAttribute(attribute) == value) {
			return all[i];
		}
	}
}
function changueInnerForAtrribute(attribute, value, inner){
	var component = findByAttributeValue(attribute, value);
	if(component != null){
		component.innerHTML = inner;
	}
}
function findByAttributeValueAll(attribute, value) {
	var all = document.getElementsByTagName('*');
	var allReturn = [];
	for (var i = 0; i < all.length; i++) {
		if (all[i].getAttribute(attribute) == value) {
			allReturn.push(all[i]);
		}
	}
	return allReturn;
}
function changueInnerForAtrributeAll(attribute, value, inner){
	var all = findByAttributeValueAll(attribute, value);
	if(all != null && all.length > 0){
		for (var i = 0; i < all.length; i++) {
			all[i].innerHTML = inner;
		}
	}
	
}