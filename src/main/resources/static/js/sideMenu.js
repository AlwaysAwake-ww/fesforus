
      var position = $(window).scrollTop()+(window.innerHeight/2); 
      $(".quickmenu").stop().animate({"top":position+"px"},1000);


$(document).ready(function(){
    // var currentPosition = parseInt($(".quickmenu").css("top"));
    $(window).scroll(function() {
      

      var position = $(window).scrollTop()+(window.innerHeight/2); 
      $(".quickmenu").stop().animate({"top":position+"px"},650);
    });
  });