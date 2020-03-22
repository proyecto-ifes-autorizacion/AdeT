$(document).ready(function() {

	if ($("body").find(".accountManagementPanel").length > 0){ 
	//Verifica si se encuentra en la pagina de Login

		$(this).attr("title", "AdeT - Iniciar Sesión");
		//Esto cambia el titulo de la pagina

		$('body').css('background-color', 'grey');
		$('body').css('background-image', 'url("/about/images/background.jpg")');
		$('body').css('color', '#ffffff');
		$('body').css('position', 'relative');
		$('body').css('height', 'calc(100vh)');
		$('body').css('background-size', 'cover');
		$('body').css('background-repeat', 'no-repeat');
		$('body').css('background-position', 'center center');
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

	}else{
        $('body').css('background-color', 'white');
        $('body').css('background-image', 'url("/about/images/background-grey.png")');
        $('body').css('background-repeat', 'repeat-y');
        $('body').css('color', 'black');
        $('body').css('position', 'relative');
        $('body').css('height', 'calc(100vh)');
        $('body').css('background-size', 'cover');
        $('body').css('background-position', 'center center');
	} //end IF Pagina Login

	if ($("body").find("div.entityPage.isis-dominio-Autorizacion").length > 0){
	//Verifica si se encuentra en la pagina de Autorizacion detalles

	/* etiqueta para ocultar columnas en las tablas de detalles de autorizacion*/
	$('.isis-dominio-Trabajador-fechaNacimiento').css('display', 'none');
	$('.isis-dominio-Trabajador-empresa').css('display', 'none');
	$('.isis-dominio-Trabajador-estado').css('display', 'none');

    $('.isis-dominio-Vehiculo-empresa').css('display', 'none');
    $('.isis-dominio-Vehiculo-fechaAlta').css('display', 'none');
    $('.isis-dominio-Vehiculo-estado').css('display', 'none');
	}//end IF Pagina Autorizacion

    //###########################################################//
    //Esto aplica en todas las paginas excepto Login
    $("footer").remove();
    //Esto oculta el footer que no se pudo ocultar de otra manera

    //Esto oculta el titulo de la pagina principal
    $('h4.iconAndTitle').css('display','none');

    //Esto traduce el boton Show All que se encuentra en tablas con paginas
    $( "button:contains('Show all')" ).text( "Mostrar Todos" );


    //Esto agarra tablas y le convierte cada fila en clickeable con el atributo href del icono
    $("table").on('click', 'tr', function ()
    {
    if( !$(this).hasClass("norecords-tr") && !$(this).hasClass("navigation")){
    window.location.href = $(this).find('td:eq(0)').find("a").attr("href");
    }
    }
    );

    //Esto agarra tablas y le agrega estilo de manito hipervinculo a cada fila SINO mouse comun -no tipo texto-
    $("table").find('td').each(function() {
    if( !$(this).hasClass("norecords-td")  && !$(this).hasClass("navigation") ){
     $(this).css('cursor','pointer')
     }else{
     $(this).css('cursor','default')
     }
    });

	
	//Verifica si se encuentra en la pagina de Autorizacion
	if ($("body").find(".isis-dominio-Autorizacion").length > 0){ 
	
	//verifica cada medio segundo si existe el div del datepicker para aplicarle css y ajustarlo a pantalla
	window.setInterval(function(){
//		if ( $('div.datepicker.col-sm-6').length ){
//			console.log("Existe el div datepicker");
//			$('div.datepicker.col-sm-6').removeClass('col-sm-6').addClass('col-sm-5').addClass('col-md-offset-2');
//			$('div.timepicker.col-sm-6').removeClass('col-sm-6').addClass('col-sm-5').addClass('col-md-offset-2');
//			$('div.bootstrap-datetimepicker-widget').css('left','-90px');
//		}else{
//			console.log("No existe el div datepicker");
//		}

	  }, 500);
	}// end Pagina Autorizacion

}); //end document.ready function