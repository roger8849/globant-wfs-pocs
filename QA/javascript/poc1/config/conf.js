'use strict';

exports.config = {
  allScriptsTimeout: 50000,
  getPageTimeout: 20000,

  framework: 'custom',
  frameworkPath: require.resolve('protractor-cucumber-framework'),

  specs: [
    '../features/**/*.feature'
  ],

  //resultJsonOutputFile: 'reports/protractor-report.json',

  cucumberOpts: {
    tags: '~@ignore',
    format  : ['json', 'pretty', 'progress'],
    require: [
      './output.js', '../steps/**/*.js'
    ]
  },

  onPrepare: function() {
    browser.manage().window().maximize();

    global.chai = require('chai');
    global.chaiAsPromised = require('chai-as-promised');
    global.chai.use(global.chaiAsPromised);

    global.expect = global.chai.expect;
    global.assert = global.chai.assert;
    global.should = global.chai.should();

    process.env.NODE_TLS_REJECT_UNAUTHORIZED = '0';
  },

  afterLaunch: function() {

  },

  maxSessions: 2,
  seleniumAddress: 'http://localhost:4444/wd/hub',
  baseUrl: 'http://' + (process.env.SUT_HTTP_HOST || 'localhost') + ':' + (process.env.SUT_HTTP_PORT || 8080),

  multiCapabilities: [
    {
      browserName: 'chrome',
      version: 'ANY'
    },
    {
      browserName: 'firefox',
      version: 'ANY'
    },
    /*{
      browserName: 'internet explorer',
      version: 'ANY'
    }*/
  ]
};
