package lk.ijse.dep9.app.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SecurityFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getServletPath().matches("/api/v1/users/?") && req.getMethod().equals("POST")){
            chain.doFilter(req,res);
        }else if (req.getServletPath().matches("/api/v1/auth/.+")){
            chain.doFilter(req,res);
        }else {
            String authorization = req.getHeader("Authorization");
            if (authorization!=null && authorization.startsWith("Basic")){
                String token = authorization.substring("Basic".length() + 1);
                String credentials = new String(Base64.getDecoder().decode(token));
                String username = credentials.split(":")[0];
                String password = credentials.split(":")[1];

                System.out.println(username+":"+password);

            }

            HashMap<String, Object> errAttribute = new LinkedHashMap<>();
            errAttribute.put("status", HttpStatus.UNAUTHORIZED.value());
            errAttribute.put("error",HttpStatus.UNAUTHORIZED.getReasonPhrase());
            errAttribute.put("message", "Unauthorized entry");
            errAttribute.put("timestamp", new Date().toString());
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType("application/json");
            ObjectMapper objectMapper=new ObjectMapper();
            objectMapper.writeValue(res.getWriter(),errAttribute);

        }
    }


}
