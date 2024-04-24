package collection;


import java.util.Locale;
import java.io.PrintStream;

/**
 * Класс, предназначенный для валидации полей объекта LabWork.
 */
public class TicketFieldValidation {
    /**
     * Метод, осуществляющий валидацию полей класса LabWork.
     * @param field поле объекта LabWork.
     * @param value значение поля объекта LabWork.
     * @return boolean. true - валидация пройдена, false - валидация не пройдена.
     */
    public static boolean validate(String field, String value) {
        try {
            switch (field) {
                case "id": {
                    if (value == null || value.equals("")) throw new NullPointerException();
                    if (Integer.parseInt(value) > 0) return true;
                    break;
                }
                case "name": {
                    if (value.equals("") || value==null) throw new NullPointerException();
                    return true;
                }
                case "coordinate_x": {
                    if (value.equals("") || value==null) return true;

                    if (Long.parseLong(value)>-985) {
                        return true;
                    }
                    break;
                }
                case "coordinate_y": {
                    if (value == null || value.equals("")) return true;

                    if (Double.parseDouble(value)!=0) {
                        return true;
                    }
                    break;
                }
                case "maximumPoint": {
                    if (value == null || value.equals("")) throw new NullPointerException();
                    if (Integer.parseInt(value) > 0) return true;
                    break;
                }
                case "personalQualitiesMaximum": {
                    if (value == null || value.equals("")) return true;
                    if (Integer.parseInt(value) > 0) return true;
                    break;
                }
                case "difficulty": {
                    EyeColor.valueOf(value.toUpperCase(Locale.ROOT));
                    return true;
                }
                case "PersonName": {
                    if (value == null || value.equals("")) throw new NullPointerException();
                    return true;
                }
                case "height": {
                    if (value == null || value.equals("")) return true;
                    if(Float.parseFloat(value)>0) return true;
                    break;
                }
                case "PersonPassportID": {
                    if (value == null || value.equals("")) throw new NullPointerException();
                    if(value.length()<=28) return true;
                    break;
                }
                case "location_coordinate_x": {
                    if (value == null || value.equals("")) return true;
                    if(Integer.parseInt(value)!=0) return true;
                    break;
                }
                case "location_coordinate_y": {
                    if (value == null || value.equals("")) return true;
                    if(Float.parseFloat(value)!=0) return true;
                    break;
                }
                case "LocationName": {
                    if (value == null || value.equals("")) throw new NullPointerException();
                    if(value.length()<=669) return true;
                    break;
                }
                case "": {
                    return false;
                }
            }
        } catch (ClassCastException | IllegalArgumentException | NullPointerException ignored) {
        }
        return false;
    }

}

