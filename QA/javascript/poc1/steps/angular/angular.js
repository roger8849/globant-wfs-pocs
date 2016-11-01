'use strict';

var angularPage = require('../../pages/angularPage.js');

// set this.World to your custom world (optional)
module.exports = function() {

	// this.Before(function () {
	// }),

	var headTitle;

	///// Your step definitions /////

	// use this.Given(), this.When() and this.Then() to declare step definitions
	this.Given(/^I navigate to Angular JS home page$/, function() {
				angularPage.go();
    }),

	this.When(/^I look at Head Title message$/, function() {
		headTitle = angularPage.getHeadTitle();
	}),

	this.Then(/^Head Title message should read "([^"]+)"$/, function(message) {
		return expect(headTitle).to.eventually.equal(message);
	});

};
