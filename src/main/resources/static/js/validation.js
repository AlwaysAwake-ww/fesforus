

// DOM 으로 컴포넌트 받아오기
const id = document.getElementById("id");
const id_dup_check = document.getElementById("id_dup_check");
const id_valid_text = document.getElementById("id_valid_text");

const password = document.getElementById("password");
const password_check = document.getElementById("password_check");
const password_valid_text = document.getElementById("password_valid_text");
const password_check_valid_text = document.getElementById("password_check_valid_text");

const nickname = document.getElementById("nickname");
const nickname_dup_check = document.getElementById("nickname_dup_check");
const nickname_valid_text = document.getElementById("nickname_valid_text");

const email = document.getElementById("email");
const email_check = document.getElementById("email_check");
const email_valid_text = document.getElementById("email_valid_text");
const email_check_valid_text = document.getElementById("email_check_valid_text");

const email_auth_btn = document.getElementById("email_auth_btn");
const email_auth_submit_btn = document.getElementById("email_auth_submit_btn");


const birth = document.getElementById("birth");
const birth_valid_text = document.getElementById("birth_valid_text");

const submitBtn = document.getElementById("submitForm");
const signupForm = document.signupForm;


const postcode = document.getElementById("postcode");
const detailAddress = document.getElementById("detailAddress");
const detailAddress_valid_text = document.getElementById("detailAddress_valid_text");

const address = document.getElementById("address");
const address_valid_text = document.getElementById("address_valid_text");


const category = document.getElementsByClassName("category");



const extraAddress = document.getElementById("extraAddress");



// 유효성 검사를 위한 정규식 패턴
const id_pattern = /^[a-zA-Z][0-9a-zA-Z]{4,8}$/;
const pw_pattern = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
const nickname_pattern = /^[a-zA-Zㄱ-힣][a-zA-Zㄱ-힣 ].{0,7}$/;
const email_pattern = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

const forms = document.getElementsByClassName('validation-form');




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


//////////////////////////////////////////////////////////////////////
// ID 유효성검사 로직


id.addEventListener("input", () => {

    id.classList.remove('valid');
    id_dup_check.disabled = false;

    validTextChange(false, id_valid_text, "아이디를 확인하세요.");


});

// ID 중복 체크 이벤트 리스너
id_dup_check.addEventListener('click', function (event) {


    pattern_check = false;
    dup_check = false;

    // Ajax POST 로 ID 중복체크
    $.ajax({
        url: "/member/iddupcheck",
        async: true,
        data: { memberId: id.value },
        method: "POST",
        dataType: "text"
    })
        .done(function (text) {
            // Ajax 통신 성공했을 경우

            console.log("AJAX SUCCESS");
            dup_check = true;

            if (text == 'S') {
                dup_check = true;
            }
            else {
                dup_check = false;
            }


            if (id_pattern.test(id.value)) {
                pattern_check = true;
            }

            else {
                pattern_check = false;
            }

            if (pattern_check && dup_check) {
                $("#id_valid_msg").textContent = "중복체크 완료";
                Swal.fire({
                    title: '유효한 아이디입니다.',
                    text: '이 아이디를 사용하시겠습니까?',
                    showDenyButton: true,
                    confirmButtonText: '사용',
                    denyButtonText: `취소`,
                }).then((result) => {
                    if (result.isConfirmed) {
                        id.classList.add('valid');
                        Swal.fire('저장되었습니다.', '', 'success');
                        id_dup_check.disabled = true;
                        validTextChange(true, id_valid_text, "사용 가능한 아이디입니다.");


                    }
                    else {
                        id.value = '';
                        id.focus();
                    }
                });
            }
            else {
                Swal.fire({
                    icon: 'error',
                    title: '사용 불가능한 아이디입니다.',
                    text: '다른 아이디를 입력하세요.',
                    confirmButtonText: '확인',
                });

                id.value = '';
                id.focus();
                validTextChange(false, id_valid_text, "아이디를 확인하세요.");


            }

        })

        .fail(function (xhr, status, errorThrown) {
            // alert("아이디 사용 불가능");
            console.log("AJAX FAIL");
            console.log("code:" + xhr.status + "\n" + "message:" + xhr.responseText + "\n" + "error:" + errorThrown);
            // 실패했을 경우
        })
});

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


