// 判断时候在Iframe框架内,在则刷新父页面
if (self != top) {
    parent.location.reload(true);
    if (!!(window.attachEvent && !window.opera)) {
        document.execCommand("stop");
    } else {
        window.stop();
    }
}

$(function () {
	$("input[name='loginName']").bind("change",function(){
		let loginName = $(this).val();
		let _this = $(this);
		if (loginName) {
			$.ajax({
				url: basePath+"/user/checkExist?loginName="+loginName,
				dataType: 'json',
				method: 'GET',
				success: function(data) {
					if(false==data.success){
						alert('error');
					}else{
						if (data.result == 1) {
							_this.focus();
						}
						if (data.result == 0) {
						}
					}
				},
				error: function(xhr) {
					// 导致出错的原因较多，以后再研究
					alert('error:' + JSON.stringify(xhr));
				}
			});
		}
	});
	
    // 得到焦点
    $("#password").focus(function () {
        $("#left_hand").animate({
            left: "150",
            top: " -38"
        }, {
            step: function () {
                if (parseInt($("#left_hand").css("left")) > 140) {
                    $("#left_hand").attr("class", "left_hand");
                }
            }
        }, 2000);
        $("#right_hand").animate({
            right: "-64",
            top: "-38px"
        }, {
            step: function () {
                if (parseInt($("#right_hand").css("right")) > -70) {
                    $("#right_hand").attr("class", "right_hand");
                }
            }
        }, 2000);
    });
    // 失去焦点
    $("#password").blur(function () {
        $("#left_hand").attr("class", "initial_left_hand");
        $("#left_hand").attr("style", "left:100px;top:-12px;");
        $("#right_hand").attr("class", "initial_right_hand");
        $("#right_hand").attr("style", "right:-112px;top:-12px");
    });
    // 验证码
    $("#captcha").click(function() {
        var $this = $(this);
        var url = $this.data("src") + new Date().getTime();
        $this.attr("src", url);
    });
    // 登录
    $('#registForm').form({
        url: basePath + '/user/add',
        onSubmit : function() {
            progressLoad();
            var isValid = $(this).form('validate');
            if(!isValid){
                progressClose();
            }
            return isValid;
        },
        success:function(result){
            progressClose();
            result = $.parseJSON(result);
            if (result.success) {
                window.location.href = basePath + '/index';
            }else{
                // 刷新验证码
                showMsg(result.msg);
            }
        }
    });
});
function submitForm(){
    $('#registForm').submit();
}
function clearForm(){
    $('#registForm').form('clear');
}
//回车登录
function enterlogin(){
    if (event.keyCode == 13){
        event.returnValue=false;
        event.cancel = true;
        $('#registForm').submit();
    }
}
