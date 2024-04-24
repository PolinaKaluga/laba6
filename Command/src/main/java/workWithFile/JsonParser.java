package workWithFile;

import collection.Ticket;


import collection.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class JsonParser {
    public List<Ticket> parseToCollection(String data) {
        List<Ticket> tickets = new ArrayList<>();

        if (data == null || data.isEmpty()) {
            System.out.println("ºaºaºaºEmpty or null data provided.");
            return tickets;
        }

        try {
            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                JSONObject coordinatesObject = jsonObject.getJSONObject("coordinates");
                float x = coordinatesObject.getFloat("x");
                float y = coordinatesObject.getFloat("y");
                JSONArray creationDateArray = jsonObject.getJSONArray("creationDate");
                String creationDateString;
                if (creationDateArray.get(0) instanceof Integer) {
                    creationDateString = Integer.toString((Integer) creationDateArray.get(0));
                } else {
                    creationDateString = creationDateArray.getString(0);
                }
                creationDateString = creationDateString + "-01-01";
                LocalDate creationDate = LocalDate.parse(creationDateString);
                int price = jsonObject.getInt("price");
                TicketType type = null;
                if (!jsonObject.isNull("type")) {
                    String typeString = jsonObject.getString("type");
                    type = TicketType.valueOf(typeString);
                }
                Person person = null;
                if (!jsonObject.isNull("person")) {
                    JSONObject personObject = jsonObject.getJSONObject("person");
                    Long height = null;
                    if (!personObject.isNull("height")) {
                        height = personObject.getLong("height");
                    }
                    Float weight = null;
                    if (!personObject.isNull("weight")) {
                        weight = (float) personObject.getDouble("weight");
                    }
                    EyeColor eyeColor = EyeColor.valueOf(personObject.getString("eyeColor"));
                    HairColor hairColor = HairColor.valueOf(personObject.getString("hairColor"));
                    person = new Person(height, weight, eyeColor, hairColor);
                }
                Ticket ticket = new Ticket(id, name, creationDate, price, type, person);
                tickets.add(ticket);
            }
        } catch (JSONException e) {
            System.out.println("Invalid JSON data provided: " + e.getMessage());
        }

        return tickets;
    }


    public String parseToJson(Ticket[] tickets) {
        JSONArray jsonArray = new JSONArray();

        for (Ticket ticket : tickets) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", ticket.getId());
            jsonObject.put("name", ticket.getName());
            jsonObject.put("coordinates", new JSONObject()
                    .put("x", ticket.getCoordinates().getX())
                    .put("y", ticket.getCoordinates().getY()));
            jsonObject.put("creationDate", ticket.getCreationDate().toString());
            jsonObject.put("price", ticket.getPrice());

            // Добавляем тип билета, если он не null
            if (ticket.getType() != null) {
                jsonObject.put("type", ticket.getType().toString());
            }

            // Добавляем информацию о человеке, если она есть
            if (ticket.getPerson() != null) {
                Person person = ticket.getPerson();
                JSONObject personObject = new JSONObject();
                if (person.getHeight() != null) {
                    personObject.put("height", person.getHeight());
                }
                if (person.getWeight() != null) {
                    personObject.put("weight", person.getWeight());
                }
                personObject.put("eyeColor", person.getEyeColor().toString());
                personObject.put("hairColor", person.getHairColor().toString());
                jsonObject.put("person", personObject);
            }

            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }



}
