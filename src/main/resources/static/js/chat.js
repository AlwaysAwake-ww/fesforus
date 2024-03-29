


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