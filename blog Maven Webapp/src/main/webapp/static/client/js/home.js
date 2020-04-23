//一般直接写在一个js文件中
layui.use(['layer', 'form','element','carousel','flow','jquery'], function(){
    var layer = layui.layer
        ,form = layui.form;
    var element = layui.element;
    var carousel = layui.carousel;
    var flow = layui.flow;
    var $=layui.jquery;

    //建造实例
    carousel.render({
        elem: '#test1'
        ,width: '100%' //设置容器宽度
        ,arrow: 'always' //始终显示箭头
        //,anim: 'updown' //切换动画方式
    });

    //监听tab
    element.on('tab(blogType)', function(data){
    	  
    	  console.log(data.index); //得到当前Tab的所在下标
    	 
    	  let tid=$(this).attr("id");
    	  blogFlow(data.index,tid)
    	});

    //blogFlow(0)
    
    element.tabChange("blogType", "blogType_"+0);
    
    //博客流加载
    function blogFlow(index,tid){
    	  flow.load({
    	        elem: '#LAY_demo'+index //流加载容器
    	        ,scrollElem: '#LAY_demo2' //滚动条所在元素，一般不用填，此处只是演示需要。
    	        ,isAuto: false
    	        ,isLazyimg: true
    	        ,done: function(page, next){ //加载下一页
    	        	console.log('page:'+page+"-newx:"+next);
    	        	
    	        	$.ajax({
    	        		url:"/article/flowArticle",
    	        		data:{tid:tid,pageIndex:page,token:$("#token").val()},
    	        		type:"GET",
    	        		dataType:"JSON",
    	        		success:function(data){
    	        			var lis = [];
    	        			var list=data.dataList;
    	        			for(let i=0;i<list.length;i++){
    	        				let  html=' <div class="layui-col-md3 flow-ct">\n' +
    	                        '                            <img class="flow-img"  src="'+list[i].cover+'">\n' +
    	                        '\n' +
    	                        '                           <div  class="flow-info">\n' +
    	                        '                                <h3>'+list[i].title+'</h3>\n' +
    	                        '                                 <p>'+list[i].summary+'</p>\n' +
    	                        '                                 <span>\n' +
    	                        '                                     <i class="layui-icon layui-icon-username"></i>\n' +
    	                        '                                     <a href="/client/user/'+list[i].userId+'">'+list[i].userName+'</a>\n' +
    	                        '                                 </span>\n' +
    	                        '\n' +
    	                        '                                 <span>\n' +
    	                        '                                     阅读:'+list[i].reader+'\n' +
    	                        '                                 </span>\n' +
    	                        '                             </div>\n' +
    	                        '                       </div>';


    	                    lis.push(html)
    	        			}
    	        			 next(lis.join(''), page < data.totalPage); //假设总页数为 6	
    	        			
    	        		},error:function(){
    	        			next("", page < 0);
    	        		}
    	        		
    	        	})
    	        	
    	        	
    	        }
    	    });
    }

  





});