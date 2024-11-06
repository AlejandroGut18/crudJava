public class ValidadorEntrada {

    //Validar sólo letras
    public static String validarLetras(String str) throws IllegalArgumentException {
        if (!str.matches("[a-zA-Z]+")){
            throw new IllegalArgumentException("Debe contener sólo letras 😘👌");

        }
        return str;
    }
    /*public static int validarEdad(int edad) throws EdadInvalidaException {
        if (edad <= 0) {
            throw new EdadInvalidaException("La edad debe ser mayor que cero. 😁😁😁😁");
        }
        return edad;
    }*/

    public static float validarNota(float nota) throws IllegalArgumentException{
        if (nota <= 0 || nota > 20) {
            throw new IllegalArgumentException("Nota invalida. Rango: 1 - 20. 😉😉😉");
        }
        return nota;
    }

    public static int validarOption(int option) throws IllegalArgumentException{
        if (option < 1 || option > 7) {
            throw new IllegalArgumentException("La opcion debe estar dentro del rango: 1 - 7.");
        }
        return option;
    }

    public static String validarLetrasNum(String str) throws IllegalArgumentException {
        if (!str.matches("[0-9a-zA-Z]+")) {
            throw new IllegalArgumentException("Ingrese un dato valido");
        }
        return str;
    }

    public static float validarFloat(String str) throws IllegalArgumentException {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Debe ser un número flotante válido 😜👌");
        }
    }

    public static int validarEntero(String input) throws IllegalArgumentException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: Ingrese un número entero válido.");
        }
    }

}
