/* Configure protractor test environment */ 
exports.config = {
  framework: 'jasmine',
  seleniumAddress: 'http://localhost:4444/wd/hub',
  specs: ['spec.js'],
  rootElement: 'app',
  useAllAngular2AppRoots: true

  /* This is how to run on multiple browsers */
  /* multiCapabilities: [{
    browserName: 'firefox'
  }, {
    browserName: 'chrome'
  }] */

  /* This is how to change default browser */
  /* capabilities: {
    browserName: 'firefox'
  } */
}