//새로고침 했을 때 페이지 맨 위로
// window.onload = function() {
//     setTimeout (function() {
//     scrollTo(0,0);
//     },100);
// }


var address = document.getElementById("address");
var mapContainer = document.getElementById("map");
var coordXY = document.getElementById("coordXY");
var mapOption;
var map;
var x,
  y = "";


mapOption = {
  center: new daum.maps.LatLng(33.450701, 126.570667), // 임의의 지도 중심좌표
  level: 4, // 지도의 확대 레벨
};

// 지도 생성
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

    // 정상적으로 검색이 완료됐으면 
    if (status === kakao.maps.services.Status.OK) {

      var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

      // 결과값으로 받은 위치를 마커로 표시합니다
      var marker = new kakao.maps.Marker({
        map: map,
        position: coords
      });

      // 인포윈도우로 장소에 대한 설명을 표시합니다
      var infowindow = new kakao.maps.InfoWindow({
        content: '<div style="width:150px;text-align:center;padding:5px 0;">행사위치</div>' 
      });
      infowindow.open(map, marker);

      // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
      map.setCenter(coords);
    }
  });


};




//편의시설 찾기
function addressChk(categoryCode) {
  hideMarkers();

  var gap = address.value; // 주소검색어
  if (gap == "") {
    alert("주소입력값 없음");
    address.focus();
    return;
  }

  // 주소-좌표 변환 객체를 생성
  var geocoder = new daum.maps.services.Geocoder();

  // 주소로 좌표를 검색
  geocoder.addressSearch(gap, function (result, status) {
    // 정상적으로 검색이 완료됐으면,
    if (status == daum.maps.services.Status.OK) {
      var coords = new daum.maps.LatLng(result[0].y, result[0].x);

      y = result[0].x;
      x = result[0].y;

      // 결과값으로 받은 위치를 마커로 표시합니다.
      var marker1 = new daum.maps.Marker({
        map: map,
        position: coords,
      });

      // 인포윈도우로 장소에 대한 설명표시
      var infowindow1 = new daum.maps.InfoWindow({
        content:
          '<div style="width:150px;text-align:center;padding:5px 0;">행사위치</div>',
      });

      infowindow1.open(map, marker1);

      // 지도 중심을 이동
      map.setCenter(coords);
    }

    // 장소 검색 객체를 생성합니다
    var ps = new kakao.maps.services.Places(map);

    // 카테고리로 편의시설을 검색합니다
    ps.categorySearch(categoryCode, placesSearchCB, { useMapBounds: true });

    // 키워드 검색 완료 시 호출되는 콜백함수 입니다
    function placesSearchCB(data, status, pagination) {
      if (status === kakao.maps.services.Status.OK) {
        for (var i = 0; i < data.length; i++) {
          displayMarker(data[i]);
          //          console.log(data[i]);
        }
      }
    }
    // 지도에 마커를 표시하는 함수입니다
    function displayMarker(place) {
      if (place != null) {
        // 마커를 생성하고 지도에 표시합니다
        var marker = new kakao.maps.Marker({
          map: map,
          position: new kakao.maps.LatLng(place.y, place.x),
        });

        var infowindow = new daum.maps.InfoWindow({
          // content: '<div style="width:150px;text-align:center;padding:5px 0;">행사위치</div>'
        });

        // 마커에 클릭이벤트를 등록합니다
        kakao.maps.event.addListener(marker, "click", function () {
          // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
          infowindow.setContent(
            '<div style="padding:5px;font-size:12px;">' +
            place.place_name +
            "</div>"
          );
          infowindow.open(map, marker);
        });

        marker.setMap(map);

        // 생성된 마커를 배열에 추가합니다
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

// "마커 감추기" 버튼을 클릭하면 호출되어 배열에 추가된 마커를 지도에서 삭제하는 함수입니다
function hideMarkers() {
  setMarkers(null);
}
