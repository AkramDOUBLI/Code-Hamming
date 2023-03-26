/**
 * DOUBLI Akram
 **/
import java.util.*;


class Hamming{

    private static Scanner sc;


    //créer la méthode getHammingCode() qui renvoie le code de Hamming.
    static int[] codeHamming(int mot[]) {
        // un tableau pour stocker le code de Hamming.
        int reslt[];
        int size;
        // connaitre le nombre requis de bits de parité.
        int i = 0, BitsParites = 0, j = 0, k = 0;
        size = mot.length;
        while (i < size) {
            // 2 puissance des bits de parité doit être égal à la position actuelle (nombre de bits parcourus + nombre de bits de parité + 1).
            if (Math.pow(2, BitsParites) == (i + BitsParites + 1)) {
                BitsParites++;
            } else {
                i++;
            }
        }
        // la taille du reslt est la taille de mot entré par l'utilisateur + le nombre de bits de parité.
        reslt = new int[size + BitsParites];

        // pour indiquer une valeur non définie à l'emplacement du bit de parité, nous initialisons le tableau resultat avec '2'
        for (i = 1; i <= reslt.length; i++) {
            // condition pour trouver l'emplacement du bit de parité.
            if (Math.pow(2, j) == i) {
                reslt[(i - 1)] = 2;
                j++;
            } else {
                reslt[(k + j)] = mot[k++];
            }
        }
        //  une boucle pour définir les bits de parité pairs aux emplacements des bits de parité.
        for (i = 0; i < BitsParites; i++) {

            reslt[((int) Math.pow(2, i)) - 1] = getBitParite(reslt, i);
        }
        return reslt;
    }

    //la méthode getBitParite() qui renvoie le bit de parité en fonction de la puissance.
    static int getBitParite(int[] reslt, int pow) {
        int bitPartie = 0;
        int size = reslt.length;

        for(int i = 0; i < size; i++) {
            // Vérifier si reslt[i] contient une valeur non définie ou non.
            if(reslt[i] != 2) {
                int k = i + 1;
                // Calculer la valeur binaire de l'indice et extraire le bit à la position 2^(puissance).
                int temp = (k / (int) Math.pow(2, pow)) % 2;
                // Si le bit est égal à 1, nous vérifions si la valeur de l'élément est également égale à 1.
                // Si oui, nous ajoutons 1 à la valeur de parité.
                bitPartie += (temp == 1) ? ((reslt[i] == 1) ? 1 : 0) : 0;
            }
        }

        return bitPartie % 2;
    }

    //methode permet de vérifier le code Hamming
    public static void verificationCode(int[] arr) {
        int size = 1;
        while (Math.pow(2, size) - 1 < arr.length) {
            size++;
        }

        System.out.println("La langeur du mot Hamming : " + arr.length);
        System.out.println("longueur du message :" + (Math.pow(2, size) - 1 - size));

        if (Math.pow(2, size) - 1 != arr.length) {
            throw new IllegalStateException("La longueur est invalide");
        }

        StringBuilder sb = new StringBuilder();
        for (int i : arr) {
            sb.append(i);
        }

        String str = sb.toString();

        int[] bitsDeControle = new int[size];

        for (int c = 0; c < size; c++) {
            for (Integer integer : controleDesBits(c, size)) {
                int index = (int) Math.pow(2, size) - integer - 1;
                if (str.charAt(index) != '0' && str.charAt(index) != '1') {
                    throw new IllegalStateException(index + " : caractère invalide.");
                }

                bitsDeControle[c] = (bitsDeControle[c] + Character.getNumericValue(str.charAt(index))) % 2;
            }
        }

        int index = 0;
        for (int i = 0; i < size; i++) {
            index += bitsDeControle[i] * Math.pow(2, i);
        }

        System.out.println(index);
        if (index != 0) {
            throw new IllegalStateException((str.length() - index) + " : il y a une erreur");
        }else   System.out.println("Le mot est correct");

    }
    private static ArrayList<Integer> controleDesBits(int i, int n) {
        ArrayList<Integer> integers = new ArrayList<>();

        for (int j = 0; j < Math.pow(2, n); j++) {
            String str = String.format("%" + n + "s", Integer.toBinaryString(j)).replace(' ', '0');
            int endroit = n - i - 1;
            if (str.charAt(endroit) == '1') {
                integers.add(j);
            }
        }

        return integers;
    }
    //Le programme principal
    public static void main(String args[])
    {
        // déclaration de variables et tableau
        int size, taille;
        int tab[];
        int hammingCode[];
        // créer un objet de la classe Scanner pour prendre l'entrée de l'utilisateur

        sc = new Scanner(System.in);
        System.out.println(" Entrez la taille de votre mot");
        size = sc.nextInt();
        // initialisation
        tab = new int[size];
        for(int j = 0 ; j < size ; j++) {
            boolean valide;
            do {
                valide=true;
                System.out.println("Enter le " + (size - j) + "-bits:");
                tab[size - j - 1] = sc.nextInt();
                if((tab[size - j - 1]!=1) && tab[size - j - 1]!=0) {
                    valide=false;
                }
            }while(valide ==false);

        }

        System.out.println("Le mot entré est :");
        for(int k = 0 ; k < size ; k++) {
            System.out.print(tab[size - k - 1]);
        }
        System.out.println();
        int choix1;
        do {
            sc = new Scanner(System.in);
            System.out.println("1 => calculer le code de Hamming\n2 => verifier code de Hamming");
            choix1 = sc.nextInt();
            if(choix1==1) {
                hammingCode = codeHamming(tab);
                taille = hammingCode.length;

                System.out.println("Le code de Hamming est:");
                for(int i = 0 ; i < taille; i++) {
                    System.out.print(hammingCode[(taille - i - 1)]);
                }
                // saut de ligne
                System.out.println("\n");
                int choix2;
                do {
                    sc = new Scanner(System.in);
                    System.out.println("1 => verifier le mot\nn'importe quel numéro => quitter");
                    choix2 = sc.nextInt();
                    if(choix2==1)
                        verificationCode(hammingCode);
                    else {
                        System.out.println("Au revoir ");
                        System.exit(0);
                    }
                }while(choix2!=1 && choix2!=2);
            }
            else if(choix1==2) {
                verificationCode(tab);
            }
            else {
                System.out.println("Veuillez bien choisir 1 pour l'ajout de bits de parité et 2 pour la verification ");
            }
        } while(choix1!=1 && choix1!=2);
    }

}