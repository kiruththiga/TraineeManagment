package services;

import com.google.gson.Gson;
import dao.TraineeDAO;
import model.Trainee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/trainee")
    public class TraineeServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final TraineeDAO traineeDAO = new TraineeDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        try (BufferedReader reader = request.getReader()) {
            Trainee trainee = gson.fromJson(reader, Trainee.class);

            if (trainee == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Invalid input\"}");
                return;
            }
            boolean isSaved = traineeDAO.saveTrainee(trainee);
            System.out.println(isSaved);
            if (isSaved) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\":\"trainee saved successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Failed to insert trainee\"}");
                System.out.println();
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid JSON: " + e.getMessage() + "\"}");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String idParam = request.getParameter("id"); // GET param ?id=1
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                Trainee trainee = traineeDAO.getTraineeById(id);
                if (trainee != null) {
                    response.getWriter().write(gson.toJson(trainee));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\":\"Trainee not found\"}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Invalid ID format\"}");
            }
        } else {
            // If no ID, return all trainees
            List<Trainee> trainees = traineeDAO.getALlTrainees();
            response.getWriter().write(gson.toJson(trainees));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"ID parameter is required\"}");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            // Parse the updated trainee data from request body
            try (BufferedReader reader = request.getReader()) {
                Trainee trainee = gson.fromJson(reader, Trainee.class);
                if (trainee == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\":\"Invalid Trainee data\"}");
                    return;
                }

                boolean isUpdated = traineeDAO.updateTrainee(id, trainee);
                if (isUpdated) {
                    response.getWriter().write("{\"message\":\"Trainee updated successfully\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\":\"Trainee not found or update failed\"}");
                }
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid ID format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String idParam = request.getParameter("id");

        if (idParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"ID parameter is required\"}");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            boolean isDeleted = traineeDAO.deleteTrainee(id);

            if (isDeleted) {
                response.getWriter().write("{\"message\":\"Trainee deleted successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"Trainee not found or could not be deleted\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid ID format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

}