/**
 * Created by Administrator on 2016/9/2.
 */
$(".great").click(function(){
    console.log(111);
    if($(this).hasClass("sprite13")){
        $(this).removeClass("sprite13");
        $(this).addClass("sprite14");
        $(this).parent().find(".add").fadeIn(1000);
        $(this).parent().find(".add").fadeOut();
         //number = parseInt($(this).next().text());
        //$(this).next().html(++number);
        //return false;
    }else if($(this).hasClass("sprite14")){
        $(this).removeClass("sprite14");
        $(this).addClass("sprite13");
    }
})