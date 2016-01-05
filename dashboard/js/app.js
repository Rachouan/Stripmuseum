$(function () {

	var ip = '10.3.50.226:8080';
	var id;
	var editid;

	function init () {



		$("#addNew").submit(function(e) {

		    var url = "http://"+ip+"/Ruimtes/voegRuimteToe"; // the script where you handle the form input.
				var formData = new FormData();
				var data = new FormData();
				/*jQuery.each(jQuery('#image')[0].files, function(i, file) {
				    data.append('image', file);
				});*/
				formData.append("image", jQuery('#image')[0].files[0]);
				formData.append("name", $('#name').val());
				formData.append("comic_en", $('#comic_en').val());
				formData.append("comic_nl", $('#comic_nl').val());
				formData.append("comic_fr", $('#comic_fr').val());

				jQuery.ajax({
				    url: url,
				    data: formData,
				    cache: false,
				    contentType: false,
				    processData: false,
				    type: 'POST',
				    success: function(data){

								$("article#comics section.upload").removeClass("open");
								loadRuimtes();
				    }
				});


		    e.preventDefault(); // avoid to execute the actual submit of the form.
		});

		$("article#room section article.edit #editRuimteInfo").submit(function(e) {

		    var url = "http://"+ip+"/Info/updateInfo/"; // the script where you handle the form input.
				var formData = new FormData();
				var data = new FormData();
				/*jQuery.each(jQuery('#image')[0].files, function(i, file) {
				    data.append('image', file);
				});*/
				formData.append("info_image", jQuery('#edit_info_image')[0].files[0]);
				formData.append("info_id", editid);
				formData.append("info_name", $('#edit_info_name').val());
				formData.append("description_en", $('#edit_description_en').val());
				formData.append("description_nl", $('#edit_description_nl').val());
				formData.append("description_fr", $('#edit_description_fr').val());

				jQuery.ajax({
				    url: url,
				    data: formData,
				    cache: false,
				    contentType: false,
				    processData: false,
				    type: 'POST',
				    success: function(data){

								$("article#room section article.edit").removeClass("open");
								loadInfo();
				    }
				});


		    e.preventDefault(); // avoid to execute the actual submit of the form.
		});

		$("#editRuimte").submit(function(e) {

		    var url = "http://"+ip+"/Ruimtes/updateRuimte"; // the script where you handle the form input.
				var formData = new FormData();
				var data = new FormData();
				/*jQuery.each(jQuery('#image')[0].files, function(i, file) {
				    data.append('image', file);
				});*/
				formData.append("image", jQuery('#edit_image')[0].files[0]);
				formData.append("r_id", id);
				formData.append("name", $('#edit_name').val());
				formData.append("comic_en", $('#edit_comic_en').val());
				formData.append("comic_nl", $('#edit_comic_nl').val());
				formData.append("comic_fr", $('#edit_comic_fr').val());

				jQuery.ajax({
				    url: url,
				    data: formData,
				    cache: false,
				    contentType: false,
				    processData: false,
				    type: 'POST',
				    success: function(data){

								$("article#comics section.edit").removeClass("open");
								loadRuimtes("en");
				    }
				});


		    e.preventDefault(); // avoid to execute the actual submit of the form.
		});


		$("#addRuimteInfo").submit(function(e) {

		    var url = "http://"+ip+"/Info/voegInfoToeAanRuimte"; // the script where you handle the form input.
				var formData = new FormData();
				var data = new FormData();


				formData.append("info_image",$('#info_image')[0].files[0]);
				formData.append("ruimte_id", id);
				formData.append("info_name", $('#info_name').val());
				formData.append("description_en", $('#description_en').val());
				formData.append("description_nl", $('#description_nl').val());
				formData.append("description_fr", $('#description_fr').val());

				jQuery.ajax({
				    url: url,
				    data: formData,
				    cache: false,
				    contentType: false,
				    processData: false,
				    type: 'POST',
				    success: function(data){
							$("article#room section article.add").removeClass("open");
							loadInfo();
				    }
				});


		    e.preventDefault(); // avoid to execute the actual submit of the form.
		});

		$("article#room header a.add").on("click",function (e) {
				e.preventDefault();

				$("article#room section").animate({scrollTop:0},1000);

				if($(this).hasClass('open')){
					$(this).removeClass('open');
					$("article#room section article.add").removeClass("open");
				}else{
					$(this).addClass('open');
					$("article#room section article.add").addClass("open");
				}
		});

		$("article#room section article.edit aside ul li span").on("click",function (e) {
				e.preventDefault();
				var tab = $(this).parent();


				if(tab.hasClass("open")){
					tab.removeClass("open");
				}else{
					$("article#room section article.edit aside ul li").each(function (key,val) {
						if( tab !== val){
								$(val).removeClass('open');
						}
					});
					tab.addClass("open");
				}
		});

		$("article#room section article.add aside ul li span").on("click",function (e) {
				e.preventDefault();
				var tab = $(this).parent();


				if(tab.hasClass("open")){
					tab.removeClass("open");
				}else{
					$("article#room section article.add aside ul li").each(function (key,val) {
						if( tab !== val){
								$(val).removeClass('open');
						}
					});
					tab.addClass("open");
				}
		});


		$("header#head input#search").on("keyup",function () {
				var searchItem = $(this).val().toLowerCase();
				$("article#comics section.comics header h1").each(function (key,val) {
					if ($(val).text().toLowerCase().indexOf(searchItem) > -1) {
							$(val).parent().parent().fadeIn(1000);
					}else{
							$(val).parent().parent().fadeOut(1000);
					}
				});
		});


		$("input[type='file']").change(function(){
		    readURL(this);
		});

		$("#btn_clear").on("click",function	(e) {

			console.log("clear");
			clearForm($("#addNew"));


		});


		$("article#room header a.close").on("click",function	(e) {
			e.preventDefault();
			$("article#room").removeClass("open");
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

			if($("article#comics").hasClass("add")){
					$("article#comics").removeClass("add");
			}else {
				$("article#comics").addClass("add");
			}


		});


		loadRuimtes("en");

		/*$('form#addNew').on('submit',function (e) {

			e.preventDefault();

			var fd = new FormData(document.getElementById($(this)[0]));
			console.log($(fd).serialize());
            $.ajax({
              url: "http://10.3.210.104/Ruimtes/voegRuimteToe",
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


		$("article#comics section").scrollLeft(0);
		$("article#comics section.comics").each(function	(key,val) {
			if (!$(val).hasClass('upload') && !$(val).hasClass('edit')) {
				$(val).remove();
			}
		});

		$.get( "http://"+ip+"/Ruimtes/getAll?lang="+lang, function( data ) {

			$(data).each(function (key,val) {
				console.log(val);

				$("<section/>").addClass("comics").addClass("comics").html("<header><h1>"+val.name+"</h1><h2>"+val.comic+"</h2><a href='' class='edit'></a></header><nav><ul><li><a href='' id='"+val.id+"'><span class='icon enter'></span><span>Enter</span></a></li><li><a href=''  id='"+val.id+"'><span class='icon edit'></span><span>Edit</span></a></li><li><a href='' id='"+val.id+"'><span class='icon trash'></span><span>Delete</span></a></li></ul></nav>").appendTo("article#comics");


			});

			assignActions();
		});

	}


	function loadInfo (lang) {

		$("article#room section").scrollTop(0);


		$("article#room section article").each(function	(key,val) {
			if(!$(val).hasClass('add') && !$(val).hasClass('edit')){
				$(val).remove();
			}
		});

		$.get( "http://"+ip+"/Info/getAlleInfoPerLang?r_id="+id+"lang="+lang, function( data ) {

			$(data).each(function (key,val) {
				console.log(val);

				$("<article/>").addClass("comics").addClass("comics").html('<header><img src="images/info/3.jpg" alt="random"></header><aside><nav><span class="heart"></span><h1>234345</h1><a href class="edit" id="'+val.i_id+'"></a></nav><p>'+val.info+'</p></aside>').appendTo("article#room section");

			});


			$("article#room section article a.edit").on("click",function	(e) {
				e.preventDefault();
				console.log("EDIT");
				var edit = $(this);
				editid = edit.attr('id');

				if($("article#room section article.edit").hasClass('open')){
					$("article#room section article.edit").removeClass('open');
				}else {
					$("article#room section article.edit").addClass('open');

					$.get( "http://"+ip+"/Info/getInfoMetAlleTalen?i_id="+editid, function( data ) {
						console.log(data);

						$("article#room section article.edit #edit_info_name").val(data[0].name);

						$(data).each(function (key,val) {
								switch (val.lang.toLowerCase()) {
									case 'en':
									$("article#room section article.edit #edit_description_en").val(val.info);
										break;

									case 'nl':
									$("article#room section article.edit #edit_description_nl").val(val.info);
										break;

									case 'fr':
									$("article#room section article.edit #edit_description_fr").val(val.info);
										break;
									default:

								}
						});


					});



				}


			});

		});




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

		$("section.comics header a.edit").on('click',function (e) {

			e.preventDefault();

			console.log("CLICKED EDIT");

			var comic = $(this).parent().parent();
			var navigation = comic.find("nav");

			if(navigation.hasClass("open")){
				navigation.removeClass("open");
			}else{

			$("article#comics section.comics nav").each(function(key,val) {
				if(val !== navigation){
					$(val).removeClass("open");
				}
			});

				navigation.addClass("open");
			}


			navigation.find("a").on("click",function (e) {
				e.preventDefault();

				console.log($(this).find("span"));
				switch($(this).find("span").attr("class")){

					case "icon trash":

						console.log($(this).attr("id"));
						$.post( "http://"+ip+"/Ruimtes/verwijderRuimte",{id:$(this).attr("id")}, function( data ) {
						  console.log(data);
						});

					break;

					case "icon enter":
						$("article#room").addClass("open");
						id = $(this).attr("id");
						$("article#room header h1").text(comic.find('header h1').text()+' room');
						loadInfo("en");
						console.log(id);

					break;

					case "icon edit":

						id = $(this).attr("id");
						console.log(id);
						if ($("article#comics section.edit").hasClass("open")) {
							$("article#comics section.edit").removeClass("open");
						}else {
							$("article#comics section.edit").addClass("open");
						}

						$.get( "http://"+ip+"/Ruimtes/getInfoMetAlleTalen?r_id="+id, function( data ) {
							console.log(data);

							$("#edit_name").val(data[0].name);

							$(data).each(function (key,val) {
									switch (val.lang.toLowerCase()) {
										case 'en':
										$("#edit_comic_en").val(val.info);
											break;

										case 'nl':
										$("#edit_comic_nl").val(val.info);
											break;

										case 'fr':
										$("#edit_comic_fr").val(val.info);
											break;
										default:

									}
							});


						});

					break;

				}

			});

		});
	}

	function clearForm(form) {

		var textInput = form.find("input[type='text']");
		$(textInput).val('');

	}

	init();
});
