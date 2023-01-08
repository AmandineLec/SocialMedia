/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package validationacquis;

import java.util.Scanner;

import validationacquis.utils.DBManager;

import java.sql.*;
public class App {
    static Scanner scan;
    public static void main(String[] args) {
        DBManager.init();
        User user = new User();
        scan = new Scanner(System.in);
        System.out.println("Que voulez-vous faire ? I = s'inscrire, C = consulter les profils");
        char chose = scan.next().charAt(0);
        if(chose == 'I'){
            user.saveUser();
        }
        else {
            System.out.println("Liste des profils disponibles : ");
            afficherlesprofil();
            System.out.println("Quel profil voulez-vous consulter ? ");        
            int id = scan.nextInt();    
            user = new User(id);
            System.out.println(user);
        }
        System.out.println("Que voulez-vous faire ? L = Liker un post, A = s'abonner à un nouveau profil, P = publier un post, Q = quitter");
        char choix = scan.next().charAt(0);
        if(choix == 'L') {
            System.out.println("Quel post voulez-vous liker ?");
            afficherlesposts();
            int idpost = scan.nextInt();
            user.liker(idpost);
        }
    
        else if(choix =='A'){
            System.out.println("A quel profil voulez-vous vous abonner ?");
            afficherlesprofil();
            int iduser = scan.nextInt();
            if(iduser == user.getId()){
                System.out.println("Vous ne pouvez pas vous abonner à votre propre profil ! ");
            }
            else{
                user.abonner(iduser);
            }
        }
        else if (choix == 'P'){
            System.out.println("Quel est le titre de votre article ?");
            String titre = scan.next(); 
            System.out.println("Merci de rentrer sa description ! ");
            String description = scan.next();
            user.publierpost(titre, description);
        }        
    }

    public static void afficherlesprofil(){
        try {
            ResultSet resultat = DBManager.query("SELECT * FROM User");
            while(resultat.next()){
                int id = resultat.getInt("User_id");
                String nom = resultat.getString("nom");
                System.out.println(id+ " : " +nom);        
                }
            }
        catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    public static void afficherlesposts() {
        try {
            ResultSet resultat = DBManager.query("SELECT Post.id_post, Post.titre, Post.description, User.nom FROM Post"+ 
                                                        " INNER JOIN User ON Post.User_id = User.User_id");
            while(resultat.next()){
                int id = resultat.getInt("Post.id_post");
                String titre = resultat.getString("Post.titre");
                String description = resultat.getString("Post.description");
                String nom = resultat.getString("User.nom");
                System.out.println(id+ " : " + titre + description + "(Auteur : "+nom +")");        
                }
            }
        catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    

}
