$(document).ready(function() {

	if ($("body").find(".accountManagementPanel").length > 0){ 
	//Verifica si se encuentra en la pagina de Login

		$(this).attr("title", "AdeT - Iniciar Sesión");
		//Esto cambia el titulo de la pagina

		$('body').css('background-color', 'grey');
		$('body').css('background-image', 'url("/about/images/background.jpg")');
		$('body').css('color', '#ffffff');
		//Esto aplica color de letra, color de fondo e imagen de fondo

		$("form:not(.filter) :input:visible:enabled").eq(0).attr("placeholder", "Ingrese nombre de usuario");
		$("form:not(.filter) :input:visible:enabled").eq(1).attr("placeholder", "Ingrese contraseña");
		//Esto cambia el placeholder que no se pudo traducir
		
		$("form:not(.filter) :input:visible:enabled").eq(0).attr("value", "admin");
		$("form:not(.filter) :input:visible:enabled").eq(1).attr("value", "admin");
		$("button.btn[type=submit]").click();
		//Esto sirve para autocompletar los campos del Login y apreta Ingresar automaticamente

		$("img[src$='/about/images/Logo-login.png']").wrap("<a href='/'> </a>");
		//Esto aplica un HREF a la imagen del logo

		$("button.btn[type=submit]").removeClass("btn-primary").addClass("btn-info");
		//Esto cambia el css del boton Ingresar para que sea del color deseado

		$("button.btn[type=reset]").hide();
		//Esto oculta el boton blanquear del formulario

	} //end Pagina Login


	  //Esto aplica en todas las paginas excepto Login
	  $("footer").remove();
	  //Esto oculta el footer que no se pudo ocultar de otra manera

	
	//Verifica si se encuentra en la pagina de Autorizacion
	if ($("body").find(".isis-dominio-Autorizacion").length > 0){ 
	
	//verifica cada medio segundo si existe el div del datepicker para aplicarle css
	window.setInterval(function(){
		if ( $('div.datepicker.col-sm-6').length ){
			console.log("Existe el div datepicker");
			$('div.datepicker.col-sm-6').removeClass('col-sm-6').addClass('col-sm-5').addClass('col-md-offset-2');
			$('div.timepicker.col-sm-6').removeClass('col-sm-6').addClass('col-sm-5').addClass('col-md-offset-2');
			$('div.bootstrap-datetimepicker-widget').css('left','-90px');
		}else{
			console.log("No existe el div datepicker");
		}

	  }, 500);
	}// end Pagina Autorizacion

}); //end document.ready function