<!doctype html>
<html lang="en">
    <head>
    <#assign basePath=request.contextPath>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="renderer" content="webkit">
        <title>找回密码</title>
        <link rel="stylesheet" type="text/css" href="${basePath}/css/pages.css"/>
        <script type="text/javascript" src="${basePath}/js/jquery-1.9.1.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="head2">
                <a href="${basicSet.bsetAddress!'#'}">
                <#if basicSet.bsetThirdLogo??>
                    <img id="logo_pic" src="${basicSet.bsetThirdLogo}" alt="" style="height:45px;width:auto;"/>
                <#else>
                    <img alt="" src="${basePath}/images/logo.jpg" id="logo_pic"/>
                </#if>

                </a>
                <h1>找回密码</h1>
            </div>
        </div>
        <div class="container pb50">
            <div class="n_step" style="padding-left:150px;">
                <div class="n_step_con">
                    <div class="n_step2"></div>
                    <ul class="ml10 clearfix">
                        <li class="p100 prev">填写账户名</li>
                        <li class="p130 cur">验证身份</li>
                        <li class="p130">设置新密码</li>
                        <li>完成</li>
                    </ul>
                </div>
                <div class="n_password">
                    <div class="n_item clearfix mb20">
                        <span class="label fl">验证方式：</span>
                        <div class="fl">
                            <select>
                                <option>已验证手机</option>
                                <#--<option>已验证邮箱</option>-->
                            </select>
                            <div class="ne_tips hide ">您输入的用户名有误</div>
                        </div>
                    </div>
                    <div class="n_item clearfix mb20">
                        <span class="label fl">以验证手机：</span>
                        <div class="fl">
                            <strong>   <#if (user??&& user.infoMobile?? && user.infoMobile?length>3)>
                    <#assign mo="${user.infoMobile?substring(3,user.infoMobile?length-3)}" />
                    <#assign mob="${user.infoMobile?replace(mo,'*****')}" />
                    ${mob}
                    </#if></strong>
                        </div>
                    </div>
                    <div class="n_item clearfix mb20">
                        <span class="label fl">验证码：</span>
                        <div class="fl">
                            <input type="text" placeholder="请输入验证码" class="short_text mr20" id="varification" name="varification"/>
                            <img id="checkCodeImg" class="code_image"  src="${basePath}/patchca.htm" onclick="this.src=this.src+'?'+Math.random(); "/>
                            <a href="#" class="ml20 ju_s"  id="checkCodeA">换一张</a>
                            <div class="ne_tips hide varification">您输入的验证码有误</div>
                        </div>
                    </div>
                    <div class="n_item clearfix mb20">
                        <span class="label fl">短信验证码：</span>
                        <div class="fl">
                            <input type="text" placeholder="请输入验证码" class="short_text mr20" id="dynamiccode"/>
                            <a href="#" class="hq_code" id="sendCode">获取短信验证码</a>
                            <div></div>
                            <div class="ne_tips hide mobile_tip">您输入的验证码有误</div>
                            <div class="mt20 col6 hide mobile_code">校验码已发出，请注意查收短信，如果没有收到，你可以在
                                <span class="ju_s timeleft">60</span>
                                秒后要求系统重新发送
                            </div>
                        </div>
                    </div>
                    <div class="n_item clearfix mb20">
                        <span class="label fl">&nbsp;</span>
                        <a class="fl" href="#">
                                <button class="n_nextstep" type="button" onclick="tonextThree()">下一步</button>
                        </a>
                    </div>
                </div>
            </div>
            <!--n_step-->
        </div>


        <div class="wp">
            <div class="footer mt20">
                <ul class="ft_links tc">
                </ul>
                <!--/ft_links-->

                <div style="margin: 15px 0px;" id="bq">
                    <a style="color:#666666;font-family:tahoma, arial, 宋体;line-height:normal;text-align:center;background-color:#FFFFFF;"></a>
                    <span style="color:#666666;font-family:tahoma, arial, 宋体;line-height:normal;text-align:center;background-color:#FFFFFF;"> </span>
                    <div class="copyright tc mt15" style="color:#666666;font-family:tahoma, arial, 宋体;line-height:normal;text-align:center;margin:15px 0px 0px;padding:0px;background-color:#FFFFFF;">
                        <p class="mb20" style="margin-top:0px;margin-bottom:20px;padding:0px;">
                        ${basicSet.thirdCopyright!''}
                        </p>
                        <p>
                            <br>
                        </p>
                    </div>

                    <ul style="text-align:center;margin-top:20px;">
                        <!--站长统计-->
                        <li>
                            <script type="text/javascript">function show() {
                            }</script>
                        </li>
                    </ul>
                </div>

            </div>
            <!--/footer-->
        </div>

    </body>
