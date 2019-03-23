var tripOrder = null,
    statusOrder,
    INTERVAL = 5000,
    lang = 'en',
    costTrip,
    myMap;
function setTripOrderStatus(statusOrder) {
    this.statusOrder = statusOrder;
}
$(document).ready(function () {

    setTimeout(function () {
        switch (statusOrder) {
            case "PENDING":
                acceptOrder();
                break;
            case "IN_PROGRESS":
                pendingClient();
                break;
            default:
                setInterval(checkOrder, INTERVAL);
        }
    },100);


    if(myMap){
        myMap.destroy();
    }

});


function init(ymaps, fromx, to, price) {
    var splitCoordinat,
        from = fromx;

    myMap = new ymaps.Map('map', {
            center: [53.888, 27.555],
            zoom: 9,
            suppressMapOpenBlock: true,
            suppressObsoleteBrowserNotifier: true,
            controls: ['smallMapDefaultSet']
        }, {
            restrictMapArea: [
                [53.806, 27.3454],
                [53.9838, 27.772]
            ]
        },
        {
            searchControlProvider: 'yandex#search'
        });
    if (from == null) {

        ymaps.geolocation.get({
            provider: 'yandex'
        }).then(function (result) {
            from = result.geoObjects.get(0).properties.get('boundedBy')[0].toString();
        });
    } else {
        splitCoordinat = from.split(',');
    }
    var multiRoute = new ymaps.multiRouter.MultiRoute({
        referencePoints: [
            [from],
            [to]
        ],
        params: {
            results: 1
        }
    }, {
        boundsAutoApply: true
    });
    myMap.geoObjects.add(multiRoute);
    if (price) {
        myMap.balloon.open([splitCoordinat[0], splitCoordinat[1]], '<span style="font-weight: bold; font-style: italic">'+ costTrip +': ' + price + ' р.</span>'), {
            closeButton: false
        }
    }
}

function checkOrder() {
    if (statusOrder === "") {
        $.ajax({
            url: "ajax",
            type: "POST",
            dataType: "json",
            data: {command: 'check_order_driver'},
            success: function (data) {
                var listOrders = data.tripOrders;
                if (listOrders) {
                    tripOrder = listOrders[getRandomArbitrary(listOrders.length -1)];
                    statusOrder = tripOrder.statusOrder;
                    showAcceptOrderPage(tripOrder);
                } else if (data.message) {
                    errorMessage(data.message);
                    location.reload();
                }
            }
        });
    }
}
function getRandomArbitrary(max) {
    return Math.random() * max ;
}
function showAcceptOrderPage(order) {

    createMapy(lang,order.from, order.to, order.price);
    $('input[name=trip_order_id]').val(order.id);
    $('input[name=client_id]').val(order.clientId);
    $('#buttons_accept').css('display', 'block');
    $('#welcome_message').remove();
}

function acceptOrder() {
    $.ajax({
        url: "ajax",
        type: "POST",
        dataType: "json",
        data: $('#formAcceptOrder').serialize(),
        success: function (data) {
            if (data.messageInfo) {
                $('#infoMessage').html(data.messageInfo);
                $('#modalInfoMessage').modal('show');
                $('#buttons_accept').css('display', 'none');
                tripOrder = data.tripOrder;
                if (myMap) {
                    myMap.destroy();
                }
                createMapy(lang,null, tripOrder.from, null);
                $('#buttons_pending').css('display', 'block');
            } else {
                errorMessage(data.message);
            }
        }
    });
}

function pendingClient() {
    $.ajax({
        url: "ajax",
        type: "POST",
        dataType: "json",
        data: $('#formPendingClient').serialize(),
        success: function (data) {
            if (data.message == null) {
                tripOrder = data.tripOrder;
                $('#buttons_pending').css('display', 'none');
                if (myMap) {
                    myMap.destroy();
                }
                createMapy(lang, tripOrder.from, tripOrder.to, tripOrder.price);
                $('#buttons_complete').css('display', 'block');
                $('#clientName').html(data.clientName);
                $('#clientPhone').html(data.clientPhone);
            } else {
                errorMessage(data.message);
            }
        }
    });
}

function completeTrip() {
    $.ajax({
        url: "ajax",
        type: "POST",
        dataType: "json",
        data: $('#formCompleteTrip').serialize(),
        success: function (data) {
            if (data.message == null) {
                $('#buttons_complete').css('display', 'none');
                if (myMap) {
                    myMap.destroy();
                }
                location.reload();
                tripOrder = null;
            } else {
                errorMessage(data.message);
            }
        }
    });
}

function errorMessage(message) {
    $('#infoMessage').html(message);
    $('#modalInfoMessage').modal('show');
}

function cancelOrder() {
    $('#buttons_accept').css('display', 'none');
    myMap.destroy();
    location.reload();
    tripOrder = null;
}

function cancelCompleteOrder() {
    $('#buttons_complete').css('display', 'none');
    myMap.destroy();
    tripOrder = null;
    $.ajax({
        url: "ajax",
        type: "POST",
        dataType: "json",
        data: {command: "cancel_complete"},
        success: function (data) {
            if (data.message == null) {
                $('#buttons_complete').css('display', 'none');
                myMap.destroy();
                tripOrder = null;
                statusOrder = '';
                location.reload();
            } else {
                errorMessage(data.message);
            }
        }
    });
}



function createMapy(lang, fromx, to, price) {
    var head = document.getElementsByTagName('head')[0];
    var language = 'en';
    if(lang){
        language = lang;
    }

    if (myMap) {
        myMap.destroy();
    }
    if( $('#map_script').length === 0){
        script = document.createElement('script');
        script.type = 'text/javascript';
        script.id = 'map_script';
        script.src = 'https://api-maps.yandex.ru/2.1/?apikey=34223f99-cf9b-42e5-99f3-79fa5603abbb&onload=init_' + language + '&lang=' + language +
            '_RU&ns=ymaps_' + language;
        head.appendChild(script);
        window['init_' + language] = function () {
            init(window['ymaps_' + language], fromx, to, price);
        }
    }else {
        init(window['ymaps_' + language], fromx, to, price);
    }



}
function setLocaleData(lang,costTrip){
    this.lang = lang;
    this.costTrip = costTrip;
}