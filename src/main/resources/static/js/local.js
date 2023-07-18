window.onload = function() {
    setTimeout (function() {
    scrollTo(0,0);
    },100);
}

var data = 0;


function localChk(local) {
    $("#boardList *").remove();
    event.preventDefault();
    data = local;
    $.ajax({
        type: "POST",
        url: "/local",
        data: {local:data},
        success: function(data) {
            var locList = data;
            var str = '<tr>';
            $.each(locList , function(i) {
                str += '<td>' + locList[i].boardAddr + '</td><td>' + locList[i].festivalTitle + '</td><td>' + locList[i].festivalUploadDate + '</td></tr>';
            });
            $("#boardList").append(str);


        }, error: function() {
            alert('실패');
        }
    });

}

