var statusOrder = null,
    myMap,
    INTERVAL_CHECK = 4000,
    WAITING = "WAITING",
    PENDING = "PENDING",
    IN_PROGRESS = "IN-PROGRESS",
    COMPLETE = "COMPLETE",
    price = null,
    script,
    tripColculate,
    distanceWord,
    costTrip;

$(document).ready(function () {
    setInterval(checkActionDriver, INTERVAL_CHECK);

    $("#use-bonus").change(function () {
        if (price == null) {
            price = parseFloat($("#price").html());
        }


        var selectVal = $("#use-bonus").val(),
            textTagFactor = $("#factor" + selectVal).html(),
            factor = 0.0;
        if (textTagFactor.indexOf('(') > -1) {
            factor = parseFloat(textTagFactor.split("(")[1].split(")")[0]);
        }

        var newprice = (price * (1.0 - factor)).toFixed(2);
        $('input[name=price]').val(newprice);
        $("#price").html(newprice);
    });
});

function ajaxOrder() {
    $.ajax({
        url: "ajax",
        type: "POST",
        dataType: "json",
        data: $('#formOrder').serialize(),
        success: function (data) {
            if (data.messageInfo) {
                myMap.destroy();
                location.reload();
            } else if (data.message) {
                $("div#infoMessage").html(data.message);
                $("#modalInfoMessage").modal("show");
            }
        }
    });
}


function errorMessage(message) {
    $('#infoMessage').html(message);
    $('#modalInfoMessage').modal('show');
}

function cancelCompleteOrder() {
    listOrders = null;
    $.ajax({
        url: "ajax",
        type: "POST",
        dataType: "json",
        data: {command: "cancel_complete"},
        success: function (data) {
            if (data.message) {
                errorMessage(data.message);
            }else {
                location.reload();
            }
        }
    });
}

function setStatusOrder(statusOrder) {
    if (statusOrder) {
        this.statusOrder = statusOrder;
    }
}

function checkActionDriver() {
    switch (statusOrder) {
        case WAITING:
            checkAjax("check_order_client", checkFindCar);
            break;
        case PENDING:
            checkAjax("pending_driver", checkDroveUpCar);
            break;
        case IN_PROGRESS:
            checkAjax('complete_order_client', checkCompleteTrip);
            break;
        case COMPLETE:
            checkAjax('complete_order_client', checkCompleteTrip);
    }
}

function checkAjax(command, initFunc) {
    $.ajax({
        url: "ajax",
        type: "POST",
        dataType: "json",
        data: {command: command},
        success: function (data) {
            initFunc(data);
        }
    });
}

function checkFindCar(data) {
    if (data.driverId) {
        $('#infoMessage').html(data.messageInfo);
        $('#modalInfoMessage').modal('show');
        $("#status_order").html(data.statusDriver);
        $('#button_cancel_complete').css('display', 'none');
        statusOrder = PENDING;
    }
    if (data.message) {
        errorMessage(data.message);
    }
}

function checkDroveUpCar(data) {
    if (data.statusOrder === IN_PROGRESS) {
        $("#status_order").html(data.statusDriver);
        statusOrder = IN_PROGRESS;
    }
    if (data.message) {
        errorMessage(data.message);
    }
}

function checkCompleteTrip(data) {
    if (data.statusOrder === COMPLETE) {
        $('#status_order').html(data.messageInfo);
    } else if (data.message) {
        $('#status_order').html(data.message);
        errorMessage(data.message);
    }
}


function createMapy(lang) {
    var head = document.getElementsByTagName('head')[0];
    var language = 'en';
    if (lang) {
        language = lang;
    }

    if (myMap) {
        myMap.destroy();
    }
    script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = 'https://api-maps.yandex.ru/2.1/?apikey=34223f99-cf9b-42e5-99f3-79fa5603abbb&onload=init_' + language + '&lang=' + language +
        '_RU&ns=ymaps_' + language;
    head.appendChild(script);
    window['init_' + language] = function () {
        init(window['ymaps_' + language]);
    }
}

function init(ymaps) {
    var DELIVERY_TARIFF = 0.8,
        MINIMUM_COST = 6,
        routePanelControl = new ymaps.control.RoutePanel({
            options: {
                showHeader: true,
                title: tripColculate
            }
        }),
        zoomControl = new ymaps.control.ZoomControl({
            options: {
                size: 'small',
                float: 'none',
                position: {
                    bottom: 145,
                    right: 10
                }
            }
        });
    myMap = new ymaps.Map('map', {
            center: [53.888, 27.555],
            zoom: 9,
            suppressMapOpenBlock: true,
            suppressObsoleteBrowserNotifier: true,
            controls: ['smallMapDefaultSet']
        }
        , {
            restrictMapArea: [
                [53.806, 27.3454],
                [53.9838, 27.772]
            ]
        });
    routePanelControl.routePanel.options.set({
        types: {auto: true}
    });

    routePanelControl.routePanel.geolocate('from');

    myMap.controls.add(routePanelControl).add(zoomControl);

    routePanelControl.routePanel.getRouteAsync().then(function (route) {

        route.model.setParams({results: 1}, true);

        route.model.events.add('requestsuccess', function () {


            var activeRoute = route.getActiveRoute();
            var points = route.getWayPoints();
            if (activeRoute) {
                var length = activeRoute.properties.get("distance"),
                    price = calculate(Math.round(length.value / 1000)).toFixed(2),
                    balloonContentLayout = ymaps.templateLayoutFactory.createClass(
                        '<span>' + distanceWord + ': ' + length.text + '.</span><br/>' +
                        '<span style="font-weight: bold; font-style: italic">' + costTrip + ': ' + price + ' р.</span>');
                route.options.set('routeBalloonContentLayout', balloonContentLayout);
                activeRoute.balloon.open();
                var from = points.get(1).properties.get("request"),
                    to = points.get(0).properties.get("request"),
                    regex = /^[0-9|.|,| ]+$/;

                if(regex.test(from)){
                    $('input[name=from]').val(from);
                }else{
                    ymaps.geocode(from).then(function func(res) {
                        var firstGeoObject = res.geoObjects.get(0);
                        $('input[name=from]').val(firstGeoObject.geometry.getCoordinates());
                    });
                }
                if(regex.test(to)){
                    $('input[name=to]').val(to);
                }else{
                    ymaps.geocode(to).then(function func(res) {
                        var firstGeoObject = res.geoObjects.get(0);
                        $('input[name=to]').val(firstGeoObject.geometry.getCoordinates());
                    });
                }

                sendToServer(points.get(1).properties.get("address"),
                    points.get(0).properties.get("address"),
                    price);
            }
        });

    });

    function calculate(routeLength) {
        return Math.max(routeLength * DELIVERY_TARIFF, MINIMUM_COST);
    }
}

function sendToServer(fromAdress, toAdress, price) {
    $('input[name=price]').val(price);
    $('p#from').html(fromAdress);
    $('p#to').html(toAdress);
    $('p#price').html(price);

    $('#modalOrder').modal("show");
}

function setLocaleWordOnMap(tripColculate, distance, costTrip) {
    this.tripColculate = tripColculate;
    this.distanceWord = distance;
    this.costTrip = costTrip;
}