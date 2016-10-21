// set this.World to your custom world (optional)

var CustomWorld = function() {};

module.exports = function() {
	
	this.World = CustomWorld;

	///// Your step definitions /////

	// use this.Given(), this.When() and this.Then() to declare step definitions
	this.Given(/^Angular JS home page is open$/, function(number) {
        browser.driver.get('https://angularjs.org/');
        element(by.css('div.center > h1')).getText().then(function(title){
            expect(title).to.equal('HTML enhanced for web apps!');
        });
    });

};
