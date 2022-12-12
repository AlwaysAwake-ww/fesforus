
const nickname = document.getElementById("nickname");
const nickname_dup_check = document.getElementById("nickname_dup_check");

const address = document.getElementById("address");
const detailAddress = document.getElementById("detailAddress");

const birth = document.getElementById("birth");

const email = document.getElementById("email");
const email_auth_btn = document.getElementById("email_auth_btn");


const memberNickname = document.getElementById("memberNickname");
const memberAddr = document.getElementById("memberAddr");
const memberBirth = document.getElementById("memberBirth");
const memberEmail = document.getElementById("memberEmail");
const memberCategory = document.getElementById("memberCategory");

const nickname_modify_btn = document.getElementById("nickname_modify_btn");
const addr_modify_btn = document.getElementById("addr_modify_btn");
const birth_modify_btn = document.getElementById("birth_modify_btn");
const email_modify_btn = document.getElementById("email_modify_btn");
const category_modify_btn = document.getElementById("category_modify_btn");

const nickname_pattern = /^[a-zA-Zㄱ-힣][a-zA-Zㄱ-힣 ].{0,7}$/;
const email_pattern = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;



const category_value = document.getElementById("category_value");
const category_view =document.getElementsByClassName("category_view");


const delete_btn = document.getElementById("delete_btn");




var cnt = 0;


category_check();


//////////////////////////////////////////////////////
// 닉네임

nickname_dup_check.addEventListener("click", () => {


    pattern_check = false;
    dup_check = false;



    $.ajax({
        url: "/member/nicknamedupcheck",
        async: true,
        data: { memberNickname: nickname.value },
        method: "POST",
        dataType: "text"
    })
        .done(function (text) {

            console.log(text);
            console.log("AJAX SUCCESS");


            if (text == 'S') {
                dup_check = true;
            }
            else {
                dup_check = false;
            }

            if (nickname_pattern.test(nickname.value)) {
                pattern_check = true;
            }

            else {
                pattern_check = false;
            }


            if (pattern_check && dup_check) {

                Swal.fire({
                    title: '유효한 닉네임입니다.',
                    text: '이 닉네임을 사용하시겠습니까?',
                    showDenyButton: true,
                    confirmButtonText: '사용',
                    denyButtonText: `취소`,
                }).then((result) => {
                    if (result.isConfirmed) {
                        nickname.ariaReadOnly = true;
                        Swal.fire({
                            title: '저장되었습니다.',
                            confirmButtonText: '확인',
                        });
                        nickname_dup_check.disabled = true;
                        nickname_modify_btn.disabled = false;

                    }
                    else {
                        nickname.value = '';
                        nickname.focus();
                    }
                });
            }

            else {
                Swal.fire({
                    icon: 'error',
                    title: '닉네임이 유효하지 않습니다.',
                    text: '닉네임을 확인하세요',
                    confirmButtonText: '확인',
                });

                nickname.value = '';
                nickname.focus();
            }

        })

        .fail(function (xhr, status, errorThrown) {
            console.log("AJAX FAIL");
            console.log("code:" + xhr.status + "\n" + "message:" + xhr.responseText + "\n" + "error:" + errorThrown);
        })
});

nickname.addEventListener("input", () => {
    nickname_dup_check.disabled = false;
});

nickname_modify_btn.addEventListener('click', () => {
    memberNickname.value = nickname.value;
    nickname_modify_btn.disabled = true;
    nickname.value=null;
});

//////////////////////////////////////////////////

//////////////////////////////////////////////////
//// 주소

detailAddress.addEventListener('input', ()=>{

    if(address.value != '' && detailAddress != ''){
        addr_modify_btn.disabled =false;
    }

});

address.addEventListener("input", () => {

    if (address.value != '') {
        addr_modify_btn.disabled =false;
    }
});



addr_modify_btn.addEventListener('click', () => {
    memberAddr.value = address.value + " " + detailAddress.value;
    addr_modify_btn.disabled = true;
});
/////////////////////////////////////////////////////

//////////////////////////////////////////////////////
/// 생년월일

birth.addEventListener('input',()=>{

    birth_modify_btn.disabled=false;

});

birth_modify_btn.addEventListener('click', ()=>{
    birth_modify_btn.disabled=true;
    memberBirth.value=birth.value;
    birth.value=null;
});

////////////////////////////////////////////////////////
// 이메일

email.addEventListener("input", () => {

    email_auth_submit_btn.disabled=true;        
    email_auth_btn.disabled=false;

    email_check.value="";


    if (email_pattern.test(email.value)) {
        email_auth_btn.disabled=false;
    }
    else {
        email_auth_btn.disabled=true;
    }
});


