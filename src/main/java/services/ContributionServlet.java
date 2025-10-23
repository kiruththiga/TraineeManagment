package services;

import com.google.gson.Gson;
import dao.ContributionDao;
import model.Contribution;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/contribution")
public class ContributionServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final ContributionDao contributionDao = new ContributionDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        try(BufferedReader reader = request.getReader()) {
            Contribution contribution = gson.fromJson(reader, Contribution.class);

            if (contribution == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Invalid input\"}");
                return;
            }

            boolean isSaved = contributionDao.saveContribution(contribution);
            System.out.println(isSaved);

            if(isSaved) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\":\"Contribution saved successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Failed to insert contribution\"}");
            }
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid JSON: " + e.getMessage() + "\"}");
        }
    }
}
