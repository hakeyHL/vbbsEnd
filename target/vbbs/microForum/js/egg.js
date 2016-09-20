/**
 * Created by Administrator on 2016/9/9.
 */
$('.egg-box').rotate({
    bind: {
        click: function(){
            $(".hammer").show();
            $(".hammer").rotate({
                angle: 45,
                animateTo: -45,
                duration:1000,
                easing: $.easing.easeInOutExpo
            });

            $.ajax({
                url: '<%=contextPath%>/vote/getVotePosts',
                type: 'GET',
                success: function(result) {
                    hammer();
                    $(this).unbind('click');

                },
                error: function(xhr, textStatus, errorThrown){

                }
            });


        }
    }

});
function hammer(){
    setTimeout(function(){
        $(".hammer").hide();
        $(".egg-break").show();
        $(".egg-spark").show();
        $(".egg-whole").hide();
        layer.open({
            content: '恭喜您获得iphone10手机一部！'
            ,skin: 'msg'
            ,time: 5 //2秒后自动关闭
            ,offset:'200px'
        });
    },1000);
}