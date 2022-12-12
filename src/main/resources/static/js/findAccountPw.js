const find_pw_btn = document.getElementById("find_pw_btn");

find_pw_btn.addEventListener("click", (event) => {
  var memberId = $("#id").val();
  var memberEmail = $("#email").val();

  Swal.fire({
    title: "임시비밀번호가 발송되었습니다.",
    text: "임시비밀번호가 도착하는데 잠깐의 시간이 소요될 수 있습니다.",
    confirmButtonText: "확인",
  });

  $.ajax({
    type: "POST",
    async: true,
    url: "/member/findaccountpw",
    data: {
      memberId: memberId,
      memberEmail: memberEmail,
    },
  })
    .done(function (text) {})
    .fail(function () {
      alert("Ajax Failed!!!!T^T");
    });
});
