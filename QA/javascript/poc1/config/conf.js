exports.config = {
  framework: 'custom',
  frameworkPath: require.resolve('protractor-cucumber-framework'),
  specs: [
        '../features/**/*.feature'
  ],
  cucumberOpts: {
    format  : ['pretty', 'progress'],
    require: [
        '../steps/**/*.js'
    ]
  },
  seleniumAddress: 'http://localhost:4444/wd/hub',
  baseUrl: 'http://' + (process.env.SUT_HTTP_HOST || 'localhost') + ':' + (process.env.SUT_HTTP_PORT || 8080),
  multiCapabilities: [
    {
      browserName: (process.env.BROWSER1_NAME || 'chrome'),
      version: (process.env.BROWSER1_VERSION || 'ANY'),
      cucumberOpts: {
        tags: '~@ignore',
        format: 'pretty'
      }
    },
    {
      browserName: (process.env.BROWSER2_NAME || 'firefox'),
      version: (process.env.BROWSER2_VERSION || 'ANY'),
      cucumberOpts: {
        tags: '~@ignore',
        format: 'pretty'
      }
    },
    {
      browserName: (process.env.BROWSER3_NAME || 'internetExplorer'),
      version: (process.env.BROWSER3_VERSION || 'ANY'),
      cucumberOpts: {
        tags: '~@ignore',
        format: 'pretty'
      }
    }
  ]
};
