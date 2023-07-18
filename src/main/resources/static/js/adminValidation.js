

const id = document.getElementById("id");
const id_dup_check = document.getElementById("id_dup_check");
const id_valid_text = document.getElementById("id_valid_text");

const password = document.getElementById("password");
const password_check = document.getElementById("password_check");
const password_valid_text = document.getElementById("password_valid_text");
const password_check_valid_text = document.getElementById("password_check_valid_text");

const submitBtn = document.getElementById("submitForm");
const signupForm = document.signupForm;



const id_pattern = /^[a-zA-Z][0-9a-zA-Z]{4,8}$/;
const pw_pattern = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;

const forms = document.getElementsByClassName('validation-form');




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


id.addEventListener("input", () => {

    id.classList.remove('valid');
    id_dup_check.disabled = false;

    validTextChange(false, id_valid_text, "아이디를 확인하세요.");


});

id_dup_check.addEventListener('click', function (event) {


    pattern_check = false;
    dup_check = false;

    $.ajax({
        url: "/admin/iddupcheck",
        async: true,
        data: { adminId: id.value },
        method: "POST",
        dataType: "text"
    })
        .done(function (text) {

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
            console.log("AJAX FAIL");
            console.log("code:" + xhr.status + "\n" + "message:" + xhr.responseText + "\n" + "error:" + errorThrown);
        })
});


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






let validFlag = false;

function validCheck() {

    validCnt = document.getElementsByClassName("valid");


    if (validCnt.length >= 2) {
        validFlag = true;
    }
    else {
        validFlag = false;
    }
}

submitBtn.addEventListener("click", (event) => {


    validCheck();

    if (validFlag) {

        Swal.fire({
            title: '관리자 회원가입이 가능합니다.',
            text: '관리자 회원가입을 진행할까요??',
            showDenyButton: true,
            confirmButtonText: '확인',
            denyButtonText: `취소`,
        }).then((result) => {
            if (result.isConfirmed) {
                $("#signUpForm").submit();
            }
            else {

            }
        });

    }
    else {

        Swal.fire({
            icon: 'error',
            title: '회원가입 실패',
            text: '필수항목을 입력해주세요.',
            confirmButtonText: '확인'
        });
    }
});







