var pageNum = 1;
//새로고침 했을 때 페이지 맨 위로
window.onload = function() {
    setTimeout (function() {
    scrollTo(0,0);
    },100);
}



$(window).scroll(function() {
	if($(window).scrollTop() + $(window).height() == $(document).height()) {
    let keyword_val = $("#keyword_val").val();
    var session_exist_check = $("#session_exist_check").val();
    console.log(session_exist_check);
    console.log(keyword_val)
    let sort_val = $('#sort_val').val();
    console.log(sort_val);
    let direction_val = $('#direction_val').val();
    console.log(direction_val);

//    console.log(sort_val);
	//bottom에 왔을 때 ajax로 다음 데이터 받아오기
		$.ajax({
            type: 'POST',
            url: "/categoryfestival/scroll",
            data: {
            page: pageNum, // current Page
            keyword: keyword_val,
            sort: sort_val,
            direction: direction_val
//            size: 6, // max page size(수정해야함)
            },
          dataType: 'text'
        }).done(function (result) {
            pageNum++;
            let json =  JSON.parse(result);
            console.log(json.content);
//            console.log(json.content[0]);
            if(json.content.length > 0 ){
            var str = '';
            $.each(json.content , function(i) {
                            str += "<div class='col-md-4'>\
                                        <div class='card' style='width: 100%;' >\
                                           <img width='180' height='300' src='/assets/img/image/"+ json.content[i].contentImage+"' class='card-img-top' alt='...'>\
                                           <div class='card-body'>\
                                             <h5 class='festivalTitle'>"+json.content[i].festivalTitle+"</h5>\
                                             <p>조회수: <span class='festival-text'>"+ json.content[i].contentViews +"</span></p>\
                                             <p>게시일자: <span class='festival-text'>"+ json.content[i].festivalUploadDate +"</span></p>"
                                        if(session_exist_check == 0) {
                                            str += "<a href='/festival/"+ json.content[i].postNum +"' class='btn btn-primary'>자세히보기</a>\
                                                     </div>\
                                                     </div>\
                                                     </div>"
                                        } else {
                                            str += "</div>\
                                                       </div>\
                                                        </div>"
                                        }

                        });
                        $("#post_list").append(str);
            }


        }).fail(function(data, textStatus, errorThrown){
                     console.log(data);
          });
    }
});

