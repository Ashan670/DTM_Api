package com.payable.ttt.api.user;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.payable.ttt.model.ORMUser;
import com.payable.ttt.config.HibernateUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/user/insert")
public class UserInsertServlet extends HttpServlet {

    private static final long serialVersionUID = 123456789L;
    private static final Logger logger = LoggerFactory.getLogger(UserInsertServlet.class);
    private static final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ORMUser user = parseUserFromRequest(request);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid user data\"}");
            return;
        }

        // Handle database transaction
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.save(user);
                transaction.commit();
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write("{\"message\":\"User created successfully\"}");
                logger.info("User created successfully with ID: {}", user.getId());
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                logger.error("Transaction failed, rolled back.", e);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Server error during user creation\"}");
            }
        } catch (Exception e) {
            logger.error("Server error during user creation.", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Server error during user creation\"}");
        }
    }

    private ORMUser parseUserFromRequest(HttpServletRequest request) throws IOException {
        try (BufferedReader reader = request.getReader()) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            ORMUser user = new ORMUser();
            
            if (jsonObject.has("firstName") && jsonObject.has("lastName") && jsonObject.has("email")) {
                user.setFirstName(jsonObject.get("firstName").getAsString());
                user.setLastName(jsonObject.get("lastName").getAsString());
                user.setEmail(jsonObject.get("email").getAsString());
                 
                
                user.setId(java.util.UUID.randomUUID().toString());
                user.setCreatedAt(new Date());
                user.setUpdatedAt(new Date());
                user.setCreatedBy(user.getId());
                user.setUpdatedBy(user.getId());
                user.setStatus(1);
                user.setPassword("Test@1234#");
                user.setSalt("E07717848B775B618382587C637AE03EBB9E35E4");
                user.setLast_ip(null);
                user.setAuth(0);
                user.setIsDefaultPassword(1);
                user.setLastLoggedTs(0);
                user.setExpiry(0);
                
                logger.info("Parsed user details: {}", user.toString());
                return user;
            } else {
                logger.error("Missing mandatory fields in JSON request.");
                return null;
            }
        } catch (Exception e) {
            logger.error("Error parsing JSON from request", e);
            return null;
        }
    }


}
