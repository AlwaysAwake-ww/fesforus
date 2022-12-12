


const email = document.getElementById("email");
const email_check = document.getElementById("email_check");
const email_valid_text = document.getElementById("email_valid_text");
const email_check_valid_text = document.getElementById("email_check_valid_text");
const email_auth_btn = document.getElementById("email_auth_btn");
const email_auth_submit_btn = document.getElementById("email_auth_submit_btn");
const email_pattern = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;



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

};


////////////////////////////////////////////////////
/// 이메일 중복


email_auth_btn.addEventListener('click', () => {

    var email = $('#email').val();
    validTextChange(false, email_check_valid_text, "이메일 인증을 완료해주세요.");

    pattern_check = false;
    dup_check = false;


    $.ajax({
        url: "/member/emaildupcheck",
        async: true,
        data: { memberEmail: email },
        method: "POST",
    })

        .done(function (text) {
            // Ajax 통신 성공했을 경우

            console.log(text);
            console.log("AJAX SUCCESS");


            if (text == 'S') {

                dup_check = false;
            }
            else {
                dup_check = true;
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
                    title: '이메일 전송 실패',
                    text: '존재하지 않거나 유효하지 않은 이메일 형식입니다.',
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


////////////////////////////////////////
// 이메일 유효성검사

email.addEventListener("input", () => {

    email_auth_submit_btn.disabled = true;
    email_auth_btn.disabled = false;

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




/////////////////////////////////////////////
// 이메일 인증 버튼 클릭

email_auth_submit_btn.addEventListener('click', () => {

    var email = $('#email').val();
    var emailAuthValue = $("#email_check").val();

    $.ajax({
        type: "POST",
        async: true,
        url: "/member/emailAuthCheck/findid",
        data: {
            email: email,
            emailAuthValue: emailAuthValue
        }
    })
        .done(function (text) {
            if (text == 'F') {
                // 실패
                Swal.fire({
                    icon: 'error',
                    title: '인증번호가 틀립니다.',
                    text: '옳바른 인증번호를 입력하세요.',
                    confirmButtonText: '확인',
                });


            }
            else {
                // 성공
                
                Swal.fire({
                    title: '인증에 성공했습니다.',
                    text: '가입하신 아이디는 "'+text+'" 입니다.',
                    confirmButtonText: '확인',
                });

                
                
                email_auth_submit_btn.disabled = true;
                email_check.readOnly = true;
                validTextChange(true, email_check_valid_text, "인증되었습니다.");
                email_auth_btn.disabled = true;

            }
        })
        .fail(function () {

            alert("Ajax Failed!!!!T^T");

        });
});