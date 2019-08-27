//导航选择器
var url = window.location.href;
if (url.indexOf("statisticsManage") > 0) {
	$("#analysis").addClass("active");
	$("#statisticsManage").addClass("active");
} else if (url.indexOf("applicationManage") > 0 || url.indexOf("channelManage") > 0
		|| url.indexOf("channelEdit") > 0 || url.indexOf("appinfoEdit") > 0 || url.indexOf("clickRecord") > 0) {
	$("#marketing").addClass("active");
	$("#applicationManage").addClass("active");
}

// 提示条配置
//toastr.options = {
//	"closeButton" : true,
//	"debug" : false,
//	"progressBar" : true,
//	"preventDuplicates" : false,
//	"positionClass" : "toast-top-right",
//	"onclick" : null,
//	"showDuration" : "400",
//	"hideDuration" : "1000",
//	"timeOut" : "7000",
//	"extendedTimeOut" : "1000",
//	"showEasing" : "swing",
//	"hideEasing" : "linear",
//	"showMethod" : "fadeIn",
//	"hideMethod" : "fadeOut"
//}