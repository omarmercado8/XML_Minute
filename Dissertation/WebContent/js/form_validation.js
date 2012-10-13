var dob_regex = /^([0-9]){2}(\/){1}([0-9]){2}(\/)([0-9]){4}$/;   // DD/MM/YYYY
var email_regex = /^[a-zA-Z0-9._-]+@([a-zA-Z0-9.-]+\.)+[a-zA-Z0-9.-]{2,4}$/;  // email address
//var digit_regex = /^[\w.-]+$/;  // allowed characters: any word . -, ( \w ) represents any word character (letters, digits, and the underscore _ ), equivalent to [a-zA-Z0-9_]
var num_regex = /^\d+$/; // numeric digits only
var search_regex = "/hello/";
var password_regex = /^[A-Za-z\d]{6,8}$/;  // any upper/lowercase characters and digits, between 6 to 8 characters in total
var phone_regex = /^\(\d{3]\) \d{3}-\d{4}$/;  // (xxx) xxx-xxxx
var question_regex = /\?$/; // ends with a question mark
//var digit_regex = /^[A-Za-z\d]/;

var digit_regex = /^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$/; 
