import java.util.ArrayList;
import java.util.List;

import aula.Pet;

public class App {
  public static void main(String[] args) throws Exception {
    System.out.println("Hello, World!");

    Pet dog = new Pet();
    Pet cat = new Pet();

    dog.nomePet = "Chira";
    cat.nomePet = "Fred";

    dog.idade = 8;
    dog.peso = 20;
    dog.raca = "vira lata";

    cat.idade = 6;
    cat.peso = 6;
    cat.raca = "Branco de olhos azuis";

    List<Pet> lista = new ArrayList<Pet>();

    lista.add(dog);
    lista.add(cat);

    for (Pet pet : lista) {
      System.out.println(">>> PET <<<");
      System.out.println(pet.nomePet);
      System.out.println(pet.idade);
      System.out.println(pet.peso);
      System.out.println(pet.raca);
    }

    for (int i = 0; i < lista.size(); i++) {
      Pet pet = lista.get(i);
      System.out.println(">>> PET/2 <<<");
      System.out.println(pet.nomePet);
      System.out.println(pet.idade);
      System.out.println(pet.peso);
      System.out.println(pet.raca);
    }
  }
}
