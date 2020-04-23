//一般直接写在一个js文件中
layui.use(['layer','form','jquery'], function(){
	var layer = layui.layer;
    let	jquery1=layui.jquery;
	let openWind=null;

	let startTime=new Date().getTime();//获得时间
	
	jquery1(document).mousemove(function(){		
		startTime=new Date();
	});
	
	setInterval(function() {
		let now=new Date().getTime();
		console.log("---检查结果---"+((now-startTime)/1000>60))
		if((now-startTime)/1000>60){
			if(openWind==null){
				openWind=layer.open({
					  type: 1,
					  title:"系统提示",
					  skin: 'layui-layer-demo', //样式类名
					  closeBtn: 0, //不显示关闭按钮
					  anim: 2,
					  shadeClose: false, //开启遮罩关闭
					  content:"<h3 style='text-align:center;'>登录已失效</h3>",
					  btn:["重新登录","返回主页"],
					  yes:function(){
						  window.location="/client/login.html"
					  },function(){
						  window.location="/client/home.html"
					  },end:function(){
						  openWind=null;
					  }
					});
			}
		}				
	}, 6000);
	
	
});