<script type="text/javascript">
    $(function(){
        //验证码绑定onclick事件
        $("#checkCodeA").click(
                function(){
                    $("#checkCodeImg").click();
                }
        );
        $("#sendCode").click(
                function(){
                    //验证码
                    var enterValue = $("#varification").val();
                    if(enterValue != ''&&enterValue!=null){
                        $.ajax({
                            url: "checkpatchca.htm?enterValue="+enterValue,
                            context: document.body,
                            async:false,
                            success: function(data){
                                if(data==0){
                                    $(".varification").html('请输入正确的验证码再发送短信');
                                    $(".varification").show();
                                }else{
                                        $(".varification").html('');
                                        $(this).attr("disabled","disabled");
                                        var mobile = "${userMobile}";
                                        var datas = "mobile=" + mobile+"&code="+enterValue;
                                        $.ajax({
                                            type: 'get',
                                            url:'sendcodetovalidate.htm',
                                            data:datas,
                                            async:false,
                                            success: function(data) {
                                                if(data==1) {
                                                    $(".varification").html('');
                                                    $(".mobile_code").show();
                                                    setTimeout(countDown, 1000);
                                                    $(this).removeAttr("disabled");
                                                    $("#sendCode").attr("disabled","disabled");
                                                    $("#sendCode").css("cursor","not-allowed")
                                                }else if(data==0){
                                                    //网络异常
                                                    $(".mobile_tip").html('网络异常请稍后再试');
                                                    $(".mobile_tip").show();
                                                    $("#sendCode").removeAttr("disabled");
                                                }else if(data==-1){
                                                    $(".mobile_tip").html('60秒内只能获取一次验证码,请稍后重试');
                                                    $(".mobile_tip").show();
                                                }
                                            },
                                            error : function() {
                                                //网络异常
                                                $(".mobile_tip").html('网络异常请稍后再试');
                                                $(".mobile_tip").show();
                                            }
                                        });
                                    };
                            }
                        });
                    }
                    else{
                        if(enterValue == ''||enterValue==null){
                            $(".varification").html('请输入验证码');
                            $(".varification").show();
                        }

                    }

                    function countDown(){
                        var time = $(".timeleft").text();
                        $(".timeleft").text(time - 1);
                        if (time == 1) {
                            $(".mobile_code").hide();
                            $("#sendCode").removeAttr("disabled");
                            $(".timeleft").text(60);
                            $("#sendCode").css("cursor","pointer")
                            $("#checkCodeImg").click();
                        } else {
                            setTimeout(countDown, 1000);
                        }
                    }
                });

    });


    function checkmobilecode(){
        //短信验证码
        var sendCode = $("#dynamiccode").val();
        //验证码
        var enterValue = $("#varification").val();
        var str=true;
        if (enterValue != '' && enterValue!=null) {

            if(sendCode!='' && sendCode!=null){
                $.ajax({
                    url: "checkmobilecode.htm?codetext=" + sendCode + "&mobile=" + ${userMobile},
                    context: document.body,
                    async:false,
                    success: function (data) {

                        if (data == 0) {
                            $(".mobile_tip").html('短信验证码不正确');
                            $(".mobile_tip").show();
                            $("#sendCode").removeAttr("disabled");
                            //如果失败修更新验证码
                            $("#checkCodeImg").click();
                            str=str&&false;
                        } else {
                            $(".varification").html('');
                            str=str&&true;
                        }

                    }
                });
            }
            else{
                $(".mobile_tip").html('请输入短信验证码');
                $(".mobile_tip").show();
                str=str&&false;
            }

        }
        else {
            $(".varification").html('请输入验证码');
            $(".varification").show();
            str=str&&false;
        }


        return str;

    }


    function tonextThree(){
        var mobile = "${userMobile}";
        //验证码
        var enterValue = $("#varification").val();
        //短信验证码
        var codetext=$("#dynamiccode").val();
        if(enterValue != ''&&enterValue!=null) {
            $(".varification").hide();
            if(checkmobilecode()){
                $.ajax({
                    url: "checkpatchca.htm?enterValue=" + enterValue,
                    async: false,
                    success: function (data) {
                        if(data==0){
                            $(".varification").html('验证码不正确');
                            $(".varification").show();

                        }else{

                            if(codetext==''||codetext==null){
                                $(".mobile_tip").html('请输入短信验证码');
                                $(".mobile_tip").show();
                            }
                            else{
                                $.ajax({
                                    url: "checkmobilecode.htm?codetext=" + codetext + "&mobile=" + mobile,
                                    context: document.body,
                                    async:false,
                                    success: function (data) {
                                        if (data == 0) {
                                            $(".mobile_tip").html('短信验证码不正确');
                                            $(".mobile_tip").show();
                                            $("#sendCode").removeAttr("disabled");
                                            //如果失败修更新验证码
                                            $("#checkCodeImg").click();
                                        } else {
                                            window.location.href="${basePath}/findCodeThree.htm?mobile="+mobile+"&codetext="+codetext;
                                        }
                                    }
                                });
                            }

                        }
                    }
                });
            }
            else{

            }

        }else{
            $(".varification").html("请输入验证码");
            $(".varification").show();
        }
    }

</script>
</html>