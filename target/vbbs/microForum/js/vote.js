/**
 * Created by Administrator on 2016/9/8.
 */
/*
var vote = {
    ele : {
        btn_unsel : $(".btn-unsel"),
        number_span : $(".number-span"),
        people_span : $(".people-span"),
        unsel :
    },
    init : function(){
        vote.vote_c();
    },
    //ͶƱ�������
vote_c : function(){
    vote.ele.btn_unsel.click(function(){
        $(this).hide();
        $(this).parent().find(".btn-sel").show();
        //ÿ��ͶƱ��������
        var span = $(this).parent().parent().find(".number-span");
        var vhtml = parseInt(span.html())+1;
        span.html(vhtml) ;
    });
},
}
vote.init();
*/

var sel = $(".sel");
var unsel = $(".unsel");
var btn = $(".vote-btn");
btn.click(function(){
    var count = $(this).parent().parent().find(".count-span");
    if($(this).find('.sel').is(":hidden")){
        $(this).find(".unsel").hide();
        $(this).find(".sel").show();
        $(this).addClass("btn-sel");
        count.html(parseInt(count.html())+1);

    }else if($(this).find('.unsel').is(":hidden")){
        $(this).find(".sel").hide();
        $(this).find(".unsel").show();
        $(this).removeClass("btn-sel");
        count.html(parseInt(count.html())-1);

    }
})