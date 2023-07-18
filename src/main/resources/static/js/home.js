


document.getElementById("signup-click").addEventListener('click', ()=>{
    

    var check = document.getElementById("admin_login_check").checked;
    console.log(check);
    if(check){
        window.location.href='/member/adminsignup';
    }
    else{
        window.location.href='/member/signup';
    }
});