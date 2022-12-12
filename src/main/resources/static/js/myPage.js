


const memberId_text = document.getElementById("memberId");
const memberNickname_text = document.getElementById("memberNickname");
const memberEmail_text = document.getElementById("memberEmail");
const memberAddr_text = document.getElementById("memberAddr");
const memberBirth_text = document.getElementById("memberBirth");
const memberCategory_text = document.getElementById("memberCategory");
const category_view =document.getElementsByClassName("category_view");


const modify_btn = document.getElementById("modify_btn");

category_check();

var flag=true;

// modify_btn.addEventListener('click', ()=>{

//     if(flag){
//         // 수정 버튼 눌렀을 때 동작

//         memberNickname_text.readOnly=false;
//         memberAddr_text.readOnly=false;
//         memberBirth_text.readOnly=false;
//         memberCategory_text.readOnly=false;

//         modify_btn.value="저장";
//         flag=false;
//     }
//     else{
//         // 변경사항 저장 버튼 눌렀을 때 동작
        
//         memberNickname_text.readOnly=true;
//         memberAddr_text.readOnly=true;
//         memberBirth_text.readOnly=true;
//         memberCategory_text.readOnly=true;

        
//         sendToController();
//         modify_btn.value="수정";
//         flag=true;
//     }

// });


// function sendToController(){
    

//     var nickname = $('#memberNickname').val();
//     var addr = $('#memberAddr').val();
//     var birth = $('#memberBirth').val();
//     var category = $('#memberCategory').val();

//     console.log(nickname);
//     console.log(birth);
//     $.ajax({
//         type: "POST",
//         async:true,
//         url: "/member/mypage/modify",
//         data:{
//             nickname:nickname,
//             addr:addr,
//             birth:birth,
//             category:category
//         }

//     })
//     .done(function(text){
       
//         console.log(text);

//     })
//     .fail(function(){
        
//         alert("Ajax Failed!!!!T^T");

//     });

// };

function category_check(){

    var category_string =  memberCategory_text.value.split(',');
    category_string = category_string.slice(0,-1);
    console.log(category_string);

    

    Array.prototype.forEach.call(category_view, (e) => {
        e.checked=false;
    });

    category_string.forEach((e)=>{
        
        idx = Number(e);
        category_view[idx-1].checked=true;
    });
};




