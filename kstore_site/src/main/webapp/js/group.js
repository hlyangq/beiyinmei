
    

    $(function(){
    	$(".sort_list").find("li").each(function(){
    		$(this).removeClass("cur");
    		if($(this).find("a").text()=='团购'){
    			$(this).addClass("cur");
    		}
    	});

        $(".alltimes").each(function(){
            var id=$(this).attr("idattr");
            countDown($("#time"+id).val(),'#count'+id);
        });
        
    });
    //date是结束日期，例如"2014/05/19";count是容器
    function countDown(date,count){
        var now = Date.parse(new Date());
        str =  date.replace(/-/g,"/");
        var target = Date.parse(new Date(str));
        var time = target - now;
        time = parseInt(time / 1000);
        var day = Math.floor(time / (60*60*24));
        time -= day * (60*60*24);
        var hour = Math.floor(time /(60*60));
        time -= hour * (60*60);
        var minute = Math.floor(time / 60);
        var second = time - (minute * 60);
        if(day<10){
            if(day<0){
                $(count).html("已结束");
                return;
            }else{
                day="0"+day;
            }
        }
        if(hour<10){
            hour="0"+hour;
        }
        if(minute<10){
            minute="0"+minute;
        }
        if(second<10){
            second="0"+second;
        }
        if(day>0){
	        $(count).find('.day').html(day+"&nbsp;天&nbsp;");
        }
        $(count).find('.hour').html(hour);
        $(count).find('.minute').html(minute);
        $(count).find('.second').html(second);
        window.setTimeout(function(){countDown(date,count);},1000);
    }
