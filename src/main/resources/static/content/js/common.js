var dialog = new function() {
	this.confirm = function (message, fnYes, fnNo){
		var n = new Noty({
			  theme: "bootstrap-v4",
			  layout: 'center',
			  modal: true,
			  text: message,
			  buttons: [
			    Noty.button('YES', 'btn btn-success', function () {
			        n.close();
			        if(fnYes !== undefined){
			    			fnYes();
			    		}
			    }, {id: 'button1', 'data-status': 'ok'}),

			    Noty.button('NO', 'btn btn-error', function () {
			        n.close();
			        if(fnNo !== undefined){
			    			fnNo();
			    		}
			    })
			  ]
		}).show();
	},
	this.confirmDefault = function (fnYes, fnNo){
		this.confirm("Are you sure?", fnYes, fnNo);
	},
	this.showMessage = function (message, type, timeout){
		 new Noty({
			  theme: "bootstrap-v4",
			  layout: 'center',
			  type: type === undefined ? 'info' : type,
			  text: message,
			  timeout: timeout == undefined ? 1000 : timeout,
			  progressBar: false
		}).show(); 
	},
	this.alert = function (message){
		this.showMessage(message, 'alert');
	},
	this.success = function (message){
		this.showMessage(message, 'success');
	},
	this.warning = function (message){
		this.showMessage(message, 'warning');
	},
	this.error = function (message){
		this.showMessage(message, 'error');
	},
	this.info = function (message){
		this.showMessage(message, 'info');
	},
	this.showAjaxMessage = function(ajaxResult){
		if(!ajaxResult.isSuccess){
			this.error(ajaxResult.message);
			return;
		}
		
		this.success(ajaxResult.message);
	}
};

// common funcs
function post(url,fnSuccess, fnError){
	$.ajax({
		type: "POST",
		url : url,
		beforeSend : function(xhr) {
			// authen token
			xhr.setRequestHeader($("meta[name='_csrf_header']").attr("content"),$("meta[name='_csrf']").attr("content"));
			xhr.setRequestHeader("Accept","application/json");
			xhr.setRequestHeader("Content-Type","application/json");
		},
		success : fnSuccess,
		error: fnError
	});
}

function postJson(url, data, fnSuccess, fnError){
	$.ajax({
		type: "POST",
		contentType : 'application/json; charset=utf-8',
		url : url,
		data: JSON.stringify(data),
		dataType: 'json',
		beforeSend : function(xhr) {
			// authen token
			xhr.setRequestHeader($("meta[name='_csrf_header']").attr("content"),$("meta[name='_csrf']").attr("content"));
			xhr.setRequestHeader("Accept","application/json");
			xhr.setRequestHeader("Content-Type","application/json");
		},
		success : fnSuccess,
		error: fnError
	});
}

function addDelete(selector){
	$(selector).click(function(event) {
		var target = $(event.target);
		
		// click on a tag
		var url = target.attr("href");
		
		// click on icon
		if(url == undefined){
			url = target.closest("a").attr("href");
		}
		
		dialog.confirmDefault(function () {
		    	post(url, function(ajaxResult) {
		    		if(ajaxResult.isSuccess){
		    			// remove deleted row
		    			target.closest("tr").remove();
		    		}
		    		
				dialog.showAjaxMessage(ajaxResult);
			});
	    });
		
		event.preventDefault();
	});
}





