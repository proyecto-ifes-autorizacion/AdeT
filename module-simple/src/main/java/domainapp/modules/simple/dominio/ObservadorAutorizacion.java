package domainapp.modules.simple.dominio;

import domainapp.modules.simple.dominio.autorizacion.EstadoAutorizacion;

public interface ObservadorAutorizacion {
    void Actualizar(EstadoAutorizacion estadoAutorizacion);
}
