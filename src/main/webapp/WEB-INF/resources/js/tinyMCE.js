/**
 * 
 */
  window.onload= function() {
window.tinymce.init({ 
	selector: 'textarea',
	plugins: ['codesample code','emoticons',
	          'print image media',
	          'textcolor colorpicker textpattern',
	          'insertdatetime nonbreaking save table contextmenu directionality'],
	toolbar: 'insertfile undo redo | styleselect forecolor backcolor | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | codesample | image media |  print',
	menubar: ["default","tools"],
	setup: function(editor){
		editor.addMenuItem('pic', {
			 text: 'Upload Picture',
		      context: 'tools',
		      onclick: function(){
		    	  editor.windowManager.open({
		    		  title: 'Upload Picture',
		    		  url: 'add-picture',
		    		  width: 200,
		    		  height: 130
		    		})
		      }
		});
		editor.on('keydown', function(event) {
	        if (event.keyCode == 9) { // tab pressed
	          if (event.shiftKey) {
	            editor.execCommand('Outdent');
	          }
	          else {
	            editor.execCommand('Indent');
	          }

	          event.preventDefault();
	          return false;
	        }
	    });
		editor.addMenuItem('vid', {
			 text: 'Upload Video',
		      context: 'tools',
		      onclick: function(){
		    	  editor.windowManager.open({
		    		  title: 'Upload Picture',
		    		  url: 'add-picture',
		    		  width: 200,
		    		  height: 130
		    		})
		      }
		});
		},
	imagetools_cors_hosts: ['tinymce.com', 'codepen.io'],
	  content_css: [
	    '//fonts.googleapis.com/css?family=Lato:300,300i,400,400i',
	    '//cdnjs.cloudflare.com/ajax/libs/prism/0.0.1/prism.css',
	    '//www.tinymce.com/css/codepen.min.css',
	    '//fonts.googleapis.com/css?family=Source+Sans+Pro'
	  ]
	  
});
  }
