$(document).ready(function() {

//#################################### PAGINA LOGIN #########################################//
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


//#################################### PAGINA HOME #########################################//
if ($("body").find("div.entityPage.isis-domainapp-application-services-homepage-HomePageViewModel").length > 0){
    window.setInterval(function(){
    //Esto agarra tablas y: le aplica href a las filas, cambia el cursor, quita el boton mostrar como Tabla
    ConvierteTablaEnClickeableAplicaCursor()
    }, 250);
}//end IF Pagina Home


//#################################### PAGINA LISTAR AUTORIZACIONES #########################################//
if ($("body").find("div.isis-Autorizacion-listAll.isis-dominio-Autorizacion").length > 0){
    window.setInterval(function(){
    //Esto agarra tablas y: le aplica href a las filas, cambia el cursor, quita el boton mostrar como Tabla
    ConvierteTablaEnClickeableAplicaCursor()
    }, 250);
}//end IF Pagina Listar Autorizaciones

//#################################### PAGINA DETALLES AUTORIZACION #########################################//
	if ($("body").find("div.entityPage.isis-dominio-Autorizacion").length > 0){
	//Verifica si se encuentra en la pagina de Autorizacion detalles

//    ConvierteTablaEnClickeableRemueveBotonAplicaCursor()

    $("div.isis-dominio-Autorizacion-solicitanteEmpresa").find("a.entityUrlSource").contents().unwrap();
    //Esto le quita el atributo href al Solicitante Empresa

    $("div.isis-dominio-Autorizacion-solicitanteTrabajador").find("a.entityUrlSource").contents().unwrap();
    //Esto le quita el atributo href al Solicitante Trabajador

    $("div.isis-dominio-Autorizacion-solicitanteVehiculo").find("a.entityUrlSource").contents().unwrap();
    //Esto le quita el atributo href al Solicitante Vehiculo

    $("div.ejecutantes.domainapp-modules-simple-dominio-autorizacion-Ejecutante table thead").remove();
    //Esto borra el header de la tabla Empresas Ejecutantes

    $("div.ejecutantes.domainapp-modules-simple-dominio-autorizacion-Ejecutante table tbody").find('td').each(function() {
    if( $(this).find("a.entityUrlSource") ){
    $(this).find("a.entityUrlSource").contents().unwrap();
     }
    });
    //Esto le quita los href a los hipervinculos de la tabla Ejecutantes


    $("div.ejecutanteTrabajadores.domainapp-modules-simple-dominio-trabajador-Trabajador table").find('a').each(function() {
    $(this).contents().unwrap();
    });
    //Esto le quita los href a los hipervinculos de la tabla Trabajadores

    $("div.ejecutanteVehiculos.domainapp-modules-simple-dominio-vehiculo-Vehiculo table").find('a').each(function() {
    $(this).contents().unwrap();
    });
    //Esto le quita los href a los hipervinculos de la tabla Vehiculos



    $("div.property.isis-dominio-Autorizacion-ejecutanteEmpresa").find("a.entityUrlSource").contents().unwrap();
    //Esto le quita el atributo href a la empresa ejecutante seleccionada


    $("div.panel-heading").find("div.additionalLinksAndSelectorDropDown.pull-right").remove();
    //Esto remueve el boton "Mostrar como tabla" que aparece en el header de las tablas


    /* etiqueta para ocultar columnas en las tablas de detalles de autorizacion*/
    $('.isis-dominio-Trabajador-fechaNacimiento').css('display', 'none');
    $('.isis-dominio-Trabajador-empresa').css('display', 'none');
    $('.isis-dominio-Trabajador-estado').css('display', 'none');

    $('.isis-dominio-Vehiculo-empresa').css('display', 'none');
    $('.isis-dominio-Vehiculo-fechaAlta').css('display', 'none');
    $('.isis-dominio-Vehiculo-estado').css('display', 'none');
	}//end IF Pagina Autorizacion


//#################################### PAGINA LISTA EMPRESAS #########################################//
    if ($("body").find("div.isis-Empresa-listAll.isis-dominio-Empresa").length > 0){
    window.setInterval(function(){
    //Esto agarra tablas y: le aplica href a las filas, cambia el cursor, quita el boton mostrar como Tabla
    ConvierteTablaEnClickeableAplicaCursor();
    }, 250);
    }//end if Pagina Lista Empresas


//#################################### PAGINA DETALLES EMPRESA #########################################//
    if ($("body").find("div.entityPage.isis-dominio-Empresa").length > 0){
    window.setInterval(function(){
    $("div.trabajadores.domainapp-modules-simple-dominio-trabajador-Trabajador").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo a la empresa del Trabajador

    QuitarBotonMostrarEnTabla()

    $("div.trabajadores.domainapp-modules-simple-dominio-trabajador-Trabajador").find("div.isis-dominio-Trabajador-empresa").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo de la empresa de la tabla Trabajadores que esta dentro de detalles-empresa

    $("div.vehiculos.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("a.entityUrlSource").contents().unwrap();
    //Esto quita los hipervinculos de la tabla Vehiculos que esta dentro de detalles-empresa
    }, 250);
    }//end if Pagina Detalles Empresa


//#################################### PAGINA LISTA TRABAJADORES #########################################//
    if ($("body").find("div.isis-Trabajador-listAll.isis-dominio-Trabajador").length > 0){
    window.setInterval(function(){
    //Esto agarra tablas y: le aplica href a las filas, cambia el cursor, quita el boton mostrar como Tabla
    ConvierteTablaEnClickeableAplicaCursor()

    $("div.isis-Trabajador-listAll.isis-dominio-Trabajador").find("div.isis-dominio-Trabajador-empresa").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo de la empresa de la tabla Trabajadores

    }, 250);
    //Esto hace que cada segundo aplique los cambios por si cambia de pagina

    }//end if Pagina Lista Trabajadores


//#################################### PAGINA DETALLE TRABAJADOR #########################################//
    if ($("body").find("div.entityPage.isis-dominio-Trabajador").length > 0){
    window.setInterval(function(){

    $("div.property.isis-dominio-Trabajador-empresa").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo de la empresa de la tabla Trabajadores

    QuitarBotonMostrarEnTabla()

    $("div.ejecucion.domainapp-modules-simple-dominio-trabajador-Trabajador").find("a.entityUrlSource").contents().unwrap();
    //Esto quita los hipervinculos de la tabla Ejecucion que esta dentro de detalles-trabajador

    $("div.habilitado.domainapp-modules-simple-dominio-trabajador-Trabajador").find("a.entityUrlSource").contents().unwrap();
    //Esto quita los hipervinculos de la tabla Habilitado que esta dentro de detalles-trabajador

    $("div.inhabilitado.domainapp-modules-simple-dominio-trabajador-Trabajador").find("a.entityUrlSource").contents().unwrap();
    //Esto quita los hipervinculos de la tabla Inhabilitado que esta dentro de detalles-trabajador

    $("div.borrado.domainapp-modules-simple-dominio-trabajador-Trabajador").find("a.entityUrlSource").contents().unwrap();
    //Esto quita los hipervinculos de la tabla Borrado que esta dentro de detalles-trabajador

    }, 250);
    }//end if Pagina Detalle Trabajador


//#################################### PAGINA LISTA VEHICULOS #########################################//
    if ($("body").find("div.isis-Vehiculo-listAll.isis-dominio-Vehiculo").length > 0){
    window.setInterval(function(){
    //Esto agarra tablas y: le aplica href a las filas, cambia el cursor, quita el boton mostrar como Tabla
    ConvierteTablaEnClickeableAplicaCursor()

    $("div.isis-Vehiculo-listAll.isis-dominio-Vehiculo").find("div.isis-dominio-Vehiculo-modelo").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo del modelo de la tabla Vehiculos

    $("div.isis-Vehiculo-listAll.isis-dominio-Vehiculo").find("div.isis-dominio-Vehiculo-empresa").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo de la empresa de la tabla Vehiculo

    }, 250);
    //Esto hace que cada segundo aplique los cambios por si cambia de pagina

    }//end if Pagina Lista Vehiculos


//#################################### PAGINA DETALLE VEHICULO #########################################//
    if ($("body").find("div.entityPage.isis-dominio-Vehiculo").length > 0){
    window.setInterval(function(){

    $("div.property.isis-dominio-Vehiculo-modelo").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo del modelo de la tabla Vehiculo

    $("div.property.isis-dominio-Vehiculo-empresa").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo de la empresa de la tabla Vehiculo

    QuitarBotonMostrarEnTabla();
    TraduceBotonShowAll();

    $("div.ejecucion.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("a.entityUrlSource").contents().unwrap();
    //Esto quita los hipervinculos de la tabla Ejecucion que esta dentro de detalles-vehiculo

    $("div.habilitado.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("a.entityUrlSource").contents().unwrap();
    //Esto quita los hipervinculos de la tabla Habilitado que esta dentro de detalles-vehiculo

    $("div.inhabilitado.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("a.entityUrlSource").contents().unwrap();
    //Esto quita los hipervinculos de la tabla Inhabilitado que esta dentro de detalles-vehiculo

    $("div.borrado.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("a.entityUrlSource").contents().unwrap();
    //Esto quita los hipervinculos de la tabla Borrado que esta dentro de detalles-vehiculo

    }, 250);
    }//end if Pagina Detalle Vehiculo


//#################################### PAGINA LISTAR MARCAS #########################################//
    if ($("body").find("div.isis-Marca-listAll.isis-dominio-Marca").length > 0){
        window.setInterval(function(){
        //Esto agarra tablas y: le aplica href a las filas, cambia el cursor, quita el boton mostrar como Tabla
        ConvierteTablaEnClickeableAplicaCursor();
        }, 250);
    }//end IF Pagina Listar Marcas


//#################################### PAGINA DETALLE MARCA #########################################//
    if ($("body").find("div.entityPage.isis-dominio-Marca").length > 0){
    window.setInterval(function(){

    $("div.modelos.domainapp-modules-simple-dominio-vehiculo-adicional-Modelo").find("div.isis-dominio-Modelo-marca").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo del modelo de la tabla Vehiculo

    ConvierteTablaEnClickeableAplicaCursor();

    QuitarBotonMostrarEnTabla();
    TraduceBotonShowAll();

    }, 250);
    }//end if Pagina Detalle Vehiculo


//#################################### PAGINA LISTAR MODELOS #########################################//
    if ($("body").find("div.isis-Modelo-listAll.isis-dominio-Modelo").length > 0){
        window.setInterval(function(){

        $("div.isis-Modelo-listAll.isis-dominio-Modelo").find("div.isis-dominio-Modelo-marca").find("a.entityUrlSource").contents().unwrap();
        //Esto quita el hipervinculo de la marca de la tabla Activas

        //Esto agarra tablas y: le aplica href a las filas, cambia el cursor, quita el boton mostrar como Tabla
        ConvierteTablaEnClickeableAplicaCursor();
        }, 250);
    }//end IF Pagina Listar Modelos


//#################################### PAGINA DETALLE MODELO #########################################//
    if ($("body").find("div.entityPage.isis-dominio-Modelo").length > 0){
    window.setInterval(function(){

    $("div.property.isis-dominio-Modelo-marca").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo del modelo de la tabla Modelo

    $("div.activas.domainapp-modules-simple-dominio-vehiculo-adicional-Modelo").find("div.isis-dominio-Modelo-marca").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo de la marca de la tabla Activas

    $("div.inactivas.domainapp-modules-simple-dominio-vehiculo-adicional-Modelo").find("div.isis-dominio-Modelo-marca").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo de la marca de la tabla Activas

    ConvierteTablaEnClickeableAplicaCursor();

    QuitarBotonMostrarEnTabla();
    TraduceBotonShowAll();

    }, 250);
    }//end if Pagina Detalle Vehiculo

//#################################### APLICA PARA TODAS LAS PAGINAS #########################################//
    if ($("body").find("div.entityPage.isis-domainapp-application-services-homepage-HomePageViewModel").length > 0){
    //Esto oculta el titulo de la pagina principal
    $('h4.iconAndTitle').css('display','none');
    }else{
    $("h4.iconAndTitle").find("a.entityUrlSource").contents().unwrap();
    //Esto le quita el atributo href al titulo de cada pagina
    }


    //Esto oculta el footer
    $("footer").remove();


    function ConvierteTablaEnClickeableAplicaCursor(){
    //Esto agarra tablas y le convierte cada fila en clickeable con el atributo href del icono
//    $("table tr:not(:first-child) td:not(:first-child)").on('click', 'tr', function (){
    $("table tbody").on('click', 'tr', function (){
        if( !$(this).hasClass("norecords-tr") && !$(this).hasClass("navigation")){
        window.location.href = $(this).find('td:eq(0)').find("a").attr("href");
        }
    });

    //Esto agarra tablas y le agrega estilo de manito hipervinculo a cada fila SINO mouse comun -no tipo texto-
    $("table tbody").find('td').each(function() {
    if( !$(this).hasClass("norecords-td")  && !$(this).hasClass("navigation") ){
     $(this).css('cursor','pointer')
     }else{
     $(this).css('cursor','default')
     }
    });

    TraduceBotonShowAll();

    }//end function ConvierteTablaEnClickeable()


    function QuitarBotonMostrarEnTabla(){
        $("div.panel-heading").find("div.additionalLinksAndSelectorDropDown.pull-right").remove();
        //Esto remueve el boton "Mostrar como tabla" que aparece en el header de las tablas
    }

    function TraduceBotonShowAll(){
        //Esto traduce el boton Show All que se encuentra en tablas con paginas
        $( "button:contains('Show all')" ).text( "Mostrar Todos" );
    }

	
//	//Verifica si se encuentra en la pagina de Autorizacion
//	if ($("body").find(".isis-dominio-Autorizacion").length > 0){
//
//	//verifica cada medio segundo si existe el div del datepicker para aplicarle css y ajustarlo a pantalla
//	window.setInterval(function(){
//		if ( $('div.datepicker.col-sm-6').length ){
//			console.log("Existe el div datepicker");
//			$('div.datepicker.col-sm-6').removeClass('col-sm-6').addClass('col-sm-5').addClass('col-md-offset-2');
//			$('div.timepicker.col-sm-6').removeClass('col-sm-6').addClass('col-sm-5').addClass('col-md-offset-2');
//			$('div.bootstrap-datetimepicker-widget').css('left','-90px');
//		}else{
//			console.log("No existe el div datepicker");
//		}
//
//	  }, 500);
//	}// end Pagina Autorizacion

}); //end document.ready function