layui.config({
    base: '/static/client/cropper/layui_modules/' //你存放新模块的目录，注意，不是layui的模块目录
}).use('cropperUpload'); //加载入口


//一般直接写在一个js文件中
layui.use(['layer', 'form','element','laydate','cropperUpload'], function(){
    var layer = layui.layer
        ,form = layui.form;
    var element = layui.element;
    var laydate = layui.laydate;
    var   cropperUpload=layui.cropperUpload;

    //执行一个laydate实例
    laydate.render({
        elem: '.date' //指定元素
    });


    //微信二维码裁剪图片组件
    let  cupload=cropperUpload.init({
        select:".cropperFilePicker",
        formDataId:"#userForm",
        imgWidth:200,
        imgHeight:200,
        formDataId:"#userForm",
        uploadFileName:"file",
        success:function (base64Img,blog) {  //base64Img  base64格式图片    blog  二进制图
            $("#showImg").attr("src",base64Img)
        }
    })
    
      //头像裁剪图片组件
	  let  cupload2=cropperUpload.init({
	      select:"#changePhoto",
	      uploadFileName:"file",
	      success:function (base64Img,blog) {  //base64Img  base64格式图片    blog  二进制图
	    	  cupload2.ajaxPost("/user/changePhoto",function(data){
	    		 if(data.success){
	    			 $(".userPhoto img").attr("src",base64Img)
	    		 }
	    		  layer.msg(data.content)
	    	  },{token:$("[name='token']").val()})
	    	  
	    	  
	          //$("#photoImg").attr("src",base64Img)
	      }
	  })
	  
	  
	  
     
	  



    //个人表单提交
    form.on('submit(formUser)', function(data){
    	console.log(cupload)
    	
        cupload.ajaxPost("/user/updateUserInfo",function(res){
        		if(res.success){
        			$("#uname").html(data.field.name)
        		}	
        		layer.msg(res.content)
        })
       
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });




    //数据校验
    form.verify({
        pass: [/^[\S]{6,12}$/,'密码必须6到12位，且不能出现空格'],
        repass:function(value, item){
            if(!/^[\S]{6,12}$/.test(value)){
                return '密码必须6到12位，且不能出现空格';
            }
            if(value!=$('[type=password]:eq(2)').val()){
                return '二次密码输入不一致';
            }
        },
        captcha:[/^[a-zA-Z0-9]{4,5}$/,"请输入有效验证码"]
    });



    //密码表单提交
    form.on('submit(formPass)', function(data){
        console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
        console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
        console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}

        let loading = layer.load(1, {
          shade: [0.6,'black'] //0.1透明度的白色背景
      });
        $.ajax({
    		  url:"/getEncode",
    		  data: "token="+data.field.token,
    		  type: "GET",
    		  dataType: "JSON",
    		  success: function(res){
    			  if(res.code){
      				  let code=res.code;
      				  let originpass=$.base64.encode(data.field.originpass+"-"+code);
      				  let pass=$.base64.encode(data.field.pass+"-"+code);
      				  let repass=$.base64.encode(data.field.repass+"-"+code);
     				 
      				 //异步请求
      				  data.field.originpass=originpass;
      				 data.field.pass=pass;
      				 data.field.repass=repass;
  			        $.ajax({
  			            url:'/user/resetpass',
  			            type:"POST",
  			            data:data.field,
  			            dataType:"JSON",
  			            success:function (rs) {
  			            	 //处理成功或者失败
  	  						  if(rs.success){
  	  							  layer.msg(rs.content,{end:function(){
  	  								  window.location="/client/login.html"
  	  							  }});
  	  						  }else {
  	  							  layer.msg(rs.content);
  	  						  }			            	
  			            },error:function () {
  			                layer.msg("服务器连接异常...");
  			            },complete:function () {
  			                layer.close(loading);
  			            }
  			        });
    			  }else{
    				  	layer.msg("非法操作",{end:function(){
    				  		window.location="/client/register.html";				  		
    				  		
    				  	}})
    			  }      
    		  },error:function(){
      			  layer.close(loding);
      			  layer.msg("服务器异常")
      		  }     		  
      	  })  
        

        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });




  //切换选项卡
    $(".site-tree-noicon [lay-event]").click(function () {
        let  f=$(this).attr("lay-event");
        if($("[lay-id='"+f+"']").length==0){
            window.location=$(this).attr("lay-url");
        }else{
            $(".site-tree-noicon").removeClass("site-tree-active");
            $(this).parent().addClass("site-tree-active");
            element.tabChange('tableInfo', $(this).attr("lay-event"));
        }
    })
    element.on('tab(tableInfo)', function(data){
       let  layid=$(this).attr("lay-id");
        location.hash = 'tab='+ $(this).attr('lay-id');
        $(".site-tree-noicon").removeClass("site-tree-active");
        $("[lay-event="+layid+"]").parent().addClass("site-tree-active");
    });

    //Hash地址的定位
    var layid = location.hash.replace(/^#tab=/, '');
    element.tabChange('tableInfo', layid);





});