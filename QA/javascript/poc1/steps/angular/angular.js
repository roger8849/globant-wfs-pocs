'use strict';

var angularPage = require('../../pages/angularPage.js');

// set this.World to your custom world (optional)
module.exports = function() {

	// this.Before(function () {
	// }),

	///// Your step definitions /////

	// use this.Given(), this.When() and this.Then() to declare step definitions
	this.Given(/^I navigate to Angular JS home page$/, function() {
				angularPage.go();
    }),

	this.When(/^I look at Head Title message$/, function() {
	}),

	this.Then(/^Head Title message should read "([^"]+)"$/, function(message) {
		expect(angularPage.getHeadTitle()).to.eventually.equal(message);
	});

};