//////////////////////////////////////////////////////////////////////////
// nickname 유효성 검사 로직


nickname.addEventListener("input", () => {

    nickname.classList.remove('valid');
    nickname_dup_check.disabled = false;

    validTextChange(false, nickname_valid_text, "사용 불가능한 닉네임입니다.");


});


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
            // Ajax 통신 성공했을 경우

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
                        nickname.classList.add('valid');
                        Swal.fire('저장되었습니다.', '', 'success');
                        nickname_dup_check.disabled = true;
                        validTextChange(true, nickname_valid_text, "사용 가능한 닉네임입니다.");

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
                validTextChange(false, nickname_valid_text, "닉네임을 확인하세요.");

            }

        })

        .fail(function (xhr, status, errorThrown) {
            console.log("AJAX FAIL");
            console.log("code:" + xhr.status + "\n" + "message:" + xhr.responseText + "\n" + "error:" + errorThrown);
        })
});


//////////////////////////////////////////////////////////
// email 유효성 검증 로직

email.addEventListener("input", () => {

    email_auth_submit_btn.disabled = true;
    email_auth_btn.disabled = false;

    email_check.classList.remove('valid');
    email_check.value = "";


    if (email_pattern.test(email.value)) {
        email_auth_btn.disabled = false;
        validTextChange(true, email_valid_text, "사용 가능한 이메일입니다.");

    }
    else {
        email_auth_btn.disabled = true;
        validTextChange(false, email_valid_text, "이메일을 확인하세요.");
    }
});


email_auth_btn.addEventListener('click', () => {

    var email = $('#email').val();
    email_check.classList.remove('valid');
    validTextChange(false, email_check_valid_text, "이메일 인증을 완료해주세요.");

    pattern_check = false;
    dup_check = false;


    $.ajax({
        url: "/member/emaildupcheck",
        async: true,
        data: { memberEmail: email},
        method: "POST",
    })

        .done(function (text) {
            // Ajax 통신 성공했을 경우

            console.log(text);
            console.log("AJAX SUCCESS");


            if (text == 'S') {

                dup_check = true;
            }
            else {
                dup_check = false;
            }

            if (email_pattern.test(email)) {
                pattern_check = true;
            }

            else {

                pattern_check = false;
            }


            if (pattern_check && dup_check) {

                Swal.fire({
                    title: '유효한 이메일입니다.',
                    text: '인증번호를 전송할까요?',
                    showDenyButton: true,
                    confirmButtonText: '확인',
                    denyButtonText: `취소`,
                }).then((result) => {
                    if (result.isConfirmed) {

                        Swal.fire({
                            title: '인증번호가 발송되었습니다.',
                            text: '인증번호가 도착하는데 잠깐의 시간이 소요될 수 있습니다.',
                            confirmButtonText: '확인',
                        });

                        $.ajax({
                            type: "POST",
                            async: true,

                            url: "/member/emailAuth",
                            data: { email: email }
                        })
                            .done(function (data) {
                                email_auth_cd = data;
                                console.log("email_auth_cd: " + email_auth_cd);
                                console.log("data: " + data);



                                email_auth_submit_btn.disabled = false;
                                email_check.readOnly = false;

                            })

                            .fail(function (data) {
                                Swal.fire({
                                    icon: 'error',
                                    title: '인증번호 발송에 실패했습니다.',
                                    text: '잠시 후 다시 시도해주세요.',
                                    confirmButtonText: '확인',
                                });
                            });

                        email_auth_submit_btn.disabled = false;

                    }
                    else {
                    }
                });
            }

            else {
                Swal.fire({
                    icon: 'error',
                    title: '이메일 전송 실패.',
                    text: '중복된 이메일이거나 유효하지 않은 이메일 형식입니다.',
                    confirmButtonText: '확인',
                });
                validTextChange(false, email_valid_text, "이메일을 확인하세요.");
            }
        })

        .fail(function (xhr, status, errorThrown) {
            console.log("AJAX FAIL");
            console.log("code:" + xhr.status + "\n" + "message:" + xhr.responseText + "\n" + "error:" + errorThrown);
        })
});



