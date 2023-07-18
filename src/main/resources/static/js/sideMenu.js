
      var position = $(window).scrollTop()+(window.innerHeight/2); 
      $(".quickmenu").stop().animate({"top":position+"px"},1000);


$(document).ready(function(){
    $(window).scroll(function() {
      

      var position = $(window).scrollTop()+(4*window.innerHeight/5); 
      $(".quickmenu").stop().animate({"top":position+"px"},650);
    });
  });