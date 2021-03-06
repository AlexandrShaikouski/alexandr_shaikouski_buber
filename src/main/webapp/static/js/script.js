function valid(form, flag) {
    var email_regex = /^[-a-z0-9!#$%&'*+=?^_`{|}~]+(?:\.[-a-z0-9!#$%&'*+=?^_`{|}~]+)*@(?:[a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\.)*(?:aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z][a-z])$/i,
        phone_regex = /^(\+375)((25)|(29)|(33)|(44))[0-9]{7}$/,
        login_regex = /^[a-zA-Z0-9]{2,45}$/,
        first_name_regex = /^[a-zA-Zа-яА-Я]{2,45}$/,
        login = form.login != null ? form.login.value : null,
        email = form.email != null ? form.email.value : null,
        phone = form.phone != null ? form.phone.value : null,
        firstName = form.first_name != null ? form.first_name.value : null,
        counter = 0;
    if (!login_regex.test(login)) {
        $('#login').addClass('alert-danger');
        $('#login').tooltip('show');
        counter = counter + 1;
    } else {
        $('#login').removeClass('alert-danger');
    }

    if (!email_regex.test(email)) {
        $('#email').addClass('alert-danger');
        $('#email').tooltip('show');
        counter = counter + 1;
    } else {
        $('#mail').removeClass('alert-danger');
    }

    if (!phone_regex.test(phone)) {
        $('#phone').addClass('alert-danger');
        $('#phone').tooltip('show');
        counter = counter + 1;
    } else {
        $('#phone').removeClass('alert-danger');
    }
    if (!first_name_regex.test(firstName)) {
        $('#first_name').addClass('alert-danger');
        $('#first_name').tooltip('show');
        counter = counter + 1;
    } else {
        $('#first_name').removeClass('alert-danger');
    }
    if (flag) {
        var password_regex = /^[a-zA-Z0-9!@#$%^&*]{6,45}$/,
            password = form.passwordUser.value,
            repassword = form.repasswordUser.value;
        if (password !== repassword) {
            $('#repasswordUser').addClass('alert-danger');
            $('#repasswordUser').tooltip('show');
            counter = counter + 1;
        } else {
            $('#repasswordUser').removeClass('alert-danger');
        }
        if (!password_regex.test(password)) {
            $('#passwordUser').addClass('alert-danger');
            $('#passwordUser').tooltip('show');
            $('#repasswordUser').addClass('alert-danger');
            counter = counter + 1;
        } else {
            $('#passwordUser').removeClass('alert-danger');
        }
        if (counter === 0 && form.acceptTerms && !form.acceptTerms.checked) {
            $('#infoMessage').html(flag);
            $('#modalInfoMessage').modal('show');
        }
    }


    if (counter > 0) {
        return false;
    } else {
        form.submit();
    }

}

function validrepassword(form) {
    var password_regex = /^[a-zA-Z0-9!@#$%^&*]{6,45}$/,
        password = form.passwordUser.value,
        repassword = form.repasswordUser.value,
        counter = 0;

    if (password != repassword) {
        $('#repasswordUser').addClass('alert-danger');
        counter = counter + 1;
    } else {
        $('#repasswordUser').removeClass('alert-danger');
    }
    if (!password_regex.test(password)) {
        $('#passwordUser').addClass('alert-danger');
        $('#repasswordUser').addClass('alert-danger');
        counter = counter + 1;
    } else {
        $('#passwordUser').removeClass('alert-danger');
    }

    if (counter > 0) {
        return false;
    } else {
        form.submit();
    }
}

function confirmDelete(form, command) {

    form.command.value = command;
    if (confirm("Confirm delete?")) {
        form.submit();
    }
}

function localeChange(form) {
    form.localePage.value = 'ru';
    form.submit();
}


function changeAddress(size,lang) {

    $('.cord').text(function (index, text) {
        var geocod = ymaps.geocode([text]);
        geocod.then(function func(res) {
            var firstGeoObject = res.geoObjects.get(0);
            $('#cordinate' + index).html(firstGeoObject.getAddressLine());
        });
    });

    setTimeout(function () {
        langTable(lang);
        $('#myTable').css('display', 'block');
    }, +size * 150);
}

function langTable(lang) {
    if(lang === 'ru'){
        $('#myTable').DataTable({
            "language": {
                "url": "http://cdn.datatables.net/plug-ins/1.10.19/i18n/Russian.json"
            }
        });
    }else{
        $('#myTable').DataTable();
    }
}

$(document).ready(function () {
    $('input').tooltip({ boundary: 'window', container: 'body' });
});


