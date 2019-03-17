var driverId = null,
    myMap,
    price = null;
ymaps.ready(init);
$(document).ready(function () {
    var interval = 5000;

    function doAjax() {
        if (driverId == null) {
            $.ajax({
                url: "ajax",
                type: "POST",
                dataType: "json",
                data: {command: 'check_order_client'},
                success: function (data) {
                    driverId = data.driverId;
                    if (data.messageInfo) {
                        $('#infoMessage').html(data.messageInfo);
                        $('#modalInfoMessage').modal('show');
                    } else if (data.message) {
                        errorMessage(data.message);
                    }
                }
            });
        }
    }

    setInterval(doAjax, interval);

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
    // Стоимость за километр.
    var DELIVERY_TARIFF = 0.8,
        // Минимальная стоимость.
        MINIMUM_COST = 6,
        // Создадим панель маршрутизации.
        routePanelControl = new ymaps.control.RoutePanel({
            options: {
                // Добавим заголовок панели.
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
    // Пользователь сможет построить только автомобильный маршрут.
    routePanelControl.routePanel.options.set({
        types: {auto: true}
    });

    routePanelControl.routePanel.geolocate('from');

    myMap.controls.add(routePanelControl).add(zoomControl);

    // Получим ссылку на маршрут.
    routePanelControl.routePanel.getRouteAsync().then(function (route) {

        // Зададим максимально допустимое число маршрутов, возвращаемых мультимаршрутизатором.
        route.model.setParams({results: 1}, true);

        // Повесим обработчик на событие построения маршрута.
        route.model.events.add('requestsuccess', function () {


            var activeRoute = route.getActiveRoute();
            var points = route.getWayPoints();
            if (activeRoute) {
                // Получим протяженность маршрута.
                var length = activeRoute.properties.get("distance"),
                    // Вычислим стоимость доставки.
                    price = calculate(Math.round(length.value / 1000)).toFixed(2),
                    // Создадим макет содержимого балуна маршрута.
                    balloonContentLayout = ymaps.templateLayoutFactory.createClass(
                        '<span>Расстояние: ' + length.text + '.</span><br/>' +
                        '<span style="font-weight: bold; font-style: italic">Стоимость поездки: ' + price + ' р.</span>');
                // Зададим этот макет для содержимого балуна.
                route.options.set('routeBalloonContentLayout', balloonContentLayout);
                // Откроем балун.
                activeRoute.balloon.open();
                sendToServer(points.get(1).properties.get("address"),
                    points.get(0).properties.get("address"),
                    points.get(1).properties.get("request"),
                    points.get(0).properties.get("request"),
                    price)
            }
        });

    });

    // Функция, вычисляющая стоимость доставки.
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
                $("div#infoMessage").html(data.messageInfo);
                $("#modalInfoMessage").modal("show");
                myMap.destroy();
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

