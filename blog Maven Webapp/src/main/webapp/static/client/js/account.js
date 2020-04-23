//一般直接写在一个js文件中
layui.use(['layer', 'form'], function(){
  let layer = layui.layer
    
      ,form = layui.form;


  //数据校验
  form.verify({
      pass: [/^[\S]{6,12}$/,'密码必须6到12位，且不能出现空格'],
      username: function(value, item){ //value：表单的值、item：表单的DOM对象
          if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5]{1,20}$").test(value)){
              return '用户名不能有特殊字符,可以使用中文、字母、数字、下划线不能超过20个字符';
          }
          if(/(^\_)|(\__)|(\_+$)/.test(value)){
              return '用户名首尾不能出现下划线\'_\'';
          }
          if(/^\d+\d+\d$/.test(value)){
              return '用户名不能全为数字';
          }
      },
      repass:function(value, item){
          if(!/^[\S]{6,12}$/.test(value)){
             return '密码必须6到12位，且不能出现空格';
          }
          if(value!=$('[type=password]').val()){
              return '二次密码输入不一致';
          }
      },
      captcha:[/^[a-zA-Z0-9]{4,5}$/,"请输入有效验证码"]
  });

 
  //登录表单提交
  form.on('submit(login)', function(data){
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
    				  let password=$.base64.encode(data.field.password+"-"+code);
    				  
   				 
    				 //异步请求
    				  data.field.password=password;
    				  
			        $.ajax({
			            url:'/user/login',
			            type:"POST",
			            data:data.field,
			            dataType:"JSON",
			            success:function (rs) {
			            	 //处理成功或者失败
	  						  if(rs.success){
	  							  layer.msg(rs.content,{end:function(){
	  								  window.location="/client/home.html"
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


  //更换验证码
  $("#captcha").click(function () {
      let  url="/kaptcha"
      $(this).find("img").attr("src",url+"?n="+parseInt(Math.random()*1000));
  })

  form.on('submit(registerForm)', function(data){
	  //加载
	  var loding = layer.load(1, {
		  shade: [0.5,'black'] //0.1透明度的白色背景
		});
	  
	  
	  $.ajax({
		  url:"/getEncode",
		  data: "token="+data.field.token,
		  type: "GET",
		  dataType: "JSON",
		  success: function(res){
			  if(res.code){
				  let code=res.code;
				  let password=$.base64.encode(data.field.password+"-"+code);
				  let repassword=$.base64.encode(data.field.repassword+"-"+code);
				  
				  //同步请求
				/*  $(data.form).find("[name='password']").val(password)
				  $(data.form).find("[name='repassword']").val(repassword)
				  $(data.form).submit();*/
				  
				  //异步请求
				  data.field.password=password;
				  data.field.repassword=repassword;
				  $.ajax({
					  url:"/user/registerJson",
					  data:data.field,
					  type: "POST",
					  dataType: "JSON",
					  success: function(rs){
						  //处理成功或者失败
						  if(rs.success){
							  layer.msg(rs.content,{end:function(){
								  window.location="/client/login.html"
							  }});
						  }else {
							  layer.msg(rs.content);
						  }
						  						  
						  },error:function(){
							  layer.msg("服务器异常")  
						  },complete: function(){
							  layer.close(loding);
							  
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
});











