layui.config({
    base: '/static/client/cropper/layui_modules/' //你存放新模块的目录，注意，不是layui的模块目录
}).use('cropperUpload'); //加载入口


let  formCoverUpload;
let  coverupload;

//一般直接写在一个js文件中
layui.use(['layer', 'form','element','laydate','cropperUpload','layedit','laypage','table'], function(){
    let layer = layui.layer
        ,form = layui.form;
    let element = layui.element;
    let laydate = layui.laydate;
    let   cropperUpload=layui.cropperUpload;
    let layedit = layui.layedit;
    let  laypage=layui.laypage;

    let table = layui.table;
    //执行一个laydate实例
    laydate.render({
        elem: '.date' //指定元素
    });

    //日期时间范围选择
    laydate.render({
        elem: '.rangeTime'
        ,range: '~' //或 range: '~' 来自定义分割字符
    });



    //表单封面裁剪图片组件
    formCoverUpload=cropperUpload.init({
        select:"#formCoverUpload",
        aspectRatio:800/400,
        imgWidth:800,
        imgHeight:400,
        formDataId: "#blogForm",
        success:function (base64Img,blog,that) {  //base64Img  base64格式图片    blog  二进制图
            $("#showImg").attr("src",base64Img)
        }
    })


  





    /**
     LayEdit并不提供服务端的图片接受，但你需要在图片上传成功后对LayEdit返回如下格式的JSON信息：
     {
      "code": 0 //0表示成功，其它失败
      ,"msg": "" //提示信息 //一般上传失败后返回
      ,"data": {
        "src": "图片路径"
        ,"title": "图片名称" //可选
      }
    }
     */
    layedit.set({
        uploadImage: {
            url: '/article/articleUploadImg?token='+$("[name='token']").val() //接口url
            ,type: 'POST' //默认post
        }
    });
   let edit=layedit.build('demo'); //建立编辑器
     
   let ding=$("#showImg").attr("src");
	  
    //博客表单提交
    form.on('submit(blogForm)', function(data){
        console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
        console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
        console.log($("#blogForm").serializeJson()) //当前容器的全部表单字段，名值对形式：{name: value}
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
	        formCoverUpload.ajaxPost("/article/add-article",function(res){
	        	if(res.success){
	        		data.form.reset();
	        		$("#showImg").attr("src",ding);
	        		edit=layedit.build('demo');
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


    
    function previewImg(obj) {
        var img = new Image();
        img.src = obj.src;
        var height = img.height + 50; //获取图片高度
        var width = img.width; //获取图片宽度
        var imgHtml = "<img src='" + obj.src + "' />";
        //弹出层
        layer.open({
            type: 1,
            shade: 0.8,
            offset: 'auto',
            area: [width + 'px',height+'px'],
            shadeClose:true,//点击外围关闭弹窗
            scrollbar: false,//不现实滚动条
            title: "图片预览", //不显示标题
            content: imgHtml, //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            cancel: function () {
                //layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', { time: 5000, icon: 6 });
            }
        });
    }

    form.on('submit(searchForm)', function(data){
    	
    	loadPageList(1,data.field);
    	return false;
    });
   

    loadPageList(1);
    
    loadPageList(1,null,true);
    
    function loadPageList(pageIndex,JsonParam,isDraft){
    	if(isDraft==null) isDraft=false
    	$.ajax({
    		url:"/article/"+isDraft+"-listArticles?token="+$("[name='token']").val()+"&pageIndex="+pageIndex+"&t="+new Date(),
    		data:JsonParam,
    		type:"GET",
    		dataType:"JSON",
    		success: function(res){
    			let list=res.dataList;
    			
    			
    			
    			let html='';
    			
    			
    			for(let a of list){
    				let msg=a.status==4?'<span style="color:red;">审核不通过</span>':'';
    				let imgUrl=(a.cover!=null&a.cover!='')?a.cover:'/static/client/img/default-photo.jpg';
    				
    				let buttons='<button type="button"  blog-id="'+a.uuid+'"  class="layui-btn layui-btn-xs layui-btn-normal  cover">'+
    				'<i class="layui-icon layui-icon-picture-fine"></i>'+
    				'更&nbsp;换&nbsp;封&nbsp;面'+
    				'</button><br>'+
    				'<button type="button" class="layui-btn layui-btn-xs layui-btn-primary"  blog-id="'+a.uuid+'" lay-event="lookBlog">'+
    				'<i class="layui-icon layui-icon-search"></i>'+
    				'查&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;看'+
    				'</button> <br>'+
    				'<button type="button" class="layui-btn layui-btn-xs layui-btn-normal"  blog-id="'+a.uuid+'"   lay-event="editBlog" >'+
    				'<i class="layui-icon layui-icon-edit"></i>'+
    				'编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;辑'+
    				'</button> <br>'+
    				'<button type="button" class="layui-btn layui-btn-xs layui-btn-danger"  blog-id="'+a.uuid+'"  lay-event="draftBlog" >'+
    				'<i class="layui-icon layui-icon-delete"></i>'+
    				'移送草稿箱'+
    				'</button>';
    				
    				if(isDraft){
    					buttons=
        				'<button type="button" class="layui-btn layui-btn-xs layui-btn-normal"  blog-id="'+a.uuid+'"   lay-event="retunBlog" >'+
        				'<i class="layui-icon layui-icon-edit"></i>'+
        				'恢&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;复'+
        				'</button> <br>'+
        				'<button type="button" class="layui-btn layui-btn-xs layui-btn-danger"  blog-id="'+a.uuid+'"  lay-event="deleteBlog" >'+
        				'<i class="layui-icon layui-icon-delete"></i>'+
        				'删&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;除'+
        				'</button>';
    				}
    				
    				
    				html+='<tr>'+
    				'<td>'+
    				'<img src="'+imgUrl+'">'+
    				'</td>'+
    				'<td>'+a.title+'</td>'+
    				'<td>'+a.summary+'</td>'+
    				'<td style="text-align: center;">'+msg+
    				'<input type="checkbox"  blog-id="'+a.uuid+'" lay-filter="status" '+(a.status==0?'checked':'')+' lay-skin="switch" lay-text="已发布|未发布">'+
    				'<br><br><button blog-id="'+a.uuid+'" lay-event="findEvent" type="button" class="layui-btn layui-btn-xs">审核日志</button></td>'+
    				'<td style="text-align: center;">'+a.createtime+'</td>'+
    				'<td style="text-align: center;">'+
    				
    				buttons
    				
    				'</td>'+
    				'</tr>';
    			}
    			
    			let formJsonData=null;
    			
    			if(isDraft){
    				formJsonData=$("#pageBlogTable1").parent().serializeJson();
    				$("#pageBlogTable1 tbody").html(html);
    				form.render()
    			}	
    			else{
    				$("#pageBlogTable tbody").html(html);
    				formJsonData=$("#pageBlogTable").parent().serializeJson();
    				form.render()
    			}
    			
    			
    			
    			 //执行一个laypage实例
    		    laypage.render({
    		    	curr:res.pageIndex,
    		    	limit:5,
    		        elem: 'blogList-page'+(isDraft)?'1':'' //注意，这里的 test1 是 ID，不用加 # 号
    		        ,count: res.count //数据总数，从服务端得到
    		        ,jump: function(obj, first){
    		            //obj包含了当前分页的所有参数，比如：
    		            console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
    		            console.log(obj.limit); //得到每页显示的条数
    		            
    		            //首次不执行
    		            if(!first){
    		              loadPageList(obj.curr,formJsonData)
    		            }
    		          }
    		    });
    		    
    		    
    		    initEvent()
    		},error:function(){
    			 $("#pageBlogTable tbody").html('<tr><td colspan="6"><h3>加载数据错误</h3></td></tr>')
    		}
    		
    		
    	})
    	
    }
    
   
    

    	



    
    function initEvent(){
    	/**
	     * 预览图片
	     * @param obj
	     */
	    $(".infoTable tr>td img").click(function () {
	        previewImg($(this)[0]);

	    })
    	 //操作
    $(".infoTable  [lay-event]").click(function () {
    	let tr=$(this).parent().parent();
        let  event=$(this).attr("lay-event");
        let uuid=$(this).attr("blog-id");
        let token=$("[name='token']").val();
        if(event=="lookBlog"){
        	openUrlWindow("/article/look-find?uuid="+uuid+"&token="+token)

        }else if(event=="editBlog"){//修改文章
            openUrlWindow("/article/update-find?uuid="+uuid+"&token="+token)

        }else if(event=="draftBlog"){//移送到草稿箱
        	  let cf=layer.confirm("您确定要放到草稿箱吗？",{
        		  btn:["确定","取消"],
        	  		yes:function(){
        	  			layer.close(cf)
        	  			
        	  			$.ajax({
        	  				url:"/article/delete-article?token="+$("[name='token']").val(),
        	  	    		data:{uuid:uuid},
        	  	    		type:"POST",
        	  	    		dataType:"JSON",	
        	  				success: function(res){
        	  					if(res.success){
        	  				     // tr.remove();//删除行
        	  				      loadPageList(1);
        	  					}
        	  					
        	  					layer.msg(res.content);
        	  				},error: function(){
        	  					 data.elem.checked=!origin
       	  	    	  		  	form.render();
        	  					layer.msg("无服务异常");
        	  				}
        	  				   	  				
        	  			})
        	  			
        	  		}
        	  });
        	
        	
        	
        }else if (event=="findEvent") {//查看审核不通过原因
        	openUrlWindow("/check/findLog?uuid="+uuid+"&token="+token,"查看审核记录");
        }
    })
    
    
    //封面裁剪图片组件
    coverupload=cropperUpload.init({
        select:".cover",
        aspectRatio:16/9,
        success:function (base64Img,blog,that) {  //base64Img  base64格式图片    blog  二进制图
            //获得触发的按钮
            let  btn=that.defaultOptions.eventEl;
            //本地直接更换
            //$(btn).parent().parent().find("td:first>img").attr("src",base64Img)
            //提交至服务器
            coverupload.ajaxPost("/article/cover-article",function (data) {
                if(data.success){
                    $(btn).parent().parent().find("td:first>img").attr("src",base64Img)
                }
                    layer.msg(data.content)
                
            },{token:$("[name='token']").val(),uuid:btn.attr("blog-id")})
        }
    })
    
    form.on('switch(status)', function(data){
    	let origin=data.elem.checked;
    	let uuid=$(data.elem).attr("blog-id");
    	let status=data.elem.checked?0:1;
    	let str=""
    	  if(origin){
    		  str="设置为发布状态吗?"
    	  }else{
    		  str="取消发布状态"
    	  }
    	  let cf=layer.confirm("您确定要"+str,{
    		  btn:["确定","取消"],
    	  		yes:function(){
    	  			layer.close(cf)
    	  			
    	  			$.ajax({
    	  				url:"/article/status-article?token="+$("[name='token']").val(),
    	  	    		data:{uuid:uuid,status:status},
    	  	    		type:"POST",
    	  	    		dataType:"JSON",	
    	  				success: function(res){
    	  					if(!res.success){
    	  				      data.elem.checked=!origin
    	  	    	  		  form.render();
    	  					}
    	  					
    	  					layer.msg(res.content);
    	  				},error: function(){
    	  					 data.elem.checked=!origin
   	  	    	  		  	form.render();
    	  					layer.msg("无服务异常");
    	  				}
    	  				   	  				
    	  			})
    	  			
    	  		},
    	  	  btn2:function(){
    	  		  data.elem.checked=!origin
    	  		  form.render();
    	  	  },cancel:function(){
    	  		data.elem.checked=!origin
  	  		  form.render();
    	  	  }
    	  });
    	}); 
    	
    }

   

    function openUrlWindow(url,title) {
        if(title==null)  title="正在编辑"
        let  ww= layer.open({
            type: 2,
            title:title,
            skin: 'layui-layer-demo', //样式类名
            closeBtn: 0, //不显示关闭按钮
            anim: 2,
            shadeClose: true, //开启遮罩关闭
            content: url,
            closeBtn:1,
            success:function () {
                $(".layui-layer-content").attr("style","height:100%")
                layer.full(ww);
                setTimeout(function () {

                    layer.tips('点击这里关闭！', '.layui-layer-close', {
                        tips: [1, 'red'],
                        time: 4000
                    });
                },300)
            }
        });
    }





});