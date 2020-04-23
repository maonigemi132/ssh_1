layui.config({
    base: '/static/client/cropper/layui_modules/' //你存放新模块的目录，注意，不是layui的模块目录
}).use('cropperUpload'); //加载入口


//一般直接写在一个js文件中
layui.use(['layer', 'form','element','laydate','cropperUpload','layedit'], function(){
    var layer = layui.layer
        ,form = layui.form;
    var element = layui.element;
    var laydate = layui.laydate;
    var   cropperUpload=layui.cropperUpload;
    let layedit = layui.layedit;



    //表单封面裁剪图片组件
    let  formCoverUpload=cropperUpload.init({
        select:"#formCoverUpload",
        aspectRatio:16/9,
        imgWidth:800,
        imgHeight:400,
        formDataId: "#blogForm", 
        success:function (base64Img,blog) {  //base64Img  base64格式图片    blog  二进制图
            $("#showImg").attr("src",base64Img)
        }
    })

    $(".closewindow").click(function(){
    	window.parent.$(".layui-layer-close").trigger("click");
    })
    
    
    if($(".closewindow").length>0){
    	$("#blogForm *").attr("disabled","disabled");
    	$(".closewindow").removeAttr("disabled");
    	form.render();
    }
    
    
  
    let edit=layedit.build('demo'); //建立编辑器



    //个人表单提交
    
    let ding=$("#showImg").attr("src");
 	  
     //博客表单提交
     form.on('submit(blogForm)', function(data){
         if(layedit.getText(edit)==""){
         	layer.tips('文章的内容不能为空', '#LAY_layedit_1', {
         		  tips: [1, 'black'] //还可配置颜色
         		});
         	return false;
         }
         if($("#showImg").attr("src").toString().endsWith("cover_default.png")){
         	layer.tips('不可以使用默认 图片作为封面', '#showImg', {
       		  tips: [1, 'black'] //还可配置颜色
       		});
       	return false;
         }
         let adata={content:layedit.getContent(edit),status:$(data.elem).val()}
 	        formCoverUpload.ajaxPost("/article/update-article",function(res){
 	        	if(res.success){
 	        		
 	        		data.form.reset();
 	        		$("#showImg").attr("src",ding);
 	        		//edit=layedit.build('demo');
 	        		formCoverUpload.formData=new FormData();
 	        	}
 	        	layer.msg(res.content)	        	
 	         },adata)
         
         

         return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
     });




    //数据校验
    form.verify({
        pass: [/^[\S]{6,12}$/,'密码必须6到12位，且不能出现空格'],
        repass:function(value, item){
            if(!/^[\S]{6,12}$/.test(value)){
                return '密码必须6到12位，且不能出现空格';
            }
            if(value!=$('[type=password]').val()){
                return '二次密码输入不一致';
            }
        },
        summary:[/^.{1,200}$/,"摘要必须在200字以内"],
        nickName:[/^[\u4e00-\u9fa5_a-zA-Z0-9]{1,15}$/,'昵称15个字符内，只能是字母数字下划线或者中文'],
        captcha:[/^[a-zA-Z0-9]{4,5}$/,"请输入有效验证码"],
        title:[/^[\u4e00-\u9fa5_a-zA-Z0-9]{1,50}$/,'标题50个字符内，只能是字母数字下划线或者中文']
    });





});