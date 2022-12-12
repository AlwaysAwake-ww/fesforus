


document.getElementById("signup-click").addEventListener('click', ()=>{
    

    var check = document.getElementById("admin_login_check").checked;
    console.log(check);
    if(check){
        // 관리자에 check 되있으면
        window.location.href='/member/adminsignup';
    }
    else{
        // 아니면
        window.location.href='/member/signup';
    }
});