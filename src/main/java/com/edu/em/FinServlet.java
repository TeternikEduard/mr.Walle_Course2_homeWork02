package com.edu.em;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/finTest")
public class FinServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] rows = req.getParameterValues("rows");
        double totalPriceSum = 0.0;
        double totalCostSum = 0.0;
        int totalCountItems = 0;

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.write("<table border='3' cellspacing='7'>");
        writer.write("<tr><th colspan='2'>Price</th><th colspan='2'>Quantity</th><th colspan='2'>Total Cost</th></tr>");

        if (rows != null) {
            for (String row : rows) {
                String[] cells = row.split(",");
                if (cells.length >= 2) {
                    try {
                        double price = Double.parseDouble(cells[0]);
                        int quantity = Integer.parseInt(cells[1]);
                        double totalCost = price * quantity;

                        totalPriceSum += price * quantity;
                        totalCountItems += quantity;
                        totalCostSum += totalCost;

                        writer.write("<tr>");
                        writer.write("<td colspan='2'>" + price + "</td>");
                        writer.write("<td colspan='2'>" + quantity + "</td>");
                        writer.write("<td colspan='2'>" + totalCost + "</td>");
                        writer.write("</tr>");
                    } catch (NumberFormatException e) {
                        writer.write("<tr><td colspan='3'>Incorrect data: " + row + "</td></tr>");
                    }
                }
            }

            double averagePrice;
            if (totalCountItems > 0) {
                averagePrice = totalPriceSum / totalCountItems;
            } else {
                averagePrice = 0;
            }
            String formattedAveragePrice = String.format("%.2f", averagePrice);
            String formattedTotalCostSum = String.format("%.2f", totalCostSum);

            writer.write("<tr><td colspan='3'>Average price: " + formattedAveragePrice + "</td></tr>");

            writer.write("<tr><td colspan='3'>Total sum of all costs: " + formattedTotalCostSum + "</td></tr>");
        } else {
            writer.write("<tr><td colspan='3'>No data to display</td></tr>");
        }

        writer.write("</table>");
    }
}