package com.notification.api.filters;

import com.notification.api.models.context.NotificationContext;
import com.notification.api.models.context.NotificationContextHolder;
import com.notification.api.utils.CommanUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.notification.api.constants.ApplicationConstants.X_REQUEST_ID;
import static com.notification.api.constants.ApplicationConstants.X_TENANT_ID;
/*
This OncePerRequestFilter validates tenant headers for API requests,
stores tenant context in a ThreadLocal scoped to the request thread,
and ensures cleanup after request completion to avoid thread reuse issues.
 */
@Component
public class NotificationAuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(isValidAPI(request.getRequestURI())){

            String xTenantId = request.getHeader(X_TENANT_ID);

            if (CommanUtils.isEmpty(xTenantId)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Unauthorized API Key !!!! Key is Required");
                String requestid = String.valueOf(CommanUtils.generateUUID());
                MDC.put(X_REQUEST_ID, requestid);
                response.setHeader(X_REQUEST_ID, requestid);

            }
            NotificationContextHolder.setContext(new NotificationContext(xTenantId));
        }
        filterChain.doFilter(request, response);

        if(isValidAPI(request.getRequestURI())){
            NotificationContextHolder.clear();
        }
    }
    static boolean isValidAPI(final String apiPath) {
        return apiPath.startsWith("/api");
    }



}
