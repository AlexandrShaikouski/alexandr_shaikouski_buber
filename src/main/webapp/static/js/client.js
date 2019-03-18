var driverId = null,
    myMap,
    price = null;

$(document).ready(function () {
    var interval = 4000;

    function doCheckDriver() {
        if (driverId == null) {
            $.ajax({
                url: "ajax",
                type: "POST",
                dataType: "json",
                data: {command: 'check_order_client'},
                success: function (data) {
                    driverId = data.driverId;
                    if (driverId) {
                        $('#infoMessage').html(data.messageInfo);
                        $('#modalInfoMessage').modal('show');
                        $("#status_order").html(data.statusOrder);
                        $('#button_cancel_complete').css('display', 'none');
                    } else if (data.message) {
                        location.reload();
                        errorMessage(data.message);
                    }
                }
            });
        }
    }
    function pendingDriver() {
        if (driverId) {
            $.ajax({
                url: "ajax",
                type: "POST",
                dataType: "json",
                data: {command: 'pending_driver'},
                success: function (data) {
                    if (data.status_order == "in-progress") {
                        $("#status_order").html(data.statusOrder);
                    } else if(data.message){
                        errorMessage(data.message);
                    }
                }
            });
        }
    }

    setInterval(doCheckDriver, interval);

    setInterval(pendingDriver, interval);

    $("#use-bonus").change(function () {
        if(price == null){
            price = parseFloat($("#price").html());
        }


        var selectVal = $("#use-bonus").val(),
            textTagFactor = $("#factor" + selectVal).html(),
            factor = 0.0;
        if (textTagFactor.indexOf('(') > -1) {
            factor = parseFloat(textTagFactor.split("(")[1].split(")")[0]);
        }

        var newprice = (price*(1.0-factor)).toFixed(2);
        $('input[name=price]').val(newprice);
        $("#price").html(newprice);
    });
});

function init() {
    var DELIVERY_TARIFF = 0.8,
        MINIMUM_COST = 6,
        routePanelControl = new ymaps.control.RoutePanel({
            options: {
                showHeader: true,
                title: 'Расчёт поездки'
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
        })
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
                        '<span>Расстояние: ' + length.text + '.</span><br/>' +
                        '<span style="font-weight: bold; font-style: italic">Стоимость поездки: ' + price + ' р.</span>');
                route.options.set('routeBalloonContentLayout', balloonContentLayout);
                activeRoute.balloon.open();
                sendToServer(points.get(1).properties.get("address"),
                    points.get(0).properties.get("address"),
                    points.get(1).properties.get("request"),
                    points.get(0).properties.get("request"),
                    price)
            }
        });

    });

    function calculate(routeLength) {
        return Math.max(routeLength * DELIVERY_TARIFF, MINIMUM_COST);
    }
}

function sendToServer(fromAdress, toAdress, pointA, pointB, price) {
    $('input[name=from]').val(pointA);
    $('input[name=to]').val(pointB);
    $('input[name=price]').val(price);
    $('p#from').html(fromAdress);
    $('p#to').html(toAdress);
    $('p#price').html(price);

    $('#modalOrder').modal("show");
}

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
            }else if(data.message){
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
            location.reload();
            if(data.message){
                errorMessage(data.message);
            }
        }
    });
}
function setDriverId(driverId) {
    if (driverId){
        this.driverId = driverId;
    }
}