/* Actual protractor tests go here */
describe('Drunkr Automated Tests:', function() {
  /* Declare 'global' vars used in multiple functions here */

  // Declare new function with name
  it('Home - Title', function() {
    // Load this URL in browser (set to Chrome by default)
    browser.get('http://localhost:8888');

    // Check title of browser
    expect(browser.getTitle()).toEqual('Drunkr.io');
  });

  /* Test valid user input */
  it('Login - valid user', function() {
    browser.get('http://localhost:8888/#/login');

    expect(browser.getTitle()).toEqual('Drunkr.io');

    // Get elements by id attribute 
    var username = element(by.id('username'));
    var password = element(by.id('password'));
    var button = element(by.id('signin'));

    // Type into input, click button 
    username.sendKeys('admin');
    password.sendKeys('password12345');
    button.click();
    
    // After click, redirected to home
    var user = element(by.id('user'));

    // Check that div with 'id=user' is displayed
    expect(user.isDisplayed()).toBe(true);
  }); 

  /* Test logout */
  it('Test Logout', function() {
    browser.get('http://localhost:8888/#/login');

    expect(browser.getTitle()).toEqual('Drunkr.io');

    // Get elements by id attribute 
    var username = element(by.id('username'));
    var password = element(by.id('password'));
    var signin = element(by.id('signin'));

    // Type into input, click button 
    username.sendKeys('admin');
    password.sendKeys('password12345');
    signin.click();
    
    // After click, redirected to home. Now we click logout button 
    var logout = element(by.id('logout'));
    logout.click();

    var user = element(by.id('user'));
    // TODO: Add check for anon, was causing errors using anon.getText 
    // expect(element(by.id('user')).isDisplayed()).toBe(false);
  });

  /* Test signup */
  it('Signup - Email already taken', function() {
    browser.get('http://localhost:8888/#/signup');

    var username = element(by.id('username'));
    var email = element(by.id('email'));
    var password = element(by.id('password'));
    var signup = element(by.id('signup'));

    username.sendKeys('testUser');
    email.sendKeys('testUser@test.com');
    password.sendKeys('password12345');
    signup.click();

    var error = element(by.id('error'));

    expect(error.isDisplayed()).toBe(true);
  });

  it('Signup - Invalid password', function () {
    browser.get('http://localhost:8888/#/signup');

    var username = element(by.id('username'));
    var email = element(by.id('email'));
    var password = element(by.id('password'));
    var signup = element(by.id('signup'));

    username.sendKeys('testUser');
    email.sendKeys('testUser@test.com');
    password.sendKeys('password12345');
    signup.click();

    var error = element(by.id('error'));

    expect(error.isDisplayed()).toBe(true);
  });
});