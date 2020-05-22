$(document).ready(function() {

//#################################### PAGINA LOGIN #########################################//
	if ($("body").find(".accountManagementPanel").length > 0){ 
	//Verifica si se encuentra en la pagina de Login

    $(this).attr("title", "AdeT - Iniciar Sesión");
    //Esto cambia el titulo de la pagina

    $('body').css('background-color', '#5a5a66');
    $('body').css('background-image', 'url("/images/FondoInicio.jpg")');
    $('body').css('color', '#ffffff');
    $('body').css('position', 'relative');
    $('body').css('height', 'calc(100vh)');
    $('body').css('background-size', 'cover');
    $('body').css('background-repeat', 'no-repeat');
    $('body').css('background-position', 'center center');
    //Esto aplica color de letra, color de fondo e imagen de fondo

    $('h2').css('margin-top', '-20px');
    //Esto ajusta un poco mas arriba el titulo Iniciar Sesion

    $("form:not(.filter) :input:visible:enabled").eq(0).attr("placeholder", "Ingrese nombre de usuario");
    $("form:not(.filter) :input:visible:enabled").eq(1).attr("placeholder", "Ingrese contraseña");
    //Esto cambia el placeholder que no se pudo traducir

    //$("form:not(.filter) :input:visible:enabled").eq(0).attr("value", "admin");
    //$("form:not(.filter) :input:visible:enabled").eq(1).attr("value", "admin");
    //$("button.btn[type=submit]").click();
    //Esto sirve para autocompletar los campos del Login y apreta Ingresar automaticamente

    $("img[src$='/images/Logo-login.png']").wrap("<a href='/'> </a>");
    //Esto aplica un HREF a la imagen del logo

    $("button.btn[type=submit]").removeClass("btn-primary").addClass("btn-info");
    //Esto cambia el css del boton Ingresar para que sea del color deseado

    $("button.btn[type=reset]").hide();
    //Esto oculta el boton blanquear del formulario

	}else{
    $('body').css('background-color', 'white');
    $('body').css('background-image', 'url("/images/FondoGris.png")');
    $('body').css('background-repeat', 'repeat-y');
    $('body').css('color', 'black');
    $('body').css('position', 'relative');
    $('body').css('height', 'calc(100vh)');
    $('body').css('background-size', 'cover');
    $('body').css('background-position', 'center center');
	} //end IF Pagina Login


//#################################### PAGINA HOME #########################################//
    if ($("body").find("div.entityPage.isis-domainapp-application-services-homepage-HomePageViewModel").length > 0){

    //$('h4.iconAndTitle').css('display','none');
    //Esto oculta el titulo de la pagina principal

    window.setInterval(function(){
    ConvierteTablaEnClickeableAplicaCursor();
    //Esto agarra tablas y le aplica href a las filas, cambia el cursor, quita el boton mostrar como Tabla

    QuitarBotonMostrarEnTabla();
    }, 200);
    }//end IF Pagina Home


//#################################### PAGINA LISTAR AUTORIZACIONES #########################################//
    if ($("body").find("div.isis-Autorizacion-listAll.isis-dominio-Autorizacion").length > 0){
    window.setInterval(function(){
    ConvierteTablaEnClickeableAplicaCursor()
    //Esto agarra tablas y: le aplica href a las filas, cambia el cursor, quita el boton mostrar como Tabla
    }, 200);
    }//end IF Pagina Listar Autorizaciones

//#################################### PAGINA DETALLES AUTORIZACION #########################################//
	if ($("body").find("div.entityPage.isis-dominio-Autorizacion").length > 0){

    counterFocus = 0;

	window.setInterval(function(){
	if(counterFocus == 0){
	//alert($("div.property.isis-dominio-Autorizacion-titulo a.scalarValueInlinePromptLink.form-control.input-sm").is( ":focus" ))
	//$("div.property.isis-dominio-Autorizacion-ubicacion a.scalarValueInlinePromptLink.form-control.input-sm").focus();
	$("div.property.isis-dominio-Autorizacion-titulo a.scalarValueInlinePromptLink.form-control.input-sm").blur();
	counterFocus = 1;
	}

    QuitarBotonMostrarEnTabla();

    $("h4.iconAndTitle").find("a.entityUrlSource").contents().unwrap();
    //Esto le quita el href al titulo

    $("div.isis-dominio-Autorizacion-solicitanteEmpresa").find("a.entityUrlSource").contents().unwrap();
    //Esto le quita el atributo href al Solicitante Empresa

    $("div.isis-dominio-Autorizacion-solicitanteTrabajador").find("a.entityUrlSource").contents().unwrap();
    //Esto le quita el atributo href al Solicitante Trabajador

    $("div.isis-dominio-Autorizacion-solicitanteVehiculo").find("a.entityUrlSource").contents().unwrap();
    //Esto le quita el atributo href al Solicitante Vehiculo


    $("div.ejecutantes.domainapp-modules-simple-dominio-autorizacion-Ejecutante table thead").remove();
    //Esto borra el header de la tabla Empresas Ejecutantes

    $("div.property.isis-dominio-Autorizacion-ejecutanteEmpresa").find("a.entityUrlSource").contents().unwrap();
    //Esto le quita el atributo href a la empresa ejecutante seleccionada

    $("div.ejecutantes.domainapp-modules-simple-dominio-autorizacion-Ejecutante table").find("a.entityUrlSource").contents().unwrap();
    //Esto le quita los href a los hipervinculos de la tabla Ejecutantes

    $("div.ejecutanteTrabajadores.domainapp-modules-simple-dominio-trabajador-Trabajador table").find("a.entityUrlSource").contents().unwrap();
    //Esto le quita los href a los hipervinculos de la tabla Trabajadores

    $("div.ejecutanteVehiculos.domainapp-modules-simple-dominio-vehiculo-Vehiculo table").find("a.entityUrlSource").contents().unwrap();
    //Esto le quita los href a los hipervinculos de la tabla Vehiculos


    /* etiqueta para ocultar columnas en las tablas de detalles de autorizacion*/
    $('.isis-dominio-Trabajador-fechaNacimiento').css('display', 'none');
    $('.isis-dominio-Trabajador-empresa').css('display', 'none');
    $('.isis-dominio-Trabajador-estado').css('display', 'none');

    $('.isis-dominio-Vehiculo-empresa').css('display', 'none');
    $('.isis-dominio-Vehiculo-fechaAlta').css('display', 'none');
    $('.isis-dominio-Vehiculo-estado').css('display', 'none');


    $("span.editing:contains('(none)')").each(function(index) {
        //console.log(index)
        if( $(this).find("span.autoCompletePlaceholder span.entityIconAndTitlePanel.entityIconAndTitleComponentType" ).has( "img" ).length){

        console.log("El elemento (none) esta acompañado asique lo borra.")
        $(this).html($(this).html().replace('(none)',''));
        }else{
        console.log("El elemento (none) esta solo asique lo traduce.")
        $(this).html($(this).html().replace('(none)','(Aún sin seleccionar)'));
        }
    //console.log("aplicado")
    });
    //Este busca y traduce la palabra (none) cuando algo aun no esta seleccionado

    }, 200);
	}//end IF Pagina Autorizacion


//#################################### PAGINA LISTA EMPRESAS #########################################//
    if ($("body").find("div.isis-Empresa-listAll.isis-dominio-Empresa").length > 0){
    console.log("Esta en pagina Listar-Empresas");
    window.setInterval(function(){
    //Esto agarra tablas y le aplica href a las filas, cambia el cursor, quita el boton mostrar como Tabla
    ConvierteTablaEnClickeableAplicaCursor();
    }, 200);
    }//end if Pagina Lista Empresas


//#################################### PAGINA DETALLES EMPRESA #########################################//
    if ($("body").find("div.entityPage.isis-dominio-Empresa").length > 0){
    console.log("Esta en pagina Detalles-Empresa");
    window.setInterval(function(){

    QuitarBotonMostrarEnTabla();

    $("div.trabajadores.domainapp-modules-simple-dominio-trabajador-Trabajador").find("div.isis-dominio-Trabajador-empresa").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo de la empresa de la tabla Trabajadores

    ConvierteTablaEnClickeableAplicaCursor($("div.trabajadores.domainapp-modules-simple-dominio-trabajador-Trabajador").find("table"))


    $("div.vehiculos.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("div.isis-dominio-Vehiculo-empresa").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo de la empresa de la tabla Vehiculos

    $("div.vehiculos.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("div.isis-dominio-Vehiculo-modelo").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo del modelo de la tabla Vehiculos

    ConvierteTablaEnClickeableAplicaCursor($("div.vehiculos.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("table"))

    ConvierteTablaEnClickeableAplicaCursor($("div.habilitada.domainapp-modules-simple-dominio-empresa-Empresa").find("table"))

    ConvierteTablaEnClickeableAplicaCursor($("div.inhabilitada.domainapp-modules-simple-dominio-empresa-Empresa").find("table"))

    ConvierteTablaEnClickeableAplicaCursor($("div.borrada.domainapp-modules-simple-dominio-empresa-Empresa").find("table"))

    }, 200);
    }//end if Pagina Detalles Empresa


//#################################### PAGINA LISTA TRABAJADORES #########################################//
    if ($("body").find("div.isis-Trabajador-listAll.isis-dominio-Trabajador").length > 0){
    window.setInterval(function(){

    ConvierteTablaEnClickeableAplicaCursor()
    //Esto agarra tablas y le aplica href a las filas, cambia el cursor, quita el boton mostrar como Tabla

    $("div.isis-Trabajador-listAll.isis-dominio-Trabajador").find("div.isis-dominio-Trabajador-empresa").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo de la empresa de la tabla Trabajadores

    }, 200);
    //Esto hace que cada segundo aplique los cambios por si cambia de pagina

    }//end if Pagina Lista Trabajadores


//#################################### PAGINA DETALLE TRABAJADOR #########################################//
    if ($("body").find("div.entityPage.isis-dominio-Trabajador").length > 0){
    window.setInterval(function(){

    QuitarBotonMostrarEnTabla();

    //$("div.property.isis-dominio-Trabajador-empresa").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo de la empresa en los detalles del Trabajador


    $("div.ejecucion.domainapp-modules-simple-dominio-trabajador-Trabajador").find("div.isis-dominio-Trabajador-empresa").find("a.entityUrlSource").contents().unwrap();
    //Esto quita los hipervinculos de la tabla Ejecucion que esta dentro de detalles-trabajador

    $("div.habilitado.domainapp-modules-simple-dominio-trabajador-Trabajador").find("div.isis-dominio-Trabajador-empresa").find("a.entityUrlSource").contents().unwrap();
    //Esto quita los hipervinculos de la tabla Habilitado que esta dentro de detalles-trabajador

    $("div.inhabilitado.domainapp-modules-simple-dominio-trabajador-Trabajador").find("div.isis-dominio-Trabajador-empresa").find("a.entityUrlSource").contents().unwrap();
    //Esto quita los hipervinculos de la tabla Inhabilitado que esta dentro de detalles-trabajador

    $("div.borrado.domainapp-modules-simple-dominio-trabajador-Trabajador").find("div.isis-dominio-Trabajador-empresa").find("a.entityUrlSource").contents().unwrap();
    //Esto quita los hipervinculos de la tabla Borrado que esta dentro de detalles-trabajador

    ConvierteTablaEnClickeableAplicaCursor($("div.ejecucion.domainapp-modules-simple-dominio-trabajador-Trabajador").find("table"))

    ConvierteTablaEnClickeableAplicaCursor($("div.habilitado.domainapp-modules-simple-dominio-trabajador-Trabajador").find("table"))

    ConvierteTablaEnClickeableAplicaCursor($("div.inhabilitado.domainapp-modules-simple-dominio-trabajador-Trabajador").find("table"))

    ConvierteTablaEnClickeableAplicaCursor($("div.borrado.domainapp-modules-simple-dominio-trabajador-Trabajador").find("table"))
    }, 200);
    }//end if Pagina Detalle Trabajador


//#################################### PAGINA LISTA VEHICULOS #########################################//
    if ($("body").find("div.isis-Vehiculo-listAll.isis-dominio-Vehiculo").length > 0){
    window.setInterval(function(){

    $("div.isis-Vehiculo-listAll.isis-dominio-Vehiculo").find("div.isis-dominio-Vehiculo-modelo").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo del modelo de la tabla Vehiculos

    $("div.isis-Vehiculo-listAll.isis-dominio-Vehiculo").find("div.isis-dominio-Vehiculo-empresa").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo de la empresa de la tabla Vehiculo

    ConvierteTablaEnClickeableAplicaCursor()
    //Esto agarra tablas y le aplica href a las filas, cambia el cursor, quita el boton mostrar como Tabla

    }, 200);
    //Esto hace que cada segundo aplique los cambios por si cambia de pagina

    }//end if Pagina Lista Vehiculos


//#################################### PAGINA DETALLE VEHICULO #########################################//
    if ($("body").find("div.entityPage.isis-dominio-Vehiculo").length > 0){
    window.setInterval(function(){

    QuitarBotonMostrarEnTabla();

    //$("div.property.isis-dominio-Vehiculo-empresa").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo de la empresa de los detalles del Vehiculo

    $("div.property.isis-dominio-Vehiculo-modelo").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo del modelo de los detalles del Vehiculo


    $("div.ejecucion.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("div.isis-dominio-Vehiculo-empresa").find("a.entityUrlSource").contents().unwrap();
    $("div.ejecucion.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("div.isis-dominio-Vehiculo-modelo").find("a.entityUrlSource").contents().unwrap();
    //Esto quita los hipervinculos Empresa y Modelo de la tabla Ejecucion

    $("div.habilitado.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("div.isis-dominio-Vehiculo-empresa").find("a.entityUrlSource").contents().unwrap();
    $("div.habilitado.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("div.isis-dominio-Vehiculo-modelo").find("a.entityUrlSource").contents().unwrap();
    //Esto quita los hipervinculos Empresa y Modelo de la tabla Habilitados

    $("div.inhabilitado.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("div.isis-dominio-Vehiculo-empresa").find("a.entityUrlSource").contents().unwrap();
    $("div.inhabilitado.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("div.isis-dominio-Vehiculo-modelo").find("a.entityUrlSource").contents().unwrap();
    //Esto quita los hipervinculos Empresa y Modelo de la tabla Inhabilitados

    $("div.borrado.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("div.isis-dominio-Vehiculo-empresa").find("a.entityUrlSource").contents().unwrap();
    $("div.borrado.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("div.isis-dominio-Vehiculo-modelo").find("a.entityUrlSource").contents().unwrap();
    //Esto quita los hipervinculos Empresa y Modelo de la tabla Borrados

    ConvierteTablaEnClickeableAplicaCursor($("div.ejecucion.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("table"))

    ConvierteTablaEnClickeableAplicaCursor($("div.habilitado.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("table"))

    ConvierteTablaEnClickeableAplicaCursor($("div.inhabilitado.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("table"))

    ConvierteTablaEnClickeableAplicaCursor($("div.borrado.domainapp-modules-simple-dominio-vehiculo-Vehiculo").find("table"))

    }, 200);
    }//end if Pagina Detalle Vehiculo


//#################################### PAGINA LISTAR MARCAS #########################################//
    if ($("body").find("div.isis-Marca-listAll.isis-dominio-Marca").length > 0){
    window.setInterval(function(){

    ConvierteTablaEnClickeableAplicaCursor();
    //Esto agarra tablas y le aplica href a las filas, cambia el cursor, quita el boton mostrar como Tabla

    }, 200);
    }//end IF Pagina Listar Marcas


//#################################### PAGINA DETALLE MARCA #########################################//
    if ($("body").find("div.entityPage.isis-dominio-Marca").length > 0){
    window.setInterval(function(){

    QuitarBotonMostrarEnTabla();

    $("div.modelos.domainapp-modules-simple-dominio-vehiculo-adicional-Modelo").find("div.isis-dominio-Modelo-marca").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo de la Marca de la tabla Modelos

    $("div.modelos.domainapp-modules-simple-dominio-vehiculo-adicional-Modelo").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo del icono de la tabla Modelos

    ConvierteTablaEnClickeableAplicaCursor($("div.activas.domainapp-modules-simple-dominio-vehiculo-adicional-Marca").find("table"))

    ConvierteTablaEnClickeableAplicaCursor($("div.inactivas.domainapp-modules-simple-dominio-vehiculo-adicional-Marca").find("table"))

    }, 200);

    }//end if Pagina Detalle Vehiculo


//#################################### PAGINA LISTAR MODELOS #########################################//
    if ($("body").find("div.isis-Modelo-listAll.isis-dominio-Modelo").length > 0){
    window.setInterval(function(){

    $("div.isis-Modelo-listAll.isis-dominio-Modelo").find("div.isis-dominio-Modelo-marca").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo de la marca

    ConvierteTablaEnClickeableAplicaCursor();
    //Esto agarra tablas y le aplica href a las filas, cambia el cursor, quita el boton mostrar como Tabla

    }, 200);
    }//end IF Pagina Listar Modelos


//#################################### PAGINA DETALLE MODELO #########################################//
    if ($("body").find("div.entityPage.isis-dominio-Modelo").length > 0){
    window.setInterval(function(){

    QuitarBotonMostrarEnTabla();

    $("div.property.isis-dominio-Modelo-marca").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo de la Marca en los detalles del Modelo

    $("div.activas.domainapp-modules-simple-dominio-vehiculo-adicional-Modelo").find("div.isis-dominio-Modelo-marca").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo de la marca de la tabla Modelos Activos

    $("div.inactivas.domainapp-modules-simple-dominio-vehiculo-adicional-Modelo").find("div.isis-dominio-Modelo-marca").find("a.entityUrlSource").contents().unwrap();
    //Esto quita el hipervinculo de la marca de la tabla Modelos Inactivos

    ConvierteTablaEnClickeableAplicaCursor();

    }, 200);
    }//end if Pagina Detalle Vehiculo

//#################################### APLICA PARA TODAS LAS PAGINAS #########################################//

    //}else{

    $('head').append('<link href="/myico.ico" rel="shortcut icon" type="image/x-icon" />');
    $('head').append('<link rel="icon" href="/myico.ico" type="image/x-icon">');
    //Esto agrega icono a la pagina cuando se guarda en Favoritos

    $("h4.iconAndTitle").find("a.entityUrlSource").contents().unwrap();
    //Esto le quita el atributo href al titulo de cada pagina
    //}


    //Esto oculta el footer
    $("footer").remove();


    function ConvierteTablaEnClickeableAplicaCursor(element = "table"){
    //Esto agarra tablas y le convierte cada fila en clickeable con el atributo href del icono
    //$("table tr:not(:first-child) td:not(:first-child)").on('click', 'tr', function (){
    //$("table tbody").on('click', 'tr', function (){
    $(element).find("tbody").on('click', 'tr', function (){
        if( !$(this).hasClass("norecords-tr") && !$(this).hasClass("navigation")){
        window.location.href = $(this).find('td:eq(0)').find("a").attr("href");
        }
    });

    //Esto agarra tablas y le agrega estilo de manito hipervinculo a cada fila SINO mouse comun -no tipo texto-
    //$("table tbody").find('td').each(function() {
    $(element).find("tbody td").each(function() {
    if( !$(this).hasClass("norecords-td")  && !$(this).hasClass("navigation") ){
     $(this).css('cursor','pointer')
     }else{
     $(this).css('cursor','default')
     }
    });

    TraduceBotonShowAll();

    }//end function ConvierteTablaEnClickeable()


    function QuitarBotonMostrarEnTabla(){
        $("div.panel-heading div.additionalLinksAndSelectorDropDown.pull-right").find("div.linksSelectorPanel").remove();
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