email_auth_submit_btn.addEventListener('click', () => {

    var email = $('#email').val();
    var emailAuthValue = $("#email_check").val();

    $.ajax({
        type: "POST",
        async: true,
        url: "/member/emailAuthCheck",
        data: {
            email: email,
            emailAuthValue: emailAuthValue
        }
    })
        .done(function (text) {
            if (text == 'S') {
                // 성공

                Swal.fire({
                    title: '인증에 성공했습니다.',
                    confirmButtonText: '확인',
                });


                email_check.classList.add('valid');
                email_auth_submit_btn.disabled = true;
                email_check.readOnly = true;
                validTextChange(true, email_check_valid_text, "인증되었습니다.");
                email_auth_btn.disabled = true;

            }
            else {
                // 실패
                Swal.fire({
                    icon: 'error',
                    title: '인증번호가 틀립니다.',
                    text: '옳바른 인증번호를 입력하세요.',
                    confirmButtonText: '확인',
                });
            }

        })
        .fail(function () {

            alert("Ajax Failed!!!!T^T");

        });
});

//////////////////////////////////////////////////////////
// email_check 유효성 검증 로직

// email_check.addEventListener("input", () => {
//     if (email_check.value == email.value) {
//         email_check.classList.add('valid');
//         validTextChange(true, email_check_valid_text, "이메일과 일치합니다.");

//     }
//     else {
//         email_check.classList.remove('valid');
//         validTextChange(false, email_check_valid_text, "이메일과 일치하지 않습니다.");

//     }
// });

////////////////////////////////////////////////////////
// 생년월일 검증 로직


birth.addEventListener("change", () => {
    if (birth.value != null) {
        birth.classList.add("valid");
        validTextChange(true, birth_valid_text, "생년월일이 입력되었습니다.");
    }
    else {
        birth.classList.remove("valid");
        validTextChange(false, birth_valid_text, "생년월일을 입력해주세요.");
    }

});




///////////////////////////////////////////////////
// 주소 유효성 검증 로직


address.addEventListener("input", () => {

    if (address.value != '') {
        address.classList.add('valid');
        address_valid_text.classList.add()
        validTextChange(true, address_valid_text, "주소가 입력되었습니다.");
    }
});

detailAddress.addEventListener("input", () => {

    if (address.value != '') {
        detailAddress.classList.add('valid');
        address_valid_text.classList.add()
        validTextChange(true, detailAddress_valid_text, "상세주소가 입력되었습니다.");
        detailAddress.classList.add('valid');
    }
    else {
        validTextChange(false, detailAddress_valid_text, "상세주소를 입력해주세요.");
        detailAddress.classList.remove('valid');
    }
});


const addr_value = document.getElementById("addr_value");

function addrMerge() {


    addr_value.value = address.value + " " + detailAddress.value;

    console.log(addr_value);
    console.log(address.value);
    console.log(detailAddress.value);
}


////////////////////////////////////////////////
// 카테고리 로직

const selected_category = document.getElementById("selected_category");
const category_value = document.getElementById("category_value");



function category_insert() {

    Array.prototype.forEach.call(category, (e) => {

        if (e.checked) {
            category_value.value += e.value + ",";
        }
    });
};


///////////////////////////////////////////////////
// submit 정의

let validFlag = false;

function validCheck() {

    validCnt = document.getElementsByClassName("valid");

    // valid 개수 카운팅 후 flag

    if (validCnt.length >= 8) {
        // valid 개수 맞으면
        validFlag = true;
    }
    else {
        // valid 개수 적으면
        validFlag = false;
    }
}





submitBtn.addEventListener("click", (event) => {


    validCheck();

    if (validFlag) {

        Swal.fire({
            title: '회원가입이 가능합니다.',
            text: '회원가입을 진행할까요??',
            showDenyButton: true,
            confirmButtonText: '확인',
            denyButtonText: `취소`,
        }).then((result) => {
            if (result.isConfirmed) {

                addrMerge();
                category_insert();
                $("#signUpForm").submit();
            }
            else {

            }
        });
    }
    else {

        // 완성 후 지우기
        Swal.fire({
            icon: 'error',
            title: '회원가입 실패',
            text: '필수항목을 입력해주세요.',
            confirmButtonText: '확인'
        });
    }

});







