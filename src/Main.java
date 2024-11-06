import java.io.*;
import java.util.*;

public class Main {
    private static List<Object> personajes = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        personajes.add(new Saiyajin("Goku", 10000.2F,"SSJBlue"));
        personajes.add(new Saiyajin("Vegeta", 2750.2F,"SSJ2"));
        personajes.add(new Humano("Krilin", 800.2F,"Multiples Artes"));
        personajes.add(new Humano("Roshi", 500.2F,"Kung Fu"));

        while (true){
            System.out.print("1-Agregar\n2-Leer\n3-Actualizar\n4-Eliminar\n5-Buscar\n6-Craer Reporte\n7-Salir\nElija una opción: ");
            int option = 0;
            while (option == 0){
                try {
                    option = leerEntero("");
                } catch (NumberFormatException e) {
                    System.out.println("Por favor, ingrese solo números.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (option == 7){
                break;
            }
            switch (option){
                case 1:
                    agregar();
                    clear();
                    break;
                case 2:
                    mostrarPersonajes();
                    clear();
                    break;
                case 3:
                    actualizarPersonaje();
                    clear();
                    break;
                case 4:
                    borrarPersonaje();
                    clear();
                    break;
                case 5:
                   buscarPersonaje();
                    clear();
                    break;
                case 6:
                    generarReporte();
                    clear();
                    break;
            }
        }
        scanner.close();
    }

    public static void generarReporte() {
        float nivelMinimo = leerFloat("Ingrese el nivel mínimo de poder para el reporte: ");

        // Filtrar los personajes con nivel de poder mayor o igual al ingresado
        List<Object> personajesFiltrados = personajes.stream()
                .filter(personaje -> {
                    if (personaje instanceof Saiyajin) {
                        return ((Saiyajin) personaje).getPower() >= nivelMinimo;
                    } else if (personaje instanceof Humano) {
                        return ((Humano) personaje).getPower() >= nivelMinimo;
                    }
                    return false;
                })
                .toList();

        try {
            File archivote = new File("reporte.txt");
            archivote.createNewFile();

            try (FileWriter fw = new FileWriter(archivote)) {
                for (Object personaje : personajesFiltrados) {
                    if (personaje instanceof Saiyajin) {
                        Saiyajin saiyajin = (Saiyajin) personaje;
                        fw.write(String.format("Saiyajin,%s,%f,%s\n", saiyajin.getName(), saiyajin.getPower(), saiyajin.getFase()));
                    } else if (personaje instanceof Humano) {
                        Humano humano = (Humano) personaje;
                        fw.write(String.format("Humano,%s,%f,%s\n", humano.getName(), humano.getPower(), humano.getArteMarcial()));
                    }
                }
            }

            System.out.println("Reporte generado correctamente en 'reporte.txt'");
            pausar();

        } catch (IOException e) {
            System.out.println("Error al crear el archivo de reporte.");
            e.printStackTrace();
        }
    }

    public static void buscarPersonaje() {
        String nombreBuscado = leerCadena("Ingrese el nombre completo del personaje a buscar: ").toLowerCase();
        boolean encontrado = false;

        // Filtrar el personaje que coincida exactamente con el nombre ingresado
        List<Object> personajesEncontrados = personajes.stream()
                .filter(personaje -> {
                    String nombre = personaje instanceof Saiyajin ? ((Saiyajin) personaje).getName() : ((Humano) personaje).getName();
                    return nombre.equalsIgnoreCase(nombreBuscado);
                })
                .toList();

        // Mostrar el personaje encontrado
        if (!personajesEncontrados.isEmpty()) {
            mostrarDetalles(personajesEncontrados, null);
            encontrado = true;
        }

        if (!encontrado) {
            System.out.println("No se encontró un personaje con ese nombre.");
        }
        pausar();
    }
    //
    public static void actualizarPersonaje() {
        String nombre = leerCadena("Ingrese el nombre del personaje a actualizar: ").toLowerCase();
        boolean encontrado = false;

        for (Object personaje : personajes) {
            String nombrePersonaje = personaje instanceof Saiyajin ? ((Saiyajin) personaje).getName() : ((Humano) personaje).getName();

            if (nombrePersonaje.toLowerCase().equals(nombre)) {
                if (personaje instanceof Saiyajin) {
                    System.out.println("Actualizando...");
                    ((Saiyajin) personaje).setPower(leerFloat("Ingrese nuevo nivel de poder: "));
                    ((Saiyajin) personaje).setFase(leerCadena("Ingrese nueva fase de super saiyan: "));
                } else {
                    System.out.println("Actualizando...");
                    ((Humano) personaje).setPower(leerFloat("Ingrese nuevo nivel de poder: "));
                    ((Humano) personaje).setArteMarcial(leerCadena("Ingrese nuevo arte marcial: "));
                }
                encontrado = true;
                break;
            }
        }
        if (encontrado) {
            guardarPersonajes();
            System.out.println("Personaje actualizado con éxito.");
        } else {
            System.out.println("No se encontró un personaje con ese nombre.");
        }
        pausar();
    }

    public static void borrarPersonaje() {
        String nombre = leerCadena("Ingrese el nombre del personaje a borrar: ").toLowerCase();
        boolean encontrado = false;

        for (Iterator<Object> iter = personajes.iterator(); iter.hasNext();) {
            Object personaje = iter.next();
            String nombrePersonaje = personaje instanceof Saiyajin ? ((Saiyajin) personaje).getName() : ((Humano) personaje).getName();

            if (nombrePersonaje.toLowerCase().equals(nombre)) {
                iter.remove();
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            guardarPersonajes();
            System.out.println("Personaje borrado con éxito.");
        } else {
            System.out.println("No se encontró un personaje con ese nombre.");
        }
        pausar();
    }

    private static void guardarPersonajes() {
        try (FileOutputStream archivo = new FileOutputStream("listaPersonajes.DBZ");
             ObjectOutputStream salida = new ObjectOutputStream(archivo)) {
            salida.writeObject(personajes);
        } catch (IOException e) {
            System.out.println("Error al guardar los personajes.");
            e.printStackTrace();
        }
    }
    //
    public static void mostrarPersonajes() {
        System.out.println("Seleccione la opción de visualización:");
        System.out.println("1 - Mostrar todos los personajes");
        System.out.println("2 - Mostrar solo Saiyajin");
        System.out.println("3 - Mostrar solo Humanos");
        int opcion = leerEntero("Ingrese una opción: ");

        try (FileInputStream archivo = new FileInputStream("listaPersonajes.DBZ")) {
            ObjectInputStream entrada = new ObjectInputStream(archivo);
            Object obj = entrada.readObject();

            if (obj instanceof List) {
                List<?> personajes = (List<?>) obj;

                if (personajes.isEmpty()) {
                    System.out.println("La lista de personajes está vacía.");
                    return;
                }
                switch (opcion) {
                    case 1:
                        System.out.println("Personajes:");
                        mostrarDetalles(personajes, null);
                        pausar();
                        break;
                    case 2:
                        System.out.println("Saiyajins:");
                        mostrarDetalles(personajes, Saiyajin.class);
                        pausar();
                        break;
                    case 3:
                        System.out.println("Humanos:");
                        mostrarDetalles(personajes, Humano.class);
                        pausar();
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } else {
                System.out.println("El archivo no contiene una lista de personajes.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de personajes. Asegúrese de haber guardado personajes.");
        } catch (IOException e) {
            System.out.println("Error de entrada/salida al deserializar el archivo.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Clase no encontrada al deserializar el archivo.");
            e.printStackTrace();
        }
    }
    //
    private static void mostrarDetalles(List<?> personajes, Class<?> tipoFiltro) {
        for (Object personaje : personajes) {
            if (tipoFiltro == null || personaje.getClass() == tipoFiltro) {
                if (personaje instanceof Saiyajin) {
                    Saiyajin saiyajin = (Saiyajin) personaje;
                    System.out.println("Saiyajin:");
                    System.out.println("Nombre: " + saiyajin.getName());
                    System.out.println("Nivel de poder: " + saiyajin.getPower());
                    System.out.println("Fase de super saiyan: " + saiyajin.getFase());
                    System.out.println("----------------");
                } else if (personaje instanceof Humano) {
                    Humano humano = (Humano) personaje;
                    System.out.println("Humano:");
                    System.out.println("Nombre: " + humano.getName());
                    System.out.println("Nivel de poder: " + humano.getPower());
                    System.out.println("Arte marcial: " + humano.getArteMarcial());
                    System.out.println("----------------");
                }
            }
        }
    }
    //
    public static void agregar() {
        System.out.println("1 - Saiyajin\n2 - Humano\n3 - Cancelar");
        int option = leerEntero("Ingrese una opción: ");
        if (option == 3) {
            return;
        }
        String nombre;
        float poder;
        switch (option) {
            case 1:
                System.out.println("Ingresa los datos del Personaje:");
                nombre = leerCadena("Nombre: ");
                poder = leerFloat("Nivel de poder: ");
                String fase = leerNumStrings("Fase de super saiyan: ");
                Saiyajin saiyajin = new Saiyajin(nombre, poder, fase);
                personajes.add(saiyajin);
                System.out.println("Personaje Saiyajin agregado con éxito.");
                break;
            case 2:
                System.out.println("Ingresa los datos del Personaje:");
                nombre = leerCadena("Nombre: ");
                poder = leerFloat("Nivel de poder: ");
                String arteMarcial = leerCadena("Arte marcial que domina el personaje: ");
                Humano humano = new Humano(nombre, poder, arteMarcial);
                personajes.add(humano);
                System.out.println("Personaje Humano agregado con éxito.");
                break;
            default:
                System.out.println("Opción inválida.");
                return;
        }

        // Serializar la lista completa de personajes
        try (FileOutputStream archivo = new FileOutputStream("listaPersonajes.DBZ");
             ObjectOutputStream salida = new ObjectOutputStream(archivo)) {
            salida.writeObject(personajes);
            System.out.println("Personajes serializados correctamente en una sola lista.");
        } catch (IOException e) {
            System.out.println("Error al guardar los personajes.");
            e.printStackTrace();
        }

        pausar();
    }
    
    public static String leerCadena(String message) {
        Scanner scanner = new Scanner(System.in);
        String cadena = null;
        while (cadena == null){
            System.out.print(message);
            try {
                cadena = ValidadorEntrada.validarLetras(scanner.nextLine());
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            } catch (Exception e){
                System.out.println("Error no controlado");
                System.out.println(e.getMessage());
            }
        }
        return cadena;
    }
    public static int leerEntero(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        int numero;
        while (true) {
            try {
                numero = ValidadorEntrada.validarEntero(scanner.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Error no controlado");
                System.out.println(e.getMessage());
            }
        }
        return numero;
    }
    public static float leerFloat(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        float numero;
        while (true){
            try {
                numero = ValidadorEntrada.validarFloat(scanner.nextLine());
                break;
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            } catch (Exception e){
                System.out.println("Error no controlado");
                System.out.println(e.getMessage());
            }
        }
        return numero;
    }
    public static String leerNumStrings(String message) {
        Scanner scanner = new Scanner(System.in);
        String cadena = null;
        while (cadena == null){
            System.out.print(message);
            try {
                cadena = ValidadorEntrada.validarLetrasNum(scanner.nextLine());
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            } catch (Exception e){
                System.out.println("Error no controlado");
                System.out.println(e.getMessage());
            }
        }
        return cadena;
    }
    public static void pausar() {
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.println("Presione Enter para continuar...");
            input = scanner.nextLine();
        } while (!input.isEmpty());
    }
    public static void clear() {
        for (int i = 0; i < 10; ++i) System.out.println();
    }
}