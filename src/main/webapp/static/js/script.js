function valid(form) {
    var email_regex = /^[-a-z0-9!#$%&'*+=?^_`{|}~]+(?:\.[-a-z0-9!#$%&'*+=?^_`{|}~]+)*@(?:[a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\.)*(?:aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z][a-z])$/i;
    var phone_regex = /^(\+375)((25)|(29)|(33)|(44))[0-9]{7}$/;
    var login_regex = /^[a-zA-Z0-9]{2,45}$/;
    var first_name_regex = /^[a-zA-Zа-яА-Я]{2,45}$/;
    var password_regex = /^[a-zA-Z0-9!@#$%^&*]{6,45}$/;
    var login = form.login.value;
    var password = form.passwordUser.value;
    var repassword = form.repasswordUser.value;
    var email = form.email.value;
    var phone = form.phone.value;
    var firstName = form.first_name.value;
    var counter = 0;
    if (!login_regex.test(login)) {
        $('#login').addClass('alert-danger');
        counter = counter + 1;
    }else{
        $('#login').removeClass('alert-danger');
    }

    if(password != repassword){
        $('#repasswordUser').addClass('alert-danger');
        counter = counter + 1;
    }else{
        $('#repasswordUser').removeClass('alert-danger');
    }
    if(!password_regex.test(password)){
        $('#passwordUser').addClass('alert-danger');
        $('#repasswordUser').addClass('alert-danger');
        counter = counter + 1;
    }else{
        $('#passwordUser').removeClass('alert-danger');
    }

    if (!email_regex.test(email)) {
        $('#email').addClass('alert-danger');
        counter = counter + 1;
    }else{
        $('#email').removeClass('alert-danger');
    }

    if (!phone_regex.test(phone)) {
        $('#phone').addClass('alert-danger');
        counter = counter + 1;
    }else{
        $('#phone').removeClass('alert-danger');
    }
    if(!first_name_regex.test(firstName)){
        $('#first_name').addClass('alert-danger');
        counter = counter + 1;
    }else{
        $('#first_name').removeClass('alert-danger');
    }


    if(counter > 0){
        return false;
    }else {
        form.submit();
    }

}
function validrepassword(form) {
    var password_regex = /^[a-zA-Z0-9!@#$%^&*]{6,45}$/;
    var password = form.passwordUser.value;
    var repassword = form.repasswordUser.value;
    var counter = 0;

    if(password != repassword){
        $('#repasswordUser').addClass('alert-danger');
        counter = counter + 1;
    }else{
        $('#repasswordUser').removeClass('alert-danger');
    }
    if(!password_regex.test(password)){
        $('#passwordUser').addClass('alert-danger');
        $('#repasswordUser').addClass('alert-danger');
        counter = counter + 1;
    }else{
        $('#passwordUser').removeClass('alert-danger');
    }

    if(counter > 0){
        return false;
    }else {
        form.submit();
    }
}
function confirmDelete(form,command) {

    form.command.value = command;
    if (confirm("Confirm delete?")) {
        form.submit();
    }
}