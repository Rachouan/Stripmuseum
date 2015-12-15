$(function () {

	function init () {

		$("form#addNew").on("submit",function	(e) {
			console.log("UPLOAD");
			return false;
		});

		$("#addNew").submit(function(e) {

	    var url = "http://10.3.210.104:8080/Ruimtes/voegRuimteToe"; // the script where you handle the form input.
			var formData = new FormData($(this)[0]);
			/*var data = new FormData();
			jQuery.each(jQuery('#image')[0].files, function(i, file) {
			    data.append('image', file);
			});*/
			formData.append("image", jQuery('#image')[0].files[0]);
			console.log(formData);
			
			jQuery.ajax({
			    url: url,
			    data: formData,
			    cache: false,
			    contentType: false,
			    processData: false,
			    type: 'POST',
			    success: function(data){
			        alert(data);
			    }
			});


	    e.preventDefault(); // avoid to execute the actual submit of the form.
	});

		loadRuimtes("en");

		/*$('form#addNew').on('submit',function (e) {

			e.preventDefault();

			var fd = new FormData(document.getElementById($(this)[0]));
			console.log($(fd).serialize());
            $.ajax({
              url: "http://10.3.210.104:8080/Ruimtes/voegRuimteToe",
              type: "POST",
              data: fd,
              processData: false,  // tell jQuery not to process the data
              contentType: false   // tell jQuery not to set contentType
            }).done(function( data ) {
                console.log("PHP Output:");
                console.log( data );
            });
            return false;
		});*/
	}

	function loadRuimtes (lang) {

		$.get( "http://10.3.210.104:8080/Ruimtes/getAll?lang="+lang, function( data ) {

			$(data).each(function (key,val) {
				console.log(val);

				$("<section/>").addClass("comics").html("<header><h1>"+val.name+"</h1><h2>"+val.comic+"</h2><a href='' class='edit'></a></header><nav><ul><li><a href=''><span class='icon enter'></span><span>Enter</span></a></li><li><a href=''><span class='icon edit'></span><span>Edit</span></a></li><li><a href='' id='"+val.id+"'><span class='icon trash'></span><span>Delete</span></a></li></ul></nav>").appendTo("article");
			});


		});

		assignActions();
	}


	function readURL(input) {

		var upload = $(input).parent();

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
					$(input).parent().css({"background-image":"url("+e.target.result+")","background-size":"cover"});
        };

        reader.readAsDataURL(input.files[0]);
    }
}


	function assignActions () {

		$("input[type='file']").change(function(){
		    readURL(this);
		});



		$("a#add").on('click',function	(e) {
			e.preventDefault();
			console.log("CLICKED");

			if($(this).hasClass('close')){
					$(this).removeClass('close');
					$(this).html('<span>New</span>');
			}else {
					$(this).addClass('close');
					$(this).html('<span>close</span>');
			}

			if($("article").hasClass("add")){
					$("article").removeClass("add");
			}else {
				$("article").addClass("add");
			}


		});

		$("section.comics header a").on('click',function (e) {
			e.preventDefault();

			var comic = $(this).parent().parent();
			var navigation = comic.find("nav");

			if(navigation.hasClass("open")){
				navigation.removeClass("open");
			}else{
				navigation.addClass("open");
			}


			navigation.find("a").on("click",function (e) {
				e.preventDefault();

				console.log($(this).find("span"));
				switch($(this).find("span").attr("class")){

					case "icon trash":

					console.log($(this).attr("id"));
					$.post( "10.3.210.104:8080/Ruimtes/verwijderRuimte",{id:$(this).attr("id")}, function( data ) {
					  console.log(data);
					});

					break;

				}

			});

		});
	}

	init();
});
