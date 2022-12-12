// function getId(id){
// 	return document.getElementById(id);
// }

// var data = {};
// var ws ;
// var mid = getId('mid');
// var btnLogin = getId('btnLogin');
// var btnSend = getId('btnSend');
// var talk = getId('talk');
// var msg = getId('msg');

// btnLogin.onclick = function(){
// 	ws = new WebSocket("ws://" + location.host + "/chatt");

// 	ws.onmessage = function(msg){
// 		var data = JSON.parse(msg.data);
// 		var css;

// 		if(data.mid == mid.value){
// 			css = 'class=me';
// 		}else{
// 			css = 'class=other';
// 		}

// 		var item = `<div ${css} >
// 		                <span><b>${data.mid}</b></span> [ ${data.date} ]<br/>
//                       <span>${data.msg}</span>
// 						</div>`;

// 		talk.innerHTML += item;
// 		talk.scrollTop=talk.scrollHeight;//스크롤바 하단으로 이동
// 	}
// }

// msg.onkeyup = function(ev){
// 	if(ev.keyCode == 13){
// 		send();
// 	}
// }

// btnSend.onclick = function(){
// 	send();
// }

// function send(){
// 	if(msg.value.trim() != ''){
// 		data.mid = getId('mid').value;
// 		data.msg = msg.value;
// 		data.date = new Date().toLocaleString();
// 		var temp = JSON.stringify(data);
// 		ws.send(temp);
// 	}
// 	msg.value ='';

// }


const chatroom_create_btn = document.getElementById("chatroom_create_btn");

chatroom_create_btn.addEventListener('click', () => {
    alert("채팅방생성 버튼 클릭");
    var name = $('#chat_name').val();

    $.ajax({
        url: "/admin/createroom",
        method: "POST",
        async: true,
        data: {
            name: name
        }
    })

        .done(function (data) {

            alert("CHAT AJAX SUCCESS");
            console.log(typeof data);
            console.log(JSON.stringify(data));
            printChatList(data);
        })
        .fail(function (xhr, status, errorThrown) {
            alert("CHAT AJAX FAIL");
        });
});



function printChatList(chatList) {


    // let output =

    //     `
    //     <div id="chatroom">

    //     <ol class="list-group list-group-numbered" >
	// 	  <li class="list-group-item d-flex justify-content-between align-items-start">
	// 		<div class="ms-2 me-auto">
	// 		  <a text="${chatList.name}"></a>
	// 		</div>
	// 		<div class="btn-group" role="group" aria-label="Basic example">
	// 		</div>
	// 	  </li>
	// 	</ol>
    //     </div>
    //     `;

        let output =

        `
        <div id="chatroom">
		<ol class="list-group list-group-numbered" >
		  <li class="list-group-item d-flex justify-content-between align-items-start" th:each="chatroom : ${chatroom}">
			<div class="ms-2 me-auto">
			  <a th:text="${chatroom.name}"></a>
			</div>
			<div class="btn-group" role="group" aria-label="Basic example">
			</div>
		  </li>
		</ol>
	</div>
        `;
    
    console.log(document.getElementById("chatroom"));
    document.getElementById("chatroom").innerHTML += output;



}