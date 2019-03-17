var listOrders = null;
var myMap;
$(document).ready(function () {

    var interval = 5000;

    function doAjax() {
        if (listOrders == null) {
            $.ajax({
                url: "ajax",
                type: "POST",
                dataType: "json",
                data: {command: 'check_order_driver'},
                success: function (data) {
                    listOrders = data.tripOrders;
                    if (listOrders) {
                        var order = listOrders[0];
                        showAcceptOrderPage(order);
                    } else if (data.message) {
                        errorMessage(data.message);
                    }
                }
            });
        }
    }

    setInterval(doAjax, interval);
});

function showAcceptOrderPage(order) {
    ymaps.ready(init(order.from, order.to, order.price));

    $('input[name=trip_order_id]').val(order.id);
    $('input[name=client_id]').val(order.clientId);
    $('#buttons_accept').css('display', 'block');
}

function init(from, to, price) {
    var splitCoordinat;
    /**
     * Создаем мультимаршрут.
     * Первым аргументом передаем модель либо объект описания модели.
     * Вторым аргументом передаем опции отображения мультимаршрута.
     * @see https://api.yandex.ru/maps/doc/jsapi/2.1/ref/reference/multiRouter.MultiRoute.xml
     * @see https://api.yandex.ru/maps/doc/jsapi/2.1/ref/reference/multiRouter.MultiRouteModel.xml
     */
    if (from == null) {
        ymaps.geolocation.get({
            provider: 'yandex',
            mapStateAutoApply: true
        }).then(function (result) {
            from = result.geoObjects.get(0).properties.get('request');
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
    myMap.geoObjects.add(multiRoute);
    if (price) {
        myMap.balloon.open([splitCoordinat[0], splitCoordinat[1]], '<span style="font-weight: bold; font-style: italic">Стоимость поездки: ' + price + ' р.</span>'), {
            closeButton: false
        }
    }
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
                myMap.destroy();
                ymaps.ready(init(null, data.tripOrder.from, null));
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
    listOrders = null;
}

function changeLocale(locale) {
    // Получим ссылки на элементы с тегом 'head' и id 'language'.
    var head = document.getElementsByTagName('head')[0];
    var language = locale;
    script = document.createElement('script');
    script.type = 'text/javascript';
    // Запишем ссылку на JS API Яндекс.Карт с выбранным языком в атрибут 'src'.
    script.src = 'https://api-maps.yandex.ru/2.1/?apikey=34223f99-cf9b-42e5-99f3-79fa5603abbb&onload=init_' + language + '&lang=' + language +
        '_RU&ns=ymaps_' + language;
    // Добавим элемент 'script' на страницу.
    head.appendChild(script);
    // Использование пространства имен позволяет избежать пересечения названий функций
    // и прочих программных компонентов.
    window['init_' + language] = function () {
        init(window['ymaps_' + language]);
    }
};