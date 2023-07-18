


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




