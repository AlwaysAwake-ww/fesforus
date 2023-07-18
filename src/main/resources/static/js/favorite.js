

const favorite_add_btn = document.getElementById("favorite_add_btn");




favorite_add_btn.addEventListener('change', () => {

    var post_num = document.getElementById("review_post_num");
    var member_index = document.getElementById("member_index_value");


    console.log(document.getElementById("review_post_num").value);
    console.log(document.getElementById("member_index_value").value);
    if (member_index == null)
        return;

    $.ajax({
        url: "/favoritemodify",
        method: "POST",
        async: true,
        data: {
            post_num: post_num.value,
            member_index: member_index.value
        }
    })
        .done(function (text) { 

            if(text=='S'){

                favorite_add_btn.checked = true;

            }

            else if(text=='E'){

                favorite_add_btn.checked = false;
            }
            else{
                alert("EXCEPTION ERROR");
            }
            
        })
        .fail(function (xhr, status, errorThrown) {
            alert("AJAX FAIL");
        });
});



