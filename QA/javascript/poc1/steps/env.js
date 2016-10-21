'use strict';

module.exports = function() {

    this.setDefaultTimeout(3 * 1000);

    this.Before(function(scenario) {
        global.scenario = scenario;
    });

    //After scenario
    this.After(function(scenario, callback) {

        // console.log('Cleaning local-storage...');
        browser.executeScript('window.sessionStorage.clear();');
        browser.executeScript('window.localStorage.clear();');

        if (scenario.isFailed()) {
            browser.takeScreenshot().then(function(png) {
                // var decodedImage = new Buffer(png, 'base64');
                // return scenario.attach(decodedImage, 'image/png');
                var decodedImage = new Buffer(png, 'base64').toString('binary');
                scenario.attach(decodedImage, 'image/png', function(err) {
                    callback(err);
                });
            });
        } else {
            callback();
        }

    });

};