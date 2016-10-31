'use strict';
module.exports = {

  AngularPage: {
    // Element definitions

    headTitle: element(by.css('div.center > h1'))
  },

  // Method definitions

  go: function() {
    browser.get('http://www.angularjs.org');
  },

  getHeadTitle: function(task) {
    var page = this.AngularPage;

    return page.headTitle.getText();
  }
};