email_auth_btn.addEventListener('click', () => {

    var email = $('#email').val();

    Swal.fire({
        title: '인증번호가 발송되었습니다.',
        text: '인증번호가 도착하는데 잠깐의 시간이 소요될 수 있습니다.',
        confirmButtonText: '확인',
    });

    $.ajax({
        type: "POST",
        async:true,

        url: "/member/emailAuth",
        data: { email: email }
    })
        .done(function (data) {
            

            email_auth_cd = data;
            console.log("email_auth_cd: " + email_auth_cd);
            console.log("data: " + data);

            email_auth_submit_btn.disabled=false;
            email_check.readOnly=false;

        })

        .fail(function (data) {
            Swal.fire({
                icon: 'error',
                title: '인증번호 발송에 실패했습니다.',
                text: '잠시 후 다시 시도해주세요.',
                confirmButtonText: '확인',
            });
        });

    email_auth_submit_btn.disabled=false;        

});



email_auth_submit_btn.addEventListener('click', ()=>{
    
    var email = $('#email').val();
    var emailAuthValue = $("#email_check").val();

    $.ajax({
        type: "POST",
        async:true,
        url: "/member/emailAuthCheck",
        data: {
            email: email,
            emailAuthValue: emailAuthValue
        }
    })
    .done(function(text){
        if(text=='S'){
            // 성공
            
            Swal.fire({
                title: '인증에 성공했습니다.',
                confirmButtonText: '확인',
            }).then(()=>{
                email_auth_submit_btn.disabled=true;
                email_check.readOnly=true;
                email_auth_btn.disabled=true;
                email_modify_btn.disabled=false;
            });


            

        }
        else{
            // 실패
            Swal.fire({
                icon: 'error',
                title: '인증번호가 틀립니다.',
                text: '옳바른 인증번호를 입력하세요.',
                confirmButtonText: '확인',
            });
        }

    })
    .fail(function(){
        
        alert("Ajax Failed!!!!T^T");

    });
});


email_modify_btn.addEventListener('click',()=>{
    console.log("email_modify_btn click");
    memberEmail.value = email.value;
    console.log(memberEmail.value);
    email_modify_btn.disabled=true;
    email.value=null;
});


/////////////////////////////////////////////////////////
// 카테고리


category_modify_btn.addEventListener('click', ()=>{

    category_insert();
    memberCategory.value = category_value.value;
    category_check();
    console.log("category.value: "+category.value);
    console.log("memberCategory.value: "+memberCategory.value);
});


function category_check(){

    var category_string =  memberCategory.value.split(',');
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

const category = document.getElementsByClassName("category");

function category_insert() {
    
    category_value.value='';
    
    Array.prototype.forEach.call(category, (e) => {

        if (e.checked) {
            category_value.value += e.value + ",";
        }
    });
};



//////////////////////////////////////////////////////////
// 저장버튼

modify_btn.addEventListener('click', ()=>{
    Swal.fire({
        title: '변경사항 저장.',
        text: '변경된 정보를 저장하시겠습니까?',
        showDenyButton: true,
        confirmButtonText: '저장',
        denyButtonText: `취소`,
    }).then((result) => {
        if (result.isConfirmed) {
            // category_insert();
            sendModify();
        }
    });

});


function sendModify(){

    var nickname = $('#memberNickname').val();
    var addr = $('#memberAddr').val();
    var birth = $('#memberBirth').val();
    var email = $('#memberEmail').val();
    console.log(email);
    var category = $('#memberCategory').val();

    console.log(nickname);
    console.log(birth);
    $.ajax({
        type: "POST",
        async:true,
        url: "/member/mypage/modifyconfirm",
        data:{
            nickname:nickname,
            addr:addr,
            birth:birth,
            email:email,
            category:category
        }
    })
    .done(function(text){
       
        console.log(text);
        Swal.fire({
            title: '저장되었습니다.',
            confirmButtonText: '확인',
        });

    })
    .fail(function(){
        
        alert("Ajax Failed!!!!T^T");

    });
};



////////////////////////////////////////////////////////////
// 회원탈퇴


delete_btn.addEventListener('click', ()=>{

    Swal.fire({
        title: '회원탈퇴',
        text: '탈퇴를 진행합니까?',
        showDenyButton: true,
        confirmButtonText: '탈퇴',
        denyButtonText: `취소`,
    }).then((result) => {
        if (result.isConfirmed) {
            
            $.ajax({
                type: "POST",
                async:true,
                url: "/member/delete",
            })
            .done(function(text){
               
                console.log(text);
        
            })
            .fail(function(){
                
                alert("Ajax Failed!!!!T^T");
        
            });
            
            sendModify();
            Swal.fire({
                title: '탈퇴되었습니다.',
                confirmButtonText: '확인',
            });
        }
    });

});