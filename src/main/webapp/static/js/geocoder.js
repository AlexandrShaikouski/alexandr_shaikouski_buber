ymaps.ready(function () {
    var myMap = new ymaps.Map('map', {
        center: [53.8975,27.6908],
        zoom: 9,
        controls: ['routeButtonControl', 'smallMapDefaultSet']
    }, {
        restrictMapArea: [
            [53.806,27.3454],
            [53.9838,27.772]
        ]
    });
    var control = myMap.controls.get('routeButtonControl');

    // Зададим координаты пункта отправления с помощью геолокации.
    control.routePanel.geolocate('from');

    // Откроем панель для построения маршрутов.
    control.state.set('expanded', true);

});