package com.example.demo.filtros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.example.demo.services.TokenService;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


@WebFilter(filterName = "jwt-filter", urlPatterns = "*")
@Component
public class TokenFilter implements Filter {

	@Autowired
	private TokenService tokenService;
	private List<String> rutasLibres;
	private List<String> rutasSwagger; //sacar en produccion
    private FilterConfig filterConf;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConf = filterConfig;
        this.rutasLibres = new LinkedList<String>();
        rutasLibres.add("/api/auth/login");
        rutasLibres.add("/api/usuarios");
        this.rutasSwagger = new LinkedList<String>();
        rutasSwagger.add("/swagger-ui"); //sacar estas 3 en produccion
        rutasSwagger.add("/swagger-ui/index.html");
        rutasSwagger.add("/v3/api-docs");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        
        //swagger, sacar en prod
        if (rutasSwagger.stream().anyMatch(ruta -> req.getRequestURI().startsWith(ruta))) {
            chain.doFilter(request, response);
            return;
        }
        
        //si está registrando un usuario, no chequea que esté el token
        if (rutasLibres.stream().anyMatch(ruta -> ruta.startsWith(req.getRequestURI()) && HttpMethod.POST.matches(req.getMethod()))) {
            chain.doFilter(request, response);
            return;
        }

        String token = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (token == null || !tokenService.validateToken(token)) {
            HttpServletResponse res = (HttpServletResponse) response;
        
            res.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        chain.doFilter(request, response);
    }

}