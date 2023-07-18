

var address = document.getElementById("address");
var mapContainer = document.getElementById("map");
var coordXY = document.getElementById("coordXY");
var mapOption;
var map;
var x,
  y = "";


mapOption = {
  center: new daum.maps.LatLng(33.450701, 126.570667),
  level: 4,
};

var map = new daum.maps.Map(mapContainer, mapOption);

var markers = [];


mapInit();

function mapInit() {

  var geocoder = new kakao.maps.services.Geocoder();
  var gap = address.value;

  if (gap == "") {
    alert("주소입력값 없음");
    address.focus();
    return;
  }

  geocoder.addressSearch(gap, function (result, status) {

    if (status === kakao.maps.services.Status.OK) {

      var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

      var marker = new kakao.maps.Marker({
        map: map,
        position: coords
      });

      var infowindow = new kakao.maps.InfoWindow({
        content: '<div style="width:150px;text-align:center;padding:5px 0;">행사위치</div>' 
      });
      infowindow.open(map, marker);

      map.setCenter(coords);
    }
  });


};




function addressChk(categoryCode) {
  hideMarkers();

  var gap = address.value;
  if (gap == "") {
    alert("주소입력값 없음");
    address.focus();
    return;
  }

  var geocoder = new daum.maps.services.Geocoder();

  geocoder.addressSearch(gap, function (result, status) {
    if (status == daum.maps.services.Status.OK) {
      var coords = new daum.maps.LatLng(result[0].y, result[0].x);

      y = result[0].x;
      x = result[0].y;

      var marker1 = new daum.maps.Marker({
        map: map,
        position: coords,
      });

      var infowindow1 = new daum.maps.InfoWindow({
        content:
          '<div style="width:150px;text-align:center;padding:5px 0;">행사위치</div>',
      });

      infowindow1.open(map, marker1);

      map.setCenter(coords);
    }

    var ps = new kakao.maps.services.Places(map);

    ps.categorySearch(categoryCode, placesSearchCB, { useMapBounds: true });

    function placesSearchCB(data, status, pagination) {
      if (status === kakao.maps.services.Status.OK) {
        for (var i = 0; i < data.length; i++) {
          displayMarker(data[i]);
        }
      }
    }
    function displayMarker(place) {
      if (place != null) {
        var marker = new kakao.maps.Marker({
          map: map,
          position: new kakao.maps.LatLng(place.y, place.x),
        });

        var infowindow = new daum.maps.InfoWindow({
        });

        kakao.maps.event.addListener(marker, "click", function () {
          infowindow.setContent(
            '<div style="padding:5px;font-size:12px;">' +
            place.place_name +
            "</div>"
          );
          infowindow.open(map, marker);
        });

        marker.setMap(map);

        markers.push(marker);
      }
    }
  });
}
function setMarkers(map) {
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(map);
  }
}

function hideMarkers() {
  setMarkers(null);
}
