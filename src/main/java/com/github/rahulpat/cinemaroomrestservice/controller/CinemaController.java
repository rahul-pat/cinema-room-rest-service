package com.github.rahulpat.cinemaroomrestservice.controller;

import com.github.rahulpat.cinemaroomrestservice.model.Cinema;
import com.github.rahulpat.cinemaroomrestservice.model.Purchases;
import com.github.rahulpat.cinemaroomrestservice.model.Seat;
import com.github.rahulpat.cinemaroomrestservice.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class CinemaController {
    @Autowired
    private Cinema cinema;
    private Purchases purchase;

    // This endpoint returns all Seats available for purchase and the dimensions of the cinema
    @GetMapping("/seats")
    public Cinema returnCinemaSeats() {
        return cinema;
    }

    // This endpoint allows a user to purchase a ticket. It is expected that a Seat selection with be provided
    // in the Request Body in JSON format
    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseTickets(@RequestBody Seat seat) {
        // Index is -2 by default (i.e Seat already purchased)
        int index = -2;

        // if the Seat is out of bounds  -> the index is set to -1
        if (seat.getColumn() > cinema.getTotalColumns() || seat.getRow() > cinema.getTotalRows() || seat.getRow() < 1 || seat.getColumn() < 1) {
            index = -1;
        } else {
            // Loop through the ArrayList of availableSeats
            // if the Seat in the RequestBody is found and available for purchase -> set the index = i
            // break the for loop if the Seat is found
            // if the Seat is not found in the Array of availableSeats, index remains as -2 since the seat is already purchased
            for (int i = 0; i < cinema.getTotalSeats().size(); i++) {
                if (cinema.getTotalSeats().get(i).getRow() == seat.getRow()
                        && cinema.getTotalSeats().get(i).getColumn() == seat.getColumn()
                        && cinema.getTotalSeats().get(i).isPurchased() == false) {
                    index = i;
                    break;
                }
            }
        }

        // return a response back to the client based on the index
        // Options are: (a) seat already purchased, (b) seat is out of bounds or (c) seat purchased succesfully
        if (index == -2) {

            return new ResponseEntity(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);

        } else if (index == -1) {

            return new ResponseEntity(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);

        } else {
            // Set the selected Seat to purchased in the totalSeats list
            Seat selectedSeat = cinema.getTotalSeats().get(index);
            selectedSeat.setPurchased(true);

            // Add the selectedSeat to purchased list
            purchase = new Purchases(selectedSeat);
            cinema.getPurchases().add(purchase);

            // Loop through the availableSeat ArrayList and remove the selectedSeat from availableSeats
            for (int j = 0; j < cinema.getAvailableSeats().size(); j++) {
                if (cinema.getAvailableSeats().get(j).getRow() == selectedSeat.getRow() && cinema.getAvailableSeats().get(j).getColumn() == selectedSeat.getColumn()) {
                    cinema.getAvailableSeats().remove(j);
                }
            }

            // Keep track of income for stats endpoint
            cinema.incrementCurrentIncome(selectedSeat.getPrice());

            return new ResponseEntity(Map.of("token", purchase.getUuid(), "ticket", cinema.getTotalSeats().get(index)), HttpStatus.OK);
        }

    }
    // This endpoint allows a user to refund a ticket. The client must provide a valid token to successfully process a refund
    @PostMapping("/return")
    public ResponseEntity<String> returnTickets(@RequestBody Token token) {
        // Loop through the purchases and see if there exist the provided token
        for (int k = 0; k < cinema.getPurchases().size(); k++) {
            if (cinema.getPurchases().get(k).getUuid().equals(token.getToken())) {
                // Keep track of income for stats endpoint
                cinema.decrementCurrentIncome(cinema.getPurchases().get(k).getSeat().getPrice());

                Seat returnedSeat = cinema.getPurchases().get(k).getSeat();

                cinema.getAvailableSeats().add(returnedSeat);
                cinema.getPurchases().remove(returnedSeat);


                return new ResponseEntity(Map.of("returned_ticket", returnedSeat) , HttpStatus.OK);
            }
        }

        return new ResponseEntity(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
    }

    // This endpoint allows a user to get the stats of the cinema.
    // The client must provide a valid password to obtain the stats
    // The password is hardcoded to super_secret as these were the requirements to pass the project
    @PostMapping("/stats")
    public ResponseEntity<String> returnStats(@RequestParam(required = false) String password) {

        if (password == null) {

            return new ResponseEntity(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);

        } else if (password.equals("super_secret")) {

            return new ResponseEntity(Map.of("current_income", cinema.getCurrentIncome(),
                    "number_of_available_seats", cinema.getAvailableSeats().size(),
                    "number_of_purchased_tickets", cinema.getTotalSeats().size() - cinema.getAvailableSeats().size())
                    , HttpStatus.OK);

        } else {
            return new ResponseEntity(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }

    }
}
