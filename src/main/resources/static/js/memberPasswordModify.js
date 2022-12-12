const password = document.getElementById("password");
const password_check = document.getElementById("password_check");
const password_valid_text = document.getElementById("password_valid_text");
const password_check_valid_text = document.getElementById("password_check_valid_text");
const pw_pattern = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;




// Valid 메세지
function validTextChange(flag, validObject, text) {


    if (flag) {
        validObject.innerHTML = text;
        validObject.classList.remove('invalidText');
        validObject.classList.add('validText');
    }
    else {
        validObject.innerHTML = text;
        validObject.classList.remove('validText');
        validObject.classList.add('invalidText');

    }

}


//////////////////////////////////////////////////////////////////////////
// PW 유효성 검사 로직
password.addEventListener("input", () => {

    if (password_check.value == password.value) {
        password_check.classList.add('valid');
        validTextChange(true, password_check_valid_text, "사용 가능한 비밀번호입니다.");

    }
    else {
        password_check.classList.remove('valid');
        validTextChange(false, password_check_valid_text, "비밀번호를 확인하세요.");
    }

    if (pw_pattern.test(password.value)) {
        password.classList.add('valid');
        validTextChange(true, password_valid_text, "사용 가능한 비밀번호입니다.");
    }
    else {
        password.classList.remove('valid');
        validTextChange(false, password_valid_text, "비밀번호를 확인하세요.");

    }
});



//////////////////////////////////////////////////////////////////////////
// PW check 유효성 검사 로직

password_check.addEventListener("keyup", () => {
    if (password_check.value == password.value) {
        password_check.classList.add('valid');
        validTextChange(true, password_check_valid_text, "비밀번호와 일치합니다.");

    }
    else {
        password_check.classList.remove('valid');
        validTextChange(false, password_check_valid_text, "비밀번호와 일치하지 않습니다.");
    }
});

password_check.addEventListener("keydown", () => {
    if (password_check.value == password.value) {
        password_check.classList.add('valid');
        validTextChange(true, password_check_valid_text, "비밀번호와 일치합니다.");

    }
    else {
        password_check.classList.remove('valid');
        validTextChange(false, password_check_valid_text, "비밀번호와 일치하지 않습니다.");

    }
});




///////////////////////////////////////////////////////
// valid 개수 카운트

var validFlag = false;

function validCheck() {

    validCnt = document.getElementsByClassName("valid");

    // valid 개수 카운팅 후 flag

    if (validCnt.length >= 2) {
        // valid 개수 맞으면
        validFlag = true;
    }
    else {
        // valid 개수 적으면
        validFlag = false;
    }
}

//////////////////////////////////////////////////////////
// 저장버튼

modify_btn.addEventListener('click', ()=>{

    validCheck();
    if(validFlag){
        Swal.fire({
            title: '변경사항 저장.',
            text: '변경된 정보를 저장하시겠습니까?',
            showDenyButton: true,
            confirmButtonText: '저장',
            denyButtonText: `취소`,
        }).then((result) => {
            if (result.isConfirmed) {
                sendModify();
            }
        });
    }
    else{
        Swal.fire({
            icon: 'error',
            title: '비밀번호 변경 실패',
            text: '비밀번호를 확인하세요.',
            confirmButtonText: '확인'
        });
    }

});


function sendModify(){

    var password = $('#password').val();

    $.ajax({
        type: "POST",
        async:true,
        url: "/member/mypage/pwmodifyconfirm",
        data:{
            password:password
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
