

const favorite_add_btn = document.getElementById("favorite_add_btn");




favorite_add_btn.addEventListener('click', () => {

    var post_num = document.getElementById("review_post_num");
    var member_index = document.getElementById("member_index_value");


    console.log(document.getElementById("review_post_num").value);
    console.log(document.getElementById("member_index_value").value);
    if (member_index == null)
        return;
    
        
    console.log(post_num);
    console.log(member_index);
    console.log(post_num.value);
    console.log(member_index.value);


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
            favorite_add_btn.tagName

            if(text=='S'){
                favorite_add_btn.classList.add("btn-primary");
                favorite_add_btn.classList.remove("btn-secondary");

            }

            else if(text=='E'){

                favorite_add_btn.classList.add("btn-secondary");
                favorite_add_btn.classList.remove("btn-primary");
            }
            else{
                alert("EXCEPTION ERROR");
            }
            
        })
        .fail(function (xhr, status, errorThrown) {
            alert("AJAX FAIL");
        });
});



