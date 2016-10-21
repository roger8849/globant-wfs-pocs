// set this.World to your custom world (optional)

module.exports = function() {

	///// Your step definitions /////

	// use this.Given(), this.When() and this.Then() to declare step definitions
	this.Given(/^Angular JS home page is open$/, function(callback) {
        browser.driver.get('https://angularjs.org/');
				expect(element(by.css('div.center > h1')).getText()).to.eventually.equal('HTML enhanced for web apps!').and.notify(callback);
        /*element(by.css('div.center > h1')).getText().then(function(title){
            expect(title).to.equal('HTML enhanced for web apps!');
        });*/
    });

};
