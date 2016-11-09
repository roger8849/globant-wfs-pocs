var Cucumber = require('cucumber'),
    fs = require('fs'),
    path = require('path');

var JsonFormatter = Cucumber.Listener.JsonFormatter();

var reportDirectory = 'reports/';
var reportFileName = 'cucumber-test-results-' + new Date().getTime() + '.json';

var reportDirectoryPath = path.join(__dirname, '../' + reportDirectory);
var reportFilePath = path.join(reportDirectoryPath + reportFileName);

function mkdirp(path, root) {
  var dirs = path.split('/'), dir = dirs.shift(), root = (root || '') + dir + '/';

  try {
    fs.mkdirSync(root);
  } catch (e) {
    if(!fs.statSync(root).isDirectory()) throw new Error(e);
  }

  return !dirs.length || mkdirp(dirs.join('/'), root);
}

function sendToXRay(post_data) {
  console.log("DATA TO SEND: ");
  //console.log(post_data);

  var username = process.env.XRAY_USERNAME || 'JuanKrzemien';
  var password = process.env.XRAY_PASSWORD || 'Juankrzemien2017';
  var auth = 'Basic ' + new Buffer(username + ':' + password).toString('base64');

  var options = {
    url: 'http://52.45.166.109:8080/rest/raven/1.0/import/execution/cucumber',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': auth
    },
    body: post_data
  };

  var request = require('request');
  request.post(options, function(error, response, body) {
    console.log(body);
  });
}

module.exports = function JsonOutputHook() {
  JsonFormatter.log = function (json) {
    fs.open(reportFilePath, 'w+', function (err, fd) {
      if (err) {
        mkdirp(reportDirectoryPath);
        fd = fs.openSync(reportFilePath, 'w+');
      }

      fs.writeSync(fd, json);
      console.log('json file location: ' + reportFilePath);
      sendToXRay(json);
    });
  };

  this.registerListener(JsonFormatter);
};
