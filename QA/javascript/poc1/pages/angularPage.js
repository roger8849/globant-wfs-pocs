'use strict';
module.exports = {

  AngularPage: {
    headTitle: element(by.css('div.center > h1'))
  },

  go: function() {
    browser.get('http://www.angularjs.org');
  },

  getHeadTitle: function(task) {
    var page = this.AngularPage;

    return page.headTitle.getText();
  }
};
