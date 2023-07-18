const submitBtn = document.querySelector('#review_submit');


submitBtn.addEventListener('click' , (e) => {
    e.preventDefault();
    var reviewScore = $("#reviewScore").val();
    var reviewText = $("#reviewText").val();
    var postNum = $("#postNum").val();
    var review_num = $("#reviewPostNum").val();
    var memberIndex = $("#memberIndex").val();




    if(reviewScore>5 || reviewScore<1 || !reviewScore) {
        alert('1부터5사이의 숫자를 입력하시오');
    } else {
        var star= '';
        for(i=0;i<reviewScore;i++) {
            star += '<i class="fa-solid fa-star"></i>';
        }
        const reviewData = {reviewScore,reviewText,postNum,review_num,memberIndex,star};
        console.log(reviewData);
        console.log(reviewData.star)
        try {
            res = fetch('/festival/review',
            {
                method:'POST',
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(reviewData)
            });
            alert('리뷰가 정상적으로 등록되었습니다');
            var str = '<tr>';
                    str += '<td>' +reviewData.star+ '</td><td>' + reviewData.reviewText +'</td><td>' + reviewData.memberIndex+'</td></tr>';
                  $("#reviewList").append(str);

        }catch(err) {
            alert(err);
        }
    }
    console.log($("#reviewScore").val());
    $("#reviewText").val('');


});