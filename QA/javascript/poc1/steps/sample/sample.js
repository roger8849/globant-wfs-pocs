// set this.World to your custom world (optional)

var CustomWorld = function() {};

CustomWorld.prototype.variable = 0;

CustomWorld.prototype.setTo = function(number) {
  this.variable = parseInt(number);
};

CustomWorld.prototype.incrementBy = function(number) {
  this.variable += parseInt(number);
};

module.exports = function() {

	this.World = CustomWorld;

	///// Your step definitions /////

	// use this.Given(), this.When() and this.Then() to declare step definitions

	this.Given(/^a variable set to (\d+)$/, function(number) {
	  this.setTo(number);
	});

	this.When(/^I increment the variable by (\d+)$/, function(number) {
	  this.incrementBy(number);
	});

	this.Then(/^the variable should contain (\d+)$/, function(number) {
	  if (this.variable != parseInt(number))
		throw new Error('Variable should contain ' + number +
		  ' but it contains ' + this.variable + '.');
	});

};
